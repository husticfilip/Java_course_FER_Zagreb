package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

public class SmartScriptEngine {

	private DocumentNode documentNode;
	private RequestContext requestContext;
	private ObjectMultistack multistack = new ObjectMultistack();
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText().replaceAll(" +$", ""));
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String valueKey = node.getVariable().asText();
			// push key and inital value on multistacck
			multistack.push(valueKey, new ValueWrapper(node.getStartExpression().asText()));

			ValueWrapper endValue = new ValueWrapper(node.getEndExpression().asText());
			ValueWrapper stepValue = (node.getStepExpression() == null) ? new ValueWrapper(Integer.valueOf(1))
					: new ValueWrapper(node.getStepExpression().asText());

			while (true) {
				ValueWrapper currentValue = multistack.peek(valueKey);
				ValueWrapper testWrapper = new ValueWrapper(currentValue.getValue());
				testWrapper.subtract(endValue.getValue());

				Object testValue = testWrapper.getValue();
				if ((testValue instanceof Double && (Double) testValue >= 0)
						|| (testValue instanceof Integer && (Integer) testValue >= 0)) {
					// remove top most value on stack attached to valueKey.
					multistack.pop(valueKey);
					break;
				}

				for (int i = 0, size = node.numberOfChildren(); i < size; ++i) {
					node.getChild(i).accept(this);
				}

				currentValue = multistack.pop(valueKey);
				currentValue.add(stepValue.getValue());
				multistack.push(valueKey, currentValue);
			}

		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<ValueWrapper> objectStack = new Stack<ValueWrapper>();

			for (Element element : node.getElements()) {
				if (element instanceof ElementConstantDouble || element instanceof ElementConstantInteger
						|| element instanceof ElementString) {
					objectStack.push(new ValueWrapper(element.asText()));
				} else if (element instanceof ElementVariable) {
					try {
						objectStack.push(multistack.peek(element.asText()));
					} catch (EmptyStackException e) {
						e.printStackTrace();
						System.out.println("Uninitialized variable.");
						System.exit(0);
					}
				} else if (element instanceof ElementOperator) {
					doOperation(objectStack, element);
				} else if (element instanceof ElementFunction) {
					doFunction(objectStack, element);
				} else {
					throw new IllegalArgumentException("Illegal element in echo node.");
				}
			}

			Stack<ValueWrapper> reverseStack = new Stack<ValueWrapper>();
			while (!objectStack.isEmpty()) {
				reverseStack.push(objectStack.pop());
			}

			while (!reverseStack.isEmpty()) {
				try {
					requestContext.write(String.valueOf(reverseStack.pop().getValue()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		@Override
		public void visitDocumentNode(DocumentNode node) {

			for (int i = 0, size = node.numberOfChildren(); i < size; ++i) {
				node.getChild(i).accept(this);
			}
		}

		private void doOperation(Stack<ValueWrapper> objectStack, Element operation) {
			ValueWrapper first = objectStack.pop();
			ValueWrapper second = objectStack.pop();

			String op = operation.asText().trim();
			if (op.equals("+")) {
				first.add(second.getValue());
				objectStack.push(first);
			} else if (op.equals("-")) {
				first.subtract(second.getValue());
				objectStack.push(first);
			} else if (op.equals("*")) {
				first.multiply(second.getValue());
				objectStack.push(first);
			} else if (op.equals("/")) {
				first.divide(second.getValue());
				objectStack.push(first);
			} else {
				throw new UnsupportedOperationException("Unsupported operation");
			}
		}

		private void doFunction(Stack<ValueWrapper> objectStack, Element function) {
			String fun = function.asText().trim();

			if (fun.equals("@sin")) {
				ValueWrapper vw = objectStack.pop();
				String value = getInFormOfString(vw);
				objectStack.push(new ValueWrapper(Math.sin(Math.toRadians(Double.parseDouble(value)))));

			} else if (fun.equals("@decfmt")) {
				objectStack.push(format(objectStack.pop(), objectStack.pop()));

			} else if (fun.equals("@dup")) {
				objectStack.push(new ValueWrapper(objectStack.peek().getValue()));
			} else if (fun.equals("@swap")) {
				ValueWrapper a = objectStack.pop();
				ValueWrapper b = objectStack.pop();

				objectStack.push(b);
				objectStack.push(a);

			} else if (fun.equals("@setMimeType")) {
				ValueWrapper x = objectStack.pop();
				requestContext.setMimeType((String) x.getValue());

			} else if (fun.equals("@paramGet")) {
				ValueWrapper def = objectStack.pop();
				ValueWrapper name = objectStack.pop();
				String param = requestContext.getParameter((String) name.getValue());
				objectStack.push(param == null ? def : new ValueWrapper(param));

			} else if (fun.equals("@pparamGet")) {
				ValueWrapper def = objectStack.pop();
				ValueWrapper name = objectStack.pop();
				String param = requestContext.getPersistentParameter((String) name.getValue());
				objectStack.push(param == null ? def : new ValueWrapper(param));

			} else if (fun.equals("@pparamSet")) {
				String name = getInFormOfString(objectStack.pop());
				String value = getInFormOfString(objectStack.pop());

				requestContext.setPersistentParameter(name, value);

			} else if (fun.equals("@pparamDel")) {
				ValueWrapper name = objectStack.pop();
				requestContext.removePersistentParameter((String) name.getValue());

			} else if (fun.equals("@tparamGet")) {
				ValueWrapper def = objectStack.pop();
				ValueWrapper name = objectStack.pop();
				String param = requestContext.getTemporaryParameter((String) name.getValue());
				objectStack.push(param == null ? def : new ValueWrapper(param));

			} else if (fun.equals("@tparamSet")) {
				String name = getInFormOfString(objectStack.pop());
				String value = getInFormOfString(objectStack.pop());

				requestContext.setTemporaryParameter(name, value);

			} else if (fun.equals("@tparamDel")) {
				ValueWrapper name = objectStack.pop();
				requestContext.removeTemporaryParameter((String) name.getValue());

			} else {
				throw new UnsupportedOperationException("Unsupported function in echo node.");
			}

		}

		private ValueWrapper format(ValueWrapper format, ValueWrapper value) {

			if (!(format.getValue() instanceof String)) {
				throw new IllegalArgumentException("Format is not in form of string.");
			}

			String strFormat = (String) format.getValue();
			String strValue = getInFormOfString(value);
			double doubleValue = Double.parseDouble(strValue);

			if (!strFormat.contains(".")) {
				return new ValueWrapper(String.format("%.0f", doubleValue));
			} else {
				// "0.000" -> 12.012
				int numOfDec = strFormat.length() - strFormat.indexOf(".") - 1;
				doubleValue = round(doubleValue, numOfDec);
				return new ValueWrapper(String.format("%." + numOfDec + "f", doubleValue));
			}
		}

		private String getInFormOfString(ValueWrapper wrapper) {
			Object value = wrapper.getValue();

			if (value instanceof Integer || value instanceof Double) {
				return String.valueOf(value);
			} else if (value instanceof String) {
				return (String) value;
			} else {
				throw new IllegalArgumentException("Parameter for function is not integer,double or string.");
			}

		}

		private double round(double value, int places) {
			long factor = (long) Math.pow(10, places);
			value = value * factor;
			long tmp = Math.round(value);
			return (double) tmp / factor;
		}

	};

	/**
	 * Basic constructr.
	 * 
	 * @param documentNode   document by which engine will run calculations.
	 * @param requestContext request which holds atributes for calculation.
	 * @throws NullPointerException if {@link DocumentNode} or
	 *                              {@link RequestContext} are null.
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		Objects.requireNonNull(documentNode);
		Objects.requireNonNull(requestContext);

		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	public void execute() {
		documentNode.accept(visitor);
	}
}

package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Scanner;
import java.util.SortedMap;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;

public class CommandsTest {
	
//	@Test 
//	public void testCharsetComm() {
//		CharsetsCommand charComm = new CharsetsCommand();
//		charComm.executeCommand(new EnviromentImpl(), null);
//		
//		System.out.println();
//		System.out.println();
//		
//		List<String> description = charComm.getCommandDescription();
//		for(String line : description) {
//			System.out.println(line);
//		}
//	} 
	
//	@Test
//	public void testCatCommand() {
//		CatCommand catCommand = new CatCommand();
//		catCommand.executeCommand(new EnviromentImpl(), "pom.xml");	
//	}
	
//	@Test
//	public void testCatCommandWithGivenCharset() {
//		CatCommand catCommand = new CatCommand();
//		catCommand.executeCommand(new EnviromentImpl(), "pom.xml UTF-16");	
//	}
	
//	@Test
//	public void testCatCommandWrongCharset() {
//		CatCommand catCommand = new CatCommand();
//		catCommand.executeCommand(new EnviromentImpl(), "pom.xml UTF-1");	
//	}
	
//	@Test
//	public void testCatCommandWithNonExistingFile() {
//		CatCommand catCommand = new CatCommand();
//		catCommand.executeCommand(new EnviromentImpl(), "p.xml");	
//	}
	
//	@Test
//	public void testCatDescription() {
//		CatCommand catCommand = new CatCommand();
//		List<String> desc =catCommand.getCommandDescription();
//		
//		for(String d : desc)
//			System.out.println(d);
//	}
	
//	@Test
//	public void testLs() {
//		LsCommand lsCommand = new LsCommand();
//		lsCommand.executeCommand(new EnviromentImpl(), "\"C:\\Users\\LegaFilip\\Desktop\\sem6\\java brat\"");
//	}

//	@Test
//	public void testLsWithoArgs() {
//		LsCommand lsCommand = new LsCommand();
//		lsCommand.executeCommand(new EnviromentImpl(), "");
//	}

//	@Test
//	public void testLSSrcMain() {
//		LsCommand lsCommand = new LsCommand();
//		lsCommand.executeCommand(new EnviromentImpl(), "src\\main");
//	}
	
//	@Test
//	public void testLsFileName() {
//		LsCommand lsCommand = new LsCommand();
//		lsCommand.executeCommand(new EnviromentImpl(), "pom.xml");
//	}
	
//	@Test
//	public void testLsNonExistingDir() {
//		LsCommand lsCommand = new LsCommand();
//		lsCommand.executeCommand(new EnviromentImpl(), "direktorij");
//	}
	
//	@Test
//	public void testLsValid() {
//		LsCommand lsCommand = new LsCommand();
//		lsCommand.executeCommand(new EnviromentImpl(), "src/main");
//	}
	
//	@Test
//	public void testMoreThanOneArgument() {
//		LsCommand lsCommand = new LsCommand();
//		lsCommand.executeCommand(new EnviromentImpl(), "src/main a");
//	}
	
//	@Test
//	public void testTreeCommand() {
//		TreeCommand treeCommand = new TreeCommand();
//		treeCommand.executeCommand(new EnviromentImpl(),"\"C:\\Users\\LegaFilip\\Desktop\\sem6\\java brat\\predavanje5\"");
//	}
	
//	@Test
//	public void testTreeCurrentDir() {
//		TreeCommand treeCommand = new TreeCommand();
//		treeCommand.executeCommand(new EnviromentImpl(),"");
//	}
	
//	@Test
//	public void testTree2Args() {
//		TreeCommand treeCommand = new TreeCommand();
//		treeCommand.executeCommand(new EnviromentImpl(),"C:\\Users abc");
//	}
	
//	@Test
//	public void testTreePathNotExist() {
//		TreeCommand treeCommand = new TreeCommand();
//		treeCommand.executeCommand(new EnviromentImpl(),"\"C:\\Users     \\a:\"");
//	}
	
//	@Test
//	public void testTreePathToTheFile() {
//		TreeCommand treeCommand = new TreeCommand();
//		treeCommand.executeCommand(new EnviromentImpl(),"pom.xml");
//	}

//	@Test
//	public void testCopyToDir() {
//		CopyCommand copyCommand = new CopyCommand();
//		copyCommand.executeCommand(new EnviromentImpl(),"\"C:\\Users\\LegaFilip\\Desktop\\sem6\\java brat\\predavanje5\\db.txt\"     \"C:\\Users\\LegaFilip\\Desktop\\sem6\\java brat\\predavanje5\\mapa\"   "  );
//	}
	
//	@Test
//	public void testCopyOverwrite() {
//		CopyCommand copyCommand = new CopyCommand();
//		copyCommand.executeCommand(new EnviromentImpl(),"\"C:\\Users\\LegaFilip\\Desktop\\sem6\\java brat\\predavanje5\\plant1.txt\"     \"C:\\Users\\LegaFilip\\Desktop\\sem6\\java brat\\predavanje5\\mapa\\db.txt\"   "  );
//	}
	
//	@Test
//	public void testCopyToNewFile() {
//		CopyCommand copyCommand = new CopyCommand();
//		copyCommand.executeCommand(new EnviromentImpl(),"\"C:\\Users\\LegaFilip\\Desktop\\sem6\\java brat\\predavanje5\\db.txt\"     \"C:\\Users\\LegaFilip\\Desktop\\sem6\\java brat\\predavanje5\\mapa\\db2.txt\"   "  );
//	}
	
//	@Test
//	public void testCopyUnexistingSrc() {
//		CopyCommand copyCommand = new CopyCommand();
//		copyCommand.executeCommand(new EnviromentImpl(),"C:\\Users\\LegaFilip\\Desktop\\sem6\\a.txt.txt     \"C:\\Users\\LegaFilip\\Desktop\\sem6\\java brat\\predavanje5\\mapa\\db2.txt\"   "  );
//	}
	
//	@Test
//	public void testCopyUnexistingDest() {
//		CopyCommand copyCommand = new CopyCommand();
//		copyCommand.executeCommand(new EnviromentImpl(),"\"C:\\Users\\LegaFilip\\Desktop\\sem6\\java brat\\predavanje5\\plant1.txt\"     C:\\Users\\LegaFilip\\Desktops\\sem6  "  );
//	}
	
	
//	@Test
//	public void testCopyNonExistingSrc() {
//		CopyCommand copyCommand = new CopyCommand();
//		copyCommand.executeCommand(new EnviromentImpl(),"\"C:\\Users\\ \"     \"C:\\Users\\LegaFilip\\Desktop\\sem6\\java brat\\predavanje5\\mapa\\plant1.txt\"   "  );
//	}
	
//	@Test
//	public void testCopyNonExistingDest() {
//		CopyCommand copyCommand = new CopyCommand();
//		copyCommand.executeCommand(new EnviromentImpl(),"pom.xml     \"src\\   pr  \\a \"   "  );
//	}
	
//	@Test
//	public void testMkdir() {
//		MkdirCommand mkdir = new MkdirCommand();
//		mkdir.executeCommand(new EnviromentImpl()," a" );
//	}
	
	
//	@Test
//	public void testMkdirQoutes() {
//		MkdirCommand mkdir = new MkdirCommand();
//		mkdir.executeCommand(new EnviromentImpl()," \"a bc\"" );
//	}
	
//	@Test
//	public void testMkdirToManyArgs() {
//		MkdirCommand mkdir = new MkdirCommand();
//		mkdir.executeCommand(new EnviromentImpl()," a bc" );
//	}
	
//	@Test
//	public void testMkdirToManyArgs() {
//		MkdirCommand mkdir = new MkdirCommand();
//		mkdir.executeCommand(new EnviromentImpl()," \"a bc\" he" );
//	}
	
//	@Test
//	public void testMkdirNoArgs() {
//		MkdirCommand mkdir = new MkdirCommand();
//		mkdir.executeCommand(new EnviromentImpl(),"" );
//	}
	
//	@Test
//	public void testMkdirWrongArgtype() {
//		MkdirCommand mkdir = new MkdirCommand();
//		mkdir.executeCommand(new EnviromentImpl(),"c\\" );
//	}

//	@Test
//	public void testHexDump() {
//		HexDump hex = new HexDump();
//		hex.executeCommand(new EnviromentImpl(),"pom.xml" );
//	}
	
//	@Test
//	public void testHexDumpQuotePath() {
//		HexDump hex = new HexDump();
//		hex.executeCommand(new EnviromentImpl(),"\"C:\\Users\\LegaFilip\\Desktop\\sem6\\java brat\\korekcije.txt  \"" );
//	}
	
//	@Test
//	public void testHexUnexistingFile() {
//		HexDump hex = new HexDump();
//		hex.executeCommand(new EnviromentImpl(),"\"C:\\Users\\LegaFilip\\Desktop\\sem6\\java brat\\korekcije10.txt  \"" );
//	}
	
//	@Test
//	public void testHexDirectory() {
//		HexDump hex = new HexDump();
//		hex.executeCommand(new EnviromentImpl(),"\"java brat\\\"" );
//	}
	
//	@Test
//	public void testHexDirectory() {
//		HexDump hex = new HexDump();
//		hex.executeCommand(new EnviromentImpl(),"\"C:\\Users\\LegaFilip\\Desktop\\sem6\\java brat  \"" );
//	}
	
//	@Test
//	public void testHexNoArgs() {
//		HexDump hex = new HexDump();
//		assertThrows(NullPointerException.class,()->{
//			hex.executeCommand(new EnviromentImpl(),null );
//		} );
//	}
	
//	@Test
//	public void testHexNoArgs() {
//		HexDump hex = new HexDump();
//		hex.executeCommand(new EnviromentImpl(),"" );
//	}
	
	@Test
	public void testTwoArgs() {
		HexDumpCommand hex = new HexDumpCommand();
		hex.executeCommand(new EnviromentImpl(),"pom.xml a" );
	}
	
	
	
	private static class EnviromentImpl implements Environment {

		@Override
		public String readLine() throws ShellIOException {
			Scanner sc = new Scanner(System.in);
			
			sc.close();
			return sc.next();
		}

		@Override
		public void write(String text) throws ShellIOException {
			System.out.printf(text);
		}

		@Override
		public void writeln(String text) throws ShellIOException {
			System.out.println(text);
		}

		@Override
		public SortedMap<String, ShellCommand> commands() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Character getMultilineSymbol() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			// TODO Auto-generated method stub

		}

		@Override
		public Character getPromptSymbol() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			// TODO Auto-generated method stub

		}

		@Override
		public Character getMorelinesSymbol() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			// TODO Auto-generated method stub

		}
	}
}

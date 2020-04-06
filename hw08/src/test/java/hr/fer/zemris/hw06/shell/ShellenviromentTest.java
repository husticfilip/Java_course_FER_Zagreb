package hr.fer.zemris.hw06.shell;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Paths;
import java.util.Scanner;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellEnviroment;
import hr.fer.zemris.java.hw06.shell.ShellIOException;

public class ShellenviromentTest {

	
//	@Test
//	public void testCurrendDir() {
//		Scanner sc = new Scanner(System.in);
//		ShellEnviroment  env= new ShellEnviroment(sc, new TreeMap<String, ShellCommand>(), 'a', 'b', 'c');
//		System.out.println(env.getCurrentDirectory());
//	}
	
//	@Test
//	public void testSetValid() {
//		Scanner sc = new Scanner(System.in);
//		ShellEnviroment  env= new ShellEnviroment(sc, new TreeMap<String, ShellCommand>(), 'a', 'b', 'c');
//		env.setCurrentDirectory(Paths.get("C:\\Users\\LegaFilip\\Desktop\\sem6"));	
//		System.out.println(env.getCurrentDirectory());
//	sc.close();
//	}
	
//	@Test
//	public void testSetValid2() {
//		Scanner sc = new Scanner(System.in);
//		ShellEnviroment  env= new ShellEnviroment(sc, new TreeMap<String, ShellCommand>(), 'a', 'b', 'c');
//		env.setCurrentDirectory(Paths.get("src"));	
//		System.out.println(env.getCurrentDirectory());
//	sc.close();
//	}
	
//	@Test
//	public void testSetValid3() {
//		Scanner sc = new Scanner(System.in);
//		ShellEnviroment  env= new ShellEnviroment(sc, new TreeMap<String, ShellCommand>(), 'a', 'b', 'c');
//		env.setCurrentDirectory(Paths.get("src"));	
//		System.out.println(env.getCurrentDirectory());
//		env.setCurrentDirectory(Paths.get(""));	
//		System.out.println(env.getCurrentDirectory());
//		sc.close();
//	}
	
//	@Test
//	public void testSetInalidPath() {
//		Scanner sc = new Scanner(System.in);
//		ShellEnviroment  env= new ShellEnviroment(sc, new TreeMap<String, ShellCommand>(), 'a', 'b', 'c');
//		
//		assertThrows(ShellIOException.class,()->{
//			env.setCurrentDirectory(Paths.get("a"));				
//		} );
//		System.out.println(env.getCurrentDirectory());
//		sc.close();
//	}
	
	@Test
	public void testSetInalidPath2() {
		Scanner sc = new Scanner(System.in);
		ShellEnviroment  env= new ShellEnviroment(sc, new TreeMap<String, ShellCommand>(), 'a', 'b', 'c');
		
		assertThrows(ShellIOException.class,()->{
			env.setCurrentDirectory(Paths.get("C:\\Users\\LegaFilip\\Desktop\\sem100"));				
		} );
		System.out.println(env.getCurrentDirectory());
		sc.close();
	}
	

	
}

package hr.fer.zemris.hw06.shell;

import java.util.Scanner;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellEnviroment;
import hr.fer.zemris.java.hw06.shell.commands.PushdCommand;
import hr.fer.zemris.java.hw06.shell.commands.PwdCommand;

public class ShellCommandsTest {

//	@Test
//	public void pwdCommand() {
//		Scanner sc = new Scanner(System.in);
//		ShellEnviroment  env= new ShellEnviroment(sc, new TreeMap<String, ShellCommand>(), 'a', 'b', 'c');
//		
//		new PwdCommand().executeCommand(env, "");
//		
//		sc.close();
//	}
	
//	@Test
//	public void pwdCommandSetDirWithArgs() {
//		Scanner sc = new Scanner(System.in);
//		ShellEnviroment  env= new ShellEnviroment(sc, new TreeMap<String, ShellCommand>(), 'a', 'b', 'c');
//		
//		new PwdCommand().executeCommand(env, "a");
//		
//		sc.close();
//	}
	
//	@Test
//	public void cdCommandGoToSrc() {
//		Scanner sc = new Scanner(System.in);
//		ShellEnviroment  env= new ShellEnviroment(sc, new TreeMap<String, ShellCommand>(), 'a', 'b', 'c');
//		
//		CdCommand cd = new CdCommand();
//		PwdCommand pwd = new PwdCommand();
//		cd.executeCommand(env, "src/main");
//		pwd.executeCommand(env, "");
//		
//		sc.close();
//	}
	
//	@Test
//	public void cdCommandGoToAbsolutePath() {
//		Scanner sc = new Scanner(System.in);
//		ShellEnviroment  env= new ShellEnviroment(sc, new TreeMap<String, ShellCommand>(), 'a', 'b', 'c');
//		
//		CdCommand cd = new CdCommand();
//		PwdCommand pwd = new PwdCommand();
//		cd.executeCommand(env, "C:\\Users\\LegaFilip\\Desktop\\sem6");
//		pwd.executeCommand(env, "");
//		
//		sc.close();
//	}
	
//	@Test
//	public void cdCommandInvalidPath() {
//		Scanner sc = new Scanner(System.in);
//		ShellEnviroment  env= new ShellEnviroment(sc, new TreeMap<String, ShellCommand>(), 'a', 'b', 'c');
//		
//		CdCommand cd = new CdCommand();
//		PwdCommand pwd = new PwdCommand();
//		cd.executeCommand(env, "src1");
//		pwd.executeCommand(env, "");
//		
//		sc.close();
//	}
	
	
//	@Test
//	public void cdCommandInvalidAbsolutePath() {
//		Scanner sc = new Scanner(System.in);
//		ShellEnviroment  env= new ShellEnviroment(sc, new TreeMap<String, ShellCommand>(), 'a', 'b', 'c');
//		
//		CdCommand cd = new CdCommand();
//		PwdCommand pwd = new PwdCommand();
//		cd.executeCommand(env, "C:\\Users\\LegaFilip\\Desktop\\sem199");
//		pwd.executeCommand(env, "");
//		
//		sc.close();
//	}

	
//	@Test
//	public void psuhdCommand() {
//		Scanner sc = new Scanner(System.in);
//		ShellEnviroment  env= new ShellEnviroment(sc, new TreeMap<String, ShellCommand>(), 'a', 'b', 'c');
//		
//		PushdCommand push = new PushdCommand();
//		PwdCommand pwd = new PwdCommand();
//		new CdCommand().executeCommand(env, "C:\\Users\\LegaFilip\\Desktop\\javaTest");
//		push.executeCommand(env, "pom");
//		pwd.executeCommand(env, "");
//		
//		sc.close();
//	}
	
	
//	@Test
//	public void psuhdCommandAbsolutePath() {
//		Scanner sc = new Scanner(System.in);
//		ShellEnviroment  env= new ShellEnviroment(sc, new TreeMap<String, ShellCommand>(), 'a', 'b', 'c');
//		
//		PushdCommand push = new PushdCommand();
//		PwdCommand pwd = new PwdCommand();
//		push.executeCommand(env, "C:\\Users\\LegaFilip\\Desktop\\javaTest");
//		pwd.executeCommand(env, "");
//		
//		sc.close();
//	}
	
//	@Test
//	public void psuhdCommandInvalid() {
//		Scanner sc = new Scanner(System.in);
//		ShellEnviroment  env= new ShellEnviroment(sc, new TreeMap<String, ShellCommand>(), 'a', 'b', 'c');
//		
//		PushdCommand push = new PushdCommand();
//		PwdCommand pwd = new PwdCommand();
//		push.executeCommand(env, "srcccc");
//		pwd.executeCommand(env, "");
//		
//		sc.close();
//	}
	
	
	@Test
	public void psuhdCommandInvalidAbsolute() {
		Scanner sc = new Scanner(System.in);
		ShellEnviroment  env= new ShellEnviroment(sc, new TreeMap<String, ShellCommand>(), 'a', 'b', 'c');
		
		PushdCommand push = new PushdCommand();
		PwdCommand pwd = new PwdCommand();
		push.executeCommand(env, "C:\\Users\\LegaFilip\\Desktop\\javaTest99");
		pwd.executeCommand(env, "");
		
		sc.close();
	}
	
	
}

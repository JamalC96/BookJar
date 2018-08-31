package controllers;


import java.io.File;
import java.io.IOException;
import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellDependent;
import asg.cliche.ShellFactory;
import models.Users;
import utils.Serializer;
import utils.XMLSerializer;

public class Main implements ShellDependent {
	private static final String ADMIN = "admin";
	private Shell shell;
	public BookJarAPI bookjar = new BookJarAPI();
	
	
	

	//Constructor for main the to load file also read and write from its data
	public Main() throws Exception {
		File books = new File("./files/users.xml");
		Serializer serializer = new XMLSerializer(books);
		bookjar = new BookJarAPI(serializer);
		if (books.isFile())
		{
			bookjar.load();
		}
	}

	public void cliSetShell(Shell shell) {
		this.shell = shell;
	}
	
	//Login Method
	@Command(description = "Log in")
	public void logIn(@Param(name = "id") Long userID, @Param(name = "lastName") String lastName) throws IOException {
		if (bookjar.login(userID, lastName) && bookjar.currentUser.isPresent()) {
			Users user = bookjar.currentUser.get();
			System.out.println("You are logged in as " + user.firstName + " " +user.lastName);
			if (user.role != null && user.role.equals(ADMIN)) {
				Admin adminMenu = new Admin(bookjar, user.firstName);
				ShellFactory.createSubshell(user.firstName, shell, "admin", adminMenu).commandLoop();
			} else {
				Default defaultMenu = new Default(bookjar, user);
				ShellFactory.createSubshell(user.firstName, shell, "default", defaultMenu).commandLoop();
			}
		} else {
			System.out.println("Sorry");
			System.out.println("Unknown username/password.");
		}
	}
	
	
	@Command(description= "Initial CSV Load")
	public void initialLoad() throws IOException{
		bookjar.initalLoad();
	}



	public static void main(String[] args) throws Exception {
		Main main = new Main();
		
		Shell shell = ShellFactory.createConsoleShell("User", "Welcome to BookJar - please type ?list for a menu or ?help for instructions", main);
		shell.commandLoop();
		main.bookjar.store();

	}

}
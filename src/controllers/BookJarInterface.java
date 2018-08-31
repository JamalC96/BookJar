package controllers;

import java.util.Collection;

import models.Books;
import models.Users;

public interface BookJarInterface {
	public void load() throws Exception;
	public void store() throws Exception;
	public void createUser(String firstName, String lastName, int age, String gender, String occupation);
	public Users getUserByName(String name);
	public Users getUserById(Long id);
	public void deleteUser(Long id);
	public Collection<Books> getBooks();
	public Books getBookByTitle(String title);
	public void addRatings(Long userID, Long bookID, int rating);
}
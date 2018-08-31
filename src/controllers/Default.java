package controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import asg.cliche.Command;
import asg.cliche.Param;
import models.Books;
import models.Ratings;
import models.Users;

public class Default {
	private String name;
	private Users user;
	private BookJarAPI bookjarAPI;

	public Default(BookJarAPI bookjarAPI, Users user) {
		this.bookjarAPI = bookjarAPI;
		this.setName(user.firstName);
		this.user=user;
	}
	
	public Users getUser() 
	{
		return user;
	}

	public void setUser(Users user) 
	{
		this.user = user;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/*
	 .................... Users ...................
	 */
	
	//TreeSet instead of hash map, easier to use
	//lists all users in order, second name first
	
	@Command(description = "Get users sorted by there Name")
	public void getAllUsers() {
		TreeSet<Users> sortedUsers = new TreeSet<Users>();
		sortedUsers.addAll(bookjarAPI.getUsers());
		Iterator<Users> iter = sortedUsers.iterator();
		while(iter.hasNext()) {
			Users u = iter.next();
			System.out.println(u.firstName + " " + u.lastName);
		}
	}
	
	@Command(description = "Get a Users details")
	public void getUser(@Param(name = "name") Long id) {
		Users user = bookjarAPI.getUserById(id);
	    System.out.println(user);
	}
	
	@Command(description = "Create a new User")
	public void createUser(@Param(name = "first name") String firstName, @Param(name = "last name") String lastName,
	@Param(name = "age") int age, @Param(name = "gender") String gender, @Param(name = "occupation") String occupation) {
		bookjarAPI.createUser(firstName, lastName, age, gender, occupation);
	}
	
	@Command(description="Logout")
	public void LogOut() throws Exception{
		bookjarAPI.logout();
		System.out.println("Logging out");
	}
	
	@Command(description="Delete a Rating")
	public void deleteUser(@Param(name="User Id")long id){
			bookjarAPI.deleteUser(id);
	}
	
	@Command(description = "search a user by name")
	public void getUserByName(String name) {
		ArrayList<Users> users = new ArrayList<Users>();
		users.addAll(bookjarAPI.getUsers());
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).firstName.toLowerCase().contains(name.toLowerCase())) {
				System.out.println(users.get(i));
			}
		}
		
		
		
	}
	
	
	
	
	/*
	......................... Ratings .........................
	 */
	@Command(description = "Add a rating")
	public void addRating(@Param(name = "userId") Long userId, @Param(name = "bookId") Long bookId, @Param(name = "rating") int rating) {
		 bookjarAPI.addRatings(userId, bookId, rating);
	}
	
	@Command(description="Get User Ratings")
	public Map<Long, Ratings> getUserRating(@Param(name="User ID")long id){
		return bookjarAPI.getUserRating(id);
	}
	
	@Command(description="Get Books Ratings")
	public Map<Long,Ratings> getBooksRating(@Param(name="Books Id")long id)
	{
		return bookjarAPI.getBookRating(id);
	}
	
	@Command(description="Return a Rating")
	public Ratings getRating(@Param(name="Rating Id")long id){
		return bookjarAPI.getRating(id);
	}
	
	@Command(description="Get All Ratings")
	public void getRatings(){
		bookjarAPI.getRatings();
	}
	
	@Command(description = "Get top 6 books")
	public void Top6books(){
		bookjarAPI.calculateAvg();
	}
	
	//Delete Rating only for current User
	@Command(description="Delete a Rating")
	public void deleteRating(@Param(name="Rating Id")long id){
		Users user = bookjarAPI.currentUser.get();
		if(user.id != id) {
			System.out.println("You can not delete another person rating");
		}else {
			bookjarAPI.deleteRating(id);
		}
	}
	
	/*
	...................... Books ........................
	*/
	
	//TreeSet instead of hash map, easier to use. Iterator is for loop using collections
	
	@Command(description="Get a List of all books sorted by there title")
	public void getBooks(){
		TreeSet<Books> sortedBooks = new TreeSet<Books>();
		sortedBooks.addAll(bookjarAPI.getBooks());
		Iterator<Books> iter = sortedBooks.iterator();
		while(iter.hasNext()) {
			Books u = iter.next();
			System.out.println(u.title + ", Released on " + u.year);
		}
	}
	
	
	@Command(description="Get a Books by its ID")
	public Books getBook(@Param(name="Books Id") Long id){
		return bookjarAPI.getBook(id);
	}
	
	

	
	
	
	
	
}
package controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import com.google.common.base.Optional;

import asg.cliche.Command;
import asg.cliche.Param;
import models.Books;
import models.Ratings;
import models.Users;


public class Admin {
	private String name;
	private BookJarAPI bookjarAPI;

	public Admin(BookJarAPI bookjarAPI, String name) {
		this.bookjarAPI = bookjarAPI;
		this.name = name;
	}
	
	//returns name
	public String getName() {
		return name;
	}
    //Sets name
	public void setName(String name) {
		this.name = name;
	}
	
	// Logout User
	@Command(description="Logout")
	public void LogOut() throws Exception{
		bookjarAPI.logout(); 
		System.out.println("Logging out");
	}
  


	

	@Command(description = "Create a new User", abbrev="addU")
	public void createUser(@Param(name = "first name") String firstName, @Param(name = "last name") String lastName,
	@Param(name = "age") int age, @Param(name = "gender") String gender, @Param(name = "occupation") String occupation) {
		bookjarAPI.createUser(firstName, lastName, age, gender, occupation);
	}

	@Command(description = "Get a Users detail", abbrev="getU")
	public void getUser(@Param(name = "name") String firstName) {
		Users user = bookjarAPI.getUserByName(firstName);
		System.out.println(user);
	}

	@Command(description = "Delete a User", abbrev="duN")
	public void deleteUser(@Param(name = "name") String name) {
		Optional<Users> user = Optional.fromNullable(bookjarAPI.getUserByName(name));
		if (user.isPresent()) {
			bookjarAPI.deleteUser(user.get().id);
		}
	}

	@Command(description = "Add a book", abbrev="addB")
	public void addBook(@Param(name = "id") long id, @Param(name = "title") String title,
	@Param(name = "year") String year, @Param(name = "url") String url) {
		bookjarAPI.addBooks(title, year, url);
	}
	

	@Command(description = "Add ratings to a book" , abbrev="addR")
	public void addRating(@Param(name = "book-id") Long id, @Param(name = "userId") Long userId,
	@Param(name = "bookId") Long bookId, @Param(name = "rating") int rating ) {
		Optional<Books> book = Optional.fromNullable(bookjarAPI.getBook(id));
		if (book.isPresent()) {
			bookjarAPI.addRatings(userId, bookId, rating);
		}
	}
	
	 /*
	 ..................................... Users Commands ..............................................
	 */
	
	@Command(description = "Get all users sorted by there Name")
	public void getAllUsers() {
		TreeSet<Users> sortedUsers = new TreeSet<Users>();
		sortedUsers.addAll(bookjarAPI.getUsers());
		Iterator<Users> iter = sortedUsers.iterator();
		while(iter.hasNext()) {
			Users u = iter.next();
			System.out.println(u.firstName + " " + u.lastName);
		}
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
	
	@Command(description = "Get top 6 books", abbrev="top6")
	public void Top6Books(){
		bookjarAPI.calculateAvg();
	}
	
	@Command(description = "Get a Users detail", abbrev="guId")
	public void getUser(@Param(name = "name") Long id) {
		Users user = bookjarAPI.getUserById(id);
	    System.out.println(user);
	}
	
	@Command(description="Delete a User", abbrev="duId")
	public void deleteUser(@Param(name="User ID")long id){
		bookjarAPI.deleteUser(id);
	}
	
	
	
	/*
	................................. Rating Commands ..................................................
	 */
	@Command(description = "Add a rating" )
	public void addRating(@Param(name = "userId") Long userId, @Param(name = "bookId") Long bookId, @Param(name = "rating") int rating) {
		 bookjarAPI.addRatings(userId, bookId, rating);
	}
	
	@Command(description="Get User Ratings")
	public Map<Long, Ratings> getUserRating(@Param(name="User ID")long id){
		return bookjarAPI.getUserRating(id);
	}
	
	@Command(description="Get Books Ratings")
	public Map<Long,Ratings> getBookRating(@Param(name="Book Id")long id)
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
	
	@Command(description="Delete a Rating" , abbrev="gau")
	public void deleteRating(@Param(name="Rating Id")long id){
		 Users user = bookjarAPI.currentUser.get();
		if(user.id  != id) {
			System.out.println("You can not delete another person rating");
		}else {
		bookjarAPI.deleteRating(id);
		}
	}
	
	@Command(description="Recommend a book" , abbrev="rec")
	public void recommender(@Param(name="id")Long id) {
		bookjarAPI.recommender(id);
	}
	
	
	
	/*
	  ...................................... Book Commands ..........................................
	 */
	
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
	
	
	@Command(description="Get a Book by its ID")
	public Books getBook(@Param(name="Book Id") Long id){
		return bookjarAPI.getBook(id);
	}
	
	@Command(description="Get book by title")
	public void getBookByTitle(@Param(name="title")String title) {
		ArrayList<Books> books = new ArrayList<Books>();
		books.addAll(bookjarAPI.getBooks());
		for(int i = 0; i < books.size(); i++) {
			if(books.get(i).title.toLowerCase().contains(title.toLowerCase())) {
				System.out.println(books.get(i) );
			}
		}
		
	}
}
		
	
	
	

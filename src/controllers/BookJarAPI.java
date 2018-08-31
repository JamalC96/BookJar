package controllers;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import edu.princeton.cs.introcs.In;



import com.google.common.base.Optional;
import models.Books;
import models.Ratings;
import models.Users;
import utils.Serializer;

public class BookJarAPI implements BookJarInterface {

	private Serializer serializer;
	public Map<Long, Users> usersIndex = new HashMap<>();
	private Map<String, Users> usersName = new HashMap<>();
	public Map<Long, Books> bookIndex = new HashMap<>();
	private Map<Long, Ratings> ratingsIndex = new HashMap<>();
	private Map<String, Books> bookTitle = new HashMap<>();
	public Optional<Users> currentUser;

	/*
	
      .................... Constructors ......................
	 */
	public BookJarAPI() {

	}

	public BookJarAPI(Serializer serializer) {
		this.serializer = serializer;
	}

	/*
	 ..................... StartUp ...........................

	 */
	// Login a user
	public boolean login(Long userId, String lastName) {
		Optional<Users> user = Optional.fromNullable(usersIndex.get(userId));
		if (user.isPresent() && user.get().lastName.equals(lastName)) {
			currentUser = user;
			return true;
		} 
		else 
		{
			return false;
		}

	}

	// Logout a user
	public void logout() {
		Optional<Users> user = currentUser;
		if (user.isPresent()) {
			currentUser = Optional.absent();
			currentUser = null;

		}
	}

	// This method will read in data from the users.dat file and items file and
	// will create new users and books that will be stored
	public void initalLoad() throws IOException {
		String delims = "[|]";
		Scanner scanner = new Scanner(new File("./files/users1.dat"));
		while (scanner.hasNextLine()) {
			String userDetails = scanner.nextLine();
			// parse user details string
			String[] userTokens = userDetails.split(delims);

			if (userTokens.length == 7) {
				createUser(userTokens[1], userTokens[2], Integer.valueOf(userTokens[3]), userTokens[4], userTokens[5]);
			} else {
				scanner.close();
				throw new IOException("Invalid member length: " + userTokens.length);
			}
		}

		scanner = new Scanner(new File("./files/items1.dat"));
		while (scanner.hasNextLine()) {
			String bookDetails = scanner.nextLine();
			// parse user details string
			String[] bookTokens = bookDetails.split(delims);

			if (bookTokens.length == 23) {
				addBooks(bookTokens[1], bookTokens[2], bookTokens[3]);
			} else {
				scanner.close();
				throw new IOException("Invalid member length: " + bookTokens.length);
			}
		}

		scanner = new Scanner(new File("./files/ratings1.dat"));
		while (scanner.hasNextLine()) {
			String userDetails2 = scanner.nextLine();
			// parse user details string
			String[] userTokens2 = userDetails2.split(delims);
			if (userTokens2.length == 4) {
				addRatings(Long.valueOf(userTokens2[0]), Long.valueOf(userTokens2[1]), Integer.valueOf(userTokens2[2]));
			} else {
				scanner.close();
				throw new IOException("Invalid member length: " + userTokens2.length);
			}

		}
		scanner.close();

	}
		
		
	

	
		

	

	@SuppressWarnings("unchecked")
	@Override
	public void load() throws Exception {
		serializer.read();
		usersIndex = (Map<Long, Users>) serializer.pop();
		bookIndex = (Map<Long, Books>) serializer.pop();
		ratingsIndex = (Map<Long, Ratings>) serializer.pop();
	}

	@Override
	public void store() throws Exception {
		serializer.push(ratingsIndex);
		serializer.push(bookIndex);
		serializer.push(usersIndex);
		// serializer.push(usersName);
		serializer.write();
	}

	/*
	  .................User Methods .....................
	 */

	// Return all users in file
	public Collection<Users> getUsers() {
		return usersIndex.values();
	}

	// delete all users
	public void deleteUsers() {
		usersIndex.clear();
		usersName.clear();
	}

	// create a new user using firstname, lastname, age, gender, occupation
	@Override
	public void createUser(String firstName, String lastName, int age, String gender, String occupation) {
		Users user = new Users(firstName, lastName, age, gender, occupation);
		usersIndex.put(user.id, user);
		// return user;
	}
	
	

	// Returns user info when searching for there first name
	@Override
	public Users getUserByName(String name) {
		return usersName.get(name);
	}

	// Returns user by id
	@Override
	public Users getUserById(Long id) {
		return usersIndex.get(id);
	}

	// delete user by their id
	@Override
	public void deleteUser(Long id) {
		usersIndex.remove(id);
	}

	/*
	 
    .............. Book Methods ................
	 */

	// create a book without an id
	public void addBooks(String title, String year, String url) {
		Books book = new Books(title, year, url);
		bookIndex.put(book.id, book);
	}

	// gets a list of all books in file
	@Override
	public Collection<Books> getBooks() {
		return bookIndex.values();
	}

	// gets a book by its id
	public Books getBook(Long id) {
		return bookIndex.get(id);
	}

	public void deleteBook(Long id) {
		bookIndex.remove(id);
	}

	// Get a book by its title details
	@Override
	public Books getBookByTitle(String title) {
		return bookTitle.get(title);
	}

	/*
	........................ Rating Methods ......................
	 */

	@Override
	public void addRatings(Long userID, Long bookID, int rating) {
		Ratings ratings;
		Optional<Users> user = Optional.fromNullable(usersIndex.get(userID));
		Optional<Books> book = Optional.fromNullable(bookIndex.get(bookID));
		if (book.isPresent() && user.isPresent()) {
			ratings = new Ratings(userID, bookID, rating); // add new rating
			user.get().userRatings.put(ratings.id, ratings); // attach user to a rating
			book.get().theBooksRatings.put(ratings.id, ratings); // attach a book to a rating
			ratingsIndex.put(ratings.id, ratings); // add rating to a collection

			book.get().ratingSystem = book.get().ratingSystem + rating;

		}
	}

	public Collection<Ratings> getRatings() {
		return ratingsIndex.values();
	}

	public Ratings getRating(long id) {
		return ratingsIndex.get(id);
	}

	public Map<Long, Ratings> getUserRating(long id) {
		Optional<Users> user = Optional.fromNullable(usersIndex.get(id));
		return user.get().userRatings;
	}

	public Map<Long, Ratings> getBookRating(long id) {
		Optional<Books> book = Optional.fromNullable(bookIndex.get(id));
		return book.get().theBooksRatings;

	}

	public void deleteRating(long id) {
		ratingsIndex.remove(id);
	}

	public void calculateAvg() {
		ArrayList<Books> ratings = new ArrayList<Books>();
		ratings.addAll(getBooks());
		for (int i = 0; i < ratings.size(); i++) {
			if (ratings.get(i).ratingSystem > 0.0) {
				System.out.println(ratings.get(i).title + " " + ratings.get(i).ratingSystem);
			}
		}

	}
    
	public void recommender(long id) {
		Map<Long, Books> books = new HashMap<>();
		books.putAll(bookIndex);
		Optional<Users> user = Optional.fromNullable(usersIndex.get(id));
		Set<Long> list;
		list = user.get().userRatings.keySet();
		for (Long ratings : list) {
			Ratings r1 = ratingsIndex.get(ratings);
			ratings = r1.bookId;
			books.remove(ratings);
		}
		List<Books> userBooks = new ArrayList<Books>(books.values());
		Collections.sort(userBooks, new Books().reversed());
		for (Books book : userBooks) {
			System.out.println(book.title);
		}
		
		
	}
}
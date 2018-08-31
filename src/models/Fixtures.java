package models;

import models.Books;
import models.Ratings;

public class Fixtures {
	
	  public static Users [] users = { 
			new Users("Jamal", "Cunnigham", 22 , "M", "Student","admin"),
			new Users("Derrick", "Johnson", 24, "M", "Student", "default"),
			new Users("Shannon", "Smith", 21, "F", "Student", "default") 
			};

	public static Books [] books = { 
			new Books("Alien Versus Predator Extinction", "17-Jul-2017", "AlienVersusPredatorBooks.com"),
			new Books("Assassin's Creed Origins: Desert Oath", "01-May-2016", "AssassinsCreedBooks.com"), 
			new Books("The Death Cure", "25-Jan-2011", "TheMazeRunerBooks.com") 
			};

	public static Ratings [] rating = { 
			new Ratings(1L, 2L, 3),
			new Ratings(2L, 3L, 4), 
			new Ratings(3L, 4L, 5),
			new Ratings(4L, 5L, 6)
			
	};
}

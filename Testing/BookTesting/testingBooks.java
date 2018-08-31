package BookTesting;

import static org.junit.Assert.*;

import org.junit.Test;

import models.Books;

public class testingBooks {
	
	Books test = new Books("The Death Cure", "25-Jan-2011", "TheMazeRunerBooks.com");
    //Create a book
	@Test
	public void testCreate() {
		assertEquals("The Death Cure", test.title);
		assertEquals("25-Jan-2011", test.year);
		assertEquals("TheMazeRunerBooks.com", test.url);
	}
	
	// test toString method
		@Test
		public void testToString() 
		{
			assertEquals("Books{" + test.id + ", The Death Cure, 5-Jan-2011, TheMazeRunerBooks.com, [] }", test.toString());
		}

}

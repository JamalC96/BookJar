package RatingTesting;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static models.Fixtures.rating;

public class testingRatings {

	@Test
	public void testCreate() 
	{
		assertEquals(1, 1L, rating[0].userId);
		assertEquals(1, 2L, rating[0].bookId);
		assertEquals(3, rating[0].rating);
	}
	
	@Test
	public void testIds() 
	{
		assertNotEquals(rating[0].id, rating[1].id);
	}
	
	@Test
	public void testToString() {
		assertEquals("Ratings{"+rating[0].id +", 1, 2, 3}", rating[0].toString());
	}

}
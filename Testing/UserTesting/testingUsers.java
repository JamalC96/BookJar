package UserTesting;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.util.HashSet;
import java.util.Set;
import models.Users;
import static models.Fixtures.users;

public class testingUsers {
	
	Users jamal = new Users("Jamal", "Cunningham", 22, "M", "Student");

	
	@Test
	public void testAdd() {
		assertEquals("Jamal", jamal.firstName);
		assertEquals("Cunningham", jamal.lastName);
		assertEquals(22, jamal.age);
		assertEquals("M", jamal.gender);
		assertEquals("Student", jamal.occupation);
	}
	
	
	@Test
	public void testEquals() {
		Users jamal = new Users("Jamal", "Cunningham", 22, "M", "Student");
		Users derrick = new Users("Derrick", "Johnson", 24, "M", "Student");
		assertEquals(jamal, jamal);
		assertEquals(derrick, derrick);
		assertNotEquals(jamal, derrick);
	}
	
	@Test
	  public void IdsTest()
	  {
	    Set<Long> ids = new HashSet<>();
	    for (Users user : users)
	    {
	      ids.add(user.id);
	    }
	    assertEquals (users.length, ids.size());
	  }
	

@Test
public void testToString() {
	assertEquals("Users{" + jamal.id + ", Jamal, Cunningham, 22, M, Student}", jamal.toString());
}

}
	
	
package models;


import java.util.HashMap;
import java.util.Map;
import com.google.common.base.Objects;
import static com.google.common.base.MoreObjects.toStringHelper;

public class Users implements Comparable<Users> {
	static Long counter = 01L;
	public Long id;
	public String firstName;
	public String lastName;
	public int age;
	public String gender;
	public String occupation;
	public String role;

	public Map<Long, Ratings> userRatings = new HashMap<>();
	
	public Users() {
		
	}

	public Users(String firstName, String lastName, int age, String gender, String occupation) {
		this(firstName, lastName, age, gender, occupation, "default");
	}

	public Users(String firstName, String lastName, int age, String gender, String occupation, String role) {
		this.id = counter++;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.occupation = occupation;
		this.role=role;
	}

	@Override
	public String toString() {
		return toStringHelper(this).addValue(id)
				.addValue(firstName)
				.addValue(lastName)
				.addValue(age)
				.addValue(gender)
				.addValue(occupation).toString() ;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.firstName, this.lastName, this.age, this.gender, this.occupation);
	}

	//hello
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Users) {
			final Users other = (Users) obj;
			return Objects.equal(firstName, other.firstName) 
					&& Objects.equal(lastName, other.lastName)
					&& Objects.equal(age, other.age) 
					&& Objects.equal(gender, other.gender)
					&& Objects.equal(occupation, other.occupation);
		} 
		else 
		{
			return false;
		}
	}

	@Override
	//return
	public int compareTo(Users user) 
	{
		return this.firstName.compareTo(user.firstName);
	}

}
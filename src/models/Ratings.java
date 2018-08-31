package models;

import com.google.common.base.Objects;
import static com.google.common.base.MoreObjects.toStringHelper;


public class Ratings {
	static Long counter = 0l;

	public Long id;
	public Long userId = (long) 0;
	public Long bookId = (long) 0;
	public int rating = 0;

	public Ratings() 
	{
	}

	public Ratings(Long userID, Long bookId, int rating) {
		this.id = counter++;
		this.userId = userID;
		this.bookId = bookId;
		this.rating = rating;
	}

	@Override
	public String toString() {
		return toStringHelper(this).addValue(id).addValue(userId).addValue(bookId).addValue(rating).toString();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.userId, this.bookId, this.rating);
	}
	
	@Override
	  public boolean equals(final Object obj) {
	    if (obj instanceof Ratings){
	      final Ratings other = (Ratings) obj;
	      return Objects.equal(userId, other.userId) 
	          && Objects.equal(bookId, other.bookId)
	          && Objects.equal(rating, other.rating);
	    }
	    else
	    {
	      return false;
	    }
	  }
}
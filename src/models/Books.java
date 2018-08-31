package models;


import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import com.google.common.base.Objects;
import static com.google.common.base.MoreObjects.toStringHelper;

public class Books implements Comparable<Books>, Comparator<Books>{
	
	
	static Long counter = 0l;

	public Long id;
	public String title;
	public String year;
	public String url;
	public double ratingSystem = 0;
	
	public Map<Long, Ratings> theBooksRatings = new HashMap<>();

	public Books() 
	{
	}

	public Books (String title, String year, String url)
	  {
	    this.id     = counter++;
	    this.title  = title;
	    this.year 	= year;
	    this.url 	= url;
	  }

	@Override
	public String toString() {
		return toStringHelper(this)
				.addValue(id)
				.addValue(title)
				.addValue(year)
				.addValue(url)
				.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.title, this.year, this.url);
	}
	
	@Override
	  public boolean equals(final Object obj){
	    if (obj instanceof Books)
	    {
	      final Books other = (Books) obj;
	      return Objects.equal(title, other.title) 
	          && Objects.equal(year,  other.year)
	          && Objects.equal(url,  other.url);    
	    }
	    else
	    {
	      return false;
	    }
	  }
	
	public int compareTo(Books book)
	{
		return this.title.compareTo(book.title);
	}
	
	public int compare(Books s1, Books s2)
	{
		return (int) (s1.ratingSystem - s2.ratingSystem);
	}
	
	
}
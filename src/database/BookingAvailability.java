package database;

/**
 * This class create an object for <time, availability> for a specific employee on the chosen date.
 * @author ranlu
 *
 */
public class BookingAvailability {
	private String time;
	private String availability;

	public BookingAvailability(){}

	public String getTime(){
		return time;
	}
	public void setTime(String time){
		this.time = time;
	}
	public String getAvailability(){
		return availability;
	}
	public void setAvailability(String availability){
		this.availability = availability;
	}
}

package database;


/**
 * Each business owner can have many employees, each employee has separate working days/time in a week.
 * @author ranlu
 *
 */
public class Employee {

	private String firstname;
	private String lastname;
	private String email;
	private String contact;
	private String day;
	private String time;

	//create constructor to call methods from Employee to other class
	public Employee(){}

	public String getFirstname(){
		return firstname;
	}

	public void setFirstname(String firstname){
		this.firstname = firstname;
	}

	public String getLastname(){
		return lastname;
	}

	public void setLastname(String lastname){
		this.lastname = lastname;
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getContact(){
		return contact;
	}

	public void setContact(String contact){
		this.contact = contact;
	}

	public String getDay(){
		return day;
	}

	public void setDay(String day){
		this.day = day;
	}
	public String getTime(){
		return time;
	}

	public void setTime(String time){
		this.time = time;
	}
}

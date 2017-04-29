package database;

public class Booking {
	private String date;
	private String time;
	private String employee;
	private String service;
	private String customer;
	private String contact;

	public Booking(){}

	public void setDate(String date){
		this.date = date;
	}
	public void setTime(String time){
		this.time = time;

	}
	public void setEmployee(String employee){
		this.employee = employee;
	}
	public void setService(String service){
		this.service = service;
	}
	public void setCustomer(String customer){
		this.customer = customer;
	}
	public void setContact(String contact){
		this.contact = contact;
	}
}

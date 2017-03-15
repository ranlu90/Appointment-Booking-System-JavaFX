package model;

import java.util.Scanner;
import user.BusinessOwner;
import user.Customer;

public class ClientModel {

	private BusinessOwner businessOwner;
	private Customer customer;

	public ClientModel() {}

    /**
     * Pass the business owner to the model.
     * @param businessOwner The UserManager being passed.
     */
    public void initBusinessOwner(BusinessOwner businessOwner)
    {
        this.businessOwner = businessOwner;
    }
    /**
     * Pass the customer to the model.
     * @param customer The UserManager being passed.
     */
    public void initCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public void menu(){
    	Scanner sc = new Scanner(System.in);
    	int i = sc.nextInt();
		if(i == 1){
			businessLogin();
		}
		else if(i == 2){
			customerLogin();
		}
		else if(i == 3){
			//register();
		}
		else{
			System.out.println("Invaild selection!");
			return;
		}
    }
}

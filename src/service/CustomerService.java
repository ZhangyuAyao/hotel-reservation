package service;

import model.Customer;

import java.util.*;

public class CustomerService {
    //state
    public static List<Customer> customersList = new LinkedList<Customer>();

    //method
    public static void addCustomer(String email, String firstName, String lastName){
        customersList.add(new Customer(firstName, lastName, email));
    }

    public static Customer getCustomer(String customerEmail){
        for(Customer customer: customersList){
            if(customer.getEmail().equals(customerEmail)) {
                return customer;
            }
        }
        return null;
    }

    public static Collection<Customer> getAllCustomers(){
        return customersList;
    }


}



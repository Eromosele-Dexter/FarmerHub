package mockDB;

import java.util.ArrayList;
import java.util.List;

import models.Machine;
import models.Produce;
import models.User;

// This is a STUB Database
// User Database
// Produce Listings Database
// Machine Listings Database

public class StubDB {
	
	// List of Users
	private static List<User> usersList = new ArrayList<User>();
	
	// Produce Listings
	private static List<Produce> produceList = new ArrayList<Produce>();
	
	// Machine Listings
	private static List<Machine> machineList = new ArrayList<Machine>();
	
	
	// Getters and Setters for usersList
	public static List<User> getUsersList() {
		return usersList;
	}

	public static void setUsersList(List<User> usersList) {
		StubDB.usersList = usersList;
	}
	
	// method to add one user to users list
	public static void addUser(User u) {
		usersList.add(u);
	}
	
	public static void intializeUserDB() {
		User u1 = new User("John","Smith","john123","pass1");
		User u2 = new User("Alice","Johnson","alice456","pass2");
		User u3 = new User("Bob","Williams","bob789","pass3");
		usersList.add(u1);
		usersList.add(u2);
		usersList.add(u3);
		
	}
	
	
	

}

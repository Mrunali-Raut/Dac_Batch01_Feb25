package org.dac.assignment01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
//        1. Register a User
//        2. List All Users based on City
//        3. Update Password of a User
//        4. Display user information based on User Name

public class JDBCDemo {

	static final String url ="jdbc:mysql://localhost:3306/wbja_b1";
	static final String userName="root";
	static final String password="cdac";
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		

        while(true) {
        	System.out.println(" 1. Register a User\n 2. List All Users based on City\n 3. Update Password of a User\n 4. Display user information based on User Name\n 5.Exit\n Enter choice:");
        	int choice=sc.nextInt();
        	switch(choice) {
        	case 1: {
        		registerUser();
        		break;
        	}
        	case 2: {
        		listUsersByCity();
        		break;
        	}
        	case 3: {
        		updatePassword();
        		break;
        	}
        	case 4: {
        		displayUserByName();
        		break;
        	}
        	case 5:System.exit(0);
        	
        	default:System.out.println("Invalid Input...");
        	}
        }

	}

	private static void registerUser() {
		try(Connection con=DriverManager.getConnection(url,userName,password)){
			sc.nextLine();
			System.out.println("Enter Username:");
			String username =sc.nextLine();
			System.out.println("Enter Password:");
			String pwd =sc.nextLine();
			System.out.println("Enter Name:");
			String name =sc.nextLine();
			System.out.println("Enter Email:");
			String email =sc.nextLine();
			System.out.println("Enter City:");
			String city =sc.nextLine();
			String query = "INSERT INTO Users values (?,?,?,?,?)";
			
			PreparedStatement preStmt =con.prepareStatement(query);
			preStmt.setString(1, username);
			preStmt.setString(2, pwd);
			preStmt.setString(3, name);
			preStmt.setString(4, email);
			preStmt.setString(5, city);
			
			int rs= preStmt.executeUpdate();
		
			System.out.println((rs>0) ? "Query Success" : "Query Fail");
			preStmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	private static void listUsersByCity() {
	    try (Connection con = DriverManager.getConnection(url, userName, password)) {
	    	sc.nextLine();
	        System.out.println("Enter City to list users:");
	        String city = sc.nextLine();
	        
	        String query = "SELECT * FROM Users WHERE City = ?";
	        PreparedStatement preStmt = con.prepareStatement(query);
	        preStmt.setString(1, city);
	        
	        ResultSet rs = preStmt.executeQuery();
	        
	        while (rs.next()) {
	            String username = rs.getString("User");
	            String name = rs.getString("Name");
	            String email = rs.getString("Email");
	            System.out.println("Username: " + username + ", Name: " + name + ", Email: " + email);
	        }
	        
	        rs.close();
	        preStmt.close();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	private static void updatePassword() {
	    try (Connection con = DriverManager.getConnection(url, userName, password)) {
	    	sc.nextLine();
	        System.out.println("Enter Username to update password:");
	        String username = sc.nextLine();
	        System.out.println("Enter new Password:");
	        String newPassword = sc.nextLine();
	        
	        String query = "UPDATE Users SET Password = ? WHERE User = ?";
	        PreparedStatement preStmt = con.prepareStatement(query);
	        preStmt.setString(1, newPassword);
	        preStmt.setString(2, username);
	        
	        int rs = preStmt.executeUpdate();
	        if (rs > 0) {
	            System.out.println("Password updated successfully.");
	        } else {
	            System.out.println("User  not found.");
	        }
	        
	        preStmt.close();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	private static void displayUserByName() {
	    try (Connection con = DriverManager.getConnection(url, userName, password)) {
	    	sc.nextLine();
	        System.out.println("Enter Name to display user:");
	        String name = sc.nextLine();
	        
	        String query = "SELECT * FROM Users WHERE name = ?";
	        PreparedStatement preStmt = con.prepareStatement(query);
	        preStmt.setString(1, name);
	        
	        ResultSet rs = preStmt.executeQuery();
	        
	        if (rs.next()) {
	            String username = rs.getString("User");
	            String email = rs.getString("Email");
	            String city = rs.getString("City");
	            System.out.println("Username: " + username + ", Email: " + email + ", City: " + city);
	        } else {
	            System.out.println("User  not found.");
	        }
	        
	        rs.close();
	        preStmt.close();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	

	


}

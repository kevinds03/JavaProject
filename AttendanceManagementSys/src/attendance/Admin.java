package attendance;

import java.beans.Statement;
import java.sql.*;
import java.util.*;

public class Admin {
	static public void insertData() {
		Scanner in = new Scanner(System.in);
		Scanner intScn = new Scanner(System.in);
		String id = "";
		String name = "";
		String kelas = "";
			
		String temp;
		char yesno;
		
		//untuk sql
		String url = "jdbc:mysql://localhost:3306/attendance";
		String username = "root";
		String password = "";
		
		System.out.println("Data to insert:");
		System.out.println("1. Student data");
		System.out.println("2. Lecturer data");
		System.out.println("3. Course data");
		System.out.println("4. Exit");
		System.out.print(">> ");
		
		int pil = in.nextInt();
		in.nextLine();
		boolean idIn = false;
		boolean nameIn = false;
		boolean classIn = false;
		
		switch(pil) {
		case 1:
			while(!idIn) {
				System.out.print("Enter Student ID: ");
				id = in.nextLine();
				if(id.length() == 8 && id.matches("-?\\d+(\\.\\d+)?")) idIn = true;
				else System.out.println("Student ID must be exactly 8 characters long and contains only numbers [0-9]!\n");
			}
			while(!nameIn) {
				System.out.print("Enter Student name: ");
				name = in.nextLine();
				if(name.length() >= 5 && name.length() <= 100 && name.matches("[a-zA-Z]+")) nameIn = true;
				else System.out.println("Name must be between 5 and 100 characters long, and only contains alphabets only!\n");
			}
			while(!classIn) {
				System.out.print("Enter classroom: ");
				kelas = in.nextLine();
				if(kelas.length() == 3 && Character.isAlphabetic(kelas.charAt(0)) && Character.isDigit(kelas.charAt(1)) && Character.isDigit(kelas.charAt(2))) {
					classIn = true;
				}
				else System.out.println("Classroom must be 3 characters long, and consists of a single character followed by two numbers [ex: A10]!\n");
			}
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				// create connection to database
				Connection connection = DriverManager.getConnection(url, username, password);
				java.sql.Statement statement = connection.createStatement();
				
				statement.executeUpdate("INSERT INTO students(sid, s_name, class, status) VALUES('"+id+"', '"+name+"', '"+kelas+"', 0)");
				
				connection.close();
			}
			catch (Exception e) {
				System.out.println(e);
			}
			break;
			
		case 2:
			while(!idIn) {
				System.out.print("Enter Lecturer ID: ");
				id = in.nextLine();
				if(id.length() == 5 && id.lastIndexOf("LEC") == 0 && Character.isDigit(id.charAt(3)) && Character.isDigit(id.charAt(4))) {
					idIn = true;
				}
				else System.out.println("Lecturer ID must consists of 'LEC' followed by two numbers [ex: LEC01]!\n");

			}
			while(!nameIn) {
				System.out.print("Enter Lecturer name: ");
				name = in.nextLine();
				if(name.length() >= 5 && name.length() <= 100 && name.matches("[a-zA-Z]+")) nameIn = true;
				else System.out.println("Name must be between 5 and 100 characters long, and only contains alphabets only!\n");
			}
			while(!classIn) {
				System.out.print("Enter classroom: ");
				kelas = in.nextLine();
				if(kelas.length() == 3 && Character.isAlphabetic(kelas.charAt(0)) && Character.isDigit(kelas.charAt(1)) && Character.isDigit(kelas.charAt(2))) {
					classIn = true;
				}
				else System.out.println("Classroom must be 3 characters long, and consists of a single character followed by two numbers [ex: A10]!\n");
			}
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				// create connection to database
				Connection connection = DriverManager.getConnection(url, username, password);
				java.sql.Statement statement = connection.createStatement();
				
				statement.executeUpdate("INSERT INTO lecturers(lid, lec_name, class) VALUES('"+id+"', '"+name+"', '"+kelas+"')");;
				
				connection.close();
			}
			catch (Exception e) {
				System.out.println(e);
			}
			break;
			
		case 3:
			while(!classIn) {
				System.out.print("Enter classroom ID: ");
				kelas = in.nextLine();
				if(kelas.length() == 3 && Character.isAlphabetic(kelas.charAt(0)) && Character.isDigit(kelas.charAt(1)) && Character.isDigit(kelas.charAt(2))) {
					classIn = true;
				}
				else System.out.println("Classroom must be 3 characters long, and consists of a single character followed by two numbers [ex: A10]!\n");
			}
			while(!nameIn) {
				System.out.print("Enter course: ");
				name = in.nextLine();
				if(name.length() > 5 && name.length() <= 100) nameIn = true;
				else System.out.println("Course must consists of more than 5 characters and at most 100 characters!\n");
			}
			
			String dateTime = "";
			boolean dateIn = false;
			int year = 0, month = 0, date = 0, hour = 0, min = 0;
			System.out.print("Enter class start year: ");
			year = intScn.nextInt();
			while(!dateIn) {
				System.out.print("Enter class start month: ");
				month = intScn.nextInt();
				if(month >= 1 && month <= 12) dateIn = true;
				else System.out.println("Month must be number between 1 to 12, each representing its month in a year [ex: 1 for January, 3 for March, etc]!\n");
			} dateIn = false;
			
			while(!dateIn) {
				System.out.print("Enter class start date: ");
				date = intScn.nextInt();
				if(year%4 == 0 && year%100 != 0) {
					if(month == 2 && date >= 1 && date <= 29) dateIn = true;
				}
				else {
					if(month == 2 && date >= 1 && date <= 28) dateIn = true;
				}
				
				if(month <= 7 && month != 2) {
					if(month%2 == 0 && date >= 1 && date <= 30) dateIn = true;
					if(month%2 == 1 && date >= 1 && date <= 31) dateIn = true;
				}
				else if(month >= 8) {
					if(month%2 == 1 && date >= 1 && date <= 30) dateIn = true;
					if(month%2 == 0 && date >= 1 && date <= 31) dateIn = true;
				}
				
				if(!dateIn) System.out.println("Please enter valid date!\n");
			} dateIn = false;
			
			while(!dateIn) {
				System.out.print("Enter class start hour: ");
				hour = intScn.nextInt();
				if(hour >= 7 && hour <= 18) dateIn = true;
				else System.out.println("Class hour must be between 7 to 18!\n");
			} dateIn = false;
			
			while(!dateIn) {
				System.out.print("Enter class start minute: ");
				min = intScn.nextInt();
				if(min >= 1 && min <= 59) dateIn = true;
				else System.out.println("Please enter valid minute!\n");
			}
			
			String yearString = Integer.toString(year);
			String monString = "";
			String dateString = "";
			String hourString = "";
			String minString = "";
			
			if(month < 10) monString = "0"+Integer.toString(month);
			else monString = Integer.toString(month);
			
			if(date < 10) dateString = "0"+Integer.toString(date);
			else dateString = Integer.toString(date);
			
			if(hour < 10) hourString = "0"+Integer.toString(hour);
			else hourString = Integer.toString(hour);
			
			if(min < 10) minString = "0"+Integer.toString(min);
			else minString = Integer.toString(min);
			
			dateTime = yearString+"-"+monString+"-"+dateString+" "+hourString+":"+minString+":00";
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				// create connection to database
				Connection connection = DriverManager.getConnection(url, username, password);
				java.sql.Statement statement = connection.createStatement();
				
				statement.executeUpdate("INSERT INTO classroom(class, course, time) VALUES('"+kelas+"', '"+name+"', '"+dateTime+"')");
				
				connection.close();
			}
			catch (Exception e) {
				System.out.println(e);
			}
			break;
		case 4:
			break;
		default:
			System.out.println("Please enter number between 1 to 3!");
			Admin.insertData();
		}
	}
	
	static public void updateData() {
		Scanner in = new Scanner(System.in);
		Scanner intScn = new Scanner(System.in);
		String id = "";
		String name = "";
		String kelas = "";
			
		String temp;
		char yesno;
		
		//untuk sql
		String url = "jdbc:mysql://localhost:3306/attendance";
		String username = "root";
		String password = "";
		
		System.out.println("Enter Data to Update:");
		System.out.println("1. Student data");
		System.out.println("2. Lecturer data");
		System.out.println("3. Course data");
		System.out.println("4. Exit");
		System.out.print(">> ");
		
		int pil = in.nextInt();
		in.nextLine();
		
		boolean idIn = false;
		boolean nameIn = false;
		boolean classIn = false;
		
		switch(pil) {
		case 1:
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				// create connection to database
				Connection connection = DriverManager.getConnection(url, username, password);
				java.sql.Statement statement = connection.createStatement();
				ResultSet rs;
				
				//konfirmasi
				do {
					yesno = 'n';
					System.out.print("Enter student ID: ");
					temp = in.nextLine();
					
					rs = statement.executeQuery(""
							+ "SELECT * FROM students WHERE sid = '"+temp+"'");
					
					if(rs.next()) {
						System.out.println("Student details:");
						System.out.printf("ID    : %10s\n", rs.getString(1));
						System.out.printf("Name  : %15s\n", rs.getString(2));
						System.out.printf("Class : %15s\n", rs.getString(3));
						
						System.out.print("Proceed to update data [y/n]? ");
						yesno = in.nextLine().charAt(0);
					}
					
				}while(yesno == 'n');
				
				while(!idIn) {
					System.out.print("Enter new ID: ");
					id = in.nextLine();
					if(id.length() == 8 && id.matches("-?\\d+(\\.\\d+)?")) idIn = true;
					else System.out.println("Student ID must be exactly 8 characters long and contains only numbers [0-9]!\n");
				}
				while(!nameIn) {
					System.out.print("Enter new name: ");
					name = in.nextLine();
					if(name.length() >= 5 && name.length() <= 100 && name.matches("[a-zA-Z]+")) nameIn = true;
					else System.out.println("Name must be between 5 and 100 characters long, and only contains alphabets only!\n");
				}
				while(!classIn) {
					System.out.print("Enter new classroom: ");
					kelas = in.nextLine();
					if(kelas.length() == 3 && Character.isAlphabetic(kelas.charAt(0)) && Character.isDigit(kelas.charAt(1)) && Character.isDigit(kelas.charAt(2))) {
						classIn = true;
					}
					else System.out.println("Classroom must be 3 characters long, and consists of a single character followed by two numbers [ex: A10]!\n");
				}
				
				statement.executeUpdate("UPDATE students SET sid = '"+id+"', s_name = '"+name+"', class = '"+kelas+"' WHERE sid = '"+temp+"'");
				
				connection.close();
			}
			catch (Exception e) {
				System.out.println(e);
			}
			break;
			
		case 2:
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				// create connection to database
				Connection connection = DriverManager.getConnection(url, username, password);
				java.sql.Statement statement = connection.createStatement();
				ResultSet rs;
				
				//konfirmasi
				do {	
					yesno = 'n';
					System.out.print("Enter Lecturer ID: ");
					temp = in.nextLine();
					
					rs = statement.executeQuery(""
							+ "SELECT * FROM lecturers WHERE lid = '"+temp+"'");
					
					if(rs.next()) {
						System.out.println("Lecturer details:");
						System.out.printf("ID   : %10s\n", rs.getString(1));
						System.out.printf("Name : %15s\n", rs.getString(2));
					
						System.out.print("Proceed to update data [y/n]? ");
						yesno = in.nextLine().charAt(0);
					}
					
				}while(yesno == 'n');
				
				while(!idIn) {
					System.out.print("Enter new ID: ");
					id = in.nextLine();
					if(id.length() == 5 && id.lastIndexOf("LEC") == 0 && Character.isDigit(id.charAt(3)) && Character.isDigit(id.charAt(4))) {
						idIn = true;
					}
					else System.out.println("Lecturer ID must consists of 'LEC' followed by two numbers [ex: LEC01]!\n");

				}
				
				while(!nameIn) {
					System.out.print("Enter new name: ");
					name = in.nextLine();
					if(name.length() >= 5 && name.length() <= 100 && name.matches("[a-zA-Z]+")) nameIn = true;
					else System.out.println("Name must be between 5 and 100 characters long, and only contains alphabets only!\n");
				}
				
				while(!classIn) {
					System.out.print("Enter new classroom: ");
					kelas = in.nextLine();
					if(kelas.length() == 3 && Character.isAlphabetic(kelas.charAt(0)) && Character.isDigit(kelas.charAt(1)) && Character.isDigit(kelas.charAt(2))) {
						classIn = true;
					}
					else System.out.println("Classroom must be 3 characters long, and consists of a single character followed by two numbers [ex: A10]!\n");
				}
				
				statement.executeUpdate("UPDATE lecturers SET lid = '"+id+"', lec_name = '"+name+"', class = '"+kelas+"' WHERE lid = '"+temp+"'");
				
				connection.close();
			}
			catch (Exception e) {
				System.out.println(e);
			}
			break;
			
		case 3:
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				// create connection to database
				Connection connection = DriverManager.getConnection(url, username, password);
				java.sql.Statement statement = connection.createStatement();
				ResultSet rs;
				
				//konfirmasi
				do {	
					yesno = 'n';
					System.out.print("Enter class ID: ");
					temp = in.nextLine();
					
					rs = statement.executeQuery(""
							+ "SELECT * FROM classroom WHERE class = '"+temp+"'");
					
					if(rs.next()) {
						System.out.println("Class details:");
						System.out.printf("Class       : %2s\n", rs.getString(1));
						System.out.printf("Course      : %5s\n", rs.getString(3));
						System.out.printf("Date, Time  : %5s\n\n", rs.getString(2));
					
						System.out.print("Proceed to update data [y/n]? ");
						yesno = in.nextLine().charAt(0);
					}
					
				}while(yesno == 'n');
				
				while(!classIn) {
					System.out.print("Enter classroom ID: ");
					kelas = in.nextLine();
					if(kelas.length() == 3 && Character.isAlphabetic(kelas.charAt(0)) && Character.isDigit(kelas.charAt(1)) && Character.isDigit(kelas.charAt(2))) {
						classIn = true;
					}
					else System.out.println("Classroom must be 3 characters long, and consists of a single character followed by two numbers [ex: A10]!\n");
				}
				while(!nameIn) {
					System.out.print("Enter course: ");
					name = in.nextLine();
					if(name.length() > 5 && name.length() <= 100) nameIn = true;
					else System.out.println("Course must consists of more than 5 characters and at most 100 characters!\n");
				}
				
				String dateTime = "";
				boolean dateIn = false;
				int year = 0, month = 0, date = 0, hour = 0, min = 0;
				System.out.print("Enter class start year: ");
				year = intScn.nextInt();
				while(!dateIn) {
					System.out.print("Enter class start month: ");
					month = intScn.nextInt();
					if(month >= 1 && month <= 12) dateIn = true;
					else System.out.println("Month must be number between 1 to 12, each representing its month in a year [ex: 1 for January, 3 for March, etc]!\n");
				} dateIn = false;
				
				while(!dateIn) {
					System.out.print("Enter class start date: ");
					date = intScn.nextInt();
					if(year%4 == 0 && year%100 != 0) {
						if(month == 2 && date >= 1 && date <= 29) dateIn = true;
					}
					else {
						if(month == 2 && date >= 1 && date <= 28) dateIn = true;
					}
					
					if(month <= 7 && month != 2) {
						if(month%2 == 0 && date >= 1 && date <= 30) dateIn = true;
						if(month%2 == 1 && date >= 1 && date <= 31) dateIn = true;
					}
					else if(month >= 8) {
						if(month%2 == 1 && date >= 1 && date <= 30) dateIn = true;
						if(month%2 == 0 && date >= 1 && date <= 31) dateIn = true;
					}
					
					if(!dateIn) System.out.println("Please enter valid date!\n");
				} dateIn = false;
				
				while(!dateIn) {
					System.out.print("Enter class start hour: ");
					hour = intScn.nextInt();
					if(hour >= 7 && hour <= 18) dateIn = true;
					else System.out.println("Class hour must be between 7 to 18!\n");
				} dateIn = false;
				
				while(!dateIn) {
					System.out.print("Enter class start minute: ");
					min = intScn.nextInt();
					if(min >= 1 && min <= 59) dateIn = true;
					else System.out.println("Please enter valid minute!\n");
				}
				
				String yearString = Integer.toString(year);
				String monString = "";
				String dateString = "";
				String hourString = "";
				String minString = "";
				
				if(month < 10) monString = "0"+Integer.toString(month);
				else monString = Integer.toString(month);
				
				if(date < 10) dateString = "0"+Integer.toString(date);
				else dateString = Integer.toString(date);
				
				if(hour < 10) hourString = "0"+Integer.toString(hour);
				else hourString = Integer.toString(hour);
				
				if(min < 10) minString = "0"+Integer.toString(min);
				else minString = Integer.toString(min);
				
				dateTime = yearString+"-"+monString+"-"+dateString+" "+hourString+":"+minString+":00";
				
				statement.executeUpdate("UPDATE classroom SET class = '"+kelas+"', course = '"+name+"', time = '"+dateTime+"' WHERE class = '"+temp+"'");
				
				connection.close();
			}
			catch (Exception e) {
				System.out.println(e);
			}
			break;
		case 4:
			break;
		default:
			System.out.println("Please enter number between 1 to 3!");
			Admin.updateData();
		}
	}
	
	static public void removeData() {
		Scanner in = new Scanner(System.in);
		String id;
		String name;
		String kelas;
			
		String temp;
		char yesno;
		String date;
		
		//untuk sql
		String url = "jdbc:mysql://localhost:3306/attendance";
		String username = "root";
		String password = "";
		
		System.out.println("Data to delete:");
		System.out.println("1. Student data");
		System.out.println("2. Lecturer data");
		System.out.println("3. Course data");
		System.out.println("4. Exit");
		System.out.print(">> ");
		
		int pil = in.nextInt();
		in.nextLine();
		
		switch(pil) {
		case 1:
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				// create connection to database
				Connection connection = DriverManager.getConnection(url, username, password);
				java.sql.Statement statement = connection.createStatement();
				ResultSet rs;
				
				//konfirmasi
				do {	
					yesno = 'n';
					System.out.print("Enter Student ID to delete: ");
					temp = in.nextLine();
					
					rs = statement.executeQuery(""
							+ "SELECT * FROM students WHERE sid = '"+temp+"'");
					
					if(rs.next()) {
						System.out.println("Student details:");
						System.out.printf("ID   : %10s\n", rs.getString(1));
						System.out.printf("Name : %15s\n", rs.getString(2));
					
						System.out.print("Proceed to delete data [y/n]? ");
						yesno = in.nextLine().charAt(0);
					}
					
				}while(yesno == 'n');
				
				// remove
				statement.executeUpdate("DELETE FROM students WHERE sid = '"+temp+"'");
				
				connection.close();
			}
			catch (Exception e) {
				System.out.println(e);
			}
			break;
			
		case 2:
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				// create connection to database
				Connection connection = DriverManager.getConnection(url, username, password);
				java.sql.Statement statement = connection.createStatement();
				ResultSet rs;
				
				//konfirmasi
				do {	
					yesno = 'n';
					System.out.print("Enter Lecturer ID to delete: ");
					temp = in.nextLine();
					
					rs = statement.executeQuery(""
							+ "SELECT * FROM lecturers WHERE lid = '"+temp+"'");
					
					if(rs.next()) {
						System.out.println("Lecturer details:");
						System.out.printf("ID   : %10s\n", rs.getString(1));
						System.out.printf("Name : %15s\n", rs.getString(2));
					
						System.out.print("Proceed to delete data [y/n]? ");
						yesno = in.nextLine().charAt(0);
					}
					
				}while(yesno == 'n');
				
				// remove
				statement.executeUpdate("DELETE FROM lecturers WHERE lid = '"+temp+"'");
				
				connection.close();
			}
			catch (Exception e) {
				System.out.println(e);
			}
			break;
			
		case 3:
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				// create connection to database
				Connection connection = DriverManager.getConnection(url, username, password);
				java.sql.Statement statement = connection.createStatement();
				ResultSet rs;
				
				//konfirmasi
				do {	
					yesno = 'n';
					System.out.print("Enter class ID to delete: ");
					temp = in.nextLine();
					
					rs = statement.executeQuery(""
							+ "SELECT * FROM classroom WHERE class = '"+temp+"'");
					
					if(rs.next()) {
						System.out.println("Class details:");
						System.out.printf("Class       : %2s\n", rs.getString(1));
						System.out.printf("Course      : %5s\n", rs.getString(3));
						System.out.printf("Date, Time  : %5s\n\n", rs.getString(2));
					
						System.out.print("Proceed to delete data [y/n]? ");
						yesno = in.nextLine().charAt(0);
					}
					
				}while(yesno == 'n');
				
				// remove
				statement.executeUpdate("DELETE FROM classroom WHERE class = '"+temp+"'");
				
				connection.close();
			}
			catch (Exception e) {
				System.out.println(e);
			}
			break;
		case 4:
			break;
		default:
			System.out.println("Please enter number between 1 to 3!");
			Admin.removeData();
		}
	}

}
package attendance;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;
import java.util.jar.Attributes.Name;
import java.util.zip.CRC32;

import javax.naming.spi.DirStateFactory.Result;

public class Main {
	public static void main(String[] args) {
		boolean runProgram = true;
		while(runProgram) {
			Scanner strScanner = new Scanner(System.in);
			Scanner intScanner = new Scanner(System.in);
			ArrayList<Student> Students = new ArrayList<Student>();
			ArrayList<Lecturer> Lecturers = new ArrayList<Lecturer>();
			ArrayList<Classroom> Classes = new ArrayList<Classroom>();
			
			// set up connection to database
			String url = "jdbc:mysql://localhost:3306/attendance";
			String username = "root";
			String password = "";
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				// create connection to database
				Connection connection = DriverManager.getConnection(url, username, password);
				
				java.sql.Statement statement = connection.createStatement();
				
				// get all lecturers data
				ResultSet rs = statement.executeQuery("select * from lecturers");
				while(rs.next()) {
					String lid = rs.getString(1);
					String lName = rs.getString(2);
					String cr = rs.getString(3);
					Lecturers.add(new Lecturer(lName, lid, cr, true));
				}
				
				// get all students data
				rs = statement.executeQuery("select * from students");
				while(rs.next()) {
					String sid = rs.getString(1);
					String sName = rs.getString(2);
					String cr = rs.getString(3);
					Boolean status = rs.getBoolean(4);
					Students.add(new Student(sName, sid, cr, status));
				}
				
				// get all classroom data
				rs = statement.executeQuery("SELECT DATE_FORMAT(time, '%a, %d %M %Y'), DATE_FORMAT(time, '%H:%i'), course, class FROM classroom");
				while(rs.next()) {
					String date = rs.getString(1);
					String time = rs.getString(2);
					String course = rs.getString(3);
					String cr = rs.getString(4);
					Classes.add(new Classroom(cr, course, time, date));
				}
				
				connection.close();
			}
			catch (Exception e) {
				System.out.println(e);
			}
			
			String user = new String();
			do {
				System.out.print("Enter as [Lecturer | Admin]: ");
				user = strScanner.nextLine();
			}while(!user.equals("Lecturer") && !user.equals("Admin"));
			
			// enter as lecturer
			if(user.equals("Lecturer")) {
				String id = "";
				String cr = "";
				do {
					System.out.print("\nEnter ID: ");
					id = strScanner.nextLine();
					cr = checkID(id, Lecturers);
				} while(cr.equals(""));
				
				String pass = "";
				do {
					System.out.println("Enter password [length >= 8 chars]: ");
					System.out.print(">> ");
					pass = strScanner.nextLine();
					if(pass.length() < 8) System.out.println("Password must be at least 8 characters long!\n");
				} while(pass.length() < 8);
				
				Classroom cDet = classDet(Classes, cr);
				ArrayList<Student> cStudents = getStudents(Students, cr);
				// overloading
				print(cDet);
				print(cStudents);
				
				attendanceMenu(cStudents, pass);
				
				checkAttendanceMenu(cStudents);
			}
			// enter as admin
			else {
				int choose = 0;
				while(choose < 1 || choose > 4) {
					System.out.println("\n1. Insert new data");
					System.out.println("2. Update data");
					System.out.println("3. Delete data");
					System.out.println("4. Exit");
					System.out.print(">> ");
					choose = intScanner.nextInt();
				}
				switch(choose) {
				case 1:
					Admin.insertData();
					break;
				case 2:
					Admin.updateData();
					break;
				case 3:
					Admin.removeData();
					break;
				}
			}
		}
	}
	
	static ArrayList<Student> getStudents(ArrayList<Student> Students, String cr) {
		ArrayList<Student> temp = new ArrayList<Student>();
		Iterator<Student> i = Students.iterator();
		while(i.hasNext()) {
			Student s = i.next();
			if(s.getClassName().equals(cr)) temp.add(s);
		}
		return temp;
	}
	
	static Classroom classDet(ArrayList<Classroom> cArr, String cr) {
		Iterator<Classroom> i = cArr.iterator();
		while(i.hasNext()) {
			Classroom c = i.next();
			if(c.getClassName().equals(cr)) return c;
		}
		return classDet(cArr, cr);
	}
	
	// check lecturer ID
	static String checkID(String id, ArrayList<Lecturer> Lecturers) {
		Iterator<Lecturer> i = Lecturers.iterator();
		while(i.hasNext()) {
			Lecturer l = i.next();
			if(l.getID().equals(id)) {
				//clearConsole();
				System.out.println("\nWelcome "+l.getName());
				return l.getClassName();
			}
		}
		System.out.println("ID not found!\n");
		return "";
	}
	
	// overloading
	static void print(Classroom c) {
		System.out.println("\nClass details:");
		System.out.printf("Class       : %2s\n", c.getClassName());
		System.out.printf("Course      : %5s\n", c.getCourse());
		System.out.printf("Date, Time  : %5s, %s\n", c.getDate(), c.getTime());
	}
	
	static void print(ArrayList<Student> Students) {
		System.out.println("\nList of Students:");
		Iterator<Student> i = Students.iterator();
		while(i.hasNext()) {
			Student s = i.next();
			if(s.getStatus())
				System.out.printf("%2s - %-25s%22s\n", s.getID(), s.getName(), "Attend !");
			else
				System.out.printf("%2s - %-25s%20s\n", s.getID(), s.getName(), "Absent");
		}
	}
	
	static void attendanceMenu(ArrayList<Student> Students, String pass) {
		Scanner intScanner = new Scanner(System.in);
		Scanner strScanner = new Scanner(System.in);
		System.out.println("1. Make Attendance");
		System.out.println("2. Start Class");
		System.out.print(">> ");
		int input = intScanner.nextInt();
		switch (input) {
		case 1:
			Student.makeAttendance(Students);
			attendanceMenu(Students, pass);
			break;
		case 2:
			System.out.print("Enter Lecturer's password: ");
			String passMatch = strScanner.nextLine();
			if(passMatch.equals(pass)) break;
			System.out.println("Password doen't match!");
			attendanceMenu(Students, pass);
			break;
		default:
			attendanceMenu(Students, pass);
			break;
		}
	}
	
	static void checkAttendanceMenu(ArrayList<Student> Students) {
		Scanner scn = new Scanner(System.in);
		Scanner scnStr = new Scanner(System.in);
		print(Students);
		System.out.println("\nCheck Student attendance");
		System.out.println("1. Change Student's status");
		System.out.println("2. Record attendance and exit");
		System.out.print(">> ");
		int menu = scn.nextInt();
		switch(menu) {
		case 1:
			System.out.print("Enter Student ID: ");
			String sid = scnStr.nextLine();
			Lecturer.checkAttendance(Students, sid);
			checkAttendanceMenu(Students);
			break;
		case 2:
			recordAttendance(Students);
			break;
		default:
			checkAttendanceMenu(Students);
			break;
		}
	}
	
	static void recordAttendance(ArrayList<Student> Students) {
		String url = "jdbc:mysql://localhost:3306/attendance";
		String username = "root";
		String password = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url, username, password);
			java.sql.Statement statement = connection.createStatement();
			Iterator<Student> i = Students.iterator();
			while(i.hasNext()) {
				Student s = i.next();
				boolean status = s.getStatus();
				statement.executeUpdate("UPDATE students SET status = "+status+" WHERE sid = '"+s.getID()+"'");
			}
			connection.close();
		}
		
		catch (Exception e) {
			
		}
	}
}
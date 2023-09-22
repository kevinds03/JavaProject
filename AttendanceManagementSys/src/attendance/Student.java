package attendance;

import java.util.*;

public class Student extends Person{
        private String name;
        private String ID;
        private String ClassName;
        private boolean status;

        Student(String name, String ID, String ClassName, boolean status) {
        	super(name, ID, ClassName, status);
            this.name = name;
            this.ID = ID;
            this.ClassName = ClassName;
            this.status = status;
        }
        
        // setter
        public void setName(String name) {
        	this.name = name;
        }
        
        public void setID(String ID) {
        	this.ID = ID;
        }
        
        public void setClassName(String ClassName) {
        	this.ClassName = ClassName;
        }
        
        public void setStatus(boolean status) {
        	this.status = status;
        }
        
        // getter
        public String getName() {
        	return name;
        }
        
        public String getID() {
        	return ID;
        }
        
        public String getClassName() {
        	return ClassName;
        }
        
        public boolean getStatus() {
        	return status;
        }
        
        public static void makeAttendance(ArrayList<Student> Students) {
        	Scanner scn = new Scanner(System.in);
        	System.out.print("Enter ID: ");
        	String temp = scn.nextLine();
        	
        	// check ID
        	Iterator<Student> i = Students.iterator();
        	boolean found = false;
        	while(i.hasNext() && found == false) {
        		Student s = i.next();
        		if(temp.equals(s.getID())) {
        			System.out.println("Welcome "+s.getID()+" - "+s.getName());
        			s.setStatus(true);
        			found = true;
        		}
        	}
        	if(!found) System.out.println("No ID match!");
        }
    }
package attendance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Lecturer extends Person{
        private String name;
        private String ID;
        private String ClassName;
        private boolean status;

        Lecturer(String name, String ID, String ClassName, boolean status) {
            super(name, ID, ClassName, status);
            this.name = name;
            this.ID = ID;
            this.ClassName = ClassName;
            this.status = status;
        }
        
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
        
        public static void checkAttendance(ArrayList<Student> Students, String id) {
        	Scanner scn = new Scanner(System.in);
        	Iterator<Student> i = Students.iterator();
        	boolean found = false;
        	while(i.hasNext() && found == false) {
        		Student s = i.next();
        		if(id.equals(s.getID())) {
        			if(s.getStatus()) System.out.println("Name: "+s.getName()+"\nSID: "+s.getID()+"\nStatus: Attend");
        			else System.out.println("Name: "+s.getName()+"\nSID: "+s.getID()+"\nStatus: Absent");
        			found = true;
        			char yesno = 'a';
            		System.out.println("Change student Attendance Status? [y/n]");
            		yesno = scn.nextLine().charAt(0);
            		switch(yesno) {
            		case 'y':
            			if(s.getStatus()) s.setStatus(false);
            			else s.setStatus(true);
            			return;
            		case 'n':
            			return;
            		default:
            			checkAttendance(Students, id);
            			break;
            		}
        		}
        	}
        	if(!found) {
    			System.out.println("No student with that ID in the class");
    			return;
    		}
        }
    }
package attendance;

public class Classroom{
        private String className;
        private String course;
        private String date;
        private String startTime;

        Classroom(String className, String course, String startTime, String date) {
            this.className = className;
            this.course = course;
            this.startTime = startTime;
            this.date = date;
        }
        
        void setClassName(String className) {
        	this.className = className;
        }
        
        void setCourse(String Course) {
        	this.course = course;
        }
        
        void setStartTime(String startTime) {
        	this.startTime = startTime;
        }
        
        void setDate(String date) {
        	this.date = date;
        }
        
        public String getClassName() {
        	return className;
        }
        
       public String getCourse() {
    	   return course;
       }
       
       public String getTime() {
    	   return startTime;
       }
       
       public String getDate() {
    	   return date;
       }
    }
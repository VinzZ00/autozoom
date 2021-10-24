package autolaunch.autozoom;


public class class_data {

	private String Class_Name;
	private String Date;
	private String Time;
	private String Link;
	
	
	public class_data(String class_Name, String date, String time, String link) {
		super();
		Class_Name = class_Name;
		Date = date;
		Time = time;
		Link = link;
	}


	public String getClass_Name() {
		return Class_Name;
	}


	public void setClass_Name(String class_Name) {
		Class_Name = class_Name;
	}


	public String getDate() {
		return Date;
	}


	public void setDate(String date) {
		Date = date;
	}


	public String getTime() {
		return Time;
	}


	public void setTime(String time) {
		Time = time;
	}


	public String getLink() {
		return Link;
	}


	public void setLink(String link) {
		Link = link;
	}
	
	
	
	
	

}

package in.co.rays.bean;

/**
 * Course JavaBean encapsulates Course attributes.
 * 
 * @author Veena Yadav
 *
 */
public class CourseBean extends BaseBean {
	private String courseName;
	private String description;
	private String duration;

	public CourseBean() {
		// TODO Auto-generated constructor stub
	}

	public String getcourseName() {
		return courseName;
	}

	public void setcourseName(String coursame) {
		courseName = coursame;
	}

	public String getdescription() {
		return description;
	}

	public void setdescription(String descrtion) {
		description = descrtion;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duraon) {
		duration = duraon;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return courseName;
	}
}

 



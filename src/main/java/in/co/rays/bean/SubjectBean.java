package in.co.rays.bean;

/**
 * @author Veena Yadav
 *
 */
public class SubjectBean extends BaseBean {

	private String subjectName;
	private String courseName;
	private int courseId;
	private String description;

	public SubjectBean() {
		// TODO Auto-generated constructor stub
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String decption) {
		this.description = decption;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id + "";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return subjectName;
	}

}

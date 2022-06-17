package in.co.rays.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.CollegeBean;
import in.co.rays.bean.FacultyBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.FacultyModel;

/**
 * @author Veena Yadav
 *
 */
public class FacultyTest {
	/**
	 * Model object to test
	 */
	public static FacultyModel model = new FacultyModel();

	/**
	 * Main method to call test methods.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception

	{
		 testAdd();
		// testDelete();
		//testUpdate();
		// testfindByPk();
		// testsearch();
		// testlist();
		// testFindByEmailId();

	}

	/**
	 * Tests add a College
	 * 
	 * @throws DuplicateRecordException
	 * @throws ParseException
	 */
	public static void testAdd() throws DuplicateRecordException, ParseException {

		try {
			FacultyBean bean = new FacultyBean();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			bean.setFirstName("Ram");
			bean.setLastName("Pal");
			bean.setGender("Male");
			bean.setDOB(sdf.parse("08-08-1984"));
			bean.setQualification("Mba");
			bean.setEmailId("p@gmail.com");
			bean.setMobileNo("8827836494");
			bean.setCollegeId(1);
			bean.setCollegeName("abc");
			bean.setCourseId(1);

			bean.setCourseName("xyz");
			bean.setSubjectId(2);
			bean.setSubjectName("chemistry");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");

			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			System.out.println("Test add succ");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testDelete() {

		try {
			FacultyBean bean = new FacultyBean();
			long pk = 4;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Test Delete succ");
			FacultyBean deletedBean = model.findByPk(pk);
			if (deletedBean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() throws Exception {

		FacultyBean bean = new FacultyBean();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-DD-yyy");
		bean.setFirstName("jay");
		bean.setLastName("AArya");
		bean.setGender("Male");
		bean.setDOB(sdf.parse("18-08-1998"));
		bean.setQualification("Mba");
		bean.setEmailId("ap@gmail.com");
		bean.setMobileNo("8827836494");
		bean.setCollegeId(1);
		bean.setCollegeName("abc");
		bean.setCourseId(1);

		bean.setCourseName("xyz");
		bean.setSubjectId(1);
		bean.setSubjectName("chemistry");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");

		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		bean.setId(1);

		model.update(bean);
		System.out.println("Test Update succ");

	}

	public static void testfindByPk() {
		try {
			FacultyBean bean = new FacultyBean();
			long pk = 1;
			bean = model.findByPk(pk);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getGender());
			System.out.println(bean.getDOB());
			System.out.println(bean.getQualification());
			System.out.println(bean.getEmailId());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getCollegeId());
			System.out.println(bean.getCollegeName());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDateTime());
			System.out.println(bean.getModifiedDateTime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByEmailId() {
		try {
			FacultyBean bean = new FacultyBean();
			bean = model.findByEmail("ap@gmail.com");
			if (bean != null) {
				System.out.println("Test Find By EmailId fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getGender());
			System.out.println(bean.getDOB());
			System.out.println(bean.getQualification());
			System.out.println(bean.getEmailId());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getCollegeId());
			System.out.println(bean.getCollegeName());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDateTime());
			System.out.println(bean.getModifiedDateTime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/*
	 * public static void testFindByPK() { try { FacultyBean bean = new
	 * FacultyBean(); long pk = 1; bean = model.findByPk(pk); if (bean == null) {
	 * System.out.println("Test Find By PK fail"); }
	 * System.out.println(bean.getId()); System.out.println(bean.getFirstName());
	 * System.out.println(bean.getLastName()); System.out.println(bean.getGender());
	 * System.out.println(bean.getDOJ());
	 * System.out.println(bean.getQualification());
	 * System.out.println(bean.getEmailId());
	 * System.out.println(bean.getMobileNo());
	 * System.out.println(bean.getCollegeId());
	 * System.out.println(bean.getCollegeName());
	 * System.out.println(bean.getCourseId());
	 * System.out.println(bean.getCourseName());
	 * System.out.println(bean.getSubjectId());
	 * System.out.println(bean.getSubjectName());
	 * System.out.println(bean.getCreatedBy());
	 * System.out.println(bean.getModifiedBy());
	 * System.out.println(bean.getCreatedDateTime());
	 * System.out.println(bean.getModifiedDateTime());
	 * 
	 * } catch (ApplicationException e) { e.printStackTrace(); } }
	 */
	public static void testsearch() {

		try {
			FacultyBean bean = new FacultyBean();
			List list = new ArrayList();
			bean.setFirstName("Ajay");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (FacultyBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getGender());
				System.out.println(bean.getDOB());
				System.out.println(bean.getQualification());
				System.out.println(bean.getEmailId());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getCollegeId());
				System.out.println(bean.getCollegeName());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getSubjectId());
				System.out.println(bean.getSubjectName());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDateTime());
				System.out.println(bean.getModifiedDateTime());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testlist() {

		try {
			FacultyBean bean = new FacultyBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (FacultyBean) it.next();

				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getGender());
				// System.out.println(bean.getDOB());
				System.out.println(bean.getQualification());
				System.out.println(bean.getEmailId());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getCollegeId());
				System.out.println(bean.getCollegeName());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getSubjectId());
				System.out.println(bean.getSubjectName());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDateTime());
				System.out.println(bean.getModifiedDateTime());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}

package in.co.rays.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.StudentBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.StudentModel;

/**
 * 
 * Student Model Test classes
 *
 */

/**
 * @author Veena Yadav
 *
 */
public class StudentTest {

	/**
	 * Model object to test
	 */

	public static StudentModel model = new StudentModel();

	/**
	 * Main method to call test methods
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// testAdd();
		// testFindByEmailId();
		// testFindByPK();
		// testUpdate();
		// testDelete();
		testSearch();
		// testList();
	}

	/**
	 * -* Tests add a Student
	 * 
	 * @throws Exception
	 */
	public static void testAdd() throws Exception {

		try {

			StudentBean bean = new StudentBean();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

			Date fo = new Date();

			Timestamp ts = new Timestamp(fo.getTime());
			bean.setId(2);
			bean.setCollegeID(111);
			bean.setCollegeName("RGPV");
			bean.setFirstName("Shubham");
			bean.setLastName("Bairagi");
			bean.setDob(sdf.parse("7-12-1999"));
			bean.setMobileNo("7458963254");
			bean.setEmail("shubhambairagi@gmail.com");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDateTime(ts);
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			StudentBean addedbean = model.findByPK(pk);
			if (addedbean == null) {
				System.out.println("Test add fail ");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Test find a Student by EmailId
	 */
	public static void testFindByEmailId() {

		try {
			StudentBean bean = new StudentBean();
			bean = model.findByEmailId("yveena24yadav@gmail.com");

			if (bean != null) {
				System.out.println("Test Find By EmailId fail");
			}
			System.out.print(bean.getId());
			System.out.print(" " + bean.getCollegeID());
			System.out.print(" " + bean.getCollegeName());
			System.out.print(" " + bean.getFirstName());
			System.out.print(" " + bean.getLastName());
			System.out.print(" " + bean.getDob());
			System.out.print(" " + bean.getMobileNo());
			System.out.print(" " + bean.getEmail());
			System.out.print(" " + bean.getCreatedBy());
			System.out.print(" " + bean.getModifiedBy());
			System.out.print(" " + bean.getCreatedDateTime());
			System.out.print(" " + bean.getModifiedDateTime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test find a Student by PK
	 * 
	 * @throws Exception
	 */
	public static void testFindByPK() throws Exception {

		try {
			StudentBean bean = new StudentBean();
			long pk = 6;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test Find By Pk fail");
			}
			System.out.print(bean.getId());
			System.out.print(" " + bean.getCollegeID());
			System.out.print(" " + bean.getCollegeName());
			System.out.print(" " + bean.getFirstName());
			System.out.print(" " + bean.getLastName());
			System.out.print(" " + bean.getDob());
			System.out.print(" " + bean.getMobileNo());
			System.out.print(" " + bean.getEmail());
			System.out.print(" " + bean.getCreatedBy());
			System.out.print(" " + bean.getModifiedBy());
			System.out.print(" " + bean.getCreatedDateTime());
			System.out.print(" " + bean.getModifiedDateTime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests update a Student
	 * 
	 * @throws Exception
	 * @throws ApplicationException
	 */
	public static void testUpdate() throws ApplicationException, Exception {

		try {
			StudentBean bean = new StudentBean();

			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

			bean.setCollegeID(110);
			bean.setCollegeName("RGPV");
			bean.setFirstName("Anayya");
			bean.setLastName("Sharma");
			bean.setDob(sdf.parse("1-11-2001"));
			bean.setMobileNo("9755413654");
			bean.setEmail("shubham@gmail.com");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

			bean.setId(6);
			model.update(bean);
			System.out.println("updated successfully");
			/*
			 * StudentBean updatebean = model.findByPk(1); if
			 * (!"rr".equals(updatebean.getFirstName())) {
			 * System.out.println("Test Update fail"); }
			 */
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test delete a Student
	 */
	public static void testDelete() throws Exception {

		try {
			StudentBean bean = new StudentBean();
			long pk = 7;
			bean.setId(pk);
			model.delete(bean);
			StudentBean deletebean = model.findByPK(pk);
			System.out.println("Success and delete");
			if (deletebean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

		StudentModel model = new StudentModel();

	}

	/**
	 * Test get Search
	 */
	public static void testSearch() {

		try {
			StudentBean bean = new StudentBean();
			List list = new ArrayList();
			bean.setFirstName("Shubham");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (StudentBean) it.next();
				System.out.print(bean.getId());
				System.out.print(" " + bean.getCollegeID());
				System.out.print(" " + bean.getCollegeName());
				System.out.print(" " + bean.getFirstName());
				System.out.print(" " + bean.getLastName());
				// System.out.print(" " + bean.getDob());
				System.out.print(" " + bean.getMobileNo());
				System.out.print(" " + bean.getEmail());
				System.out.print(" " + bean.getCreatedBy());
				System.out.print(" " + bean.getModifiedBy());
				System.out.print(" " + bean.getCreatedDateTime());
				System.out.println(" " + bean.getModifiedDateTime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test get List
	 */
	public static void testList() {

		try {
			StudentBean bean = new StudentBean();
			List list = new ArrayList();
			list = model.list(1, 5);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (StudentBean) it.next();

				System.out.print(bean.getId());
				System.out.print(" " + bean.getCollegeID());
				System.out.print(" " + bean.getCollegeName());
				System.out.print(" " + bean.getFirstName());
				System.out.print(" " + bean.getLastName());
				System.out.print(" " + bean.getDob());
				System.out.print(" " + bean.getMobileNo());
				System.out.print(" " + bean.getEmail());
				System.out.print(" " + bean.getCreatedBy());
				System.out.print(" " + bean.getModifiedBy());
				System.out.print(" " + bean.getCreatedDateTime());
				System.out.println(" " + bean.getModifiedDateTime());

			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}

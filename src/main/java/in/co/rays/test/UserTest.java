package in.co.rays.test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.UserBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.exception.RecordNotFoundException;
import in.co.rays.model.UserModel;

/**
 * User Model Test classes
 */

/**
 * @author Veena Yadav
 *
 */
public class UserTest {

	/**
	 * Model object to test
	 */

	public static UserModel model = new UserModel();

	/**
	 * Main method to call test methods.
	 *
	 * @param args
	 * @throws ParseException
	 * @throws DuplicateRecordException
	 */

	public static void main(String[] args) throws Exception {

		// testAdd();
		// testDelete();
		// testUpdate();
		// testFindByPK();
		// testFindByLogin();
		// testSearch();
		// testList();
		// testAuthenticate();
		// testGetRoles();
		// testchangePassword(); // nhi hui
		// testRegisterUser();
		// testresetPassword(); //nhi hui
		// testforgetPassword(); // nhi hui
	}

	/**
	 * Tests add a User
	 *
	 * @throws ParseException
	 * @throws DuplicateRecordException
	 */
	public static void testAdd() throws ParseException, DuplicateRecordException {

		try {
			UserBean bean = new UserBean();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-DD-yyy");

			// bean.setId(5234L);
			bean.setFirstName("Priya");
			bean.setLastName("Gupta");
			bean.setLogin("aan183@gmail.com");
			bean.setPassword("pass1234");
			bean.setDob(sdf.parse("31-12-1990"));
			bean.setRoleId(5);
			bean.setMobileNo("9568741235");
			bean.setGender("Male");
			bean.setRoleId(3);
			// bean.setLastLogin(null);

			// bean.setConfirmPassword("pass1234");
			long pk = model.add(bean);
			UserBean addedbean = model.findByPK(pk);
			System.out.println("Test add succ");
			/*
			 * if (addedbean == null) { System.out.println("Test add fail"); }
			 */
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests find a User by PK.
	 */
	public static void testFindByPK() {
		try {
			UserBean bean = new UserBean();
			long pk = 2;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getConfirmPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getGender());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests delete a User
	 * 
	 * @throws Exception
	 */
	public static void testDelete() throws Exception {

		try {
			UserBean bean = new UserBean();
			long pk = 6;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Test Delete succ " + bean.getId());
			UserBean deletedbean = model.findByPK(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests update a User
	 * 
	 * @throws Exception
	 */
	public static void testUpdate() throws Exception {

		try {
			UserBean bean = model.findByPK(2);
			bean.setFirstName("Khushi");
//			bean.setLastName("pvt ltd");
			// bean.setLogin("ranjit210@gmail.com");
			// bean.setPassword("658986");

			model.update(bean);

			// UserBean updatedbean = model.findByPK(2);
			/*
			 * if (!"".equals(updatedbean.getLogin())) {
			 * System.out.println("Test Update fail"); }
			 */
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests find a User by Login.
	 */
	public static void testFindByLogin() {
		try {
			UserBean bean = new UserBean();
			bean = model.findByLogin("raman123@gmail.com");
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getConfirmPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getGender());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests get Search
	 */
	public static void testSearch() {

		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();
			bean.setFirstName("V");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getGender());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests get List.
	 */
	public static void testList() {

		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getGender());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDateTime());
				System.out.println(bean.getModifiedDateTime());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests authenticate User.
	 */
	public static void testAuthenticate() {

		try {
			UserBean bean = new UserBean();
			bean.setLogin("dev123@gmail.com");
			bean.setPassword("Dev@1234");
			bean = model.authenticate(bean.getLogin(), bean.getPassword());
			if (bean != null) {
				System.out.println("Successfully login");

			} else {
				System.out.println("Invalid login Id & password");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests get Roles.
	 */
	public static void testGetRoles() {

		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();
			bean.setRoleId(1);
			list = model.getRoles(bean);
			if (list.size() < 0) {
				System.out.println("Test Get Roles fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getGender());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests changepassword
	 *
	 * @throws ParseException
	 */
	public static void testchangePassword() {

		try {
			UserBean bean = model.findByLogin("shivraj123@gmail.com");
			String oldPassword = bean.getPassword();
			// bean.setId(2);

			bean.setPassword("Sharma@123");
			// bean.setConfirmPassword("Bhiya");
			String newPassword = bean.getPassword();
			try {
				model.changePassword(4L, oldPassword, newPassword);
				System.out.println("password has been change successfully");
			} catch (RecordNotFoundException e) {
				e.printStackTrace();
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests add a User register
	 *
	 * @throws ParseException
	 */

	public static void testRegisterUser() throws ParseException {
		try {
			UserBean bean = new UserBean();
			String k = "01/01/1996";
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date m = sdf.parse(k);
			// bean.setId(8L);
			bean.setFirstName("vipin");
			bean.setLastName("kumawat");
			bean.setLogin("navn453@gmail.com");
			bean.setPassword("4444");
			bean.setConfirmPassword("4444");
			bean.setDob(sdf.parse(k));
			bean.setGender("Male");
			bean.setRoleId(2);
			long pk = model.registerUser(bean);
			System.out.println("Successfully register");
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			UserBean registerbean = model.findByPK(pk);
			if (registerbean != null) {
				System.out.println("Test registation fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests resetPassword
	 *
	 * @throws ParseException
	 */
	public static void testresetPassword() {
		UserBean bean = new UserBean();
		try {
			bean = model.findByLogin("aman123@gmail.com");
			if (bean != null) {
				boolean pass = model.resetPassword(bean);
				if (pass = false) {
					System.out.println("Test Update fail");
				}
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests fogetPassword
	 *
	 * @throws ParseException
	 */
	public static void testforgetPassword() {
		try {
			boolean b = model.forgetPassword("aman123@gmail.com");

			System.out.println("Suucess : Test Forget Password Success");

		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}

package in.co.rays.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.RoleBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.RoleModel;
import in.co.rays.util.HTMLUtility;

/**
 * @author Veena Yadav
 *
 */
public class RoleTest {

	static RoleModel model = new RoleModel();

	public static void main(String[] args) throws Exception {
		 testadd();
		// testdelete();
		// testupdate();
		// testfindByPK();
		// testfindByName();
		// testsearch();
		// testList();

	}

	public static void testadd() throws ApplicationException {
		try {
			RoleBean bean = new RoleBean();
			// bean.setId(3);
			bean.setName("Vijay");
			bean.setDescription("Kumar");

			long pk = model.add(bean);
			RoleBean addedbean = model.findByPK(pk);
			if (addedbean == null) {
				System.out.println("Test and Fail");
			}

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testdelete() {
		try {
			RoleBean bean = new RoleBean();

			long pk = 1;
			bean.setId(pk);

			model.delete(bean);
			RoleBean deletedbean = model.findByPK(pk);
			if (deletedbean != null) {
				System.out.println("Del Role fail");
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testupdate() {
		try {
			RoleBean bean = new RoleBean();
			bean.setId(1);
			bean.setName("Ram");
			bean.setDescription("Kumar");

			model.update(bean);
			long pk = 3;
			RoleBean addedbean = model.findByPK(pk);
			if (addedbean != null) {
				System.out.println("Test and Fail");
			}

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testfindByPK() {
		try {
			RoleBean bean = new RoleBean();
			long pk = 1;

			bean = model.findByPK(1);
			if (bean == null) {
				System.out.println("test roll pk fail");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testfindByName() {
		try {
			RoleBean bean = new RoleBean();

			bean = model.findByName("Ram");
			if (bean == null) {
				System.out.println("test roll name fail");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testsearch() {
		try {
			RoleBean bean = new RoleBean();

			List list = new ArrayList();
			// bean.setName("Faculty");
			// bean.setDescription("kumar");

			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}

			Iterator it = list.iterator();

			while (it.hasNext()) {
				bean = (RoleBean) it.next();

				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDateTime());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testList() {
		try {
			RoleBean bean = new RoleBean();

			List list = new ArrayList();

			list = model.list();
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}

			Iterator it = list.iterator();

//			HTMLUtility in = new HTMLUtility();
//		    String pin = in.getList("n"," selectedVal", list);
//		    System.out.println(pin);
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getCreatedDateTime() + "   " + bean.getCreatedBy());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

package in.co.rays.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.MarksheetBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.MarksheetModel;

/**
 * Marksheet Model Test classes
 */

/**
 * @author Veena Yadav
 *
 */
public class MarksheetTest {

	/**
	 * Model object to test
	 */

	public static MarksheetModel model = new MarksheetModel();

	/**
	 * Main method to call test methods.
	 *
	 * @param args
	 */

	public static void main(String[] args) throws Exception {
		// testAdd();
		// testDelete();
		// testUpdate();
		// testFindByRollNo();
		// testFindByPK();
		// testSearch();
		// testList();
		// testMeritList();
	}

	/**
	 * Tests add a Marksheet
	 * 
	 * @throws Exception
	 */

	public static void testAdd() throws Exception {

		try {
			MarksheetBean bean = new MarksheetBean();
			// bean.setId(1L);
			bean.setRollNo("9");
			bean.setPhysics(80);
			bean.setChemistry(70);
			bean.setMaths(90);
			bean.setStudentId(2);
			long pk = model.add(bean);
			System.out.println("Add Marksheet Test");
			MarksheetBean addedbean = model.findByPK(pk);
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests find a marksheet by PK.
	 */
	public static void testFindByPK() {
		try {
			MarksheetBean bean = new MarksheetBean();
			long pk = 2;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getName());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests delete a Marksheet
	 */
	public static void testDelete() {

		try {
			MarksheetBean bean = new MarksheetBean();
			long pk = 2;
			bean.setId(pk);
			model.delete(bean);
			MarksheetBean deletedbean = model.findByPK(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests update a Marksheet
	 * 
	 * @throws Exception
	 */
	public static void testUpdate() throws Exception {

		try {
			MarksheetBean bean = model.findByPK(2);
			bean.setName("Medicaps");
			bean.setChemistry(57);
			bean.setMaths(77);
			// bean.setStudentId(2);
			model.update(bean);

			MarksheetBean updatedbean = model.findByPK(2);
			System.out.println("Test Update succ");
			if (!"ram kumawat".equals(updatedbean.getName())) {
				System.out.println("Test Update fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests find a marksheet by Roll No.
	 */

	public static void testFindByRollNo() {

		try {
			MarksheetBean bean = model.findByRollNo("9");
			if (bean == null) {
				System.out.println("Test Find By RollNo fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getName());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests search a Marksheets
	 */

	public static void testSearch() {
		try {
			MarksheetBean bean = new MarksheetBean();
			List list = new ArrayList();
			bean.setName("SHUBHAM SHARMA");
			// bean.setRollNo("108");
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests list of Marksheets
	 */
	public static void testList() {
		try {
			MarksheetBean bean = new MarksheetBean();
			List list = new ArrayList();
			list = model.list(1, 6);
			if (list.size() < 0) {
				System.out.println("Test List fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDateTime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDateTime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests get the meritlist of Marksheets
	 */
	public static void testMeritList() {
		try {
			MarksheetBean bean = new MarksheetBean();
			List list = new ArrayList();
			list = model.getMeritList(1, 5);
			if (list.size() < 0) {
				System.out.println("Test List fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

}

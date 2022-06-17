package in.co.rays.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.TimetableBean;
import in.co.rays.bean.UserBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.TimetableModel;

/**
 * @author Veena Yadav
 *
 */
public class TimetableTest {
	public static TimetableModel model = new TimetableModel();

	public static void main(String[] args) throws Exception {
		// testAdd();
		// testDelete();
		// testUpdate();
		// testFindByPK();
		// testSearch();
		// testList();

	}

	public static void testAdd() throws DuplicateRecordException, ParseException, Exception {

		TimetableBean bean = new TimetableBean();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

		bean.setCourseName("M.Com");
		bean.setCourseId(2);
		bean.setSubjectName("Account");
		bean.setSubjectId(2);
		bean.setExamDate(sdf.parse("08-08-2019"));
		// bean.setExamTime("2:00 - 5:00");
		bean.setSemester("2nd");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

		long pk = model.add(bean);
		System.out.println("Test add succ");
		/*
		 * TimetableBean addedBean = model.findByPk(pk); if (addedBean == null) {
		 * System.out.println("Test add fail"); }
		 */ }

	public static void testDelete() {

		try {
			TimetableBean bean = new TimetableBean();
			long pk = 2;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Test Delete succ");
			TimetableBean deletedBean = model.findByPk(pk);
			if (deletedBean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() throws Exception {

		TimetableBean bean = new TimetableBean();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		// bean.setCourseName("BSW");

		bean.setCourseId(2);
		// bean.setSubjectName("humanity");
		bean.setSubjectId(2);
		bean.setExamDate(sdf.parse("08-08-2019"));
		bean.setExamTime("12:00am -4:00pm");
		bean.setSemester("3rd");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		bean.setId(1);
		model.update(bean);
		System.out.println("Test Update succ");

	}

	public static void testFindByPK() throws Exception {

		TimetableBean bean = new TimetableBean();
		long pk = 1;
		bean = model.findByPk(pk);

		System.out.println(bean.getId());
		System.out.println(bean.getCourseName());
		System.out.println(bean.getCourseId());
		System.out.println(bean.getSubjectName());
		System.out.println(bean.getSubjectId());
		System.out.println(bean.getExamDate());
		System.out.println(bean.getExamTime());
		System.out.println(bean.getSemester());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDateTime());
		System.out.println(bean.getModifiedDateTime());

	}

	public static void testList() {

		try {
			TimetableBean bean = new TimetableBean();
			List list = new ArrayList();
			list = model.list(1, 10);

			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (TimetableBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getSubjectId());
				System.out.println(bean.getSubjectName());
				System.out.println(bean.getExamDate());
				System.out.println(bean.getExamTime());
				// System.out.println(bean.get`);
				// System.out.println(bean.getM);
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDateTime());
				System.out.println(bean.getModifiedDateTime());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {

		try {
			TimetableBean bean = new TimetableBean();
			List list = new ArrayList();
			bean.setCourseName("BCA");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (TimetableBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getExamTime());
				System.out.println(bean.getSemester());
				System.out.println(bean.getExamDate());

			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

}

package in.co.rays.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import in.co.rays.bean.BaseBean;
import in.co.rays.bean.StudentBean;
import in.co.rays.exception.ApplicationException;
import org.apache.log4j.Logger;

import in.co.rays.model.CollegeModel;
import in.co.rays.model.StudentModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

/**
 * @author Veena Yadav
 *
 */
@WebServlet(name = "StudentCtl", urlPatterns = { "/ctl/StudentCtl" })
public class StudentCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(StudentCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
		CollegeModel model = new CollegeModel();
		try {
			List l = model.list();
			request.setAttribute("collegeList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}

	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("StudentCtl Method validate Started");

		boolean pass = true;

		String op = DataUtility.getString(request.getParameter("operation"));
		String email = request.getParameter("email");
		String dob = request.getParameter("dob");

		if (DataValidator.isNull(request.getParameter("firstname"))) {
			request.setAttribute("firstname", PropertyReader.getValue("error.require", "First Name "));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("firstname"))) {
			request.setAttribute("firstname",
					PropertyReader.getValue("error.require", "First Name must be in character"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("lastname"))) {
			request.setAttribute("lastname", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("lastname"))) {
			request.setAttribute("lastname",
					PropertyReader.getValue("error.require", "Last Name must be in character"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobile"))) {
			request.setAttribute("mobile", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		}
		if (!DataValidator.isMobileNo(request.getParameter("mobile"))) {
			request.setAttribute("mobile",
					PropertyReader.getValue("error.require", "Mobile No be an interger and start with 6-9"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email "));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.email", "Invalid Email "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("collegename"))) {
			request.setAttribute("collegename", PropertyReader.getValue("error.require", "College Name"));
			pass = false;
		}

		if (DataValidator.isNull(dob)) {
			System.out.println("chekcn ");
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		} else if (!DataValidator.isvalidateAge(dob)) {
			System.out.println("rtwoo");
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Minimum 18 years"));
			pass = false;
		}

		log.debug("StudentCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("StudentCtl Method populatebean Started");

		StudentBean bean = new StudentBean();

		bean.setId(DataUtility.getInt(request.getParameter("id")));

		bean.setFirstName(DataUtility.getString(request.getParameter("firstname")));
		System.out.println("fname>>>>" + request.getParameter("firstname"));
		bean.setLastName(DataUtility.getString(request.getParameter("lastname")));
		System.out.println("lastname>>>>" + request.getParameter("lastname"));

		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		System.out.println("dob>>>>" + request.getParameter("dob"));

		bean.setMobileNo(DataUtility.getString(request.getParameter("mobile")));
		System.out.println("modile>>>>" + request.getParameter("mobile"));

		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		System.out.println("email>>>>" + request.getParameter("email"));
		bean.setCollegeID(DataUtility.getInt(request.getParameter("collegename")));
		System.out.println("cid>>>>" + request.getParameter("collegename"));
		populateDTO(bean, request);

		log.debug("StudentCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * Contains Display logics
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("StudentCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		int id = DataUtility.getInt(request.getParameter("id"));

		// get model

		StudentModel model = new StudentModel();
		if (id > 0 || op != null) {
			StudentBean bean;
			try {
				bean = model.findByPK(id); // model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("StudentCtl Method doGett Ended");
	}

	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("StudentCtl Method doPost Started");
		System.out.println("inside do post");
		String op = DataUtility.getString(request.getParameter("operation"));

		// get model

		StudentModel model = new StudentModel();

		int id = DataUtility.getInt(request.getParameter("id"));
//		System.out.println("inside save  op");
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			System.out.println("inside save  op");

			StudentBean bean = (StudentBean) populateBean(request);
			System.out.println("inside populate bean");

			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage(" Student is successfully Updated", request);
				} else {
					System.out.println("upper add model");

					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage(" Student is successfully Added", request);
					//bean.setId(pk);
				}

				// ServletUtility.setBean(bean, request);
				// ServletUtility.setSuccessMessage("Student is Successfully updated", request);

			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
				// ServletUtility.handleException(e, request, response);
				return;
			}

			// } catch (DuplicateException e) {
//                ServletUtility.setBean(bean, request);
//                ServletUtility.setErrorMessage(
//                        "Student Email Id already exists", request);
//            }

		}

		else if (OP_DELETE.equalsIgnoreCase(op)) {

			StudentBean bean = (StudentBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				// log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);

		log.debug("StudentCtl Method doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.STUDENT_VIEW;
	}

}

package in.co.rays.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.RoleBean;
import in.co.rays.bean.UserBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.UserModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;
import java.sql.Date;;

/**
 * Servlet implementation class UserRegistrationCtl
 */

/**
 * @author Veena Yadav
 *
 */
@WebServlet(name = "UserRegistrationCtl", urlPatterns = { "/UserRegistrationCtl" })
public class UserRegistrationCtl extends BaseCtl {
	public static final String OP_SIGN_UP = "SignUp";

	private static Logger log = Logger.getLogger(UserRegistrationCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("UserRegistrationCtl Method validate Started");

		boolean pass = true;

		String login = request.getParameter("login");
		String dob = request.getParameter("dob");

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName",
					PropertyReader.getValue("error.require", "First Name should be in Character"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName",
					PropertyReader.getValue("error.require", "Last Name should be in Character"));
			pass = false;
		}
		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("password"))) {
			request.setAttribute("password",
					PropertyReader.getValue("error.require", "Password Should be Proper(Veena@123)"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword",
					PropertyReader.getValue("error.require", "Confirm Password must be same as password"));
			pass = false;
		} else if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "Password does not matched");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobilenumber"))) {
			request.setAttribute("mobilenumber", PropertyReader.getValue("error.require", "Mobile Number "));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("mobilenumber"))) {
			request.setAttribute("mobilenumber",
					PropertyReader.getValue("error.require", "Mobile Number  must be an integer start with 6-9"));
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
		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("Confirm  Password  should  be matched", request);

			pass = false;
		}
		log.debug("UserRegistrationCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("UserRegistrationCtl Method populatebean Started");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getInt(request.getParameter("id")));

		bean.setRoleId(RoleBean.STUDENT);

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobilenumber")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));

		populateDTO(bean, request);

		log.debug("UserRegistrationCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * Display concept of user registration
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserRegistrationCtl Method doGet Started");
		ServletUtility.forward(getView(), request, response);

	}

	/**
	 * Submit concept of user registration
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("in get method");
		log.debug("UserRegistrationCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		UserModel model = new UserModel();
		int id = DataUtility.getInt(request.getParameter("id"));
		if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);
			try {
				long pk = model.add(bean);
				bean.setId(pk);
				request.getSession().setAttribute("UserBean", bean);
				ServletUtility.setSuccessMessage("Data is successfully saved", request);
				ServletUtility.forward(getView(), request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				log.error(e);
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
				ServletUtility.forward(getView(), request, response);
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;
		}
		log.debug("UserRegistrationCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USER_REGISTRATION_VIEW;
	}

}

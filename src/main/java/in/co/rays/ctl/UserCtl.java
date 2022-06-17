package in.co.rays.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.UserBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.RoleModel;
import in.co.rays.model.UserModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

/**
 * *User functionality Controller. Performs operation for add, update and get
 * User
 */

/**
 * @author Veena Yadav
 *
 */
@WebServlet(name = "UserCtl", urlPatterns = { "/ctl/UserCtl" })
public class UserCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(UserCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {

		RoleModel model = new RoleModel();
		List list;
		try {
			list = model.list();
			request.setAttribute("roleList", list);
			System.out.println("List in controller........................" + list);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// super.preload(request);
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("UserCtl Method validate Started");
		boolean pass = true;

		long id = DataUtility.getInt(request.getParameter("id"));

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
					PropertyReader.getValue("error.require", "last Name should be in Character"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Invalid Email"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("password"))) {
			request.setAttribute("password",
					"Password should contain 8 letter with alpha-numeric and special Character");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword",
					"Password should contain 8 letter with alpha-numeric and special Character");
			pass = false;
		} else if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "Password does not matched");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileno"))) {
			request.setAttribute("mobileno", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("mobileno"))) {
			request.setAttribute("mobileno", "Mobile No. must be 10 Digit and No. Series start with 6-9");

			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("roleId"))) {
			request.setAttribute("roleId", PropertyReader.getValue("error.require", "Role ID"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}

		if (DataValidator.isNull(dob)) {
			System.out.println("chekcn ");
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		} else if(!DataValidator.isvalidateAge(dob) ) {
			System.out.println("rtwoo");
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Minimum 18 years"));
			pass = false;
		}

		log.debug("UserCtl Method validate Ended");
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("UserCtl Method populatebean Started");
		UserBean bean = new UserBean();
		// long id = DataUtility.getInt(request.getParameter("id"));

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileno")));

		bean.setRoleId(DataUtility.getLong(request.getParameter("roleId")));
		System.out.println(bean.getRoleId() + " " + request.getParameter("roleId"));

		populateDTO(bean, request);
		log.debug("UserCtl Method populatebean Ended");
		return bean;
	}

	/**
	 * Contains Display logics
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("UserCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		UserModel model = new UserModel();

		if (id > 0 || op != null) {
			// System.out.println("in id > 0 condition");
			UserBean bean;
			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("UserCtl Method doGet Ended");

	}

	/**
	 * Contains Submit logics
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("UserCtl Method doPost Started");
		System.out.println("In doPost of UserCtl........................................");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		UserModel model = new UserModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			UserBean bean = (UserBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				} else {
					long pk = model.add(bean);
					// bean.setId(pk);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("User is Successfully updated", request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);

			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("UserCtl Method doPost Ended");

	}

	@Override
	protected String getView() {
		return ORSView.USER_VIEW;
	}

}

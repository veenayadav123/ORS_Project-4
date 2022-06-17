package in.co.rays.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.UserBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.RecordNotFoundException;
import in.co.rays.model.UserModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;


/**
 * @author Veena Yadav
 *
 */
@WebServlet(name = "ChangePasswordCtl", urlPatterns = { "/ctl/ChangePasswordCtl" })
public class ChangePasswordCtl extends BaseCtl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String OP_CHANGE_MY_PROFILE = "Change My Profile";

	// private static Logger log = Logger.getLogger(ChangePasswordCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		// log.debug("ChangePasswordCtl Method validate Started");

		boolean pass = true;

		String op = request.getParameter("operation");

		if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {

			return pass;
		}
		if (DataValidator.isNull(request.getParameter("oldPassword"))) {
			request.setAttribute("oldPassword", PropertyReader.getValue("error.require", "Old Password"));
			pass = false;
		}else if (!DataValidator.isPassword(request.getParameter("oldPassword"))) {
			request.setAttribute("oldPassword", PropertyReader.getValue("error.require", "Password should be alpha-numeric and special Character"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", PropertyReader.getValue("error.require", "New Password"));
			pass = false;
		}else if (!DataValidator.isPassword(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", PropertyReader.getValue("error.require", "New Password should be alpha-numeric and special Character"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		}else if (!DataValidator.isPassword(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password should be alpha-numeric and special Character"));
			pass = false;
		}
		if (!request.getParameter("newPassword").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword","New and confirm passwords not matched");

			pass = false;
		}

		// log.debug("ChangePasswordCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		// log.debug("ChangePasswordCtl Method populatebean Started");

		UserBean bean = new UserBean();

		bean.setPassword(DataUtility.getString(request.getParameter("oldPassword")));

		populateDTO(bean, request);

		// log.debug("ChangePasswordCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * Display Logics inside this method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Submit logic inside it
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		// log.debug("ChangePasswordCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		UserModel model = new UserModel();

		UserBean bean = (UserBean) populateBean(request);

		UserBean UserBean = (UserBean) session.getAttribute("user");
		
		String oldPassword = (String) request.getParameter("oldPassword");

		String newPassword = (String) request.getParameter("newPassword");
		
		String confirmPassword = (String) request.getParameter("confirmPassword");

		long id = UserBean.getId();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			try {
				boolean flag = model.changePassword(id, bean.getPassword(), newPassword);
				if (flag == true) {
					bean = model.findByLogin(UserBean.getLogin());
					System.out.println("in doPost "+bean.getFirstName()+bean.getRoleId());
					session.setAttribute("user", bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Password has been changed Successfully.", request);
				}
			} catch (ApplicationException e) {
				// log.error(e);
				ServletUtility.handleException(e, request, response);
				return;

			} catch (RecordNotFoundException e) {
				e.printStackTrace();
				ServletUtility.setErrorMessage("Old PassWord is Invalid", request);
			}
		} else if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
		// log.debug("ChangePasswordCtl Method doGet Ended");
	}

	@Override
	protected String getView() {
		return ORSView.CHANGE_PASSWORD_VIEW;
	}

}

 



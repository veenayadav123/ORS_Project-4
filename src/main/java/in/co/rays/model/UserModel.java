package in.co.rays.model;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import in.co.rays.bean.UserBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.exception.RecordNotFoundException;
import in.co.rays.util.EmailBuilder;
import in.co.rays.util.EmailMessage;
import in.co.rays.util.EmailUtility;
import in.co.rays.util.JDBCDataSource;

/**
 * @author Veena Yadav
 *
 */
public class UserModel {
	private static Logger log = Logger.getLogger(CollegeModel.class);

	/**
	 * Find next PK of User
	 *
	 */
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_USER");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk + 1;
	}

	/**
	 * Add a User
	 *
	 *
	 */
	public long add(UserBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;

		UserBean existbean = findByLogin(bean.getLogin());

		if (existbean != null) {

			throw new DuplicateRecordException("Login Id already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
			System.out.println(pk + " in ModelJDBC");
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO ST_USER VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getFirstName());
			pstmt.setString(3, bean.getLastName());
			pstmt.setString(4, bean.getLogin());
			pstmt.setString(5, bean.getPassword());
			pstmt.setDate(6, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(7, bean.getMobileNo());
			pstmt.setLong(8, bean.getRoleId());
			pstmt.setInt(9, bean.getUnSuccessfulLogin());
			pstmt.setString(10, bean.getGender());
			pstmt.setTimestamp(11, bean.getLastLogin());
			pstmt.setString(12, bean.getLock());
			pstmt.setString(13, bean.getRegisteredIP());
			pstmt.setString(14, bean.getLastLoginIP());
			pstmt.setString(15, bean.getCreatedBy());
			pstmt.setString(16, bean.getModifiedBy());
			pstmt.setTimestamp(17, bean.getCreatedDateTime());
			pstmt.setTimestamp(18, bean.getModifiedDateTime());
			System.out.println("User Model");
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			// throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	/**
	 * Find User by Login
	 *
	 */

	public UserBean findByLogin(String email) throws ApplicationException {
		log.debug("Model findByLogin Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE LOGIN=?");
		UserBean bean = null;
		Connection conn = null;
		// System.out.println("sql" + sql);

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setGender(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDateTime(rs.getTimestamp(12));
				bean.setModifiedDateTime(rs.getTimestamp(13));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByLogin End");
		return bean;

	}

	/**
	 * Find User by PK
	 *
	 */

	public UserBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE ID=?");
		UserBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				// bean.setConfirmPassword(rs.getString(6));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIP(rs.getString(13));
				bean.setLastLoginIP(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDateTime(rs.getTimestamp(17));
				bean.setModifiedDateTime(rs.getTimestamp(18));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return bean;
	}

	/**
	 * Delete a User
	 *
	 */
	public void delete(UserBean bean) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_USER WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
	}

	/**
	 * Update a user
	 *
	 */

	public void update(UserBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;

		UserBean beanExist = findByLogin(bean.getLogin());
		// Check if updated LoginId already exist
		if (beanExist != null && !(beanExist.getId() == bean.getId())) {
			throw new DuplicateRecordException("LoginId is already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_USER SET FIRST_NAME=?,LAST_NAME=?,LOGIN=?,PASSWORD=?,DOB=?,MOBILE_NO=?,ROLE_ID=?,UNSUCCESSFUL_LOGIN=?,GENDER=?,LAST_LOGIN=?,USER_LOCK=?,REGISTERED_IP=?,LAST_LOGIN=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getFirstName());
			pstmt.setString(2, bean.getLastName());
			pstmt.setString(3, bean.getLogin());
			pstmt.setString(4, bean.getPassword());
			pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(6, bean.getMobileNo());
			pstmt.setLong(7, bean.getRoleId());
			pstmt.setInt(8, bean.getUnSuccessfulLogin());
			pstmt.setString(9, bean.getGender());
			pstmt.setTimestamp(10, bean.getLastLogin());
			pstmt.setString(11, bean.getLock());
			pstmt.setString(12, bean.getRegisteredIP());
			pstmt.setString(13, bean.getLastLoginIP());
			pstmt.setString(14, bean.getCreatedBy());
			pstmt.setString(15, bean.getModifiedBy());
			pstmt.setTimestamp(16, bean.getCreatedDateTime());
			pstmt.setTimestamp(17, bean.getModifiedDateTime());
			pstmt.setLong(18, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating User ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	/**
	 * Search User with pagination
	 *
	 */

	public List search(UserBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE 1=1 ");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
			}
			if (bean.getLogin() != null && bean.getLogin().length() > 0) {
				sql.append(" AND LOGIN like '" + bean.getLogin() + "%'");
			}
			if (bean.getPassword() != null && bean.getPassword().length() > 0) {
				sql.append(" AND PASSWORD like '" + bean.getPassword() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + bean.getGender());
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE_NO = " + bean.getMobileNo());
			}
			if (bean.getRoleId() > 0) {
				sql.append(" AND ROLEID = " + bean.getRoleId());
			}
			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" AND GENDER like '" + bean.getGender() + "%'");
			}

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + " , " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println(sql);
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setGender(rs.getString(10));
				bean.setCreatedBy(rs.getString(12));
				bean.setModifiedBy(rs.getString(13));
				bean.setCreatedDateTime(rs.getTimestamp(13));
				bean.setModifiedDateTime(rs.getTimestamp(14));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search user");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	/**
	 * Get List of User with pagination
	 *
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_USER");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				UserBean bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setConfirmPassword(rs.getString(6));
				bean.setDob(rs.getDate(7));
				bean.setMobileNo(rs.getString(8));
				bean.setRoleId(rs.getLong(9));
				bean.setGender(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDateTime(rs.getTimestamp(13));
				bean.setModifiedDateTime(rs.getTimestamp(14));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;

	}

	public UserBean authenticate(String login, String password) throws ApplicationException {
		log.debug("Model authenticate Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE LOGIN = ? AND PASSWORD = ?");
		UserBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, login);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				// bean.setConfirmPassword(rs.getString(6));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIP(rs.getString(13));
				bean.setLastLoginIP(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDateTime(rs.getTimestamp(17));
				bean.setModifiedDateTime(rs.getTimestamp(18));

			}
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in get Authenticate");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model authenticate End");
		return bean;
	}

	/**
	 * Lock User for certain time duration
	 *
	 */

	public boolean lock(String login) throws RecordNotFoundException, ApplicationException {
		log.debug("Service lock Started");
		boolean flag = false;
		UserBean beanExist = null;
		try {
			beanExist = findByLogin(login);
			if (beanExist != null) {
				// beanExist.setLock(UserBean.ACTIVE);
				update(beanExist);
				flag = true;
			} else {
				throw new RecordNotFoundException("LoginId not exist");
			}
		} catch (DuplicateRecordException e) {
			log.error("Application Exception..", e);
			throw new ApplicationException("Database Exception");
		}
		log.debug("Service lock End");
		return flag;
	}

	/**
	 * Get User Roles
	 *
	 */

	public List getRoles(UserBean bean) throws ApplicationException {
		log.debug("Model get roles Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE role_Id=?");
		Connection conn = null;
		List list = new ArrayList();
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, bean.getRoleId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setConfirmPassword(rs.getString(6));
				bean.setDob(rs.getDate(7));
				bean.setMobileNo(rs.getString(8));
				bean.setRoleId(rs.getLong(9));
				bean.setGender(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDateTime(rs.getTimestamp(13));
				bean.setModifiedDateTime(rs.getTimestamp(14));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in get roles");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model get roles End");
		return list;
	}

	public boolean changePassword(Long id, String oldPassword, String newPassword)
			throws RecordNotFoundException, ApplicationException {

		log.debug("model changePassword Started");
		boolean flag = false;
		UserBean beanExist = null;

		beanExist = findByPK(id);
		System.out.println(beanExist);
		if (beanExist != null && beanExist.getPassword().equals(oldPassword)) {
			beanExist.setPassword(newPassword);
			try {
				update(beanExist);
			} catch (DuplicateRecordException e) {
				log.error(e);
				e.printStackTrace();
				throw new ApplicationException("LoginId is already exist");
			}
			flag = true;

		} else {

			throw new RecordNotFoundException("Login not exist");
		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", beanExist.getLogin());
		map.put("password", beanExist.getPassword());
		map.put("firstName", beanExist.getFirstName());
		map.put("lastName", beanExist.getLastName());

		String message = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(beanExist.getLogin());
		msg.setSubject(" Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		 EmailUtility.sendMail(msg);

		log.debug("Model changePassword End");
		return flag;

	}

	/**
	 * Register a user
	 *
	 */

	public long registerUser(UserBean bean) throws ApplicationException, DuplicateRecordException {

//        log.debug("Model add Started");

		long pk = add(bean);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(bean.getLogin());
		msg.setSubject("Registration is successful");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		 EmailUtility.sendMail(msg);
		return pk;
	}

	/**
	 * Reset Password of User with auto generated Password
	 *
	 */

	public boolean resetPassword(UserBean bean) throws ApplicationException {

		String newPassword = String.valueOf(new Date(0).getTime()).substring(0, 0);
		UserBean userData = findByPK(bean.getId());
		userData.setPassword(newPassword);

		try {
			update(userData);
		} catch (DuplicateRecordException e) {
			return false;
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());
		map.put("firstName", bean.getFirstName());
		map.put("lastName", bean.getLastName());

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(bean.getLogin());
		msg.setSubject("Password has been reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		 EmailUtility.sendMail(msg);

		return true;
	}

	/**
	 * Send the password of User to his Email
	 *
	 */

	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException {
		UserBean userData = findByLogin(login);
		boolean flag = false;

		if (userData == null) {
			throw new RecordNotFoundException("Email ID does not exists !");

		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", userData.getLogin());
		map.put("password", userData.getPassword());
		map.put("firstName", userData.getFirstName());
		map.put("lastName", userData.getLastName());
		String message = EmailBuilder.getForgetPasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(login);
		msg.setSubject("SUNRAYS ORS Password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		EmailUtility.sendMail(msg);
		flag = true;

		return flag;
	}
}

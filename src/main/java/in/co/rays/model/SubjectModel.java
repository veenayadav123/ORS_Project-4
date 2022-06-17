package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import in.co.rays.bean.CourseBean;
import in.co.rays.bean.SubjectBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

/**
 * @author Veena Yadav
 *
 */
public class SubjectModel {
	private static Logger log = Logger.getLogger(SubjectModel.class);

	/**
	 * Find next PK of Subject
	 * 
	 * 
	 * 
	 */

	public Integer nextPk() throws ApplicationException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM ST_SUBJECT");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			log.error("database Exception ...", e);
			throw new ApplicationException("Exception in NextPk of subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	/**
	 * Add a Subject
	 * 
	 * 
	 * 
	 */

	public Long add(SubjectBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		// convert id into name.....
		CourseModel model = new CourseModel();
		CourseBean bean1 = model.findByPk(bean.getCourseId());
		String courseName = bean1.getcourseName();
//		System.out.println("----------------->"+courseName);

		SubjectBean DuplicateSubjectName = findByName(bean.getSubjectName());
		if (DuplicateSubjectName != null) {
			throw new DuplicateRecordException("Subject name Already Exsist");
		}

		try {
			pk = nextPk();

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_SUBJECT VALUES(?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getSubjectName());
			pstmt.setString(3, courseName);
			pstmt.setInt(4, bean.getCourseId());
			pstmt.setString(5, bean.getDescription());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDateTime());
			pstmt.setTimestamp(9, bean.getModifiedDateTime());
			pstmt.executeUpdate();

			pstmt.close();
			conn.commit();
		} catch (Exception e) {
			log.error("database Exception ...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in the Rollback of Subject Model" + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception in Add method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;

	}

	/**
	 * Delete a Subject
	 * 
	 *
	 */

	public void delete(SubjectBean bean) throws ApplicationException {
		log.debug("Subject Model Delete method Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_SUBJECT WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			log.error("database Exception ...", e);

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(
						"Exception in Rollback of Delte Method of Subject Model" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Delte Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Subject Model Delete method End");
	}

	/**
	 * Update a Subject
	 * 
	 * 
	 */

	public void update(SubjectBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Subject Model update method Started");
		Connection conn = null;

		CourseModel coumodel = new CourseModel();
		CourseBean coubean = coumodel.findByPk(bean.getCourseId());
		String courseName = coubean.getcourseName();

		SubjectBean beanExist = findByName(bean.getSubjectName());
		if (beanExist != null && bean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Subject name Already Exsist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_SUBJECT SET Subject_Name=?,Course_Name=?,Course_ID=?,Description=?,CreatedBy=?,ModifiedBy=?,Created_DateTime=?,Modified_DateTime=? WHERE ID=?");

			pstmt.setString(1, bean.getSubjectName());
			pstmt.setString(2, courseName);
			pstmt.setInt(3, bean.getCourseId());
			pstmt.setString(4, bean.getDescription());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDateTime());
			pstmt.setTimestamp(8, bean.getModifiedDateTime());
			pstmt.setLong(9, bean.getId());
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("database Exception....", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(
						"Exception in Rollback of Update Method of Subject Model" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Delte Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Subject Model Update method End");
	}

	/**
	 * Find User by Subject Name
	 * 
	 * 
	 */

	public SubjectBean findByName(String name) throws ApplicationException {
		log.debug("Subject Model findByName method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE COURSE_NAME=?");
		Connection conn = null;
		SubjectBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();

				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setCourseId(rs.getInt(3));
				bean.setCourseName(rs.getString(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception....", e);
			throw new ApplicationException("Exception in findByName Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		log.debug("Subject Model findByName method End");
		return bean;
	}

	/**
	 * Find User by Subject PK
	 * 
	 * 
	 */
	public SubjectBean findByPk(long pk) throws ApplicationException {
		log.debug("Subject Model findBypk method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE ID=?");
		Connection conn = null;
		SubjectBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();

				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setCourseName(rs.getString(3));
				bean.setCourseId(rs.getInt(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("database Exception....", e);
			throw new ApplicationException("Exception in findByPk Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		log.debug("Subject Model findByPk method End");
		return bean;
	}

	/**
	 * Search Subject
	 * 
	 * 
	 */

	public List search(SubjectBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Search Subject with pagination
	 * 
	 * 
	 * 
	 */

	public List search(SubjectBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Subject Model search method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE 1=1 ");
		System.out.println("model search" + bean.getId());

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}

			if (bean.getCourseId() > 0) {
				sql.append(" AND Course_ID = " + bean.getCourseId());
			}

			if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
				sql.append(" AND Name like '" + bean.getSubjectName() + "%'");
			}
			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" AND Course_Name like '" + bean.getCourseName() + "%'");
			}

			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" AND description like '" + bean.getDescription() + " % ");
			}

		}

		// Page Size is greater then Zero then aplly pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}
		System.out.println("sql is" + sql);
		Connection conn = null;
		ArrayList list = new ArrayList();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new SubjectBean();

				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setCourseName(rs.getString(3));
				bean.setCourseId(rs.getInt(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));
				list.add(bean);
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("database Exception....", e);

			throw new ApplicationException("Exception in search Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Subject Model search method End");
		return list;
	}

	/**
	 * Get List of Subject
	 * 
	 * 
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of Subject with pagination
	 * 
	 * @return list : List of Subject
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 * 
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Subject Model list method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT ");

		// Page Size is greater then Zero then aplly pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		Connection conn = null;
		ArrayList list = new ArrayList();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				SubjectBean bean = new SubjectBean();

				bean.setId(rs.getLong(1));
				bean.setSubjectName(rs.getString(2));
				bean.setCourseName(rs.getString(3));
				bean.setCourseId(rs.getInt(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDateTime(rs.getTimestamp(8));
				bean.setModifiedDateTime(rs.getTimestamp(9));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("database Exception....", e);
			e.printStackTrace();
			throw new ApplicationException("Exception in list Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Subject Model list method End");
		return list;
	}
}

package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import in.co.rays.bean.CollegeBean;
import in.co.rays.bean.CourseBean;
import in.co.rays.bean.UserBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

/**
 * @author Veena Yadav
 *
 */
public class CourseModel {
	/** The log. */
	private static Logger log = Logger.getLogger(CourseModel.class);

	/**
	 * Find next PK of Course.
	 *
	 */

	public Integer nextPk() throws DatabaseException {
		log.debug("CourseModel nextPK method started");

		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_COURSE");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in Database..", e);
			throw new DatabaseException("Exception : Exception in getting Pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("CourseModel nextPK method END");
		return pk + 1;
	}

	/**
	 * Add a Course.
	 *
	 */

	public int add(CourseBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("CourseModel Add method END");
		Connection conn = null;
		int pk = 0;

		CourseBean duplicateCourseName = findByName(bean.getcourseName());
		if (duplicateCourseName != null) {
			throw new DuplicateRecordException("Course Name already Exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_COURSE VALUES(?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getcourseName());
			pstmt.setString(3, bean.getdescription());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDateTime());
			pstmt.setTimestamp(7, bean.getModifiedDateTime());
			pstmt.setString(8, bean.getDuration());
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("EXception in Database...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add Rollback Exception.." + ex.getMessage());
			}
			throw new ApplicationException("Exception in Course Add method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("CourseModel Add method END");
		return pk;
	}

	public void update(CourseBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;

		CourseBean beanExist = findByName(bean.getcourseName());
		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("Course Already Exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_COURSE SET COURSE_NAME=?,DESCRIPTION=?,CREATEDBY=?,MODIFIEDBY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=?,DURATION=?  WHERE ID=?");
			pstmt.setString(1, bean.getcourseName());
			pstmt.setString(2, bean.getdescription());
			pstmt.setString(3, bean.getCreatedBy());
			pstmt.setString(4, bean.getModifiedBy());
			pstmt.setTimestamp(5, bean.getCreatedDateTime());
			pstmt.setTimestamp(6, bean.getModifiedDateTime());
			pstmt.setString(7, bean.getDuration());
			pstmt.setLong(8, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Database Exception ...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : Exception in Rollback.." + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception in Updating the Course Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public CourseBean findByName(String name) {

		log.debug("Course Model FindByName method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE COURSE_NAME=?");
		CourseBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getInt(1));
				bean.setcourseName(rs.getString(2));
				bean.setdescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));
				bean.setDuration(rs.getString(8));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Database Exception ..", e);
			// throw new ApplicationException("Exception in Course Model FindByName Method
			// ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Course Model FindByName method End");
		return bean;
	}

	/**
	 * Find User by Course.
	 *
	 */

	public CourseBean findByPk(long pk) throws ApplicationException {
		log.debug("CourseModel FindbyPK method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE ID=?");
		CourseBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getInt(1));
				bean.setcourseName(rs.getString(2));
				bean.setdescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));
				bean.setDuration(rs.getString(8));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DatabaseException ... ", e);
			throw new ApplicationException("Exception : Exception in the findbyPk method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("CourseModel FindbyPK method End");
		return bean;
	}

	/**
	 * Delete a Course.
	 *
	 */

	public void delete(CourseBean bean) throws ApplicationException {
		log.debug("CourseModel Delete Method Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_COURSE WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Exception in Rollback Method" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Delete Method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("CourseModel Delete Method End");
	}

	/**
	 * Find User by Course
	 * 
	 * 
	 */

	public CourseBean findByPk(Long pk) throws ApplicationException {
		log.debug("CourseModel FindbyPK method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE ID=?");
		CourseBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setcourseName(rs.getString(2));
				bean.setdescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));
				bean.setDuration(rs.getString(8));
			}
			rs.close();
		} catch (Exception e) {
			log.error("DatabaseException ... ", e);
			throw new ApplicationException("Exception : Exception in the findbyPk method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("CourseModel FindbyPK method End");
		return bean;
	}

	/**
	 * Search Course
	 * 
	 * 
	 */

	public List search(CourseBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Search Course with pagination
	 * 
	 * 
	 * 
	 */

	public List<CourseBean> search(CourseBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE 1=1 ");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getcourseName() != null && bean.getcourseName().length() > 0) {
				sql.append(" AND COURSE_NAME like '" + bean.getcourseName() + "%'");
			}
			if (bean.getDuration() != null && bean.getDuration().length() > 0) {
				sql.append(" AND DURATION like '" + bean.getDuration() + "%'");
			}
			if (bean.getdescription() != null && bean.getdescription().length() > 0) {
				sql.append(" AND DESCRIPTION like '" + bean.getdescription() + "%'");
			}

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println(sql);
		ArrayList<CourseBean> list = new ArrayList<CourseBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setcourseName(rs.getString(2));
				bean.setdescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));
				bean.setDuration(rs.getString(8));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in search user");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;

	}

	/**
	 * Get List of Course
	 * 
	 * @return list : List of Course
	 * 
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of Course with pagination
	 * 
	 * 
	 */

	public List<CourseBean> list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("CourseModel List Method End");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE ");
		// if page size is greater thanzero then apply pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;
		System.out.println(sql);
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CourseBean bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setcourseName(rs.getString(2));
				bean.setdescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));
				bean.setDuration(rs.getString(8));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception in list ...", e);
			throw new ApplicationException("Exception : Exception in CourseModel List method " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("CourseModel List Method End");
		return list;
	}

	/*
	 * public List list(int pageNo, int pageSize) throws ApplicationException {
	 * log.debug("Model list Started"); ArrayList list = new ArrayList();
	 * StringBuffer sql = new StringBuffer("select * from ST_COLLEGE"); // if page
	 * size is greater than zero then apply pagination if (pageSize > 0) { //
	 * Calculate start record index pageNo = (pageNo - 1) * pageSize;
	 * sql.append(" limit " + pageNo + "," + pageSize); }
	 * 
	 * Connection conn = null;
	 * 
	 * try { conn = JDBCDataSource.getConnection(); PreparedStatement pstmt =
	 * conn.prepareStatement(sql.toString()); ResultSet rs = pstmt.executeQuery();
	 * while (rs.next()) { CollegeBean bean = new CollegeBean();
	 * bean.setId(rs.getLong(1)); bean.setcourseName(rs.getString(2)); bean.set;
	 * bean.setAddress(rs.getString(3)); bean.setState(rs.getString(4));
	 * bean.setCity(rs.getString(5)); bean.setPhoneNo(rs.getString(6));
	 * bean.setCreatedBy(rs.getString(7)); bean.setModifiedBy(rs.getString(8));
	 * bean.setCreatedDateTime(rs.getTimestamp(9));
	 * bean.setModifiedDateTime(rs.getTimestamp(10)); list.add(bean); } rs.close();
	 * } catch (Exception e) { log.error("Database Exception..", e); throw new
	 * ApplicationException("Exception : Exception in getting list of users"); }
	 * finally { JDBCDataSource.closeConnection(conn); }
	 * 
	 * log.debug("Model list End"); return list;
	 * 
	 * }
	 */}

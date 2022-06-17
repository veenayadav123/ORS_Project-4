package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.bean.CollegeBean;
import in.co.rays.bean.CourseBean;
import in.co.rays.bean.FacultyBean;
import in.co.rays.bean.SubjectBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

/**
 * @author Veena Yadav
 *
 */
public class FacultyModel {
	public static Logger log = Logger.getLogger(FacultyModel.class);

	/**
	 * Find next PK of Faculty
	 * 
	 * 
	 */

	public Integer nextPk() throws ApplicationException {
		log.debug("Faculty Model nextPK method Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM ST_FACULTY");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			log.error("DataBase Exception ..", e);
			throw new ApplicationException("Exception in Getting the PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model nextPK method End");
		return pk + 1;
	}

	/**
	 * Add a Faculty.
	 *
	 */

	public int add(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		Connection conn = null;
		// System.out.println("gender :- "+bean.getGender());
		int pk = 0;
		CollegeModel collegeModel = new CollegeModel();
		CollegeBean collegeBean = collegeModel.findByPK(bean.getCollegeId());
		String collegeName = collegeBean.getName();

		CourseModel courseModel = new CourseModel();
		CourseBean courseBean = courseModel.findByPk(bean.getCourseId());
		String courseName = courseBean.getcourseName();

		SubjectModel subjectModel = new SubjectModel();
		SubjectBean subjectBean = subjectModel.findByPk(bean.getSubjectId());
		String subjectName = subjectBean.getSubjectName();

//		FacultyBean beanExist = findByEmail(bean.getEmail_id());
//		if (beanExist != null) { 
//			  throw new DuplicateException("Email already exists"); 
//			  }
//		 

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO ST_FACULTY VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getFirstName());
			pstmt.setString(3, bean.getLastName());
			pstmt.setString(4, bean.getGender());
			System.out.println("4  bd");
			pstmt.setDate(5, new java.sql.Date(bean.getDOB().getTime()));
			System.out.println("5   add");
			pstmt.setString(6, bean.getQualification());
			pstmt.setString(7, bean.getEmailId());
			pstmt.setString(8, bean.getMobileNo());
			pstmt.setInt(9, bean.getCollegeId());
			pstmt.setString(10, collegeName);
			pstmt.setInt(11, bean.getCourseId());
			pstmt.setString(12, courseName);
			pstmt.setInt(13, bean.getSubjectId());
			pstmt.setString(14, subjectName);
			pstmt.setString(15, bean.getCreatedBy());
			pstmt.setString(16, bean.getModifiedBy());
			pstmt.setTimestamp(17, bean.getCreatedDateTime());
			pstmt.setTimestamp(18, bean.getModifiedDateTime());

			pstmt.executeUpdate();
			conn.commit(); // End transaction
			System.out.println("faculty add close");
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in add Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}
	/*
	 * Delete a Faculty
	 * 
	 * 
	 */

	public void delete(FacultyBean bean) throws ApplicationException {
		log.debug("Faculty Model Delete method End");
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_FACULTY WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			log.error("DATABASE EXCEPTION ", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in Faculty Model rollback" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Faculty Model Delete Method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model delete method End");
	}

	/**
	 * Update a Faculty.
	 * 
	 */

	public void update(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;
		CollegeModel collegeModel = new CollegeModel();
		CollegeBean collegeBean = collegeModel.findByPK(bean.getCollegeId());

		String collegeName = collegeBean.getName();

		CourseModel courseModel = new CourseModel();
		CourseBean courseBean = courseModel.findByPk(bean.getCourseId());
		System.out.println(courseBean);
		String courseName = courseBean.getcourseName();

		SubjectModel subjectModel = new SubjectModel();
		SubjectBean subjectBean = subjectModel.findByPk(bean.getSubjectId());
		String subjectName = subjectBean.getSubjectName();
		
		  FacultyBean beanExist = findByEmail(bean.getEmailId()); // Check if updated EmailId already exist
		   if (beanExist != null && !(beanExist.getId() ==
		  bean.getId())) { throw new DuplicateRecordException("EmailId is already exist"); }
		 

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_FACULTY SET  FIRST_NAME=?, LAST_NAME=?, GENDER=?, DOB=?,QUALIFICATION=?, EMAIL_ID=?, MOBILE_NO=? , COLLEGE_ID=?, COLLEGE_NAME=?,COURSE_ID=?,COURSE_NAME=?, SUBJECT_ID=?, SUBJECT_NAME=?, CREATEDBY=? , MODIFIEDBY=? , CREATED_DATETIME=? , MODIFIED_DATETIME=?  WHERE ID= ? ");

			pstmt.setString(1, bean.getFirstName());
			pstmt.setString(2, bean.getLastName());
			pstmt.setString(3, bean.getGender());
			pstmt.setDate(4, new java.sql.Date(bean.getDOB().getTime()));
			pstmt.setString(5, bean.getQualification());
			pstmt.setString(6, bean.getEmailId());
			pstmt.setString(7, bean.getMobileNo());
			pstmt.setInt(8, bean.getCollegeId());
			pstmt.setString(9, collegeName);
			pstmt.setInt(10, bean.getCourseId());
			pstmt.setString(11, courseName);
			pstmt.setInt(12, bean.getSubjectId());
			pstmt.setString(13, subjectName);
			pstmt.setString(14, bean.getCreatedBy());
			pstmt.setString(15, bean.getModifiedBy());
			pstmt.setTimestamp(16, bean.getCreatedDateTime());
			pstmt.setTimestamp(17, bean.getModifiedDateTime());
			pstmt.setLong(18, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DATABASE EXCEPTION ...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in rollback faculty model .." + ex.getMessage());
			}
			throw new ApplicationException("Exception in faculty model Update Method..");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model update method End");
	}

	/**
	 * Find User by Faculty name.
	 *
	 */

	public FacultyBean findByEmail(String EmailId) throws ApplicationException {
		log.debug("Faculty Model findByName method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE EMAIL_Id=?");
		Connection conn = null;
		FacultyBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, EmailId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getInt(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setDOB(rs.getDate(5));
				bean.setQualification(rs.getString(6));
				bean.setEmailId(rs.getString(7));
				bean.setMobileNo(rs.getString(8));
				bean.setCollegeId(rs.getInt(9));
				bean.setCollegeName(rs.getString(10));
				bean.setCourseId(rs.getInt(11));
				bean.setCourseName(rs.getString(12));
				bean.setSubjectId(rs.getInt(13));
				bean.setSubjectName(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDateTime(rs.getTimestamp(17));
				bean.setModifiedDateTime(rs.getTimestamp(18));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("database exception ...", e);
			throw new ApplicationException("Exception : Exception in faculty model in findbyName method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model findbyName method End");
		return bean;
	}

	/**
	 * Find User by Faculty PK.
	 *
	 */

	public FacultyBean findByPk(long pk) throws ApplicationException {
		log.debug("Faculty Model findByPK method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE ID=?");
		Connection conn = null;
		FacultyBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getInt(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setDOB(rs.getDate(5));
				bean.setQualification(rs.getString(6));
				bean.setEmailId(rs.getString(7));
				bean.setMobileNo(rs.getString(8));
				bean.setCollegeId(rs.getInt(9));
				bean.setCollegeName(rs.getString(10));
				bean.setCourseId(rs.getInt(11));
				bean.setCourseName(rs.getString(12));
				bean.setSubjectId(rs.getInt(13));
				bean.setSubjectName(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDateTime(rs.getTimestamp(17));
				bean.setModifiedDateTime(rs.getTimestamp(18));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("database exception ...", e);
			throw new ApplicationException("Exception : Exception in findByPK in faculty model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model FindByPK method end");
		return bean;
	}

	/**
	 * Search Faculty.
	 *
	 */

	public List search(FacultyBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Search Faculty with pagination.
	 *
	 */

	public List search(FacultyBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Faculty Model search  method Started");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE true");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getCourseId() > 0) {
				sql.append(" AND college_Id = " + bean.getCourseId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().trim().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().trim().length() > 0) {
				sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
			}

			if (bean.getEmailId() != null && bean.getEmailId().length() > 0) {
				sql.append(" AND Email_Id like '" + bean.getEmailId() + "%'");
			}

			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" AND Gender like '" + bean.getGender() + "%'");
			}

			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND Mobile_No like '" + bean.getMobileNo() + "%'");
			}

			if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
				sql.append(" AND college_Name like '" + bean.getCollegeName() + "%'");
			}
			if (bean.getCourseId() > 0) {
				sql.append(" AND course_Id = " + bean.getCourseId());
			}
			if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
				sql.append(" AND course_Name like '" + bean.getCollegeName() + "%'");
			}
			if (bean.getSubjectId() > 0) {
				sql.append(" AND Subject_Id = " + bean.getSubjectId());
			}
			if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
				sql.append(" AND subject_Name like '" + bean.getSubjectName() + "%'");
			}
		}

		// if page no is greater then zero then apply pagination
		System.out.println("model page ........" + pageNo + " " + pageSize);
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

				bean = new FacultyBean();
				bean.setId(rs.getInt(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setDOB(rs.getDate(5));
				bean.setQualification(rs.getString(6));
				bean.setEmailId(rs.getString(7));
				bean.setMobileNo(rs.getString(8));
				bean.setCollegeId(rs.getInt(9));
				bean.setCollegeName(rs.getString(10));
				bean.setCourseId(rs.getInt(11));
				bean.setCourseName(rs.getString(12));
				bean.setSubjectId(rs.getInt(13));
				bean.setSubjectName(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDateTime(rs.getTimestamp(17));
				bean.setModifiedDateTime(rs.getTimestamp(18));
				list.add(bean);

			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("database Exception .. ", e);
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in Search method of Faculty Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model search  method End");
		// System.out.println("retuen >>>>>>>>>>>>>>>"+list.size());
		return list;

	}

	/**
	 * Get List of Faculty.
	 *
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of Faculty with pagination.
	 *
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Faculty Model List method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY");
		Connection conn = null;
		ArrayList list = new ArrayList();

		// if page is greater than zero then apply pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				FacultyBean bean = new FacultyBean();
				bean.setId(rs.getInt(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setDOB(rs.getDate(5));
				bean.setQualification(rs.getString(6));
				bean.setEmailId(rs.getString(7));
				bean.setMobileNo(rs.getString(8));
				bean.setCollegeId(rs.getInt(9));
				bean.setCollegeName(rs.getString(10));
				bean.setCourseId(rs.getInt(11));
				bean.setCourseName(rs.getString(12));
				bean.setSubjectId(rs.getInt(13));
				bean.setSubjectName(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDateTime(rs.getTimestamp(17));
				bean.setModifiedDateTime(rs.getTimestamp(18));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception ......", e);
			throw new ApplicationException("Exception in list method of FacultyModel");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model List method End");
		return list;
	}

}

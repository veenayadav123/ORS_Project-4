package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.RoleBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

/**
 * JDBC Implementation of Role Model
 */

/**
 * @author Veena Yadav
 *
 */
public class RoleModel {
//	private static Logger log = Logger.getLogger(RoleModel.class);

	/**
	 * Find next PK of Role
	 *
	 */
	public Integer nextPK() throws DatabaseException {
		// log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_ROLE");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			// log.error("Database Exception..", e);
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model nextPK End");
		return pk + 1;
	}

	/**
	 * Add a Role
	 *
	 *
	 */
	public long add(RoleBean bean) throws ApplicationException, DuplicateRecordException {
		// log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;

		RoleBean duplicataRole = findByName(bean.getName());
		// Check if create Role already exist
		if (duplicataRole != null) {
			throw new DuplicateRecordException("Role already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
			System.out.println(pk + " in ModelJDBC");
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_ROLE VALUES(?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDateTime());
			pstmt.setTimestamp(7, bean.getModifiedDateTime());
			pstmt.executeUpdate();
			System.out.println("Role Add Success");
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model add End");
		return pk;
	}

	public long delete(RoleBean bean) throws ApplicationException, DuplicateRecordException {
		// log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();

			// Get auto-generated next primary key

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_ROLE WHERE ID=?");
			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();
			System.out.println("Role Delete Succ");

			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model add End");
		return pk;
	}

	/**
	 * Find User by Role
	 *
	 */

	public RoleBean findByName(String name) throws ApplicationException {
		// log.debug("Model findBy EmailId Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_ROLE WHERE NAME=?");
		RoleBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));

			}
			rs.close();
		} catch (Exception e) {
			// log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by emailId");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model findBy EmailId End");
		return bean;
	}

	/**
	 * Find User by PK
	 *
	 */

	public RoleBean findByPK(long pk) throws ApplicationException {
		// log.debug("Model findBy EmailId Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_ROLE WHERE ID=?");
		RoleBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));

			}

			rs.close();
		} catch (Exception e) {
			// log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model findBy EmailId End");
		return bean;
	}

	public void update(RoleBean bean) throws ApplicationException {
		// log.debug("Model update Started");
		Connection conn = null;
		int pk = 0;

		RoleBean duplicateRole = findByName(bean.getName());
		if (duplicateRole != null && duplicateRole.getId() != bean.getId()) {
			System.out.println("Role already exists");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_ROLE SET NAME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");

			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getDescription());
			pstmt.setString(3, bean.getCreatedBy());
			pstmt.setString(4, bean.getModifiedBy());
			pstmt.setTimestamp(5, bean.getCreatedDateTime());
			pstmt.setTimestamp(6, bean.getModifiedDateTime());

			pstmt.setLong(7, bean.getId());
			pstmt.executeUpdate();
			System.out.println("Update Role");
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			// log.error("DataBase Exception :"+e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception in updating Role ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model update End");
	}

	/**
	 * Search Role with pagination
	 *
	 *
	 */

	public List search(RoleBean bean, int pageNo, int pageSize) {
		// log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_ROLE WHERE 1=1 ");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" AND DESCRIPTION like '" + bean.getDescription() + "%'");
			}

		}
		// if page size is greater than zero then apply pagination

		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + " , " + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));
				list.add(bean);
			}
			rs.close();

		} catch (Exception e) {
			// log.error("Database Exception..", e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		// log.debug("Model search End");
		return list;
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		// log.debug("Model search Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_ROLE where 1=1 ");

		// if page size is greater than zero then apply pagination

		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append("Limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			System.out.println("check 1");
			while (rs.next()) {
				RoleBean bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));
				list.add(bean);
			}
			rs.close();

		} catch (Exception e) {
			// log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in list role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		// log.debug("Model search End");
		return list;
	}

	public List list() throws ApplicationException {
		return list(0, 0);

	}

}

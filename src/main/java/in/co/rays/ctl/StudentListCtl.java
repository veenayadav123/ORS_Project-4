package in.co.rays.ctl;

import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import in.co.rays.bean.BaseBean;
import in.co.rays.bean.CollegeBean;
import in.co.rays.bean.StudentBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.model.CollegeModel;
import in.co.rays.model.StudentModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

/**
 * Servlet implementation class StudentListCtl
 */

/**
 * @author Veena Yadav
 *
 */
@ WebServlet(name="StudentListCtl",urlPatterns={"/ctl/StudentListCtl"})
public class StudentListCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(StudentListCtl.class);
	
	protected void preload(HttpServletRequest request) {
		StudentModel sm = new StudentModel();
		try {
			List rlist =  sm.list();
			request.setAttribute("name", rlist);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        StudentBean bean = new StudentBean();

        bean.setId(DataUtility.getLong(request.getParameter("fname")));
        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
        bean.setEmail(DataUtility.getString(request.getParameter("email")));

        return bean;
    }

    /**
     * Contains Display logics
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
   //     log.debug("StudentListCtl doGet Start");
        List list = null;

        int pageNo = 1;

        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        StudentBean bean = (StudentBean) populateBean(request);

        String op = DataUtility.getString(request.getParameter("operation"));

        StudentModel model = new StudentModel();
        try {
            list = model.search(bean, pageNo, pageSize);
            ServletUtility.setList(list, request);
            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found ", request);
            }
            ServletUtility.setList(list, request);

            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
      //      log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }
    //    log.debug("StudentListCtl doGet End");
    }

    /**
     * Contains Submit logics
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    //    log.debug("StudentListCtl doPost Start");
    	log.debug("StudentListCtl doPost Start");

		List list = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");
		StudentModel model = new StudentModel();
		
		StudentBean bean = (StudentBean) populateBean(request);
		ServletUtility.setBean(bean, request);
		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
			pageNo--;
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				StudentBean deletebean = new StudentBean();
				// UserBean deletebean = new UserBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);;
					} catch (ApplicationException e) {
						ServletUtility.handleException(e, request, response);
						return;
					}
					ServletUtility.setSuccessMessage("Student Data Successfully Deleted", request);
				}
			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}
		try {
			list = model.search(bean, pageNo, pageSize);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		// ServletUtility.setList(list, request);

		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No record found ", request);
		}
		ServletUtility.setList(list, request);

		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
		log.debug("CollegeListCtl doPost End");
	}

    @Override
    protected String getView() {
        return ORSView.STUDENT_LIST_VIEW;
    }
}

 



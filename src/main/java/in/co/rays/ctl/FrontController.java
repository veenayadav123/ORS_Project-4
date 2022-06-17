package in.co.rays.ctl;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.util.ServletUtility;

/**
 * @author Veena Yadav
 *
 */
@WebFilter(filterName = "FrontCtl", urlPatterns = { "/ctl/*", "/doc/*" })
public class FrontController implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		HttpSession session = request.getSession();

		if (session.getAttribute("user") == null) {
			//request.setAttribute("error", "Your session has been expired. Please re-Login.");
			ServletUtility.setErrorMessage(" Your Session has been Expired... Please Login Again", request);

			String str = request.getRequestURI();

			request.setAttribute("uri", str);

			ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);

			return;

		} else {
			chain.doFilter(req, resp);
		}
	}

	public void init(FilterConfig conf) throws ServletException {
	}

}

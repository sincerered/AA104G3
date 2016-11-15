package filters;

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

import com.member.model.MemberVO;
import com.store.model.StoreVO;

/**
 * Servlet Filter implementation class StoreLoginFilter
 */
public class StoreLoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public StoreLoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		HttpSession session = req.getSession();
		StoreVO storeVO = (StoreVO)session.getAttribute("storeVO");
		String action = req.getParameter("action");
		if ("getOne_for_display".equals(action)) {
			
		} else if ("login".equals(action)) {
			
		} else if ("register".equals(action)) {
			
		} else if ("get_stopic".equals(action)) {
			
		} else if ("confirm_stoid".equals(action)) {
			
		} else if (storeVO == null) {
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			if (memberVO == null) {
				session.setAttribute("location", req.getRequestURI());
				System.out.println(req.getRequestURI());
				res.sendRedirect(req.getContextPath() + "/front-end/login.jsp");
				return;
			} else {
				res.sendRedirect(req.getContextPath() + "/front-end/index.jsp");
				return;
			}
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

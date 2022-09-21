package filters;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class FilterCors implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest Request, ServletResponse Response, FilterChain Chain)
			throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) Response;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "false");
        response.setHeader("Access-Control-Allow-Methods", "*");
//        response.setHeader("HttpOnly", "false");
//        response.setHeader("SameSite", "Lax");
//        response.setHeader("Access-Control-Expose-Headers", "*");

        
		Chain.doFilter(Request, Response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub	
	}

}

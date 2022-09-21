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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@WebFilter(urlPatterns = {"/api/up/list", "/api/up/show/*", "/api/up/delete/*"})
public class FilterImageAuth implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest Request, ServletResponse Response, FilterChain Chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) Request;
		String token = "";
		token = request.getHeader("token");
		
		if(token != null && !token.isEmpty()) {
			try {
				Claims claims = Jwts.parserBuilder().setSigningKey("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9".getBytes("UTF-8")).build().parseClaimsJws(token).getBody();
				Chain.doFilter(Request, Response);

			} catch (Exception e) {

//				((HttpServletResponse) Response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Não autorizado ou token expirado.");
				HttpServletResponse resp = (HttpServletResponse) Response;
				resp.addHeader("error","Não autorizado ou token expirado.");
				resp.setHeader("Access-Control-Expose-Headers", "error");
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}else{
			HttpServletResponse resp = (HttpServletResponse) Response;
			resp.addHeader("error","Não autorizado ou token expirado.");
			resp.setHeader("Access-Control-Expose-Headers", "error");
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
//	if(Request.getContentLength() > 0){
//	if(Request.getParameter("tt").equals("hue")) {


}

package view;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import service.DatabaseConnection;


@WebFilter("/home/*")
public class Authendication extends HttpFilter implements Filter {
    
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		
		Cookie []cookie = ((HttpServletRequest)request).getCookies();
//		System.out.println(this.getFilterName());
		if(cookie != null) {
		for(int i =0; i < cookie.length; i++) {
			
			if(cookie[i].getName().equals("sessionId")) {
				String value = cookie[i].getValue();
				try {
					PreparedStatement ps = DatabaseConnection.con.prepareStatement("select s.*, e.* from sessionTable s, Employee e where sessionId = ? and s.emp_id = e.emp_id");
					ps.setString(1, value);
					ResultSet rs = ps.executeQuery();
					if(rs.next()) {
						int empId = rs.getInt("emp_id");
						request.setAttribute("empId", empId);
						request.setAttribute("role", rs.getString("emp_role"));
						request.setAttribute("branchId", rs.getString("branch_id"));
						chain.doFilter(request, response);
						break;
					}
					else {
						JSONObject json = new JSONObject();
						json.put("statusCode", 404);
						json.put("message", "User not Found");
						response.getWriter().append(json.toString());
					}
				}
				catch(Exception e) {
					e.printStackTrace();
					JSONObject json = new JSONObject();
					json.put("statusCode", 500);
					json.put("message", "Fatel error Plz contact ADMIN");
					response.getWriter().append(json.toString());
				}
			}
			
		}
		}
		else {
			JSONObject json = new JSONObject();
			json.put("statusCode", 404);
			json.put("message", "USer not Found");
			response.getWriter().append(json.toString());
		}
	}

}

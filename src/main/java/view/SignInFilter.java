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
import javax.servlet.http.HttpFilter;

import org.json.simple.JSONObject;

import service.DatabaseConnection;

@WebFilter("/SignIn")
public class SignInFilter extends HttpFilter implements Filter {
    
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		System.out.println(this.getFilterName());
		
		int emp_id = Integer.valueOf(request.getParameter("emp_id"));
		String password = request.getParameter("password");
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from Employee where emp_id = ? and status = \"ACTIVE\"");
			ps.setInt(1, emp_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				if(password.equals(rs.getString("emp_password"))) {
					request.setAttribute("role", rs.getString("emp_role"));
					request.setAttribute("userName", rs.getString("emp_name"));
					chain.doFilter(request, response);
				}
				else {
					JSONObject json = new JSONObject();
					json.put("statusCode", 400);
					json.put("message", "Incorrect Password");
					response.getWriter().append(json.toString());
				}
			}
			else {
				JSONObject json = new JSONObject();
				json.put("statusCode", 404);
				json.put("message", "User Not Found");
				response.getWriter().append(json.toString());
			}
		}
		catch(Exception e) {
			JSONObject json = new JSONObject();
			json.put("statusCode", 500);
			json.put("message", "Fatel error Plz contact Admin");
			response.getWriter().append(json.toString());
		}
		
	}

}

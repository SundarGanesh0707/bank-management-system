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

@WebFilter("/home/AddBranch")
public class AddBranchFIlter extends HttpFilter implements Filter {
    
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		System.out.println(this.getFilterName());
		
		String IFSCCode = request.getParameter("IFSCCode");
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from Branch where IFSC_code = ?");
			ps.setString(1, IFSCCode);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				chain.doFilter(request, response);
			}
			else {
				JSONObject json = new JSONObject();
				json.put("statusCode", 400);
				json.put("message", "Sorry, This IFSCCode Already existed");
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

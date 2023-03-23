package controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import service.DatabaseConnection;

@WebServlet("/IsCookieAlive")
public class IsCookieAlive extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println(this.getServletName());
		JSONObject json = new JSONObject();
		Cookie []cookie = request.getCookies();
		
		boolean isCookieAlive = false;
		
		if(cookie != null) {
			for(int i =0; i < cookie.length; i++) {
				isCookieAlive = true;
				if(cookie[i].getName().equals("sessionId")) {
					try {
						String sql = "select * from Employee e, sessionTable s where s.emp_id = e.emp_id and s.sessionId = ?";
						PreparedStatement ps = DatabaseConnection.con.prepareStatement(sql);
						ps.setString(1, cookie[i].getValue());
						ResultSet rs = ps.executeQuery();
						if(rs.next()) {
							json.put("statusCode", 200);
							json.put("message", "Welcome "+rs.getString("emp_name"));
							json.put("name", rs.getString("emp_name"));
							json.put("role", rs.getString("emp_role"));
						}
					}
					catch(Exception e) {
						e.printStackTrace();
						json.put("statusCode", 500);
						json.put("message", "Fatel error Plz contact ADMIN");
					}
				}
			}
		}
		else {
			json.put("statusCode", 404);
		}
		
		if(!isCookieAlive) {
			json.put("statusCode", 404);
		}
		
		response.getWriter().append(json.toString());
		
	}

}

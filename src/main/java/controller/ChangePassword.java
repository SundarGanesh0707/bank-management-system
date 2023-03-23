package controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;


import model.Customer;
import service.DBService;
import service.DatabaseConnection;

@WebServlet("/home/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String regex = "^(?=.*[0-9])"
	            + "(?=.*[a-z])(?=.*[A-Z])"
	            + "(?=.*[@#$%^&+=])"
	            + "(?=\\S+$).{8,20}$";
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		int empId = (int)request.getAttribute("empId");

		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from Employee where emp_id = ?");
			ps.setInt(1, empId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			if(rs.getString("emp_password").equals(oldPassword)) {
				if(newPassword.matches(regex)) {
					DBService.updateEmployeePassword(newPassword, empId);
					JSONObject json = new JSONObject();
					json.put("statusCode", 200);
					json.put("message", "SuccessFully password Changed");
					response.getWriter().append(json.toString());
				}
				else {
					JSONObject json = new JSONObject();
					json.put("statusCode", 400);
					json.put("message", "Plz create the strong password");
					response.getWriter().append(json.toString());
				}
			}
			else {
				JSONObject json = new JSONObject();
				json.put("statusCode", 400);
				json.put("message", "Plz Check Your Old Password");
				response.getWriter().append(json.toString());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}

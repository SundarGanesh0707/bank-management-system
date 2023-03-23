package controller;

import java.io.IOException;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import service.DatabaseConnection;

@WebServlet("/home/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(this.getServletName());
		try {
			int customerId = (int)request.getAttribute("empId");
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("delete from sessionTable where emp_id = ?");
			ps.setInt(1, customerId);
			ps.executeUpdate();
			JSONObject json = new JSONObject();
			json.put("statusCode", 200);
			json.put("message", "Thank You come again");
			response.getWriter().append(json.toString());
		}
		catch(Exception e) {
			JSONObject json = new JSONObject();
			json.put("statusCode", 500);
			json.put("message", "Fatel error Plz contact ADMIN");
			response.getWriter().append(json.toString());
		}
	}

}

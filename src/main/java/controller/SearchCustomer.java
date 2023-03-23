package controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import service.DatabaseConnection;

@WebServlet("/home/SearchCustomer")
public class SearchCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int customerId = Integer.valueOf(request.getParameter("customerId"));
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from Customer where customer_id = ?");
			ps.setInt(1, customerId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("statusCode", 200);
				json.put("customerId", rs.getInt("customer_id"));
				json.put("customerName", rs.getString("customer_name"));
				json.put("dob", new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("dob")));
				json.put("phoneNo", rs.getString("phone_no"));
				json.put("address", rs.getString("address"));
				json.put("aadharNo", rs.getString("aadhar_no"));
				json.put("gender", rs.getString("gender"));
				response.getWriter().append(json.toString());
			}
			else {
				JSONObject json = new JSONObject();
				json.put("statusCode", 404);
				json.put("message", "Sorry, Employee Not Found");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}

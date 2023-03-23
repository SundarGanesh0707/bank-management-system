package controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import service.DatabaseConnection;

@WebServlet("/home/ChangeCustomerProfile")
public class ChangeCustomerProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@SuppressWarnings({ "unchecked", "resource" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String phoneNo = request.getParameter("phoneNo");
		String address = request.getParameter("address");
		String change = request.getParameter("change");
		int customerId = Integer.valueOf(request.getParameter("customerId"));
		PreparedStatement ps = null;
		
		try {
			ps = DatabaseConnection.con.prepareStatement("select * from Customer where customer_id = ?");
			ps.setInt(1, customerId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				ps.clearParameters();
				if(change.equals("both")) {
					if(!phoneNo.matches("^\\d{10}$")) {
						JSONObject json = new JSONObject();
						json.put("statusCode", 400);
						json.put("message", "Invaild PhoneNo Plz Check");
						response.getWriter().append(json.toString());
					}
					else {
						updateCustomer(phoneNo, address, customerId);
						JSONObject json = new JSONObject();
						json.put("statusCode", 200);
						json.put("message", "Successfully Profile Changed");
						response.getWriter().append(json.toString());
					}
					
				}
				else if(change.equals("phoneNo")) {
					if(!phoneNo.matches("^\\d{10}$")) {
						JSONObject json = new JSONObject();
						json.put("statusCode", 400);
						json.put("message", "Invaild PhoneNo Plz Check");
						response.getWriter().append(json.toString());
					}
					else {
						updateCustomer(phoneNo, rs.getString("address"), customerId);
						JSONObject json = new JSONObject();
						json.put("statusCode", 200);
						json.put("message", "Successfully Profile Changed");
						response.getWriter().append(json.toString());
					}
				}
				else {
					updateCustomer(rs.getString("phone_no"), address, customerId);
					JSONObject json = new JSONObject();
					json.put("statusCode", 200);
					json.put("message", "Successfully Profile Changed");
					response.getWriter().append(json.toString());
				}
			}
			else {
				JSONObject json = new JSONObject();
				json.put("statusCode", 404);
				json.put("message", "Customer Not Found");
				response.getWriter().append(json.toString());
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public void updateCustomer(String phoneNo, String address, int customerId) throws Exception {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("update Customer set phone_no = ?, address = ? where customer_id = ?");
			ps.setString(1, phoneNo);
			ps.setString(2, address);
			ps.setInt(3, customerId);
			ps.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		
	}

}

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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import service.DatabaseConnection;

@WebServlet("/home/ViewCustomer")
public class ViewCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("statusCode", 200);
			jsonArray.add(jsonObject);
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from Customer where status = \"ACTIVE\"");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("customerId", rs.getInt("customer_id"));
				json.put("customerName", rs.getString("customer_name"));
				json.put("dob", new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("dob")));
				json.put("phoneNo", rs.getString("phone_no"));
				json.put("address", rs.getString("address"));
				json.put("aadharNo", rs.getString("aadhar_no"));
				json.put("gender", rs.getString("gender"));
				jsonArray.add(json);
			}
			System.out.println(jsonArray);
			if(jsonArray.size() > 1) {
				response.getWriter().append(jsonArray.toString());
			}
			else {
				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}

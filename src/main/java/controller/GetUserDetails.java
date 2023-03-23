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

@WebServlet("/home/GetUserDetails")
public class GetUserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int empId = (int)request.getAttribute("empId");
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from Employee where emp_id = ?");
			ps.setInt(1, empId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("statusCode", 200);
				json.put("name", rs.getString("emp_name"));
				json.put("dob", new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("emp_dob")));
				json.put("empId", rs.getInt("emp_id"));
				json.put("phoneNo", rs.getString("emp_phone_no"));
				json.put("address", rs.getString("emp_address"));
				json.put("branchId", rs.getString("branch_id"));
				json.put("role", rs.getString("emp_role").toString()); 
//				System.out.println(json.toString());
				response.getWriter().append(json.toString());
			}
		}
		catch(Exception e) {
			
		}
		
	}

}

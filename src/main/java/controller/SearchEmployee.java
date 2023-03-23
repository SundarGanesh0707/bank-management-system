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

@WebServlet("/home/SearchEmployee")
public class SearchEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int empId = Integer.valueOf(request.getParameter("empId"));
		try {
			String role = (String)request.getAttribute("role");
			String branchId = (String)request.getAttribute("branchId");
			PreparedStatement ps = null;
			if(role.equals("MANAGER")) {
				ps = DatabaseConnection.con.prepareStatement("select * from Employee where status = \"ACTIVE\" and emp_id = ? and branch_id = ?");
				ps.setInt(1, empId);
				ps.setString(2, branchId);
			}
			else {
				ps = DatabaseConnection.con.prepareStatement("select * from Employee where status = \"ACTIVE\" and emp_id = ?");
				ps.setInt(1, empId);
			}
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("statusCode", 200);
				json.put("empId", rs.getInt("emp_id"));
				json.put("empName", rs.getString("emp_name"));
				json.put("dob", new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("emp_dob")));
				json.put("phoneNo", rs.getString("emp_phone_no"));
				json.put("address", rs.getString("emp_address"));
				json.put("branchId", rs.getString("branch_id"));
				json.put("role", rs.getString("emp_role"));
				response.getWriter().append(json.toString());
			}
			else {
				JSONObject json = new JSONObject();
				json.put("statusCode", 404);
				json.put("message", "Sorry, Employee Not Found");
				response.getWriter().append(json.toString());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}

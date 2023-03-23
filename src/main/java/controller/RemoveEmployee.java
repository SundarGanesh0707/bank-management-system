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

import service.DBService;
import service.DatabaseConnection;

@WebServlet("/home/RemoveEmployee")
public class RemoveEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			int empId = Integer.valueOf(request.getParameter("empId"));
			String resson = request.getParameter("resson");
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from Employee where emp_id = ? and status = \"ACTIVE\"");
			ps.setInt(1, empId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String branch = rs.getString("branch_id");
					PreparedStatement ps2 = DatabaseConnection.con.prepareStatement("select count(*) from Employee where emp_role = ? and branch_id = ?");
					ps2.setString(1, rs.getString("emp_role"));
					ps2.setString(2, branch);
					ResultSet rs2 = ps2.executeQuery();
					rs2.next();
					int count = rs2.getInt(1); 
					System.out.println(count);
					if(count==1) {
						JSONObject json = new JSONObject();
						json.put("statusCode", 400);
						json.put("message", "Sorry, You cannot Remove he is One Employee in this Branch");
						response.getWriter().append(json.toString());
					}
					else{
						DBService.changeEmpAndCustomerStatus(empId, "update Employee set status = ? where emp_id = ?", "INACTIVE");
						DBService.addCustomerAndEmployeeResson(empId, "insert into EmployeeRemoved values(?, ?, ?)", resson);
						JSONObject json = new JSONObject();
						json.put("statusCode", 200);
						json.put("message", "Successfully Remove Employee");
						response.getWriter().append(json.toString());
					}
			}
			else {
				JSONObject json = new JSONObject();
				json.put("statusCode", 404);
				json.put("message", "Employee Not Found");
				response.getWriter().append(json.toString());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			JSONObject json = new JSONObject();
			json.put("statusCode", 500);
			json.put("message", "Fatel error Plz contact Admin");
			response.getWriter().append(json.toString());
		}
		
	}

}

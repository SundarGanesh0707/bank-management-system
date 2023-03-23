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

@WebServlet("/home/AddExcitedEmployee")
public class AddExcitedEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			int empId = Integer.valueOf(request.getParameter("emp_id"));
			String branch = request.getParameter("IFSCCode");
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from Employee where emp_id = ? and status = \"ACTIVE\"");
			ps.setInt(1, empId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				if(rs.getString("branch_id").equals(branch)) {
					JSONObject json = new JSONObject();
					json.put("statusCode", 400);
					json.put("message", "Sorry, He is Already In this Branch");
					response.getWriter().append(json.toString());
				}
				else {
					PreparedStatement ps2 = DatabaseConnection.con.prepareStatement("select count(*) from Employee where emp_role = ? and branch_id = ?");
					ps2.setString(1, rs.getString("emp_role"));
					ps2.setString(2, branch);
					ResultSet rs2 = ps2.executeQuery();
					rs2.next();
					if(rs2.getInt(1)==1) {
						JSONObject json = new JSONObject();
						json.put("statusCode", 400);
						json.put("message", "Sorry, You cannot Move to Another Branch");
						response.getWriter().append(json.toString());
					}
					else{
						DBService.changeBranch(empId, branch);
						JSONObject json = new JSONObject();
						json.put("statusCode", 200);
						json.put("message", "Successfully Branch Changed");
						response.getWriter().append(json.toString());
					}
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

package controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import service.DatabaseConnection;

@WebServlet("/home/GetEmployeesAccountDetails")
public class GetEmployeesAccountDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("statusCode", 200);
			jsonArray.add(jsonObject);

				PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from Account where status = \"ACTIVE\"");
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					JSONObject json = new JSONObject();
					json.put("accountNo", rs.getString("account_no"));
					json.put("IFSCCode", rs.getString("branch_id"));
					json.put("balance", rs.getString("balance"));
					jsonArray.add(json);
				}
				
				if(jsonArray.size() == 1) {
					jsonArray.remove(0);
					JSONObject json = new JSONObject();
					json.put("statusCode", 400);
					json.put("message", "No Account Process");
				}
				
				response.getWriter().append(jsonArray.toString());
			
			
			
		}
		catch(Exception e) {
			
		}
		
	}

}

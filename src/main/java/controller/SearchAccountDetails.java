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

import service.DatabaseConnection;

@WebServlet("/home/SearchAccountDetails")
public class SearchAccountDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String accountNo = request.getParameter("accountNo");
			String role = (String)request.getAttribute("role");
			String branchId = (String)request.getAttribute("branchId");
			PreparedStatement ps = null;
			ps = DatabaseConnection.con.prepareStatement("select * from Account where account_no = ? and status = \"ACTIVE\"");
			ps.setString(1, accountNo);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
					JSONObject json = new JSONObject();
					json.put("statusCode", 200);
					json.put("accountNo", rs.getString("account_no"));
					json.put("IFSCCode", rs.getString("branch_id"));
					json.put("balance", rs.getString("balance"));
					response.getWriter().append(json.toString());
			}
			else {
				JSONObject json = new JSONObject();
				json.put("statusCode", 404);
				json.put("message", "Sorry, Account Not Found");
				response.getWriter().append(json.toString());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}

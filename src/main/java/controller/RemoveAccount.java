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

@WebServlet("/home/RemoveAccount")
public class RemoveAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String accountNo = request.getParameter("accountNo");
			String resson = request.getParameter("resson");
			
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from Account where account_no = ? and status = \"ACTIVE\"");
			ps.setString(1, accountNo);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				DBService.changeAccountStatus(accountNo, "update Account set status = ? where account_no = ?", "INACTIVE");
				DBService.addAccountResson(accountNo, "insert into AccountRemoved values(?, ?, ?)", resson);
				JSONObject json = new JSONObject();
				json.put("statusCode", 200);
				json.put("message", "Successfully removed Account");
				response.getWriter().append(json.toString());
			}
			else {
				JSONObject json = new JSONObject();
				json.put("statusCode", 404);
				json.put("message", "Account Not Found");
				response.getWriter().append(json.toString());
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}

package controller;

import java.io.IOException;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import service.DatabaseConnection;

@WebServlet("/home/AddBranch")
public class AddBranch extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println(this.getServletName());
		String branchName = request.getParameter("branchName");
		String IFSCCode = request.getParameter("IFSCCode");
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("insert into Branch values(?, ?)");
			ps.setString(1, branchName);
			ps.setString(2, IFSCCode);
			ps.executeUpdate();
			
			JSONObject json = new JSONObject();
			json.put("statusCode", 200);
			json.put("message", "SuccessFully added Branch");
			response.getWriter().append(json.toString());
		}
		catch(Exception e) {
			JSONObject json = new JSONObject();
			json.put("statusCode", 500);
			json.put("message", "Fatel error Plz contact Admin");
			response.getWriter().append(json.toString());
		}
		
	}

}

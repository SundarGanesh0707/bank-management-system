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

@WebServlet("/home/BranchDetails")
public class BranchDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(this.getServletName());
		try {
			JSONArray jsonArray = new JSONArray();
			String sql = "select * from Branch order by branchName";
			PreparedStatement ps = DatabaseConnection.con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("key", rs.getString(1));
				json.put("value", rs.getString(2));
				jsonArray.add(json);
			}
			response.getWriter().append(jsonArray.toString());
		}
		catch(Exception e) {
			
		}
	}

}

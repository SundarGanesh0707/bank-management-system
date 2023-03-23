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

@WebServlet("/home/SearchBranchTransaction")
public class SearchBranchTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("statusCode", 200);
			jsonArray.add(jsonObject);
			String IFSCCode = request.getParameter("IFSCCode");
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select r.* from Receipt r, Account a where r.from_account_no = a.account_no and a.branch_id = ? order by date desc");
			ps.setString(1, IFSCCode);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("date", new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("date")));
				json.put("fromAccountNo", rs.getString("from_account_no"));
				if(rs.getString("to_account_no") == null) {
					json.put("toAccountNo", "-");
				}
				else {
					json.put("toAccountNo", rs.getString("to_account_no"));
				}
				json.put("transferType", rs.getString("Transfer_type"));
				json.put("amount", rs.getDouble("amount"));
				json.put("balance", rs.getDouble("balance"));
				jsonArray.add(json);
			}
			if(jsonArray.size() == 1) {
				jsonArray.remove(0);
				JSONObject jsonObject1 = new JSONObject();
				jsonObject1.put("statusCode", 404);
				jsonObject1.put("message", "No Transaction Process");
				jsonArray.add(jsonObject1);
			}
			
			response.getWriter().append(jsonArray.toString());
		}
		catch(Exception e) {
			
		}
		
	}

}

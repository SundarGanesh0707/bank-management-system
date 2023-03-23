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

import service.DBService;
import service.DatabaseConnection;

@WebServlet("/home/SearchCustomerReceipt")
public class SearchCustomerReceipt extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int customerId = 0;
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String search = request.getParameter("search");
		System.out.println(search);
		String branchId = (String)request.getAttribute("branchId");
		boolean isCustomerFound = true;
		
		if(!(request.getParameter("customerId").equals(""))) {
			customerId = Integer.valueOf(request.getParameter("customerId"));
			ResultSet rs = DBService.getCustomerByCustomerId(customerId);
			if(rs != null) {
				isCustomerFound = true;
			}
			else {
				isCustomerFound = false;
				JSONArray jsonA = new JSONArray();
				JSONObject json = new JSONObject();
				json.put("statusCode", 404);
				json.put("message", "Customer Not Found");
				jsonA.add(json);
				response.getWriter().append(jsonA.toString());
			}
		}
		System.out.println(isCustomerFound);
		if(isCustomerFound) {
			try {
				JSONArray jsonArray = new JSONArray();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("statusCode", 200);
				jsonArray.add(jsonObject);
				PreparedStatement ps = null;
				ResultSet rs = null;
				switch(search) {
				
				case "all" :
					ps = DatabaseConnection.con.prepareStatement("select r.* from Receipt r, Account a where r.from_account_no = a.account_no and r.date >= ? and r.date <= ? and a.branch_id = ? and a.customer_id = ? order by date desc");
					ps.setDate(1, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate).getTime()));
					ps.setDate(2, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(toDate).getTime()));
					ps.setString(3, branchId);
					ps.setInt(4, customerId);
					rs = ps.executeQuery();
				break;	
				
				case "fromAndTo" :
					ps = DatabaseConnection.con.prepareStatement("select r.* from Receipt r, Account a where r.from_account_no = a.account_no and r.date >= ? and r.date <= ? and a.branch_id = ? order by date desc");
					ps.setDate(1, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate).getTime()));
					ps.setDate(2, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(toDate).getTime()));
					ps.setString(3, branchId);
					rs = ps.executeQuery();
				break;	
				
				case "customerId" : 
					ps = DatabaseConnection.con.prepareStatement("select r.* from Receipt r, Account a where r.from_account_no = a.account_no and a.branch_id = ? and a.customer_id = ? order by date desc");
					ps.setString(1, branchId);
					ps.setInt(2, customerId);
					rs = ps.executeQuery();
				break;
				}
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
				System.out.println(jsonArray);
				response.getWriter().append(jsonArray.toString());
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
			
	}

}

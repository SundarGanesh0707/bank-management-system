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

@WebServlet("/home/ViewLoan")
public class ViewLoan extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("statusCode", 200);
			jsonArray.add(jsonObject);
			String role = (String)request.getAttribute("role");
			String branchId = (String)request.getAttribute("branchId");
			System.out.println(branchId);
			PreparedStatement ps = null;
			if(role.equals("MANAGER")) {
				ps = DatabaseConnection.con.prepareStatement("select l.*, c.customer_name from Loan l, Account a, Customer c where a.branch_id = ? and l.customer_id = c.customer_id order by applied_date desc");
				ps.setString(1, branchId);
			}
			else {
				ps = DatabaseConnection.con.prepareStatement("select l.*, c.customer_name from Loan l, Customer c where l.customer_id = c.customer_id order by applied_date desc");
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("customerId", rs.getInt("customer_id"));
				json.put("loanId", rs.getInt("loan_id"));
				json.put("customerName", rs.getString("customer_name"));
				json.put("amount", rs.getDouble("total_amount"));
				json.put("date", new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("applied_date")));
				if(rs.getString("loan_status").equals("SENT_FOR_REQUEST")) {
					json.put("balancePayment", "-");
					json.put("loanStatus", "REQUEST");
				}
				else {
					json.put("balancePayment", rs.getDouble("balance_amount"));
					json.put("loanStatus", rs.getString("loan_status"));
				}
				json.put("loanType", rs.getString("loan_type"));
				jsonArray.add(json);
			}
			
			if(jsonArray.size() == 1) {
				jsonArray.remove(0);
				JSONObject jsonObject1 = new JSONObject();
				jsonObject1.put("statusCode", 404);
				jsonObject1.put("message", "No Loan Applied");
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

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

import org.json.simple.JSONObject;

import service.DatabaseConnection;

@WebServlet("/home/SearchLoan")
public class SearchLoan extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			int loanId = Integer.valueOf(request.getParameter("loanId"));
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select l.*, c.customer_name from Loan l, Customer c where loan_id = ? and l.customer_id = c.customer_id");
			ps.setInt(1, loanId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("statusCode", 200);
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
				response.getWriter().append(json.toString());
			}
			else {
				JSONObject json = new JSONObject();
				json.put("statusCode", 400);
				json.put("message", "Loan Not Found");
				response.getWriter().append(json.toString());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}

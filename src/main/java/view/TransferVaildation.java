package view;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

import org.json.simple.JSONObject;

import model.TransferType;
import service.DatabaseConnection;

@WebFilter("/home/Transfer")
public class TransferVaildation extends HttpFilter implements Filter {
    
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		int customerId = Integer.valueOf(request.getParameter("customerId"));
		String fromAccountNo = request.getParameter("fromAccountNo");
		String toAccountNo = request.getParameter("toAccountNo");
		double amount = Double.valueOf(request.getParameter("amount"));
		TransferType type = TransferType.valueOf(request.getParameter("type"));
	
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from Account where account_no = ? and customer_id = ? and status = \"ACTIVE\"");
			ps.setString(1, fromAccountNo);
			ps.setInt(2, customerId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				ps.clearParameters();
				ps = DatabaseConnection.con.prepareStatement("select * from Account where account_no = ? and status = \"ACTIVE\"");
				ps.setString(1, toAccountNo);
				ResultSet rs3 = ps.executeQuery();
				if(rs3.next()) {
					ps.clearParameters();
					ps = DatabaseConnection.con.prepareStatement("select * from Receipt where from_account_no = ? and Transfer_type = ? and date = ?");
					ps.setString(1, fromAccountNo);
					ps.setString(2, type.toString());
					ps.setDate(3, new java.sql.Date(new Date().getTime()));
					ResultSet rs2 = ps.executeQuery();
					double totalAmount = 0;
					while(rs2.next()) {
						totalAmount += rs2.getDouble("amount");
					}
					if((totalAmount + amount) <= 1000000) {
						if(rs.getDouble("balance") >= amount) {
							request.setAttribute("fromAccountBalance", rs.getDouble("balance"));
							request.setAttribute("toAccountBalance", rs3.getDouble("balance"));
							chain.doFilter(request, response);
						}
						else {
							JSONObject json = new JSONObject();
							json.put("statusCode", 400);
							json.put("message", "Sorry your balance less than "+amount);
							response.getWriter().append(json.toString());
						}
					}
					else {
						JSONObject json = new JSONObject();
						json.put("statusCode", 400);
						json.put("message", "Transfer Limit Reached");
						response.getWriter().append(json.toString());
					}
				}
				else{
					JSONObject json = new JSONObject();
					json.put("statusCode", 404);
					json.put("message", "Receiver Account Not Found");
					response.getWriter().append(json.toString());
				}
			}
			else {
				JSONObject json = new JSONObject();
				json.put("statusCode", 404);
				json.put("message", "From Account Not Found");
				response.getWriter().append(json.toString());
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}

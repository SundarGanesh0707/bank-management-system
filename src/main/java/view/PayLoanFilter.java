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

import model.Account;
import model.LoanStatus;
import service.DBService;
import service.DatabaseConnection;

@WebFilter("/home/PayLoan")
public class PayLoanFilter extends HttpFilter implements Filter {
    
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		String accountNo = request.getParameter("accountNo");
		int loanId = Integer.valueOf(request.getParameter("loanId"));
		double amount = Double.valueOf(request.getParameter("amount"));
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from Loan where loan_id = ?");
			ps.setInt(1, loanId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				LoanStatus loanStatus = LoanStatus.valueOf(rs.getString("loan_status"));
				if(loanStatus.equals(LoanStatus.ACCEPTED)) {
					ps.clearParameters();
					ps = DatabaseConnection.con.prepareStatement("select * from LoanLastPay where loan_id = ?");
					ps.setInt(1, loanId);
					ResultSet rs2 = ps.executeQuery();
					Date lastPay = new java.util.Date(rs2.getDate(2).getTime());
					long diff = DBService.getMonthsDifference(lastPay, new Date());
					System.out.println("Month diff : "+diff);
					if(diff>1) {
						ps.clearParameters();
						ps = DatabaseConnection.con.prepareStatement("select * from Account where accountNo = ?");
						ps.setString(1, accountNo);
						ResultSet rs3 = ps.executeQuery();
						if(rs3.next()) {
							double balance = rs3.getDouble("balance");
							if(balance >= amount) {
								chain.doFilter(request, response);
							}
							else {
								JSONObject json = new JSONObject();
								json.put("statusCode", 400);
								json.put("message", "Plz check The Account Balance");
								response.getWriter().append(json.toString());
							}
						}
						else {
							JSONObject json = new JSONObject();
							json.put("statusCode", 404);
							json.put("message", "Account Not Found");
							response.getWriter().append(json.toString());
						}
					}
					else {
						JSONObject json = new JSONObject();
						json.put("statusCode", 400);
						json.put("message", "Sorry, You already Paid This Month");
						response.getWriter().append(json.toString());
					}
				
				}
				else if(loanStatus.equals(LoanStatus.ACCEPTED)) {
					JSONObject json = new JSONObject();
					json.put("statusCode", 200);
					json.put("message", "You Already Finished This Loan");
					response.getWriter().append(json.toString());
				}
				else {
					JSONObject json = new JSONObject();
					json.put("statusCode", 200);
					json.put("message", "Plz Check Your Loan Status");
					response.getWriter().append(json.toString());
				}
			}
			else {
				JSONObject json = new JSONObject();
				json.put("statusCode", 404);
				json.put("message", "Loan Not Found");
				response.getWriter().append(json.toString());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}

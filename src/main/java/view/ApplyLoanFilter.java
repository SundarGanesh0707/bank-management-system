package view;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import service.DBService;
import service.DatabaseConnection;

@WebFilter("/home/ApplyLoan")
public class ApplyLoanFilter extends HttpFilter implements Filter {
     
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		try {
			int customerId = Integer.valueOf(request.getParameter("customerId"));
			String accountNo = request.getParameter("accountNo");
			String loanType = request.getParameter("loanType");
			double interestRate = Double.valueOf(request.getParameter("interestRate"));
			double amount = Double.valueOf(request.getParameter("amount"));
			int noOfYears = Integer.valueOf(request.getParameter("noOfYears"));
			String branchId = (String)request.getAttribute("branchId");
			
			ResultSet rs2 = DBService.getCustomerByCustomerId(customerId);
			
			if(rs2 != null) {
				JSONArray jsona = DBService.checkVaildAccountNo(accountNo, branchId);
				if(jsona == null) {
					PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from Loan where customer_id = ? and (loan_status = \"SENT_FOR_REQUEST\" or loan_status = \"ACCEPTED\")");
					ps.setInt(1, customerId);
					ResultSet rs = ps.executeQuery();
					if(!rs.next()) {
						chain.doFilter(request, response);
					}
					else {
						JSONObject json = new JSONObject();
						json.put("statusCode", 400);
						json.put("message", "Sorry, you already have a Loan");
						response.getWriter().append(json.toString());
					}
				}
				else {
					response.getWriter().append(jsona.get(0).toString());
				}
				
			}
			else {
				JSONObject json = new JSONObject();
				json.put("statusCode", 400);
				json.put("message", "Customer Not Found");
				response.getWriter().append(json.toString());
			}
			
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}

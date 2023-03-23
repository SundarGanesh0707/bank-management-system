package controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import model.Account;
import model.Receipt;
import service.DBService;
import service.DatabaseConnection;

@WebServlet("/home/AddAccount")
public class AddAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		int customerId = Integer.valueOf(request.getParameter("customerId"));
		String IFSCCode = request.getParameter("IFSCCode");
		double amount = Double.valueOf(request.getParameter("amount"));
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from Customer where customer_id = ?");
			ps.setInt(1, customerId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String accountNo = IFSCCode.substring(IFSCCode.length()-5)+(100 + DBService.getAccountCount(IFSCCode));
				Account account = new Account(accountNo, amount, IFSCCode, new ArrayList<Receipt>());
				DBService.insertAccount(customerId, account);
				if(amount > 0) {
					DBService.insertReceipt(new Date(), accountNo, null, "DEPOSITED", amount, account.getBalance(), "CASH");
				}
				JSONObject json = new JSONObject();
				json.put("statusCode", 200);
				json.put("message", "Successfully added Account and Account No : "+accountNo);
				response.getWriter().append(json.toString());
			}
			else {
				JSONObject json = new JSONObject();
				json.put("statsCode", 404);
				json.put("message", "Customer Not Found");
				response.getWriter().append(json.toString());
			}
		}
		catch(Exception e) {
			
		}
		
	}

}

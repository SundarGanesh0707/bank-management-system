package controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import model.TransferType;
import service.DBService;

@WebServlet("/home/Transact")
public class Transact extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println(this.getServletName());
		int customerId = Integer.valueOf(request.getParameter("customerId"));
		String accountNo = request.getParameter("accountNo");
		double amount = Double.valueOf(request.getParameter("amount"));
		TransferType type = TransferType.valueOf(request.getParameter("type"));
		String payType = request.getParameter("paymentType");
		
		double balance = (double)request.getAttribute("balance");
		
		if(type.equals(TransferType.WITHDRAWN)) {
			balance -= amount;
		}
		else {
			balance += amount;
		}
		
		DBService.updateAccount(accountNo, balance);

		try {
			DBService.insertReceipt(new Date(), accountNo, null, type.toString(), amount, balance, payType);
			JSONObject json = new JSONObject();
			json.put("statusCode", 200);
			json.put("message", "Transaction success Now your balance :"+balance);
			response.getWriter().append(json.toString());
		} 
		catch (Exception e) {
			e.printStackTrace();
			JSONObject json = new JSONObject();
			json.put("statusCode", 500);
			json.put("message", "Fatel error Plz contact ADMIN");
			response.getWriter().append(json.toString());
		}
	}

}

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

@WebServlet("/home/Transfer")
public class Transfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int customerId = Integer.valueOf(request.getParameter("customerId"));
		String fromAccountNo = request.getParameter("fromAccountNo");
		String toAccountNo = request.getParameter("toAccountNo");
		double amount = Double.valueOf(request.getParameter("amount"));
		TransferType type = TransferType.valueOf(request.getParameter("type"));
		
		double fromAccountBalance = (double)request.getAttribute("fromAccountBalance");
		double toAccountBalance = (double)request.getAttribute("toAccountBalance");
		
		fromAccountBalance -= amount;
		toAccountBalance += amount;
		
		
		DBService.updateAccount(fromAccountNo, fromAccountBalance);
		DBService.updateAccount(toAccountNo, toAccountBalance);
		
		try {
			DBService.insertReceipt(new Date(), fromAccountNo, toAccountNo, type.toString(), amount, fromAccountBalance, null);
			DBService.insertReceipt(new Date(), toAccountNo, fromAccountNo, TransferType.RECEIVED.toString(), amount, toAccountBalance, null);
			JSONObject json = new JSONObject();
			json.put("statusCode", 200);
			json.put("message", "Transition success Now your balance :"+fromAccountBalance);
			response.getWriter().append(json.toString());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

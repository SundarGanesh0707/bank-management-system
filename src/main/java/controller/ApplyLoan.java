package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import model.Account;
import model.Loan;
import model.LoanStatus;
import model.LoanType;
import model.Receipt;
import service.DBService;

@WebServlet("/home/ApplyLoan")
public class ApplyLoan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			int customerId = Integer.valueOf(request.getParameter("customerId"));
			String accountNo = request.getParameter("accountNo");
			String loanType = request.getParameter("loanType");
			double interestRate = Double.valueOf(request.getParameter("interestRate"));
			double amount = Double.valueOf(request.getParameter("amount"));
			int noOfYears = Integer.valueOf(request.getParameter("noOfYears"));
			String path = request.getParameter("path");
			
			double totalPayment = amount+ (amount*interestRate/100);
			
			Account account = new Account(accountNo, 0.0, "", new ArrayList<Receipt>());
			
			int loanId = DBService.getLoanCount();
			
			Loan loan = new Loan(loanId, amount, 0, account, new Date(), noOfYears, Math.round(totalPayment/(noOfYears*12)), LoanType.valueOf(loanType), LoanStatus.SENT_FOR_REQUEST);
			
			DBService.insertLoan(loan, customerId);
			DBService.insertPath(loanId, path);
			
			JSONObject json = new JSONObject();
			json.put("statusCode", 200);
			json.put("message", "Request Sent To Manager and LoanId : "+loanId);
			response.getWriter().append(json.toString());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}

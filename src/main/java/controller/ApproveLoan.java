package controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import model.Loan;
import model.LoanStatus;
import model.LoanType;
import model.Receipt;
import model.TransferType;
import service.DBService;

@WebServlet("/home/ApproveLoan")
public class ApproveLoan extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			int loanId = Integer.valueOf(request.getParameter("loanId"));
			String status = request.getParameter("status");
			String path = request.getParameter("path");
			
			if(status.equals("REJECT")) {
				DBService.deletePath(loanId);
				Loan loan = DBService.getLoan(loanId);
				loan.setStatus(LoanStatus.REJECTED);
			}
			else {
				Loan loan = DBService.getLoan(loanId);
				loan.setStatus(LoanStatus.ACCEPTED);
				double totalAmount = loan.getAmountOfLoan();
				loan.getAccount().setBalance(totalAmount+loan.getAccount().getBalance());
				Receipt receipt = new Receipt(new Date(), loan.getAccount().getAccountNo(), null, TransferType.LOAN_SANCTION, totalAmount, loan.getAccount().getBalance(), null);
				DBService.insertReceipt(receipt.getDate(), receipt.getFromAccountNo(), receipt.getToAccountNo(), receipt.getTransferType().toString(), totalAmount, receipt.getNowBalance(), null);
				
				if(loan.getLoan_type().equals(LoanType.HOMELOAN)) {
					totalAmount += (totalAmount*6.8)/100; 
				}
				else if(loan.getLoan_type().equals(LoanType.GOLDLOAN)) {
					totalAmount += (totalAmount*7.35)/100; 
				}
				else {
					totalAmount += (totalAmount*10.5)/100; 
				}
				totalAmount = Math.round(totalAmount);
				loan.setBalance_payment(totalAmount);
				loan.setAmountOfLoan(totalAmount);
				Double paymentPerMonth = (double) Math.round(totalAmount/(loan.getTotalYears()*12));
				loan.setPayment_per_month(paymentPerMonth);
				DBService.updateLoan(loan);
				DBService.updateAccount(loan.getAccount().getAccountNo(), loan.getAccount().getBalance());
				DBService.insertLoanLastPay(loanId, loan.getDate());
				JSONObject json = new JSONObject();
				json.put("statusCode", 200);
				json.put("message", "Approve Successfully");
				response.getWriter().append(json.toString());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}

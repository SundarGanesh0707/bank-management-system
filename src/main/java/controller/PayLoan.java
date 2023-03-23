package controller;

import java.io.IOException;
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
import model.Receipt;
import model.TransferType;
import service.DBService;

@WebServlet("/home/PayLoan")
public class PayLoan extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String accountNo = request.getParameter("accountNo");
		int loanId = Integer.valueOf(request.getParameter("loanId"));
		double amount = Double.valueOf(request.getParameter("amount"));
		try {
			Loan loan = DBService.getLoan(loanId);
			
			loan.setBalance_payment(loan.getBalance_payment()- amount);
			if(loan.getBalance_payment()<=3) {
				loan.setBalance_payment(0);
				loan.setStatus(LoanStatus.FINISHED);
			}
			DBService.insertLoanLastPay(loanId, new Date());
			
			Account account = loan.getAccount();
			
			account.setBalance(account.getBalance() - amount);
			
			Receipt receipt = new Receipt(new Date(), account.getAccountNo(), null, TransferType.PAID_LOAN, amount, account.getBalance(), null);
			DBService.insertReceipt(receipt.getDate(), receipt.getFromAccountNo(), receipt.getToAccountNo(), receipt.getTransferType().toString(), receipt.getAmount(), receipt.getNowBalance(), null);
			
			DBService.updateAccount(account.getAccountNo(), account.getBalance());
			
			DBService.updateLoan(loan);
			
			JSONObject json = new JSONObject();
			json.put("statusCode", 200);
			json.put("message", "Successfully Paid Loan");
			response.getWriter().append(json.toString());
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}

package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import model.Account;
import model.Customer;
import model.Gender;
import model.Loan;
import model.Receipt;
import service.DBService;

@WebServlet("/home/AddCustomer")
public class AddCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String customerName = request.getParameter("customerName");
			String IFSCCode = request.getParameter("IFSCCode");
			String phoneNo = request.getParameter("phoneNo");
			String aadharNo = request.getParameter("aadharNo");
			String address = request.getParameter("address");
			String gender = request.getParameter("gender");
			Date dob = null;
			try {
				dob = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dob"));
			} 
			catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			int customerId = 1000+DBService.getCustomerCount()+1;
			String accountNo = IFSCCode.substring(IFSCCode.length()-5)+(100 + DBService.getAccountCount(IFSCCode));
			Customer customer = new Customer(customerId, customerName, aadharNo, phoneNo, dob, address, Gender.valueOf(gender), new HashMap<String, Account>(), new ArrayList<Loan>());
			Account account = new Account(accountNo, 0.0, IFSCCode, new ArrayList<Receipt>());
			DBService.insertCustomer(customer);	
			DBService.insertAccount(customer.getCustomerId(), account);
			JSONObject json = new JSONObject();
			json.put("statusCode", 200);
			json.put("message", "SuccessFully Added New Customer");
			json.put("customerId", customer.getCustomerId());
			json.put("accountNo", account.getAccountNo());
			response.getWriter().append(json.toString());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}

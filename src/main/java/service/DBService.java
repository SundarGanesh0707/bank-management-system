package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.Account;
import model.Customer;
import model.Employee;
import model.Loan;
import model.LoanStatus;
import model.LoanType;
import model.Receipt;
import model.Role;


public class DBService {

	public static int getAccountCount(String IFSCCode) {
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select count(*) from Account where branch_id = ?");
			ps.setString(1, IFSCCode);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getEmployeeCount() {
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select count(*) from Employee");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+101;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getCustomerCount() {
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select count(*) from Customer");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getLoanCount() {
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select count(*) from Loan");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return 100+rs.getInt(1)+1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void insertAccount(int customerId, Account account) throws Exception {
		try {
			PreparedStatement ps2 = DatabaseConnection.con.prepareStatement("insert into Account values(?, ?, ?, ?, ?)");
			ps2.setInt(1, customerId);
			ps2.setString(2, account.getAccountNo());
			ps2.setString(3, account.getIFSCCode());
			ps2.setDouble(4, account.getBalance());
			ps2.setString(5, "ACTIVE");
			ps2.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public static Date getLastPayDate(int loanId) {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from loanLastPay where loanId = ?");
			ps.setInt(1, loanId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return new java.util.Date(rs.getDate(2).getTime());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int updateBalance(String accountNo, double balance) throws Exception {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("update account set balance = ? where accountNo = ?");
			ps.setDouble(1, balance);
			ps.setString(2, accountNo);
			return ps.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public static int insertReceipt(Date date, String fromAccountNo, String toAccountNo, String type, double amount, double balance, String paymentType) throws Exception {
		try {
			String sql2 = "insert into Receipt values(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = DatabaseConnection.con.prepareStatement(sql2);
			ps.setDate(1, new java.sql.Date(date.getTime()));
			ps.setString(2, fromAccountNo);
			ps.setString(3, toAccountNo);
			ps.setDouble(4, Math.abs(amount));
			ps.setDouble(5, balance);
			ps.setString(6, type);
			ps.setString(7, paymentType);
			return ps.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	
	
	public static int insertNewSession(String sessionId, int empId) {
		String sql = "insert into sessionTable values(?, ?)";
		try {
			PreparedStatement p1 = DatabaseConnection.con.prepareStatement("select * from sessionTable where emp_id = ?");
			p1.setInt(1, empId);
			ResultSet r1 = p1.executeQuery();
			if(r1.next()) {
				sql = "update sessionTable set sessionId = ? where emp_id = ?";
			}
		}
		catch(Exception e) {
			
		}
		
		try {
			
			PreparedStatement ps = DatabaseConnection.con.prepareStatement(sql);
			ps.setString(1, sessionId);
			ps.setInt(2, empId);
			return ps.executeUpdate();
		}
		catch(Exception e) {
			return 1;
		}
		
	}
	
	
	public static void insertLoan(Loan loan, int customerId) throws Exception {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("insert into Loan values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, customerId);
			ps.setInt(2, loan.getLoanId());
			ps.setDate(3, new java.sql.Date(loan.getDate().getTime()));
			ps.setDouble(4, loan.getAmountOfLoan());
			ps.setDouble(5, loan.getBalance_payment());
			ps.setString(6, loan.getAccount().getAccountNo());
			ps.setString(7, loan.getLoan_type().toString());
			ps.setInt(8, loan.getTotalYears());
			ps.setString(9, loan.getStatus().toString());
			ps.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	
	
	public static void insertPath(int loanId, String path) throws Exception {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("insert into LoanPath values(?, ?)");
			ps.setInt(1, loanId);
			ps.setString(2, path);
			ps.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	
	public static void insertCustomer(Customer customer) throws Exception {
		
		try {
			String sql = "insert into Customer values(?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = DatabaseConnection.con.prepareStatement(sql);
			ps.setInt(1, customer.getCustomerId());
			ps.setString(2, customer.getName());
			ps.setString(3, customer.getAadharNo());
			ps.setString(4, customer.getPhoneNo());
			ps.setString(5, customer.getAddress());
			ps.setString(6, customer.getGender().toString());
			ps.setDate(7, new java.sql.Date(customer.getDob().getTime()));
			ps.setString(8, "ACTIVE");
			ps.executeUpdate();	
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	
	public static void insertAccount(Account account, String customerId) throws Exception {
		
		try {
			String sql = "insert into account values(?, ?, ?, ?)";
			PreparedStatement ps = DatabaseConnection.con.prepareStatement(sql);
			ps.setString(1, customerId);
			ps.setString(2, account.getAccountNo());
			ps.setDouble(3, account.getBalance());
			ps.setString(4, account.getIFSCCode());
			ps.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	
	
	public static Role getRole(String customerId) throws Exception {
		
		try {
			String sql = "select * from customer where customerId = ?";
			PreparedStatement ps = DatabaseConnection.con.prepareStatement(sql);
			ps.setString(1, customerId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return Role.valueOf(rs.getString("role"));
			}
		}
		catch(Exception e) {
			throw e;
		}
		return null;
		
	}
	
	public static void insertEditRequest(String customerId, String name, String address, String phoneNo) throws Exception {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("insert into editRequest values(?, ?, ?, ?, ?)");
			ps.setDate(1, new java.sql.Date(new Date().getTime()));
			ps.setString(2, customerId);
			ps.setString(3, name);
			ps.setString(4, address);
			ps.setString(5, phoneNo);
			ps.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	
	public static void insertEmployeeRequest(Employee employee) throws Exception {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("insert into Employee values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, employee.getEmp_id());
			ps.setString(2, employee.getEmp_name());
			ps.setDate(3, new java.sql.Date(employee.getEmp_dob().getTime()));
			ps.setString(4, employee.getEmp_phone_no());
			ps.setString(5, employee.getEmp_addres());
			ps.setString(6, employee.getBranch_id());
			ps.setString(7, employee.getEmp_role().toString());
			ps.setString(8, employee.getEmp_password());
			ps.setString(9, "ACTIVE");
			ps.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	
	
	public static void changeBranch(int empId, String newBranchId) throws Exception {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("update Employee set branch_id = ? where emp_id = ?");
			ps.setString(1, newBranchId);
			ps.setInt(2, empId);
			ps.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	
	public static void changeEmpAndCustomerStatus(int primaryKey, String sql, String status) throws Exception {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement(sql);
			ps.setString(1, status);
			ps.setInt(2, primaryKey);
			ps.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	
	public static void changeAccountStatus(String primaryKey, String sql, String status) throws Exception {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement(sql);
			ps.setString(1, status);
			ps.setString(2, primaryKey);
			ps.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	
	public static void addCustomerAndEmployeeResson(int primaryKey, String sql, String resson) throws Exception {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement(sql);
			ps.setDate(1, new java.sql.Date(new Date().getTime()));
			ps.setInt(2, primaryKey);
			ps.setString(3, resson);
			ps.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	
	public static void addAccountResson(String primaryKey, String sql, String resson) throws Exception {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement(sql);
			ps.setDate(1, new java.sql.Date(new Date().getTime()));
			ps.setString(2, primaryKey);
			ps.setString(3, resson);
			ps.executeUpdate();
			
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	
	public static void updateEmployeeDetails(String phoneNo, String address, int empId) throws Exception {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("update Employee set emp_phone_no = ? , emp_address = ? where emp_id = ?");
			ps.setString(1, phoneNo);
			ps.setString(2, address);
			ps.setInt(3, empId);
			ps.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	
	
	public static void updateEmployeePassword(String newPassword, int empId) throws Exception {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("update Employee set emp_password = ? where emp_id = ?");
			ps.setString(1, newPassword);
			ps.setInt(2, empId);
			ps.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	
	
	public static void updateAccount(String accountNo, double balance) {
		try {
			String sql = "update Account set balance = ? where account_no = ?";
			PreparedStatement ps = DatabaseConnection.con.prepareStatement(sql);
			ps.setDouble(1, balance);
			ps.setString(2, accountNo);
			synchronized (ps) {
				ps.executeUpdate();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static ResultSet getCustomerByCustomerId(int customerId) {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from Customer where customer_id = ?");
			ps.setInt(1, customerId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return rs;
			}
			else {
				return null;
			}
		}
		catch(Exception e) {
			
		}
		return null;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public static JSONArray checkVaildAccountNo(String accountNo, String branch) throws Exception {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from Account where account_no = ? and status = \"ACTIVE\"");
			ps.setString(1, accountNo);
			ResultSet rs = ps.executeQuery();
			JSONArray jsonArray = new JSONArray();
			if(rs.next()) {
				if(rs.getString("branch_id").equals(branch)) {
					return null;
				}
				else {
					JSONObject json = new JSONObject();
					json.put("statusCode", 400);
					json.put("message", "This Account is Not In our Branch Plz Check");
					jsonArray.add(json);
					return jsonArray;
				}
			}
			else {
				JSONObject json = new JSONObject();
				json.put("statusCode", 404);
				json.put("message", "Account Not Found");
				jsonArray.add(json);
				return jsonArray;
			}
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	
	public static void deletePath(int loanId) {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("delete from LoanPath where loan_id = ?");
			ps.setInt(1, loanId);
			ps.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static Loan getLoan(int loanId) throws Exception {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from Loan where loan_id = ?");
			ps.setInt(1, loanId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Account account = getAccount(rs.getString("account_no"));
				Double totalAmount = rs.getDouble("total_amount");
				int noOfYears = rs.getInt("no_of_years");
				Double paymentPerMonth = (double) Math.round(totalAmount/(noOfYears*12));
				Loan loan = new Loan(loanId, totalAmount, rs.getDouble("balance_amount"), account,rs.getDate("applied_date"), noOfYears, paymentPerMonth, LoanType.valueOf(rs.getString("loan_type")), LoanStatus.valueOf(rs.getString("loan_status")));
				return loan;
			}
			return null;
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	
	public static Account getAccount(String accountNo) throws Exception {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from Account where account_no = ?");
			ps.setString(1, accountNo);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Account account = new Account(rs.getString("account_no"), rs.getDouble("balance"), rs.getString("branch_id"), new ArrayList<Receipt>());
				return account;
			}
			else {
				return null;
			}
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	
	
	public static void updateLoan(Loan loan) throws Exception {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("update Loan set total_amount = ?, balance_amount = ?, loan_status = ? where loan_id = ?");
			ps.setDouble(1, loan.getAmountOfLoan());
			ps.setDouble(2, loan.getBalance_payment());
			ps.setString(3, loan.getStatus().toString());
			ps.setInt(4, loan.getLoanId());
			ps.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	
	public static void insertLoanLastPay(int loanId, Date date) throws Exception {
		
		try {
			PreparedStatement ps = DatabaseConnection.con.prepareStatement("insert into LoanLastPay values(?, ?)");
			ps.setInt(1, loanId);
			ps.setDate(2, new java.sql.Date(date.getTime()));
			ps.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	
	public static final long getMonthsDifference(Date date1, Date date2) {
	    YearMonth m1 = YearMonth.from(date1.toInstant().atZone(ZoneOffset.UTC));
	    YearMonth m2 = YearMonth.from(date2.toInstant().atZone(ZoneOffset.UTC));

	    return m1.until(m2, ChronoUnit.MONTHS) + 1;
	}

	
}

package model;

import java.util.Date;

public class Loan implements Comparable<Loan>{

	private int loanId;
	private double amountOfLoan;
	private double balance_payment;
	private Account account;
	private Date date;
	private double payment_per_month;
	private int totalYears;
	private LoanType loan_type;
	private LoanStatus status;
	
	public Loan(int loanId,double amountOfLoan, double balance_payment, Account account, Date date,int totalYears,double payment_per_month,
			LoanType loan_type, LoanStatus status) {
		super();
		this.setLoanId(loanId);
		this.amountOfLoan = amountOfLoan;
		this.balance_payment = balance_payment;
		this.account = account;
		this.totalYears = totalYears;
		this.date = date;
		this.payment_per_month = payment_per_month;
		this.loan_type = loan_type;
		this.status = status;
	}
	
	public int getTotalYears() {
		return totalYears;
	}
	public void setTotalYears(int totalYears) {
		this.totalYears = totalYears;
	}
	public double getAmountOfLoan() {
		return amountOfLoan;
	}
	public void setAmountOfLoan(double amountOfLoan) {
		this.amountOfLoan = amountOfLoan;
	}
	public double getBalance_payment() {
		return balance_payment;
	}
	public void setBalance_payment(double balance_payment) {
		this.balance_payment = balance_payment;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getPayment_per_month() {
		return payment_per_month;
	}
	public void setPayment_per_month(double payment_per_month) {
		this.payment_per_month = payment_per_month;
	}
	public LoanType getLoan_type() {
		return loan_type;
	}
	public void setLoan_type(LoanType loan_type) {
		this.loan_type = loan_type;
	}
	public LoanStatus getStatus() {
		return status;
	}
	public void setStatus(LoanStatus status) {
		this.status = status;
	}
	
	@Override
	public int compareTo(Loan o) {
		return this.getDate().compareTo(o.getDate())*-1;
	}

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	
}

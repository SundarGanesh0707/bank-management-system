package model;

import java.util.ArrayList;
import java.util.Collections;

public class Account {
	
	private String accountNo;
	private double balance;
	private String IFSCCode;
	private ArrayList<Receipt> receipts = new ArrayList<Receipt>();
	
	public Account(String accountNo, double balance, String iFSCCode, ArrayList<Receipt> arrayList) {
	
		this.accountNo = accountNo;
		this.balance = balance;
		IFSCCode = iFSCCode;
		this.receipts = arrayList;
		Collections.sort(this.receipts);
		
	}


	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getIFSCCode() {
		return IFSCCode;
	}
	public void setIFSCCode(String iFSCCode) {
		IFSCCode = iFSCCode;
	}
	public ArrayList<Receipt> getReceipts() {
		return receipts;
	}
	public void setReceipts(ArrayList<Receipt> receipts) {
		this.receipts = receipts;
		Collections.sort(this.receipts);
	}
	
}

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Customer {
	
	private int customerId;
	private String name;
	private String aadharNo;
	private String phoneNo;
	private Date dob;
	private String address;
	private Gender gender;
	private Map<String,Account> accounts = new HashMap<String,Account>();
	private ArrayList<Loan> loans = new ArrayList<Loan>();
	
	public Customer(int customerId, String name, String aadharNo, String phoneNo, Date dob, String address,
			Gender gender, Map<String, Account> accounts, ArrayList<Loan> arrayList) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.aadharNo = aadharNo;
		this.phoneNo = phoneNo;
		this.dob = dob;
		this.address = address;
		this.gender = gender;
		this.accounts = accounts;
		this.loans = arrayList;
		Collections.sort(this.loans);
	}
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAadharNo() {
		return aadharNo;
	}
	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Map<String, Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(Map<String, Account> accounts) {
		this.accounts = accounts;
	}
	public ArrayList<Loan> getLoans() {
		return loans;
	}
	public void setLoans(ArrayList<Loan> loans) {
		this.loans = loans;
		Collections.sort(this.loans);
	}
	
}

package model;

import java.util.Date;

public class Receipt implements Comparable<Receipt>{
	
	private Date date;
	private String fromAccountNo;
	private String toAccountNo;
	private TransferType transferType;
	private double amount;
	private double nowBalance;
	private PaymentType payType;
	
	public Receipt(Date date, String fromAccountNo, String toAccountNo, TransferType transferType, double amount,
			double nowBalance, PaymentType payType) {
		super();
		this.date = date;
		this.fromAccountNo = fromAccountNo;
		this.toAccountNo = toAccountNo;
		this.transferType = transferType;
		this.amount = amount;
		this.nowBalance = nowBalance;
		this.payType = payType;
	}
	
	public PaymentType getPayType() {
		return payType;
	}
	public void setPayType(PaymentType payType) {
		this.payType = payType;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getFromAccountNo() {
		return fromAccountNo;
	}
	public void setFromAccountNo(String fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}
	public String getToAccountNo() {
		return toAccountNo;
	}
	public void setToAccountNo(String toAccountNo) {
		this.toAccountNo = toAccountNo;
	}
	public TransferType getTransferType() {
		return transferType;
	}
	public void setTransferType(TransferType transferType) {
		this.transferType = transferType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getNowBalance() {
		return nowBalance;
	}
	public void setNowBalance(double nowBalance) {
		this.nowBalance = nowBalance;
	}
	
	@Override
	public int compareTo(Receipt o) {
		return this.getDate().compareTo(o.getDate())*-1;
	}
	
}

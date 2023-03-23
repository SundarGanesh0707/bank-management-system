package model;

import java.util.Date;

public class Employee {

	private int emp_id;
	private String emp_name;
	private Date emp_dob;
	private String emp_phone_no;
	private String emp_addres;
	private String branch_id;
	private Role emp_role;
	private String emp_password;
	
	public Employee(int emp_id, String emp_name, Date emp_dob, String emp_phone_no, String emp_addres, String branch_id,
			Role emp_role, String emp_password) {
		super();
		this.emp_id = emp_id;
		this.emp_name = emp_name;
		this.emp_dob = emp_dob;
		this.emp_phone_no = emp_phone_no;
		this.emp_addres = emp_addres;
		this.branch_id = branch_id;
		this.emp_role = emp_role;
		this.emp_password = emp_password;
	}
	
	public int getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public Date getEmp_dob() {
		return emp_dob;
	}
	public void setEmp_dob(Date emp_dob) {
		this.emp_dob = emp_dob;
	}
	public String getEmp_phone_no() {
		return emp_phone_no;
	}
	public void setEmp_phone_no(String emp_phone_no) {
		this.emp_phone_no = emp_phone_no;
	}
	public String getEmp_addres() {
		return emp_addres;
	}
	public void setEmp_addres(String emp_addres) {
		this.emp_addres = emp_addres;
	}
	public String getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
	public Role getEmp_role() {
		return emp_role;
	}
	public void setEmp_role(Role emp_role) {
		this.emp_role = emp_role;
	}
	public String getEmp_password() {
		return emp_password;
	}
	public void setEmp_password(String emp_password) {
		this.emp_password = emp_password;
	}
	
}

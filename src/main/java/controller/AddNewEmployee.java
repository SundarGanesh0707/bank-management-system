package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import model.Employee;
import model.Role;
import service.DBService;

@WebServlet("/home/AddNewEmployee")
public class AddNewEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println(this.getServletName());
		String empName = request.getParameter("empName");
		String phoneNo = request.getParameter("phoneNo");
		String address = request.getParameter("address");
		String password = request.getParameter("password");
		String role = request.getParameter("role");
		String IFSCCode = request.getParameter("IFSCCode");
		Date dob = null;
		try {
			dob = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dob"));
		} 
		catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		int empId = DBService.getEmployeeCount();
		
		Employee employee = new Employee(empId, empName, dob, phoneNo, address, IFSCCode, Role.valueOf(role), password);
		
		try {
			DBService.insertEmployeeRequest(employee);
			JSONObject json = new JSONObject();
			json.put("statusCode", 200);
			json.put("message", "Successfully Added Employee his Employee Id :"+empId);
			System.out.println(json.toString());
			response.getWriter().append(json.toString());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}

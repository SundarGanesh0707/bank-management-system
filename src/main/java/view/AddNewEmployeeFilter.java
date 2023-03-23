package view;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

import org.json.simple.JSONObject;

import service.DatabaseConnection;

@WebFilter("/home/AddNewEmployee")
public class AddNewEmployeeFilter extends HttpFilter implements Filter {
     
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "unchecked", "unused" })
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		System.out.println(this.getFilterName());
		
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
		
		String regex = "^(?=.*[0-9])"
	            + "(?=.*[a-z])(?=.*[A-Z])"
	            + "(?=.*[@#$%^&+=])"
	            + "(?=\\S+$).{8,20}$";
		
		JSONObject json = new JSONObject();
		
		StringBuffer InvaildInputs = new StringBuffer();
		
		try {
			if(getAge(dob)>18) {
				PreparedStatement ps = DatabaseConnection.con.prepareStatement("select * from Employee where emp_phone_no = ?");
				ps.setString(1, phoneNo);
				ResultSet rs = ps.executeQuery();
				if(!rs.next()) {
					if(empName.matches("[A-z\\s]*")) {
						if(phoneNo.matches("^\\d{10}$")) {
							if(password.matches(regex)) {
								chain.doFilter(request, response);
							}
							else {
								json.put("statusCode", 400);
								json.put("message", "Plz Create The Strong Password");
							}
						}
						else {
							json.put("statusCode", 400);
							json.put("message", "Invaild PhoneNo Plz Check");
						}
					}
					else {
						json.put("statusCode", 400);
						json.put("message", "Invaild Name Plz Check");
					}
				}
				else {
					json.put("statusCode", 400);
					json.put("message", "This PhoneNo is Already existed");
				}
			}
			else {
				json.put("statusCode", 109);
				json.put("message", "Sorry, Your Not Eligible");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			json.put("statusCode", 500);
			json.put("message", "FATAL error Plz Contact Admin");
		}
		response.getWriter().append(json.toString());
	}
	
	@SuppressWarnings("deprecation")
	public int getAge(Date dob) {
	    long ageInMillis = new Date().getTime() - dob.getTime();

	    Date age = new Date(ageInMillis);

	    return age.getYear();
	}
	
}

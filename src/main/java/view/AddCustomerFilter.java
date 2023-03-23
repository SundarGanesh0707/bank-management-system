package view;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

import org.json.simple.JSONObject;

import service.DatabaseConnection;


@WebFilter("/home/AddCustomer")
public class AddCustomerFilter extends HttpFilter implements Filter {
    
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		System.out.println(this.getFilterName());
		
		String customerName = request.getParameter("customerName");
		String IFSCCode = request.getParameter("IFSCCode");
		String phoneNo = request.getParameter("phoneNo");
		String aadharNo = request.getParameter("aadharNo");
		String address = request.getParameter("address");
		String gender = request.getParameter("gender");
		Date dob = null;
		StringBuffer invaildFildes = new StringBuffer();
		try {
			dob = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dob"));
		} 
		catch (ParseException e1) {
			e1.printStackTrace();
		}
		String sql  = "select * from Customer where aadhar_no = ? or phone_no = ?";
		boolean isEligible = true; 
		PreparedStatement ps;
		try {
			ps = DatabaseConnection.con.prepareStatement(sql);
			ps.setString(1, aadharNo);
			ps.setString(2, phoneNo);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				if(!customerName.matches("[A-z\\s]*")){
					invaildFildes.append("Name ");
				}
				if(!phoneNo.matches("^\\d{10}$")) {
					invaildFildes.append("PhoneNo ");
				}
				if(!aadharNo.matches("^\\d{12}$")) {
					invaildFildes.append("AadharNo ");
				}
				if(getAge(dob) < 18) {
					isEligible = false;
					invaildFildes.append("Your are Not eligible ");
				}
				if(!isEligible) {
					JSONObject json = new JSONObject();
					json.put("statusCode", 109);
					json.put("message", "Sorry Your Not Eligible");
					response.getWriter().append(json.toString());
				}
				else if(invaildFildes.isEmpty() || invaildFildes.equals("")) {
					chain.doFilter(request, response);	
				}
				else {
					JSONObject json = new JSONObject();
					json.put("statusCode", 400);
					json.put("message", "Invaild "+invaildFildes);
					response.getWriter().append(json.toString());
				}
			}
			else {
				JSONObject json = new JSONObject();
				json.put("statusCode", 402);
				json.put("message", "Aadhar or Phone No are Same one already existed");
				response.getWriter().append(json.toString());
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public int getAge(Date dob) {
	    long ageInMillis = new Date().getTime() - dob.getTime();

	    Date age = new Date(ageInMillis);

	    return age.getYear();
	}

}

package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import service.DBService;

@WebServlet("/home/SaveProfile")
public class SaveProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			int empId = (int)request.getAttribute("empId");
			String address = request.getParameter("address");
			String phoneNo = request.getParameter("phoneNo");
			if(phoneNo.matches("^\\d{10}$")) {
				
				DBService.updateEmployeeDetails(phoneNo, address, empId);
				
				JSONObject json = new JSONObject();
				json.put("statusCode", 200);
				json.put("message", "Successfully Details Changed");
				response.getWriter().append(json.toString());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}

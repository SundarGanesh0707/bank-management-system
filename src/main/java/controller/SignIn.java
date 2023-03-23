package controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import service.DBService;

@WebServlet("/SignIn")
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println(this.getServletName());
		
		int emp_id = Integer.valueOf(request.getParameter("emp_id"));
		String empName = (String)request.getAttribute("userName");
		String role = (String)request.getAttribute("role");
		
		UUID uuid=UUID.randomUUID();
		String uuidString = uuid.toString();
		
		DBService.insertNewSession(uuidString, emp_id);
		
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "Welcome "+empName);
		json.put("role", role);
		json.put("sessionId", uuidString);
		json.put("name", empName);
		response.getWriter().append(json.toString());
		
	}

}

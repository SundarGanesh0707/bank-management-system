package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
@WebServlet(urlPatterns = {"/DatabaseConnection"}, loadOnStartup = 0)
public class DatabaseConnection extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public static Connection con;
	
	public void init() {
		try {
			ServletContext sc = getServletContext();
			String databaseName = sc.getInitParameter("databaseName");
			String databaseUserName = sc.getInitParameter("databaseUserName");
			String databasePassword = sc.getInitParameter("databasePassword");
			Class.forName("com.mysql.cj.jdbc.Driver");
			DatabaseConnection.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+databaseName, databaseUserName, databasePassword);
			System.out.println(DatabaseConnection.con);
		} 
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
    }
}

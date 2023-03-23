package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home/ReadFile")
public class ReadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setHeader("Content-Disposition", "inline");
		String path = req.getParameter("path");
		File file = new File(path);
		FileInputStream in = new FileInputStream(file);
		ServletOutputStream out = res.getOutputStream();
		byte[] bytes = new byte[4096];
		int bytesRead ;
		while((bytesRead = in.read(bytes))!=-1) {
			out.write(bytes, 0, bytesRead);
		}
		in.close();
		out.close();
	}

}

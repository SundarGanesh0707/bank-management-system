package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.simple.JSONObject;

import service.DBService;

@MultipartConfig(maxFileSize = (1024*1024*1024*2)-5)
@WebServlet("/home/CreateFile")
public class CreateFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			System.out.println(this.getServletName());
			Part part = request.getPart("file");
			InputStream in = part.getInputStream();
			String path = "/home/sundar-zstk307/Documents/";
			byte[] bytes = in.readAllBytes();
			path+=DBService.getLoanCount()+".pdf";
			File file = new File(path);
			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			out.write(bytes);
			out.flush();
			out.close();
			JSONObject json = new JSONObject();
			json.put("statusCode", 200);
			json.put("message", "SuccessFully file Created");
			json.put("path", path);
			response.getWriter().append(json.toString());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}

package com.test.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		String password=request.getParameter("pass");
		String repeatPassword=request.getParameter("re_pass");
		String contact=request.getParameter("contact");
		
//		PrintWriter out = response.getWriter();
//		out.println(name);
//		out.println(email);
//		out.println(password);
//		out.println(repeatPassword);
//		out.println(contact);	
		Connection conn=null;
		RequestDispatcher dispatcher=null;
		try {
			String url="jdbc:mysql://localhost:3306/login_register_app?useSSL=false";
			conn=DriverManager.getConnection(url,"root","root");
			String insertQuery="insert into users (name, email, password, contact_number) values(?,?,?,?)";
			PreparedStatement st = conn.prepareStatement(insertQuery);
			st.setString(1, name);
			st.setString(2, email);
			st.setString(3, password);
			st.setString(4, contact);
			int resp=st.executeUpdate();
			dispatcher=request.getRequestDispatcher("registration.jsp");
			if(resp>0) {
				request.setAttribute("status", "success");
			}
			else {
				request.setAttribute("status", "failed");
			}
			System.out.println(request.getAttribute("status"));
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

package myfirstwebapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SubmitForm
 */
@WebServlet("/SubmitForm")
public class SubmitForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitForm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		pw.println("<h2>Welcome to the Contact Form!</h2>");
		pw.println("<p>Use the form to submit your details.</p>");
		pw.println("<a href='index.html'>Go to Form</a>");


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  response.setContentType("text/html");
	        PrintWriter out = response.getWriter();

	        String name = request.getParameter("name");
	        String email = request.getParameter("email");
	        String message = request.getParameter("message");
	        // --- Validation ---
	        if (name == null || email == null || message == null ||
	            name.isEmpty() || email.isEmpty() || message.isEmpty()) {
	            out.println("<h3 style='color:red;'>Please fill all fields!</h3>");
	            out.println("<a href='index.html'>Go Back</a>");
	            return;
	        }
	        try {
	            // --- Database Connection ---
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection con = DriverManager.getConnection(
	                "jdbc:mysql://localhost:3306/webformdb", "root", "USER@786");

	            PreparedStatement ps = con.prepareStatement(
	                "Insert Into submissions (name, email, message) VALUES (?, ?, ?)");

	            ps.setString(1, name);
	            ps.setString(2, email);
	            ps.setString(3, message);

	            int result = ps.executeUpdate();
	            if (result > 0) {
	                out.println("<h2>Form Submitted Successfully!</h2>");
	                out.println("<p><b>Name:</b> " + name + "</p>");
	                out.println("<p><b>Email:</b> " + email + "</p>");
	                out.println("<p><b>Message:</b> " + message + "</p>");
	            } else {
	                out.println("<h3 style='color:red;'>Failed to submit form!</h3>");
	            }

	            con.close();

	        } catch (Exception e) {
	            e.printStackTrace(out);
	            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
	        }

	        out.println("<br><a href='index.html'>Back to Form</a>");
	    }

		//doGet(request, response);
	}


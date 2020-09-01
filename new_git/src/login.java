

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * Default constructor. 
     */
    public login() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		String pswrd = request.getParameter("password");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/novels", "root", "root");
			String qr = "select * from new_user where name=? and pswrd=?";
			PreparedStatement ps = con.prepareStatement(qr);
			ps.setString(1, name);
			ps.setString(2, pswrd);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				HttpSession session = request.getSession();
				session.setMaxInactiveInterval(5*60);
				session.setAttribute("name", name);
				response.sendRedirect("category.jsp");	
			} else {	
				out.print("Invalid ID and Password");
				/*RequestDispatcher rd = request.getRequestDispatcher("Index.html");
				rd.include(request, response);*/
			}
			con.close();
		} catch (Exception e) {
			out.println(e);
		}
}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

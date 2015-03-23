
package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class Controller extends HttpServlet {

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        
        // Login request
		if (request.getParameter("login_action") != null) {
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            
            out.println("ola k ase " + request.getParameter("user") + " loco.");
		}
    }

    private void gotoPage (String address, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
    	dispatcher.forward(request, response);
    	return;
    }

}

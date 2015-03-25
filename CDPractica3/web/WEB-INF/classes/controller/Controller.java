
package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import model.*; // DAO

public class Controller extends HttpServlet {

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        DAOInt DAO = (DAOInt) new DAOImpl();
        
        // Login request
		if (request.getParameter("login_action") != null) {
            mostrarPaginaError("Aun no está implementado :(", session, request, response);
		} 

        // Register request
        else if (request.getParameter("register_action") != null) {
            // try {
            //     Client nuevoCliente = new Client(request.getParameter("user"),"prueba@mail.com",request.getParameter("password1"));
            // } catch (Exception ex) {
            //     mostrarPaginaError("Error:\n"+ex.getMessage(), session, request, response);
            // }
            mostrarPaginaError("Aun no está implementado :(", session, request, response);
        }
    }

    private void gotoPage (String address, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
    	dispatcher.forward(request, response);
    	return;
    }

    private void mostrarPaginaError (String error, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session.setAttribute("error", error);
        gotoPage("/error.jsp", request, response); // Imprimo el error en una pagina
    }

}

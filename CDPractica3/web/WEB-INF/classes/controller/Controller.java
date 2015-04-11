
package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import model.*; // DAO

public class Controller extends HttpServlet {

    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;
    DAOInt DAO;

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.request = request;
        this.response = response;
        this.session = request.getSession(true);
        DAO = (DAOInt) new DAOImpl();

        // Session variables
        session.removeAttribute("mensaje");
        session.removeAttribute("error");
        
        // Login request
        if (request.getParameter("login_action") != null) {

            String username = request.getParameter("user");
            String password = request.getParameter("password");

            login(username, password);
        } 

        // Register request
        else if (request.getParameter("register_action") != null) {
            String username = request.getParameter("user");
            String email = request.getParameter("email");
            String password1 = request.getParameter("password1");
            String password2 = request.getParameter("password2");

            if ( register(username, email, password1, password2) ) {
                login(username, password1); // login if the register was successful
            }
            
        }

        // Change password request
        else if (request.getParameter("change_password_action") != null) {
            String username = (String) session.getAttribute("username");
            String old_password = request.getParameter("old_password");
            String newPassword1 = request.getParameter("password1");
            String newPassword2 = request.getParameter("password2");

            changePassword(username, old_password, newPassword1, newPassword2);
        }

        // Delete account request
        else if (request.getParameter("delete_account_action") != null) {
            String username = (String) session.getAttribute("username");
            String password = request.getParameter("password");

            deleteAccount(username, password);
        }

        // Logout request
        else if (request.getParameter("logout_action") != null) {
            session.removeAttribute("username");
            session.invalidate();

            gotoPage("/index.html", request, response);
        }
    }

    private void login(String username, String password) throws ServletException, IOException {

        if (username != null && password != null) {
            if (DAO.checkPass(username, password)) {
                session.setAttribute("username", username);
                gotoPage("/perfil.jsp", request, response);
            } else {
                mostrarPaginaError("No se ha podido iniciar sesión", session, request, response); 
            }
        } else {
            mostrarPaginaError("No se ha podido iniciar sesión", session, request, response); 
        }

    }

    private boolean register (String username, String email, String password1, String password2) throws ServletException, IOException {
        if (username != null && email != null && password1 != null && password2 != null) {

            if (password1.equals(password2)) {

                try {

                    Client cliente = new Client(username, email, password1);

                    if ( ! DAO.newClient(cliente) ) {
                        mostrarPaginaError("Error: Ya existe un cliente con ese nombre, o se ha producido un error en la transacción", session, request, response);
                        return false;
                    }

                    return true; // registro valido

                } catch (Exception ex) {

                    mostrarPaginaError("Error:\n"+ex, session, request, response);
                }

            } else {
                mostrarPaginaError("Las contraseñas no coinciden", session, request, response); 
            }

        } else {
            mostrarPaginaError("No se ha podido registrar", session, request, response); 
        }

        return false;
    }

    private void changePassword(String username, String oldpassword, String newPassword1, String newPassword2) throws ServletException, IOException {
        if (username != null && oldpassword != null && newPassword1 != null && newPassword2 != null) {
            if (newPassword1.equals(newPassword2)) {

                if (DAO.checkPass(username, oldpassword)) { // check if the password is correct

                    DAO.changeClientPass(username, newPassword1);

                    session.setAttribute("mensaje", "Contraseña cambiada!"); // Successfully changed
                    gotoPage("/perfil.jsp", request, response);

                } else {
                    mostrarPaginaError("Antigua contraseña no válida", session, request, response); 
                }

            } else {
                mostrarPaginaError("Las contraseñas no coinciden", session, request, response); 
            }
        } else {
            mostrarPaginaError("No se ha podido cambiar la contraseña", session, request, response); 
        }
    }

    private void deleteAccount(String username, String password) throws ServletException, IOException {
        if (username != null && password != null) {

            if (DAO.checkPass(username, password)) { // check if the password is correct

                DAO.removeClient(username);

                session.removeAttribute("username");
                gotoPage("/index.html", request, response);

            } else {
                mostrarPaginaError("Contraseña no válida", session, request, response); 
            }         

        } else {
            mostrarPaginaError("No se ha podido eliminar la cuenta", session, request, response); 
        }
    }

    private void gotoPage (String address, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    private void mostrarPaginaError (String error, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session.setAttribute("error", error);
        gotoPage("/error.jsp", request, response); // print an error page
    }

}

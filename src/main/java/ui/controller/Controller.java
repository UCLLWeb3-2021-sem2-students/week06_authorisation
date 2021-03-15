package ui.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.model.Role;
import domain.model.User;


@WebServlet("/Controller")
public class Controller extends HttpServlet {
    ControllerFactory controllerFactory = new ControllerFactory();


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.isEmpty())
            action = "Home";
        RequestHandler handler = controllerFactory.getController(action);

        try {
            handler.handleRequest(request, response);

        } catch (NotAuthorizedException e) {
            request.setAttribute("notAuthorized", "You have insufficient rights to have a look at the requested page.");
            controllerFactory.getController("Home").handleRequest(request,response);
        }


    }


}

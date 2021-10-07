package servlets;

import utils.Database;
import utils.Util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "adminLoginServlet", value = "/admin-login-servlet")
public class AdminLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String rememberMe = req.getParameter("remember_me");

        Database db = new Database();
        if (db.adminLogin(email, password, rememberMe, req, resp)) {
            resp.sendRedirect(Util.base_url + "admin_dashboard.jsp");
        }else{
            req.setAttribute("loginError", "Kullanıcı adı ya da şifre hatalı!");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin_login.jsp");
            dispatcher.forward(req, resp);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean isNull = req.getParameter("data") == null;
        Util util = new Util();
        if(!isNull){
            util.hasLoggedIn(req, resp, 1);
        }else{
            util.hasLoggedIn(req, resp, 0);
        }
    }
}

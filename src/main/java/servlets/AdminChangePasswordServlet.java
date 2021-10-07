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

@WebServlet(name = "adminChangePwServlet", value = "/admin-change-pw-servlet")
public class AdminChangePasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.sendRedirect(Util.base_url + "admin_change_password.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Dashboard açıldığında Utils.hasloggedIn fonksiyonu ile session a "aid" attribute u eklenecek.
        int id = (int) req.getSession().getAttribute("aid");
        String oldPassword = (String) req.getSession().getAttribute("password");
        String password = req.getParameter("password");
        String passwordCheck = req.getParameter("passwordCheck");
        String passwordNew = req.getParameter("passwordNew");

        password = Util.createMD5Password(password, 3);
        passwordCheck = Util.createMD5Password(passwordCheck, 3);

        if(password.equals(oldPassword)) {

            if (password.equals(passwordCheck) && !password.equals(passwordNew)) {

                Database db = new Database();
                int status = db.adminChangePassword(id, passwordNew);
                if (status != 0) {
                    req.getSession().setAttribute("password", passwordNew);
                    resp.sendRedirect(Util.base_url + "admin_dashboard.jsp");
                } else {
                    req.setAttribute("pwChangeError", "Şifre değiştirilemedi!");
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin_change_password.jsp");
                    dispatcher.forward(req, resp);
                }

            } else if (!password.equals(passwordCheck)) {

                req.setAttribute("pwChangeError", "Şifreleriniz eşleşmiyor!");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin_change_password.jsp");
                dispatcher.forward(req, resp);

            } else {

                req.setAttribute("pwChangeError", "Eski ve yeni şifreniz aynı olamaz!");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin_change_password.jsp");
                dispatcher.forward(req, resp);

            }
        }else{
            req.setAttribute("pwChangeError", "Şifreniz yanlış!");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin_change_password.jsp");
            dispatcher.forward(req, resp);
        }



    }
}

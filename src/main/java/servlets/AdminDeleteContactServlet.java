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


@WebServlet(name = "adminDeleteContactServlet", value = "/admin-delete-contact-servlet")
public class AdminDeleteContactServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cid = req.getParameter("cid");
        int cidInt = Integer.parseInt(cid);

        Database db = new Database();
        int status = db.deleteContact(cidInt);

        if(status != 0){
            resp.sendRedirect(Util.base_url + "admin_contacts.jsp");
        }else{
            req.setAttribute("contactDeleteError", "Form silinirken bir hata oluştu!");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin_contacts.jsp");
            dispatcher.forward(req, resp);
        }
    }
}

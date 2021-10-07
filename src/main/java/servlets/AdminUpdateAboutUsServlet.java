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

@WebServlet(name = "adminUpdateAboutUsServlet", value = "/admin-update-about-us-servlet")
public class AdminUpdateAboutUsServlet extends HttpServlet {

    public boolean fromPost = false;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String text = "";

        if(fromPost){
            text = (String) req.getSession().getAttribute("updatingAboutUsText")   ;
        }else{
            text = req.getParameter("text");
        }
        req.setAttribute("text", text);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin_update_about_us.jsp");
        dispatcher.forward(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String text = req.getParameter("text_area");
        Database db = new Database();
        int status = db.setAboutUs(text);

        if(status > 0){
            req.setAttribute("text", text);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/about_us.jsp");
            dispatcher.forward(req, resp);

        }else{

            String errorMessage = "Ekleme sırasında bir hata oluştu!";
            req.setAttribute("updateaboutUsError", errorMessage);
            req.getSession().setAttribute("updatingAboutUsText", text);
            fromPost = true;
            this.doGet(req, resp);

        }

    }
}

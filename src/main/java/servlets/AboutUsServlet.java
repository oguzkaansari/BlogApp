package servlets;

import utils.Database;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "aboutUsServlet", value = "/about-us-servlet")
public class AboutUsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Database db = new Database();
        String text = db.getAboutUs();
        if(text.equals("")){
            text = "Hakkımızda yazısı eklenmemiş!";
        }
        req.setAttribute("text", text);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/about_us.jsp");
        dispatcher.forward(req, resp);

    }
}

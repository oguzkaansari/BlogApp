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

@WebServlet(name = "adminDeleteBlogServlet", value = "/admin-delete-blog-servlet")
public class AdminDeleteBlogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String bid = req.getParameter("bid");
        int aid = (int) req.getSession().getAttribute("aid");
        int bidInt = Integer.parseInt(bid);

        Database db = new Database();
        int status = db.deleteBlog(bidInt, aid);

        if(status != 0){
            resp.sendRedirect(Util.base_url + "admin_dashboard.jsp");
        }else{
            req.setAttribute("blogDeleteError", "Blog silinirken bir hata oluştu!");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin_dashboard.jsp");
            dispatcher.forward(req, resp);
        }

    }
}

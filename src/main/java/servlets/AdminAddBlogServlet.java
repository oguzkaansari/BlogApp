package servlets;

import props.Blog;
import utils.Database;
import utils.Util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "adminAddBlogServlet", value = "/admin-add-blog-servlet")
public class AdminAddBlogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(Util.base_url + "admin_add_blog.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int aid = (int) req.getSession().getAttribute("aid");
        String title = req.getParameter("title");
        String type = req.getParameter("type");
        String text = req.getParameter("text");

        Blog blog = new Blog();
        blog.setAid(aid);
        blog.setTitle(title);
        blog.setType(type);
        blog.setViewCount(0);
        blog.setText(text);

        Database db = new Database();
        int status = db.insertBlog(blog);
        if(status > 0){
            resp.sendRedirect(Util.base_url + "admin_dashboard.jsp");
        }else if(status == -1){
            req.setAttribute("insertBlogError", "Aynı başlıklı bir blog zaten var!");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin_add_blog.jsp");
            dispatcher.forward(req, resp);
        }else{
            req.setAttribute("insertBlogError", "Blog eklenemedi!");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin_add_blog.jsp");
            dispatcher.forward(req, resp);

        }

    }
}

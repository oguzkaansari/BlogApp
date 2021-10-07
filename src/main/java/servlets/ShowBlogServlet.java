package servlets;

import props.Blog;
import utils.Database;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "showBlogServlet", value = "/show-blog-servlet")
public class ShowBlogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String bid = req.getParameter("bid");
        req.getSession().setAttribute("showingBid", bid);
        int bidInt = Integer.parseInt(bid);

        Database db = new Database();
        Database dbUpdateViewCount = new Database();
        Blog blog = db.getBlog(bidInt);
        req.setAttribute("currentBlog", blog);
        dbUpdateViewCount.increaseBlogViewCount(blog.getId(), blog.getViewCount());
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/show_blog.jsp");
        dispatcher.forward(req, resp);

    }
}

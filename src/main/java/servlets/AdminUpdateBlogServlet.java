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


@WebServlet(name = "adminUpdateBlogServlet", value = "/admin-update-blog-servlet")
public class AdminUpdateBlogServlet extends HttpServlet {

    public boolean fromPost = false;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int bid = 0;

        if(fromPost){
            bid = (int) req.getSession().getAttribute("updatingBid");
        }else{
            bid = Integer.parseInt(req.getParameter("bid"));
            req.getSession().setAttribute("updatingBid", bid);
        }

        Database db = new Database();
        Blog blog = db.getBlog(bid);
        req.setAttribute("currentBlog", blog);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin_update_blog.jsp");
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int bid = (int) req.getSession().getAttribute("updatingBid");

        if(bid != 0){

            String title = req.getParameter("title");
            String type = req.getParameter("type");
            String text = req.getParameter("text");

            Database db = new Database();
            Blog blog = new Blog();
            blog.setId(bid);
            blog.setTitle(title);
            blog.setType(type);
            blog.setText(text);

            int status = db.updateBlog(blog);

            if(status > 0){
                resp.sendRedirect(Util.base_url + "admin_dashboard.jsp");
            }else {
                String errorMessage = "";
                if(status == -1){
                    errorMessage = "Aynı başlığa sahip bir blog zaten var!";
                }
                if(status == 0){
                    errorMessage = "Güncelleme sırasında bir hata oluştu!";
                }
                req.setAttribute("updateBlogError", errorMessage);
                req.getSession().setAttribute("updatingBid", bid);
                fromPost = true;
                this.doGet(req, resp);
            }


        }

    }
}

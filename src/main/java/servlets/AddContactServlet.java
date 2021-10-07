package servlets;

import props.Contact;
import utils.Database;
import utils.Util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "addContactServlet", value = "/add-contact-servlet")
public class AddContactServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String message = req.getParameter("message");

        Contact contact = new Contact();
        contact.setName(name);
        contact.setEmail(email);
        contact.setPhone(phone);
        contact.setMessage(message);

        Database db = new Database();
        int status = db.addContact(contact);

        if(status > 0){
            resp.sendRedirect(Util.base_url + "contact_me.jsp");
        }else{
            req.setAttribute("addContactError", "Form gönderilemedi!");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/contact_me.jsp");
            dispatcher.forward(req, resp);
        }

    }
}

package utils;

import props.Admin;
import props.Blog;
import props.Contact;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private final String driver = "com.mysql.cj.jdbc.Driver";
    private final String url = "jdbc:mysql://localhost:3306/blog_project";
    private final String user = "root";
    private final String password = "";

    public Connection conn = null;

    public Database(){

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection Succeed");
        }catch (Exception e){
            System.err.println("Connection Error : " + e);
        }
    }

    public boolean adminLogin(String email, String password, String remember, HttpServletRequest req, HttpServletResponse resp){

        boolean status = false;

        try {
            String sql = "SELECT * FROM admin WHERE email = ? AND password = ?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, email);
            pre.setString(2, Util.createMD5Password(password, 3));
            ResultSet rs = pre.executeQuery();
            status = rs.next();

            if(status){
                //session create
                int aid = rs.getInt("id");
                String name = rs.getString("name");
                String passwordMD5 = rs.getString("password");

                req.getSession().setAttribute("aid", aid);
                req.getSession().setAttribute("name", name);
                req.getSession().setAttribute("email", email);
                req.getSession().setAttribute("password", passwordMD5);


                //cookie create
                if(remember != null && remember.equals("on")){
                    name = name.replaceAll( " ", "_");
                    String value = aid+"_"+email+"_"+passwordMD5+"_"+name;
                    Cookie cookie = new Cookie("user", value);
                    cookie.setMaxAge(60*60*24); // Oturum 24 saat açık kalır.
                    resp.addCookie(cookie);
                }
            }

        }catch(Exception e){
            System.err.println("Login Error : " + e);
        }finally {
            close();
        }

        return status;
    }

    public Admin getAdmin(int id){

        Admin admin = new Admin();

        try{

            String sql = "SELECT * FROM admin WHERE id = ?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            while(rs.next()) {

                admin.setName(rs.getString("name"));
                admin.setEmail(rs.getString("email"));

            }

        }catch (SQLException s){
            System.err.println("Get Admin Error : " + s);
        }

        return admin;
    }

    public int adminChangePassword(int id, String password){

        int status = 0;

        try{

            String sql = "UPDATE admin SET password = ? WHERE id = ?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, Util.createMD5Password(password, 3));
            pre.setInt(2, id);
            status = pre.executeUpdate();

        }catch (SQLException s){
            System.err.println("Admin Change Password Error : " + s);
        }finally {
            close();
        }

        return status;
    }

    public int insertBlog(Blog blog){

        int status = 0;

        try{
            String sql = "INSERT INTO blog VALUES ( null, ?, ?, ?, ?, ?, now())";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, blog.getAid());
            pre.setString(2, blog.getTitle());
            pre.setString(3, blog.getType());
            pre.setInt(4, blog.getViewCount());
            pre.setString(5, blog.getText());
            status = pre.executeUpdate();

        }catch (SQLException s){
            if(s instanceof SQLIntegrityConstraintViolationException ){
                System.err.println("Blog Duplicate Error!");
                status = -1;
            }else{
                System.err.println("Blog Insert Error : " + s);
            }        }finally {
            close();
        }

        return status;
    }

    public int updateBlog(Blog blog){

        int status = 0;

        try{
            String sql = "UPDATE blog SET title = ?, blog_type = ?, text = ?, date = now() WHERE id = ?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, blog.getTitle());
            pre.setString(2, blog.getType());
            pre.setString(3, blog.getText());
            pre.setInt(4, blog.getId());
            status = pre.executeUpdate();

        }catch (SQLException s){
            if(s instanceof SQLIntegrityConstraintViolationException ){
                System.err.println("Blog Duplicate Error!");
                status = -1;
            }else{
                System.err.println("Blog Update Error : " + s);
            }        }finally {
            close();
        }

        return status;
    }

    public int deleteBlog(int id, int aid){

        int status = 0;

        try {

            String sql = "DELETE FROM blog WHERE id = ? AND admin_id = ?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            pre.setInt(2, aid);

            status = pre.executeUpdate();

        }catch (Exception e){

            System.err.println("Blog Delete Error : " + e);

        }finally {
            close();
        }

        return status;
    }

    public Blog getBlog(int id){

        Blog blog = new Blog();

        try{

            String sql = "SELECT * FROM blog WHERE id = ?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            while(rs.next()) {

                blog.setId(rs.getInt("id"));
                blog.setAid(rs.getInt("admin_id"));
                blog.setTitle(rs.getString("title"));
                blog.setType(rs.getString("blog_type"));
                blog.setViewCount(rs.getInt("view_count"));
                blog.setText(rs.getString("text"));
                blog.setDate(rs.getDate("date"));

            }
        }catch (SQLException s){
            System.err.println("Blog Delete Error : " + s);
        }finally {
            close();
        }

        return blog;
    }

    public List<Blog> getBlogs(int aid){

        List<Blog> blogList = new ArrayList<>();
        System.out.println(aid);

        try{

            String sql = "SELECT * FROM blog WHERE admin_id = ?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, aid);
            ResultSet rs = pre.executeQuery();
            while(rs.next()){

                Blog blog = new Blog();
                blog.setId(rs.getInt("id"));
                blog.setAid(aid);
                blog.setTitle(rs.getString("title"));
                blog.setType(rs.getString("blog_type"));
                blog.setViewCount(rs.getInt("view_count"));
                blog.setText(rs.getString("text"));
                blog.setDate(rs.getDate("date"));
                blogList.add(blog);
            }

        }catch(SQLException s){
            System.err.println("Blog List Error : " + s);
        }finally {
            close();
        }
        return blogList;
    }

    public List<Blog> getAllBlogs(int pageCount){

        List<Blog> blogList = new ArrayList<>();

        try{

            String sql = "SELECT * FROM blog ORDER BY date DESC LIMIT ?,10";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, pageCount*10);
            ResultSet rs = pre.executeQuery();
            while(rs.next()) {

                Blog blog = new Blog();
                blog.setId(rs.getInt("id"));
                blog.setAid(rs.getInt("admin_id"));
                blog.setTitle(rs.getString("title"));
                blog.setType(rs.getString("blog_type"));
                blog.setViewCount(rs.getInt("view_count"));
                blog.setText(rs.getString("text"));
                blog.setDate(rs.getDate("date"));
                blogList.add(blog);

            }

        }catch (SQLException s){
            System.err.println("Get All Blogs Error : " + s);
        }finally {
            close();
        }

        return blogList;
    }

    public int getAllBlogsCount(){

        int count = 0;

        try{

            String sql = "SELECT COUNT(id) FROM blog ";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while(rs.next()){
                count = rs.getInt("count(id)");
            }
        }catch (SQLException s){
            System.err.println("Get All Blogs Count Error : " + s);
        }finally {
            close();
        }

        return count;
    }

    public int increaseBlogViewCount(int id, int currentViewCount){

        int status = 0;

        try{

            String sql = "UPDATE blog SET view_count = ? WHERE id = ?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, currentViewCount+1);
            pre.setInt(2, id);
            status = pre.executeUpdate();

        }catch (SQLException s){
            System.err.println("Increase View Count Error : " + s);
        }finally {
            close();
        }

        return status;
    }

    public List<Contact> getContacts(){

        List<Contact> contactList = new ArrayList<>();

        try{

            String sql = "SELECT * FROM contact_me";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while(rs.next()){

                Contact contact = new Contact();
                contact.setId(rs.getInt("id"));
                contact.setName(rs.getString("name"));
                contact.setEmail(rs.getString("email"));
                contact.setPhone(rs.getString("phone"));
                contact.setMessage(rs.getString("message"));
                contact.setDate(rs.getDate("date"));

                contactList.add(contact);
            }

        }catch(SQLException s){
            System.err.println("Contact List Error : " + s);
        }finally {
            close();
        }

        return contactList;
    }

    public int addContact(Contact contact){

        int status = 0;

        try{

            String sql = "INSERT INTO contact_me VALUES (null, ?, ?, ?, ?, now())";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, contact.getName());
            pre.setString(2, contact.getEmail());
            pre.setString(3, contact.getPhone());
            pre.setString(4, contact.getMessage());
            status = pre.executeUpdate();

        }catch (SQLException s){
            System.err.println("Set Contact Error : " + s);
        }finally {
            close();
        }

        return status;
    }

    public int deleteContact(int id){
        int status = 0;

        try{

            String sql = "DELETE FROM contact_me WHERE id = ?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            status = pre.executeUpdate();

        }catch (SQLException s){
            System.err.println("Delete Contact Error : " + s);
        }finally {
            close();
        }

        return status;
    }

    public String getAboutUs(){
        String text = "";

        try{

            String sql = "SELECT * FROM about_us";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while(rs.next()) {
                text = rs.getString("text");
            }

            }catch (SQLException s){
            System.err.println("About Us Error : " + s);
        }finally {
            close();
        }

        return text;
    }

    public int setAboutUs(String text){

        int status = 0;

        try{

            String sql = "INSERT INTO about_us VALUES (?)";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, text);
            status = pre.executeUpdate();

        }catch (SQLException s){
            System.err.println("Set About Us Error : " + s);
        }finally {
            close();
        }

        return status;
    }



    public void close(){

        try {

            if(conn != null){
                conn.close();
            }

        }catch (Exception e){
            System.err.println("Close Error : " + e);
        }
    }
}

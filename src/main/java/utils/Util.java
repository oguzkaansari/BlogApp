package utils;

import props.Admin;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.CookieHandler;

public class Util {

    public static final String base_url = "http://localhost:8080/BlogProject_war_exploded/";

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static String createMD5Password( String data, int count )  {
        String passString = MD5(data);
        for (int i = 0; i < count - 1; i++) {
            passString = MD5(passString);
        }
        return passString;
    }


    public Admin hasLoggedIn(HttpServletRequest request, HttpServletResponse response, int data){

        if(request.getCookies() != null){

            Cookie[] cookies = request.getCookies();

            for(Cookie cookie : cookies){

                if(cookie.getName().equals("user")){

                    String values = cookie.getValue();
                    System.out.println(values);
                    try {
                        String[] arr = values.split("_");
                        request.getSession().setAttribute("aid", Integer.parseInt(arr[0]));
                        request.getSession().setAttribute("email", arr[1]);
                        request.getSession().setAttribute("password", arr[2]);

                        StringBuilder name = new StringBuilder("");
                        for(int i = 3; i < arr.length; i++){
                            if(i  == arr.length - 1){
                                name.append(arr[i]);
                            }else{
                                name.append(arr[i]).append(" ");
                            }
                            request.getSession().setAttribute("name", name.toString());

                        }
                    } catch (NumberFormatException e) {
                        Cookie cookie1 = new Cookie("user", "");
                        cookie1.setMaxAge(0);
                        response.addCookie(cookie1);
                    }
                    break;

                }
            }

        }

        Object sessionObj = request.getSession().getAttribute("aid");
        Admin admin = new Admin();
        if(sessionObj == null){

            try {
                response.sendRedirect(base_url + "admin_login.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{

            int aid = (int) request.getSession().getAttribute("aid");
            String name = (String) request.getSession().getAttribute("name");
            String email = (String) request.getSession().getAttribute("email");
            String passwordMD5 = (String) request.getSession().getAttribute("password");

            admin.setId(aid);
            admin.setName(name);
            admin.setEmail(email);
            admin.setPassword(passwordMD5);

            if(data == 1){
                try {
                    response.sendRedirect(base_url + "admin_dashboard.jsp");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return admin;
    }
}

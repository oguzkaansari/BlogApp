<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="props.Blog" %>

<jsp:useBean id="util" class="utils.Util"></jsp:useBean>
<jsp:useBean id="admin" class="props.Admin"></jsp:useBean>
<jsp:useBean id="db" class="utils.Database"></jsp:useBean>
<html>
<head>
    <title>Hakkımda</title>

    <style>
        body {
            background-image: url('images/background_image.jpg');
            background-position: center;
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-size: cover;
        }
    </style>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">

</head>

<body>

<%
    boolean aidStatus = request.getSession().getAttribute("aid") != null;
    String text = (String) request.getAttribute("text");
    if(aidStatus){
        admin = util.hasLoggedIn(request, response, 0);
        int id = admin.getId();
        String name = admin.getName();
        String email = admin.getEmail();
%>

<nav class="navbar navbar-expand-sm bg-warning navbar-light">
    <div class="container-fluid ">

        <a class="navbar-brand" href="#" >
            <img src="images/person_icon.svg" alt="" width="50" height="50" >
        </a>
        <div class="collapse navbar-collapse" id="navbarSupportedContentAdmin">

            <div class="me-3">
                <c:out value="${name}"></c:out>
                <br/>
                <c:out value="${email}"></c:out>
            </div>

            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="admin_dashboard.jsp">
                        <b>Bloglar</b>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="about-us-servlet">
                        <b>Hakkımızda</b>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="admin_contacts.jsp">
                        <b>İletişim Formları</b>
                    </a>
                </li>
            </ul>
            <ul class="navbar-nav ms-auto">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <img src="images/settings_icon.svg" alt="" width="30" height="30">
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="admin-change-pw-servlet">Şifre Değiştir</a></li>
                        <li><a class="dropdown-item" href="admin-logout-servlet">Çıkış</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<% }else{ %>

<nav class="navbar navbar-expand-sm bg-warning navbar-light">
    <div class="container-fluid ">

        <a class="navbar-brand" href="index.jsp" >
            <img src="images/home_icon.svg" alt="" width="30" height="30" >
        </a>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">

            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="about-us-servlet">
                        <b>Hakkımızda</b>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="contact_me.jsp">
                        <b>Bize Ulaşın</b>
                    </a>
                </li>
            </ul>
            <ul class="navbar-nav ms-auto">
                <a class="nav-link" href="admin-login-servlet" >
                    <b>Yönetici Girişi</b>
                    <img src="images/login_icon.svg" alt="" width="30" height="30" style="vertical-align:middle">
                </a>
            </ul>
        </div>

    </div>
</nav>

<%}%>

<div class="container ">
    <div class="row - mt-5" >
        <div style="padding: unset">

            <% if(aidStatus){ %>
                <a href="admin-update-about-us-servlet?text=<%=text%>" class="btn btn-warning btn-sn" style="float: right; margin: fill">
                    Düzenle
                    <img src="images/update_icon.svg" alt="" width="30" height="30" style="vertical-align:middle">
                </a>
            <%}%>
        </div>
    </div>
    <div class="row - mt-5">
        <div class="bg-light text-dark rounded p-5">
            <div class="mb-3">
                <label style="font-size:30px;">Hakkımızda</label>
            </div>

            <div class="mb-3">
                <p> <%=text%></p>
            </div>


        </div>
    </div>
</div>


<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</body>
</html>


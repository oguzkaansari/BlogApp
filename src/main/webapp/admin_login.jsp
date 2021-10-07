<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Giriş</title>

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

<div class="container">

    <div class="row - mt-5"></div>
    <div class="row - mt-5"></div>
    <div class="row - mt-5"></div>

    <div class="row - mt-5">
        <div class = "col-sm-4"></div>
        <div class="col-sm-4 text-dark rounded p-5" style="background-color: rgba(234, 234, 220, 0.75)">
            <form action="admin-login-servlet" method = "post" class="justify-content-center">

                <div class="mb-3">
                    <p style="font-size:40px;">Yönetici Girişi</p>

                    <%
                        Object error = request.getAttribute("loginError");
                        if(error != null){
                            String errorMessage = " " + error;
                    %>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>Hata</strong> <%=errorMessage%>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <% } %>

                </div>
                <div class="mb-3">
                    <input type = "email" class = "form-control" name = "email" placeholder="E-Mail" required>
                </div>

                <div class="mb-3">
                    <input type = "password" class = "form-control" name = "password" placeholder="Şifre" required>
                </div>

                <div class="mb-3 form-check">
                    <input name = "remember_me" type="checkbox" class="form-check-input" id="exampleCheck1">
                    <label class="form-check-label" for="exampleCheck1">Beni Hatırla</label>
                </div>

                <div class="col text-center">
                    <input class = "btn btn-warning" style="width:100%" type = "submit" value = "Giriş">
                </div>
            </form>
        </div>
    </div>
</div>


<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</body>>
</html>
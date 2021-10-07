<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="db" class="utils.Database"></jsp:useBean>
<jsp:useBean id="dbGetAdmin" class="utils.Database"></jsp:useBean>
<jsp:useBean id="dbGetCount" class="utils.Database"></jsp:useBean>


<%
    boolean isFirstTime = request.getSession().getAttribute("pageNumber") == null;
    if(isFirstTime){
        request.getSession().setAttribute("pageNumber", 1);
        request.setAttribute("allBlogList", db.getAllBlogs(0));
        double allBlogsCount = dbGetCount.getAllBlogsCount();
        int pageCount = (int) Math.ceil(allBlogsCount/10);
        request.getSession().setAttribute("pageCount", pageCount);

    }else{
        int pageNumber = (int) request.getSession().getAttribute("pageNumber");
        int pageCount = (int) request.getSession().getAttribute("pageCount");
        request.setAttribute("pageCount", pageCount);
        request.setAttribute("allBlogList", db.getAllBlogs(pageNumber-1));
    }
%>

<html>
<head>
    <title>Anasayfa</title>

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
                <a class="nav-link" href="admin-login-servlet?data=${1}" >
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

    <div class="row - mt-5">

        <c:if test="${allBlogList.size() > 0}">
            <c:forEach items="${allBlogList}" var="item">
                <div class = "col-sm-3"></div>

                <div class="col-sm-6 text-dark rounded p-5" style="background-color: rgba(234, 234, 220, 0.75)">

                    <div class="col">

                        <p style="font-size:20px"><b><c:out value="${item.title}"></c:out></b></p>
                        <br>

                        <c:out value="${dbGetAdmin.getAdmin(item.aid).name}"></c:out>
                        <c:out value="(${item.date})"></c:out>
                    </div>
                    <div class="col">
                        <a class="btn btn-warning float-end" href="show-blog-servlet?bid=${item.id}">
                            <b style="font-size:15px "><u>İçeriği Gör</u></b>
                            <img src="images/read_icon.svg" alt="" width="20" height="20" style="vertical-align:middle">
                        </a>

                    </div>
                </div>
                <div class="row - mt-5"></div>

            </c:forEach>
        </c:if>

        <div class="row - mt-5"></div>

        <div class="col-sm-2"></div>
        <div class="col-sm-8 bg-transparent text-center">

            <c:forEach begin="1" end="${pageCount}" varStatus="loop">

            <a class="btn btn-warning" href="home-servlet?pageNumber=${loop.index}">
                <b style="font-size:15px"><u>${loop.index}</u></b>
            </a>


            </c:forEach>
        </div>
    </div>
    <div class="row - mt-5"></div>

</div>



<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</body>
</html>
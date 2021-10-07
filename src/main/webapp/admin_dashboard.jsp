<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="util" class="utils.Util"></jsp:useBean>
<jsp:useBean id="admin" class="props.Admin"></jsp:useBean>
<jsp:useBean id="db" class="utils.Database"></jsp:useBean>
<%
    admin = util.hasLoggedIn(request, response, 0);
    int id = admin.getId();
    String name = admin.getName();
    String email = admin.getEmail();
    request.setAttribute("blogList", db.getBlogs(id));

%>


<html>
<head>
    <title>Yönetim Paneli</title>

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

        <a class="navbar-brand" href="#" >
            <img src="images/person_icon.svg" alt="" width="50" height="50" >
        </a>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">

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

<div class="container ">
    <div class="row - mt-5" >
        <div style="padding: unset">
            <a href="admin-add-blog-servlet" class="btn btn-warning btn-sn" style="float: right; margin: fill">
                Yeni Blog
                <img src="images/add_icon.svg" alt="" width="30" height="30" style="vertical-align:middle">            </a>

        </div>
    </div>

    <div class="row - mt-5">
        <div class="bg-light text-dark rounded p-5">

            <h2>Blog Listesi</h2>

            <div class="mb-3">
                <%
                    Object error = request.getAttribute("blogDeleteError");
                    if(error != null){
                        String errorMessage = " " + error;
                %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong>Hata</strong> <%=errorMessage%>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% } %>

            </div>

            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Başlık</th>
                    <th scope="col">Tür</th>
                    <th scope="col">Tarih</th>
                    <th scope="col">Görüntülenme Sayısı</th>
                    <th scope="col">İşlemler</th>

                </tr>
                </thead>
                <tbody>


                <c:if test="${blogList.size() == 0}">
                    <tr>Blog Yok</tr>
                </c:if>

                <c:if test="${blogList.size() > 0}">
                    <c:forEach items="${blogList}" var="item">
                        <tr>
                            <th scope="row"><c:out value="${item.id}"></c:out></th>
                            <td><c:out value="${item.title}"></c:out></td>
                            <td><c:out value="${item.type}"></c:out></td>
                            <td><c:out value="${item.date}"></c:out></td>
                            <td><c:out value="${item.viewCount}"></c:out></td>

                            <td>
                                <a href="show-blog-servlet?bid=${item.id}" class="btn btn-warning btn-sn">Görüntüle</a>
                                <a href="admin-update-blog-servlet?bid=${item.id}" class="btn btn-warning btn-sn">Düzenle</a>
                                <a onclick="return show_alert()" href="admin-delete-blog-servlet?bid=${item.id}" class = "btn btn-danger btn-sn">Sil</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>

                </tbody>
            </table>
        </div>
    </div>
</div>







<script>
    function show_alert(){
        return confirm("Silme istediğinizden emin misiniz?")
    }
</script>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</body>
</html>

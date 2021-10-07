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
    request.setAttribute("contactList", db.getContacts());
%>


<html>
<head>
    <title>İletişim Formları</title>

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

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

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

<div class="modal fade" id="messageModel" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="titleId"></h5>
            </div>
            <div class="modal-body">
                <p type="text" id="messageId"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning" data-bs-dismiss="modal" >Tamam</button>
            </div>
        </div>
    </div>
</div>

<div class="container ">
    <div class="row - mt-5" ></div>

    <div class="row - mt-5">
        <div class="bg-light text-dark rounded p-5">

            <h2>İletişim Formları</h2>
            <%
                Object error = request.getAttribute("contactDeleteError");
                if(error != null){
                    String errorMessage = " " + error;
            %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>Hata</strong> <%=errorMessage%>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <% } %>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">İsim</th>
                    <th scope="col">E-Mail</th>
                    <th scope="col">Tel No</th>
                    <th scope="col">Tarih</th>

                </tr>
                </thead>
                <tbody>

                <c:if test="${contactList.size() == 0}">
                    <tr>İletişim Formu Yok</tr>
                </c:if>

                <c:if test="${contactList.size() > 0}">
                    <c:forEach items="${contactList}" var="item">
                        <tr>
                            <th scope="row"><c:out value="${item.id}"></c:out></th>
                            <td><c:out value="${item.name}"></c:out></td>
                            <td><c:out value="${item.email}"></c:out></td>
                            <td><c:out value="${item.phone}"></c:out></td>
                            <td><c:out value="${item.date}"></c:out></td>


                            <td>
                                <a data-bs-toggle="modal" data-bs-target="#messageModel" data-message-id ="${item.message}" data-title-id ="${item.name}" class="openMessageModel btn btn-warning btn-sn">Görüntüle</a>
                                <a onclick="return show_alert()" href="admin-delete-contact-servlet?cid=${item.id}" class = "btn btn-danger btn-sn">Sil</a>
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
        return confirm("Silmek istediğinizden emin misiniz?")
    }
</script>

<script>
    $(document).on("click", ".openMessageModel", function() {

        var message = $(this).data('message-id');
        var title = $(this).data('title-id');

        $(".modal-header #titleId").text(title)
        $(".modal-body #messageId").text(message)

    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</body>
</html>

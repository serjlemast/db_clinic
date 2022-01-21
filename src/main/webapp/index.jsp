<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>work with role</title>

    <script type="text/javascript">
        let baseUrl = '<%= request.getRequestURL() %>'

        let newUserNameValue = document.getElementById("newUserName").value;
        let newUserPasswordValue = document.getElementById("newUserPassword").value;
        let newUserPhoneValue = document.getElementById("newUserPhone").value;
        let newUserFirstNameValue = document.getElementById("newUserFirstName").value;
        let newUserSecondNameValue = document.getElementById("newUserSecondName").value;
        let newUserIdValue = document.getElementById("newUserId").value;
        let newUserRoleName = document.getElementById("selectRoleButton").value;

        let user = {
            username: newUserNameValue,
            password: newUserPasswordValue,
            phone: newUserPhoneValue,
            firstName: newUserFirstNameValue,
            secondName: newUserSecondNameValue,
            roleName: newUserRoleName
        };
        // todo drygoi
    </script>

    <script type="text/javascript" src="scripts/user_script.js"></script>
    <script type="text/javascript" src="scripts/role_script.js"></script>
    <script type="text/javascript" src="webjars/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/popper.js/1.14.3/umd/popper.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <link type="text/css" rel="stylesheet" href="webjars/bootstrap/4.1.3/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="bd-example bd-example-tabs">
            <ul class="nav nav-tabs" id="myTab" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active show" id="home-tab" data-toggle="tab" href="#userTab" role="tab"
                       aria-controls="userTab" aria-selected="false">User</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="profile-tab" data-toggle="tab" href="#roleTab" role="tab"
                       aria-controls="roleTab" aria-selected="false">Role</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="contact-tab" data-toggle="tab" href="#categoryTab" role="tab"
                       aria-controls="categoryTab" aria-selected="false">Category</a>
                </li>
            </ul>
            <div class="tab-content" id="myTabContent">
                <div class="tab-pane fade active show" id="userTab" role="tabpanel" aria-labelledby="home-tab">
                    <jsp:include page="/pages/user_component.jsp"/>
                </div>
                <div class="tab-pane fade" id="roleTab" role="tabpanel" aria-labelledby="profile-tab">
                    <jsp:include page="/pages/role_component.jsp"/>
                </div>
                <div class="tab-pane fade" id="categoryTab" role="tabpanel" aria-labelledby="contact-tab">
                    <jsp:include page="/pages/category_component.jsp"/>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

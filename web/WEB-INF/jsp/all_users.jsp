<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="/WEB-INF/custom.tld" %>

<!DOCTYPE html>
<html>
<head>
    <title>Users</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <link href="../../css/car-rental-style.css" rel="stylesheet">
    <link href="../../font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.local" var="locale"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
    <fmt:message bundle="${locale}" key="local.welcomePrivateOfficeAdmin" var="welcomeAdmin"/>
    <fmt:message bundle="${locale}" key="local.adminPrivateOffice" var="adminPrivateOffice"/>
    <fmt:message bundle="${locale}" key="local.logOut" var="logOut"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.mUsers" var="mUsers"/>
    <fmt:message bundle="${locale}" key="local.userNumber" var="nUser"/>
    <fmt:message bundle="${locale}" key="local.mLogin" var="mLogin"/>
    <fmt:message bundle="${locale}" key="local.mType" var="mType"/>
    <fmt:message bundle="${locale}" key="local.mLastName" var="mLastName"/>
    <fmt:message bundle="${locale}" key="local.mFirstName" var="mFirstName"/>
    <fmt:message bundle="${locale}" key="local.mPhone" var="mPhone"/>
    <fmt:message bundle="${locale}" key="local.eMail" var="email"/>
    <fmt:message bundle="${locale}" key="local.mUserType" var="mUserType"/>
    <fmt:message bundle="${locale}" key="local.mAdminType" var="mAdmin"/>
    <fmt:message bundle="${locale}" key="local.mDetails" var="mDetails"/>
    <fmt:message bundle="${locale}" key="local.toPrivOffice" var="toPrivOffice"/>
    <fmt:message bundle="${locale}" key="local.mPage" var="mPage"/>
    <fmt:message bundle="${locale}" key="local.mNoUsers" var="mNoUsers"/>
    <fmt:message bundle="${locale}" key="local.viewOrders" var="viewOrders"/>
    <fmt:message bundle="${locale}" key="local.viewUsers" var="viewUsers"/>
    <fmt:message bundle="${locale}" key="local.addCar" var="addCar"/>
</head>
<body>

<%@include file="../jspf/navigation.jspf" %>

<div class="container">
    <c:if test="${sessionScope.role == 'ADMINISTRATOR'}">
        <div class="col-lg-12">
            <h1 class="page-header">
                    ${mUsers}
            </h1>
            <ol class="breadcrumb">
                <li>
                    <form action="Controller" method="get" class="btn btn-link">
                        <input type="hidden" name="command" value="to-home-page">
                        <input type="submit" value="${home}" class="btn btn-link">
                    </form>
                </li>
                <li>
                    <form action="Controller" method="get" class="btn btn-link">
                        <input type="hidden" name="command" value="to-priv-office-admin">
                        <input type="submit" value="${privateOffice}" class="btn btn-link">
                    </form>
                </li>
                <li class="active">${mUsers}</li>
            </ol>
        </div>


        <c:if test="${requestScope.noOrders == true }">
            <p>${mNoUsers}</p>
        </c:if>

        <div class="well col-lg-12">
            <c:forEach var="user" items="${sessionScope.allUsers}">
                <div class="div-order">

                    <table border="1" width="100%">
                        <thead>
                        <tr>
                            <th>${nUser}</th>
                            <th>${mLogin}</th>
                            <th>${mLastName}</th>
                            <th>${mFirstName}</th>
                            <th>${email}</th>
                            <th>${mPhone}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><c:out value="${user.userID}"/></td>
                            <td><c:out value="${user.login}"/></td>
                            <td><c:out value="${user.surName}"/></td>
                            <td><c:out value="${user.name}"/></td>
                            <td><c:out value="${user.email}"/></td>
                            <td><c:out value="${user.phone}"/></td>
                        </tr>
                        </tbody>
                    </table>

                    <br/>

                    <div class="btn-block">
                        <form action="Controller" method="get">
                            <input type="hidden" name="selectedUserId" value="${user.userID}">
                            <input type="hidden" name="command" value="view-user">
                            <input type="submit" value="${mDetails}" class="btn btn-info"/>
                        </form>
                    </div>
                </div>
                <hr/>

            </c:forEach>

        </div>

        <div class="btn-group">
            <c:forEach var="i" begin="1" end="${requestScope.amountPages}">
                <form action="Controller" method="get" class="btn pag">
                    <input type="hidden" name="command" value="view-all-users">
                    <input type="hidden" name="pageNumber" value="${i}"/>
                    <c:if test="${requestScope.pageNumber == i}">
                        <input type="submit" value="${i}" class="btn btn-default active"/>
                    </c:if>
                    <c:if test="${requestScope.pageNumber != i}">
                        <input type="submit" value="${i}" class="btn btn-default"/>
                    </c:if>
                </form>
            </c:forEach>
            <p><c:out value="${mPage}: ${requestScope.pageNumber}"/></p>
        </div>
    </c:if>

    <c:if test="${sessionScope.role != 'ADMINISTRATOR'}">
        <h1 class="page-header"></h1>

        <form action="Controller" method="get">
            <input type="hidden" name="command" value="to-home-page">
            <input type="submit" value="${home}" class="btn btn-primary">
        </form>
    </c:if>

    <hr/>
    <%@include file="../jspf/footer.jspf" %>
</div>

</body>
</html>

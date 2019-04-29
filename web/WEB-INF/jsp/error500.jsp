<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="/WEB-INF/custom.tld" %>

<!DOCTYPE html>
<html>
<head>
    <title>ERROR 500</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <link href="../../css/car-rental-style.css" rel="stylesheet">
    <link href="../../font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.local" var="locale"/>

    <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="local.signIn" var="signIn"/>
    <fmt:message bundle="${locale}" key="local.registration" var="registration"/>
    <fmt:message bundle="${locale}" key="local.login" var="login"/>
    <fmt:message bundle="${locale}" key="local.password" var="password"/>
    <fmt:message bundle="${locale}" key="local.messageInvLogin" var="messageInvLogin"/>
    <fmt:message bundle="${locale}" key="local.messageInvLogin" var="invLogin"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.logOut" var="logOut"/>

    <fmt:message bundle="${locale}" key="local.mERROR500" var="mError"/>
    <fmt:message bundle="${locale}" key="local.mErrorMessage500" var="mErrorMessage"/>
    <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
</head>
<body>

<%@include file="../jspf/navigation.jspf" %>

<div class="container">

    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">${mError}
                <small>${mErrorMessage}</small>
            </h1>
            <ol class="breadcrumb">
                <li>
                    <form action="Controller" method="get" class="btn btn-link">
                        <input type="hidden" name="command" value="to-home-page">
                        <input type="submit" value="${home}" class="btn btn-link">
                    </form>
                </li>
                <li class="active">Error 500</li>
            </ol>
        </div>
    </div>

    <hr/>
    <%@include file="../jspf/footer.jspf" %>

</div>

<script src="../../js/jquery.js"></script>
<script src="../../js/bootstrap.min.js"></script>

</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="/WEB-INF/custom.tld" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Success</title>
    <meta charset="utf-8">
<%--    <link href="css/bootstrap.min.css" rel="stylesheet">--%>
<%--    <link href="css/car-rental-style.css" rel="stylesheet">--%>
<%--    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">--%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.local" var="locale"/>
    <fmt:message bundle="${locale}" key="local.mStatusUpdated" var="mStatusUpdated"/>
    <fmt:message bundle="${locale}" key="local.mDmgPriceUpdated" var="mDmgPriceUpdated"/>
    <fmt:message bundle="${locale}" key="local.mRealFromDateUpdated" var="realDateFromUpdated"/>
    <fmt:message bundle="${locale}" key="local.mRealToDateUpdated" var="realDateToUpdated"/>
    <fmt:message bundle="${locale}" key="local.carAddedMessage" var="carAdded"/>
    <fmt:message bundle="${locale}" key="local.carDeletedMessage" var="carDeleted"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
    <fmt:message bundle="${locale}" key="local.logOut" var="logOut"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.viewOrders" var="viewOrders"/>
    <fmt:message bundle="${locale}" key="local.viewUsers" var="viewUsers"/>
    <fmt:message bundle="${locale}" key="local.addCar" var="addCar"/>
    <fmt:message bundle="${locale}" key="local.mSuccess" var="mSuccess"/>
</head>
<body>

<%@include file="WEB-INF/jspf/navigation.jspf" %>

<div class="container">

    <c:if test="${sessionScope.role == 'ADMINISTRATOR'}">

        <div class="col-lg-12">
            <h1 class="page-header"></h1>

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
                <li>
                    <form action="Controller" method="get" class="btn">
                        <input type="hidden" name="command" value="view-orders-admin">
                        <input type="submit" value="${viewOrders}" class="btn btn-link">
                    </form>
                </li>
                <li class="active">${mSuccess}</li>
            </ol>
        </div>
        <hr/>
    </c:if>

    <h2>
        <c:if test="${sessionScope.carSuccessfulAdded == true}">
            <p class="text-primary">${carAdded}</p>
            <c:set scope="session" var="carSuccessfulAdded" value="false"/>
        </c:if>

        <c:if test="${sessionScope.carSuccessfulDeleted == true}">
            <p class="text-primary">${carDeleted}</p>
            <c:set scope="session" var="carSuccessfulDeleted" value="false"/>
        </c:if>

        <c:if test="${sessionScope.successfulUpdatedStatus == true}">
            <p class="text-primary">${mStatusUpdated}</p>
            <c:set scope="session" var="successfulUpdatedStatus" value="false"/>
        </c:if>

        <c:if test="${sessionScope.dmgPriceUpdated == true}">
            <p class="text-primary">${mDmgPriceUpdated}</p>
            <c:set scope="session" var="dmgPriceUpdated" value="false"/>
        </c:if>

        <c:if test="${sessionScope.dateFromUpdated == true}">
            <p class="text-primary">${realDateFromUpdated}</p>
            <c:set scope="session" var="dateFromUpdated" value="false"/>
        </c:if>

        <c:if test="${sessionScope.dateToUpdated == true}">
            <p class="text-primary">${realDateToUpdated}</p>
            <c:set scope="session" var="dateToUpdated" value="false"/>
        </c:if>
    </h2>

    <c:if test="${sessionScope.role != 'ADMINISTRATOR'}">
        <form action="Controller" method="get">
            <input type="hidden" name="command" value="to-home-page">
            <input type="submit" value="${home}" class="btn btn-info">
        </form>
    </c:if>

    <hr/>

    <%@include file="/WEB-INF/jspf/footer.jspf" %>

</div>


<%--<script src="js/jquery.js"></script>--%>
<%--<script src="js/bootstrap.min.js"></script>--%>

</body>
</html>
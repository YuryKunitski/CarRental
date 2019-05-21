<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="/WEB-INF/custom.tld" %>

<!DOCTYPE html>
<html>
<head>
    <title>Date</title>
    <meta charset="utf-8">
<%--    <link href="css/bootstrap.min.css" rel="stylesheet">--%>
<%--    <link href="css/car-rental-style.css" rel="stylesheet">--%>
<%--    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">--%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.local" var="locale"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
    <fmt:message bundle="${locale}" key="local.mStatusUpdated" var="mStatusUpdated"/>
    <fmt:message bundle="${locale}" key="local.mInvalidDate" var="mInvalidDate"/>
    <fmt:message bundle="${locale}" key="local.setDate" var="mSetDate"/>
    <fmt:message bundle="${locale}" key="local.logOut" var="logOut"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.viewOrders" var="viewOrders"/>
    <fmt:message bundle="${locale}" key="local.viewUsers" var="viewUsers"/>
    <fmt:message bundle="${locale}" key="local.addCar" var="addCar"/>
    <fmt:message bundle="${locale}" key="local.realDateFrom" var="dateFrom"/>
    <fmt:message bundle="${locale}" key="local.realDateTo" var="dateTo"/>
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

        <h2>
            <c:if test="${sessionScope.successfulUpdatedStatus == true}">
                <p class="text-primary">${mStatusUpdated}</p>
                <c:set scope="session" var="successfulUpdatedStatus" value="false"></c:set>
            </c:if>
        </h2>
        <c:if test="${requestScope.invalidDateFrom == true}">
            <p class="text-danger">${mInvalidDate}</p>
        </c:if>

        <c:if test="${requestScope.invalidDateTo == true}">
            <p class="text-danger">${mInvalidDate}</p>
        </c:if>

<%--        <c:if test="${sessionScope.fromDate == true}">--%>
<%--            <p>From date:</p>--%>

<%--            <form action="Controller" method="post">--%>
<%--                <input type="date" name="real-date-from" required>--%>
<%--                <input type="time" name="real-time-from" required>--%>
<%--                <input type="hidden" name="selectedOrderId" value="${requestScope.selectedOrder.id}">--%>
<%--                <input type="hidden" name="command" value="update-real-date-from">--%>
<%--                <hr/>--%>
<%--                <input type="submit" name="${mSetDate}" class="btn bg-info">--%>
<%--            </form>--%>
<%--        </c:if>--%>

<%--        <c:if test="${sessionScope.toDate == true}">--%>
<%--            <p>To date:</p>--%>

<%--            <form action="Controller" method="post">--%>
<%--                <input type="date" name="real-date-to" required>--%>
<%--                <input type="time" name="real-time-to" required>--%>
<%--                <input type="hidden" name="selectedOrderId" value="${requestScope.selectedOrder.id}">--%>
<%--                <input type="hidden" name="command" value="update-real-date-to">--%>
<%--                <hr/>--%>
<%--                <input type="submit" name="${mSetDate}" class="btn btn-info">--%>
<%--            </form>--%>
<%--        </c:if>--%>
    </c:if>
    <c:if test="${sessionScope.role != 'ADMINISTRATOR'}">
        <div class="divSubMenu">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="to-home-page">
                <input type="submit" value="${home}" class="btn btn-info">
            </form>
        </div>
    </c:if>

    <hr/>

    <%@include file="/WEB-INF/jspf/footer.jspf" %>

</div>

<%--<script src="js/jquery.js"></script>--%>
<%--<script src="js/bootstrap.min.js"></script>--%>

</body>
</html>

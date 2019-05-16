<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="/WEB-INF/custom.tld" %>

<!DOCTYPE html>
<html>
<head>
    <title>User Success</title>
    <meta charset="utf-8">
<%--    <link href="css/bootstrap.min.css" rel="stylesheet">--%>
<%--    <link href="css/car-rental-style.css" rel="stylesheet">--%>
<%--    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">--%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.local" var="locale"/>
    <fmt:message bundle="${locale}" key="local.mSuccessfulPaymentForOrder" var="mSuccessfulPayment"/>
    <fmt:message bundle="${locale}" key="local.mOrderAdded" var="orderAded"/>
    <fmt:message bundle="${locale}" key="local.mOrderCanceled" var="orderCanceled"/>
    <fmt:message bundle="${locale}" key="local.mSuccessfulPaymentForDmg" var="mSuccessfulPaymentForDmg"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
    <fmt:message bundle="${locale}" key="local.logOut" var="logOut"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.orders" var="myOrders"/>
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.viewOrders" var="mViewAllOrders"/>
    <fmt:message bundle="${locale}" key="local.mMakeOrder" var="mMakeOrder"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.orders" var="myOrders"/>
    <fmt:message bundle="${locale}" key="local.mSuccess" var="mSuccess"/>
</head>
<body>

<%@include file="WEB-INF/jspf/navigation.jspf" %>

<div class="container">

    <c:if test="${sessionScope.user.type.equals('user')}">
        <div class="col-lg-12">
            <h1 class="page-header"></h1>

            <div class="col-lg-12">
                <ol class="breadcrumb">
                    <li>
                        <form action="Controller" method="get" class="btn btn-link">
                            <input type="hidden" name="command" value="to-home-page">
                            <input type="submit" value="${home}" class="btn btn-link">
                        </form>
                    </li>
                    <li>
                        <form action="Controller" method="get" class="btn btn-link">
                            <input type="hidden" name="command" value="to-priv-office-user">
                            <input type="submit" value="${privateOffice}" class="btn btn-link">
                        </form>
                    </li>
                    <li>
                        <form action="Controller" method="get" class="btn btn-link">
                            <input type="hidden" name="command" value="view-orders-user">
                            <input type="submit" value="${myOrders}" class="btn btn-link">
                        </form>
                    </li>
                    <li class="active">${mSuccess}</li>
                </ol>
            </div>
        </div>
    </c:if>

    <hr/>

    <div class="col-lg-12 well">
        <h2>
            <c:if test="${sessionScope.orderSuccessfulMade == true}">
                <p class="text-primary">${orderAded}</p>
                <c:set scope="session" var="orderSuccessfulMade" value="false"/>
            </c:if>

            <c:if test="${sessionScope.successfulCanceled == true}">
                <p class="text-primary">${orderCanceled}</p>
                <c:set scope="session" var="successfulCanceled" value="false"/>
            </c:if>

            <c:if test="${sessionScope.successfulPayment == true}">
                <p class="text-primary">${mSuccessfulPayment}</p>
                <c:set scope="session" var="successfulPayment" value="false"/>
            </c:if>

            <c:if test="${sessionScope.successfulPaymentForDmg == true}">
                <p class="text-primary">${mSuccessfulPaymentForDmg}</p>
                <c:set scope="session" var="successfulPaymentForDmg" value="false"/>
            </c:if>
        </h2>
    </div>
    <c:if test="${!sessionScope.user.type.equals('user')}">
        <form action="Controller" method="get">
            <input type="hidden" name="command" value="to-home-page">
            <input type="submit" value="${home}" class="btn btn-info">
        </form>
    </c:if>

    <hr/>
    <%@include file="/WEB-INF/jspf/footer.jspf" %>

</div>

<script src="js/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>

</body>
</html>
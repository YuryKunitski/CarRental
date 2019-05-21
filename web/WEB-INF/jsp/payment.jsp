<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="/WEB-INF/custom.tld" %>

<!DOCTYPE html>
<html>
<head>
    <title>Orders</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
<%--    <link href="../../css/bootstrap.min.css" rel="stylesheet">--%>
<%--    <link href="../../css/car-rental-style.css" rel="stylesheet">--%>
<%--    <link href="../../font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">--%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.local" var="locale"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
    <fmt:message bundle="${locale}" key="local.privateOfficeUser" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.welcomeToOffice" var="welcomeToOffice"/>
    <fmt:message bundle="${locale}" key="local.logOut" var="logOut"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.orders" var="myOrders"/>
    <fmt:message bundle="${locale}" key="local.mOrderPrice" var="mOrderPrice"/>
    <fmt:message bundle="${locale}" key="local.mDamagePrice" var="mDmgPrice"/>
    <fmt:message bundle="${locale}" key="local.mPayForOrder" var="mPayForOrder"/>
    <fmt:message bundle="${locale}" key="local.mPayForDmg" var="mPayForDmg"/>
    <fmt:message bundle="${locale}" key="local.mPaymentPage" var="mPaymentPage"/>
    <fmt:message bundle="${locale}" key="local.mToOrder" var="mToOrder"/>
    <fmt:message bundle="${locale}" key="local.viewOrders" var="mViewAllOrders"/>
    <fmt:message bundle="${locale}" key="local.mMakeOrder" var="mMakeOrder"/>
    <fmt:message bundle="${locale}" key="local.mAllOrders" var="mAllOrders"/>
    <fmt:message bundle="${locale}" key="local.mOrder" var="mOrder"/>
</head>
<body>

<%@include file="../jspf/navigation.jspf" %>

<div class="container">
    <c:if test="${sessionScope.role == 'CUSTOMER'}">
        <div class="col-lg-12">
            <h1 class="page-header">
                    ${mPaymentPage}
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
                        <input type="hidden" name="command" value="to-priv-office-user">
                        <input type="submit" value="${privateOffice}" class="btn btn-link">
                    </form>
                </li>
                <li>
                    <form action="Controller" method="get" class="btn btn-link">
                        <input type="hidden" name="command" value="view-orders-user">
                        <input type="submit" value="${mAllOrders}" class="btn btn-link">
                    </form>
                </li>
                <li>
                    <form action="Controller" method="get" class="btn btn-link">
                        <input type="hidden" name="selectedOrderId" value="${sessionScope.selectedOrder.orderID}">
                        <input type="hidden" name="command" value="view-order-user">
                        <input type="submit" value="${mOrder}" class="btn btn-link"/>
                    </form>
                </li>
                <li class="active">
                        ${mPaymentPage}
                </li>
            </ol>
        </div>


        <div class="col-lg-12 well">
            <c:if test="${sessionScope.paymentType.equals('order')}">
                <h3>${mOrderPrice}: </h3>

                <h3>${sessionScope.selectedOrder.totalBill}$</h3>

                <form action="Controller" method="post">
                    <input type="hidden" name="command" value="pay">
                    <input type="hidden" name="processRequest" value="redirect">
                    <input type="submit" value="${mPayForOrder}" class="btn btn-primary my-info"/>
                </form>
            </c:if>
            <c:if test="${sessionScope.paymentType.equals('damage')}">
                <h3>${mDmgPrice}: </h3>

                <h3>${sessionScope.selectedOrder.damagePrice}$</h3>

                <form action="Controller" method="post">
                    <input type="hidden" name="command" value="pay-for-damage">
                    <input type="hidden" name="processRequest" value="redirect">
                    <input type="submit" value="${mPayForDmg}" class="btn btn-primary"/>
                </form>
            </c:if>
        </div>
    </c:if>
    <c:if test="${sessionScope.role != 'CUSTOMER'}">
        <h1 class="page-header"></h1>

        <form action="Controller" method="get">
            <input type="hidden" name="command" value="to-home-page">
            <input type="submit" value="${home}" class="btn btn-default">
        </form>
    </c:if>

    <hr/>
    <%@include file="../jspf/footer.jspf" %>
</div>

<%--<script src="../../js/jquery.js"></script>--%>
<%--<script src="../../js/bootstrap.min.js"></script>--%>
</body>
</html>

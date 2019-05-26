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
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <link href="../../css/car-rental-style.css" rel="stylesheet">
<%--    <link href="../../font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">--%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.local" var="locale"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
    <fmt:message bundle="${locale}" key="local.privateOfficeUser" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.welcomeToOffice" var="welcomeToOffice"/>
    <fmt:message bundle="${locale}" key="local.logOut" var="logOut"/>
    <fmt:message bundle="${locale}" key="local.toPrivOffice" var="mToPrivateOffice"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.orders" var="myOrders"/>
    <fmt:message bundle="${locale}" key="local.mOrderNumber" var="mOrderNumber"/>
    <fmt:message bundle="${locale}" key="local.model" var="mModel"/>
    <fmt:message bundle="${locale}" key="local.carClass" var="mClass"/>
    <fmt:message bundle="${locale}" key="local.mDateFrom" var="dateFrom"/>
    <fmt:message bundle="${locale}" key="local.mDateTo" var="dateTo"/>
    <fmt:message bundle="${locale}" key="local.mStatus" var="mStatus"/>
    <fmt:message bundle="${locale}" key="local.price" var="mPrice"/>
    <fmt:message bundle="${locale}" key="local.statusRejected" var="sRejected"/>
    <fmt:message bundle="${locale}" key="local.statusApproved" var="sApproved"/>
    <fmt:message bundle="${locale}" key="local.statusUndefined" var="sUndefined"/>
    <fmt:message bundle="${locale}" key="local.statusClosed" var="sClosed"/>
    <fmt:message bundle="${locale}" key="local.statusPaid" var="sPaid"/>
    <fmt:message bundle="${locale}" key="local.mDetails" var="mDetails"/>
    <fmt:message bundle="${locale}" key="local.noOrders" var="mNoOrders"/>
    <fmt:message bundle="${locale}" key="local.mPage" var="mPage"/>
    <fmt:message bundle="${locale}" key="local.viewOrders" var="mViewAllOrders"/>
    <fmt:message bundle="${locale}" key="local.mMakeOrder" var="mMakeOrder"/>
</head>
<body>

<%@include file="../jspf/navigation.jspf" %>

<div class="container">
    <c:if test="${sessionScope.role == 'CUSTOMER'}">
        <div class="col-lg-12">
            <h1 class="page-header">
                    ${myOrders}</h1>
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
                <li class="active">${myOrders}</li>
            </ol>
        </div>

        <div class="col-lg-12">
            <c:if test="${requestScope.noOrders == true }">
                <p>${mNoOrders}</p>
            </c:if>

            <div class="well">

                <c:forEach var="order" items="${sessionScope.orders}">
                    <div class="div-order">

                        <table border="1" width="100%">
                            <thead>
                            <tr>
                                <th>${mOrderNumber}</th>
                                <th>${mClass}</th>
                                <th>${mModel}</th>
                                <th>${dateFrom}</th>
                                <th>${dateTo}</th>
                                <th>${mStatus}</th>
                                <th>${mPrice}, $</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td><c:out value="${order.orderID}"/></td>
                                <td><c:out value="${order.car.carClassType}"/></td>
                                <td><c:out value="${order.car.carModel}"/></td>
                                <td><c:out value="${order.rentalStartDate}"/></td>
                                <td><c:out value="${order.rentalEndDate}"/></td>
                                <td>
                                    <c:if test="${order.status.equals('approved')}">
                                        ${sApproved}
                                    </c:if>
                                    <c:if test="${order.status.equals('undefined')}">
                                        ${sUndefined}
                                    </c:if>
                                    <c:if test="${order.status.equals('rejected')}">
                                        ${sRejected}
                                    </c:if>
                                    <c:if test="${order.status.equals('closed')}">
                                        ${sClosed}
                                    </c:if>
                                    <c:if test="${order.status.equals('paid')}">
                                        ${sPaid}
                                    </c:if>
                                </td>
                                <td><c:out value="${order.totalBill}"/></td>
                            </tr>
                            </tbody>
                        </table>

                        <div>
                            <form action="Controller" method="get" class="btn">
                                <input type="hidden" name="selectedOrderId" value="${order.orderID}">
                                <input type="hidden" name="command" value="view-order-user">
                                <input type="submit" value="${mDetails}" class="btn btn-info"/>
                            </form>
                        </div>
                    </div>
                    <hr/>
                </c:forEach>
            </div>

    </c:if>

    <c:if test="${sessionScope.role != 'CUSTOMER'}">
        <div class="col-lg-12">
            <h1 class="page-header"></h1>
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="to-home-page">
                <input type="submit" value="${home}" class="btn btn-info">
            </form>
            <hr/>
        </div>
    </c:if>

    <%@include file="../jspf/footer.jspf" %>
</div>

<%--<script src="../../js/jquery.js"></script>--%>
<%--<script src="../../js/bootstrap.min.js"></script>--%>

</body>
</html>
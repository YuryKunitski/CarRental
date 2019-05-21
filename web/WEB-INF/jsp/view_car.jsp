<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="/WEB-INF/custom.tld" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Car</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%--    <link href="../../css/bootstrap.min.css" rel="stylesheet">--%>
    <%--    <link href="../../css/car-rental-style.css" rel="stylesheet">--%>
    <%--    <link href="../../font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">--%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.local" var="locale"/>
    <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="local.signIn" var="signIn"/>
    <fmt:message bundle="${locale}" key="local.registration" var="registration"/>
    <fmt:message bundle="${locale}" key="local.login" var="login"/>
    <fmt:message bundle="${locale}" key="local.password" var="password"/>
    <fmt:message bundle="${locale}" key="local.messageInvLogin" var="invLogin"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.logOut" var="logOut"/>
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.orders" var="orders"/>
    <fmt:message bundle="${locale}" key="local.price" var="price"/>
    <fmt:message bundle="${locale}" key="local.model" var="model"/>
    <fmt:message bundle="${locale}" key="local.year" var="year"/>
    <fmt:message bundle="${locale}" key="local.class" var="carClass"/>
    <fmt:message bundle="${locale}" key="local.carInformation" var="carInfo"/>
    <fmt:message bundle="${locale}" key="local.forMakeOrderMessage" var="forMakeOrderMessage"/>
    <fmt:message bundle="${locale}" key="local.makeOrder" var="makeOrder"/>
    <fmt:message bundle="${locale}" key="local.businessClass" var="business"/>
    <fmt:message bundle="${locale}" key="local.economyClass" var="economy"/>
    <fmt:message bundle="${locale}" key="local.truck" var="truck"/>
    <fmt:message bundle="${locale}" key="local.deleteCar" var="deleteCar"/>
    <fmt:message bundle="${locale}" key="local.mThisCarIsUsedInOrders" var="mCarIsUsed"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.mRublesPerDay" var="mRublesPerDay"/>
    <fmt:message bundle="${locale}" key="local.mAllCars" var="mAutomobiles"/>
</head>
<body>

<%@include file="../jspf/navigation.jspf" %>

<div class="container">

    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">
                <c:out value="${sessionScope.selectedCar.carModel}"/>
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
                        <input type="hidden" name="command" value="view-all-cars"/>
                        <input type="submit" value="${mAutomobiles}" class="btn btn-link"/>
                    </form>
                </li>
                <li class="active">
                    ${sessionScope.selectedCar.carModel}
                </li>
            </ol>
        </div>
    </div>

    <div>
        <c:if test="${requestScope.cannotDelete}">
        <p class="text-danger my-info">
                ${mCarIsUsed}
        </p>
        </c:if>
        <div class="col-lg-12 well">
            <div class="col-lg-8">
                <img class="img-responsive car-middle-img"
                     src="data:image/jpg;base64,${sessionScope.selectedCar.image}" alt="Car"/>
            </div>

            <div class="col-lg-4">

                <p class="my-info">${model}: ${sessionScope.selectedCar.carModel}</p>

                <p class="my-info">
                    ${year}: ${sessionScope.selectedCar.yearIssue}
                </p>

                <p class="my-info">
                    ${carClass}:
                    <c:if test="${sessionScope.carClassType == 'BUSINESS'}">
                        <c:out value="${business}"/>
                    </c:if>

                    <c:if test="${sessionScope.carClassType == 'ECONOM'}">
                        <c:out value="${economy}"/>
                    </c:if>

                    <c:if test="${sessionScope.carClassType == 'TRUCK'}">
                        <c:out value="${truck}"/>
                    </c:if>

                </p>
                <hr/>

                     <p class="price">${price}: ${sessionScope.selectedCar.pricePerDay} ${mRublesPerDay}</p>

                                <hr/>
                                <c:if test="${sessionScope.role == 'CUSTOMER'}">
<%--                                    <div>--%>
<%--                                        <form action="Controller" method="get">--%>
<%--                                            <input type="hidden" name="command" value="to-make-order">--%>
<%--                                            <input type="submit" value="${makeOrder}" class="btn btn-block btn-primary my-info"/>--%>
<%--                                        </form>--%>
<%--                                    </div>--%>

                                </c:if>
                                <c:if test="${sessionScope.role == 'ADMINISTRATOR'}">
<%--                                    <div>--%>
<%--                                        <form action="Controller" method="post">--%>
<%--                                            <input type="hidden" name="command" value="delete-car">--%>
<%--                                            <input type="hidden" name="processRequest" value="redirect">--%>
<%--                                            <input type="submit" value="${deleteCar}" class="btn btn-block btn-danger my-info">--%>
<%--                                        </form>--%>
<%--                                    </div>--%>
                                </c:if>
                                <c:if test="${sessionScope.user == null}">
                                    <p class="my-info text-primary">${forMakeOrderMessage}</p>
                                </c:if>
                <%--            </div>--%>
                <%--        </div>--%>
                <%--    </div>--%>

                <%--    <hr/>--%>
                <%--    <%@include file="../jspf/footer.jspf" %>--%>
                <%--</div>--%>

                <%--<script src="../../js/jquery.js"></script>--%>
                <%--<script src="../../js/bootstrap.min.js"></script>--%>

</body>
</html>
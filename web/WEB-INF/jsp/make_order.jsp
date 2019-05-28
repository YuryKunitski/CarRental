<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="/WEB-INF/custom.tld" %>

<!DOCTYPE html>
<html>
<head>
    <title>Make order</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <link href="../../css/car-rental-style.css" rel="stylesheet">
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
    <fmt:message bundle="${locale}" key="local.orders" var="myOrders"/>
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.carIsUsed" var="carIsUsed"/>
    <fmt:message bundle="${locale}" key="local.seeUnusedCarsMessage" var="unusedCarsMsg"/>
    <fmt:message bundle="${locale}" key="local.toCarsPage" var="toCars"/>
    <fmt:message bundle="${locale}" key="local.supposedFromDate" var="fromDate"/>
    <fmt:message bundle="${locale}" key="local.supposedToDate" var="toDate"/>
    <fmt:message bundle="${locale}" key="local.mInvalidDate" var="mInvalidDate"/>
    <fmt:message bundle="${locale}" key="local.makeOrder" var="makeOrder"/>
    <fmt:message bundle="${locale}" key="local.mMakingOrder" var="mMakingOrder"/>
    <fmt:message bundle="${locale}" key="local.viewOrders" var="mViewAllOrders"/>
    <fmt:message bundle="${locale}" key="local.mMakeOrder" var="mMakeOrder"/>
    <fmt:message bundle="${locale}" key="local.mAllCars" var="mAutomobiles"/>
    <fmt:message bundle="${locale}" key="local.mFieldWithoutTags" var="mWithoutTags"/>
</head>
<body>

<%@include file="../jspf/navigation.jspf" %>

<div class="container">

    <c:if test="${sessionScope.role == 'CUSTOMER'}">
        <div class="col-lg-12">
            <h1 class="page-header">${mMakingOrder}</h1>
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
                        ${mMakingOrder}
                </li>
            </ol>
        </div>

        <div class="well col-lg-12">
            <div class="col-lg-8">
                <c:if test="${requestScope.addOrderFailed == true}">
                    <p class="text-danger">${carIsUsed}</p>

                    <p class="text-info">${unusedCarsMsg}</p>

                    <form action="Controller" method="get">
                        <input type="hidden" name="command" value="view-all-cars"/>
                        <input type="submit" value="${toCars}" class="btn btn-default"/>
                    </form>
                </c:if>
                <c:if test="${sessionScope.selectedCar.carID != null}">
                    <img class="img-responsive car-middle-img"
                         src="${sessionScope.selectedCar.image}" alt="Car"/>

                    <p class="my-info"><c:out value="${sessionScope.selectedCar.carClassType}"/>
                        <br/>
                        <c:out value="${sessionScope.selectedCar.carModel}"/></p>
                </c:if>
            </div>

            <div class="col-lg-4">
                <c:if test="${requestScope.addOrderFailed == true}">
                    <div class="btn-group">
                        <form action="Controller" method="get" class="btn">
                            <input type="hidden" name="command" value="view-orders-user">
                            <input type="submit" value="${mViewAllOrders}" class="btn btn-info">
                        </form>

                        <form action="Controller" method="get" class="btn">
                            <input type="hidden" name="command" value="view-all-cars">
                            <input type="submit" value="${mMakeOrder}" class="btn btn-info">
                        </form>
                    </div>
                </c:if>

                <form action="Controller" method="post">

                    <c:if test="${requestScope.invalidDate == true}">
                        <p class="text-danger"> ${mInvalidDate} </p>
                    </c:if>

                    <p>${fromDate}</p>
                    <input type="date" name="supposedDateFrom" value="${sessionScope.supposedDateFrom}" required/>

                    <p>${toDate}</p>
                    <input type="date" name="supposedDateTo" value="${sessionScope.supposedDateTo}" required/>



                    <hr/>
                    <input type="hidden" name="command" value="make-order">
                    <input type="hidden" name="processRequest" value="redirect">
                    <input type="submit" value="${makeOrder}" class="btn btn-primary"/>
                </form>
            </div>
        </div>
    </c:if>
    <c:if test="${sessionScope.role != 'CUSTOMER'}">
        <h1 class="page-header"></h1>

        <form action="Controller" method="get">
            <input type="hidden" name="command" value="to-home-page">
            <input type="submit" value="${home}" class="btn btn-info">
        </form>
    </c:if>

    <hr/>
    <%@include file="../jspf/footer.jspf" %>
</div>
</body>
</html>
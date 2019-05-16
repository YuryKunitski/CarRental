<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="/WEB-INF/custom.tld" %>

<!DOCTYPE html>
<html>
<head>
    <title>All cars</title>
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
    <fmt:message bundle="${locale}" key="local.messageChooseTripDate" var="chooseTripDate"/>
    <fmt:message bundle="${locale}" key="local.mInvalidDate" var="mInvalidDate"/>
    <fmt:message bundle="${locale}" key="local.supposedFromDate" var="mSupFromDate"/>
    <fmt:message bundle="${locale}" key="local.supposedToDate" var="mSupToDate"/>
    <fmt:message bundle="${locale}" key="local.viewFreeCars" var="freeCars"/>
    <fmt:message bundle="${locale}" key="local.businessClass" var="business"/>
    <fmt:message bundle="${locale}" key="local.economyClass" var="econom"/>
    <fmt:message bundle="${locale}" key="local.truck" var="truck"/>
    <fmt:message bundle="${locale}" key="local.all" var="all"/>
    <fmt:message bundle="${locale}" key="local.carAddedMessage" var="carAdded"/>
    <fmt:message bundle="${locale}" key="local.carDeletedMessage" var="carDeleted"/>
    <fmt:message bundle="${locale}" key="local.Automobiles" var="autos"/>
    <fmt:message bundle="${locale}" key="local.searchForFreeCars" var="searchForFree"/>
    <fmt:message bundle="${locale}" key="local.selectClass" var="selectClass"/>
    <fmt:message bundle="${locale}" key="local.noCars" var="mNoCars"/>
    <fmt:message bundle="${locale}" key="local.mPage" var="mPage"/>
    <fmt:message bundle="${locale}" key="local.mForMakingOrderTakeCar" var="mForMakingOrder"/>
</head>
<body>

<%@include file="../jspf/navigation.jspf" %>

<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">${autos}</h1>
            <ol class="breadcrumb">
                <li>
                    <form action="Controller" method="get" class="btn btn-link">
                        <input type="hidden" name="command" value="to-home-page">
                        <input type="submit" value="${home}" class="btn btn-link">
                    </form>
                </li>
                <li class="active">${autos}</li>
            </ol>
        </div>
    </div>

    <div class="row">
        <div class="well">
            <c:if test="${sessionScope.role != 'ADMINISTRATOR'}">
                <p class="text-primary">${mForMakingOrder}</p>
            </c:if>

            <form action="Controller" method="get" role="form">

                <div class="btn-group" data-toggle="buttons">
                    <c:forEach var="varClass" items="${sessionScope.allClasses}">
                        <label class="btn btn-default">
                            <input type="radio" name="carClass" value="${varClass}" autocomplete="off">

                            <c:if test="${varClass == 'BUSINESS'}">
                                ${business}
                            </c:if>

                            <c:if test="${varClass == 'ECONOM'}">
                                ${econom}
                            </c:if>

                            <c:if test="${varClass == 'TRUCK'}">
                                ${truck}
                            </c:if>

                        </label>
                    </c:forEach>
                </div>

                <hr/>
                <c:if test="${requestScope.invalidType == true}">
                    <p class="text-danger">
                        <c:out value="${selectClass}"/>
                    </p>
                </c:if>
                <c:if test="${sessionScope.role != 'ADMINISTRATOR'}">
                    <p class="text-primary">${chooseTripDate}</p>
                </c:if>
                <c:if test="${requestScope.invalidDate == true}">
                    <p class="text-danger">${mInvalidDate}</p>
                </c:if>
                <p>${mSupFromDate}</p>
                <input type="date" value="${sessionScope.supposedDateFrom}" name="supposedDateFrom" required/>

                <p>${mSupToDate}</p>
                <input type="date" value="${sessionScope.supposedDateTo}" name="supposedDateTo" required/>
                <hr/>
                <input type="hidden" name="command" value="view-class-unused"/>
                <input type="submit" value="${searchForFree}" class="btn btn-primary">

            </form>
        </div>
    </div>

    <div class="btn-group">
        <c:forEach var="varClass" items="${sessionScope.allClasses}">
            <form action="Controller" method="get" class="btn pag">
                <input type="hidden" name="command" value="view-class"/>
                <input type="hidden" name="carClass" value="${varClass}"/>

                <c:if test="${varClass == 'BUSINESS'}">
                    <input type="submit" value="${business}" class="btn btn-default"/>
                </c:if>

                <c:if test="${varClass == 'ECONOM'}">
                    <input type="submit" value="${econom}" class="btn btn-default"/>
                </c:if>

                <c:if test="${varClass == 'TRUCK'}">
                    <input type="submit" value="${truck}" class="btn btn-default"/>
                </c:if>

            </form>
        </c:forEach>
        <form action="Controller" method="get" class="btn pag">
            <input type="hidden" name="command" value="view-all-cars"/>
            <input type="submit" value="${all}" class="btn btn-default"/>
        </form>
    </div>

    <h2>
        <c:if test="${requestScope.carClass == 'BUSINESS'}">
            ${business}
        </c:if>
        <c:if test="${requestScope.carClass == 'ECONOM'}">
            ${econom}
        </c:if>
        <c:if test="${requestScope.carClass == 'TRUCK'}">
            ${truck}
        </c:if>

    </h2>

    <div class="row well">
        <c:if test="${requestScope.noCars == true}">
            <p>${mNoCars}</p>
        </c:if>
        <c:if test="${requestScope.noCars !=true}">
            <c:forEach var="car" items="${sessionScope.allCars}">
                <div class="col-md-4 img-portfolio">
                    <form action="Controller" method="get">
                        <input type="hidden" name="command" value="view-car"/>
                        <input type="hidden" name="selectedCarId" value="${car.carID}">
                        <button type="submit" class="btn btn-default">
                            <img class="img-responsive img-hover car-small-img"
                                 src="data:image/jpg;base64,${car.image}"/>

                            <p class="my-info"> ${car.carModel}</p>
                        </button>
                    </form>
                </div>
            </c:forEach>
        </c:if>
    </div>

<%--    <div class="btn-group">--%>
<%--        <c:if test="${requestScope.command == null && requestScope.noCars == true}">--%>
            <c:forEach var="i" begin="1" end="${requestScope.amountPages}">
                <form action="Controller" method="get" class="btn pag">
                    <input type="hidden" name="command" value="view-all-cars">
                    <input type="hidden" name="pageNumber" value="${i}"/>
                    <c:if test="${requestScope.pageNumber == i}">
                        <input type="submit" value="${i}" class="btn btn-default active"/>
                    </c:if>
                    <c:if test="${requestScope.pageNumber!=i}">
                        <input type="submit" value="${i}" class="btn btn-default"/>
                    </c:if>
                </form>
            </c:forEach>
            <p><c:out value="${mPage}: ${requestScope.pageNumber}"/></p>
<%--        </c:if>--%>

<%--        <c:if test="${requestScope.command != null && requestScope.noCars != false && requestScope.carType != null}">--%>
<%--            <ul class="pagination">--%>
<%--                <c:forEach var="i" begin="1" end="${requestScope.amountPages}">--%>
<%--                    <li>--%>
<%--                        <form action="Controller" method="get" class="btn pag">--%>
<%--                            <input type="hidden" name="carType" value="${requestScope.carType}">--%>
<%--                            <input type="hidden" name="command" value="${requestScope.command}">--%>
<%--                            <input type="hidden" name="pageNumber" value="${i}"/>--%>
<%--                            <c:if test="${requestScope.pageNumber==i}">--%>
<%--                                <input type="submit" value="${i}" class="btn btn-default active"/>--%>
<%--                            </c:if>--%>
<%--                            <c:if test="${requestScope.pageNumber!=i}">--%>
<%--                                <input type="submit" value="${i}" class="btn btn-default"/>--%>
<%--                            </c:if>--%>
<%--                        </form>--%>
<%--                    </li>--%>
<%--                </c:forEach>--%>
<%--                <p><c:out value="${mPage}: ${requestScope.pageNumber}"/></p>--%>
<%--            </ul>--%>
<%--        </c:if>--%>
<%--    </div>--%>

    <hr/>
    <%@include file="../jspf/footer.jspf" %>
</div>

<%--<script src="../../js/jquery.js"></script>--%>
<%--<script src="../../js/bootstrap.min.js"></script>--%>

</body>
</html>
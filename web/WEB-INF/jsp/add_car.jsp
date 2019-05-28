<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="/WEB-INF/custom.tld" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add car</title>
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
    <fmt:message bundle="${locale}" key="local.toPrivOffice" var="mToPrivOffice"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
    <fmt:message bundle="${locale}" key="local.addCar" var="mAddCar"/>
    <fmt:message bundle="${locale}" key="local.chooseCarClass" var="mChooseCarClass"/>
    <fmt:message bundle="${locale}" key="local.chooseModel" var="mChooseModel"/>
    <fmt:message bundle="${locale}" key="local.chooseCarYear" var="mChooseCarYear"/>
    <fmt:message bundle="${locale}" key="local.chooseCarPrice" var="mChooseCarPrice"/>
    <fmt:message bundle="${locale}" key="local.chooseCarImage" var="mChooseCarImage"/>
    <fmt:message bundle="${locale}" key="local.invalidYear" var="mInvalidYear"/>
    <fmt:message bundle="${locale}" key="local.model" var="mModel"/>
    <fmt:message bundle="${locale}" key="local.price" var="mPrice"/>
    <fmt:message bundle="${locale}" key="local.year" var="mYear"/>
    <fmt:message bundle="${locale}" key="local.yearIssue" var="mYearIssue"/>
    <fmt:message bundle="${locale}" key="local.carClass" var="mCarClass"/>
    <fmt:message bundle="${locale}" key="local.mImage" var="mImage"/>
    <fmt:message bundle="${locale}" key="local.economyClass" var="econom"/>
    <fmt:message bundle="${locale}" key="local.businessClass" var="business"/>
    <fmt:message bundle="${locale}" key="local.truck" var="truck"/>
    <fmt:message bundle="${locale}" key="local.messageReg" var="messageReq"/>
    <fmt:message bundle="${locale}" key="local.messageCarYear" var="messageCarYear"/>
    <fmt:message bundle="${locale}" key="local.messageCarModel" var="messageCarModel"/>
    <fmt:message bundle="${locale}" key="local.messageCarPrice" var="messageCarPrice"/>
    <fmt:message bundle="${locale}" key="local.mAddCar" var="mAddCar"/>
    <fmt:message bundle="${locale}" key="local.viewOrders" var="viewOrders"/>
    <fmt:message bundle="${locale}" key="local.viewUsers" var="viewUsers"/>
    <fmt:message bundle="${locale}" key="local.addCar" var="addCar"/>
    <fmt:message bundle="${locale}" key="local.mFieldWithoutTags" var="mNoTags"/>
</head>
<body>

<%@include file="../jspf/navigation.jspf" %>

<div class="container">
    <c:if test="${sessionScope.role == 'ADMINISTRATOR'}">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">
                    ${mAddCar}</h1>
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
                <li class="active">
                        ${mAddCar}
                </li>
            </ol>
        </div>

        <div class="well col-lg-12">
            <hr/>
            <p class="text-danger">
                <c:if test="${requestScope.validCarClass == false}">
                    ${mChooseCarClass}
                </c:if>
                <c:if test="${requestScope.validCarModel == false}">
                    ${mChooseModel}
                </c:if>
                <c:if test="${requestScope.validCarYear == false}">
                    ${mChooseCarYear}
                </c:if>
                <c:if test="${requestScope.validCarPrice == false}">
                    ${mChooseCarPrice}
                </c:if>
                <c:if test="${requestScope.validCarImage == false}">
                    ${mChooseCarImage}
                </c:if>
            </p>

            <form action="Controller" method="post" enctype="multipart/form-data">

                <p>${mCarClass}:</p>
                <select name="carClass" class="form-control">
                    <option value="econom">${econom}</option>
                    <option value="business">${business}</option>
                    <option value="truck">${truck}</option>
                </select>

                <p>${mYearIssue}:</p>
                <input type="text" name="carYear" required placeholder="XXXX" maxlength="4"
                       pattern="[1-2][0-9]{3}"
                       title="${messageCarYear}" class="form-control" />

                <p>${mModel}:</p>
                <input type="text" name="carModel" required pattern="[a-z0-9_]+"
                       title="${messageCarModel}" class="form-control" />

                <p>${mPrice}:</p>
                <input type="text" name="carPrice" required pattern="\d+(\.\d{0,2})?"
                       title="${messageCarPrice}" class="form-control" />

                <hr/>

                <label for="photo">${mImage}: </label>
                <input type="file" name="image" accept="image/*" id="photo" required class="btn btn-default"/>
                <hr/>

                <input type="hidden" name="command" value="add-car">
                <input type="hidden" name="processRequest" value="redirect">
                <input type="submit" value="${mAddCar}" class="btn btn-primary"/>
            </form>
        </div>
        </c:if>

    <c:if test="${sessionScope.role != 'ADMINISTRATOR'}">
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

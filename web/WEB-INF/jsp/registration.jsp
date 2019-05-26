<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="/WEB-INF/custom.tld" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Registration</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
<%--    <link href="../../css/bootstrap.min.css" rel="stylesheet">--%>
<%--    <link href="../../css/car-rental-style.css" rel="stylesheet">--%>
<%--    <link href="../../font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">--%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.local" var="locale"/>
    <fmt:message bundle="${locale}" key="local.signIn" var="signIn"/>
    <fmt:message bundle="${locale}" key="local.login" var="login"/>
    <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${locale}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${locale}" key="local.registration" var="registration"/>
    <fmt:message bundle="${locale}" key="local.messageReg" var="messageReg"/>
    <fmt:message bundle="${locale}" key="local.reqLogin" var="reqLogin"/>
    <fmt:message bundle="${locale}" key="local.reqPassword" var="reqPassword"/>
    <fmt:message bundle="${locale}" key="local.reqConfirmPassword" var="reqConfirmPassword"/>
    <fmt:message bundle="${locale}" key="local.reqName" var="reqName"/>
    <fmt:message bundle="${locale}" key="local.reqSurname" var="reqSurname"/>
    <fmt:message bundle="${locale}" key="local.reqEMail" var="reqEMail"/>
    <fmt:message bundle="${locale}" key="local.reqPhone" var="reqPhone"/>
    <fmt:message bundle="${locale}" key="local.reqPassportID" var="reqPassportID"/>
    <fmt:message bundle="${locale}" key="local.register" var="register"/>
    <fmt:message bundle="${locale}" key="local.notUniqueLogin" var="notUniqueLogin"/>
    <fmt:message bundle="${locale}" key="local.notUniqueEmail" var="notUniqueEmail"/>
    <fmt:message bundle="${locale}" key="local.notUniquePassportID" var="notUniquePassportID"/>
    <fmt:message bundle="${locale}" key="local.messageLogin" var="messageLogin"/>
    <fmt:message bundle="${locale}" key="local.messagePassword" var="messagePassword"/>
    <fmt:message bundle="${locale}" key="local.messageConfirmPassword" var="messageConfirmPassword"/>
    <fmt:message bundle="${locale}" key="local.messageEmail" var="messageEmail"/>
    <fmt:message bundle="${locale}" key="local.messageName" var="messageName"/>
    <fmt:message bundle="${locale}" key="local.messagePhone" var="messagePhone"/>
    <fmt:message bundle="${locale}" key="local.messagePassportID" var="messagePassport"/>
    <fmt:message bundle="${locale}" key="local.invalidLogin" var="invalidLogin"/>
    <fmt:message bundle="${locale}" key="local.invalidPassword" var="invalidPassword"/>
    <fmt:message bundle="${locale}" key="local.invalidEmail" var="invalidEmail"/>
    <fmt:message bundle="${locale}" key="local.invalidSurname" var="invalidSurname"/>
    <fmt:message bundle="${locale}" key="local.invalidName" var="invalidName"/>
    <fmt:message bundle="${locale}" key="local.invalidPhone" var="invalidPhone"/>
    <fmt:message bundle="${locale}" key="local.invalidPassportID" var="invalidPassportID"/>
    <fmt:message bundle="${locale}" key="local.messageConfirmationPassword" var="notConfirmedPassword"/>
    <fmt:message bundle="${locale}" key="local.home" var="home"/>
    <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
    <fmt:message bundle="${locale}" key="local.info" var="info"/>
    <fmt:message bundle="${locale}" key="local.mFieldWithoutTags" var="mNoTags"/>
</head>
<body>

<%@include file="../jspf/navigation.jspf" %>

<div class="container">
    <div class="col-lg-12">
        <h1 class="page-header">
            ${registration}
        </h1>
        <ol class="breadcrumb">
            <li>
                <form action="Controller" method="get" class="btn btn-link">
                    <input type="hidden" name="command" value="to-home-page">
                    <input type="submit" value="${home}" class="btn btn-link">
                </form>
            </li>
            <li class="active">
                ${registration}
            </li>
        </ol>
    </div>

    <h4>${messageReg}</h4>

    <c:if test="${requestScope.uniqueLogin == false}">
        <p class="text-danger input-lg">${notUniqueLogin}</p>
    </c:if>
    <c:if test="${requestScope.uniqueEmail == false}">
        <p class="text-danger input-lg">${notUniqueEmail}</p>
    </c:if>
    <c:if test="${requestScope.uniquePassport == false}">
        <p class="text-danger input-lg">${notUniquePassportID}</p>
    </c:if>

    <c:if test="${requestScope.validLogin == false}">
        <p class="text-danger input-lg">${invalidLogin}</p>
    </c:if>
    <c:if test="${requestScope.validPassword == false}">
        <p class="text-danger input-lg">${invalidPassword}</p>
    </c:if>
    <c:if test="${requestScope.validEmail == false}">
        <p class="text-danger input-lg">${invalidEmail}</p>
    </c:if>
    <c:if test="${requestScope.validName == false}">
        <p class="text-danger input-lg">${invalidName}</p>
    </c:if>
    <c:if test="${requestScope.validSurname == false}">
        <p class="text-danger input-lg">${invalidSurname}</p>
    </c:if>
    <c:if test="${requestScope.validPhone == false}">
        <p class="text-danger input-lg"> ${invalidPhone}</p>
    </c:if>
    <c:if test="${requestScope.validPassportID == false}">
        <p class="text-danger input-lg">${invalidPassportID}</p>
    </c:if>

    <c:if test="${requestScope.confirmationPassword == false}">
        <p class="text-danger input-lg">${notConfirmedPassword}</p>
    </c:if>

    <div class="well col-lg-12">
        <div class="col-lg-6">
            <form action="Controller" method="post" class="form-horizontal">
                <div class="form-group">
                    <p>
                        <abbr title="${messageLogin}">${reqLogin}</abbr>
                    </p>
                    <input type="text" name="login" id="login" maxlength="25" required pattern="[A-Za-z0-9_]+"
                           title="${messageLogin}" class="form-control"/>
                </div>
                <div class="form-group">
                    <p>
                        <abbr title="${messagePassword}">${reqPassword}</abbr>
                    </p>
                    <input type="password" name="password" maxlength="35" required pattern="\S+"
                           title="${messagePassword}" class="form-control"/>
                </div>
                <div class="form-group">
                    <p>
                        <abbr title="${messageConfirmPassword}">${reqConfirmPassword}</abbr>
                    </p>
                    <input type="password" name="confirm-password" maxlength="35" required pattern="\S+"
                           title="${messageConfirmPassword}" class="form-control"/>
                </div>
                <div class="form-group">
                    <p>
                        <abbr title="${messageEmail}">${reqEMail}</abbr>
                    </p>
                    <input type="text" name="e-mail" maxlength="60" required
                           pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" title="${messageEmail}"
                           class="form-control"/>
                </div>
                <div class="form-group">
                    <p>
                        <abbr title="${messageName}"> ${reqSurname}</abbr>
                    </p>
                    <input type="text" name="surname" value="" maxlength="25" required pattern="[A-Za-zА-Яа-я-]+"
                           title="${messageName}" class="form-control"/>
                </div>
                <div class="form-group">
                    <p>
                        <abbr title="${messageName}"> ${reqName}</abbr>
                    </p>
                    <input type="text" name="name" value="" maxlength="25" required pattern="[A-Za-zА-Яа-я-]+"
                           title="${messageName}" class="form-control"/>
                </div>

                <div class="form-group">
                    <p>
                        <abbr title="${messagePhone}">${reqPhone}</abbr>
                    </p>
                    <input type="text" name="phone" value="" maxlength="30" required pattern="[0-9-+\s()]+"
                           title="${messagePhone}" class="form-control"/>
                </div>
                <div class="form-group">
                    <p>
                        <abbr title="${messagePassport}">${reqPassportID}</abbr>
                    </p>
                    <input type="text" name="passportID" value="" maxlength="15" required pattern="[A-Z0-9]+"
                           title="${messagePassport}" class="form-control"/>
                </div>

                <hr/>
                <input type="hidden" name="command" value="register">
                <input type="hidden" name="processRequest" value="redirect">
                <input type="submit" value="${register}" class="btn btn-success"/>
            </form>
        </div>
    </div>

    <hr/>
    <%@include file="../jspf/footer.jspf" %>
</div>

<%--<script src="../../js/jquery.js"></script>--%>
<%--<script src="../../js/bootstrap.min.js"></script>--%>

</body>
</html>

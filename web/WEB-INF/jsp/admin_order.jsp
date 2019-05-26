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
    <fmt:message bundle="${locale}" key="local.order" var="mOrder"/>
    <fmt:message bundle="${locale}" key="local.mOrderNumber" var="mOrderNumber"/>
    <fmt:message bundle="${locale}" key="local.mStatus" var="mStatus"/>
    <fmt:message bundle="${locale}" key="local.reason" var="reason"/>
    <fmt:message bundle="${locale}" key="local.invalidReason" var="invalidReason"/>
    <fmt:message bundle="${locale}" key="local.selectRealFrom" var="selectFrom"/>
    <fmt:message bundle="${locale}" key="local.selectRealTo" var="selectTo"/>
    <fmt:message bundle="${locale}" key="local.statusRejected" var="sRejected"/>
    <fmt:message bundle="${locale}" key="local.statusApproved" var="sApproved"/>
    <fmt:message bundle="${locale}" key="local.statusUndefined" var="sUndefined"/>
    <fmt:message bundle="${locale}" key="local.statusClosed" var="sClosed"/>
    <fmt:message bundle="${locale}" key="local.statusPaid" var="sPaid"/>
    <fmt:message bundle="${locale}" key="local.changeStatus" var="changeStatus"/>
    <fmt:message bundle="${locale}" key="local.supposedFromDate" var="mSupposedFromDate"/>
    <fmt:message bundle="${locale}" key="local.supposedToDate" var="mSupposedToDate"/>
    <fmt:message bundle="${locale}" key="local.shippingPlaceMessage" var="mShippingPlace"/>
    <fmt:message bundle="${locale}" key="local.returnPlaceMessage" var="mReturnPlace"/>
    <fmt:message bundle="${locale}" key="local.realDateFrom" var="mRealDateFrom"/>
    <fmt:message bundle="${locale}" key="local.realDateTo" var="mRealDateTo"/>
    <fmt:message bundle="${locale}" key="local.BYN" var="byn"/>
    <fmt:message bundle="${locale}" key="local.orderPrice" var="mOrderPrice"/>
    <fmt:message bundle="${locale}" key="local.orderInfo" var="orderInfo"/>
    <fmt:message bundle="${locale}" key="local.mInvalidDate" var="mInvalidDate"/>
    <fmt:message bundle="${locale}" key="local.setDate" var="mSetDate"/>
    <fmt:message bundle="${locale}" key="local.mDamagePrice" var="mDmgPrice"/>
    <fmt:message bundle="${locale}" key="local.invalidDmgPrice" var="invalidDmgPrice"/>
    <fmt:message bundle="${locale}" key="local.setDmgPrice" var="setDmgPrice"/>
    <fmt:message bundle="${locale}" key="local.mUser" var="mUser"/>
    <fmt:message bundle="${locale}" key="local.mLogin" var="mLogin"/>
    <fmt:message bundle="${locale}" key="local.mLastName" var="mLastName"/>
    <fmt:message bundle="${locale}" key="local.mFirstName" var="mFirstName"/>
    <fmt:message bundle="${locale}" key="local.mPhone" var="mPhone"/>
    <fmt:message bundle="${locale}" key="local.mPassport" var="mPassport"/>
    <fmt:message bundle="${locale}" key="local.carClass" var="mCarClass"/>
    <fmt:message bundle="${locale}" key="local.model" var="mModel"/>
    <fmt:message bundle="${locale}" key="local.year" var="mYear"/>
    <fmt:message bundle="${locale}" key="local.mCar" var="mCar"/>
    <fmt:message bundle="${locale}" key="local.mToAllOrders" var="mToAllOrders"/>
    <fmt:message bundle="${locale}" key="local.mUpdate" var="mUpdate"/>
    <fmt:message bundle="${locale}" key="local.viewOrders" var="viewOrders"/>
    <fmt:message bundle="${locale}" key="local.viewUsers" var="viewUsers"/>
    <fmt:message bundle="${locale}" key="local.addCar" var="addCar"/>
    <fmt:message bundle="${locale}" key="local.mAllOrders" var="mAllOrders"/>
    <fmt:message bundle="${locale}" key="local.mSetDateFrom" var="mSetDateFrom"/>
    <fmt:message bundle="${locale}" key="local.mSetDateTo" var="mSetDateTo"/>
    <fmt:message bundle="${locale}" key="local.mFieldWithoutTags" var="mNoTags"/>
    <fmt:message bundle="${locale}" key="local.dmgPriceField" var="mDmgPriceField"/>
</head>
<body>

<%@include file="../jspf/navigation.jspf" %>

<div class="container">

    <c:if test="${sessionScope.role == 'ADMINISTRATOR'}">
    <div class="col-lg-12">
        <h1 class="page-header">${mOrder}</h1>
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
                <form action="Controller" method="get" class="btn btn-link">
                    <input type="hidden" name="command" value="view-orders-admin">
                    <input type="submit" value="${mAllOrders}" class="btn btn-link">
                </form>
            </li>
            <li class="active">${mOrder}</li>
        </ol>
    </div>


    <h2>${mOrderNumber} <c:out value="${requestScope.selectedOrder.orderID}"/></h2>


    <ul class="nav navbar-nav">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">${mUser}<span
                    class="caret"></span></a>
            <ul class="dropdown-menu order-cr-us-info">
                <li>
                    <div>

                        <p>${mLogin}: <c:out value="${requestScope.selectedOrder.user.login}"/></p>

                        <p>${mLastName}: <c:out value="${requestScope.selectedOrder.user.surName}"/></p>

                        <p>${mFirstName}: <c:out value="${requestScope.selectedOrder.user.name}"/></p>

                        <p>e-mail: <c:out value="${requestScope.selectedOrder.user.email}"/></p>

                        <p>${mPhone}: <c:out value="${requestScope.selectedOrder.user.phone}"/></p>

                        <p>${mPassport}: <c:out value="${requestScope.selectedOrder.user.passportID}"/></p>

                    </div>
                </li>
            </ul>
        </li>

        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">${mCar}<span
                    class="caret"></span></a>
            <ul class="dropdown-menu order-cr-us-info">
                <li>
                    <div>
                        <img class="img-responsive car-small-img"
                             src="data:image/jpg;base64,${requestScope.selectedOrder.car.image}"/>

                        <p>${mCarClass}: <c:out value="${requestScope.selectedOrder.car.carClassType}"/></p>

                        <p>${mModel}: <c:out value="${requestScope.selectedOrder.car.carModel}"/></p>

                        <p>${mYear}: <c:out value="${requestScope.selectedOrder.car.yearIssue}"/></p>


                    </div>
                </li>
            </ul>

        </li>

    </ul>

    <br/>
    <hr/>


    <c:if test="${requestScope.invalidInfo == true}">
        <h4 class="text-danger my-info">${invalidReason}</h4>
    </c:if>

    <p> ${mStatus}:
        <c:if test="${requestScope.selectedOrder.status.equals('approved')}">
            ${sApproved}
        </c:if>

        <c:if test="${requestScope.selectedOrder.status.equals('undefined')}">
            ${sUndefined}
        </c:if>

        <c:if test="${requestScope.selectedOrder.status.equals('rejected')}">
            ${sRejected}
        </c:if>

        <c:if test="${requestScope.selectedOrder.status.equals('closed')}">
            ${sClosed}
        </c:if>

        <c:if test="${requestScope.selectedOrder.status.equals('paid')}">
            ${sPaid}
        </c:if>
    </p>

    <br/>

    <c:if test="${!requestScope.selectedOrder.status.equals('closed')}">

        <form action="Controller" method="post" class="col-lg-4">
            <select name="statusOrder" class="form-control">
                <option value="approved">${sApproved}</option>
                <option value="rejected">${sRejected}</option>
                <option value="closed">${sClosed}</option>
                <option value="undefined">${sUndefined}</option>
            </select>

            <br/>

            <p>${reason}:</p>
            <textarea name="order-info" cols="40" rows="3" required
                      oninvalid="this.setCustomValidity('Fill it')"
                      oninput="setCustomValidity('')" class="form-control" maxlength="140" pattern="[^<>]*"
                      tittle="${mNoTags}"></textarea>
            <br/>
            <input type="hidden" name="orderId" value="${requestScope.selectedOrder.orderID}">
            <input type="hidden" name="command" value="update-status">
            <input type="submit" value="${changeStatus}" class="btn btn-primary"/>


        </form>
    </c:if>

    <div class="col-lg-8">

        <p> ${mOrderPrice} <c:out value="${requestScope.selectedOrder.totalBill}"/> ${byn}</p>

        <p> ${mRealDateFrom} <c:out value="${requestScope.selectedOrder.rentalStartDate}"/></p>

        <p> ${mRealDateTo} <c:out value="${requestScope.selectedOrder.rentalEndDate}"/></p>


        <c:if test="${requestScope.selectedOrder.damagePrice != null}">
            <p> ${mDmgPrice}: <c:out value="${requestScope.selectedOrder.damagePrice}"/> ${byn}</p>
        </c:if>

        <c:if test="${requestScope.selectedOrder.status.equals('paid')}">

            <c:if test="${requestScope.invalidDamagePrice == true}">
                <p class="text-danger">${invalidDmgPrice}</p>
            </c:if>

            <form action="Controller" method="post">
                <input type="text" name="damage-price" min=""
                       pattern="(^[0-9]+\.([0-9][0-9]|[0-9])$)|(^[0-9]+$)" class="form-control"
                       title="${mDmgPriceField}">
                <input type="hidden" name="selectedOrderId" value="${requestScope.selectedOrder.orderID}">
                <input type="hidden" name="statusOrder" value="${requestScope.selectedOrder.status}">
                <input type="hidden" name="command" value="update-damage-price">
                <br/>
                <input type="submit" value="${setDmgPrice}" class="btn btn-primary">
            </form>
        </c:if>
        <br/>

        <p> ${orderInfo}: <c:out value="${requestScope.selectedOrder.info}"/></p>
    </div>
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


<%--<script src="../../js/jquery.js"></script>--%>
<%--<script src="../../js/bootstrap.min.js"></script>--%>

</body>
</html>
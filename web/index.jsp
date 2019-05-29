<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="cr" uri="/WEB-INF/custom.tld" %>

<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Car Rental</title>
  <link href="css/bootstrap.min.css" rel="stylesheet">
  <link href="css/car-rental-style.css" rel="stylesheet">
  <fmt:setLocale value="${sessionScope.locale}"/>
  <fmt:setBundle basename="localization.local" var="locale"/>
  <fmt:message bundle="${locale}" key="local.carRental" var="carRental"/>
  <fmt:message bundle="${locale}" key="local.locbutton.name.en" var="en_button"/>
  <fmt:message bundle="${locale}" key="local.locbutton.name.ru" var="ru_button"/>
  <fmt:message bundle="${locale}" key="local.signIn" var="signIn"/>
  <fmt:message bundle="${locale}" key="local.registration" var="registration"/>
  <fmt:message bundle="${locale}" key="local.login" var="login"/>
  <fmt:message bundle="${locale}" key="local.password" var="password"/>
  <fmt:message bundle="${locale}" key="local.messageWelcome" var="messageWelcome"/>
  <fmt:message bundle="${locale}" key="local.seeAllBtn" var="seeAllBtn"/>
  <fmt:message bundle="${locale}" key="local.messageInvLogin" var="messageInvLogin"/>
  <fmt:message bundle="${locale}" key="local.messageInvLogin" var="invLogin"/>
  <fmt:message bundle="${locale}" key="local.home" var="home"/>
  <fmt:message bundle="${locale}" key="local.cars" var="cars"/>
  <fmt:message bundle="${locale}" key="local.info" var="info"/>
  <fmt:message bundle="${locale}" key="local.logOut" var="logOut"/>
  <fmt:message bundle="${locale}" key="local.privateOffice" var="privateOffice"/>
  <fmt:message bundle="${locale}" key="local.orders" var="orders"/>
  <fmt:message bundle="${locale}" key="local.businessClass" var="business"/>
  <fmt:message bundle="${locale}" key="local.economyClass" var="econom"/>
  <fmt:message bundle="${locale}" key="local.truck" var="truck"/>
  <fmt:message bundle="${locale}" key="local.mSuccessfulRegister" var="mSuccessfulRegister"/>
  <fmt:message bundle="${locale}" key="local.mBestDeals" var="mBestDeals"/>
  <fmt:message bundle="${locale}" key="local.mLowPrices" var="mLowPrices"/>
  <fmt:message bundle="${locale}" key="local.mCustomerSupport" var="mCustomersSupport"/>
  <fmt:message bundle="${locale}" key="local.mTakingCare" var="mTakingCare"/>
</head>

<body>

<%@include file="/WEB-INF/jspf/navigation.jspf" %>

<div class="container">
  <div class="row">
    <div class="col-lg-12">
      <h1 class="page-header">
        ${messageWelcome}
          <h2>${mLowPrices}</h2>
        <br/>
      </h1>
    </div>
  </div>

    <div class="row">
      <div class="col-lg-12">
        <h2 class="page-header">${cars}</h2>
      </div>

      <div class="col-md-4 col-sm-6 img-portfolio">
        <form action="Controller" method="get">
          <button class="btn btn-default">
            <img class="img-responsive img-hover car-small-img" src="img/economCar.jpg" alt="Econom" >
            <p class="my-info">${econom}</p>
          </button>
          <input type="hidden" name="command" value="view-class">
          <input type="hidden" name="carClass" value="ECONOM"/>
        </form>
      </div>

      <div class="col-md-4 col-sm-6 img-portfolio">
        <form action="Controller" method="get">
          <button class="btn btn-default">
            <img class="img-responsive img-hover car-small-img" src="img/businessCar.jpg" alt="Business" >
            <p class="my-info">${business}</p>
          </button>
          <input type="hidden" name="command" value="view-class">
          <input type="hidden" name="carClass" value="BUSINESS"/>
        </form>
      </div>

      <div class="col-md-4 col-sm-6 img-portfolio">
        <form action="Controller" method="get">
          <button class="btn btn-default">
            <img class="img-responsive img-hover car-small-img" src="img/truckCar.jpg" alt="Truck" >
            <p class="my-info">${truck}</p>
          </button>
          <input type="hidden" name="command" value="view-class">
          <input type="hidden" name="carClass" value="TRUCK"/>
        </form>
      </div>

    </div>


  <hr>


  <form action="Controller" method="get" class="text-center">
    <input type="hidden" name="command" value="view-all-cars">
    <input type="submit" value="${seeAllBtn}" class="btn btn-lg btn-info"/>
  </form>


  <hr/>
  <%@include file="/WEB-INF/jspf/footer.jspf" %>
</div>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>
<!-- jQuery -->
<script src="js/jquery.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</body>
</html>
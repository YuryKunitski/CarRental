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
  <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
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
  <fmt:message bundle="${locale}" key="local.cabriolet" var="cabriolet"/>
  <fmt:message bundle="${locale}" key="local.cargo" var="cargo"/>
  <fmt:message bundle="${locale}" key="local.coupe" var="coupe"/>
  <fmt:message bundle="${locale}" key="local.jeep" var="jeep"/>
  <fmt:message bundle="${locale}" key="local.smallClass" var="small"/>
  <fmt:message bundle="${locale}" key="local.middleClass" var="middle"/>
  <fmt:message bundle="${locale}" key="local.minibus" var="minibus"/>
  <fmt:message bundle="${locale}" key="local.premium" var="premium"/>
  <fmt:message bundle="${locale}" key="local.vintage" var="vintage"/>
  <fmt:message bundle="${locale}" key="local.mSuccessfulRegister" var="mSuccessfulRegister"/>
  <fmt:message bundle="${locale}" key="local.mBestDeals" var="mBestDeals"/>
  <fmt:message bundle="${locale}" key="local.mLowPrices" var="mLowPrices"/>
  <fmt:message bundle="${locale}" key="local.mCustomerSupport" var="mCustomersSupport"/>
  <fmt:message bundle="${locale}" key="local.mTakingCare" var="mTakingCare"/>
</head>

<body>

<%@include file="/WEB-INF/jspf/navigation.jspf" %>

<header id="myCarousel" class="carousel slide">
  <ol class="carousel-indicators">
    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
    <li data-target="#myCarousel" data-slide-to="1"></li>
    <li data-target="#myCarousel" data-slide-to="2"></li>
  </ol>

  <div class="carousel-inner">
    <div class="item active">
<%--      <div class="fill" style="background-image:url('img/main1Img.jpg');"></div>--%>
      <div class="carousel-caption">
        <h2>${mBestDeals}</h2>
      </div>
    </div>
    <div class="item">
<%--      <div class="fill" style="background-image:url('img/main2Img.jpg');"></div>--%>
      <div class="carousel-caption">
        <h2>${mLowPrices}</h2>
      </div>
    </div>
    <div class="item">
<%--      <div class="fill" style="background-image:url('img/main3Img.jpg');"></div>--%>
      <div class="carousel-caption">
        <h2>${mCustomersSupport}</h2>
      </div>
    </div>
  </div>

  <a class="left carousel-control" href="#myCarousel" data-slide="prev">
    <span class="icon-prev"></span>
  </a>
  <a class="right carousel-control" href="#myCarousel" data-slide="next">
    <span class="icon-next"></span>
  </a>
</header>

<div class="container">
  <div class="row">
    <div class="col-lg-12">
      <h1 class="page-header">
        ${messageWelcome}
        <br/>
        <small>${mTakingCare}</small>
      </h1>
    </div>
  </div>

  <div class="well">
    <div class="row">
      <div class="col-lg-12">
        <h2 class="page-header">${cars}</h2>
      </div>
      <div class="col-md-4 col-sm-6 img-portfolio">
        <form action="Controller" method="get">
          <button class="btn btn-default">
            <img class="img-responsive img-hover car-small-img" src="img/fFocus.jpg" alt="Small">

            <p class="my-info">${small}</p>
          </button>
          <input type="hidden" name="command" value="view-type">
          <input type="hidden" name="carType" value="Small"/>
        </form>
      </div>
      <div class="col-md-4 col-sm-6 img-portfolio">
        <form action="Controller" method="get">
          <button class="btn btn-default">
            <img class="img-responsive img-hover car-small-img" src="img/n370.jpg" alt="Coupe">

            <p class="my-info">${coupe}</p>
          </button>
          <input type="hidden" name="command" value="view-type">
          <input type="hidden" name="carType" value="Coupe"/>
        </form>
      </div>
      <div class="col-md-4 col-sm-6 img-portfolio">
        <form action="Controller" method="get">
          <button class="btn btn-default">
            <img class="img-responsive img-hover car-small-img" src="img/mbS.jpg" alt="Premium">

            <p class="my-info">${premium}</p>
          </button>
          <input type="hidden" name="command" value="view-type">
          <input type="hidden" name="carType" value="Premium"/>
        </form>
      </div>
      <div class="col-md-4 col-sm-6 img-portfolio">
        <form action="Controller" method="get">
          <button class="btn btn-default">
            <img class="img-responsive img-hover car-small-img" src="img/p308cc.jpg" alt="Cabriolet">

            <p class="my-info"> ${cabriolet}</p>
          </button>
          <input type="hidden" name="command" value="view-type">
          <input type="hidden" name="carType" value="Cabriolet"/>
        </form>
      </div>
      <div class="col-md-4 col-sm-6 img-portfolio">
        <form action="Controller" method="get">
          <button class="btn btn-default">
            <img class="img-responsive img-hover car-small-img" src="img/mbSprinter.jpg" alt="Cargo">

            <p class="my-info">${cargo}</p>
          </button>
          <input type="hidden" name="command" value="view-type">
          <input type="hidden" name="carType" value="Cargo"/>
        </form>
      </div>
      <div class="col-md-4 col-sm-6 img-portfolio">
        <form action="Controller" method="get">
          <button class="btn btn-default">
            <img class="img-responsive img-hover car-small-img" src="img/g21.jpg" alt="Vintage">

            <p class="my-info">${vintage}</p>
          </button>
          <input type="hidden" name="command" value="view-type">
          <input type="hidden" name="carType" value="Vintage"/>
        </form>
      </div>
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


<!-- jQuery -->
<script src="js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>

<!-- Script to Activate the Carousel -->
<script>
  $('.carousel').carousel({
    interval: 5000 //changes the speed
  })
</script>

</body>
</html>
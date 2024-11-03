<%@ page import="com.example.pizzashop.Db_ITR" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.pizzashop.Models.Pizza" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    Db_ITR db = new Db_ITR();
    List<Pizza> pizzaList = db.findAllPizza();
    String isPost = "POST";

    if (isPost.equalsIgnoreCase(request.getMethod())) {
        String name = request.getParameter("namePizza");

        request.getSession().removeAttribute("type");
        request.getSession().setAttribute("type", "origin");

        request.getSession().removeAttribute("name-pizza");
        request.getSession().setAttribute("name-pizza", name);

        response.sendRedirect(request.getContextPath() + "/pizza-order.jsp");
    }

%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Pizza Shop</title>
    <link rel="stylesheet" href="webjars/bootstrap/5.3.3/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="css/p_style.css">
</head>
<body>
<div class="container">
    <jsp:include page="general-pages/menu.jsp"/>
    <div class="d-flex justify-content-between p-3 flex-wrap">
        <%for (Pizza pz : pizzaList) {%>
        <div class="card p-3 m-3 card-w">
            <div class="card-header bg-body text-center fw-semibold fs-5"><%=pz.getName()%></div>
            <div class="d-flex align-items-center justify-content-center">
                <img src="img/m_d_pizza.png" class="card-img-top img-w" alt="Default image">
            </div>
            <div class="card-body">
                <div class="fw-semibold mb-2">Ingredients:</div>
                <div class="d-flex flex-wrap mb-3"><%=pz.getIngredients()%></div>
            </div>
            <div class="card-footer bg-body">
                <form method="post">
                    <input type="hidden" name="namePizza" value="<%=pz.getName()%>">
                    <div class="d-flex align-items-center justify-content-center">
                        <%if (pz.getName() != null && !pz.getName().isBlank()) {%>
                        <button type="submit" class="btn btn-sm btn-outline-secondary px-3">Buy</button>
                        <%} else {%>
                        <button type="submit" class="btn btn-sm btn-outline-secondary px-3" disabled>Buy</button>
                        <%}%>
                    </div>
                </form>
            </div>
        </div>
        <%}%>
    </div>
</div>

<script src="webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>
<%@ page import="java.util.List" %>
<%@ page import="com.example.pizzashop.Db_ITR" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    Db_ITR db = new Db_ITR();

    List<String> ingredients = db.findAllIngredients();

    String error = (String) request.getSession().getAttribute("error");
%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Pizza constructor</title>
    <link rel="stylesheet" href="webjars/bootstrap/5.3.3/css/bootstrap.min.css"/>
</head>
<body>
<div class="container">
    <jsp:include page="general-pages/menu.jsp"/>
    <div class="d-flex align-items-center justify-content-center my-3">
        <div class="card shadow">
            <div class="card-header bg-body">
                <div class="text-center fw-semibold fs-5">Create your own pizza</div>
            </div>
            <form method="post" action="save-pizza">
                <div class="card-body">
                    <%if (error != null && !error.isBlank()) {%>
                    <div class="d-flex align-items-center justify-content-center fw-semibold fs-5 mb-3 text-danger"><%=error%></div>
                    <%}
                        request.getSession().removeAttribute("error");
                    %>

                    <div class="row align-items-center">
                        <div class="col-5">
                            <div class="d-flex align-items-center justify-content-center">
                                <img src="img/pizza_df.png" class="d-block w-75 p-3" alt="Default pizza">
                            </div>
                        </div>
                        <div class="col">
                            <div class="d-flex align-items-center mb-4 border-bottom">
                                <input type="text" name="nameCastomPizza" class="form-control form-control-sm mb-4"
                                       aria-label="Enter name of pizza" placeholder="Enter name of pizza">
                            </div>

                            <div class="d-flex flex-wrap align-items-center">
                                <%if (ingredients.isEmpty()) {%>
                                <div class="text-center fw-semibold fs-5">
                                    There are no ingredients. The constructor is temporarily unavailable.
                                </div>
                                <%} else {%>
                                <%for (String ing : ingredients) {%>
                                <div class="form-check form-check-inline mb-2">
                                    <input type="checkbox" class="form-check-input" name="ingredients" id="ing<%=ing%>" value="<%=ing%>">
                                    <label class="form-check-label" for="ing<%=ing%>"><%=ing%></label>
                                </div>
                                <%}}%>
                            </div>
                        </div>
                    </div>
                    <div class="card-footer bg-body">
                        <div class="d-flex align-items-center justify-content-end">
                            <a href="<%=request.getContextPath()%>" class="btn btn-sm btn-outline-danger me-2 px-3">Back</a>

                            <%if (!ingredients.isEmpty()) {%>
                                <button type="submit" class="btn btn-sm btn-outline-success me-2 px-3">Order</button>
                            <%}%>
                        </div>
                    </div>
                </div>
            </form>

        </div>
    </div>

</div>
<script src="webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>

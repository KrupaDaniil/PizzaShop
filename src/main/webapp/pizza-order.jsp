<%@ page import="com.example.pizzashop.Db_ITR" %>
<%@ page import="com.example.pizzashop.Models.Order" %>
<%@ page import="com.example.pizzashop.Models.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Db_ITR db = new Db_ITR();
    String isPost = "POST";
    int result = 1;

    if (isPost.equalsIgnoreCase(request.getMethod())) {
        String type = (String) request.getSession().getAttribute("type");
        String name = (String) request.getSession().getAttribute("name-pizza");

        if (type != null && !type.isBlank()) {
            if (name != null && !name.isBlank()) {
                Order order = new Order();
                Users user = new Users();

                if (!request.getParameter("userName").isBlank()) {
                    user.setName(request.getParameter("userName"));
                }
                if (!request.getParameter("userSurname").isBlank()) {
                    user.setSurname(request.getParameter("userSurname"));
                }
                if (!request.getParameter("userPhone").isBlank()) {
                    String phone = request.getParameter("userPhone");

                    user.setPhone(phone);
                    order.setPhone(phone);
                }

                if (!request.getParameter("userEmail").isBlank()) {
                    user.setEmail(request.getParameter("userEmail"));
                }

                if (!request.getParameter("userAddress").isBlank()) {
                    order.setAddress(request.getParameter("userAddress"));
                }

                if (user.getPhone() != null) {
                    if (db.checkUser(user.getPhone())) {
                        db.addUser(user);
                    }
                }

                if (type.equalsIgnoreCase("castom")) {
                    order.setCastomPizzaName(name);
                    result = db.addOrder(order);
                }

                if (type.equalsIgnoreCase("origin")) {
                    order.setPizzaName(name);
                    result = db.addOrder(order);
                }
            }
        }

    }
%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Order</title>
    <link rel="stylesheet" href="webjars/bootstrap/5.3.3/css/bootstrap.min.css"/>
    <script src="js/orderPg.js"></script>
</head>
<body>
<div class="container">
    <jsp:include page="general-pages/menu.jsp"/>
    <div class="d-flex align-items-center justify-content-center">
        <div class="card shadow my-3">
            <div class="card-header">
                <div class="text-center fw-semibold fs-5">Placing an order</div>
            </div>
            <form method="post">
                <div class="card-body">
                    <div class="row row-cols-2">
                        <div class="col mb-3">
                            <input type="text" class="form-control form-control-sm" name="userName"
                                   placeholder="Enter Name" aria-label="Enter name" required>
                        </div>
                        <div class="col mb-3">
                            <input type="text" class="form-control form-control-sm" name="userSurname"
                                   placeholder="Enter Surname" aria-label="Enter surname" required>
                        </div>
                        <div class="col mb-3">
                            <input type="text" class="form-control form-control-sm" name="userPhone" id="userPhone"
                                   placeholder="Enter Phone" aria-label="Enter phone" required maxlength="12">
                        </div>
                        <div class="col mb-3">
                            <input type="email" class="form-control form-control-sm" name="userEmail"
                                   placeholder="Enter Email" aria-label="Enter email">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <input type="text" class="form-control form-control-sm" name="userAddress" placeholder="Enter Address"
                                   aria-label="Enter address" required>
                        </div>
                    </div>
                </div>
                <div class="card-footer">
                    <div class="d-flex align-items-center justify-content-end">
                        <a href="<%=request.getContextPath()%>" class="btn btn-sm btn-outline-secondary px-3 me-2">Home</a>
                        <button type="submit" class="btn btn-sm btn-outline-success px-3">Place Order</button>
                    </div>
                </div>
            </form>
            <%if (result == 0) {%>
            <div class="d-flex align-items-center justify-content-center my-3">
                <div class="fw-semibold fs-6 text-success">Order successfully placed</div>
            </div>
            <%}%>
            <%if (result == -1) {%>
            <div class="d-flex align-items-center justify-content-center my-3">
                <div class="fw-semibold fs-6 text-danger">Failed to place order. Try again later</div>
            </div>
            <%}%>
        </div>
    </div>
</div>

<script src="webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>

</body>
</html>

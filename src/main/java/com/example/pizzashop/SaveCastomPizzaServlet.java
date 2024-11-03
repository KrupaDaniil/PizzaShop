package com.example.pizzashop;

import com.example.pizzashop.Models.Pizza;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/save-pizza")
public class SaveCastomPizzaServlet extends HttpServlet {
    private Db_ITR db;

    @Override
    public void init() {
        db = new Db_ITR();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("nameCastomPizza");
        StringBuilder builder;
        HttpSession session = request.getSession();

        if (name == null || name.isBlank()) {
            session.setAttribute("error", "Please enter a pizza name");
            try {
                response.sendRedirect(request.getContextPath() + "/castom-pizza.jsp");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
        else {
            String[] ingredients = request.getParameterValues("ingredients");
            if (ingredients == null || ingredients.length == 0) {
                session.setAttribute("error", "Please enter at least one ingredient");
                try {
                    response.sendRedirect(request.getContextPath() + "/castom-pizza.jsp");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                builder = new StringBuilder();

                int size = ingredients.length;

                for (int i = 0; i < size; i++) {
                    if (i == size - 1) {
                        builder.append(ingredients[i]);
                    }
                    else {
                        builder.append(ingredients[i]).append(", ");
                    }
                }

                Pizza pizza = new Pizza(name, builder.toString());
                if (db.addCastomPizza(pizza) != -1) {

                    request.getSession().removeAttribute("type");
                    request.getSession().setAttribute("type", "castom");

                    request.getSession().removeAttribute("name-pizza");
                    request.getSession().setAttribute("name-pizza", name);


                    try {
                        response.sendRedirect(request.getContextPath() + "/pizza-order.jsp");
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
                else {
                    session.setAttribute("error", "Unable to save order. Change the name of the pizza, or contact the administrator.");
                    try {
                        response.sendRedirect(request.getContextPath() + "/castom-pizza.jsp");
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

        }


    }
}

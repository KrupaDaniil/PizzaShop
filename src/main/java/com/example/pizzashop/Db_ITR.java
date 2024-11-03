package com.example.pizzashop;

import com.example.pizzashop.Models.Order;
import com.example.pizzashop.Models.Pizza;
import com.example.pizzashop.Models.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Db_ITR {
    private final String patch;
    private final String uName;
    private final String uPassword;
    private final String driver;
    private final String driverError;

    public Db_ITR() {
        patch = "jdbc:sqlserver://192.168.0.103:1433;database=PizzaShopDB;encrypt=false";
        uName = "sa";
        uPassword = "123456";
        driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        driverError = "Error with connection driver";
    }

    public List<Pizza> findAllPizza() {
        List<Pizza> pizzaList = new ArrayList<>();

        try {
            Class.forName(driver);

            try(Connection cn = DriverManager.getConnection(patch, uName, uPassword)) {
                String cmd = "select P.id, P.namePizza, STRING_AGG(Ig.nameIngredient, ', ') as ingredients from PizzaIngredients as P_I " +
                        "inner join Pizza as P on P.id = P_I.pizzaId " +
                        "inner join Ingredients as Ig on Ig.id = P_I.ingredientId " +
                        "group by P.Id, P.namePizza;";
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(cmd);
                while (rs.next()) {
                    pizzaList.add(new Pizza(rs.getInt(1), rs.getString(2), rs.getString(3)));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (ClassNotFoundException e) {
            System.out.println(driverError);
        }

        return pizzaList;
    }

    public List<String> findAllIngredients() {
        List<String> ingredientList = new ArrayList<>();

        try {
            Class.forName(driver);
            try(Connection cn = DriverManager.getConnection(patch, uName, uPassword)) {

                String cmd = "select * from Ingredients";
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(cmd);
                while (rs.next()) {
                    ingredientList.add(rs.getString(2));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println(driverError);
        }

        return ingredientList;
    }

    public int addUser(Users user) {
        if (user != null) {
            try {
                Class.forName(driver);

                try(Connection cn = DriverManager.getConnection(patch, uName, uPassword)) {
                    String cmd = "insert into Users values(?,?,?,?)";
                    PreparedStatement ps = cn.prepareStatement(cmd);
                    ps.setString(1, user.getName());
                    ps.setString(2, user.getSurname());
                    ps.setString(3, user.getPhone());
                    ps.setString(4, user.getEmail());
                    ps.executeUpdate();

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    return -1;
                }
            } catch (ClassNotFoundException e) {
                System.out.println(driverError);
                return -1;
            }

        } else {
            throw new NullPointerException("User is null");
        }
        return 0;
    }

    public boolean checkUser(String phone) {
        try {
            Class.forName(driver);

            try(Connection cn = DriverManager.getConnection(patch, uName, uPassword)) {
                String cmd = "select cast(case when exists(select 1 from Users where Phone = ?) then 1 else 0 end as bit) as isExists;";
                PreparedStatement ps = cn.prepareStatement(cmd);
                ps.setString(1, phone);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return true;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println(driverError);
        }
        return false;
    }

    private int getUserId(String userPhone) {
        try {
            Class.forName(driver);

            try(Connection cn = DriverManager.getConnection(patch, uName, uPassword)) {
                String cmd = "select id from Users where Phone = ?";
                PreparedStatement ps = cn.prepareStatement(cmd);
                ps.setString(1, userPhone);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println(driverError);
        }

        return -1;
    }

    private int getPizzaId(String pizzaName) {
        try {
            Class.forName(driver);

            try(Connection cn = DriverManager.getConnection(patch, uName, uPassword)) {
                String cmd = "select id from Pizza where namePizza = ?";
                PreparedStatement ps = cn.prepareStatement(cmd);
                ps.setString(1, pizzaName);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println(driverError);
        }

        return -1;
    }

    private int getCastomPizzaId(String castomPizzaName) {
        try {
            Class.forName(driver);

            try(Connection cn = DriverManager.getConnection(patch, uName, uPassword)) {
                String cmd = "select id from CastomPizza where namePizza = ?";
                PreparedStatement ps = cn.prepareStatement(cmd);
                ps.setString(1, castomPizzaName);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println(driverError);
        }

        return -1;
    }

    public int addCastomPizza(Pizza pizza) {
        if (pizza != null) {
            try {
                Class.forName(driver);

                try(Connection cn = DriverManager.getConnection(patch, uName, uPassword)) {
                    String cmd = "insert into CastomPizza values(?,?)";
                    PreparedStatement ps = cn.prepareStatement(cmd);
                    ps.setString(1, pizza.getName());
                    ps.setString(2, pizza.getIngredients());
                    ps.executeUpdate();

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    return -1;
                }
            } catch (ClassNotFoundException e) {
                System.out.println(driverError);
                return -1;
            }

        } else {
            throw new NullPointerException("Pizza is null");
        }
        return 0;
    }

    public int addOrder(Order order) {
        if (order != null) {
            int userId = 0;
            String address;
            int pizzaId = 0;
            int castomPizzaId = 0;

            if (order.getPhone() != null && !order.getPhone().isBlank()) {
                userId = getUserId(order.getPhone());
                if (userId == -1) {
                    throw new IllegalArgumentException("Phone number is invalid");
                }
            } else {
                throw new NullPointerException("Phone is null");
            }

            if (order.getAddress() != null && !order.getAddress().isBlank()) {
                address = order.getAddress();
            } else {
                throw new IllegalArgumentException("Invalid address");
            }

            if (order.getPizzaName() != null && !order.getPizzaName().isBlank()) {
                pizzaId = getPizzaId(order.getPizzaName());
                if (pizzaId == -1) {
                    throw new IllegalArgumentException("Pizza name is invalid");
                }
            }

            if (order.getCastomPizzaName() != null && !order.getCastomPizzaName().isBlank()) {
                castomPizzaId = getCastomPizzaId(order.getCastomPizzaName());
                if (castomPizzaId == -1) {
                    throw new IllegalArgumentException("Castom pizza name is invalid");
                }
            }

            try {
                Class.forName(driver);

                try(Connection cn = DriverManager.getConnection(patch, uName, uPassword)) {
                    String cmd = "insert into Orders values(?,?,?,?)";
                    PreparedStatement ps = cn.prepareStatement(cmd);
                    ps.setInt(1, userId);
                    ps.setString(2, address);
                    if (pizzaId > 0) {
                        ps.setInt(3, pizzaId);
                    } else {
                        ps.setNull(3, Types.INTEGER);
                    }

                    if (castomPizzaId > 0) {
                        ps.setInt(4, castomPizzaId);
                    } else {
                        ps.setNull(4, Types.INTEGER);
                    }

                    ps.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    return -1;
                }
            } catch (ClassNotFoundException e) {
                System.out.println(driverError);
                return -1;
            }
        }

        return 0;
    }
}

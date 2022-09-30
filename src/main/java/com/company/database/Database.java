package com.company.database;

import com.company.model.CartProduct;
import com.company.model.Category;
import com.company.model.Customer;
import com.company.model.Product;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.company.ShopBot.conf;

public interface Database {
    List<Customer> customerList = new ArrayList<>();
    List<Category> categoryList = new ArrayList<>();
    List<Product> productList = new ArrayList<>();
    List<CartProduct> cartProducts = new ArrayList<>();

    static Connection getConnection() {

        Connection conn;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(conf.getProperty("dbUrl"),
                                               conf.getProperty("dbUsername"),
                                               conf.getProperty("dbPassword"));
            if (conn != null) {
                System.out.println("Connection worked");
            } else {
                System.out.println("Connection failed");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return conn;
    }
}

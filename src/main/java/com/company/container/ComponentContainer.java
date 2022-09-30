package com.company.container;

import com.company.ShopBot;
import com.company.enums.AdminStatus;
import com.company.enums.CustomerStatus;
import com.company.model.Category;
import com.company.model.Product;

import java.util.HashMap;
import java.util.Map;

public abstract class ComponentContainer {
    public static int generalId;

    public static ShopBot my_telegram_bot;

    public static Map<String, Product> productMap = new HashMap<>();

    public static Map<String, Category> categoryMap = new HashMap<>();

    public static Map<String, Integer> categoryIdMap = new HashMap<>();

    public static Map<String, Integer> productIdMap = new HashMap<>();

    public static Map<String, AdminStatus> stepMap = new HashMap<>();

    public static Map<String, Integer> quantityMap = new HashMap<>();

    public static Map<String, Product> crudStepMap = new HashMap<>();

    public static Map<String, CustomerStatus> customerStepMap = new HashMap<>();
}

package id.achfajar.model;

import java.util.*;

public class Data {
    public static List<Users> users = new ArrayList<>();
    static {
        users.add(new Users(UUID.randomUUID(),"Sabrina", "user1@gmail.com", "123"));
    }

    public static Map<Integer, Merchant> merchantMap = new HashMap<>();
    static {
        merchantMap.put(1, new Merchant(UUID.randomUUID(), "Merchant A", "Jaksel", true));
        merchantMap.put(2, new Merchant(UUID.randomUUID(), "Merchant B", "Jaktim", true));
        merchantMap.put(3, new Merchant(UUID.randomUUID(), "Merchant C", "Jakpus", false));
        merchantMap.put(4, new Merchant(UUID.randomUUID(),"Merchant D", "Jakbar", true));
    }

    public static Map<Integer, Product> foodMap = new HashMap<>();
    static {
        foodMap.put(1, new Product(UUID.randomUUID(), "Nasi Goreng Jawa", 15_000, merchantMap.get(1)));
        foodMap.put(2, new Product(UUID.randomUUID(), "Nasi Goreng Gila", 14_500, merchantMap.get(2)));
        foodMap.put(3, new Product(UUID.randomUUID(), "Nasi Goreng", 16_000, merchantMap.get(3)));
        foodMap.put(4, new Product(UUID.randomUUID(), "Mie Goreng", 16_000, merchantMap.get(4)));
    }

    public static List<OrderDetail> orderDetails = new ArrayList<>();
    public static List<Note> notesPerItem = new ArrayList<>();
    public static Map<Integer, Order> orderMap = new HashMap<>();
}

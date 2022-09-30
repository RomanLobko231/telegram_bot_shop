package com.company.service;

import com.company.container.ComponentContainer;
import com.company.database.Database;
import com.company.model.CartProduct;
import com.company.model.Customer;
import com.company.model.Product;
import com.company.util.InlineKeyboardUtil;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Objects;
import java.util.Optional;

import static com.company.container.ComponentContainer.my_telegram_bot;
import static com.company.database.Database.cartProducts;

public class CartProductService {
    public static void addToCart(String userId, Integer productId, Integer quantity){
        Customer customer = CustomerService.getCustomerById(userId);
        Product product = ProductService.getProductById(productId);

        Optional<CartProduct> optional = Database.cartProducts.stream()
                .filter(cartProduct -> {
                    if (!Objects.equals(cartProduct.getCustomer().getId(), customer.getId())) {
                        return false;
                    }
                    assert product != null;
                    return Objects.equals(cartProduct.getProduct().getId(), product.getId()) && Objects.equals(cartProduct.getProduct().getCategoryId(), product.getCategoryId());
                })
                .findFirst();

        if(optional.isPresent()){
            CartProduct cartProduct = optional.get();
            cartProduct.setQuantity(cartProduct.getQuantity() + quantity);
        }else{
            CartProduct cartProduct = new CartProduct(++ComponentContainer.generalId, customer, product, quantity);
            Database.cartProducts.add(cartProduct);
        }
    }

    public static void showCart(String chatId, String customerId){
        ProductService.loadProductList();
        my_telegram_bot.sendMessage(chatId, "Your cart:", (InlineKeyboardMarkup) null);

        for (CartProduct cartProduct : cartProducts){
            if (Objects.equals(cartProduct.getCustomer().getId(), customerId)){
                Product product = cartProduct.getProduct();
                my_telegram_bot.sendPhoto(chatId, String.format("""
                                    💻 Product: %s
                                    💸 Cost: %s
                                    Description: %s
                                    Quantity: %s""",
                                product.getName(),
                                product.getPrice(),
                                product.getDescription(),
                                cartProduct.getQuantity()),
                                product.getImage(),
                                InlineKeyboardUtil.deleteFromCart(cartProduct.getId()));
            }
        }
    }
    public static void deleteFromCart(Integer cartProductId, int messageId, String chatId) {
        Database.cartProducts.removeIf(cartProduct -> cartProduct.getId().equals(cartProductId));
        my_telegram_bot.deleteMessage(chatId, messageId);
    }
}

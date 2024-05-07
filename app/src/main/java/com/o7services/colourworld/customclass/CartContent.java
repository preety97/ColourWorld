package com.o7services.colourworld.customclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CartContent {

    public static ArrayList<ProductCart> CARTLIST = new ArrayList<>();
    public static int TOTALAMOUNT = 0;

    public static class ProductCart {

        public final String productId;
        public final String productType;
        public final String productName;
        public final String productImage;
        public final String productPrice;
        public final int productqnty;
        public final String quantity;
        public final String productDescription;

        public ProductCart(String productId, String productType, String productName,
                           String productImage, String productPrice, String productDescription,int productqnty,String qnty) {
            this.productId = productId;
            this.productType = productType;
            this.productName = productName;
            this.productImage = productImage;
            this.productPrice = productPrice;
            this.productDescription = productDescription;
            this.productqnty = productqnty;
            this.quantity = qnty;
        }
    }
}

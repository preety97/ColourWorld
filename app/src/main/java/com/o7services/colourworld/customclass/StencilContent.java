package com.o7services.colourworld.customclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StencilContent {


    public static final List<StencilItem> ITEMS = new ArrayList<StencilItem>();


    public static class StencilItem {
        public final String id;
        public final String type;
        public final String name;
        public final String image;
        public final String price;
        public final String description;

        public StencilItem(String id, String type, String name,String image,String price,String description) {
            this.id = id;
            this.type = type;
            this.name = name;
            this.image = image;
            this.price = price;
            this.description = description;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}

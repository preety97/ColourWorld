package com.o7services.colourworld.customclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MenuContent {

    public static final ArrayList<MenuItem> ITEMS = new ArrayList<>();


    public static class MenuItem {
        public final String id;
        public final String name;
        public final String image;

        public MenuItem(String id, String name,String image) {
            this.id = id;
            this.name = name;
            this.image = image;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}

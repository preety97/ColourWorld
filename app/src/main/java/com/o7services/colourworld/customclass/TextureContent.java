package com.o7services.colourworld.customclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class TextureContent {

    public static final ArrayList<DummyItem> ITEMS = new ArrayList<DummyItem>();


        public static class DummyItem {
            public final String id;
            public final String type;
            public final String name;
            public final String image;
            public final String price;
            public final String description;

            public DummyItem(String id, String type, String name,String image,String price,String description) {
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


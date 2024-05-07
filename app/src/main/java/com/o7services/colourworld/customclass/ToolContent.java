package com.o7services.colourworld.customclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ToolContent {


    public static final List<ToolContent.ToolItem> ITEMS = new ArrayList<ToolContent.ToolItem>();


    public static class ToolItem {
    public final String id;
    public final String name;
    public final String image;

    public ToolItem(String id, String name,String image) {
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
package com.jude.keychain.domain.value;

/**
 * Created by Mr.Jude on 2015/11/3.
 */
public enum Color {

    Green("#4CAF50",0),
    Purple("#9C27B0",1),
    Blue("#2196F3",2),
    Red("#F44336",3),
    Orange("#FF9800",4),
    Brown("#795548",5),
    Yellow("#FFEB3B",6),
    Grey("#9E9E9E",7);

    private int color;
    private int type;

    public int getColor() {
        return color;
    }

    public int getType() {
        return type;
    }

    Color(String color,int type) {
        this.color = android.graphics.Color.parseColor(color);
        this.type = type;
    }

    public static int getColorByType(int type){
        for (Color color : Color.values()) {
            if (color.type == type)return color.color;
        }
        return android.graphics.Color.BLACK;
    }

    public static int getTypeByColor(int colorInt){
        for (Color color : Color.values()) {
            if (color.color == colorInt)return color.type;
        }
        return -1;
    }

    public static int[] getColors(){
        int[] colors = new int[Color.values().length];
        for (int i = 0; i < Color.values().length; i++) {
            colors[i] = Color.values()[i].color;
        }
        return colors;
    }
}

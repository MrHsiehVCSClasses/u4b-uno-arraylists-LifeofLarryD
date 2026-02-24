package u6pp;

import java.util.Arrays;

public class Card {

    public static String RED = "RED";
    public static String GREEN = "GREEN";
    public static String BLUE = "BLUE";
    public static String YELLOW = "YELLOW";

    public static String ZERO = "0";
    public static String ONE = "1";
    public static String TWO = "2";
    public static String THREE = "3";
    public static String FOUR = "4";
    public static String FIVE = "5";
    public static String SIX = "6";
    public static String SEVEN = "7";
    public static String EIGHT = "8";
    public static String NINE = "9";

    public static String DRAW_2 = "DRAW_2";
    public static String REVERSE = "REVERSE";
    public static String SKIP = "SKIP";
    public static String WILD = "WILD";
    public static String WILD_DRAW_4 = "WILD_DRAW_4";

    // Wild color is the default color for wilds, before they are played. 
    public static String[] COLORS = {RED, GREEN, BLUE, YELLOW, WILD}; 
    public static String[] VALUES = {ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, 
        DRAW_2, REVERSE, SKIP, WILD, WILD_DRAW_4};

    private String color;
    private String value;

    public Card(String color, String value){
        if(!isValid(color, COLORS) || !isValid(value, VALUES)){
            throw new IllegalArgumentException();
        }

        this.value = value;

        if(value.equals(WILD) || (value.equals(WILD_DRAW_4))){
            this.color = WILD;
        }
        else{
            this.color = color;
        }
    }

    public String getColor(){
        return color;
    }

    public String getValue(){
        return value;
    }

    public boolean trySetColor(String newColor){
        if(newColor== null) return false;

        if (!value.equals(WILD) && (!value.equals(WILD_DRAW_4))){
            return false;
        }

        if (!isValid(newColor, COLORS) || (newColor.equals(WILD))){
            return false;
        }

        this.color = newColor;
        return true;
    }

    public boolean canPlayOn(Card other){
        if(other == null) return false;

        if (this.value.equals(WILD) || this.value.equals(WILD_DRAW_4)){
            return true;
        }

        if(other.color.equals(WILD)){
            return false;
        }

        return this.color.equals(other.color) || this.value.equals(other.value);
    }

    private boolean isValid(String input, String[] validArray){
        return input != null && Arrays.asList(validArray).contains(input);
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Card)) return false;
        Card other = (Card) obj;
        return this.color.equals(other.color) && this.value.equals(other.value);
    }





}

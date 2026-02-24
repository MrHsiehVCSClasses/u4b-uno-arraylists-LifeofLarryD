package u6pp;

import java.util.ArrayList;

    public class Player {
        private String name;
        private ArrayList<Card> hand;

        public Player(String name){
            this.name = name;
            this.hand = new ArrayList<>();
        }

        public String getName(){
            return name;
        }

        public ArrayList<Card> getHand(){
            return hand;
        }
    }


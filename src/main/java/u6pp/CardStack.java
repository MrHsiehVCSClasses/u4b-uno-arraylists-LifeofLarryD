package u6pp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CardStack {
    private ArrayList<Card> cards;

    public CardStack(){
        cards = new ArrayList<>();
    }

    public void push(Card card){
        if(card != null){
            cards.add(card);
        }
    }

    public Card pop(){
        if (isEmpty()) return null;
        return cards.remove(cards.size() - 1);
    }

    public Card peek(){
        if (isEmpty()) return null;
        return cards.get(cards.size() - 1);
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }

    public int getSize(){
        return cards.size();
    }

    public void clear(){
        cards.clear();
    }

    public void addAll(CardStack other){
        if(other == null || other == this || other.isEmpty()) return;

        ArrayList<Card> temp = new ArrayList<>();

        while(!other.isEmpty()){
            temp.add(other.pop());
        }

        for(int i = temp.size() - 1; i >= 0; i--){
            this.push(temp.get(i));
        }
    }

    public void shuffle(){
        Random rand = new Random();
        for(int i=cards.size() - 1;i > 0;i--){
            int j = rand.nextInt(i + 1);
            Collections.swap(cards, i, j);
        }
    }



}

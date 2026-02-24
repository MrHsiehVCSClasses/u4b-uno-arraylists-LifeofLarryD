package u6pp;

import java.util.ArrayList;

public class Uno {
    private ArrayList<Player> players;
    private CardStack deck;
    private CardStack discard;
    private int currentIndex;
    private boolean reversed;

    public Uno(ArrayList<Player> players, CardStack deck, CardStack discard, int currentIndex, boolean reversed){
        this.players = players;
        this.deck = deck;
        this.discard = discard;
        this.currentIndex = currentIndex;
        this.reversed = reversed;
    }

    public Uno(int numPlayers){
        players = new ArrayList<>();
        for(int i = 1; i <= numPlayers; i++){
            players.add(new Player("Player " + i));
        }

        deck = new CardStack();
        for(String color : Card.COLORS){
            if(color.equals(Card.WILD)) continue;

            deck.push(new Card(color, Card.ZERO));

            for(int i = 0; i < 2; i++){
                deck.push(new Card(color, Card.ONE));
                deck.push(new Card(color, Card.TWO));
                deck.push(new Card(color, Card.THREE));
                deck.push(new Card(color, Card.FOUR));
                deck.push(new Card(color, Card.FIVE));
                deck.push(new Card(color, Card.SIX));
                deck.push(new Card(color, Card.SEVEN));
                deck.push(new Card(color, Card.EIGHT));
                deck.push(new Card(color, Card.NINE));
                deck.push(new Card(color, Card.DRAW_2));
                deck.push(new Card(color, Card.REVERSE));
                deck.push(new Card(color, Card.SKIP));
            }
        }

        for(int i = 0; i < 4; i++){
            deck.push(new Card(Card.WILD, Card.WILD));
            deck.push(new Card(Card.WILD, Card.WILD_DRAW_4));
        }

        deck.shuffle();

        discard = new CardStack();

        for(Player p : players){
            for(int i = 0; i < 7; i++){
                p.getHand().add(deck.pop());
            }
        }

        Card first = deck.pop();
        while(first.getValue().equals(Card.WILD) || (first.getValue().equals(Card.WILD_DRAW_4))){
            deck.push(first);
            deck.shuffle();
            first = deck.pop();
        }
        discard.push(first);

        currentIndex = 0;
        reversed = false;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public Player getCurrentPlayer(){
        return players.get(currentIndex);
    }

    public Player getNextPlayer(){
        int next = reversed ? currentIndex - 1 : currentIndex + 1;

        if(next < 0) next = players.size() - 1;
        if(next >= players.size()) next = 0;

        return players.get(next);
    }

    public Card getTopDiscard(){
        return discard.peek();
    }

    public Player getWinner(){
        for(Player p : players){
            if(p.getHand().isEmpty()) return p;
        }
        return null;

    }

    private void moveToNextPlayer(){
        if(reversed){
            currentIndex--;
            if(currentIndex < 0){
                currentIndex = players.size() - 1;
            }
        } else {
            currentIndex++;
            if(currentIndex >= players.size()){
                currentIndex = 0;
            }
        }
    }

    private void drawCards(Player p, int num){
        for(int i = 0; i < num; i++){

            if(deck.isEmpty()){
                reshuffleDeck();
            }

            if(deck.isEmpty()){
                return;
            }

            p.getHand().add(deck.pop());
        }
    }

    private void reshuffleDeck(){
        if(discard.getSize() <= 1) return;

        Card top = discard.pop();
        deck.addAll(discard);
        deck.shuffle();
        discard.clear();
        discard.push(top);
    }

    public boolean playCard(Card card, String chosenColor){
    Player current = getCurrentPlayer();

    if(card == null){
        drawCards(current, 1);
        moveToNextPlayer();
        return true;
    }

    // Always call this (Mockito test requirement)
    boolean canPlay = card.canPlayOn(getTopDiscard());

    // FIRST: must be in hand
    if(!current.getHand().contains(card)){
        return false;
    }

    // SECOND: must be playable
    if(!canPlay){
        return false;
    }

    // Handle wild cards
    if(card.getValue().equals(Card.WILD) ||
       card.getValue().equals(Card.WILD_DRAW_4)){

        if(chosenColor == null){
            return false;
        }

        try {
            card.trySetColor(chosenColor);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    current.getHand().remove(card);
    discard.push(card);

    handleSpecial(card);

    return true;
}


    private void handleSpecial(Card card){
        String value = card.getValue();

        if (value.equals(Card.REVERSE)){
            reversed = !reversed;

        if(players.size() == 2){
            moveToNextPlayer();
            moveToNextPlayer();
        } else {
            moveToNextPlayer();
        }
    }

        else if(value.equals(Card.SKIP)){
            moveToNextPlayer();
            moveToNextPlayer();
        }

        else if(value.equals(Card.DRAW_2)){
            Player next = getNextPlayer();
            drawCards(next, 2);
            moveToNextPlayer();
            moveToNextPlayer();
        }

        else if(value.equals(Card.WILD_DRAW_4)){
            Player next = getNextPlayer();
            drawCards(next, 4);
            moveToNextPlayer();
            moveToNextPlayer();
        }
        else{
            moveToNextPlayer();
        }

    }
}

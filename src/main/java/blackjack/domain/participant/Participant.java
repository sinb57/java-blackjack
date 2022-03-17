package blackjack.domain.participant;

import java.util.List;

import blackjack.domain.card.Card;
import blackjack.domain.card.CardHands;
import blackjack.domain.card.Deck;

public abstract class Participant {

    private final String name;
    private final CardHands cards = new CardHands();

    protected Participant(final String name, final Deck deck) {
        validateNameNotBlank(name);
        this.name = name;
        initiallyDrawCards(deck);
    }

    private static void validateNameNotBlank(final String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("참가자 이름은 공백이 될 수 없습니다.");
        }
    }

    private void initiallyDrawCards(final Deck deck) {
        final int initiallyDrawCardCount = 2;
        for (int i = 0; i < initiallyDrawCardCount; i++) {
            drawCard(deck);
        }
    }

    public void drawCard(final Deck deck) {
        cards.addCard(deck.drawCard());
    }

    public abstract boolean isPossibleToDrawCard();

    public boolean isBlackjack() {
        return cards.isBlackjack();
    }

    public boolean isBust() {
        return cards.isBust();
    }

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards.getCards();
    }

    public int getScore() {
        return cards.calculateScore();
    }

    public boolean equalsName(final String name) {
        return name.equals(this.name);
    }

}

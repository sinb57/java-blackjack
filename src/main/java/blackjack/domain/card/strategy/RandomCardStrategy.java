package blackjack.domain.card.strategy;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import blackjack.domain.card.Card;
import blackjack.domain.card.CardNumber;
import blackjack.domain.card.CardPattern;

public class RandomCardStrategy implements CardStrategy {

    private final List<Card> cards;

    public RandomCardStrategy() {
        this.cards = Arrays.stream(CardPattern.values())
                .map(this::createCardsPerPattern)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<Card> createCardsPerPattern(final CardPattern cardPattern) {
        return Arrays.stream(CardNumber.values())
                .map(cardNumber -> new Card(cardNumber, cardPattern))
                .collect(Collectors.toList());
    }

    @Override
    public List<Card> generate() {
        Collections.shuffle(cards);
        return new LinkedList<>(cards);
    }

}

package blackjack.domain.card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import blackjack.domain.card.generator.ManualDeckGenerator;

class DeckTest {

    private final ManualDeckGenerator manualDeckGenerator = new ManualDeckGenerator();

    private Deck generateDeck(final List<Card> initializedCards) {
        manualDeckGenerator.initCards(initializedCards);
        return Deck.generate(manualDeckGenerator);
    }

    @DisplayName("카드 생성 시, 중복된 카드는 존재할 수 없다.")
    @ParameterizedTest(name = "[{index}] 카드 : {0}")
    @MethodSource("blackjack.domain.card.provider.DeckTestProvider#provideForCardDuplicatedExceptionTest")
    void cardDuplicatedExceptionTest(final List<Card> cards) {
        manualDeckGenerator.initCards(cards);
        assertThatThrownBy(() -> Deck.generate(manualDeckGenerator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("중복된 카드는 존재할 수 없습니다.");
    }

    @DisplayName("2장의 카드를 배분할 수 있어야 한다.")
    @ParameterizedTest(name = "[{index}] 카드 : {0}")
    @MethodSource("blackjack.domain.card.provider.DeckTestProvider#provideForDistributeInitialCardsTest")
    void distributeInitialCardsTest(final List<Card> cards) {
        final Deck deck = this.generateDeck(cards);
        final List<Card> actualCards = deck.distributeInitialCards();
        final List<Card> expectedCards = cards.subList(0, 2);
        assertThat(actualCards).isEqualTo(expectedCards);
    }

    @DisplayName("한번에 한장씩 카드를 뽑을 수 있어야 한다.")
    @ParameterizedTest(name = "[{index}] 카드 : {0}")
    @MethodSource("blackjack.domain.card.provider.DeckTestProvider#provideForDrawCardTest")
    void drawCardTest(final List<Card> expectedCards) {
        final Deck deck = this.generateDeck(expectedCards);

        final List<Card> actualCards = new LinkedList<>();
        for (int i = 0; i < expectedCards.size(); i++) {
            actualCards.add(deck.drawCard());
        }

        assertThat(actualCards).isEqualTo(expectedCards);
    }

    @DisplayName("뽑을 수 있는 카드가 없으면 예외가 발생한다.")
    @ParameterizedTest(name = "[{index}] 카드 : {0}")
    @MethodSource("blackjack.domain.card.provider.DeckTestProvider#provideForDrawableCardNotExistExceptionTest")
    void drawableCardNotExistExceptionTest(final List<Card> cards) {
        final Deck deck = this.generateDeck(cards);
        for (int i = 0; i < cards.size(); i++) {
            deck.drawCard();
        }
        assertThatThrownBy(deck::drawCard)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("더 이상 뽑을 수 있는 카드가 없습니다.");
    }

}

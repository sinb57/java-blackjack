package blackjack.domain;

import blackjack.domain.strategy.ManualCardStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeckTest {

    private final ManualCardStrategy manualCardStrategy = new ManualCardStrategy();

    @ParameterizedTest
    @MethodSource("provideForValidateCardDuplicatedTest")
    @DisplayName("중복된 카드가 존재할 시 예외 발생")
    void validateCardDuplicatedTest(final List<Card> cards) {
        manualCardStrategy.initCards(cards);
        assertThatThrownBy(() -> Deck.generate(manualCardStrategy))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("중복된 카드는 존재할 수 없습니다.");
    }

    private static Stream<Arguments> provideForValidateCardDuplicatedTest() {
        final CardPattern pattern = CardPattern.CLOVER;
        final CardNumber number = CardNumber.ACE;
        final CardNumber otherNumber = CardNumber.TWO;

        return Stream.of(
                Arguments.of(
                        List.of(new Card(pattern, number),
                                new Card(pattern, number))
                ),
                Arguments.of(
                        List.of(new Card(pattern, otherNumber),
                                new Card(pattern, otherNumber),
                                new Card(pattern, otherNumber))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideForDrawCardTest")
    @DisplayName("카드 덱에서 한장의 카드를 뽑는다.")
    void drawCardTest(final List<Card> expectedCards) {
        manualCardStrategy.initCards(expectedCards);

        final Deck deck = Deck.generate(manualCardStrategy);
        final List<Card> actualCards = new LinkedList<>();
        for (int i = 0; i < expectedCards.size(); i++) {
            actualCards.add(deck.drawCard());
        }

        assertThat(actualCards).isEqualTo(expectedCards);
    }

    private static Stream<Arguments> provideForDrawCardTest() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new Card(CardPattern.DIAMOND, CardNumber.ACE),
                                new Card(CardPattern.DIAMOND, CardNumber.EIGHT)
                        )
                ),
                Arguments.of(
                        List.of(
                                new Card(CardPattern.SPADE, CardNumber.ACE),
                                new Card(CardPattern.HEART, CardNumber.EIGHT),
                                new Card(CardPattern.DIAMOND, CardNumber.EIGHT)
                        )
                )
        );
    }

}
package blackjack.domain.card.generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import blackjack.domain.card.Card;

public class RandomCardGeneratorTest {

    private static final int TOTAL_CARD_COUNT = 52;

    private final RandomDeckGenerator randomCardStrategy = new RandomDeckGenerator();

    @DisplayName("카드는 52장 생성되어야 한다.")
    @Test
    void randomGeneratedCardsSizeCheckTest() {
        final List<Card> cards = randomCardStrategy.generate();
        assertThat(cards.size()).isEqualTo(TOTAL_CARD_COUNT);
    }

}

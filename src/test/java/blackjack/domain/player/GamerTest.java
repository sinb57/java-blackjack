package blackjack.domain.player;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;
import blackjack.domain.card.Denomination;
import blackjack.domain.card.Shape;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GamerTest {

    @DisplayName("게이머의 상태가 힛이 아닐 때 카드를 드로우할 수 없다")
    @Test
    void 카드_드로우_테스트() {
        // given, when
        Cards cards = Cards.of(
                Card.of(Denomination.KING, Shape.CLUBS),
                Card.of(Denomination.ACE, Shape.CLUBS)
        );
        Gamer gamer = new Gamer(new Name("테스트 게이머"), cards);

        // then
        assertThatThrownBy(() -> {
            gamer.draw(Card.of(Denomination.FIVE, Shape.CLUBS));
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 이미 끝이나서 카드를 뽑을 수 없습니다.");
    }
}
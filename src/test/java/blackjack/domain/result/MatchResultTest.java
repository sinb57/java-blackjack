package blackjack.domain.result;

import static blackjack.Fixture.SPADE_ACE;
import static blackjack.Fixture.SPADE_TEN;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import blackjack.domain.participant.Player;

class MatchResultTest {

    @DisplayName("플레이어는 블랙잭으로 승리한 경우, 베팅 금액의 1.5배의 수익을 받는다.")
    @Test
    void calculatePlayerOutcomeAboutBlackjackTest() {
        final Player player = Player.readyToPlay("name", List.of(SPADE_ACE, SPADE_TEN));
        player.betAmount(1000);

        final MatchResult matchResult = new MatchResult(Map.of(player, MatchStatus.BLACKJACK));
        final int actualOutcome = matchResult.getPlayerOutcomes().get("name");
        final int expectedOutcome = 1500;
        assertThat(actualOutcome).isEqualTo(expectedOutcome);
    }

    @DisplayName("플레이어는 승리한 경우, 베팅 금액의 1배의 수익을 받는다.")
    @Test
    void calculatePlayerOutcomeAboutWinTest() {
        final Player player = Player.readyToPlay("name", List.of(SPADE_ACE, SPADE_TEN));
        player.betAmount(1000);

        final MatchResult matchResult = new MatchResult(Map.of(player, MatchStatus.WIN));
        final int actualOutcome = matchResult.getPlayerOutcomes().get("name");
        final int expectedOutcome = 1000;
        assertThat(actualOutcome).isEqualTo(expectedOutcome);
    }

    @DisplayName("플레이어는 무승부인 경우, 베팅 금액을 그대로 돌려받는다.")
    @Test
    void calculatePlayerOutcomeAboutDrawTest() {
        final Player player = Player.readyToPlay("name", List.of(SPADE_ACE, SPADE_TEN));
        player.betAmount(1000);

        final MatchResult matchResult = new MatchResult(Map.of(player, MatchStatus.DRAW));
        final int actualOutcome = matchResult.getPlayerOutcomes().get("name");
        final int expectedOutcome = 0;
        assertThat(actualOutcome).isEqualTo(expectedOutcome);
    }

    @DisplayName("플레이어는 패배한 경우, 베팅 금액을 잃는다.")
    @Test
    void calculatePlayerOutcomeAboutLossTest() {
        final Player player = Player.readyToPlay("name", List.of(SPADE_ACE, SPADE_TEN));
        player.betAmount(1000);

        final MatchResult matchResult = new MatchResult(Map.of(player, MatchStatus.LOSS));
        final int actualOutcome = matchResult.getPlayerOutcomes().get("name");
        final int expectedOutcome = -1000;
        assertThat(actualOutcome).isEqualTo(expectedOutcome);
    }

    @DisplayName("딜러는 전체 수익금을 계산할 수 있어야 한다.")
    @Test
    void calculateDealerOutcomeTest() {
        final Player player1 = Player.readyToPlay("name1", List.of(SPADE_ACE, SPADE_TEN));
        player1.betAmount(10000);
        final Player player2 = Player.readyToPlay("name2", List.of(SPADE_ACE, SPADE_TEN));
        player2.betAmount(1000);

        final MatchResult matchResult = new MatchResult(Map.of(
                player1, MatchStatus.LOSS,
                player2, MatchStatus.WIN
        ));
        final int actualOutcome = matchResult.getDealerOutcome();
        final int expectedOutcome = 9000;
        assertThat(actualOutcome).isEqualTo(expectedOutcome);
    }

}
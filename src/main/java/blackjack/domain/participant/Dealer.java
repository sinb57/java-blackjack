package blackjack.domain.participant;

import blackjack.domain.card.Deck;
import blackjack.domain.result.MatchStatus;

public class Dealer extends Participant {

    public static final String DEALER_NAME = "딜러";
    public static final int DRAWABLE_SCORE_LIMIT = 16;

    private Dealer(final Deck deck) {
        super(DEALER_NAME, deck);
    }

    public static Dealer readyToPlay(final Deck deck) {
        return new Dealer(deck);
    }

    @Override
    public boolean isPossibleToDrawCard() {
        return cards.calculateScore() <= DRAWABLE_SCORE_LIMIT;
    }

    public MatchStatus judgeWinner(final Player player) {
        if (player.isBust()) {
            return MatchStatus.LOSS;
        }
        if (this.isBust()) {
            return MatchStatus.WIN;
        }
        return MatchStatus.from(this.isLowerThan(player));
    }

    private boolean isLowerThan(final Player player) {
        return this.getScore() < player.getScore();
    }

    public String getFirstCardName() {
        return cards.getFirstCardName();
    }

}

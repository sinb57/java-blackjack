package blackjack.domain.participant;

public class BettingAmount {

    private final int amount;

    public BettingAmount(final int amount) {
        validateAmountPositive(amount);
        this.amount = amount;
    }

    private static void validateAmountPositive(final int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("베팅 금액은 양수여야 합니다.");
        }
    }

    public int getAmount() {
        return amount;
    }

}
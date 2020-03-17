package blackjack.domain.user;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import blackjack.domain.card.Card;
import blackjack.domain.card.Deck;
import blackjack.domain.user.hand.Hand;

public abstract class User {
	private static final int DRAW_LOWER_BOUND = 1;

	protected final String name;
	protected final Hand hand;

	public User(String name) {
		validate(name);
		this.name = name.trim();
		this.hand = new Hand();
	}

	User(String name, List<Card> cards) {
		this(name);
		this.hand.add(cards);
	}

	private void validate(String name) {
		if (Objects.isNull(name) || name.trim().isEmpty()) {
			throw new InvalidUserException(InvalidUserException.NULL_OR_EMPTY);
		}
	}

	public void draw(Deck deck) {
		hand.add(deck.draw());
	}

	public void draw(Deck deck, int drawNumber) {
		validateDrawNumber(drawNumber);
		List<Card> drawCards = IntStream.range(0, drawNumber)
			.mapToObj(e -> deck.draw())
			.collect(toList());
		hand.add(drawCards);
	}

	protected void validateDrawNumber(int drawNumber) {
		if (drawNumber < DRAW_LOWER_BOUND) {
			throw new InvalidUserException(InvalidUserException.INVALID_DRAW_NUMBER);
		}
	}

	public abstract boolean canDraw();

	public abstract List<Card> getInitialHand();

	public String getName() {
		return name;
	}

	public List<Card> getHand() {
		return hand.getCards();
	}

	public int getScore() {
		return hand.calculateScore().getScore();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User that = (User)o;
		return name.equals(that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
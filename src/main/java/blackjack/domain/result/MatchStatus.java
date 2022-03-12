package blackjack.domain.result;

import java.util.Arrays;
import java.util.Map;

public enum MatchStatus {

    WIN("승", true),
    LOSS("패", false);

    private final String name;
    private final boolean result;

    MatchStatus(final String name, final boolean result) {
        this.name = name;
        this.result = result;
    }

    public static MatchStatus from(final boolean result) {
        return Arrays.stream(MatchStatus.values())
                .filter(it -> it.result == result)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("승패를 구분 지을 수 없습니다."));
    }

    public Long countMatchStatus(final Map<String, MatchStatus> matchStatuses) {
        return matchStatuses.values().stream()
                .filter(this::equals)
                .count();
    }

    public String getName() {
        return name;
    }

}

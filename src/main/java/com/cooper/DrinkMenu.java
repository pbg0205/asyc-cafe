package com.cooper;

import java.time.Duration;
import java.util.Arrays;

public enum DrinkMenu {
    AMERICANO(1, "아메리카노", Duration.ofSeconds(3)),
    CAFE_LATTE(2, "카페라떼", Duration.ofSeconds(5)),
    FRAPPUCCINO(3, "프라푸치노", Duration.ofSeconds(10));

    private final int id;
    private final String name;
    private final Duration preparationSeconds;

    DrinkMenu(int id, String name, Duration duration) {
        this.id = id;
        this.name = name;
        this.preparationSeconds = duration;
    }

    public static DrinkMenu valueOf(final int id) {
        return Arrays.stream(values())
                .filter(drinkMenu -> drinkMenu.id == id)
                .findFirst()
                .orElseThrow();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Duration getPreparationSeconds() {
        return preparationSeconds;
    }
}

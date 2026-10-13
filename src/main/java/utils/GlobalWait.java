package utils;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.time.Duration;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public final class GlobalWait {
    public static final Duration TIMEOUT = Duration.ofSeconds(20);

    private GlobalWait() {
    }

    public static Performable untilVisible(Target target) {
        return WaitUntil.the(target, isVisible()).forNoMoreThan(TIMEOUT);
    }
}

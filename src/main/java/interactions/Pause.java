package interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import java.time.Duration;

public class Pause implements Task {
    private final Duration duration;

    private Pause(Duration duration) {
        this.duration = duration;
    }

    public static Pause beforeSubmit() {
        return new Pause(Duration.ofSeconds(3));
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted during interaction pause", exception);
        }
    }
}

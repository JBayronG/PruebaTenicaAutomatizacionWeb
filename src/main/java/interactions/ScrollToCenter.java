package interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.JavascriptExecutor;

public class ScrollToCenter implements Task {
    private final Target target;

    private ScrollToCenter(Target target) {
        this.target = target;
    }

    public static ScrollToCenter of(Target target) {
        return new ScrollToCenter(target);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        JavascriptExecutor javascript = (JavascriptExecutor) BrowseTheWeb.as(actor).getDriver();
        javascript.executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});",
                target.resolveFor(actor)
        );
    }
}

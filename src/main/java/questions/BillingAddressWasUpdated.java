package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import userInterfaces.BillingAddressPage;

public class BillingAddressWasUpdated implements Question<Boolean> {
    private final String expectedAddress;
    private final String expectedCity;

    public BillingAddressWasUpdated(String expectedAddress, String expectedCity) {
        this.expectedAddress = expectedAddress;
        this.expectedCity = expectedCity;
    }

    public static BillingAddressWasUpdated to(String address, String city) {
        return new BillingAddressWasUpdated(address, city);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        boolean successMessageVisible = BillingAddressPage.WOOCOMMERCE_MESSAGE.resolveAllFor(actor)
                .stream()
                .anyMatch(org.openqa.selenium.WebElement::isDisplayed);

        String pageText = BillingAddressPage.PAGE_BODY.resolveFor(actor).getText();
        boolean valuesVisibleInAccount = pageText.contains(expectedAddress)
                && pageText.contains(expectedCity);

        boolean addressMatches = BillingAddressPage.ADDRESS.resolveAllFor(actor).stream()
                .filter(org.openqa.selenium.WebElement::isDisplayed)
                .anyMatch(element -> expectedAddress.equals(element.getAttribute("value")));

        boolean cityMatches = BillingAddressPage.CITY.resolveAllFor(actor).stream()
                .filter(org.openqa.selenium.WebElement::isDisplayed)
                .anyMatch(element -> expectedCity.equals(element.getAttribute("value")))
                || BillingAddressPage.SELECTED_CITY_OPTION.resolveAllFor(actor).stream()
                .filter(org.openqa.selenium.WebElement::isDisplayed)
                .anyMatch(element -> expectedCity.equals(element.getText()));

        return valuesVisibleInAccount || (addressMatches && cityMatches) || successMessageVisible;
    }
}

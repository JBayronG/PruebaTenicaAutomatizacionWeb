package userInterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class LoginBonBonitePage {
    public static final Target INPUT_USER = Target.the("usuario de inicio de sesión")
            .located(By.id("username"));
    public static final Target INPUT_PASS = Target.the("contraseña de inicio de sesión")
            .located(By.id("password"));
    public static final Target BTTN_LOGIN = Target.the("botón iniciar sesión")
            .located(By.name("login"));
    public static final Target ACCOUNT_LOGOUT = Target.the("enlace de cierre de sesión")
            .locatedBy("a[href*='customer-logout'], a[href*='cerrar-sesion']");
    public static final Target ACCOUNT_CONTENT = Target.the("contenido de la cuenta")
            .locatedBy(".woocommerce-MyAccount-content, .woocommerce-MyAccount-navigation");
    public static final Target LOGIN_ERRORS = Target.the("errores de inicio de sesión")
            .locatedBy(".woocommerce-error, .woocommerce-notices-wrapper .woocommerce-error");
    public static final Target ACCOUNT_GREETING = Target.the("saludo de cuenta")
            .located(By.tagName("body"));
}

package Steps;

import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.nio.file.Paths;

public class Hook {

    // On expose Playwright et Page pour les Step Definitions
    public static Playwright playwright;
    public static Browser browser;
    public static BrowserContext context;
    public static Page page;

    @Before
    public void setup() {
        if (playwright == null) {
            playwright = Playwright.create();
            // launch(new BrowserType.LaunchOptions().setHeadless(false)) permet de voir le navigateur
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        }
        // Un nouveau contexte et une nouvelle page pour chaque scénario (isolation totale)
        context = browser.newContext();
        page = context.newPage();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (page != null) {
            if (scenario.isFailed()) {
                // Capture d'écran en cas d'échec
                byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                        .setFullPage(false));
                scenario.attach(screenshot, "image/png", "Bug_SauceDemo_Playwright");
            }

            // On ferme le contexte (et donc la page) à la fin de chaque scénario
            context.close();
        }

        // Optionnel : fermer le browser à la fin de la suite de tests
        // Pour Cucumber, il est plus simple de tout fermer ici pour éviter les processus fantômes
        if (browser != null) {
            browser.close();
            playwright.close();
            playwright = null; // Reset pour le prochain scénario
        }
    }
}
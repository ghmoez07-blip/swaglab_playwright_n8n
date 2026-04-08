package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/Features", // Chemin vers vos fichiers .feature
        glue = "Steps",                      // Nom du package contenant vos Steps et Hooks
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json"
        },
        monochrome = true,
        tags = "@conforme" // Vous pouvez filtrer ici les tests à exécuter
)
public class TestRunner {
}
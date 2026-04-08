package Steps;

import Utils.VisualValidation;
import com.microsoft.playwright.Locator;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import java.nio.file.Paths;
import com.microsoft.playwright.Page;

public class VisualSteps {

    // On utilise la page partagée depuis le Hook
    private Page page = Hook.page;
/*
    @Then("l'icône du panier doit être graphiquement conforme")
    public void verifierIconePanier() {
        String screenshotPath = "target/actual_cart.png";
        String referencePath = "src/test/resources/visual_refs/cart_icon_ref.png";

        // 1. Prendre une capture d'écran de la zone ou de la page
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));

        // 2. Utiliser OpenCV pour chercher l'icône dans la capture
        boolean isFound = VisualValidation.isElementPresent(screenshotPath, referencePath, 0.7);

        // 3. Assertion
        Assert.assertTrue("L'icône du panier est graphiquement différente ou absente !", isFound);
    }*/
    @Then("l'icône du panier doit être graphiquement conforme")
    public void verifierIconePanier() {
        String screenshotPath = "target/actual_cart.png";
        String referencePath = "src/test/resources/visual_refs/cart_icon_ref.png";

        // ✅ CORRECTION : On cible uniquement l'élément du panier
        // Cela va recadrer l'image automatiquement sur l'icône
        page.locator(".shopping_cart_link").screenshot(new Locator.ScreenshotOptions()
                .setPath(Paths.get(screenshotPath)));

        // Comparaison avec OpenCV
        boolean isFound = VisualValidation.isElementPresent(screenshotPath, referencePath, 0.9);

        Assert.assertTrue("L'icône du panier est graphiquement différente !", isFound);
    }

    @Then("la page de confirmation ne doit pas avoir de décalage visuel")
    public void verifierPageComplete() {
        String actualPath = "target/last_checkout_page.png";
        String expectedPath = "src/test/resources/visual_refs/checkout_success_master.png";
        String diffPath = "target/diff_result.png";

        // 1. Capture plein écran
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(actualPath)).setFullPage(true));

        // 2. Comparaison pixel par pixel avec OpenCV
        double diffPercent = VisualValidation.compareImages(expectedPath, actualPath, diffPath);

        // 3. Assertion : on tolère moins de 1% de différence (ex: horloge système ou rendu texte)
        Assert.assertTrue("Décalage visuel détecté : " + diffPercent + "%. Voir : " + diffPath,
                diffPercent < 1.0);
    }


}
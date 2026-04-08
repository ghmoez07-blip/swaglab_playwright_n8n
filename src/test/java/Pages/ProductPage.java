package Pages;

import Steps.Hook;
import Utils.VisualValidation;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.junit.Assert;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductPage {
    private final Page page;

    // Sélecteurs
    private final String addBackpackBtn = "#add-to-cart-sauce-labs-backpack";
    private final String panierBtn = ".shopping_cart_link";
    private final String itemInCart = ".cart_item .inventory_item_name";
    private final String productLink = "#item_5_title_link > div";
    private final String badge = ".shopping_cart_badge";
    private final String addTshirtBtn = "[id='add-to-cart-test.allthethings()-t-shirt-(red)']";
    private final String removeBtn = "#remove-sauce-labs-backpack";
    private final String filterSelect = ".product_sort_container";
    private final String productNames = ".inventory_item_name";
    private final String checkoutBtn = "#checkout";
    private final String firstNameInput = "#first-name";
    private final String lastNameInput = "#last-name";
    private final String postalCodeInput = "#postal-code";
    private final String continueBtn = "#continue";
    private final String finishBtn = "#finish";
    private final String successMsg = ".complete-header";
    private final String errorMsg = ".error-message-container";

    // Sélecteurs pour le prix
    private final String subtotalLabel = ".summary_subtotal_label";
    private final String taxLabel = ".summary_tax_label";
    private final String totalLabel = ".summary_total_label";
    private final String inventoryPrices = ".inventory_item_price";

    public ProductPage(Page page) {
        this.page = page;
    }

    public void AddTocart() { page.click(addBackpackBtn); }
    public void ClickPanier() { page.click(panierBtn); }

    public void verifPanier() {
        Assert.assertTrue(page.url().contains("cart.html"));
    }

    public void VerifAjoutProduitPanier() {
        Assert.assertTrue(page.locator(itemInCart).isVisible());
    }

    public void clickProduit() { page.click(productLink); }

    public int getnumberBadge() {
        if (page.locator(badge).count() == 0) return 0;
        return Integer.parseInt(page.textContent(badge).trim());
    }

    public void VerifBadgeisEmpty() {
        Assert.assertEquals(0, getnumberBadge());
    }

    public void AddtocartDeuxiemeproduit() { page.click(addTshirtBtn); }
    public void ClickRemoveBtn() { page.click(removeBtn); }

    public void VerifProduitSupprime() {
        Assert.assertEquals(0, page.locator(itemInCart).count());
    }

    public void ClickFilter() {
        page.selectOption(filterSelect, "za");
    }

    public void VerifFiltrerFromZToA() {
        List<String> actualNames = page.locator(productNames).allTextContents();
        List<String> expectedNames = new ArrayList<>(actualNames);
        expectedNames.sort(Collections.reverseOrder());
        Assert.assertEquals(expectedNames, actualNames);
    }

    public void ClickCheckout() { page.click(checkoutBtn); }

    public void nameinput(String name) { page.fill(firstNameInput, name); }
    public void lastnameinput(String last) { page.fill(lastNameInput, last); }
    public void codeinput(String code) { page.fill(postalCodeInput, code); }

    public void ClickContinue() { page.click(continueBtn); }
    public void ClickFinish() { page.click(finishBtn); }

    public void SuccessfulCheckout() {
        Assert.assertTrue(page.url().contains("checkout-complete.html"));
        Assert.assertEquals("Thank you for your order!", page.textContent(successMsg));
    }

    public String getmsgfailedcheckout() {
        return page.textContent(errorMsg);
    }

    public double verifierCalculTotalDeuxProduits2() {
        List<String> prices = page.locator(inventoryPrices).allTextContents();
        double sum = prices.stream().mapToDouble(p -> Double.parseDouble(p.replace("$", ""))).sum();
    return sum;
    }

    public void verifierCalculTotalDeuxProduits() {
        List<String> prices = page.locator(inventoryPrices).allTextContents();
        double sum = prices.stream().mapToDouble(p -> Double.parseDouble(p.replace("$", ""))).sum();

        double subtotal = Double.parseDouble(page.textContent(subtotalLabel).replace("Item total: $", ""));
        double tax = Double.parseDouble(page.textContent(taxLabel).replace("Tax: $", ""));
        double total = Double.parseDouble(page.textContent(totalLabel).replace("Total: $", ""));

        Assert.assertEquals(sum, subtotal, 0.01);
        Assert.assertEquals((subtotal + tax), total, 0.01);
    }

    public void verifierIconeParImage() {
        // 1. Capture d'écran native Playwright
        String path = "target/screenshot_playwright.png";
        Hook.page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)));

        // 2. Comparer
        boolean isVisible = VisualValidation.isElementPresent(path, "src/test/resources/cart_icon.png", 0.9);
        Assert.assertTrue("L'icône graphique est absente ou modifiée", isVisible);
    }
}
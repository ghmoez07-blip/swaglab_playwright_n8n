package Pages;

import com.microsoft.playwright.Page;
import org.junit.Assert;

public class ProductPage2 {
    private final Page page;

    private final String addBtn = "#add-to-cart";
    private final String cartBtn = ".shopping_cart_link";

    public ProductPage2(Page page) {
        this.page = page;
    }

    public void ClickAddtocart() { page.click(addBtn); }
    public void ClickPanier() { page.click(cartBtn); }

    public void VerifAjoutProduitPanier() {
        Assert.assertTrue(page.url().contains("cart.html"));
    }
}
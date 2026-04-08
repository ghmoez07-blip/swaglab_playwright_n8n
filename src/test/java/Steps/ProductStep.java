package Steps;

import Pages.LoginPage;
import Pages.ProductPage;
import Pages.ProductPage2;
import io.cucumber.java.en.*;
import org.junit.Assert;

public class ProductStep {

    LoginPage loginpage = new LoginPage(Hook.page);
    ProductPage productPage = new ProductPage(Hook.page);
    ProductPage2 productPage2 = new ProductPage2(Hook.page);
    int CompteurAvant;

    @Given("je suis sur la page dacceuil")
    public void je_suis_sur_la_page_dacceuil() {
        Hook.page.navigate("https://www.saucedemo.com/");
        loginpage.enterUsername("standard_user");
        loginpage.enterPassword("secret_sauce");
        loginpage.clickLogin();

        productPage.VerifBadgeisEmpty();
        CompteurAvant = productPage.getnumberBadge();
    }

    @When("je click sur le bouton AddToCart")
    public void jeClickSurLeBoutonAddToCart() { productPage.AddTocart(); }

    @And("je click sur le panier")
    public void jeClickSurLePanier() {
        productPage.ClickPanier();
        productPage.verifPanier();
    }

    @Then("produit ajouté au panier avec succés")
    public void produitAjoutéAuPanierAvecSuccés() { productPage.VerifAjoutProduitPanier(); }

    @When("je click sur le produit")
    public void je_click_sur_le_produit() { productPage.clickProduit(); }

    @When("je click sur le bouton AddToCart du produit")
    public void je_click_sur_le_bouton_add_to_cart_du_produit() { productPage2.ClickAddtocart(); }

    @And("je click sur le panier du produit")
    public void je_click_sur_le_panier_du_produit() { productPage2.ClickPanier(); }

    @Then("produit ajouté au panier du produit avec succés")
    public void produit_ajouté_au_panier_du_produit_avec_succés() {
        productPage2.VerifAjoutProduitPanier();
    }
    @Then("notification dajout saffiche sur le panier")
    public void notificationDajoutSafficheSurLePanier() {
        // On vérifie que le badge contient au moins "1"
        int nombreArticles = productPage.getnumberBadge();
        Assert.assertTrue("La notification (badge) ne s'affiche pas", nombreArticles > 0);
    }

    @Then("incremetation panier")
    public void incremetationPanier() {
        int CompteurApres = productPage.getnumberBadge();
        Assert.assertEquals(CompteurAvant + 2, CompteurApres);
    }

    @And("je click sur le bouton AddToCart DeuxiemeProduit")
    public void jeClickSurLeBoutonAddToCartDeuxiemeProduit() {
        productPage.AddtocartDeuxiemeproduit();
    }

    @And("je click sur Remove")
    public void jeClickSurRemove() { productPage.ClickRemoveBtn(); }

    @Then("produit supprimé")
    public void produitSupprimé() { productPage.VerifProduitSupprime(); }

    @When("je click sur filtreicon je choisi ZTOA")
    public void jeClickSurFiltreiconJeChoisiZTOA() { productPage.ClickFilter(); }

    @Then("produits filtrés selon Name Z to A")
    public void produitsFiltrésSelonNameZToA() { productPage.VerifFiltrerFromZToA(); }

    @When("je click checkout")
    public void jeClickCheckout() { productPage.ClickCheckout(); }

    @And("je saisi Firstname {string}")
    public void jeSaisiFirstname(String name) { productPage.nameinput(name); }

    @And("je saisi Lastname {string}")
    public void jeSaisiLastname(String last) { productPage.lastnameinput(last); }

    @And("je saisi Codepostal {string}")
    public void jeSaisiCodepostal(String code) { productPage.codeinput(code); }

    @And("je click Continue")
    public void jeClickContinue() { productPage.ClickContinue(); }

    @And("je click Finish")
    public void jeClickFinish() { productPage.ClickFinish(); }

    @Then("successful checkout")
    public void successfulCheckout() { productPage.SuccessfulCheckout(); }

    @Then("cehckout failed affichage msg {string}")
    public void cehckoutFailedAffichageMsg(String Expectedmsg) {
        Assert.assertEquals(Expectedmsg, productPage.getmsgfailedcheckout());
    }

    @Then("le prix total des deux produits doit être correct")
    public void lePrixTotalDesDeuxProduitsDoitEtreCorrect() {
        System.out.println(+productPage.verifierCalculTotalDeuxProduits2());
        productPage.verifierCalculTotalDeuxProduits();
    }

    @And("je termine un achat complet")
    public void jeTermineUnAchatComplet() {
        // 1. Aller au panier
        productPage.ClickPanier();

        // 2. Cliquer sur Checkout
        productPage.ClickCheckout();

        // 3. Remplir les informations (on utilise des données par défaut pour le test visuel)
        productPage.nameinput("Test");
        productPage.lastnameinput("Visual");
        productPage.codeinput("75000");

        // 4. Continuer vers la page de résumé
        productPage.ClickContinue();

        // Note : On ne clique PAS sur Finish ici, car le scénario Gherkin
        // veut vérifier la page de confirmation/résumé visuellement.
    }
}
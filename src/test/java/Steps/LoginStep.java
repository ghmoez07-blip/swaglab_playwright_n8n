package Steps;

import Pages.LoginPage;
import Pages.LogoutPage;
import io.cucumber.java.en.*;
import org.junit.Assert;

public class LoginStep {
    // On récupère la page depuis le Hook
    LoginPage loginPage = new LoginPage(Hook.page);
    LogoutPage logoutPage = new LogoutPage(Hook.page);

    @Given("je suis sur la page Login")
    public void je_suis_sur_la_page_login() {
        Hook.page.navigate("https://www.saucedemo.com/");
    }

    @When("je saisi l'username {string}")
    public void je_saisi_l_username(String user) {
        loginPage.enterUsername(user);
    }

    @And("je saisi le mot de passe {string}")
    public void je_saisi_le_mot_de_passe(String pass) {
        loginPage.enterPassword(pass);
    }

    @And("je click sur le bouton Login")
    public void je_click_sur_le_bouton_login() {
        loginPage.clickLogin();
    }

    @Then("redirection vers la page Home")
    public void redirection_vers_la_page_home() {
        // Assertion sur l'URL
        Assert.assertTrue("Redirection échouée", Hook.page.url().contains("inventory.html"));
    }

    @Then("un msg derreur doit safficher {string}")
    public void unMsgDerreurDoitSafficher(String expected) {
        //Assert.assertEquals(expected, loginPage.getErrorMessage());
        Assert.assertTrue(loginPage.getErrorMessage().contains(expected));
    }

    @When("je click sur menuBtn")
    public void clickMenu() {
        logoutPage.clickMenuBtn();
    }

    @And("je click sur logoutSidebarBtn")
    public void clickLogout() {
        logoutPage.clickLogoutBtn();
    }

    @Then("redirection vers la page login")
    public void verifRedirectionLogin() {
        Assert.assertEquals("https://www.saucedemo.com/", Hook.page.url());
    }
}
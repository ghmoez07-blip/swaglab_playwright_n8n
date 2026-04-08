package Pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public class LoginPage {
    private final Page page;

    // Sélecteurs
    private final String usernameInput = "#user-name";
    private final String passwordInput = "#password";
    private final String loginBtn = "#login-button";
    private final String errorMessage = ".error-message-container";

    public LoginPage(Page page) {
        this.page = page;
    }

    public void enterUsername(String user) {
        page.fill(usernameInput, user);
    }

    public void enterPassword(String pass) {
        page.fill(passwordInput, pass);
    }

    public void clickLogin() {
        page.click(loginBtn);
    }

    public String getErrorMessage() {
        return page.textContent(errorMessage);
    }
}
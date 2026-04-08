package Pages;

import com.microsoft.playwright.Page;

public class LogoutPage {
    private final Page page;

    private final String menuBtn = "#react-burger-menu-btn";
    private final String logoutBtn = "#logout_sidebar_link";

    public LogoutPage(Page page) {
        this.page = page;
    }

    public void clickMenuBtn() {
        page.click(menuBtn);
    }

    public void clickLogoutBtn() {
        // Playwright attend que l'élément soit visible et stable automatiquement
        page.click(logoutBtn);
    }
}
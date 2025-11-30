package KFly_Project_AdminPage.pages;

public class BasePage {

    private LoginPage_Admin loginPage_admin;


    // methods

    public LoginPage_Admin getLoginPage() {
        if (loginPage_admin == null) {
            loginPage_admin = new LoginPage_Admin();
        }
        return loginPage_admin;
    }
}


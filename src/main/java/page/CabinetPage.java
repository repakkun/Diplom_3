package page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CabinetPage {
    private final WebDriver driver;

    public CabinetPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By constructorButton = By.xpath(".//p[text()='Конструктор']");
    private final By logonButton = new By.ByClassName("AppHeader_header__logo__2D0X2");
    private final By logOutButton = new By.ByXPath(".//button[@class='Account_button__14Yp3 text text_type_main-medium text_color_inactive']");

    @Step("Click constructor button")
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    @Step("Click logon button")
    public void clickLogonButton() {
        driver.findElement(logonButton).click();
    }

    @Step("Click logout button")
    public void clickLogOutButton() {
        driver.findElement(logOutButton).click();
    }
}

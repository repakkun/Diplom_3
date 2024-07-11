import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.LoginPage;
import page.MainPage;
import page.RegistrationPage;
import com.github.javafaker.Faker;

import java.time.LocalDateTime;

public class RegistrationTest extends BaseTest {

    private RegistrationPage registrationPage;
    Faker faker = new Faker();

    @Before
    @Override
    public void setUp() {
        super.setUp();
        registrationPage = new RegistrationPage(driver);
        driver.get(Constants.REGISTRATION_URL);
    }

    @Test
    @DisplayName("Регистрация пользователя")
    public void registrationUserTest() {
        LoginPage loginPage = new LoginPage(driver);
        String email = "Pakkun" + faker.name().firstName() + "@gmail.com";
        String password = String.valueOf(LocalDateTime.now());

        registrationPage.inputName("Ilya" + faker.name().lastName());
        registrationPage.inputEmail(email);
        registrationPage.inputPassword(password);
        registrationPage.clickRegistrationButton();

        new WebDriverWait(driver, Constants.TIMER).until(ExpectedConditions.urlToBe(Constants.LOGIN_URL));
        loginPage.inputEmail(email);
        loginPage.inputPassword(password);
        loginPage.clickLoginButton();

        MainPage.waitOrderButtonClickable(driver);
        token = storageTokenClient.downloadStorageAccessToken(driver);
        Assert.assertNotNull(token);
    }

    @Test
    @DisplayName("Ошибка для некорректного пароля")
    public void errorIncorrectPasswordTest() {
        registrationPage.inputName("Ilya" + faker.name().lastName());
        registrationPage.inputEmail("Pakkun" + faker.name().firstName() + "@mail.ru");
        registrationPage.inputPassword("null");
        registrationPage.clickRegistrationButton();

        new WebDriverWait(driver, Constants.TIMER).until(ExpectedConditions.visibilityOfElementLocated(registrationPage.incorrectPassword));
        Assert.assertEquals(registrationPage.getIncorrectPasswordMessage(), "Некорректный пароль");
    }
}

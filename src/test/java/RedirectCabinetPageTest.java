import api.helpers.DeleteUserClient;
import api.helpers.StorageTokenClient;
import api.helpers.UserChecks;
import api.helpers.UserCreateClient;
import api.pojo.UserCreate;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.LoginPage;
import page.MainPage;

public class RedirectCabinetPageTest {

    @Rule
    public DriverFactory driverFactory = new DriverFactory();
    private final StorageTokenClient storageTokenClient = new StorageTokenClient();
    private WebDriver driver;
    private final UserCreateClient client = new UserCreateClient();
    private final UserChecks check = new UserChecks();
    private String storageToken;
    private String token;

    @Before
    public void before() {
        driver = driverFactory.getDriver();
        LoginPage loginPage = new LoginPage(driver);

        UserCreate user = UserCreate.random();
        String email = user.getEmail();
        String password = user.getPassword();
        ValidatableResponse createUserResponse = client.userCreate(user);
        token = check.createdUserSuccessfully(createUserResponse);

        driver.get(Constants.LOGIN_URL);
        loginPage.inputEmail(email);
        loginPage.inputPassword(password);
        loginPage.clickLoginButton();

        MainPage.waitOrderButtonClickable(driver);
        storageToken = storageTokenClient.downloadStorageAccessToken(driver);
    }

    @Test
    @DisplayName("Переход в личный кабинет: главная страница")
    public void redirectMainPageTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickCabinetButton();
        new WebDriverWait(driver, Constants.TIMER).until(ExpectedConditions.urlToBe(Constants.CABINET_URL));
        Assert.assertEquals(driver.getCurrentUrl(), Constants.CABINET_URL);
    }

    @After
    public void after() {
        DeleteUserClient deleteClient = new DeleteUserClient();
        if (storageToken != null) {
            deleteClient.userDelete(storageToken);
        } else {
            deleteClient.userDelete(token);
        }
    }
}

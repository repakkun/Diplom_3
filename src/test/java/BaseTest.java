import api.helpers.DeleteUserClient;
import api.helpers.StorageTokenClient;
import api.helpers.UserChecks;
import api.helpers.UserCreateClient;
import api.pojo.UserCreate;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.LoginPage;
import page.MainPage;

public abstract class BaseTest {

    @Rule
    public DriverFactory driverFactory = new DriverFactory();
    protected WebDriver driver;
    protected StorageTokenClient storageTokenClient = new StorageTokenClient();
    protected UserCreateClient client = new UserCreateClient();
    protected UserChecks check = new UserChecks();
    protected String token;
    protected String storageToken;

    @Before
    public void setUp() {
        driver = driverFactory.getDriver();
    }

    @After
    public void tearDown() {
        DeleteUserClient deleteClient = new DeleteUserClient();
        if (storageToken != null) {
            deleteClient.userDelete(storageToken);
        } else if (token != null) {
            deleteClient.userDelete(token);
        }
    }

    protected void createUserAndLogin() {
        UserCreate user = UserCreate.random();
        String email = user.getEmail();
        String password = user.getPassword();
        ValidatableResponse createUserResponse = client.userCreate(user);
        token = check.createdUserSuccessfully(createUserResponse);

        driver.get(Constants.LOGIN_URL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.inputEmail(email);
        loginPage.inputPassword(password);
        loginPage.clickLoginButton();

        MainPage.waitOrderButtonClickable(driver);
        storageToken = storageTokenClient.downloadStorageAccessToken(driver);
    }
}

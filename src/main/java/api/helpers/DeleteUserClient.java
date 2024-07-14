package api.helpers;

import io.qameta.allure.Step;

public class DeleteUserClient extends Client {
    public static final String USER_DELETE_PATH = "auth/user";

    @Step("Delete user request")
    public void userDelete(String token) {
        spec().auth().oauth2(token.replace("Bearer ", ""))
                .delete(USER_DELETE_PATH)
                .then().log().all();
    }
}

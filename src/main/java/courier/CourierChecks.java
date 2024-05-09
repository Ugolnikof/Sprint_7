package courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierChecks {
    @Step("check courier removed successfully")
    public void checkRemovalSuccessfully(ValidatableResponse removalResponse) {
        removalResponse
                .statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("ok", Matchers.equalTo(true));
    }

    @Step("check login unsuccessfully")
    public void checkLoginUnSuccessfully(ValidatableResponse loginResponse) {
        loginResponse
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .assertThat().body("message", Matchers.equalTo("Недостаточно данных для входа"));
    }

    @Step("check login not found")
    public void checkLoginNotFound(ValidatableResponse loginResponse) {
        loginResponse
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .assertThat().body("message", Matchers.equalTo("Учетная запись не найдена"));
    }

    @Step("check courier login successfully")
    public Integer checkLoginSuccessfully(ValidatableResponse loginResponse) {
        return loginResponse
                .statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("id", notNullValue())
                .extract()
                .path("id");
    }

    @Step("check courier created successfully")
    public void checkCreateSuccessfully(ValidatableResponse createResponse) {
        createResponse
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .assertThat().body("ok", Matchers.equalTo(true));
    }

    @Step("check courier created unsuccessfully")
    public void checkCreateUnSuccessfully(ValidatableResponse createResponse) {
        createResponse
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .assertThat().body("message", Matchers.equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("check courier twin created unsuccessfully")
    public void checkCreateTwin(ValidatableResponse createResponse) {
        createResponse
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .assertThat().body("message", Matchers.equalTo("Этот логин уже используется"));
    }
}

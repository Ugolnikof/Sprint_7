package courier;

import configure.EnvConfig;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class Courier {
    private String login;
    private String password;
    private String firstName;


    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public Courier() {
    }

    public Courier(String password, String firstname) {
        this.password = password;
        this.firstName = firstname;
    }

    public Courier(String password) {
        this.password = password;
    }

    public static void checkLoginUnSuccessfully(ValidatableResponse loginResponse) {
        loginResponse
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .assertThat().body("message", Matchers.equalTo("Недостаточно данных для входа"));
    }

    public static void checkLoginNotFound(ValidatableResponse loginResponse) {
        loginResponse
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .assertThat().body("message", Matchers.equalTo("Учетная запись не найдена"));
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public static Courier random() {
        return new Courier(RandomStringUtils.randomAlphabetic(8, 16), "12345", "Vasily");
    }

    public static Courier from(Courier courier) {
        return new Courier(courier.getLogin(), courier.getPassword(), courier.getFirstName());
    }

    @Step("check courier login successfully")
    public static Integer checkLoginSuccessfully(ValidatableResponse loginResponse) {
        return loginResponse
                .statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("id", notNullValue())
                .extract()
                .path("id");
    }

    @Step("login courier")
    public static ValidatableResponse loginCourier(CourierCredentials creds) {
        return given().log().all()
                .contentType(io.restassured.http.ContentType.JSON)
                .baseUri(EnvConfig.BASE_URL)
                .body(creds)
                .when()
                .post(EnvConfig.COURIER_PATH + "/login")
                .then().log().all();
    }

    @Step("check courier created successfully")
    public static void checkCreateSuccessfully(ValidatableResponse createResponse) {
        createResponse
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .assertThat().body("ok", Matchers.equalTo(true));
    }

    @Step("create courier")
    public static ValidatableResponse createCourier(Courier courier) {
        return given().log().all()
                .contentType(io.restassured.http.ContentType.JSON)
                .baseUri(EnvConfig.BASE_URL)
                .body(courier)
                .when()
                .post(EnvConfig.COURIER_PATH)
                .then().log().all();
    }

    @Step("check courier created unsuccessfully")
    public static void checkCreateUnSuccessfully(ValidatableResponse createResponse) {
        createResponse
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .assertThat().body("message", Matchers.equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("check courier twin created unsuccessfully")
    public static void checkCreateTwin(ValidatableResponse createResponse) {
        createResponse
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .assertThat().body("message", Matchers.equalTo("Этот логин уже используется"));
    }

}

package courier;

import configure.EnvConfig;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierProperties {
    @Step("delete courier")
    public ValidatableResponse deleteCourier(CourierRemoval courierRemoval) {
        return given().log().all()
                .contentType(io.restassured.http.ContentType.JSON)
                .baseUri(EnvConfig.BASE_URL)
                .body(courierRemoval)
                .when()
                .delete(EnvConfig.COURIER_PATH + "/" + courierRemoval.getCourierId())
                .then().log().all();
    }

    @Step("login courier")
    public ValidatableResponse loginCourier(CourierCredentials creds) {
        return given().log().all()
                .contentType(io.restassured.http.ContentType.JSON)
                .baseUri(EnvConfig.BASE_URL)
                .body(creds)
                .when()
                .post(EnvConfig.COURIER_PATH + "/login")
                .then().log().all();
    }

    @Step("create courier")
    public ValidatableResponse createCourier(Courier courier) {
        return given().log().all()
                .contentType(io.restassured.http.ContentType.JSON)
                .baseUri(EnvConfig.BASE_URL)
                .body(courier)
                .when()
                .post(EnvConfig.COURIER_PATH)
                .then().log().all();
    }

}

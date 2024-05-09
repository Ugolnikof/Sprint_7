package order;

import configure.EnvConfig;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderProperties {
    @Step("get orders list")
    public ValidatableResponse getOrdersList() {
        return given().log().all()
                .contentType(io.restassured.http.ContentType.JSON)
                .baseUri(EnvConfig.BASE_URL)
                .when()
                .get(EnvConfig.ORDER_PATH)
                .then().log().all();
    }

    @Step("create order")
    public static ValidatableResponse createOrder(Order order) {
        return given().log().all()
                .contentType(io.restassured.http.ContentType.JSON)
                .baseUri(EnvConfig.BASE_URL)
                .body(order)
                .when()
                .post(EnvConfig.ORDER_PATH)
                .then().log().all();
    }

    @Step("cancel order")
    public ValidatableResponse orderCancel(OrderCancel cancel) {
        return given().log().all()
                .contentType(io.restassured.http.ContentType.JSON)
                .baseUri(EnvConfig.BASE_URL)
                .queryParam("track", cancel.getTrack())
                .when()
                .put(EnvConfig.ORDER_PATH + "/cancel")
                .then().log().all();
    }
}

package order;

import configure.EnvConfig;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;

public class OrderCancel {
    private int track;

    public OrderCancel(int track) {
        this.track = track;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    @Step("cancel order")
    public static ValidatableResponse orderCancel(OrderCancel cancel) {
        return given().log().all()
                .contentType(io.restassured.http.ContentType.JSON)
                .baseUri(EnvConfig.BASE_URL)
                .queryParam("track", cancel.getTrack())
                .when()
                .put(EnvConfig.ORDER_PATH + "/cancel")
                .then().log().all();
    }

    @Step("check order canceled successfully")
    public void checkCancelSuccessfully(ValidatableResponse cancelResponse) {
        cancelResponse
                .statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("ok", Matchers.equalTo(true));
    }
}

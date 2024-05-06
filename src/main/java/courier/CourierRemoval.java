package courier;

import configure.EnvConfig;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;

public class CourierRemoval {
    private String courierId;

    public CourierRemoval(String courierId) {
        this.courierId = courierId;
    }

    public String getCourierId() {
        return courierId;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    @Step("delete courier")
    public static ValidatableResponse deleteCourier(CourierRemoval courierRemoval) {
        return given().log().all()
                .contentType(io.restassured.http.ContentType.JSON)
                .baseUri(EnvConfig.BASE_URL)
                .body(courierRemoval)
                .when()
                .delete(EnvConfig.COURIER_PATH + "/" + courierRemoval.getCourierId())
                .then().log().all();
    }

    @Step("check courier removed successfully")
    public void checkRemovalSuccessfully(ValidatableResponse removalResponse) {
        removalResponse
                .statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("ok", Matchers.equalTo(true));
    }
}

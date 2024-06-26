package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderChecks {
    @Step("check orders list shows successfully")
    public void checkOrdersListShows(ValidatableResponse response) {
        response
                .statusCode(HttpURLConnection.HTTP_OK)
                .assertThat()
                .body("orders", notNullValue());
    }

    @Step("check order created successfully")
    public Integer checkOrderCreated(ValidatableResponse response) {
        return response
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .assertThat().body("track", notNullValue())
                .extract()
                .path("track");
    }

    @Step("check order canceled successfully")
    public void checkCancelSuccessfully(ValidatableResponse response) {
        response
                .statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("ok", Matchers.equalTo(true));
    }
}

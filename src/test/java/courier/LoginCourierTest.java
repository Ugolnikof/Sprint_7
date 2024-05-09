package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginCourierTest {
    private String courierId;
    private Courier courier;
    private final CourierProperties client = new CourierProperties();
    private final CourierChecks check = new CourierChecks();

    @Before
    @DisplayName("create courier")
    @Description("positive test")
    public void createCourier() {
        courier = Courier.random();
        client.createCourier(courier);
    }

    @Test
    @DisplayName("login courier")
    @Description("positive test")
    public void loginCourier() {
        CourierCredentials creds = CourierCredentials.from(courier);

        ValidatableResponse loginResponse = client.loginCourier(creds);
        courierId = String.valueOf(check.checkLoginSuccessfully(loginResponse));
    }

    @Test
    @DisplayName("login courier with wrong login")
    @Description("negative test")
    public void loginCourierWrongLogin() {
        CourierCredentials creds = CourierCredentials.from(courier);
        creds.setLogin(RandomStringUtils.randomAlphabetic(8, 16));

        ValidatableResponse loginResponse = client.loginCourier(creds);
        check.checkLoginNotFound(loginResponse);
    }

    @Test
    @DisplayName("login courier without login")
    @Description("negative test")
    public void loginCourierWithoutLogin() {
        CourierCredentials creds = CourierCredentials.from(courier);
        creds.setLogin(null);

        ValidatableResponse loginResponse = client.loginCourier(creds);
        check.checkLoginUnSuccessfully(loginResponse);
    }

    @After
    @DisplayName("delete courier after test")
    @Description("clear data")
    public void deleteCourier() {
        if (courierId != null) {
            CourierRemoval courierRemoval = new CourierRemoval(courierId);

            ValidatableResponse removalResponse = client.deleteCourier(courierRemoval);
            check.checkRemovalSuccessfully(removalResponse);
        }
    }

}

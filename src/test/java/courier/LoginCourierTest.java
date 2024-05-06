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

    @Before
    @DisplayName("create courier")
    @Description("positive test")
    public void createCourier() {
        courier = Courier.random();
        Courier.createCourier(courier);
    }

    @Test
    @DisplayName("login courier")
    @Description("positive test")
    public void loginCourier() {
        CourierCredentials creds = CourierCredentials.from(courier);

        ValidatableResponse loginResponse = Courier.loginCourier(creds);
        courierId = String.valueOf(Courier.checkLoginSuccessfully(loginResponse));
    }

    @Test
    @DisplayName("login courier with wrong login")
    @Description("negative test")
    public void loginCourierWrongLogin() {
        CourierCredentials creds = CourierCredentials.from(courier);
        creds.setLogin(RandomStringUtils.randomAlphabetic(8, 16));

        ValidatableResponse loginResponse = Courier.loginCourier(creds);
        Courier.checkLoginNotFound(loginResponse);
    }

    @Test
    @DisplayName("login courier without login")
    @Description("negative test")
    public void loginCourierWithoutLogin() {
        CourierCredentials creds = CourierCredentials.from(courier);
        creds.setLogin(null);

        ValidatableResponse loginResponse = Courier.loginCourier(creds);
        Courier.checkLoginUnSuccessfully(loginResponse);
    }

    @After
    @DisplayName("delete courier after test")
    @Description("clear data")
    public void deleteCourier() {
        if (courierId != null) {
            CourierRemoval courierRemoval = new CourierRemoval(courierId);

            ValidatableResponse removalResponse = CourierRemoval.deleteCourier(courierRemoval);
            courierRemoval.checkRemovalSuccessfully(removalResponse);
        }
    }

}

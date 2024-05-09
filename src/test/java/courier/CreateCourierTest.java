package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class CreateCourierTest {
    private String courierId;
    private Courier courier;
    private final CourierProperties courierProperties = new CourierProperties();
    private final CourierChecks check = new CourierChecks();

    @Test
    @DisplayName("create courier")
    @Description("positive test")
    public void createCourier() {
        courier = Courier.random();
        ValidatableResponse createResponse = courierProperties.createCourier(courier);
        check.checkCreateSuccessfully(createResponse);

        CourierCredentials creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = courierProperties.loginCourier(creds);
        courierId = String.valueOf(check.checkLoginSuccessfully(loginResponse));
    }

    /**
     * Этот тест падает, потому что в документации и в реализации ответы отличаются:
     * "Этот логин уже используется" vs "Этот логин уже используется. Попробуйте другой."
     */
    @Test
    @DisplayName("create courier again")
    @Description("negative test")
    public void createCourierTwin() {
        courier = Courier.random();
        courierProperties.createCourier(courier);

        Courier courierTwin = Courier.from(courier);
        ValidatableResponse createResponse = courierProperties.createCourier(courierTwin);
        check.checkCreateTwin(createResponse);
    }


    @Test
    @DisplayName("create courier without Password")
    @Description("negative test")
    public void createCourierWithoutPassword() {
        courier = Courier.random();
        Courier courierWithoutPassword = Courier.from(courier);
        courierWithoutPassword.setPassword(null);

        ValidatableResponse createResponse = courierProperties.createCourier(courierWithoutPassword);
        check.checkCreateUnSuccessfully(createResponse);
    }


    @After
    @DisplayName("delete courier after test")
    @Description("clear data")
    public void deleteCourier() {
        if (courierId != null) {
            CourierRemoval courierRemoval = new CourierRemoval(courierId);

            ValidatableResponse removalResponse = courierProperties.deleteCourier(courierRemoval);
            check.checkRemovalSuccessfully(removalResponse);
        }
    }


}

package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class CreateCourierTest {
    private String courierId;
    private Courier courier;

    @Test
    @DisplayName("create courier")
    @Description("positive test")
    public void createCourier() {
        courier = Courier.random();
        ValidatableResponse createResponse = Courier.createCourier(courier);
        Courier.checkCreateSuccessfully(createResponse);

        CourierCredentials creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = Courier.loginCourier(creds);
        courierId = String.valueOf(Courier.checkLoginSuccessfully(loginResponse));
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
        Courier.createCourier(courier);

        Courier courierTwin = Courier.from(courier);
        ValidatableResponse createResponse = Courier.createCourier(courierTwin);
        Courier.checkCreateTwin(createResponse);
    }


    @Test
    @DisplayName("create courier without Password")
    @Description("negative test")
    public void createCourierWithoutPassword() {
        courier = Courier.random();
        Courier courierWithoutPassword = Courier.from(courier);
        courierWithoutPassword.setPassword(null);

        ValidatableResponse createResponse = Courier.createCourier(courierWithoutPassword);
        Courier.checkCreateUnSuccessfully(createResponse);
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

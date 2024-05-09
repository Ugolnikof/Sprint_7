package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private int track;
    private final String[] color;
    OrderProperties orderProperties = new OrderProperties();
    OrderChecks check = new OrderChecks();

    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {new String[] {"BLACK"}},
                {new String[] {"GREY"}},
                {new String[] {"BLACK", "GREY"}},
                {new String[] {}},
        };
    }

    @Test
    @DisplayName("create order")
    @Description("positive test")
    public void createOrder() {
        Order order = Order.createSomeOrder(color);
        ValidatableResponse createOrder = OrderProperties.createOrder(order);
        track = check.checkOrderCreated(createOrder);
    }

    @After
    @DisplayName("cansel order")
    @Description("clear data")
    public void canselOrder() {
        if (track != 0) {
            OrderCancel cancel = new OrderCancel(track);

            ValidatableResponse cancelResponse = orderProperties.orderCancel(cancel);
            check.checkCancelSuccessfully(cancelResponse);
        }
    }
}

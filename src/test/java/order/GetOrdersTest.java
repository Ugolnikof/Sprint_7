package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Test;


public class GetOrdersTest {
    OrderProperties orderProperties = new OrderProperties();
    OrderChecks check = new OrderChecks();

    @Test
    @DisplayName("get orders list")
    @Description("positive test")
    public void getOrders() {
        ValidatableResponse orderList = orderProperties.getOrdersList();
        check.checkOrdersListShows(orderList);
    }

}

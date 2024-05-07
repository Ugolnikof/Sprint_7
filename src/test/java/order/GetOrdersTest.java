package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Test;


public class GetOrdersTest {

    @Test
    @DisplayName("get orders list")
    @Description("positive test")
    public void getOrders() {
        ValidatableResponse orderList = Order.getOrdersList();
        Order.checkOrdersListShows(orderList);
    }

}

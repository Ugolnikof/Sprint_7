package order;

import configure.EnvConfig;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public Order(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Step("check orders list shows successfully")
    public static void checkOrdersListShows(ValidatableResponse orderList) {
        orderList
                .statusCode(HttpURLConnection.HTTP_OK)
                .assertThat()
                .body("orders", notNullValue());
    }

    @Step("get orders list")
    public static ValidatableResponse getOrdersList() {
        return given().log().all()
                .contentType(io.restassured.http.ContentType.JSON)
                .baseUri(EnvConfig.BASE_URL)
                .when()
                .get(EnvConfig.ORDER_PATH)
                .then().log().all();
    }

    @Step("create order")
    public static ValidatableResponse createOrder(Order order) {
        return given().log().all()
                .contentType(io.restassured.http.ContentType.JSON)
                .baseUri(EnvConfig.BASE_URL)
                .body(order)
                .when()
                .post(EnvConfig.ORDER_PATH)
                .then().log().all();
    }

    @Step("check order created successfully")
    public static Integer checkOrderCreated(ValidatableResponse createOrder) {
        return createOrder
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .assertThat().body("track", notNullValue())
                .extract()
                .path("track");
    }

    public static Order createSomeOrder(String[] color) {
        return new Order("Паша", "Техник", "ул.Пушкина д.Колотушкина", "Солевая", "88002000600", 5, "2020-06-06", "Моё имя Павел", color);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public void setRentTime(int rentTime) {
        this.rentTime = rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String[] getColor() {
        return color;
    }

    public void setColor(String[] color) {
        this.color = color;
    }

}

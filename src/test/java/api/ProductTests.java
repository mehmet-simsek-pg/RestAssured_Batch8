package api;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ProductTests extends BaseTest{

    private int productID;

    @Test(priority = 1, description = "Create a new product")
    public void createProduct(){
        String createBody = """
                {
                  "name": "Lenovo ThinkPad",
                  "category": "Elektronik",
                  "price": 499.9,
                  "stock": 25
                }
                """;

        Response response =
        given()
                .spec(request)
                .body(createBody)

                .when()
                .post("/products")

                .then()
                .statusCode(201)
                .body("name", equalTo("Lenovo ThinkPad"))
                .body("price", notNullValue())
                .extract().response();

        productID = response.path("id");

    }

    @Test(priority = 2, description = "Get all products data")
    public void getAllProducts() {

        given()
                .spec(request)

                .when()
                .get("/products")

                .then()
                .statusCode(200);
    }

    @Test(priority = 3, description = "Get product by id")
    public void getProductById() {

        given()
                .spec(request)

                .when()
                .get("/products/{id}", productID)

                .then()
                .statusCode(200);
    }

    @Test(priority = 4, description = "Update product")
    public void updateProduct() {

        String updateBody = """
                {
                  "name": "Gaming Klavye",
                  "category": "Elektronik",
                  "price": 799.9,
                  "stock": 15
                }
                """;

        given()
                .spec(request)
                .body(updateBody)

                .when()
                .put("/products/{id}", productID)

                .then()
                .statusCode(200);
    }

    @Test(priority = 5, description = "Delete existing product")
    public void deleteProduct() {

        given()
                .spec(request)

                .when()
                .delete("/products/{id}", productID)

                .then()
                .statusCode(204);
    }
}

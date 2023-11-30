package test;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

public class ProductTest extends BaseTest{

    @Test
    public void verifyGetProduct(){
        requestFactory.getAllProducts().then().log().all().statusCode(200);
    }

    @Test
    public void verifyAddProduct(){
        String requestPayload = new JSONObject()
                .put("name", "Samsung Mobile")
                .put("type", "Mobile")
                .put("price", 100)
                .toString();
        requestFactory.addProducts(requestPayload).then().log().all().statusCode(201);
    }

    @Test
    public void verifyAddProductWithRequestPayloadAsMap(){
        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("name", "Samsung Mobile");
        requestPayload.put("type", "Mobile");
        requestPayload.put("price", 100);
        requestFactory.addProducts(requestPayload).then().log().all().statusCode(201);
    }

    @Test
    public void verifyEndToEndCallFlow(){
        // 1. Send a Post request to create a product
        String requestPayload = new JSONObject()
                .put("name", "Samsung Mobile")
                .put("type", "Mobile")
                .put("price", 100)
                .toString();

        Response response = requestFactory.addProducts(requestPayload);
        response.then().log().all().statusCode(201);

        // Get id of the product


        // 2. Send a Get request to verify if the product is added


        // 3. Send a Put request to edit the product


        // 4. Send a Delete request to delete the product


        // 5. Send a Get request to verify Delete


    }
}

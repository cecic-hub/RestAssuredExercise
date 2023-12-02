package test;

import com.aventstack.extentreports.Status;
import enums.PetStatusEnum.PetStatus;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

public class PetTest extends BaseTest{

    @Test
    public void verifyGetPetByStatus(){
        extentReport.createTest("Verify Get Pet By Status");
       Response response =  requestFactoryForPet.getPetByStatus(PetStatus.available);
        extentReport.addLog(Status.INFO, response.asPrettyString());
        response.then().statusCode(200);
    }

    @Test
    public void verifyAddPet(){
        extentReport.createTest("Verify Add Pet");

        Map<String, Object> category = new HashMap<>();
        category.put("id", 0);
        category.put("name", "African forest elephant");

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add("abc.com");

        ArrayList<Object> tags = new ArrayList<>();
        tags.add(category);

        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("id", 0);
        requestPayload.put("category", category);
        requestPayload.put("name", "elephant");
        requestPayload.put("photoUrls", photoUrls);
        requestPayload.put("tags", tags);
        requestPayload.put("status", PetStatus.pending);

        Response response = requestFactoryForPet.addPet(requestPayload);
        extentReport.addLog(Status.INFO, response.asPrettyString());
        response.then().statusCode(200);
    }

    @Test
    public void verifyEndToEndCallFlow(){
        extentReport.createTest("Verify End to End Call Flow");

        // 1. Send a Post request to create a Pet
        Map<String, Object> category = new HashMap<>();
        category.put("id", 0);
        category.put("name", "African forest elephant");

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add("abc.com");

        ArrayList<Object> tags = new ArrayList<>();
        tags.add(category);

        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("id", 0);
        requestPayload.put("category", category);
        requestPayload.put("name", "elephant");
        requestPayload.put("photoUrls", photoUrls);
        requestPayload.put("tags", tags);
        requestPayload.put("status", PetStatus.pending.getPetStatus());

        Response responseAdd = requestFactoryForPet.addPet(requestPayload);
        extentReport.addLog(Status.INFO, "Add new pet");
        extentReport.addLog(Status.INFO, responseAdd.asPrettyString());
        responseAdd.then().statusCode(200);

        // Get id of the Pet
        long petId = responseAdd.jsonPath().getLong("id");
        extentReport.addLog(Status.INFO, "New pet id is " + petId);

        // 2. Send a Get request to verify if the Pet is added
        Response responseVerify = requestFactoryForPet.getPetById(petId);
        extentReport.addLog(Status.INFO, "Get pet by id");
        extentReport.addLog(Status.INFO, responseVerify.asPrettyString());
        responseVerify.then().statusCode(200);

        // 3. Send a Put request to edit the Pet
        requestPayload.put("id", petId);
        requestPayload.put("status", PetStatus.available.getPetStatus());

        Response responseEdit = requestFactoryForPet.updatePet(requestPayload);
        extentReport.addLog(Status.INFO, "Update pet status to available");
        extentReport.addLog(Status.INFO, responseEdit.asPrettyString());
        responseEdit.then().statusCode(200);

        // 4. Send a Get request to verify if the Pet is updated
        Response responseVerifyUpdate = requestFactoryForPet.getPetById(petId);
        extentReport.addLog(Status.INFO, "Assert pet status is available");
        extentReport.addLog(Status.INFO, responseVerifyUpdate.asPrettyString());
        responseVerifyUpdate
                .then()
                .statusCode(200)
                .body("status", equalTo(PetStatus.available.getPetStatus()));
    }
}

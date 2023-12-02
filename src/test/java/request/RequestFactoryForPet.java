package request;

import client.RestClient;
import enums.PetStatusEnum.PetStatus;
import io.restassured.response.Response;

public class RequestFactoryForPet {
    RestClient restClient;

    public RequestFactoryForPet(){
        restClient = new RestClient();
    }

    public Response getPetByStatus(PetStatus status){
        return restClient.SendGetRequest("/v2/pet/findByStatus?status=" + status.getPetStatus());
    }

    public Response getPetById(long id){
        return restClient.SendGetRequest("/v2/pet/" + id);
    }

    public Response addPet(Object requestPayload){
        return restClient.SendPostRequest("/v2/pet", requestPayload);
    }

    public Response updatePet(Object requestPayload){
        return restClient.SendPutRequest("/v2/pet", requestPayload);
    }

}

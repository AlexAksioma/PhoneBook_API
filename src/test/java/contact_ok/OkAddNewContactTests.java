package contact_ok;

import dto.*;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class OkAddNewContactTests extends OkBase{

    String tokenNew;

    @BeforeClass
    public void login() throws IOException {
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("qwerty3171@gmail.com")
                .password("Qwerty123!_")
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO), JSON);
        Request request = new Request.Builder()
                .url(baseUrl+"/v1/user/login/usernamepassword")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        AuthResponseDTO responseDTO = gson.fromJson(response.body().string(), AuthResponseDTO.class);
        tokenNew = responseDTO.getToken();
    }

    @Test
    public void addNewContactPositiveTest() throws IOException {
        int i = new Random().nextInt(1000)+1000;
        ContactDTO contact = ContactDTO.builder()
                .name("Alex"+i)
                .lastName("LastName"+i)
                .email("email"+i+"@mail.com")
                .phone("1231231"+i)
                .address("Haifa")
                .description("its my contact")
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(baseUrl+"/v1/contacts")
                .addHeader("Authorization",tokenNew)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        ResponseMessageDTO responseMessageDTO = gson.fromJson(response.body().string(), ResponseMessageDTO.class);
        //Assert.assertEquals(responseMessageDTO.getMessage(), "Contact was added!");

    }

    @Test
    public void addNewContactNegativeTest_WrongPhone() throws IOException {
        int i = new Random().nextInt(1000)+1000;
        ContactDTO contact = ContactDTO.builder()
                .name("Alex"+i)
                .lastName("LastName"+i)
                .email("email"+i+"@mail.com")
                .phone("")
                .address("Haifa")
                .description("its my contact")
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(baseUrl+"/v1/contacts")
                .addHeader("Authorization",tokenNew)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        System.out.println("response code --> "+response.code());
        ErrorDTO errorDTO  = gson.fromJson(response.body().string(), ErrorDTO.class);
        System.out.println(errorDTO.toString());
    }

    @Test
    public void addNewContactNegativeTest_NotAuthorisation() throws IOException {
        int i = new Random().nextInt(1000)+1000;
        ContactDTO contact = ContactDTO.builder()
                //.id("id="+i)
                .name("Alex"+i)
                .lastName("LastName"+i)
                .email("email"+i+"@mail.com")
                .phone("")
                .address("Haifa")
                .description("its my contact")
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(baseUrl+"/v1/contacts")
                .addHeader("Authorization","111")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        System.out.println("response code --> "+response.code());
        ErrorDTO errorDTO  = gson.fromJson(response.body().string(), ErrorDTO.class);
        System.out.println(errorDTO.toString());
    }

    @Test
    public void addNewContactNegativeTest_DuplicateContact() throws IOException { //Bug
        int i = new Random().nextInt(1000)+1000;
        ContactDTO contact = ContactDTO.builder()
                .name("Alex"+i)
                .lastName("LastName"+i)
                .email("email"+i+"@mail.com")
                .phone("111112222233")
                .address("Haifa")
                .description("its my contact")
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(baseUrl+"/v1/contacts")
                .addHeader("Authorization",tokenNew)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.isSuccessful());

        Response responseNew = client.newCall(request).execute();

        Assert.assertFalse(responseNew.isSuccessful());
        System.out.println("response code --> "+responseNew.code());
        ErrorDTO errorDTO  = gson.fromJson(responseNew.body().string(), ErrorDTO.class);
        System.out.println(errorDTO.toString());
    }
}

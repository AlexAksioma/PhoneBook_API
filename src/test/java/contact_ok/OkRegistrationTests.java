package contact_ok;

import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class OkRegistrationTests extends OkBase {
    @Test
    public void registrationPositiveTest() throws IOException {
        int i = new Random().nextInt(1000) + 1000;
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("AlexDTO" + i + "@gmail.com")
                .password(i + "AlexDTO$")
                .build();
        System.out.println(requestDTO.toString());
        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO), JSON);

        Request request = new Request.Builder()
                .url(baseUrl + "/v1/user/registration/usernamepassword")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        String responseJson = response.body().string();
        AuthResponseDTO responseDTO = gson.fromJson(responseJson, AuthResponseDTO.class);
        System.out.println(responseDTO.getToken());
        System.out.println("response code = "+response.code());
    }

    @Test
    public void registrationNegativeTest_WrongUserName() throws IOException {
        int i = new Random().nextInt(1000) + 1000;
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("AlexDTO" + i + "gmail.com")
                .password(i + "AlexDTO$")
                .build();
        System.out.println(requestDTO.toString());
        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO), JSON);

        Request request = new Request.Builder()
                .url(baseUrl + "/v1/user/registration/usernamepassword")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());

        System.out.println("Response code = " + response.code());
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        System.out.println(errorDTO.getStatus()
                + "-->" + errorDTO.getMessage()
                + "--> " + errorDTO.getError());
    }
}

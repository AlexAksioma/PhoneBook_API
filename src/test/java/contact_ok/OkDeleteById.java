package contact_ok;

import dto.ResponseMessageDTO;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkDeleteById extends OkBase{

    String id = "fa779c5b-da28-40de-a134-4cfa54c670f6";
    @Test
    public void deleteById() throws IOException {
        Request request  = new Request.Builder()
                .url(baseUrl+"/v1/contacts/"+id)
                .addHeader("Authorization", token)
                .delete()
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        ResponseMessageDTO messageDTO = gson.fromJson(response.body().string(), ResponseMessageDTO.class);
        System.out.println(messageDTO.getMessage());
    }
}

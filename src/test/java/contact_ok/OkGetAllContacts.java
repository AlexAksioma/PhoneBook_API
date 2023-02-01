package contact_ok;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.GetAllContactsDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkGetAllContacts extends OkBase{

    //Gson gson = new Gson();
    //OkHttpClient client = new OkHttpClient();

    @Test
    public void getAllContacts() throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl+"/v1/contacts")
                .addHeader("Authorization",token)
                .build()
                ;

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());

        GetAllContactsDTO  contacts = gson.fromJson(response.body().string(), GetAllContactsDTO.class);
        for (ContactDTO contactDTO: contacts.getContacts()) {
            System.out.println(contactDTO.getId());
            System.out.println(contactDTO.getEmail());
            System.out.println(contactDTO.getPhone());
            System.out.println("===================================");
        }
    }
}

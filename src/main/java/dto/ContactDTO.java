package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*    "id": "string",
      "name": "string",
      "lastName": "string",
      "email": "string",
      "phone": "8325242373756",
      "address": "string",
      "description": "string"*/
@Getter
@Setter
@Builder
@ToString

public class ContactDTO {
    String id;
    String name;
    String lastName;
    String email;
    String phone;
    String address;
    String description;
}

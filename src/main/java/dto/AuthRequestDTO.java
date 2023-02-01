package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*{
  "username": "string",
  "password": "F[8_/<M1?r|VS}C=r%wkdO D7HG&u{T@}aXeEI46GDfHV;~6PKQHdCu`BuJ\\)Me]|go<<//NZr^01YInY4trY\\Gd1]<0RIH8hTOnl1*["
}*/
@Getter
@Setter
@Builder
@ToString

public class AuthRequestDTO {
    private String username;
    private String password;
}

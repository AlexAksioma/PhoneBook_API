package dto;
/*{
  "timestamp": "2023-01-31T16:16:51.976Z",
  "status": 0,
  "error": "string",
  "message": {},
  "path": "string"
}*/

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString

public class ErrorDTO {

    private int status;
    private Object error;
    private Object message;
    private String path;
}

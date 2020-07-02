package az.pashabank.apl.ms.thy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewCardOrderStep1Request {

    private Integer appId;
    private String name;
    private String surname;
    private String mobileNo;
    private String email;

}

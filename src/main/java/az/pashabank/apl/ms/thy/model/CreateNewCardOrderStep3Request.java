package az.pashabank.apl.ms.thy.model;

import az.pashabank.apl.ms.thy.entity.CRSAnswerEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewCardOrderStep3Request {

    private Integer appId;
    private String name;
    private String surname;
    private String mobileNo;
    private String email;
    private String pin;
    private String birthDate;
    private String tkNo;
    @JsonIgnore
    private String passportName;
    @JsonIgnore
    private String passportSurname;
    private Integer acceptedTerms;
    private Integer acceptedGsa;
    private Integer acceptedFatca;
    private List<CRSAnswer> crsAnswers;
    private String ipAddress;
    @JsonIgnore
    private List<CRSAnswerEntity> crsAnswerEntities;

}

package az.pashabank.apl.ms.thy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

@Data
@NoArgsConstructor
@Entity
@Table(name = "thy_applications")
public class ThyApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_thy_applications")
    @SequenceGenerator(sequenceName = "seq_thy_applications", allocationSize = 1, name = "seq_thy_applications")
    private Integer id;
    @JsonIgnore
    private String residency;
    @JsonIgnore
    private String nationality;
    private String name;
    private String surname;
    @JsonIgnore
    private String patronymic;
    @JsonIgnore
    private String gender;
    private String birthDate;
    @JsonIgnore
    private String registrationCity;
    @JsonIgnore
    private String registrationAddress;
    @JsonIgnore
    private String domicileAddress;
    private String mobileNumber;
    private String email;
    private String pin;
    @JsonIgnore
    private String secretCode;
    @JsonIgnore
    private String workplace;
    @JsonIgnore
    private String position;
    private boolean urgent;
    private String tkNo;
    private String passportName;
    private String passportSurname;
    private boolean acceptedTerms;
    private boolean acceptedGsa;
    @Column()
    private boolean acceptedFatca;
    private String currency;
    private Integer cardProductId;
    private Integer period;
    private String branchCode;
    private String branchName;
    private Integer amountToPay;
    @JsonIgnore
    private boolean mailSent;
    @CreationTimestamp
    private LocalDateTime createDate;
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
    private boolean active;
    @JsonIgnore
    private boolean paymentCompleted;
    private String paymentMethod;
    private Integer couponId;
    @JsonIgnore
    private String roamingNumber;
    private Integer promoCodeId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "app", fetch = FetchType.LAZY)
    private List<CRSAnswerEntity> crsAnswers;
    private Integer step;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "app", fetch = FetchType.LAZY)
    private List<UploadWrapperEntity> uploadWrappers;

    public ThyApplicationEntity(Integer id, String name, String surname, String mobileNumber, String email, Integer step) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.step = step;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ThyApplicationEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("residency='" + residency + "'")
                .add("nationality='" + nationality + "'")
                .add("name='" + name + "'")
                .add("surname='" + surname + "'")
                .add("patronymic='" + patronymic + "'")
                .add("gender='" + gender + "'")
                .add("birthDate='" + birthDate + "'")
                .add("registrationCity='" + registrationCity + "'")
                .add("registrationAddress='" + registrationAddress + "'")
                .add("domicileAddress='" + domicileAddress + "'")
                .add("mobileNumber='" + mobileNumber + "'")
                .add("email='" + email + "'")
                .add("secretCode='" + secretCode + "'")
                .add("workplace='" + workplace + "'")
                .add("position='" + position + "'")
                .add("urgent=" + urgent)
                .add("tkNo='" + tkNo + "'")
                .add("passportName='" + passportName + "'")
                .add("passportSurname='" + passportSurname + "'")
                .add("acceptedTerms=" + acceptedTerms)
                .add("acceptedGsa=" + acceptedGsa)
                .add("currency='" + currency + "'")
                .add("cardProductId=" + cardProductId)
                .add("period=" + period)
                .add("branchCode='" + branchCode + "'")
                .add("branchName='" + branchName + "'")
                .add("amountToPay=" + amountToPay)
                .add("mailSent=" + mailSent)
                .add("createDate=" + createDate)
                .add("lastUpdate=" + lastUpdate)
                .add("active=" + active)
                .add("paymentCompleted=" + paymentCompleted)
                .add("paymentMethod='" + paymentMethod + "'")
                .add("roamingNumber='" + roamingNumber + "'")
                .add("step=" + step)
                .toString();
    }
}

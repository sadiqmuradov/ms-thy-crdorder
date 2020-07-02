package az.pashabank.apl.ms.thy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "thy_source_of_income")
public class SourceOfIncomeEntity {

    @Id
    private Integer id;
    private String text;
    @JsonIgnore
    private String lang;

}

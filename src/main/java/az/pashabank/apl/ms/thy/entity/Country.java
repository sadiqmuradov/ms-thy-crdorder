package az.pashabank.apl.ms.thy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "thy_countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "thy_country_seq")
    @SequenceGenerator(sequenceName = "thy_country_seq", name = "thy_country_seq", allocationSize = 1)
    @JsonIgnore
    private long id;

    private String code;
    private String name;
    private String region;
    @JsonIgnore
    private String lang;

}

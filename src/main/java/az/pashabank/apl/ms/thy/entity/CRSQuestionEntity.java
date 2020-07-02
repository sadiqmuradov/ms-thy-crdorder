package az.pashabank.apl.ms.thy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "thy_crs_questions")
public class CRSQuestionEntity {

    @Id
    private int id;
    private String lang;
    private String question;
    private String addquestion;
}

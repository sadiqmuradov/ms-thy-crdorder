package az.pashabank.apl.ms.thy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.StringJoiner;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "thy_crs_answers")
@IdClass(CRSAnswerId.class)
public class CRSAnswerEntity {

    @Id
    private Integer questionId;
    private Integer answer;
    private String description;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appId", nullable = false)
    @JsonIgnore
    private ThyApplicationEntity app;

    public CRSAnswerEntity(Integer questionId, Integer answer, String description) {
        this.questionId = questionId;
        this.answer = answer;
        this.description = description;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CRSAnswerEntity.class.getSimpleName() + "[", "]")
                .add("questionId=" + questionId)
                .add("answer=" + answer)
                .add("description='" + description + "'")
                .toString();
    }
}

package az.pashabank.apl.ms.thy.model;

import java.util.StringJoiner;

public class CRSAnswer {

    private Integer appId;
    private Integer questionId;
    private Integer answer;
    private String description;

    public CRSAnswer() {
    }

    public CRSAnswer(Integer questionId, Integer answer, String description) {
        this.questionId = questionId;
        this.answer = answer;
        this.description = description;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CRSAnswer.class.getSimpleName() + "[", "]")
                .add("appId=" + appId)
                .add("questionId=" + questionId)
                .add("answer=" + answer)
                .add("description='" + description + "'")
                .toString();
    }

}

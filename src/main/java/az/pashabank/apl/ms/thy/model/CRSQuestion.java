package az.pashabank.apl.ms.thy.model;

public class CRSQuestion {
    private int id;
    private String lang;
    private String question;
    private String addquestion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAddquestion() {
        return addquestion;
    }

    public void setAddquestion(String addquestion) {
        this.addquestion = addquestion;
    }

    @Override
    public String toString() {
        return "CRSQuestion{" +
                "id=" + id +
                ", lang='" + lang + '\'' +
                ", question='" + question + '\'' +
                ", addquestion='" + addquestion + '\'' +
                '}';
    }
}

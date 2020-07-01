package az.pashabank.apl.ms.thy.model.thy;

public class MemberData {

    private String format;
    private String key;
    private String type;
    private String value;

    public MemberData() {
    }

    public MemberData(String format, String key, String type, String value) {
        this.format = format;
        this.key = key;
        this.type = type;
        this.value = value;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MemberData { " +
                "format=" + format + ", " +
                "key=" + key + ", " +
                "type=" + type + ", " +
                "value=" + value + " " +
                "}";
    }

}

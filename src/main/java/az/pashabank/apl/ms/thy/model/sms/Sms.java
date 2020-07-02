package az.pashabank.apl.ms.thy.model.sms;

public class Sms {

    private String to;
    private String text;
    private String channel;

    public Sms() {}

    public Sms(String to, String text, String channel) {
        this.to = to;
        this.text = text;
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "to='" + to + '\'' +
                ", text='" + text + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }
}

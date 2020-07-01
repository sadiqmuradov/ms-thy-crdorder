package az.pashabank.apl.ms.thy.model.thy;

public class EnrollmentChannel {

    private int firstChannel;
    private int secondChannel;

    public EnrollmentChannel() {
    }

    public EnrollmentChannel(int firstChannel, int secondChannel) {
        this.firstChannel = firstChannel;
        this.secondChannel = secondChannel;
    }

    public int getFirstChannel() {
        return firstChannel;
    }

    public void setFirstChannel(int firstChannel) {
        this.firstChannel = firstChannel;
    }

    public int getSecondChannel() {
        return secondChannel;
    }

    public void setSecondChannel(int secondChannel) {
        this.secondChannel = secondChannel;
    }

    @Override
    public String toString() {
        return "EnrollmentChannel { firstChannel=" + firstChannel + ", secondChannel=" + secondChannel + " }";
    }

}

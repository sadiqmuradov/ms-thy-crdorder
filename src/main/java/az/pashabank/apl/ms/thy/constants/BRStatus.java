package az.pashabank.apl.ms.thy.constants;

public enum BRStatus {
    CANCELED (0),
    ERROR (1),
    IDLE (2),
    PENDING (3),
    SUCCESS (4);

    private int statusCode;

    BRStatus(int statusCode) {
        this.statusCode = statusCode;
    }
}

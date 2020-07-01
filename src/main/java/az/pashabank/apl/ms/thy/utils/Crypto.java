package az.pashabank.apl.ms.thy.utils;

import java.util.UUID;

public class Crypto {

    private Crypto() {
    }

    public static final String getUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    public static final String getDoubleUuid() {
        UUID uuid = UUID.randomUUID();
        UUID uuid1 = UUID.randomUUID();

        return (uuid.toString() + uuid1.toString()).replaceAll("-", "");
    }

}

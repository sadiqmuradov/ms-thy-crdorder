package az.pashabank.apl.ms.thy.model;

import java.util.Arrays;
import java.util.StringJoiner;

public class UploadWrapper {

    private byte[] bytes;
    private long size;
    private String name;
    private String location;
    private String contentType;

    public UploadWrapper() {
    }

    public UploadWrapper(long size, String name, String location, String contentType) {
        this.size = size;
        this.name = name;
        this.location = location;
        this.contentType = contentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UploadWrapper.class.getSimpleName() + "[", "]")
                .add("bytes=" + Arrays.toString(bytes))
                .add("size=" + size)
                .add("name='" + name + "'")
                .add("location='" + location + "'")
                .add("contentType='" + contentType + "'")
                .toString();
    }
}

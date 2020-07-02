package az.pashabank.apl.ms.thy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.StringJoiner;

public class UploadWrapper {

    private byte[] bytes;
    private String contentType;
    @JsonIgnore
    private long size;
    @JsonIgnore
    private String name;
    @JsonIgnore
    private String location;

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
                .add("size=" + size)
                .add("name='" + name + "'")
                .add("location='" + location + "'")
                .add("contentType='" + contentType + "'")
                .toString();
    }
}

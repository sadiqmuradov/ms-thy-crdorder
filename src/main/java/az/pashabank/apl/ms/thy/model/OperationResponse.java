package az.pashabank.apl.ms.thy.model;

import az.pashabank.apl.ms.thy.constants.ResultCode;

public class OperationResponse<T> {

    private ResultCode code;
    private String message;
    private T data;

    public OperationResponse() {
    }

    public OperationResponse(ResultCode code) {
        this.code = code;
    }

    public ResultCode getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OperationResponse { " + "code=" + code + ", message=" + message + ", data=" + data + " }";
    }

}

package az.pashabank.apl.ms.thy.model.sms;


import az.pashabank.apl.ms.thy.constants.BRStatus;

public class BaseResponse {

    private BRStatus status;
    private String error;
    private Object data;

    public BaseResponse() {}

    public BaseResponse(BRStatus status) {
        this.status = status;
    }

    public BRStatus getStatus() {
        return status;
    }

    public void setStatus(BRStatus status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status=" + status +
                ", error='" + error + '\'' +
                ", data=" + data +
                '}';
    }
}
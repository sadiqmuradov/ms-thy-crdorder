package az.pashabank.apl.ms.thy.model.thy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckTkRestResponse {

    private ResponseHeader responseHeader;
    @JsonProperty("return")
    private CheckTkReturn checkTkReturn;

    public CheckTkRestResponse() {
    }

    public CheckTkRestResponse(CheckTkReturn checkTkReturn) {
        this.checkTkReturn = checkTkReturn;
    }

    public CheckTkRestResponse(ResponseHeader responseHeader, CheckTkReturn checkTkReturn) {
        this.responseHeader = responseHeader;
        this.checkTkReturn = checkTkReturn;
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public CheckTkReturn getCheckTkReturn() {
        return checkTkReturn;
    }

    public void setCheckTkReturn(CheckTkReturn checkTkReturn) {
        this.checkTkReturn = checkTkReturn;
    }

    @Override
    public String toString() {
        return "CheckTkRestResponse { responseHeader=" + responseHeader + ", checkTkReturn=" + checkTkReturn + " }";
    }

}

package in.gov.udiseplus.kv.report.bean;

import lombok.Data;

@Data
public class TransProfileV2Resp{

    private int status;
    private String message;
    private TransProfileV2 response;
    private String responseCode;

}
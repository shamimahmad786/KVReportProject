package in.gov.udiseplus.kv.report.bean;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TcDcPointResp {

    private Integer id;
    private String kvCode;
    private Integer teacherId;
    private String transferId;
    private Integer teacherEmployeeCode;
    private Integer dcStayAtStation;
    private Integer dcPeriodAbsence;
    private Integer dcReturnStation;
    private Integer dcStayStationPoint;
    private String dcTenureHardPoint;
    private Integer dcPhysicalChallengedPoint;
    private Integer dcMdDfGroungPoint;
    private Integer dcLtrPoint;
    private Integer dcSpousePoint;
    private Integer dcSinglePoint;
    private Integer dcNonSopouseSinglePoint;
    private Integer dcRjcmNjcmPoint;
    private Integer dcTotalPoint;
    private Integer dcSaveYn;
    private Integer tcSaveYn;
    private Integer tcStayAtStation;
    private Integer tcPeriodAbsence;
    private Integer tcStayStationPoint;
    private Integer tcTenureHardPoint;
    private Integer tcPhysicalChallengedPoint;
    private Integer tcMdDfGroungPoint;
    private Integer tcLtrPoint;
    private Integer tcSpousePoint;
    private Integer tcSinglePoint;
    private Integer tcNonSopouseSinglePoint;
    private Integer tcRjcmNjcmPoint;
    private Integer tcTotalPoint;
    private String finalStatus;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime createdDateTime;
    private String updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime updateDateTime;
    private String inityear;

}

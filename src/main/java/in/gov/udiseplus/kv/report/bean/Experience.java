package in.gov.udiseplus.kv.report.bean;

import lombok.Data;

@Data
public class Experience {
    private int workExperienceId;
    private int teacherId;
    private String udiseSchCode;
    private Object schoolId;
    private String workStartDate;
    private Object workEndDate;
    private String positionType;
    private Object natureOfAppointment;
    private String appointedForSubject;
    private String udiseSchoolName;
    private Object shiftType;
    private Object verifyFlag;
    private Object verifiedType;
    private Object createdBy;
    private Object createdTime;
    private Object modifiedBy;
    private Object modifiedTime;
    private String groundForTransfer;
    private String currentlyActiveYn;
    private Object shift_yn;

}
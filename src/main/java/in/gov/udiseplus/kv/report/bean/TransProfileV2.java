package in.gov.udiseplus.kv.report.bean;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TransProfileV2 {

    private Integer id;
    private Integer teacherId;
    private Integer applyTransferYn;
    private String choiceKv1StationCode;
    private String choiceKv1StationName;
    private String choiceKv2StationCode;
    private String choiceKv2StationName;
    private String choiceKv3StationCode;
    private String choiceKv3StationName;
    private String choiceKv4StationCode;
    private String choiceKv4StationName;
    private String choiceKv5StationCode;
    private String choiceKv5StationName;
    private String displacement1StationCode;
    private String displacement1StationName;
    private String displacement2StationCode;
    private String displacement2StationName;
    private String displacement3StationCode;
    private String displacement3StationName;
    private String displacement4StationCode;
    private String displacement4StationName;
    private String displacement5StationCode;
    private String displacement5StationName;
    private Integer spouseKvsYnD;
    private String spouseEmpCode;
    private String spousePost;
    private String spouseStationName;
    private Integer personalStatusMdgD;
    private String patientName;
    private String patientAilment;
    private String patientHospital;
    private String patientMedicalOfficerName;
    private String patientMedicalOfficerDesignation;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime medicalCertificateIssueDate;
    private Integer personalStatusSpD;
    private String singleParentGround;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime singleParentCertificateIssueDate;
    private Integer personalStatusDfpD;
    private String deathOfFamilyGround;
    private String nameOfFamilyMember;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime deathCertificateIssueDate;
    private String relationOfDeathPerson;
    private String childDifferentName;
    private String childDifferentDisabilityName;
    private String childDifferentDisabilityPrcnt;
    private Integer memberJCM;
    private String positionOfNjcmRjcm;
    private String child_10_12YnD;
    private String careGiverYnD;
    private String careGiverName;
    private String careGiverRelation;
    private String careGiverDisabilityName;
    private Integer careGiverDisabilityPrcnt;
    private String childDifferentAbleYnD;
    private String child1012Name;
    private String child1012Class;
    private String child1012School;
    private String child1012Board;
    private String shiftChangeSameSchool;
    private String careGiverFaimlyYnD;
    private Integer disciplinaryYn;
    private Integer absenceDaysOne;
    private String transferStatus;
    private String transferId;
    private String teacherEmployeeCode;
    private Integer surveHardYn;
    private String periodAbsent;
    private String relationWithEmplMdg;
    private String transEmpIsDeclaration;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime transEmpDeclaraionDate;
    private String transEmpDeclarationIp;
    private String inityear;
    private String transEmpIsDeclaaration;

}

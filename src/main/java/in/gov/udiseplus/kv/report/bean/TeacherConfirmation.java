package in.gov.udiseplus.kv.report.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TeacherConfirmation {
	private Integer id;
    private String teacherName;
    private Integer teacherId;
    private String teacherGender;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private String teacherDob;
    private String teacherEmployeeCode;
    private String teacherDisabilityYn;
    private String workExperienceWorkStartDatePresentKv;
    private String workExperienceAppointedForSubject;
    private String lastPromotionPositionType;
    private String ip;
    private String createdDateTime;
}

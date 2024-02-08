package in.gov.udiseplus.kv.report.bean;

import java.util.List;

import lombok.Data;

@Data
public class TeacherProfileResponseData {
    private SchoolDetails schoolDetails;
    private TeacherProfile teacherProfile;
    private List<Experience> experience;
}


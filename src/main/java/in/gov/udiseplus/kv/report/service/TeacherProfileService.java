package in.gov.udiseplus.kv.report.service;


import java.io.IOException;

import org.springframework.http.ResponseEntity;

import in.gov.udiseplus.kv.report.bean.ConfirmedTeacherDetailsResp;
import in.gov.udiseplus.kv.report.bean.TeacherConfirmation;


public interface TeacherProfileService {
	
	public ResponseEntity<?> teacherProfileReport(String token, String username, Integer teacherId) throws IOException;

	public ResponseEntity<?> getTeacherDetailsPdf(ConfirmedTeacherDetailsResp dataObj,TeacherConfirmation dataObj1) throws IOException;

	public ResponseEntity<?> genTeacherConfirmation(String token, String username, Integer teacherId)throws IOException;

	public ResponseEntity<?> genTransManagementPdf()throws IOException;
}

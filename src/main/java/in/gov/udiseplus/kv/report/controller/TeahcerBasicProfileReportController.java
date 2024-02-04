package in.gov.udiseplus.kv.report.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import in.gov.udiseplus.kv.report.bean.ConfirmedTeacherDetailsResp;
import in.gov.udiseplus.kv.report.bean.TeacherConfirmation;
import in.gov.udiseplus.kv.report.service.TeacherProfileService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TeahcerBasicProfileReportController {

	@Autowired
	TeacherProfileService teacherProfileService;

	@GetMapping(value = "/getTeacherBasicDetailPdf")
	public ResponseEntity<?> fetchTeacherBasicDetailPdf(
	@RequestParam(value ="token", required = true) String token,
	@RequestParam(value ="username", required = true) String username,
	@RequestParam(value ="teacherId", required = true) Integer teacherId) throws IOException {

		/*String token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrdl8xODE0Iiwic2NvcGVzIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dLCJpc3MiOiJodHRwOi8vaGNlbGFsLmNvbSIsImlhdCI6MTcwNjc4ODgzMCwiZXhwIjoxNzA2ODc1MjMwfQ.9yEKGM7jwukxaWq4_A8nLVw-xNJAkg2sDqSoi2P5xV0";
		String username="kv_1814";
		Integer teacherId=42677;*/
		
		ResponseEntity<?> apiResp=teacherProfileService.teacherProfileReport(token,username,teacherId);
		
		ConfirmedTeacherDetailsResp dataObj=(ConfirmedTeacherDetailsResp) apiResp.getBody();
		
		ResponseEntity<?> apiResp2= teacherProfileService.genTeacherConfirmation(token,username,teacherId);
		TeacherConfirmation dataObj1=(TeacherConfirmation) apiResp2.getBody();
		
		ResponseEntity<?> resp=teacherProfileService.getTeacherDetailsPdf(dataObj,dataObj1);

		return resp;
	}
	
	
	
	
	
	
	@GetMapping(value = "/genTransferOrderPdf")
	public ResponseEntity<?> fetchTransferOrderPdf() throws IOException {
		ResponseEntity<?> resp=teacherProfileService.genTransferOrderPdf();
		return resp;
	}
}

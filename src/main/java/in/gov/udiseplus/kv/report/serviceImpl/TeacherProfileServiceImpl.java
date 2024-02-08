package in.gov.udiseplus.kv.report.serviceImpl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.gov.udiseplus.kv.report.bean.ConfirmedTeacherDetailsResp;
import in.gov.udiseplus.kv.report.bean.TcDcPointResp;
import in.gov.udiseplus.kv.report.bean.TeacherConfirmation;
import in.gov.udiseplus.kv.report.bean.TeacherConfirmationResp;
import in.gov.udiseplus.kv.report.bean.TransProfileV2Resp;
import in.gov.udiseplus.kv.report.pdf.GenerateTeacherDetailsPdf;
import in.gov.udiseplus.kv.report.pdf.TransManagementPdf;
import in.gov.udiseplus.kv.report.service.TeacherProfileService;
import in.gov.udiseplus.kv.report.utill.CommonMethods;

@Service
public class TeacherProfileServiceImpl implements TeacherProfileService {

	@Autowired
	GenerateTeacherDetailsPdf generatePdfReportService;

	
	@Autowired
	private GenerateTeacherDetailsPdf generateTeacherDetailsPdf;
	
	@Autowired
	private TransManagementPdf transManagementPdf;

	@Value("${report.api.url}")
	private String reportBaseUrl;

	@Override
	public ResponseEntity<?> teacherProfileReport(String token, String username, Integer teacherId) throws IOException {
	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonBody = objectMapper.writeValueAsString(teacherId);
		String urlApi=reportBaseUrl+"/api/teacher/getConfirmedTeacherDetailsV2";
		ResponseEntity<?> apiResp=CommonMethods.getApiResponseByPayLoad(token,username,jsonBody,urlApi,ConfirmedTeacherDetailsResp.class);
		return apiResp;
	}
	
	@Override
	public ResponseEntity<?> genTeacherConfirmation(String token, String username, Integer teacherId) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
	    TeacherConfirmation tc=new TeacherConfirmation();
		tc.setTeacherId(teacherId);
		String jsonBody = objectMapper.writeValueAsString(tc);
	    String urlApi=reportBaseUrl+"/api/teacher/getTeacherConfirmationV2";
		ResponseEntity<?> apiResp=CommonMethods.getApiResponseByPayLoad(token,username,jsonBody,urlApi,TeacherConfirmationResp.class);
		return apiResp;
	}

	
	
	@Override
	public ResponseEntity<?> getTeacherDetailsPdf(ConfirmedTeacherDetailsResp dataObj,TeacherConfirmation dataObj1) throws IOException {
		return generateTeacherDetailsPdf.downloadTeacherDetailsPdf(dataObj,dataObj1);
	}
	
	
	
	


	@Override
	public ResponseEntity<?> genTransManagementPdf(String token, String username, Map<String, Object> payload) throws IOException {
	

		ObjectMapper objectMapper = new ObjectMapper();
		 
		String jsonBody1 = objectMapper.writeValueAsString(payload);
		String urlTcDcPointByTeacherIdAndInityear=reportBaseUrl+"/api/teacher/transfer/getTcDcPointByTeacherIdAndInityear";
		ResponseEntity<?> apiTcDcPointResp=CommonMethods.getApiResponseByPayLoad(token,username,jsonBody1,urlTcDcPointByTeacherIdAndInityear,TcDcPointResp.class);
		TcDcPointResp tcDcPointObj=(TcDcPointResp) apiTcDcPointResp.getBody();
		

		payload.put("teacherId", 38290);
		
		String jsonBody2 = objectMapper.writeValueAsString(payload);
		String urlTransProfileV2=reportBaseUrl+"/api/transprofile/getTransProfileV2";
		ResponseEntity<?> apiTransProfileV2Resp=CommonMethods.getApiResponseByPayLoad(token,username,jsonBody2,urlTransProfileV2,TransProfileV2Resp.class);
		TransProfileV2Resp transProfileV2Obj=(TransProfileV2Resp) apiTransProfileV2Resp.getBody();

		
		return transManagementPdf.downloadTransManagementPdf(payload,transProfileV2Obj,tcDcPointObj);
	}


	
}

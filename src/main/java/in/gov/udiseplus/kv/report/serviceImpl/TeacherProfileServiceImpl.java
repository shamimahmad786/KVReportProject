package in.gov.udiseplus.kv.report.serviceImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.gov.udiseplus.kv.report.bean.ConfirmedTeacherDetailsResp;
import in.gov.udiseplus.kv.report.bean.Response;
import in.gov.udiseplus.kv.report.bean.TeacherConfirmation;
import in.gov.udiseplus.kv.report.bean.TeacherConfirmationResp;
import in.gov.udiseplus.kv.report.pdf.GenerateTeacherDetailsPdf;
import in.gov.udiseplus.kv.report.service.TeacherProfileService;
import in.gov.udiseplus.kv.report.utill.ErrorResponse;
import in.gov.udiseplus.kv.report.utill.StandardErrorMessages;

@Service
public class TeacherProfileServiceImpl implements TeacherProfileService {

	@Autowired
	GenerateTeacherDetailsPdf generatePdfReportService;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private GenerateTeacherDetailsPdf generateTeacherDetailsPdf;

	@Value("${report.api.url}")
	private String reportBaseUrl;

	@Override
	public ResponseEntity<?> teacherProfileReport(String token, String username, Integer teacherId) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		headers.set("username", username);
		headers.set("Authorization", token);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(teacherId);

		HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
		ResponseEntity<Resource> httpResponse = restTemplate.exchange(reportBaseUrl+"/api/teacher/getConfirmedTeacherDetailsV2", HttpMethod.POST, requestEntity, Resource.class);

		Resource responseResource = httpResponse.getBody();
	    HttpHeaders respHeaders = httpResponse.getHeaders();
	    if (responseResource != null) {
	        try {
	            String responseContent = StreamUtils.copyToString(responseResource.getInputStream(), StandardCharsets.UTF_8);
	            ConfirmedTeacherDetailsResp apiResponse = objectMapper.readValue(responseContent, ConfirmedTeacherDetailsResp.class);
	            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).headers(respHeaders).body(apiResponse);
	        } catch (IOException e) {
	            return ResponseEntity.ok(new Response(new ErrorResponse(StandardErrorMessages.EXCEPTION)));
	        }
	    } else {
	        return ResponseEntity.ok(new Response(new ErrorResponse(StandardErrorMessages.EXCEPTION)));
	    }

	}
	
	
	@Override
	public ResponseEntity<?> genTeacherConfirmation(String token, String username, Integer teacherId) throws IOException {
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		headers.set("username", username);
		headers.set("Authorization", token);
		ObjectMapper objectMapper = new ObjectMapper();
		TeacherConfirmation tc=new TeacherConfirmation();
		tc.setTeacherId(teacherId);
		String jsonBody = objectMapper.writeValueAsString(tc);
	
		HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
		ResponseEntity<Resource> httpResponse = restTemplate.exchange(reportBaseUrl+"/api/teacher/getTeacherConfirmationV2", HttpMethod.POST, requestEntity, Resource.class);

		Resource responseResource = httpResponse.getBody();
	    HttpHeaders respHeaders = httpResponse.getHeaders();
	    if (responseResource != null) {
	        try {
	            String responseContent = StreamUtils.copyToString(responseResource.getInputStream(), StandardCharsets.UTF_8);
	            System.out.println(responseContent);
	            TeacherConfirmationResp apiResponse = objectMapper.readValue(responseContent, TeacherConfirmationResp.class);
	            System.out.println("teacherId--->"+apiResponse.getResponse().getTeacherId());
	            
	            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).headers(respHeaders).body(apiResponse.getResponse());
	        } catch (IOException e) {
	            return ResponseEntity.ok(new Response(new ErrorResponse(StandardErrorMessages.EXCEPTION)));
	        }
	    } else {
	        return ResponseEntity.ok(new Response(new ErrorResponse(StandardErrorMessages.EXCEPTION)));
	    }
	
		
	}

	@Override
	public ResponseEntity<?> getTeacherDetailsPdf(ConfirmedTeacherDetailsResp dataObj,TeacherConfirmation dataObj1) throws IOException {
		return generateTeacherDetailsPdf.downloadTeacherDetailsPdf(dataObj,dataObj1);
	}
	
	
	

	@Override
	public ResponseEntity<?> genTransferOrderPdf() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}

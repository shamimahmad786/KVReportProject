package in.gov.udiseplus.kv.report.utill;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import in.gov.udiseplus.kv.report.bean.Response;


public class CommonMethods {

	private static RestTemplate restTemplate = new RestTemplate();
	
	@SuppressWarnings("unchecked")
	public static <T> ResponseEntity<?> getApiResponseByJsonPayLoad(String token, String username, String jsonBody, String url, Class<T> responseType) throws IOException {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.TEXT_PLAIN);
	    headers.set("username", username);
	    headers.set("Authorization", token);

	    HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
	    try {
	        ResponseEntity<?> httpResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
	        T responseBody = (T) httpResponse.getBody();
	        HttpHeaders respHeaders = httpResponse.getHeaders();

	        if (responseBody != null) {
	            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).headers(respHeaders).body(responseBody);
	        } else {
	            return (ResponseEntity<?>) ResponseEntity.ok(new Response(new ErrorResponse(StandardErrorMessages.EXCEPTION)));
	        }
	    } catch (HttpServerErrorException ex) {
	        System.err.println("An error occurred: " + ex.getStatusCode() + ", " + ex.getResponseBodyAsString());
	        return (ResponseEntity<?>) ResponseEntity.status(ex.getStatusCode()).body(new Response(new ErrorResponse(StandardErrorMessages.EXCEPTION)));
	    }
	}

}

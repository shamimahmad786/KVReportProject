package in.gov.udiseplus.kv.report.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherConfirmationResp {
	@JsonProperty("status")
	private int status;
	
	@JsonProperty("message")
    private String message;
	
	@JsonProperty("response")
    private TeacherConfirmation response;
	 
	@JsonProperty("responseCode")
    private String responseCode;
}

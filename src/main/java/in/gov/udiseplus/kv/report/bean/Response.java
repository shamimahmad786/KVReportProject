package in.gov.udiseplus.kv.report.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import in.gov.udiseplus.kv.report.utill.Constants;
import in.gov.udiseplus.kv.report.utill.ErrorResponse;
import in.gov.udiseplus.kv.report.utill.Messages;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {
	
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT= new SimpleDateFormat(Constants.META_DATA_DATE_FORMAT);
	
	@JsonInclude(Include.NON_NULL)
	private String url;
	
	private Integer httpStatus;

	private boolean status;

	@JsonInclude(Include.NON_NULL)
	private String message;

	@JsonInclude(Include.NON_NULL)
	private Object data;

	@JsonInclude(Include.NON_NULL)
	private Object error;
	
	@JsonInclude(Include.NON_NULL)
	private String dataLastModifiedOn;


	public Response(Messages messages, Object data) {
		this.httpStatus = HttpStatus.OK.value();
		this.status = messages.isStatus();
		this.message = messages.getMessage();
		this.data = data;
	}
	
	public Response(Messages messages, Object data, Date modifiedDate) {
		this.httpStatus = HttpStatus.OK.value();
		this.status = messages.isStatus();
		this.message = messages.getMessage();
		this.data = data;
		this.dataLastModifiedOn = modifiedDate == null ? "" : SIMPLE_DATE_FORMAT.format(modifiedDate);
	}
	
	public Response(ErrorResponse error) {
		this.httpStatus = HttpStatus.OK.value();
		this.status = false;
		this.error = error;
	}
	
	public Response(ErrorResponse error, Integer statusCode) {
		this.httpStatus = statusCode;
		this.status = false;
		this.error = error;
	}
	
	
}

package in.gov.udiseplus.kv.report.utill;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {


	private String message;
	
//	@JsonInclude(Include.NON_NULL)
	private String type;
	
	@JsonInclude(Include.NON_NULL)
	private Object data;

	@JsonInclude(Include.NON_NULL)
	private String errorId;
	
	public ErrorResponse(Messages message) {
		this.message = message.getMessage();
		this.type = message.getType();
	}
	
	public ErrorResponse(StandardErrorMessages message) {
		this.message = message.getMessage();
		this.type = message.getType();
	}

	public ErrorResponse(StandardErrorMessages message, String data) {
		this.message = message.getMessage();
		this.type = message.getType();
		this.errorId = data;
	}
	
	public ErrorResponse(Messages message, Object data) {
		this.message = message.getMessage();
		this.type = message.getType();
		this.data = data;
	}

	public ErrorResponse(String message, String type, Object data) {
		this.message = message;
		this.type = type;
		this.data = data;
	}

}

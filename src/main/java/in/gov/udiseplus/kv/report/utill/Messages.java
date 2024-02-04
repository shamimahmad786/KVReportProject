package in.gov.udiseplus.kv.report.utill;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Messages {
	
	SUCCESS("successful", true, "Successful"), 
	EXCEPTION("exception", false, "Some Error Occurred"),
	ERROR("internal_server_error", false, "Something Gone wrong"),
	DATA_NOT_SAVED("data_not_saved", false, "Some error occurred while saving data. Please try after sometime or contact ADMIN if error persists"),
	DATA_NOT_UPDATED("data_not_updated", false, "Some error occurred while updating data. Please try after sometime or contact ADMIN if error persists");

	private String type;
	private boolean status;
	private String message;


}

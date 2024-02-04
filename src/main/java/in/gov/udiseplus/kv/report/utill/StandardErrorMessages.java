package in.gov.udiseplus.kv.report.utill;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum StandardErrorMessages {

	SUCCESS("successful", true, "Successful"),
	EXCEPTION("exception", false, "Some Error Occurred"),
	ERROR("internal_server_error", false, "Something Gone wrong"),

	CONFIG_ERROR("config_error", false, "Something Gone wrong (Config Error)"),

	// httpexception messages
	BAD_REQUEST_400("BAD_REQUEST", false, "Invalid parameters found in the request"),
	UNAUTHORISED_401("UNAUTHORISED", false, "Unauthorised request or the session has expired. Please LOGIN again"),
	NOT_FOUND_404("NOT_FOUND", false, "ERROR 404: Not Found"),
	INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", false, "Some error occurred on the server side. Please try after some time."),
	REQUEST_TIMED_OUT("REQUEST_TIMED_OUT", false, "Could not establish connection. Please try after some time."),

	EX_400_BAD_REQUEST("bad_request", false, "BAD REQUEST"),

	DATA_ENTRY_NOT_ACTIVE_STATE("data_entry_not_active_state", false, "Data Entry Not Active for your state"),
//	DATA_ENTRY_NOT_ACTIVE_STATE("data_entry_not_active_state", false, "Data Entry Not Active for your state"),
	STATE_NOT_BOARDED_SDMS("state_not_boarded_sdms", false, "Your state is not boarded on SDMS platform"),
	STATE_NOT_BOARDED_UDISE("state_not_boarded_udise", false, "Your state is not boarded on UDISE+ platform"),

	SCHOOL_NOT_BOARDED_SDMS("school_not_boarded_sdms", false, "Your school is not boarded on SDMS platform"),
	SCHOOL_NOT_OPERATIONAL("school_not_operational", false, "Your school is not operational"),

	REPORT_GEN_ERROR_500("report_error_500", false, "Some error occurred in generating report. Please try after sometime.");

	// 961
	private String type;
	private boolean status;
	private String message;

}

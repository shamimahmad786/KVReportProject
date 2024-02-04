package in.gov.udiseplus.kv.report.utill;
import lombok.Data;

@Data
public class Constants {
	
	
//	public static final String USER_INFO = "userInfo";

	public static final String FAILED_ATTEMPTS = "failedAttempts";
	public static final String RANDOM_TOKEN = "rndToken";
	public static final String CAPTCHA = "captcha";

	public static final String CAPTCHA_EDIT = "captchaEdit";
	
	public static final String SESSION_INIT_TIME = "session_init_time";
	public static final String CURRENT_LOGIN_TIME = "current_login_time";
	
	public static final int LOGIN_RATE_LIMIT_ATTEMPTS = 10;
	public static final int LOGIN_RATE_LIMIT_TIME = 1000; //in milliseconds
	
	public static final String USER_INFO = "UserInfo";
	
	public static final String ACCESS_TOKEN = "access_token";
	
	public static final String SESSION_KEY = "skey";
	
	public static final String AUTHORIZED_CLIENT = "authorized_client";
	
	public static final String AUTHENTICATION_RESPONSE = "auth_response";
	
	public static final int ACTIVE_ID = 1;
	
//	public static final int OVERALL_STATUS_INIT = 5;
	
	public static final int OVERALL_STATUS_EDIT = 5;
	
	public static final int OVERALL_STATUS_COMPLETE = 6;
	
	public static final int FORM_STATUS_PROFILE = 1;
	
	public static final int FORM_STATUS_ENR = 2;
	
	public static final int FORM_STATUS_FACILITY = 3;
	
	public static final int FORM_STATUS_VOCATIONAL = 4;
	
	public static final String IS_LOGGED_IN = "isLoggedIn";
	
	public static final String AADHAAR_DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";

	public static final String YEAR_FORMAT = "yyyy";
	
	public static final String INIT_DATE = "01/01/1943";

	public static final String SESSION_INIT_DATE = "01/01/2009";
	
	public static final String META_DATA_DATE_FORMAT = "dd/MM/yyyy hh:mm:ss aaa";
	
	public static final String STATIC_AADHAAR_NUMBER = "999999999999";

	public static final String STATIC_REFERENCE_KEY = "2127924366785";
	
	public static final boolean IS_SAVE = true;
	

}

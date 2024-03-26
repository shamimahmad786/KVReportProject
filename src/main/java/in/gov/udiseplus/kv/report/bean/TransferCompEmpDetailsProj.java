package in.gov.udiseplus.kv.report.bean;

import java.util.Date;

public interface TransferCompEmpDetailsProj {

	public String getEmp_code();

	public String getEmpNameGender();

	public String getGender();

	public String getPostname();

	public String getSubject();

	public Date getDob();

	public Date getDor();

	public String getTransferred_under_cat();

	public String getCurrent_station();

	public String getCurrent_kv();

	public String getAlloted_station();

	public String getAlloted_kv();

	public Integer getTot_tc();

	public Integer getTot_tc2();

	public Integer getTot_dc();

	public Date getDoj_in_present_stn_irrespective_of_cadre();

	public String getChoice_stn1();

	public String getChoice_stn2();

	public String getChoice_stn3();

	public String getChoice_stn4();

	public String getChoice_stn5();

	public Integer getStation_code_1();

	public Integer getStation_code_2();

	public Integer getStation_code_3();

	public Integer getStation_code_4();

	public Integer getStation_code_5();
	
	public Integer getPost_id();
    public Integer getSubject_id();
	
}
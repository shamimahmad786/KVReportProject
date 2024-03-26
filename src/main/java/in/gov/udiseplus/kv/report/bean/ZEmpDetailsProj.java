package in.gov.udiseplus.kv.report.bean;
public interface ZEmpDetailsProj {
	String getEmp_code();
	String getEmp_name();
	String getPost_name();
	String getSubject_name();
	String getRegion_code();
	String getRegion_name_present();

	Integer getPresent_station_code();
	String getStation_name_present();
	Integer getPresent_kv_code();
	String getKv_name_present();
	Integer getShift();
	String getRegion_code_alloted();
	String getRegion_name_alloted();
	Integer getAllot_stn_code();
	String getStation_name_alloted();
	Integer getAllot_kv_code();
	String getKv_name_alloted();
	Integer getAllot_shift();

}
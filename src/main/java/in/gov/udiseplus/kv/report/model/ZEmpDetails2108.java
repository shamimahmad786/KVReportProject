package in.gov.udiseplus.kv.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "z_emp_details_2108")
public class ZEmpDetails2108 {

	@Id
	@Column(name = "teacher_id")
	private Long teacherId;

	@Column(name = "emp_code")
	private String empCode;

	@Column(name = "emp_name")
	private String empName;

	@Column(name = "gender")
	private Integer gender;

	@Column(name = "dob")
	private Date dob;

	@Column(name = "post_id")
	private Integer postId;

	@Column(name = "subject_id")
	private Integer subjectId;

	@Column(name = "region_code")
	private Integer regionCode;

	@Column(name = "present_station_code")
	private Integer presentStationCode;

	@Column(name = "present_kv_code")
	private Integer presentKvCode;

	@Column(name = "present_kv_master_code")
	private String presentKvMasterCode;

	@Column(name = "shift")
	private Integer shift;

	@Column(name = "doj_in_present_stn_irrespective_of_cadre")
	private Date dojInPresentStnIrrespectiveOfCadre;

	@Column(name = "is_ner_recruited")
	private Integer isNerRecruited;

	@Column(name = "isjcm_rjcm")
	private Integer isjcmRjcm;

	@Column(name = "is_pwd")
	private Integer isPwd;

	@Column(name = "is_hard_served")
	private Integer isHardServed;

	@Column(name = "is_currently_in_hard")
	private Integer isCurrentlyInHard;

	@Column(name = "station_code_1")
	private Integer stationCode1;

	@Column(name = "station_code_2")
	private Integer stationCode2;

	@Column(name = "station_code_3")
	private Integer stationCode3;

	@Column(name = "station_code_4")
	private Integer stationCode4;

	@Column(name = "station_code_5")
	private Integer stationCode5;

	@Column(name = "tot_tc")
	private Integer totTc;

	@Column(name = "tot_tc2")
	private Integer totTc2;

	@Column(name = "tot_dc")
	private Integer totDc;

	@Column(name = "transfer_applied_for")
	private Integer transferAppliedFor;

	@Column(name = "dc_applied_for")
	private Integer dcAppliedFor;

	@Column(name = "is_trasnfer_applied")
	private Integer isTransferApplied;

	@Column(name = "allot_stn_code")
	private Integer allotStnCode;

	@Column(name = "allot_kv_code")
	private Integer allotKvCode;

	@Column(name = "allot_shift")
	private Integer allotShift;

	@Column(name = "transferred_under_cat")
	private Integer transferredUnderCat;

	@Column(name = "emp_transfer_status")
	private Integer empTransferStatus;

	@Column(name = "is_displaced")
	private Integer isDisplaced;

	@Column(name = "elgible_yn")
	private Integer elgibleYn;

	@Column(name = "is_ner")
	private Integer isNer;

	@Column(name = "apply_transfer_yn")
	private Integer applyTransferYn;

	@Column(name = "ground_level")
	private Integer groundLevel;

	@Column(name = "print_order")
	private Integer printOrder;

	@Column(name = "kv_name_present")
	private String kvNamePresent;

	@Column(name = "kv_name_alloted")
	private String kvNameAlloted;

	@Column(name = "station_name1")
	private String stationName1;

	@Column(name = "station_name2")
	private String stationName2;

	@Column(name = "station_name3")
	private String stationName3;

	@Column(name = "station_name4")
	private String stationName4;

	@Column(name = "station_name5")
	private String stationName5;

	@Column(name = "region_name_present")
	private String regionNamePresent;

	@Column(name = "region_code_alloted")
	private Integer regionCodeAlloted;

	@Column(name = "region_name_alloted")
	private String regionNameAlloted;

	@Column(name = "station_name_present")
	private String stationNamePresent;

	@Column(name = "station_name_alloted")
	private String stationNameAlloted;

	@Column(name = "post_name")
	private String postName;

	@Column(name = "subject_name")
	private String subjectName;

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(Integer regionCode) {
		this.regionCode = regionCode;
	}

	public Integer getPresentStationCode() {
		return presentStationCode;
	}

	public void setPresentStationCode(Integer presentStationCode) {
		this.presentStationCode = presentStationCode;
	}

	public Integer getPresentKvCode() {
		return presentKvCode;
	}

	public void setPresentKvCode(Integer presentKvCode) {
		this.presentKvCode = presentKvCode;
	}

	public String getPresentKvMasterCode() {
		return presentKvMasterCode;
	}

	public void setPresentKvMasterCode(String presentKvMasterCode) {
		this.presentKvMasterCode = presentKvMasterCode;
	}

	public Integer getShift() {
		return shift;
	}

	public void setShift(Integer shift) {
		this.shift = shift;
	}

	public Date getDojInPresentStnIrrespectiveOfCadre() {
		return dojInPresentStnIrrespectiveOfCadre;
	}

	public void setDojInPresentStnIrrespectiveOfCadre(Date dojInPresentStnIrrespectiveOfCadre) {
		this.dojInPresentStnIrrespectiveOfCadre = dojInPresentStnIrrespectiveOfCadre;
	}

	public Integer getIsNerRecruited() {
		return isNerRecruited;
	}

	public void setIsNerRecruited(Integer isNerRecruited) {
		this.isNerRecruited = isNerRecruited;
	}

	public Integer getIsjcmRjcm() {
		return isjcmRjcm;
	}

	public void setIsjcmRjcm(Integer isjcmRjcm) {
		this.isjcmRjcm = isjcmRjcm;
	}

	public Integer getIsPwd() {
		return isPwd;
	}

	public void setIsPwd(Integer isPwd) {
		this.isPwd = isPwd;
	}

	public Integer getIsHardServed() {
		return isHardServed;
	}

	public void setIsHardServed(Integer isHardServed) {
		this.isHardServed = isHardServed;
	}

	public Integer getIsCurrentlyInHard() {
		return isCurrentlyInHard;
	}

	public void setIsCurrentlyInHard(Integer isCurrentlyInHard) {
		this.isCurrentlyInHard = isCurrentlyInHard;
	}

	public Integer getStationCode1() {
		return stationCode1;
	}

	public void setStationCode1(Integer stationCode1) {
		this.stationCode1 = stationCode1;
	}

	public Integer getStationCode2() {
		return stationCode2;
	}

	public void setStationCode2(Integer stationCode2) {
		this.stationCode2 = stationCode2;
	}

	public Integer getStationCode3() {
		return stationCode3;
	}

	public void setStationCode3(Integer stationCode3) {
		this.stationCode3 = stationCode3;
	}

	public Integer getStationCode4() {
		return stationCode4;
	}

	public void setStationCode4(Integer stationCode4) {
		this.stationCode4 = stationCode4;
	}

	public Integer getStationCode5() {
		return stationCode5;
	}

	public void setStationCode5(Integer stationCode5) {
		this.stationCode5 = stationCode5;
	}

	public Integer getTotTc() {
		return totTc;
	}

	public void setTotTc(Integer totTc) {
		this.totTc = totTc;
	}

	public Integer getTotTc2() {
		return totTc2;
	}

	public void setTotTc2(Integer totTc2) {
		this.totTc2 = totTc2;
	}

	public Integer getTotDc() {
		return totDc;
	}

	public void setTotDc(Integer totDc) {
		this.totDc = totDc;
	}

	public Integer getTransferAppliedFor() {
		return transferAppliedFor;
	}

	public void setTransferAppliedFor(Integer transferAppliedFor) {
		this.transferAppliedFor = transferAppliedFor;
	}

	public Integer getDcAppliedFor() {
		return dcAppliedFor;
	}

	public void setDcAppliedFor(Integer dcAppliedFor) {
		this.dcAppliedFor = dcAppliedFor;
	}

	public Integer getIsTransferApplied() {
		return isTransferApplied;
	}

	public void setIsTransferApplied(Integer isTransferApplied) {
		this.isTransferApplied = isTransferApplied;
	}

	public Integer getAllotStnCode() {
		return allotStnCode;
	}

	public void setAllotStnCode(Integer allotStnCode) {
		this.allotStnCode = allotStnCode;
	}

	public Integer getAllotKvCode() {
		return allotKvCode;
	}

	public void setAllotKvCode(Integer allotKvCode) {
		this.allotKvCode = allotKvCode;
	}

	public Integer getAllotShift() {
		return allotShift;
	}

	public void setAllotShift(Integer allotShift) {
		this.allotShift = allotShift;
	}

	public Integer getTransferredUnderCat() {
		return transferredUnderCat;
	}

	public void setTransferredUnderCat(Integer transferredUnderCat) {
		this.transferredUnderCat = transferredUnderCat;
	}

	public Integer getEmpTransferStatus() {
		return empTransferStatus;
	}

	public void setEmpTransferStatus(Integer empTransferStatus) {
		this.empTransferStatus = empTransferStatus;
	}

	public Integer getIsDisplaced() {
		return isDisplaced;
	}

	public void setIsDisplaced(Integer isDisplaced) {
		this.isDisplaced = isDisplaced;
	}

	public Integer getElgibleYn() {
		return elgibleYn;
	}

	public void setElgibleYn(Integer elgibleYn) {
		this.elgibleYn = elgibleYn;
	}

	public Integer getIsNer() {
		return isNer;
	}

	public void setIsNer(Integer isNer) {
		this.isNer = isNer;
	}

	public Integer getApplyTransferYn() {
		return applyTransferYn;
	}

	public void setApplyTransferYn(Integer applyTransferYn) {
		this.applyTransferYn = applyTransferYn;
	}

	public Integer getGroundLevel() {
		return groundLevel;
	}

	public void setGroundLevel(Integer groundLevel) {
		this.groundLevel = groundLevel;
	}

	public Integer getPrintOrder() {
		return printOrder;
	}

	public void setPrintOrder(Integer printOrder) {
		this.printOrder = printOrder;
	}

	public String getKvNamePresent() {
		return kvNamePresent;
	}

	public void setKvNamePresent(String kvNamePresent) {
		this.kvNamePresent = kvNamePresent;
	}

	public String getKvNameAlloted() {
		return kvNameAlloted;
	}

	public void setKvNameAlloted(String kvNameAlloted) {
		this.kvNameAlloted = kvNameAlloted;
	}

	public String getStationName1() {
		return stationName1;
	}

	public void setStationName1(String stationName1) {
		this.stationName1 = stationName1;
	}

	public String getStationName2() {
		return stationName2;
	}

	public void setStationName2(String stationName2) {
		this.stationName2 = stationName2;
	}

	public String getStationName3() {
		return stationName3;
	}

	public void setStationName3(String stationName3) {
		this.stationName3 = stationName3;
	}

	public String getStationName4() {
		return stationName4;
	}

	public void setStationName4(String stationName4) {
		this.stationName4 = stationName4;
	}

	public String getStationName5() {
		return stationName5;
	}

	public void setStationName5(String stationName5) {
		this.stationName5 = stationName5;
	}

	public String getRegionNamePresent() {
		return regionNamePresent;
	}

	public void setRegionNamePresent(String regionNamePresent) {
		this.regionNamePresent = regionNamePresent;
	}

	public Integer getRegionCodeAlloted() {
		return regionCodeAlloted;
	}

	public void setRegionCodeAlloted(Integer regionCodeAlloted) {
		this.regionCodeAlloted = regionCodeAlloted;
	}

	public String getRegionNameAlloted() {
		return regionNameAlloted;
	}

	public void setRegionNameAlloted(String regionNameAlloted) {
		this.regionNameAlloted = regionNameAlloted;
	}

	public String getStationNamePresent() {
		return stationNamePresent;
	}

	public void setStationNamePresent(String stationNamePresent) {
		this.stationNamePresent = stationNamePresent;
	}

	public String getStationNameAlloted() {
		return stationNameAlloted;
	}

	public void setStationNameAlloted(String stationNameAlloted) {
		this.stationNameAlloted = stationNameAlloted;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

}

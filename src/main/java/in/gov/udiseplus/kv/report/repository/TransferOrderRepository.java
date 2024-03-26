package in.gov.udiseplus.kv.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.gov.udiseplus.kv.report.bean.TransferCompChoiceProj;
import in.gov.udiseplus.kv.report.bean.TransferCompEmpDetailsProj;
import in.gov.udiseplus.kv.report.bean.ZEmpDetailsProj;
import in.gov.udiseplus.kv.report.model.ZEmpDetails2108;

@Repository
public interface TransferOrderRepository extends JpaRepository<ZEmpDetails2108, Long> {

	@Query(nativeQuery = true,value = "select emp_code,emp_name,post_name,subject_name, "
			+ "region_code,region_name_present,present_station_code,station_name_present,present_kv_code,kv_name_present,shift, "
			+ "region_code_alloted,region_name_alloted,allot_stn_code,station_name_alloted,allot_kv_code,kv_name_alloted,allot_shift "
			+ "from z_emp_details_2108 where (CASE WHEN print_order = -1 THEN Allot_stn_code <> -1 ELSE 1=1 END) "
			+ "and print_order=:printOrder and post_id=:postId and subject_id=:subjectId ")
	
	List<ZEmpDetailsProj> findEmpDetailsByPOrderAndPostIdAndSubjectId(@Param("printOrder") Integer printOrder,@Param("postId") Integer postId,@Param("subjectId") Integer subjectId);

	
	
	@Query(nativeQuery = true,value = "select emp_code,post_id,subject_id,emp_name||' / '||(case when gender=1 then 'M'  when gender=2 then 'F' end) as EmpNameGender,\r\n"
			+ "post_id ||' - '|| post_name as PostName,subject_id ||' - '|| subject_name as Subject,dob,\r\n"
			+ "(dob + interval '60 years')\\:\\:date as DoR,\r\n"
			+ "(case when transferred_under_cat=40 then 'PWD' when transferred_under_cat=35 then 'DFP/MDG'\r\n"
			+ "when transferred_under_cat=30 then 'Hard Station' when transferred_under_cat=25 then 'LTR'\r\n"
			+ "when transferred_under_cat=20 then 'Single Parent' when transferred_under_cat=15 then 'KVS Spouse' \r\n"
			+ "when transferred_under_cat=12 then 'CG Spouse' when transferred_under_cat=10 then 'State Spouse'\r\n"
			+ "when transferred_under_cat=8 then 'Women' when transferred_under_cat=6 then 'RJCM/NJCM'\r\n"
			+ "when transferred_under_cat=98 then 'Below 40 Transfer' when transferred_under_cat=99 then 'Displacement without Choice'\r\n"
			+ "when transferred_under_cat=0 then 'Displacement with Choice' else 'NA' end) as transferred_under_cat,\r\n"
			+ "present_station_code||' - '||station_name_present as Current_Station,present_kv_code||' - '||kv_name_present as Current_KV,\r\n"
			+ "coalesce(allot_stn_code||' - '||station_name_alloted,'NA') as Alloted_Station,coalesce(allot_kv_code||' - '||kv_name_alloted,'NA') as Alloted_KV,\r\n"
			+ "tot_tc,tot_tc2,tot_dc,doj_in_present_stn_irrespective_of_cadre,\r\n"
			+ "station_code_1||' - '||station_name1 as Choice_Stn1 ,station_code_2||' - '||station_name2 as Choice_Stn2,\r\n"
			+ "station_code_3||' - '||station_name3 as Choice_Stn3,station_code_4||' - '||station_name4 as Choice_Stn4,\r\n"
			+ "station_code_5||' - '||station_name5 as Choice_Stn5,station_code_1 ,station_code_2 ,station_code_3 ,station_code_4 ,station_code_5\r\n"
			+ "from z_emp_details_2108 where emp_code =:empCode ")
	TransferCompEmpDetailsProj findOemEmpDetailsBy(@Param("empCode") String empCode);

	
	

	

	 
	 @Query(nativeQuery = true,value = "select emp_code,emp_name||' / '||(case when gender=1 then 'M'  when gender=2 then 'F' end) as EmpNameGender,dob,(case when transferred_under_cat=40 then 'PWD' when transferred_under_cat=35 then 'DFP/MDG'\r\n"
	 		+ "when transferred_under_cat=30 then 'Hard Station' when transferred_under_cat=25 then 'LTR'\r\n"
	 		+ "when transferred_under_cat=20 then 'Single Parent' when transferred_under_cat=15 then 'KVS Spouse' \r\n"
	 		+ "when transferred_under_cat=12 then 'CG Spouse' when transferred_under_cat=10 then 'State Spouse'\r\n"
	 		+ "when transferred_under_cat=8 then 'Women' when transferred_under_cat=6 then 'RJCM/NJCM'\r\n"
	 		+ "when transferred_under_cat=98 then 'Below 40 Transfer' when transferred_under_cat=99 then 'Displacement without Choice'\r\n"
	 		+ "when transferred_under_cat=0 then 'Displacement with Choice' else 'NA' end) as transferred_under_cat,tot_tc,tot_tc2,tot_dc,\r\n"
	 		+ "case when station_code_1=allot_stn_code then 1 when station_code_2=allot_stn_code then 2 \r\n"
	 		+ "when station_code_3=allot_stn_code then 3 when station_code_4=allot_stn_code then 4 else 5 end as choice_stn \r\n"
	 		+ "from z_emp_details_2108 \r\n"
	 		+ "where emp_code !=:empCode and post_id =:postId and subject_id =:subjectId and allot_stn_code =:stationCode order by tot_tc desc, gender desc, dob asc")
	    List<TransferCompChoiceProj> findOemEmpChoice(@Param("empCode") String empCode, @Param("postId") Integer postId,
	    		@Param("subjectId") Integer subjectId, @Param("stationCode") Integer stationCode);

	
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 /*@Query(value = "SELECT count(survey_id) FROM survey_map_question WHERE survey_id=:surveyId AND is_mandatory=0", nativeQuery = true)
	int isMapped(@Param("surveyId") String surveyId);
	
	@Query(value = "SELECT * FROM survey_master WHERE id in (:surveyIdList)", nativeQuery = true)
	List<ZEmpDetails2108> findAllSurveyById(@Param("surveyIdList") List<Integer> surveyIdList);
	
	@Query(value = "SELECT deo_level_id FROM survey_master WHERE id= :id", nativeQuery = true)
	int findSurveyLevelId(@Param("id") int id);
	
	@Query(value = "SELECT level_id from user_details WHERE u_id= :u_id", nativeQuery = true)
	int findUserLevelId(@Param("u_id") int u_id);
	
	@Query(value = "SELECT id FROM level_master WHERE parent_level_id IN :parentLevelIds", nativeQuery = true)
	List<Integer> findUserChildLevelId(List<Integer> parentLevelIds);
	
	@Query(value = "SELECT id, level_name from level_master WHERE id IN :levelIds", nativeQuery = true)
	List<Object[]> findLevelNameByLevelId(List<Integer> levelIds);
	
	@Query(value = "Call refresh_website_tables_data(:year_code)", nativeQuery = true)
	String moveWebsiteTables(@PathVariable("year_code") Integer year_code);*/
}

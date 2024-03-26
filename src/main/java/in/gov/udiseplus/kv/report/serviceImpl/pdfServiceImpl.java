package in.gov.udiseplus.kv.report.serviceImpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.aspectj.apache.bcel.classfile.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import in.gov.udiseplus.kv.report.bean.TransferCompChoiceProj;
import in.gov.udiseplus.kv.report.bean.TransferCompEmpDetailsProj;
import in.gov.udiseplus.kv.report.bean.ZEmpDetailsProj;
import in.gov.udiseplus.kv.report.pdf.TransferComparisonReptPdf;
import in.gov.udiseplus.kv.report.pdf.TransferOrderPdf;
import in.gov.udiseplus.kv.report.repository.TransferOrderRepository;
import in.gov.udiseplus.kv.report.service.PDFService;

@Service
public class pdfServiceImpl implements PDFService{

	@Autowired
	TransferOrderRepository transferOrderRepository;
	
	@Autowired
	TransferOrderPdf transferOrderPdf;
	@Autowired
	TransferComparisonReptPdf transferComparisonReptPdf;
	
	
	
	
	@Override
	public ResponseEntity<?> findEmpDetailsByPOrderAndPostIdAndSubjectId(Integer orderType, Integer postId,
			Integer subjectId) throws IOException {
		String[] orderGroundMinusOne = { "F.No. 11-E-II046/2/2023-ESTT-II/ Part1-A-2-II-(A)-a)-Administrative grounds",
				"केविसं की स्थानांतरण नीति 2023 के Part - 1 – A के पैरा 2 (II) (A) a) के अंतगर्त स्टेशनों में निर्धारित कायर्काल पूणर् करने के उपरांत अथवा DFP/MDG/LTR के अंतर्गत आने वाले मामलों में उप्लब्ध रिक्त पदों पर कर्मचारियों का स्थानांतरण करने का प्रावधान है। तदनुसार, निम्नलिखित कर्मचारियों का स्थानांतरण प्रशासनिक आधार पर किया जाता है। निम्नलिखित कर्मचारी केविसं केनियमानुसार स्थानांतरण सम्बन्धी लाभ के हक़दार होंगे। बशर्ते उन्होंने निर्धारित कार्यकाल पूर्ण कर लिया हो।",
				"In terms of para 2 (II) (A) a) of Part - 1 – A of the KVS Transfer Policy 2023 which inter-alia provides transfer of employees who have completed tenure or cases covered under DFP/MDG/ LTR to Stations having vacancies. Accordingly, transfer of the following employees is ordered on administrative grounds. They are entitled for all Transfer benefits as per KVS rules subject to completion of prescribed tenure",
				"Request_Transfer" };

		String[] orderGroundOne = { "F.No. 11-E-II046/2/2023-ESTT-II/ Part1-A-2-II-(A)-b)(iii)-Administrative grounds",
				"केविसं की स्थानांतरण नीति 2023 के Part - 1 – A के पैरा 2 (II) (A) b) (iii) के अनुसार PwD/MDG/DFP /LTR के अंतर्गत आने वाले कर्मचारियों को समायोजित करने हेतु विस्थापन आधार पर स्थानांतरण करने का प्रावधान है। तदनुसार, निम्नलिखित कर्मचारियों का स्थानांतरण प्रशासनिक आधार पर किया जाता है।  निम्नलिखित कर्मचारी केविसं केनियमानुसार स्थानांतरण सम्बन्धी लाभ के हक़दार होंगे। बशर्ते उन्होंने निर्धारित कार्यकाल पूर्ण कर लिया हो।",
				"In terms of para 2 (II) (A) b) (iii) of Part - 1 – A of the KVS Transfer Policy 2023 which inter-alia provides transfer of employees on Displacement to accommodate the employees falling under PwD/MDG/DFP/LTR. Accordingly, transfer of the following employees is ordered on administrative grounds. They are entitled for all Transfer benefits as per KVS rules subject to completion of prescribed tenure ",
				"Displacement_Of_PWD_DFP_MDG" };

		String[] orderGroundTwo = {
				"F.No. 11-E-II046/2/2023-ESTT-II/ Part1-A-2-II-(A)-b)(i) & (ii)-Administrative grounds",
				"केविसं की स्थानांतरण नीति 2023 के Part - 1 – A के पैरा 2 (II) (A) b) (i) & (ii) के अंतर्गत दुर्गम/पूर्वोत्तर स्टेशन में अपना कार्यकाल पूर्ण करने वाले कर्मचारियों को समायोजित करने हेतु विस्थापन आधार पर स्थानांतरण करने का प्रावधान है। तदनुसार,निम्नलिखित कर्मचारियों का स्थानांतरण प्रशासनिक आधार पर किया जाता है। निम्नलिखित कर्मचारी केविसं केनियमानुसार स्थानांतरण सम्बन्धी लाभ के हक़दार होंग।",
				"In terms of para 2 (II) (A) b) (i) & (ii) of Part - 1 – A of the KVS Transfer Policy 2023 which inter-alia provides transfer of employees on Displacement to accommodate the employees who have completed their tenures in Hard/NER station. Accordingly, transfer of the following employees is ordered on administrative grounds. They are entitled for all Transfer benefits as per KVS rules",
				"Displacement_of_HardStation" };

		String[] orderGroundThree = {
				"F.No. 11-E-II046/2/2023-ESTT-II/Part1-A-2-I-(B)-a)-Organizational Requirement (under-40)",
				"केविसं की स्थानांतरण नीति 2023 के Part - 1 – A के पैरा 2 (I) (B) a) के अंतर्गत दुर्गम/पूर्वोत्तर क्षेत्र मेंरिक्त पदों को भरने केलिए कर्मचारियों को स्थानांतरित करने का प्रावधान है, तदनुसार ससंगठनात्मक आवश्यकताओं मेंनिम्नलिखित कर्मचारियों का स्थानांतरण किया जाता है। निम्नलिखित कर्मचारी केविसं केनियमानुसार स्थानांतरण सम्बन्धी लाभ के हक़दार होंग ।",
				"In terms of para 2 ( I ) (B) a) of Part - 1 – A of the KVS Transfer Policy 2023 which inter-alia provides transfer of employees to fill up vacancies in Hard/NER Station. Accordingly, the transfer of the following employees is ordered in Organisational requirements. They are entitled for all Transfer benefits as per KVS rules",
				"Under40_NER_Hard" };
		String[] orderGroundFour = { "F.No. 11-E-II046/2/2023-ESTT-II/ Part1-A-2-I-(B)-b)-Organizational Requirement",
				"केविसं की स्थानांतरण नीति 2023 के Part - 1 – A के पैरा 2 (I) (B) b) के अंतर्गत दुर्गम/पूर्वोत्तर स्टेशनों मेंरिक्त पदों को भरने केलिए इच्छुक महिला कर्मचारियों को प्रशासनिक आधार पर स्थानांतरित करने का प्रावधान है । तदनुसार ससंगठनात्मक आवश्यकताओं मेंनिम्नलिखित कर्मचारियों का स्थानांतरण किया जाता है। निम्नलिखित कर्मचारी केविसं केनियमानुसार स्थानांतरण सम्बन्धी लाभ के हक़दार होंगे।",
				"In terms of para 2 (I) (B) b) of Part - 1 – A of the KVS Transfer Policy 2023 which inter-alia provides administrative transfer of willing female employees to fill up vacancies in Hard/ NER Station. Accordingly, transfer of the following employees is ordered in organizational requirement. They are entitled for all Transfer benefits as per KVS rules.",
				"Female_NER_Hard" };

		List<String[]> orderDataList = new ArrayList<>();
		orderDataList.add(orderGroundMinusOne);
		orderDataList.add(orderGroundOne);
		orderDataList.add(orderGroundTwo);
		orderDataList.add(orderGroundThree);
		orderDataList.add(orderGroundFour);

		List<ZEmpDetailsProj> empDataList = transferOrderRepository
				.findEmpDetailsByPOrderAndPostIdAndSubjectId(orderType, postId, subjectId);
		String[] orderData = null;
		try {
			orderData = orderDataList.get(orderType == -1 ? 0 : orderType);
		} catch (Exception e) {
			System.out.println("Exception found in orderDataList.get(orderType) where orderType=" + orderType);
		}

		ResponseEntity<?> resp = transferOrderPdf.downloadPdfTransferOrder(orderType, orderData, empDataList);
		return resp;
	}

	
	
	
	@Override
	public ResponseEntity<?> downloadTransferComparisonReptPdf(String empCode) throws IOException {
		
		TransferCompEmpDetailsProj tcEmpDetails = transferOrderRepository.findOemEmpDetailsBy(empCode);
		Map<Integer ,List<TransferCompChoiceProj>> choiceMap=new TreeMap<>();
		if(tcEmpDetails !=null) {
			Integer postId=tcEmpDetails.getPost_id();
			Integer subjectId=tcEmpDetails.getSubject_id();
			
			
			for(int i=1;i<=5;i++) {
				String getStationCodeM = "getStation_code_"+i;
				try {
					java.lang.reflect.Method method = tcEmpDetails.getClass().getMethod(getStationCodeM);
					Integer stationCode = (Integer) method.invoke(tcEmpDetails);

					List<TransferCompChoiceProj> choice = transferOrderRepository.findOemEmpChoice(empCode,postId,subjectId,stationCode);
					
					System.out.println("empCode="+empCode+", postId="+postId+", subjectId="+subjectId+", stationCode="+stationCode);

					choiceMap.put(i, choice);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		

		
		
		ResponseEntity<?> resp = transferComparisonReptPdf.downloadTransferComparisonReptPdf(empCode,tcEmpDetails,choiceMap);
		return resp;
	}

}

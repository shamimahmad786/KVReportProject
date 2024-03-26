package in.gov.udiseplus.kv.report.controller;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.gov.udiseplus.kv.report.service.PDFService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PDFController {
	
	@Autowired
	PDFService pdfService;

	@GetMapping(value = "/transfer-order/{orderType}/{postId}/{subjectId}")
	public ResponseEntity<?> fetchTransManagementPdf(
	@PathVariable(value ="orderType", required = true) Integer orderType,
	@PathVariable(value ="postId", required = true) Integer postId,
	@PathVariable(value ="subjectId", required = true) Integer subjectId
	) throws IOException {
		ResponseEntity<?> resp = pdfService.findEmpDetailsByPOrderAndPostIdAndSubjectId(orderType, postId, subjectId);
		return resp;
	}
	


	/*@GetMapping(value = "/transfer-order/{orderNo}")
	public ResponseEntity<?> fetchTransManagementPdf(
	@PathVariable(value ="orderNo", required = true) Integer orderNo	) throws IOException {
	
		String downloadFolderPath = System.getProperty("user.home") + "/Downloads/";
		String downloadedResponse = "";
		for (int orderType = 0; orderType < orderNo; orderType++) {
			String[] orderTextGround = {
					"केविसं की स्थानांतरण नीति 2023 के Part - 1 – A के पैरा 2 (II) (A) a) के अंतर्गत स्टेशनों में निर्धारित कार्यकाल पूर्ण करने के उपरांत अथवा DFP/MDG/LTR के अंतर्गत आने वाले मामलों में उप्लब्ध रिक्त पदों पर कर्मचारियों का स्थानांतरण करने का प्रावधान है । तदनुसार, निम्नलिखित कर्मचारियों का स्थानांतरण प्रशासनिक आधार पर किया जाता है। निम्नलिखित कर्मचारी केविसं के नियमानुसार स्थानांतरण सम्बन्धी लाभ के हक़दार होंगे। बशर्ते उन्होंने निर्धारित कार्यकाल पूर्ण कर लिया हो।",
					"/ In terms of para 2 (II) (A) a) of Part - 1 – A of the KVS Transfer Policy 2023 which inter-alia provides transfer of employees who have completed tenure or cases covered under DFP/MDG/ LTR to Stations having vacancies. Accordingly, transfer of the following employees is ordered on administrative grounds. They are entitled for all Transfer benefits as per KVS rules subject to completion of prescribed tenure.",
					"F.No. 11-E-II046/2/2023-ESTT-II/ Part1-A-2-II-(A)-a)-Administrative grounds",
					"Transfer Admn ground available vacancies"};
	
			String[] orderTextStationFemale = {
					"केविसं की स्थानांतरण नीति 2023 के Part - 1 – A के पैरा 2 (I) (B) b) के अंतर्गत दुर्गम/पूर्वोत्तर स्टेशनों में रिक्त पदों को भरने के लिए इच्छुक महिला कर्मचारियों को प्रशासनिक आधार पर स्थानांतरित करने का प्रावधान है । तदनुसार संगठनात्मक आवश्यकताओं में निम्नलिखित कर्मचारियों का स्थानांतरण किया जाता है। निम्नलिखित कर्मचारी केविसं के नियमानुसार स्थानांतरण सम्बन्धी लाभ के हक़दार होंगे।",
					"/ In terms of para 2 (I) (B) b) of Part - 1 – A of the KVS Transfer Policy 2023 which inter-alia provides administrative transfer of willing female employees to fill up vacancies in Hard/ NER Station. Accordingly, transfer of the following employees is ordered in organizational requirement. They are entitled for all Transfer benefits as per KVS rules.",
					"F.No. 11-E-II046/2/2023-ESTT-II/ Part1-A-2-I-(B)-b)-Organizational Requirement",
					"Hard Station - Under-40 request of willing female employee Organizational Requirement"};
	
			String[] orderTextStation = {
					"केविसं की स्थानांतरण नीति 2023 के Part - 1 – A के पैरा 2 (I) (B) a) के अंतर्गत दुर्गम /पूर्वोत्तर क्षेत्र में रिक्त पदों को भरने के लिए कर्मचारियों को स्थानांतरित करने का प्रावधान है, तदनुसार संगठनात्मक आवश्यकताओं में निम्नलिखित कर्मचारियों का स्थानांतरण किया जाता है। निम्नलिखित कर्मचारी केविसं के नियमानुसार स्थानांतरण सम्बन्धी लाभ के हक़दार होंगे ।",
					"/ In terms of para 2 ( I ) (B) a) of Part - 1 – A of the KVS Transfer Policy 2023 which inter-alia provides transfer of employees to fill up vacancies in Hard/NER Station. Accordingly, the transfer of the following employees is ordered in Organisational requirements. They are entitled for all Transfer benefits as per KVS rules.",
					"F.No. 11-E-II046/2/2023-ESTT-II/Part1-A-2-I-(B)-a)-Organizational Requirement (under-40)",
					"Hard Station - Under-40 Org Req"};
	
			String[] orderTextDisplacement = {
					"केविसं की स्थानांतरण नीति 2023 के Part - 1 – A के पैरा 2 (II) (A) b) (iii) के अनुसार PwD/MDG/DFP /LTR के अंतर्गत आने वाले कर्मचारियों को समायोजित करने हेतु विस्थापन आधार पर स्थानांतरण करने का प्रावधान है। तदनुसार, निम्नलिखित कर्मचारियों का स्थानांतरण प्रशासनिक आधार पर किया जाता है। निम्नलिखित कर्मचारी केविसं के नियमानुसार स्थानांतरण सम्बन्धी लाभ के हक़दार होंगे। बशर्ते उन्होंने निर्धारित कार्यकाल पूर्ण कर लिया हो।",
					"/ In terms of para 2 (II) (A) b) (iii) of Part - 1 – A of the KVS Transfer Policy 2023 which inter-alia provides transfer of employees on Displacement to accommodate the employees falling under PwD/MDG/DFP/LTR. Accordingly, transfer of the following employees is ordered on administrative grounds. They are entitled for all Transfer benefits as per KVS rules subject to completion of prescribed tenure.",
					"F.No. 11-E-II046/2/2023-ESTT-II/ Part1-A-2-II-(A)-b)(iii)-Administrative grounds",
					"Transfer Admn ground on displacement (1) -PwD DFP MDG LTR"};
	
			List<String[]> orderDataList = new ArrayList<>();
			orderDataList.add(orderTextGround);
			orderDataList.add(orderTextStationFemale);
			orderDataList.add(orderTextStation);
			orderDataList.add(orderTextDisplacement);
	
			String[] orderData = null;
			try {
				orderData = orderDataList.get(orderType);
			} catch (Exception e) {
				System.out.println("Exception found in orderDataList.get(orderType) where orderType=" + orderType);
			}
			
			byte[] pdfBytes = transferOrderPdf.downloadPdfTransferOrder(orderType, orderData);
			Path path = Paths.get(downloadFolderPath,orderData[3] + ".pdf");
			
			if (Files.exists(path)) {
	            int copyNumber = 1;
	            String newFileName;
	            do {
	                newFileName = String.format("%s_copy%d.pdf", orderData[3], copyNumber++);
	                path = Paths.get(downloadFolderPath, newFileName);
	            } while (Files.exists(path));
	        }
	
			Files.write(path, pdfBytes);
			downloadedResponse += "Download PDF files successfully and PDF location: " + path+"\n";
		}
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body(downloadedResponse);
	}*/
	

	@GetMapping(value = "/transfer-comparison/{empCode}")
	public ResponseEntity<?> fetchTransferComparisonReptPdf(
	@PathVariable(value ="empCode", required = true) String empCode	) throws IOException {
		ResponseEntity<?>  resp = pdfService.downloadTransferComparisonReptPdf(empCode);
		return resp;
	}
	
	
	/*@PostMapping("/downloadExcel")
	public ResponseEntity<?> downloadExcel() throws Exception {
	
		String fileName = "abcd.xlsx";
		Path filePath = Paths.get("D:/PGIDownload/" + fileName);
		if (!Files.exists(filePath)) {
			return ResponseEntity.notFound().build();
		}
		
		File file = filePath.toFile();
		FileInputStream fileInputStream = new FileInputStream(file);
		InputStreamResource resource = new InputStreamResource(fileInputStream);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).headers(headers).body(resource);
	}*/
	
	/*	@PostMapping("/downloadExcel")
		public ResponseEntity<?> downloadExcel() {
		    String fileName = "abcd.xlsx";
		    Path filePath = Paths.get("D:/PGIDownload/" + fileName);
		    System.out.println("Checking if file exists: " + fileName);
		    if (!Files.exists(filePath)) {
		        System.out.println("File not found: " + fileName);
		        return ResponseEntity.notFound().build();
		    }
		    System.out.println("File found, preparing download: " + fileName);
		    FileSystemResource resource = new FileSystemResource(filePath.toFile());
		    HttpHeaders headers = new HttpHeaders();
		    headers.add("Content-Disposition", "attachment; filename=deployment-definitions.xlsx");
		    System.out.println("Returning response entity for download: " + fileName);
		    
		    //response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		    //response.setHeader("Content-Disposition", "attachment; filename=deployment-definitions.xlsx");
		    
		    return ResponseEntity.ok()
		            .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
		            .headers(headers)
		            .body(resource);
		}*/

	
	@PostMapping("/downloadExcel")
	    public ResponseEntity<String> getDownloadLink() {
	        String fileName = "abcd.xlsx";
	        Path filePath = Paths.get("D:/PGIDownload/" + fileName);

	        if (!filePath.toFile().exists()) {
	            return ResponseEntity.notFound().build();
	        }

	        // Generate a temporary link to the file
	        URI fileUri = filePath.toUri();
	        return ResponseEntity.ok("Download link: " + fileUri.toString());
	    }
	
	
}

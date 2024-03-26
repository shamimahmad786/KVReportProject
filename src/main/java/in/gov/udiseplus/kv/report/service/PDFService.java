package in.gov.udiseplus.kv.report.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

public interface PDFService {

	ResponseEntity<?> findEmpDetailsByPOrderAndPostIdAndSubjectId(Integer orderType, Integer postId, Integer subjectId) throws IOException;

	ResponseEntity<?> downloadTransferComparisonReptPdf(String empCode)throws IOException;

}

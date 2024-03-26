package in.gov.udiseplus.kv.report.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {

	@GetMapping("/transferPdf")
    public String generateDownloadLinks() {
		return "trasferOrderPdfs";
    }
}
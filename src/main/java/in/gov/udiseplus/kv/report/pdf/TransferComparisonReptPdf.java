package in.gov.udiseplus.kv.report.pdf;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.splitting.ISplitCharacters;
import com.itextpdf.licensekey.LicenseKey;

import in.gov.udiseplus.kv.report.bean.TransferCompChoiceProj;
import in.gov.udiseplus.kv.report.bean.TransferCompEmpDetailsProj;
import in.gov.udiseplus.kv.report.utill.CommonMethodForPdf;
import in.gov.udiseplus.kv.report.utill.Constants;


@Component
public class TransferComparisonReptPdf {
	
	DecimalFormat df = new DecimalFormat("0.00000");

	public byte[] inputStreamToByteArray(InputStream is) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[16384];
		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}
		buffer.flush();
		return buffer.toByteArray();
	}

	@SuppressWarnings("resource")
	public byte[] addFooterAndPageNumbers(byte[] pdf, String empNameGender, String empCode) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfReader reader = new PdfReader(new ByteArrayInputStream(pdf));

		SimpleDateFormat sdf = new SimpleDateFormat(Constants.META_DATA_DATE_FORMAT);
		String formattedDate = sdf.format(new Date());

		PdfWriter writer = new PdfWriter(baos);
		PdfDocument pdfDoc = new PdfDocument(reader, writer);
		int numberOfPages = pdfDoc.getNumberOfPages();
		// PdfFont font = PdfFontFactory.createFont();
		PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

		for (int i = 1; i <= numberOfPages; i++) {
			PdfPage page = pdfDoc.getPage(i);
			Rectangle pageSize = page.getPageSize();
			PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

			String pageInfo = "Page no " + Integer.toString(pdfDoc.getPageNumber(page)) + " of " + numberOfPages;

			new Canvas(pdfCanvas, pdfDoc, pageSize).setFont(font).setFontSize(9).showTextAligned(pageInfo, pageSize.getWidth() / 2, 20, TextAlignment.CENTER, VerticalAlignment.MIDDLE, 0);

			new Canvas(pdfCanvas, pdfDoc, pageSize).setFont(font).setFontSize(9).setFontColor(new DeviceRgb(12, 49, 99))
			.showTextAligned("Generated on " + formattedDate, 640, 26, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0)
			.showTextAligned("https://kvsonlinetransfer.kvs.gov.in", 640, 15, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			
			if (i > 1) {
				new Canvas(pdfCanvas, pdfDoc, pageSize).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(9).setFontColor(new DeviceRgb(165, 42, 42))
						.showTextAligned("Employee Name : " + empNameGender, 15, 570, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0)
						.showTextAligned("Employee Code : " + empCode, 725, 570, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			}
			
			
			
			PdfCanvas canvasImage = new PdfCanvas(page).saveState().setExtGState(new PdfExtGState());
			try (Canvas canvas = new Canvas(canvasImage, pdfDoc, pageSize)) {
				Image image = new Image(ImageDataFactory.create(inputStreamToByteArray(getClass().getResourceAsStream("/static/images/kvs-nic.png"))));
				//image.scaleAbsolute(200, 25);
				image.setHeight(25);
				image.setFixedPosition(32, 6);
				canvas.add(image);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				canvasImage.restoreState();
			}
			
			
			/*PdfCanvas canvasNicLogo = new PdfCanvas(page).saveState().setExtGState(new PdfExtGState().setFillOpacity(.7f));
			try (Canvas canvas = new Canvas(canvasNicLogo, pdfDoc, pageSize)) {
				Image image = new Image(ImageDataFactory.create(inputStreamToByteArray(getClass().getResourceAsStream("/static/images/nic-logo.png"))));
				image.scaleAbsolute(140, 20);
				image.setFixedPosition(68, 8);
				canvas.add(image);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				canvasNicLogo.restoreState();
			}*/

		}

		pdfDoc.close();
		return baos.toByteArray();
	}
	

	
	
	
	
	
	
	public 	ResponseEntity<?>  downloadTransferComparisonReptPdf(String empCode, TransferCompEmpDetailsProj tcEmpDetails, Map<Integer, List<TransferCompChoiceProj>> choiceMap) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PdfWriter write = new PdfWriter(byteArrayOutputStream);
		write.setSmartMode(true);

		PdfDocument pdfDoc = new PdfDocument(write);
		pdfDoc.setDefaultPageSize(PageSize.A4.rotate());
		Document doc = new Document(pdfDoc);

		 Rectangle pageSize = pdfDoc.getDefaultPageSize();
		 doc.setMargins(30, 10, 31, 10); // Set all margins to 0
		 doc.setFixedPosition(0, 0, pageSize.getWidth(), pageSize.getHeight());
		
		

		try {
			String itextkeyPath = new File(getClass().getResource("/static/itextkey1576128028874_0.xml").toURI()).getAbsolutePath();
			LicenseKey.loadLicenseFile(itextkeyPath);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		Color paraFColor1 = new DeviceRgb(27, 50, 90);
		doc.add(CommonMethodForPdf.createParaGraphBold("Employee Transfer Comparison Report", 5f, 10f, 15, paraFColor1, null, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.BOTTOM));
		
		/*float[] columnWidMainTab = { 1f,1f,1f,1f,1f};
		Table mainTable = new Table(UnitValue.createPercentArray(columnWidMainTab));
		mainTable.setWidth(UnitValue.createPercentValue(100));*/
		
		String empNameGender=tcEmpDetails.getEmpNameGender();
		Table tableEmp = getEmpDetails(doc,tcEmpDetails);
		doc.add(tableEmp);
		if (choiceMap != null) {
			//Color[] colorHeadArr= {new DeviceRgb(243, 193, 169),new DeviceRgb(150, 192, 231),new DeviceRgb(134, 134, 227),new DeviceRgb(229, 206, 111),new DeviceRgb(183,173,145)};
			//Color[] colorBodyArr= {new DeviceRgb(255, 238, 230),new DeviceRgb(230,243,255),new DeviceRgb(230, 230, 255),new DeviceRgb(255, 245, 204),new DeviceRgb(223,216,197)};
			int maxChoice = 0;
		    for (Map.Entry<Integer, List<TransferCompChoiceProj>> entry : choiceMap.entrySet()) {
		        int currentSize = entry.getValue().size();
		        if (currentSize > maxChoice) {
		        	maxChoice = currentSize;
		        }
		    }
		    
			if(maxChoice > 0) {
				Table tableChoice = getChoice(doc,choiceMap,tcEmpDetails,maxChoice);
				doc.add(tableChoice);
			}else {
				doc.add(CommonMethodForPdf.createParaGraphBold("Choice not avaliable", 50f, 0f, 25, new DeviceRgb(0, 0, 0), null, TextAlignment.CENTER));
			}
		}
		
		doc.close();

		byte[] bytes = byteArrayOutputStream.toByteArray();
		
		try {
			bytes = addFooterAndPageNumbers(bytes, empNameGender,empCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=choice.pdf");
        //headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+orderData[3]+".pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        headers.setCacheControl("no-cache, no-store, must-revalidate");
        headers.setPragma("no-cache");
        headers.setExpires(0);
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(bytes);

	}
	


	


	private Table getEmpDetails(Document doc, TransferCompEmpDetailsProj empDetails) {
		float[] columnWidths = {.62f,1f,.03f, .9f,1f,.03f,  .6f,1.6f,.03f, .25f,.25f,.03f, .4f,.5f};
		Table table = new Table(UnitValue.createPercentArray(columnWidths));
		table.setWidth(UnitValue.createPercentValue(100));

		try {
			//Color bgHeadColor=new DeviceRgb(255, 255, 179);
			Color bgHeadColor=new DeviceRgb(230,243,255);
			Color fHeadColor = null;
			float fontSize=8f;
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
			//String formattedDate = sdf.format(new Date());
			
			table.addCell(CommonMethodForPdf.createCellBold("Employee Code", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor));
			table.addCell(CommonMethodForPdf.createCell(empDetails.getEmp_code() !=null?empDetails.getEmp_code():"", 1, 1, fontSize));
			table.addCell(CommonMethodForPdf.createCellBold("", 1, 1, fontSize).setBorder(null));
			
			table.addCell(CommonMethodForPdf.createCellBold("Employee Name / Gender", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor));
			table.addCell(CommonMethodForPdf.createCell(empDetails.getEmpNameGender() !=null?empDetails.getEmpNameGender():"", 1, 1, fontSize));
			table.addCell(CommonMethodForPdf.createCellBold("", 1, 1, fontSize).setBorder(null));
		
			table.addCell(CommonMethodForPdf.createCellBold("Post", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor));
			table.addCell(CommonMethodForPdf.createCell(empDetails.getPostname() !=null?empDetails.getPostname():"", 1, 1, fontSize));
			table.addCell(CommonMethodForPdf.createCellBold("", 1, 1, fontSize).setBorder(null));
			
			table.addCell(CommonMethodForPdf.createCellBold("DC", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor));
			table.addCell(CommonMethodForPdf.createCell(empDetails.getTot_dc() !=null?empDetails.getTot_dc()+"":"", 1, 1, fontSize));
			table.addCell(CommonMethodForPdf.createCellBold("", 1, 1, fontSize).setBorder(null));

			table.addCell(CommonMethodForPdf.createCellBold("DoB", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor));
			table.addCell(CommonMethodForPdf.createCell(empDetails.getDob() !=null?sdf.format(empDetails.getDob()):"", 1, 1, fontSize));
			//table.addCell(CommonMethodForPdf.createCellBold("", 14, 1, fontSize).setBorder(null));
			
			
			//--------------------------------------------------------------------------------------
			
			table.addCell(CommonMethodForPdf.createCellBold("Subject", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor));
			table.addCell(CommonMethodForPdf.createCell(empDetails.getSubject() !=null?empDetails.getSubject():"", 1, 1, fontSize));
			table.addCell(CommonMethodForPdf.createCellBold("", 1, 1, fontSize).setBorder(null));
			
			table.addCell(CommonMethodForPdf.createCellBold("Allotted KV", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor));
			table.addCell(CommonMethodForPdf.createCell(empDetails.getAlloted_kv() !=null?empDetails.getAlloted_kv():"No Kv Alloted", 1, 1, fontSize));
			table.addCell(CommonMethodForPdf.createCellBold("", 1, 1, fontSize).setBorder(null));
			
			table.addCell(CommonMethodForPdf.createCellBold("Current KV", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor));
			table.addCell(CommonMethodForPdf.createCell(empDetails.getCurrent_kv() !=null?empDetails.getCurrent_kv():"", 1, 1, fontSize));
			table.addCell(CommonMethodForPdf.createCellBold("", 1, 1, fontSize).setBorder(null));
			
			table.addCell(CommonMethodForPdf.createCellBold("TC1", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor));
			table.addCell(CommonMethodForPdf.createCell(empDetails.getTot_tc() !=null?empDetails.getTot_tc()+"":"", 1, 1, fontSize));
			table.addCell(CommonMethodForPdf.createCellBold("", 1, 1, fontSize).setBorder(null));
			
			table.addCell(CommonMethodForPdf.createCellBold("DOJ STN", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor));
			table.addCell(CommonMethodForPdf.createCell(empDetails.getDoj_in_present_stn_irrespective_of_cadre() !=null?sdf.format(empDetails.getDoj_in_present_stn_irrespective_of_cadre()):"", 1, 1, fontSize));
			//table.addCell(CommonMethodForPdf.createCellBold("", 1, 1, fontSize).setBorder(null));

			//--------------------------------------------------------------------------------------
			
			table.addCell(CommonMethodForPdf.createCellBold("Transfer Ground", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor));
			table.addCell(CommonMethodForPdf.createCell(empDetails.getTransferred_under_cat() !=null?empDetails.getTransferred_under_cat()+"":"", 1, 1, fontSize));
			table.addCell(CommonMethodForPdf.createCellBold("", 1, 1, fontSize).setBorder(null));
			
			table.addCell(CommonMethodForPdf.createCellBold("Allotted Station", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor));
			table.addCell(CommonMethodForPdf.createCell(empDetails.getAlloted_kv() !=null?empDetails.getAlloted_kv():"No Kv Alloted", 1, 1, fontSize));
			table.addCell(CommonMethodForPdf.createCellBold("", 1, 1, fontSize).setBorder(null));

			table.addCell(CommonMethodForPdf.createCellBold("Current Station", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor));
			table.addCell(CommonMethodForPdf.createCell(empDetails.getCurrent_station() !=null?empDetails.getCurrent_station()+"":"", 1, 1, fontSize));
			table.addCell(CommonMethodForPdf.createCellBold("", 1, 1, fontSize).setBorder(null));
			
			
			

			
			table.addCell(CommonMethodForPdf.createCellBold("TC2", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor));
			table.addCell(CommonMethodForPdf.createCell(empDetails.getTot_tc2() !=null?empDetails.getTot_tc2()+"":"", 1, 1, fontSize));
			table.addCell(CommonMethodForPdf.createCellBold("", 1, 1, fontSize).setBorder(null));

			table.addCell(CommonMethodForPdf.createCellBold("DOR", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor));
			table.addCell(CommonMethodForPdf.createCell(empDetails.getDor() !=null?sdf.format(empDetails.getDor()):"", 1, 1, fontSize));

			
			//table.addCell(CommonMethodForPdf.createCellBold("", 1, 1, fontSize).setBackgroundColor(bgHeadColor));
			//table.addCell(CommonMethodForPdf.createCell("", 1, 1, fontSize).setBackgroundColor(bgHeadColor));
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
			return table;
		}
		
		
		return table;
	}

	

	private Table getChoice(Document doc, Map<Integer, List<TransferCompChoiceProj>> choiceMap,TransferCompEmpDetailsProj tcEmpDetails, int maxChoice) {
		
		float[] choiceWidth = {.6f, 1f, .8f, .29f, .29f, .36f, .21f, .79f};
		float[] columnWidths = new float[5*choiceWidth.length];
		for (int i = 0; i < 5; i++) {
		    System.arraycopy(choiceWidth, 0, columnWidths, i * choiceWidth.length, choiceWidth.length);
		}
		
		/*	float[] columnWidths = {
					.6f,.9f, .9f,.38f,.38f,.38f,.38f,.9f,
					.6f,.9f, .9f,.38f,.38f,.38f,.38f,.9f,
					.6f,.9f, .9f,.38f,.38f,.38f,.38f,.9f,
					.6f,.9f, .9f,.38f,.38f,.38f,.38f,.9f,
					.6f,.9f, .9f,.38f,.38f,.38f,.38f,.9f
					};*/
		Table table = new Table(UnitValue.createPercentArray(columnWidths));
		table.setWidth(UnitValue.createPercentValue(100)).setFixedLayout();

		ISplitCharacters noSplit = (text, glyphPos) -> false;
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		
		try {
			Color bgHeadColor = new DeviceRgb(37,132,198);
			float fontSize=5.8f;
			Color fHeadColor = new DeviceRgb(255,255,255);
			Border borderColor = new SolidBorder(new DeviceRgb(255,255,255), 1.1f);
			PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
			PdfFont fontNormal = PdfFontFactory.createFont(StandardFonts.HELVETICA);
			
			table.addHeaderCell(CommonMethodForPdf.createCellBold("", columnWidths.length, 1, fontSize).setBorder(null).setPaddingTop(5));
			
			for (Map.Entry<Integer, List<TransferCompChoiceProj>> entry : choiceMap.entrySet()) {
				Integer choiceNo = entry.getKey();
				String getStationCodeM = "getChoice_stn"+choiceNo;
				java.lang.reflect.Method method = tcEmpDetails.getClass().getMethod(getStationCodeM);
				String choiceStationName = (String) method.invoke(tcEmpDetails);

				table.addHeaderCell(CommonMethodForPdf.createCellBold("Choice "+choiceNo, 2, 1, 8f).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor).setTextAlignment(TextAlignment.CENTER).setBorder(borderColor));
				table.addHeaderCell(CommonMethodForPdf.createCellBold(choiceStationName, 6, 1, 8f).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor).setBorder(borderColor));
			}
			
			for (Map.Entry<Integer, List<TransferCompChoiceProj>> entry : choiceMap.entrySet()) {
				bgHeadColor = new DeviceRgb(37,132,198);
				table.addHeaderCell(CommonMethodForPdf.createCellBold("Emp Code", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor).setTextAlignment(TextAlignment.CENTER).setBorder(borderColor));
				
				table.addHeaderCell(CommonMethodForPdf.createCellBold("Name / Gender", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor).setTextAlignment(TextAlignment.LEFT).setBorder(borderColor));
				
				/*Paragraph paraName = new Paragraph("Name").setFont(fontBold).setFontSize(fontSize).setFontColor(fHeadColor);
				Paragraph paraGender = new Paragraph("/ Gender").setFont(fontBold).setFontSize(fontSize).setFontColor(fHeadColor);
				table.addHeaderCell(new Cell(1, 1).add(paraName).add(paraGender).setBackgroundColor(bgHeadColor).setTextAlignment(TextAlignment.LEFT).setBorder(borderColor));*/
				
		        
				table.addHeaderCell(CommonMethodForPdf.createCellBold("Transfer Ground", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor).setTextAlignment(TextAlignment.LEFT).setBorder(borderColor));

				table.addHeaderCell(CommonMethodForPdf.createCellBold("TC1", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor).setRotationAngle(Math.PI/2).setSplitCharacters(noSplit).setVerticalAlignment(VerticalAlignment.BOTTOM).setTextAlignment(TextAlignment.CENTER).setBorder(borderColor).setPadding(1).setPaddingRight(2.8f));
				table.addHeaderCell(CommonMethodForPdf.createCellBold("TC2", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor).setRotationAngle(Math.PI/2).setSplitCharacters(noSplit).setVerticalAlignment(VerticalAlignment.BOTTOM).setTextAlignment(TextAlignment.CENTER).setBorder(borderColor).setPadding(1).setPaddingRight(2.8f));
				table.addHeaderCell(CommonMethodForPdf.createCellBold("DC", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor).setRotationAngle(Math.PI/2).setSplitCharacters(noSplit).setVerticalAlignment(VerticalAlignment.BOTTOM).setTextAlignment(TextAlignment.CENTER).setBorder(borderColor).setPadding(2).setPaddingRight(3.7f));
				table.addHeaderCell(CommonMethodForPdf.createCellBold("CH", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor).setRotationAngle(Math.PI/2).setSplitCharacters(noSplit).setVerticalAlignment(VerticalAlignment.BOTTOM).setTextAlignment(TextAlignment.CENTER).setBorder(borderColor).setPadding(0).setPaddingRight(2.3f));
				table.addHeaderCell(CommonMethodForPdf.createCellBold("DoB", 1, 1, fontSize).setBackgroundColor(bgHeadColor).setFontColor(fHeadColor).setTextAlignment(TextAlignment.CENTER).setBorder(borderColor));
			}
			
			

			

			Color bgBodyColor = null;
			fontSize=5.5f;
			for (Map.Entry<Integer, List<TransferCompChoiceProj>> entry : choiceMap.entrySet()) {

				Table tableIn = new Table(UnitValue.createPercentArray(choiceWidth));
				tableIn.setWidth(UnitValue.createPercentValue(100)).setFixedLayout();
				
				Integer choiceNo = entry.getKey();
			    List<TransferCompChoiceProj> choiceList = entry.getValue();
			    for (TransferCompChoiceProj obj : choiceList) {
			    	tableIn.addCell(CommonMethodForPdf.createCell(obj.getEmp_code()!=null?obj.getEmp_code():"", 1, 1, fontSize).setBackgroundColor(bgBodyColor).setTextAlignment(TextAlignment.CENTER));
			    	
			    	tableIn.addCell(CommonMethodForPdf.createCell(obj.getEmpNameGender()!=null?obj.getEmpNameGender():"", 1, 1, fontSize).setBackgroundColor(bgBodyColor));
			    	
					/*Paragraph paraName = new Paragraph("Name").setFont(fontNormal).setFontSize(fontSize);
					Paragraph paraGender = new Paragraph("/Gender").setFont(fontNormal).setFontSize(fontSize).setFontColor(new DeviceRgb(51, 0, 26));
					tableIn.addCell(new Cell(1, 1).add(paraName).add(paraGender).setBackgroundColor(bgBodyColor).setBorder(new SolidBorder(new DeviceRgb(26, 0, 13), 0.5f)));*/
			    	
			    	
			    	tableIn.addCell(CommonMethodForPdf.createCell(obj.getTransferred_under_cat()!=null?obj.getTransferred_under_cat()+"":"", 1, 1, fontSize).setBackgroundColor(bgBodyColor));
					
			    	tableIn.addCell(CommonMethodForPdf.createCell(obj.getTot_tc()!=null?obj.getTot_tc()+"":"", 1, 1, fontSize).setBackgroundColor(bgBodyColor).setTextAlignment(TextAlignment.CENTER));
			    	tableIn.addCell(CommonMethodForPdf.createCell(obj.getTot_tc2()!=null?obj.getTot_tc2()+"":"", 1, 1, fontSize).setBackgroundColor(bgBodyColor).setTextAlignment(TextAlignment.CENTER));
			    	tableIn.addCell(CommonMethodForPdf.createCell(obj.getTot_dc()!=null?obj.getTot_dc()+"":"", 1, 1, fontSize).setBackgroundColor(bgBodyColor).setTextAlignment(TextAlignment.CENTER));
			    	tableIn.addCell(CommonMethodForPdf.createCell(obj.getChoice_stn()!=null?obj.getChoice_stn()+"":"", 1, 1, fontSize).setBackgroundColor(bgBodyColor).setTextAlignment(TextAlignment.CENTER));
			    	tableIn.addCell(CommonMethodForPdf.createCell(obj.getDob()!=null?sdf.format(obj.getDob()):"", 1, 1, 5f).setBackgroundColor(bgBodyColor).setTextAlignment(TextAlignment.CENTER));
			    }
				int remaingSize=maxChoice-choiceList.size();
				if(remaingSize >0) {
					tableIn.addCell(CommonMethodForPdf.createCell("", choiceWidth.length, remaingSize, fontSize).setBackgroundColor(bgBodyColor).setBorder(null));
				}
				table.addCell(new Cell(1,choiceWidth.length).add(tableIn).setPadding(0));
			 }

		
		} catch (Exception e) {
			e.printStackTrace();
			return table;
		}
		
		
		return table;
	}
	
	
	


}

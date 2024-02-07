package in.gov.udiseplus.kv.report.pdf;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
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
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import in.gov.udiseplus.kv.report.bean.ConfirmedTeacherDetailsResp;
import in.gov.udiseplus.kv.report.bean.Experience;
import in.gov.udiseplus.kv.report.bean.ResponseData;
import in.gov.udiseplus.kv.report.bean.TeacherConfirmation;
import in.gov.udiseplus.kv.report.bean.TeacherProfile;
import in.gov.udiseplus.kv.report.utill.CommonMethodForPdf;
import in.gov.udiseplus.kv.report.utill.Constants;


@Component
public class GenerateTeacherDetailsPdf {
	
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
	public byte[] addFooterAndPageNumbers(byte[] pdf, String regionName) throws Exception {
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
			.showTextAligned("Generated on " + formattedDate, 403, 28, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0)
			.showTextAligned("https://kvsonlinetransfer.kvs.gov.in/", 403, 15, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);


			Image imagehead = null;
			if (i == 1) {
				try {
					InputStream is = getClass().getResourceAsStream("/static/images/kvs-logo.png");
					imagehead = new Image(ImageDataFactory.create(inputStreamToByteArray(is)));
					// imagehead.scaleAbsolute(100, 50);
					imagehead.setFixedPosition(32, 750);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Image imagefooter = null;
			try {
				InputStream is = getClass().getResourceAsStream("/static/images/kvs-nic-logo.png");
				imagefooter = new Image(ImageDataFactory.create(inputStreamToByteArray(is)));
				imagefooter.scaleAbsolute(200, 25);
				imagefooter.setFixedPosition(32, 6);
			} catch (Exception e) {
				e.printStackTrace();
			}

			PdfExtGState gstate = new PdfExtGState();
			PdfCanvas canvasImage = new PdfCanvas(page);
			canvasImage.saveState();
			canvasImage.setExtGState(gstate);
			try (Canvas canvas2 = new Canvas(canvasImage, pdfDoc, pageSize)) {
				canvas2.add(imagefooter);
				if(imagehead !=null)
					canvas2.add(imagehead);
			} catch (Exception e) {
				e.printStackTrace();
			}
			canvasImage.restoreState();
		}

		pdfDoc.close();
		return baos.toByteArray();
	}
	

	
	
	
	
	
	
	public ResponseEntity<?> downloadTeacherDetailsPdf(ConfirmedTeacherDetailsResp dataObj, TeacherConfirmation dataObj1) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PdfWriter write = new PdfWriter(byteArrayOutputStream);
		write.setSmartMode(true);

		PdfDocument pdfDoc = new PdfDocument(write);
		pdfDoc.setDefaultPageSize(PageSize.A4);
		Document doc = new Document(pdfDoc);
		
		
		Color paraFColor1 = new DeviceRgb(165, 42, 42);
		Color paraFColor2 = new DeviceRgb(0, 0, 0);
		
		doc.add(CommonMethodForPdf.createParaGraphBold("Basic Profile", 0f, 20f, 25, paraFColor1, null, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.BOTTOM));
		
		if (dataObj!=null && dataObj.getResponse() !=null && dataObj.getResponse().getTeacherProfile() !=null ) {
			float[] columnWidMainTab = { 1 };
			Table mainTable1 = new Table(UnitValue.createPercentArray(columnWidMainTab));
			mainTable1.setWidth(UnitValue.createPercentValue(100));

			if(dataObj.getResponse() !=null && dataObj.getResponse() !=null && dataObj.getResponse().getTeacherProfile() !=null) {
				Table table = getProfileDetails(doc,dataObj.getResponse());
				Cell c1 = new Cell(1, 1);
				c1.add(table).setBorder(null);
				mainTable1.addCell(c1);
				mainTable1.addCell(CommonMethodForPdf.createCellBold("", 1, 1, 9f).setBorder(null).setPaddingBottom(10));
			}
		
			if (dataObj!=null && dataObj.getResponse() !=null && dataObj.getResponse().getExperience().size()>0) {
				Table tableExp = getExperience(doc, dataObj.getResponse());
				Cell c2 = new Cell(1, 1);
				c2.add(tableExp).setBorder(null);
				mainTable1.addCell(c2);
				mainTable1.addCell(CommonMethodForPdf.createCellBold("", 1, 1, 9f).setBorder(null).setPaddingBottom(10));
			}
			
			
			if(dataObj.getResponse() !=null && dataObj.getResponse() !=null && dataObj.getResponse().getTeacherProfile() !=null) {
			Table tableEmp = getEmpDetails(doc, dataObj1);
			Cell c3 = new Cell(1, 1);
			c3.add(tableEmp).setBorder(null);
			mainTable1.addCell(c3);
			}

			doc.add(mainTable1);

		} else {
			doc.add(CommonMethodForPdf.createParaGraphBold("Data not avaliable", 50f, 0f, 25, paraFColor2, null, TextAlignment.CENTER));
		}
		doc.close();

		byteArrayOutputStream.close();
		byte[] bytes = byteArrayOutputStream.toByteArray();
		try {
			bytes = addFooterAndPageNumbers(bytes, "ABCD");
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=TeacherBasicProfilePdf"  + ".pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(bytes);
	}
	

	
	private Table getProfileDetails(Document doc, ResponseData responseData) throws IOException {
		float[] columnWidths = {1f , 2f , 1f ,1 , 1 , 1};
		Table table = new Table(UnitValue.createPercentArray(columnWidths));
		table.setWidth(UnitValue.createPercentValue(100));
		
		try {
			TeacherProfile tProfile=responseData.getTeacherProfile();
			table.addCell(CommonMethodForPdf.createCellBold("Profile Details", 6, 1, 11f).setBackgroundColor(new DeviceRgb(116,146,203)).setTextAlignment(TextAlignment.CENTER).setFontColor(DeviceRgb.WHITE));

			System.out.println("School Name--->"+responseData.getSchoolDetails().getKvName());
			
			table.addCell(CommonMethodForPdf.createCellBold("KV/RO/ZIET/HQ Name", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell(responseData.getSchoolDetails().getKvName()==null?"":responseData.getSchoolDetails().getKvName()+" ("+responseData.getSchoolDetails().getKvCode()+")", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			
			table.addCell(CommonMethodForPdf.createCellBold("Employee Code", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell(tProfile.getTeacherEmployeeCode()==null?"":tProfile.getTeacherEmployeeCode()+"", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			
			table.addCell(CommonMethodForPdf.createCellBold("Staff Type", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell(tProfile.getTeachingNonteaching()==null?"":tProfile.getTeachingNonteaching().equals("1")?"Teaching":tProfile.getTeachingNonteaching().equals("2")?"Non Teaching":"", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			
			
			table.addCell(CommonMethodForPdf.createCellBold("Name", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell(tProfile.getTeacherName()==null?"":tProfile.getTeacherName()+"", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			
			table.addCell(CommonMethodForPdf.createCellBold("Gender", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell(tProfile.getTeacherGender()==null?"":tProfile.getTeacherGender().equals("1")?"Male":tProfile.getTeacherGender().equals("2")?"Female":"Transgender", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			
			table.addCell(CommonMethodForPdf.createCellBold("Date of Birth", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell(tProfile.getTeacherDob()==null?"":tProfile.getTeacherDob()+"", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			
			
			table.addCell(CommonMethodForPdf.createCellBold("Email", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell(tProfile.getTeacherEmail()==null?"":tProfile.getTeacherEmail()+"", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			
			table.addCell(CommonMethodForPdf.createCellBold("Mobile Number", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell(tProfile.getTeacherMobile()==null?"":tProfile.getTeacherMobile()+"", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			
			table.addCell(CommonMethodForPdf.createCellBold("Disability", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell(tProfile.getTeacherDisabilityYn()==null?"":tProfile.getTeacherDisabilityYn().equals("0")?"No":tProfile.getTeacherDisabilityYn().equals("1")? "Yes":"", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			
			table.addCell(CommonMethodForPdf.createCellBold("Present Station Name", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell(tProfile.getCurrentUdiseSchCode()==null?"":tProfile.getCurrentUdiseSchCode()+"", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			
		
			
			table.addCell(CommonMethodForPdf.createCellBold("Present post name", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell(tProfile.getLastPromotionPositionType()==null?"":tProfile.getLastPromotionPositionType()+"", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			
			table.addCell(CommonMethodForPdf.createCellBold("Subject Name", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell(tProfile.getWorkExperienceAppointedForSubject()==null?"":tProfile.getWorkExperienceAppointedForSubject()+"", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
		} catch (Exception e) {
			e.printStackTrace();
			return table;
		}
		
		
		return table;
	}
	
	private Table getExperience(Document doc, ResponseData responseData) throws IOException {
		float[] columnWidths = {1f , 2f , 1f ,1 , 1 , 1};
		Table table = new Table(UnitValue.createPercentArray(columnWidths));
		table.setWidth(UnitValue.createPercentValue(100));
		
		try {
			List<Experience> expList=responseData.getExperience();
			table.addCell(CommonMethodForPdf.createCellBold("Experience", 6, 1, 11f).setBackgroundColor(new DeviceRgb(116,146,203)).setTextAlignment(TextAlignment.CENTER).setFontColor(DeviceRgb.WHITE));

			table.addCell(CommonMethodForPdf.createCellBold("School Name", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCellBold("From", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCellBold("To", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCellBold("Position Held", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCellBold("Appointed for Subject", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCellBold("Transfer Ground", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			
			for (Experience listObj : expList) {
				table.addCell(CommonMethodForPdf.createCell(listObj.getUdiseSchoolName()==null?"":listObj.getUdiseSchoolName()+"" , 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
				table.addCell(CommonMethodForPdf.createCell(listObj.getWorkStartDate()==null?"":listObj.getWorkStartDate()+"", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
				table.addCell(CommonMethodForPdf.createCell(listObj.getWorkEndDate()==null?"":listObj.getWorkEndDate()+"", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
				table.addCell(CommonMethodForPdf.createCell(listObj.getPositionType()==null?"":listObj.getPositionType()+"", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
				table.addCell(CommonMethodForPdf.createCell(listObj.getAppointedForSubject()==null?"":listObj.getAppointedForSubject()+"", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
				table.addCell(CommonMethodForPdf.createCell(listObj.getGroundForTransfer()==null?"":listObj.getGroundForTransfer().equals("null")?"":listObj.getGroundForTransfer()+"", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return table;
		}
		
		
		return table;
	}
	
	private Table getEmpDetails(Document doc, TeacherConfirmation tProfile) throws IOException {
		float[] columnWidths = {1.5f,1f,0.5f  ,2f,1f,0.5f};
		Table table = new Table(UnitValue.createPercentArray(columnWidths));
		table.setWidth(UnitValue.createPercentValue(100));
		
		try {
			
			InputStream is = getClass().getResourceAsStream("/static/images/un-checkbox.png");
			Image imgUnCheckbox = new Image(ImageDataFactory.create(inputStreamToByteArray(is)));
			
			InputStream is1 = getClass().getResourceAsStream("/static/images/checkbox.png");
			Image imgCheckbox = new Image(ImageDataFactory.create(inputStreamToByteArray(is1)));
			
			
			table.addCell(CommonMethodForPdf.createCellBold("Employee Name:", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER).setBorderRight(null));
			table.addCell(CommonMethodForPdf.createCell(tProfile.getTeacherName()==null?"":tProfile.getTeacherName()+"", 1, 1, 9f).setTextAlignment(TextAlignment.RIGHT).setBorderRight(null).setBorderLeft(null));
			table.addCell(new Cell( 1, 1).add((tProfile.getTeacherName()==null?imgUnCheckbox:imgCheckbox).setHeight(15f)).setTextAlignment(TextAlignment.LEFT).setBorderRight(null).setBorderLeft(null));

			table.addCell(CommonMethodForPdf.createCellBold("Employee Gender:", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER).setBorderRight(null).setBorderLeft(null));
			table.addCell(CommonMethodForPdf.createCell(tProfile.getTeacherGender()==null?"":tProfile.getTeacherGender().equals("1")?"Male":tProfile.getTeacherGender().equals("2")?"Female":"Transgender", 1, 1, 9f).setTextAlignment(TextAlignment.RIGHT).setBorderRight(null).setBorderLeft(null));
			table.addCell(new Cell( 1, 1).add((tProfile.getTeacherGender()==null?imgUnCheckbox:imgCheckbox).setHeight(15f)).setTextAlignment(TextAlignment.LEFT).setBorderLeft(null));
			
			
			table.addCell(CommonMethodForPdf.createCellBold("Employee Dob:", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER).setBorderRight(null));
			table.addCell(CommonMethodForPdf.createCell(tProfile.getTeacherDob()==null?"":tProfile.getTeacherDob().split("-")[2]+"-"+tProfile.getTeacherDob().split("-")[1]+"-"+tProfile.getTeacherDob().split("-")[0]+"", 1, 1, 9f).setTextAlignment(TextAlignment.RIGHT).setBorderRight(null).setBorderLeft(null));
			table.addCell(new Cell( 1, 1).add((tProfile.getTeacherDob()==null?imgUnCheckbox:imgCheckbox).setHeight(15f)).setTextAlignment(TextAlignment.LEFT).setBorderLeft(null).setBorderRight(null));
			
			table.addCell(CommonMethodForPdf.createCellBold("Employee Code:", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER).setBorderRight(null).setBorderLeft(null));
			table.addCell(CommonMethodForPdf.createCell(tProfile.getTeacherEmployeeCode()==null?"":tProfile.getTeacherEmployeeCode()+"", 1, 1, 9f).setTextAlignment(TextAlignment.RIGHT).setBorderRight(null).setBorderLeft(null));
			table.addCell(new Cell( 1, 1).add((tProfile.getTeacherEmployeeCode()==null?imgUnCheckbox:imgCheckbox).setHeight(15f)).setTextAlignment(TextAlignment.LEFT).setBorderLeft(null));


			table.addCell(CommonMethodForPdf.createCellBold("Employee Disability:", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER).setBorderRight(null));
			table.addCell(CommonMethodForPdf.createCell(tProfile.getTeacherDisabilityYn()==null?"":tProfile.getTeacherDisabilityYn().equals("0")?"No":tProfile.getTeacherDisabilityYn().equals("1")? "Yes":"", 1, 1, 9f).setTextAlignment(TextAlignment.RIGHT).setBorderRight(null).setBorderLeft(null));
			table.addCell(new Cell( 1, 1).add((tProfile.getTeacherDisabilityYn()==null?imgUnCheckbox:imgCheckbox).setHeight(15f)).setTextAlignment(TextAlignment.LEFT).setBorderRight(null).setBorderLeft(null));
			
			table.addCell(CommonMethodForPdf.createCellBold("Experience Start Date Present KV:", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER).setBorderRight(null).setBorderLeft(null));
			table.addCell(CommonMethodForPdf.createCell(tProfile.getWorkExperienceWorkStartDatePresentKv()==null?"":tProfile.getWorkExperienceWorkStartDatePresentKv()+"", 1, 1, 9f).setTextAlignment(TextAlignment.RIGHT).setBorderRight(null).setBorderLeft(null));
			table.addCell(new Cell( 1, 1).add((tProfile.getWorkExperienceWorkStartDatePresentKv()==null?imgUnCheckbox:imgCheckbox).setHeight(15f)).setTextAlignment(TextAlignment.LEFT).setBorderLeft(null));
			
			table.addCell(CommonMethodForPdf.createCellBold("Appointed For Subject:", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER).setBorderRight(null));
			table.addCell(CommonMethodForPdf.createCell(tProfile.getWorkExperienceAppointedForSubject()==null?"":tProfile.getWorkExperienceAppointedForSubject()+"", 1, 1, 9f).setTextAlignment(TextAlignment.RIGHT).setBorderRight(null).setBorderLeft(null));
			table.addCell(new Cell( 1, 1).add((tProfile.getWorkExperienceAppointedForSubject()==null?imgUnCheckbox:imgCheckbox).setHeight(15f)).setTextAlignment(TextAlignment.LEFT).setBorderRight(null).setBorderLeft(null));

			table.addCell(CommonMethodForPdf.createCellBold("Position Held:", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER).setBorderRight(null).setBorderLeft(null));
			table.addCell(CommonMethodForPdf.createCell(tProfile.getLastPromotionPositionType()==null?"":tProfile.getLastPromotionPositionType()+"", 1, 1, 9f).setTextAlignment(TextAlignment.RIGHT).setBorderRight(null).setBorderLeft(null));
			table.addCell(new Cell( 1, 1).add((tProfile.getLastPromotionPositionType()==null?imgUnCheckbox:imgCheckbox).setHeight(15f)).setTextAlignment(TextAlignment.LEFT).setBorderLeft(null));
		} catch (Exception e) {
			e.printStackTrace();
			return table;
		}
		
		
		return table;
	}


}

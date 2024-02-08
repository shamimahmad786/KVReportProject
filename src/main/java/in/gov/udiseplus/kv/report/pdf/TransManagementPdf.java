package in.gov.udiseplus.kv.report.pdf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import in.gov.udiseplus.kv.report.bean.Experience;
import in.gov.udiseplus.kv.report.bean.TcDcPointResp;
import in.gov.udiseplus.kv.report.bean.TeacherProfileResponseData;
import in.gov.udiseplus.kv.report.bean.TransProfileV2;
import in.gov.udiseplus.kv.report.bean.TransProfileV2Resp;
import in.gov.udiseplus.kv.report.bean.TeacherConfirmation;
import in.gov.udiseplus.kv.report.utill.CommonMethodForPdf;
import in.gov.udiseplus.kv.report.utill.Constants;


@Component
public class TransManagementPdf {
	
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
	

	
	
	
	
	
	
	public ResponseEntity<?> downloadTransManagementPdf(Map<String, Object> payload, TransProfileV2Resp transProfileV2Obj, TcDcPointResp tcDcPointObj) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PdfWriter write = new PdfWriter(byteArrayOutputStream);
		write.setSmartMode(true);

		PdfDocument pdfDoc = new PdfDocument(write);
		pdfDoc.setDefaultPageSize(PageSize.A4);
		Document doc = new Document(pdfDoc);

		Color paraFColor1 = new DeviceRgb(165, 42, 42);
		Color paraFColor2 = new DeviceRgb(0, 0, 0);

		doc.add(CommonMethodForPdf.createParaGraphBold("Transfer Management", 0f, 20f, 25, paraFColor1, null, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.BOTTOM));

		if ((transProfileV2Obj != null && transProfileV2Obj.getResponse() != null) || tcDcPointObj != null) {
			float[] columnWidMainTab = { 1f };
			Table mainTable = new Table(UnitValue.createPercentArray(columnWidMainTab));
			mainTable.setWidth(UnitValue.createPercentValue(100));

			if (transProfileV2Obj != null && transProfileV2Obj.getResponse() != null) {
				Table table = getStationTransfer(doc, transProfileV2Obj);
				mainTable.addCell(new Cell(1, 1).add(table).setBorder(null));
				mainTable.addCell(CommonMethodForPdf.createCellBold("", 1, 1, 9f).setBorder(null).setPaddingBottom(10));
			}

			if (transProfileV2Obj != null && transProfileV2Obj.getResponse() != null) {
				Table table1 = getMiscellaneous(doc,transProfileV2Obj);
				mainTable.addCell(new Cell(1, 1).add(table1).setBorder(null));
				mainTable.addCell(CommonMethodForPdf.createCellBold("", 1, 1, 9f).setBorder(null).setPaddingBottom(10));
			}
			
			if(tcDcPointObj !=null ) {
				Table innerTable = new Table(UnitValue.createPercentArray(new float[] { 1f, .01f, 1f })).setWidth(UnitValue.createPercentValue(100));
				
				Table tableLeftDcCount = getDcCount(doc,tcDcPointObj);
				innerTable.addCell(new Cell(1, 1).add(tableLeftDcCount).setBorder(null));
				innerTable.addCell(new Cell(1, 1).setBorder(null));
				
				Table tableRightTCCount = getTCCount(doc,tcDcPointObj);
				innerTable.addCell(new Cell(1, 1).add(tableRightTCCount).setBorder(null)); 
				mainTable.addCell(new Cell(1, 1).add(innerTable).setBorder(null));
				mainTable.addCell(CommonMethodForPdf.createCellBold("", 1, 1, 9f).setBorder(null).setPaddingBottom(10));
			}
			
			if (transProfileV2Obj != null && transProfileV2Obj.getResponse() != null) {
				Table tableStation = getStation(doc,transProfileV2Obj,tcDcPointObj);
				mainTable.addCell(new Cell(1, 1).add(tableStation));
				mainTable.addCell(CommonMethodForPdf.createCellBold("", 1, 1, 9f).setBorder(null).setPaddingBottom(10));
				mainTable.addCell(CommonMethodForPdf.createCellBold("", 1, 1, 9f).setBorder(null).setPaddingBottom(50));
			}
			
			Table tableUndertaking = getUndertaking(doc);
			mainTable.addCell(new Cell(1, 1).add(tableUndertaking).setBorder(null));
			mainTable.addCell(CommonMethodForPdf.createCellBold("", 1, 1, 9f).setBorder(null));

			doc.add(mainTable);

		} else {
			doc.add(CommonMethodForPdf.createParaGraphBold("Data not avaliable", 50f, 0f, 25, paraFColor2, null, TextAlignment.CENTER));
		}
		doc.close();

		byte[] bytes = byteArrayOutputStream.toByteArray();
		try {
			bytes = addFooterAndPageNumbers(bytes, "BACD");
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=TransferManagement.pdf");
		//headers.add("Content-Disposition", "inline; filename=TransferManagement.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(bytes);

	}
	
 
	
	private Table getUndertaking(Document doc) {
		float[] columnWidths = {0.3f,10f};
		Table table = new Table(UnitValue.createPercentArray(columnWidths));
		table.setWidth(UnitValue.createPercentValue(100));
		
		try {
			
			InputStream is = getClass().getResourceAsStream("/static/images/un-checkbox.png");
			Image imgUnCheckbox = new Image(ImageDataFactory.create(inputStreamToByteArray(is)));
			
			InputStream is1 = getClass().getResourceAsStream("/static/images/checkbox.png");
			Image imgCheckbox = new Image(ImageDataFactory.create(inputStreamToByteArray(is1)));
			
			table.addCell(CommonMethodForPdf.createCellBold("Undertaking:", 2, 1, 11f).setTextAlignment(TextAlignment.LEFT).setBorder(null));
			
			
			table.addCell(new Cell( 1, 1).add((imgUnCheckbox).setHeight(10f)).setTextAlignment(TextAlignment.LEFT).setBorder(null));
			table.addCell(CommonMethodForPdf.createCell("I, the undersigned, certify that to the best of my knowledge and belief, this Profile Information, Qualifications, Experience etc. correctly describes the associated employee.", 1, 1, 10f).setTextAlignment(TextAlignment.LEFT).setBorder(null));
			
			table.addCell(new Cell( 1, 1).add((imgUnCheckbox).setHeight(10f)).setTextAlignment(TextAlignment.LEFT).setBorder(null));
			table.addCell(CommonMethodForPdf.createCell("I also declare that I have saved all the previous sections separately.", 1, 1, 10f).setTextAlignment(TextAlignment.LEFT).setBorder(null));
			

		} catch (Exception e) {
			e.printStackTrace();
			return table;
		}
		
		
		return table;
	}

	private Table getStation(Document doc, TransProfileV2Resp obj, TcDcPointResp tcDcPointObj) {
		float[] columnWidths = {1.5f,1f,0.5f  ,1.5f,1f,0.5f  ,1.5f,1f,0.5f};
		Table table = new Table(UnitValue.createPercentArray(columnWidths));
		table.setWidth(UnitValue.createPercentValue(100));
		
		try {
			
			InputStream is = getClass().getResourceAsStream("/static/images/un-checkbox.png");
			Image imgUnCheckbox = new Image(ImageDataFactory.create(inputStreamToByteArray(is)));
			
			InputStream is1 = getClass().getResourceAsStream("/static/images/checkbox.png");
			Image imgCheckbox = new Image(ImageDataFactory.create(inputStreamToByteArray(is1)));
			

			TransProfileV2 resp=obj.getResponse();

			table.addCell(CommonMethodForPdf.createCellBold("Station (I)", 1, 1, 9f).setBackgroundColor(new DeviceRgb(246,249,255)).setTextAlignment(TextAlignment.CENTER).setBorder(null));
			table.addCell(CommonMethodForPdf.createCell(resp.getChoiceKv1StationName()==null?"":resp.getChoiceKv1StationName()+" ("+resp.getChoiceKv1StationCode()+")", 1, 1, 9f).setTextAlignment(TextAlignment.RIGHT).setBorder(null));
			table.addCell(new Cell( 1, 1).add((resp.getChoiceKv1StationName()==null?imgUnCheckbox:imgCheckbox).setHeight(15f)).setTextAlignment(TextAlignment.LEFT).setBorder(null));

			table.addCell(CommonMethodForPdf.createCellBold("Station (II)", 1, 1, 9f).setBackgroundColor(new DeviceRgb(246,249,255)).setTextAlignment(TextAlignment.CENTER).setBorder(null));
			table.addCell(CommonMethodForPdf.createCell(resp.getChoiceKv2StationName()==null?"":resp.getChoiceKv2StationName()+" ("+resp.getChoiceKv1StationCode()+")", 1, 1, 9f).setTextAlignment(TextAlignment.RIGHT).setBorder(null));
			table.addCell(new Cell( 1, 1).add((resp.getChoiceKv2StationName()==null?imgUnCheckbox:imgCheckbox).setHeight(15f)).setTextAlignment(TextAlignment.LEFT).setBorder(null));
			
			table.addCell(CommonMethodForPdf.createCellBold("Station (III)", 1, 1, 9f).setBackgroundColor(new DeviceRgb(246,249,255)).setTextAlignment(TextAlignment.CENTER).setBorder(null));
			table.addCell(CommonMethodForPdf.createCell(resp.getChoiceKv3StationName()==null?"":resp.getChoiceKv3StationName()+" ("+resp.getChoiceKv1StationCode()+")", 1, 1, 9f).setTextAlignment(TextAlignment.RIGHT).setBorder(null));
			table.addCell(new Cell( 1, 1).add((resp.getChoiceKv3StationName()==null?imgUnCheckbox:imgCheckbox).setHeight(15f)).setTextAlignment(TextAlignment.LEFT).setBorder(null));
			
			table.addCell(CommonMethodForPdf.createCell("", 9, 1, 9f).setBorder(null));
			
			table.addCell(CommonMethodForPdf.createCellBold("Station (IV)", 1, 1, 9f).setBackgroundColor(new DeviceRgb(246,249,255)).setTextAlignment(TextAlignment.CENTER).setBorder(null));
			table.addCell(CommonMethodForPdf.createCell(resp.getChoiceKv4StationName()==null?"":resp.getChoiceKv4StationName()+"("+resp.getChoiceKv1StationCode()+")", 1, 1, 9f).setTextAlignment(TextAlignment.RIGHT).setBorder(null));
			table.addCell(new Cell( 1, 1).add((resp.getChoiceKv4StationName()==null?imgUnCheckbox:imgCheckbox).setHeight(15f)).setTextAlignment(TextAlignment.LEFT).setBorder(null));

			table.addCell(CommonMethodForPdf.createCellBold("Station (V)", 1, 1, 9f).setBackgroundColor(new DeviceRgb(246,249,255)).setTextAlignment(TextAlignment.CENTER).setBorder(null));
			table.addCell(CommonMethodForPdf.createCell(resp.getChoiceKv5StationName()==null?"":resp.getChoiceKv5StationName()+"("+resp.getChoiceKv1StationCode()+")", 1, 1, 9f).setTextAlignment(TextAlignment.RIGHT).setBorder(null));
			table.addCell(new Cell( 1, 1).add((resp.getChoiceKv5StationName()==null?imgUnCheckbox:imgCheckbox).setHeight(15f)).setTextAlignment(TextAlignment.LEFT).setBorder(null));
			
			table.addCell(CommonMethodForPdf.createCell("", 3, 1, 9f).setBorder(null));
			table.addCell(CommonMethodForPdf.createCell("", 9, 1, 9f).setBorder(null));
			
			
			if(tcDcPointObj !=null) {
				table.addCell(CommonMethodForPdf.createCellBold("Total DC Count", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER).setBorder(null));
				table.addCell(new Cell( 1, 1).add((tcDcPointObj.getDcTotalPoint()==null?imgUnCheckbox:imgCheckbox).setHeight(15f).setHorizontalAlignment(HorizontalAlignment.RIGHT)).setTextAlignment(TextAlignment.RIGHT).setBorder(null));
				table.addCell(CommonMethodForPdf.createCell(tcDcPointObj.getDcTotalPoint()==null?"":tcDcPointObj.getDcTotalPoint()+"", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT).setBorder(null));
				
				table.addCell(CommonMethodForPdf.createCellBold("Total TC Count", 1, 1, 9f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER).setBorder(null));
				table.addCell(new Cell( 1, 1).add((tcDcPointObj.getTcTotalPoint()==null?imgUnCheckbox:imgCheckbox).setHeight(15f).setHorizontalAlignment(HorizontalAlignment.RIGHT)).setTextAlignment(TextAlignment.RIGHT).setBorder(null));
				table.addCell(CommonMethodForPdf.createCell(tcDcPointObj.getTcTotalPoint()==null?"":tcDcPointObj.getTcTotalPoint()+"", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT).setBorder(null));
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return table;
		}
		
		
		return table;
	}

	private Table getTCCount(Document doc, TcDcPointResp obj) {

		float[] columnWidths = {.5f , 8f , 0.8f};
		Table table = new Table(UnitValue.createPercentArray(columnWidths));
		table.setWidth(UnitValue.createPercentValue(100));
		
		try {

			
			table.addCell(CommonMethodForPdf.createCellBold("TC Count", 3, 1, 10f).setBackgroundColor(new DeviceRgb(116,146,203)).setFontColor(DeviceRgb.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(null));
			
			
			table.addCell(CommonMethodForPdf.createCellBold("S.No.", 1, 1, 10f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCellBold("Question Description", 1, 1, 10f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCellBold("Points", 1, 1, 10f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));

			table.addCell(CommonMethodForPdf.createCellBold("1", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell("Continuous Active Stay at station as on 30th june for all stations excluding periods of absence (any kind of leave other than maternity Leave) of 30 days or more at normal station and 45 days or more at Hard/NER/Priorty stations, irrespective of cadre.", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			table.addCell(CommonMethodForPdf.createCellBold(obj.getTcStayAtStation()==null?"":obj.getTcStayAtStation()+"", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));

			table.addCell(CommonMethodForPdf.createCell("2", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCell("PwD employees", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCell(obj.getTcPeriodAbsence()==null?"":obj.getTcPeriodAbsence()+"", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			
			
			
			
			table.addCell(CommonMethodForPdf.createCellBold("3", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell("Completion of prescribed tenure in Hard/NER/Priority stations at present place of posting.", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			table.addCell(CommonMethodForPdf.createCellBold(obj.getTcTenureHardPoint()==null?"":obj.getTcTenureHardPoint()+"", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			
			table.addCell(CommonMethodForPdf.createCell("4", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCell("Clarification: If an employee qualifies on more than one ground, the points shall be limited to a maximum of (+) 35 only.", 1, 1, 9f)
					.add(new Paragraph("Further,if an employee has already secured a transfer in previous year(s) on the basis of these grounds,then points shall not be given again.").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)).setFontSize(9f).setPaddingTop(7f))
					.add(new Paragraph("a. Medical ground(MDG).").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(9f).setPaddingLeft(5f).setPaddingTop(7f))
					.add(new Paragraph("b. Death of Family person(DFP).").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(9f).setPaddingLeft(5f).setPaddingTop(7f))
					.setTextAlignment(TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCell(obj.getTcMdDfGroungPoint()==null?"":obj.getTcMdDfGroungPoint()+"", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			
			
			
			table.addCell(CommonMethodForPdf.createCellBold("5", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell("Less than three years to retire (LTR).", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			table.addCell(CommonMethodForPdf.createCellBold(obj.getTcLtrPoint()==null?"":obj.getTcLtrPoint()+"", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			
			table.addCell(CommonMethodForPdf.createCell("6", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCell("Single Parent.", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCell(obj.getTcSinglePoint()==null?"":obj.getTcSinglePoint()+"", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			
			
			
			
			table.addCell(CommonMethodForPdf.createCellBold("7", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell("Spouse if a KVS Employee and posted at the choice station.", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			table.addCell(CommonMethodForPdf.createCellBold(obj.getTcSpousePoint()==null?"":obj.getTcSpousePoint()+"", 1, 4, 9f).setTextAlignment(TextAlignment.CENTER));
			
			table.addCell(CommonMethodForPdf.createCell("8", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCell("Spouse if a Central Government/Central Govt. Autonomous body/Central Govt. Public Sector Undertaking/ Defence Employee and Central Armed Police Forces employee posted at the choice station.", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(246,249,255)));
			
			table.addCell(CommonMethodForPdf.createCellBold("9", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell("Spouse if a State Covernment / State Govt. Autonomous body / State Govt. Public Sectors Undertaking Employee & posted at the choice station.", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			
			table.addCell(CommonMethodForPdf.createCell("10", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCell("Woman employee not covered under Sl. No. 6,7,8,9 above.", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(246,249,255)));

			
			

			
			table.addCell(CommonMethodForPdf.createCellBold("11", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell("Members of recognized associations of KVS staff who are also members of JCM at KVS regional offices and / or KVS Headquarters. Note: - Benefit will be given only if they are posted in KVs located at the station of Regional Office/ ZIET.", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			table.addCell(CommonMethodForPdf.createCellBold(obj.getTcRjcmNjcmPoint()==null?"":obj.getTcRjcmNjcmPoint()+"", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setMinHeight(112f));
			

			table.addCell(CommonMethodForPdf.createCellBold("Total Transfer Counts", 2, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCellBold(obj.getTcTotalPoint()==null?"":obj.getTcTotalPoint()+"", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));

			
		} catch (Exception e) {
			e.printStackTrace();
			return table;
		}
		
		
		return table;
	
	}

	private Table getDcCount(Document doc, TcDcPointResp obj) {
		float[] columnWidths = {.5f , 8f , 0.8f};
		Table table = new Table(UnitValue.createPercentArray(columnWidths));
		table.setWidth(UnitValue.createPercentValue(100));
		
		try {

			
			table.addCell(CommonMethodForPdf.createCellBold("DC Count", 3, 1, 10f).setBackgroundColor(new DeviceRgb(116,146,203)).setFontColor(DeviceRgb.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(null));
			
			
			table.addCell(CommonMethodForPdf.createCellBold("S.No.", 1, 1, 10f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCellBold("Question Description", 1, 1, 10f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCellBold("Points", 1, 1, 10f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			

			table.addCell(CommonMethodForPdf.createCellBold("1", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell("Stay at a station as on 30th June in complete years irrespective of Cadre Clarification.", 1, 1, 9f)
					.add(new Paragraph("a. This should be line  2, and it is!").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(9f).setPaddingLeft(5f).setPaddingTop(7f))
					.add(new Paragraph("b. If an employee transferred from station \"A\" to station \"B\" returns to the station \"A\" on request without completing three years of stay at station \"B\" then the period of stay at station \"A\" will be calculated as total number of years served at station \"A\" prior to his posting at \"B\" and the number of years served after his return to station \"A\" taken together.").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(9f).setPaddingLeft(7f).setPaddingTop(7f))
					.setTextAlignment(TextAlignment.LEFT));
			table.addCell(CommonMethodForPdf.createCellBold(obj.getDcStayAtStation()==null?"":obj.getDcStayAtStation()+"", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));

			table.addCell(CommonMethodForPdf.createCell("2", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCell("Whether the employee below 40 years (as on 30th June of the year) has completed one tenure at Hard/NER/Priority station (during entire service).", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCell(obj.getDcTenureHardPoint()==null?"":obj.getDcTenureHardPoint()+"", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			
			
			
			
			table.addCell(CommonMethodForPdf.createCellBold("3", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell("PwD employees", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			table.addCell(CommonMethodForPdf.createCellBold(obj.getDcPeriodAbsence()==null?"":obj.getDcPeriodAbsence()+"", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			
			table.addCell(CommonMethodForPdf.createCell("4", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCell("Medical ground (MDG)/Death of family person (DFP)", 1, 1, 9f)
					.add(new Paragraph("a. Medical ground (MDG).").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(9f).setPaddingLeft(7f).setPaddingTop(7f))
					.add(new Paragraph("b. Death of Family person (DFP).").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(9f).setPaddingLeft(7f).setPaddingTop(7f))
					.setTextAlignment(TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCell(obj.getDcMdDfGroungPoint()==null?"":obj.getDcMdDfGroungPoint()+"", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			
			
			
			table.addCell(CommonMethodForPdf.createCellBold("5", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell("Less than three years to retire (LTR).", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			table.addCell(CommonMethodForPdf.createCellBold(obj.getDcLtrPoint()==null?"":obj.getDcLtrPoint()+"", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			
			table.addCell(CommonMethodForPdf.createCell("6", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCell("Single Parent.", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCell(obj.getDcSinglePoint()==null?"":obj.getDcSinglePoint()+"", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			
			
			
			
			table.addCell(CommonMethodForPdf.createCellBold("7", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell("Spouse if a KVS Employee and posted at the same station.", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			table.addCell(CommonMethodForPdf.createCellBold(obj.getDcSpousePoint()==null?"":obj.getDcSpousePoint()+"", 1, 4, 9f).setTextAlignment(TextAlignment.CENTER));
			
			table.addCell(CommonMethodForPdf.createCell("8", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCell("Spouse if a Central Government/Central body/Central Govt. Public Sector Undertaking/ Defence Employee and Central Armed Police Forces employee posted at the same station.", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(246,249,255)));
			
			table.addCell(CommonMethodForPdf.createCellBold("9", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell("Spouse if a State Covernment / State Govt. Autonomous body / State Govt. Public Sectors Undertaking Employee and posted at the same station.", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			
			table.addCell(CommonMethodForPdf.createCell("10", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCell("Woman employee not covered under Sl. No. 6,7,8,9 above.", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(246,249,255)));

			
			

			
			table.addCell(CommonMethodForPdf.createCellBold("11", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell("Members of recognized associations of KVS staff who are also members of JCM at KVS regional offices and / or KVS Headquarters. Note: - Benefit will be given only if they are posted in KVs located at the station of Regional Office/ ZIET.", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			table.addCell(CommonMethodForPdf.createCellBold(obj.getDcRjcmNjcmPoint()==null?"":obj.getDcRjcmNjcmPoint()+"", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			

			table.addCell(CommonMethodForPdf.createCellBold("Total Displacement Count", 2, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCellBold(obj.getDcTotalPoint()==null?"":obj.getDcTotalPoint()+"", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));

			
		} catch (Exception e) {
			e.printStackTrace();
			return table;
		}
		
		
		return table;
	}

	
	
	
	private Table getMiscellaneous(Document doc, TransProfileV2Resp obj) {
		float[] columnWidths = {.5f , 8f , 0.8f};
		Table table = new Table(UnitValue.createPercentArray(columnWidths));
		table.setWidth(UnitValue.createPercentValue(100));
		
		try {

			TransProfileV2 resp=obj.getResponse();
			table.addCell(CommonMethodForPdf.createCellBold("Miscellaneous", 3, 1, 11f).setBackgroundColor(new DeviceRgb(116,146,203)).setTextAlignment(TextAlignment.CENTER).setFontColor(DeviceRgb.WHITE));

			table.addCell(CommonMethodForPdf.createCellBold("1", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCellBold("Whether the employee is seeking benefit of spouse who is working at the same station where employee is posted/transfer is being sought for.", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			table.addCell(CommonMethodForPdf.createCellBold(resp.getSpouseKvsYnD()==null?"":resp.getSpouseKvsYnD()==1?"Yes":"No", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));

			
			table.addCell(CommonMethodForPdf.createCellBold("2", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCellBold("Whether the employee is seeking benefit of medical ground (MDG Ground).", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCellBold(resp.getPersonalStatusMdgD()==null?"":resp.getPersonalStatusMdgD()==1?"Yes":"No", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			
			
			table.addCell(CommonMethodForPdf.createCellBold("3", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCellBold("Whether the employee is seeking benefit of single parent (SP Ground).", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			table.addCell(CommonMethodForPdf.createCellBold(resp.getPersonalStatusSpD()==null?"":resp.getPersonalStatusSpD()==1?"Yes":"No", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			
			
			table.addCell(CommonMethodForPdf.createCellBold("4", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCellBold("Whether the employee is seeking benefit of Death of Family Person (DFP Ground).", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCellBold(resp.getPersonalStatusDfpD()==null?"":resp.getPersonalStatusDfpD()==1?"Yes":"No", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			
			
			table.addCell(CommonMethodForPdf.createCellBold("5", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCellBold("Whether your are main care-giver to the person with disability in the family (i.e spouse/son/daughter).", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			table.addCell(CommonMethodForPdf.createCellBold(resp.getCareGiverDisabilityName()==null?"":resp.getCareGiverDisabilityName(), 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			
			
			table.addCell(CommonMethodForPdf.createCellBold("6", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCellBold("Members of JCM at KVS Regional Office (RJCM) / KVS Headquarters (NJCM).", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCellBold(resp.getMemberJCM()==null?"":resp.getMemberJCM()==0?"None":"Yes", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			
			
			table.addCell(CommonMethodForPdf.createCellBold("7", 1, 1, 9f) .setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCellBold("Active stay (in years) refer 2 (i) of Part- 1 of Transfer Policy 2023.", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT));
			table.addCell(CommonMethodForPdf.createCellBold(resp.getTransferId()==null?"":resp.getTransferId()+"", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			
			
			table.addCell(CommonMethodForPdf.createCellBold("8", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCellBold("Whether disciplinary proceedings are in progress.", 1, 1, 9f).setTextAlignment(TextAlignment.LEFT).setBackgroundColor(new DeviceRgb(246,249,255)));
			table.addCell(CommonMethodForPdf.createCellBold(resp.getDisciplinaryYn()==null?"":resp.getDisciplinaryYn()==1?"Yes":"No", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(new DeviceRgb(246,249,255)));
			
			
			table.addCell(CommonMethodForPdf.createCellBold("9", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCellBold("Whether, the employee has completed one tenure at hard/NER/Priority station(during entire service).", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCellBold(resp.getSurveHardYn()==null?"":resp.getSurveHardYn()==1?"Yes":"No", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return table;
		}
		
		
		return table;
	}

	private Table getStationTransfer(Document doc, TransProfileV2Resp obj) throws IOException {
		float[] columnWidths = {1f , 1f , 1f ,1f , 1f };
		Table table = new Table(UnitValue.createPercentArray(columnWidths));
		table.setWidth(UnitValue.createPercentValue(100));
		
		try {
			
			table.addCell(CommonMethodForPdf.createCellBold("Choice Of Station (Transfer)", 6, 1, 11f).setBackgroundColor(new DeviceRgb(116,146,203)).setTextAlignment(TextAlignment.CENTER).setFontColor(DeviceRgb.WHITE));


			table.addCell(CommonMethodForPdf.createCellBold("Station name", 1, 1, 10f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCellBold("Station name", 1, 1, 10f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCellBold("Station name", 1, 1, 10f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCellBold("Station name", 1, 1, 10f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCellBold("Station name", 1, 1, 10f).setBackgroundColor(new DeviceRgb(229,238,255)).setTextAlignment(TextAlignment.CENTER));
			
			
			TransProfileV2 resp=obj.getResponse();
			table.addCell(CommonMethodForPdf.createCell(resp.getChoiceKv1StationName()==null?"":resp.getChoiceKv1StationName()+"("+resp.getChoiceKv1StationCode()+")", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell(resp.getChoiceKv2StationName()==null?"":resp.getChoiceKv2StationName()+"("+resp.getChoiceKv2StationCode()+")", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell(resp.getChoiceKv3StationName()==null?"":resp.getChoiceKv3StationName()+"("+resp.getChoiceKv3StationCode()+")", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell(resp.getChoiceKv4StationName()==null?"":resp.getChoiceKv4StationName()+"("+resp.getChoiceKv4StationCode()+")", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));
			table.addCell(CommonMethodForPdf.createCell(resp.getChoiceKv5StationName()==null?"":resp.getChoiceKv5StationName()+"("+resp.getChoiceKv5StationCode()+")", 1, 1, 9f).setTextAlignment(TextAlignment.CENTER));

			
		} catch (Exception e) {
			e.printStackTrace();
			return table;
		}
		
		
		return table;
	}
	
	


}

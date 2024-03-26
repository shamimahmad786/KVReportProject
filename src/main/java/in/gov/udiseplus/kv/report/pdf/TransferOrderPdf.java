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

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.itextpdf.io.font.PdfEncodings;
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
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.licensekey.LicenseKey;

import in.gov.udiseplus.kv.report.bean.ZEmpDetailsProj;
import in.gov.udiseplus.kv.report.utill.CommonMethodForPdf;
import in.gov.udiseplus.kv.report.utill.Constants;


@Component
public class TransferOrderPdf {
	
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

			/*new Canvas(pdfCanvas, pdfDoc, pageSize).setFont(font).setFontSize(9).setFontColor(new DeviceRgb(12, 49, 99))
			.showTextAligned("Generated on " + formattedDate, 403, 28, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0)
			.showTextAligned("https://kvsonlinetransfer.kvs.gov.in/", 403, 15, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);*/

			/*
			 * PdfCanvas canvasLogoTop = new PdfCanvas(page).saveState().setExtGState(new
			 * PdfExtGState()); try (Canvas canvas = new Canvas(canvasLogoTop, pdfDoc,
			 * pageSize)) { Image image = new
			 * Image(ImageDataFactory.create(inputStreamToByteArray(getClass().
			 * getResourceAsStream("/static/images/kvs-logo.png"))));
			 * image.setFixedPosition(32, 750); canvas.add(image); } catch (Exception e) {
			 * e.printStackTrace(); }finally { canvasLogoTop.restoreState(); }
			 */
			

			
			/*Image imagehead = null;
			if (i == 1) {
				try {
					InputStream is = getClass().getResourceAsStream("/static/images/kvs-logo.png");
					imagehead = new Image(ImageDataFactory.create(inputStreamToByteArray(is)));
					// imagehead.scaleAbsolute(100, 50);
					imagehead.setFixedPosition(32, 750);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}*/
			if (i == 1) {
				PdfCanvas canvasLogo = new PdfCanvas(page).saveState().setExtGState(new PdfExtGState());
				try (Canvas canvas = new Canvas(canvasLogo, pdfDoc, pageSize)) {
					Image image = new Image(ImageDataFactory.create(inputStreamToByteArray(getClass().getResourceAsStream("/static/images/kvs-logo.png"))));
					image.scaleAbsolute(50, 40);
					image.setFixedPosition(380, 547);
					canvas.add(image);
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					canvasLogo.restoreState();
				}
			}

		}

		pdfDoc.close();
		return baos.toByteArray();
	}
	

	
	
	
	
	
	
	public 	ResponseEntity<?>  downloadPdfTransferOrder(Integer orderType, String[] orderData, List<ZEmpDetailsProj> empDataList) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PdfWriter write = new PdfWriter(byteArrayOutputStream);
		write.setSmartMode(true);

		PdfDocument pdfDoc = new PdfDocument(write);
		pdfDoc.setDefaultPageSize(PageSize.A4.rotate());
		Document doc = new Document(pdfDoc);

		try {
			String itextkeyPath = new File(getClass().getResource("/static/itextkey1576128028874_0.xml").toURI()).getAbsolutePath();
			LicenseKey.loadLicenseFile(itextkeyPath);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		float[] columnWidMainTab = { 1f };
		Table mainTable = new Table(UnitValue.createPercentArray(columnWidMainTab));
		mainTable.setWidth(UnitValue.createPercentValue(100));
		

		Table tableHeader = getHeader(doc);
		mainTable.addCell(new Cell(1, 1).add(tableHeader).setBorder(null));
		

		if (orderData != null) {
			Table tableOrder = getTransferOrder(doc,orderData);
			mainTable.addCell(new Cell(1, 1).add(tableOrder).setBorder(null));

			
			Table tableEmpDetails = getEmpDetails(doc,empDataList);
			mainTable.addCell(new Cell(1, 1).add(tableEmpDetails).setBorder(null));
			
			
			Table tableDistribution = getDistribution(doc);
			mainTable.addCell(new Cell(1, 1).add(tableDistribution).setBorder(null));

			doc.add(mainTable);

		} else {
			doc.add(CommonMethodForPdf.createParaGraphBold("Data not avaliable", 50f, 0f, 25, new DeviceRgb(0, 0, 0), null, TextAlignment.CENTER));
		}
		doc.close();

		byte[] bytes = byteArrayOutputStream.toByteArray();
		
		try {
			bytes = addFooterAndPageNumbers(bytes, "BACD");
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		
        HttpHeaders headers = new HttpHeaders();
        //headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+orderData[3]+".pdf");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+orderData[3]+".pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        headers.setCacheControl("no-cache, no-store, must-revalidate");
        headers.setPragma("no-cache");
        headers.setExpires(0);
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(bytes);

	}
	


	private Table getHeader(Document doc) {
		float[] columnWidths = {1.5f,1.5f,0.8f};
		Table table = new Table(UnitValue.createPercentArray(columnWidths));
		table.setWidth(UnitValue.createPercentValue(100));

		try {
			String poppinsSemiBoldFontFile = new File(getClass().getResource("/static/font/Poppins-SemiBold.ttf").toURI()).getAbsolutePath();
			PdfFont fontPoppinsSemiBold= PdfFontFactory.createFont(poppinsSemiBoldFontFile ,PdfEncodings.IDENTITY_H,true);
			
			String poppinsMediumFontFile = new File(getClass().getResource("/static/font/Poppins-Medium.ttf").toURI()).getAbsolutePath();
			PdfFont fontPoppinsMedium= PdfFontFactory.createFont(poppinsMediumFontFile ,PdfEncodings.IDENTITY_H,true);
			
			table.addCell(new Cell(1,3)
				    .add(new Paragraph("केन्द्रीय विद्यालय संगठन/KENDRIYA VIDYALAYA SANGATHAN").setMargin(-2))
				    .add(new Paragraph("18, संस्थागत क्षेत्र , शहीद जीत सिंह मार्ग /18 INSTITUTIONAL AREA, SHAHEED JEET SINGH MARG,").setMargin(-5))
				    .add(new Paragraph("नई दिल्ली-110016 / NEW DELHI-110016").setMargin(-2))
				    .setFont(fontPoppinsSemiBold).setFontSize(11).setBorder(null).setTextAlignment(TextAlignment.CENTER).setUnderline().setPaddingTop(10)
				    );
		} catch (Exception e) {
			e.printStackTrace();
			return table;
		}
		
		
		return table;
	}

	
	private Table getTransferOrder(Document doc, String[] orderData) {
		float[] columnWidths = {2.5f,2f,1f};
		Table table = new Table(UnitValue.createPercentArray(columnWidths));
		table.setWidth(UnitValue.createPercentValue(100));

		
		try {
			String poppinsSemiBoldFontFile = new File(getClass().getResource("/static/font/Poppins-SemiBold.ttf").toURI()).getAbsolutePath();
			PdfFont fontPoppinsSemiBold= PdfFontFactory.createFont(poppinsSemiBoldFontFile ,PdfEncodings.IDENTITY_H,true);
			
			String poppinsMediumFontFile = new File(getClass().getResource("/static/font/Poppins-Medium.ttf").toURI()).getAbsolutePath();
			PdfFont fontPoppinsMedium= PdfFontFactory.createFont(poppinsMediumFontFile ,PdfEncodings.IDENTITY_H,true);
			
			String poppinsFileRegular = new File(getClass().getResource("/static/font/Poppins-Regular.ttf").toURI()).getAbsolutePath();
			PdfFont fontPoppinsRegular= PdfFontFactory.createFont(poppinsFileRegular ,PdfEncodings.IDENTITY_H,true);
			
			table.addCell(new Cell(1,2)
				    .add(new Paragraph(orderData[0]))
				    .setFont(fontPoppinsMedium).setFontSize(9f).setBorder(null).setTextAlignment(TextAlignment.LEFT)
				    );
			table.addCell(new Cell(1,1)
				    .add(new Paragraph("Date:  -- / -- / ---- "))
				    .setFont(fontPoppinsRegular).setFontSize(11f).setBorder(null).setTextAlignment(TextAlignment.CENTER)
				    );
			
			table.addCell(new Cell(1,3)
				    .add(new Paragraph("स्थानांतरण आदेश/TRANSFER ORDER"))
				    .setFont(fontPoppinsSemiBold).setFontSize(12).setBorder(null).setTextAlignment(TextAlignment.CENTER).setUnderline().setPaddingBottom(0)
				    );
			
			table.addCell(new Cell(1, 3)
				    .add(new Paragraph(orderData[1]).setFont(fontPoppinsRegular).setFontSize(9f))
				    .add(new Paragraph(orderData[2]).setFont(fontPoppinsRegular).setFontSize(9f))
				    .setBorder(null)
				    .setTextAlignment(TextAlignment.JUSTIFIED)
				    .setPaddingTop(5)
				    .setPaddingBottom(0) // Adjust the bottom padding to reduce space between paragraphs
				);
		} catch (Exception e) {
			e.printStackTrace();
			return table;
		}
		
		
		return table;
	}

		private Table getEmpDetails(Document doc, List<ZEmpDetailsProj> empDataList) {
			//float[] columnWidths = {.4f,.5f,1.5f,1.2f,.6f,  .53f,.6f,.4f,2f,.4f,  .53f,.6f,.4f,2f,.4f};
			
			float[] columnWidths = {.3f,.4f,1.5f,1.2f,.6f,  .38f,.38f,.33f,2.5f,.2f,  .38f,.38f,.33f,2.5f,.2f};
			Table table = new Table(UnitValue.createPercentArray(columnWidths));
			table.setWidth(UnitValue.createPercentValue(100)).setFixedLayout();
			
			try {
				Color bgHeadColor=new DeviceRgb(232,232,232);
				float fontSize=7f;
				PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
				CommonMethodForPdf.createDataCellTableHeadEveryPage(table,"SL No.", 1, 2, fontSize,bgHeadColor,TextAlignment.CENTER,null);
				CommonMethodForPdf.createDataCellTableHeadEveryPage(table,"EMP CODE", 1, 2, fontSize,bgHeadColor,TextAlignment.CENTER,null);
				CommonMethodForPdf.createDataCellTableHeadEveryPage(table,"EMPLOYEE NAME", 1, 2, fontSize,bgHeadColor,TextAlignment.CENTER,null);
				CommonMethodForPdf.createDataCellTableHeadEveryPage(table,"POST", 1, 2, fontSize,bgHeadColor,TextAlignment.CENTER,null);
				CommonMethodForPdf.createDataCellTableHeadEveryPage(table,"SUBJECT", 1, 2, fontSize,bgHeadColor,TextAlignment.CENTER,null);

				CommonMethodForPdf.createDataCellTableHeadEveryPageVeriticle(table,"REGION CODE", 1, 1, fontSize,bgHeadColor,TextAlignment.CENTER,null);
				CommonMethodForPdf.createDataCellTableHeadEveryPageVeriticle(table,"STATION CODE", 1, 1, fontSize,bgHeadColor,TextAlignment.CENTER,null);
				CommonMethodForPdf.createDataCellTableHeadEveryPageVeriticle(table,"KV CODE", 1, 1, fontSize,bgHeadColor,TextAlignment.CENTER,null);
				
				CommonMethodForPdf.createDataCellTableHeadEveryPage(table,"KV NAME", 1, 1, fontSize,bgHeadColor,TextAlignment.CENTER,null);
				CommonMethodForPdf.createDataCellTableHeadEveryPageVeriticle(table,"SHIFT", 1, 1, fontSize,bgHeadColor,TextAlignment.CENTER,null);

				CommonMethodForPdf.createDataCellTableHeadEveryPageVeriticle(table,"REGION CODE", 1, 1, fontSize,bgHeadColor,TextAlignment.CENTER,null);
				CommonMethodForPdf.createDataCellTableHeadEveryPageVeriticle(table,"STATION CODE", 1, 1, fontSize,bgHeadColor,TextAlignment.CENTER,null);
				CommonMethodForPdf.createDataCellTableHeadEveryPageVeriticle(table,"KV CODE", 1, 1, fontSize,bgHeadColor,TextAlignment.CENTER,null);
				
				CommonMethodForPdf.createDataCellTableHeadEveryPage(table,"KV NAME", 1, 1, fontSize,bgHeadColor,TextAlignment.CENTER,null);
				CommonMethodForPdf.createDataCellTableHeadEveryPageVeriticle(table,"SHIFT", 1, 1, fontSize,bgHeadColor,TextAlignment.CENTER,null);

				CommonMethodForPdf.createDataCellTableHeadEveryPage(table,"l-------TRANSFERRED FROM-----l", 5, 1, fontSize,bgHeadColor,TextAlignment.CENTER,null);
				CommonMethodForPdf.createDataCellTableHeadEveryPage(table,"l-------TRANSFERRED TO-----l", 5, 1, fontSize,bgHeadColor,TextAlignment.CENTER,null);



				
				
				float fData=7f;
				int srNo=1;
				for (ZEmpDetailsProj objList : empDataList) {
					table.addCell(CommonMethodForPdf.createCell(srNo+"",1,1,fData).setTextAlignment(TextAlignment.CENTER));
					table.addCell(CommonMethodForPdf.createCell(objList.getEmp_code()!=null? objList.getEmp_code():"",1,1,fData).setTextAlignment(TextAlignment.CENTER));
					table.addCell(CommonMethodForPdf.createCell(objList.getEmp_name()!=null? objList.getEmp_name().toUpperCase():"",1,1,fData));
					table.addCell(CommonMethodForPdf.createCell(objList.getPost_name()!=null? objList.getPost_name():"",1,1,fData));
					table.addCell(CommonMethodForPdf.createCell(objList.getSubject_name()!=null? objList.getSubject_name():"NOT APPLICABLE",1,1,fData));

					table.addCell(CommonMethodForPdf.createCell(objList.getRegion_code()!=null? objList.getRegion_code():"",1,1,fData).setTextAlignment(TextAlignment.CENTER));
					table.addCell(CommonMethodForPdf.createCell(objList.getPresent_station_code()!=null? objList.getPresent_station_code()+"":"",1,1,fData).setTextAlignment(TextAlignment.CENTER));
					table.addCell(CommonMethodForPdf.createCell(objList.getPresent_kv_code()!=null? objList.getPresent_kv_code()+"":"",1,1,fData).setTextAlignment(TextAlignment.CENTER));
					table.addCell(CommonMethodForPdf.createCell(objList.getKv_name_present()!=null? objList.getKv_name_present():"",1,1,6.5f));
					table.addCell(CommonMethodForPdf.createCell(objList.getShift()!=null? objList.getShift()+"":"",1,1,fData).setTextAlignment(TextAlignment.CENTER));
					
					table.addCell(CommonMethodForPdf.createCell(objList.getRegion_code_alloted()!=null? objList.getRegion_code_alloted():"",1,1,fData).setTextAlignment(TextAlignment.CENTER));
					table.addCell(CommonMethodForPdf.createCell(objList.getAllot_stn_code()!=null? objList.getAllot_stn_code()+"":"",1,1,fData).setTextAlignment(TextAlignment.CENTER));
					table.addCell(CommonMethodForPdf.createCell(objList.getAllot_kv_code()!=null? objList.getAllot_kv_code()+"":"",1,1,fData).setTextAlignment(TextAlignment.CENTER));
					table.addCell(CommonMethodForPdf.createCell(objList.getKv_name_alloted()!=null? objList.getKv_name_alloted():"",1,1,6.5f));
					table.addCell(CommonMethodForPdf.createCell(objList.getAllot_shift()!=null? objList.getAllot_shift()+"":"",1,1,fData).setTextAlignment(TextAlignment.CENTER));
					
					srNo++;
					
				}

			} catch (Exception e) {
				e.printStackTrace();
				return table;
			}
			return table;
		}
		
	private Table getDistribution(Document doc) {
		float[] columnWidths = {0.3f,10f};
		Table table = new Table(UnitValue.createPercentArray(columnWidths));
		table.setWidth(UnitValue.createPercentValue(100));
		
		try {
			String poppinsSemiBoldFontFile = new File(getClass().getResource("/static/font/Poppins-SemiBold.ttf").toURI()).getAbsolutePath();
			PdfFont fontPoppinsSemiBold= PdfFontFactory.createFont(poppinsSemiBoldFontFile ,PdfEncodings.IDENTITY_H,true);
			
			
			String poppinsMediumFontFile = new File(getClass().getResource("/static/font/Poppins-Medium.ttf").toURI()).getAbsolutePath();
			PdfFont fontPoppinsMedium= PdfFontFactory.createFont(poppinsMediumFontFile ,PdfEncodings.IDENTITY_H,true);
			
			table.addCell(new Cell(1, 2)
				    .add(new Paragraph("यह आदेश केविसं के सक्षम प्राधिकारी के अनुमोदन से जारी किए जाते हैं। / This issues with the approval of the Competent Authority of KVS."))
				    .setFont(fontPoppinsMedium).setFontSize(9f).setBorder(null).setTextAlignment(TextAlignment.JUSTIFIED).setPaddingTop(10)
				);
			
			table.addCell(new Cell(1, 2)
				    .add(new Paragraph("(दीपक कुमार डबराल)").setMargin(-5))
				    .add(new Paragraph("(Deepak Kumar Dabral)").setMargin(-5))
				    .add(new Paragraph("सहायक आयुक्त(स्था-II/III)").setMargin(-5))
				    .add(new Paragraph("Assistant Commissioner (Estt.II/III)").setMargin(-5))
				    .setFont(fontPoppinsMedium).setFontSize(11f).setBorder(null).setTextAlignment(TextAlignment.RIGHT)
				);
			
			table.addCell(new Cell(1, 2)
				    .add(new Paragraph("वितरण/Distribution:-"))
				    .setFont(fontPoppinsSemiBold).setFontSize(11f).setBorder(null).setTextAlignment(TextAlignment.JUSTIFIED)
			);
			table.addCell(new Cell(1,2)
				    .add(new Paragraph("1. संबंधित कर्मचारी/ The Individual concerned."))
				    .add(new Paragraph("2. संबंधित प्राचार्य को इन निर्देशों के साथ कि वे संबंधित कर्मचारी को तत्काल कार्यमुक्त करें। उन्हें यह भी निर्देशित किया जाता है कि वे संबंधित कर्मचारी को कार्यमुक्त करने/ कार्यभार ग्रहण करने संबंधी जानकारी अपने क्षेत्रीय कार्यालय को शीघ्र दें। / The Principal concerned with the direction to relieve the employee immediately. They are also directed to intimate to RO concerned about relieving/joining of the employee."))
				    .add(new Paragraph("3. उपायुक्त, केविसं, संबंधित क्षेत्रीय कार्यालय को सूचना एवं आवश्यक कार्रवाई हेतु।/ Deputy Commissioner, KVS RO concerned for information and necessary action."))
				    .add(new Paragraph("4. संबंधित क्षेत्रीय कार्यालय के वित्त अधिकारी को आवश्यक कार्रवाई हेतु। / Finance Officer of the region concerned for necessary action."))
				    
				    .add(new Paragraph("5. केविसं के मान्यता प्राप्त सभी एसोसिएशनों के महासचिव। / General Secy. of the recognized associations of KVS."))
				    .add(new Paragraph("6. उपायुक्त (शै0/ई.डी.पी), के.वि.सं (मु.) को इस निवेदन के साथ कि वे इस स्थानांतरण आदेश की प्रतिलिपि के.वि.सं. मु. की वेबसाइट पर अपलोड करवाएं। / Deputy Commissioner (Acad./EDP), KVS HQ with the request to upload a copy of this transfer order on KVS HQ website."))
				    .add(new Paragraph("7. गार्ड फाइल। / Guard File."))
				    .setFont(fontPoppinsMedium).setFontSize(8f).setBorder(null).setTextAlignment(TextAlignment.JUSTIFIED)
				);

		} catch (Exception e) {
			e.printStackTrace();
			return table;
		}
		
		
		return table;
	}

	
	
	


}

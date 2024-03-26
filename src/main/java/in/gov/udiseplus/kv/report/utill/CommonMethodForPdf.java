package in.gov.udiseplus.kv.report.utill;

import java.io.IOException;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.BaseDirection;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.splitting.ISplitCharacters;

public class CommonMethodForPdf {

		//methods for table header And Every Page header-------------------
		public static void createDataCellTableHead(Table table, String content, int colspan, int rowspan,float fontSIze,Color bgColor,TextAlignment textAlignment) throws IOException {
			PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
			Cell cell = new Cell(rowspan, colspan).add(new Paragraph(content).setFont(font).setFontSize(fontSIze));
			cell.setTextAlignment(textAlignment);
			cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
			cell.setPadding(0);
			Color fcolor = new DeviceRgb(255,255,255);
			cell.setFontColor(fcolor);
			Border borderColor = new SolidBorder(new DeviceRgb(239, 239, 239), 1);
			cell.setBorder(borderColor);
			if(bgColor !=null)
				cell.setBackgroundColor(bgColor);
			table.addCell(cell);

		}
		
		public static void createDataCellTableHeadEveryPage(Table table, String content, int colspan, int rowspan,float fontSIze,Color bgColor,TextAlignment textAlignment,Color fcolor) throws IOException {
			PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
			Cell cell = new Cell(rowspan, colspan).add(new Paragraph(content).setFont(font).setFontSize(fontSIze));
			cell.setMinHeight(14);
			cell.setTextAlignment(textAlignment);
			cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
			//Color fcolor = new DeviceRgb(255,255,255);
			if(fcolor !=null)
				cell.setFontColor(fcolor);
			//Border borderColor = new SolidBorder(new DeviceRgb(239, 239, 239), 1);
			//cell.setBorder(borderColor);
			if(bgColor !=null)
				cell.setBackgroundColor(bgColor);
			table.addHeaderCell(cell);
		}
		
		public static void createDataCellTableHeadEveryPageVeriticle(Table table, String content, int colspan, int rowspan,float fontSIze,Color bgColor,TextAlignment textAlignment,Color fcolor) throws IOException {
			PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
			Cell cell = new Cell(rowspan, colspan).add(new Paragraph(content).setFont(font).setFontSize(fontSIze));
			cell.setTextAlignment(textAlignment);
			cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
			
			ISplitCharacters noSplit = (text, glyphPos) -> false;
			cell.setRotationAngle(Math.PI/2).setSplitCharacters(noSplit);
			
			cell.setPadding(.5f);
			
			
			
			//cell.setFixedPosition(left, bottom, width);
			
			//cell.setFixedPosition(40, 11, 25);
			

			if(fcolor !=null)
				cell.setFontColor(fcolor);

			if(bgColor !=null)
				cell.setBackgroundColor(bgColor);
			table.addHeaderCell(cell);
		}
		//----------------------------------------------
		
		
		
		//methods for table Body  Bold And Non Bold--------------------
		public static Cell createCell(String content, int colspan, int rowspan,float fontSize) throws IOException {
			PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
			Cell cell = new Cell(rowspan, colspan).add(new Paragraph(content).setFont(font).setFontSize(fontSize));
			//cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
			Border borderColor = new SolidBorder(new DeviceRgb(111,107,107), 0.5f);
			cell.setBorder(borderColor);
			return cell;
		}
		public static Cell createCellBold(String content, int colspan, int rowspan,float fontSize) throws IOException {
			PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
			Cell cell = new Cell(rowspan, colspan).add(new Paragraph(content).setFont(font).setFontSize(fontSize));
			//cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
			Border borderColor = new SolidBorder(new DeviceRgb(111,107,107), 0.5f);
			cell.setBorder(borderColor);
			return cell;
		}
		
		public static void createDataCellBold(Table table, String content, int colspan, int rowspan,float fontSize,Color bgColor,TextAlignment textAlignment) throws IOException {
			PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
			Cell cell = new Cell(rowspan, colspan).add(new Paragraph(content).setFont(font).setFontSize(fontSize));
			cell.setTextAlignment(textAlignment);
			//cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
			Border borderColor = new SolidBorder(new DeviceRgb(111,107,107), 0.5f);
			cell.setBorder(borderColor);
			if(bgColor !=null) {
				cell.setBackgroundColor(bgColor);
			}
			table.addCell(cell);
		}
		//----------------------------------------------
		
		
		
		
		
		
		//methods for Paragraph  Bold And Non Bold--------------------
		public static Paragraph createParaGraph(String text, float paddingTop, float paddingBottom, int fontSize,Color fColor,Color bgColor,TextAlignment textAlignment) throws IOException {
			Paragraph para = new Paragraph(text);
			para.setPaddingTop(paddingTop);
			para.setPaddingBottom(paddingBottom);
			para.setFontSize(fontSize);
			para.setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA));
			para.setTextAlignment(textAlignment);
			para.setFontColor(fColor);
			if(bgColor !=null)
				para.setBackgroundColor(fColor);
			return para;
		}
		public static Paragraph createParaGraphBold(String text, float paddingTop, float paddingBottom, int fontSize,Color fColor,Color bgColor,TextAlignment textAlignment) throws IOException {
			Paragraph para = new Paragraph(text);
			para.setPaddingTop(paddingTop);
			para.setPaddingBottom(paddingBottom);
			para.setFontSize(fontSize);
			para.setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD));
			para.setTextAlignment(textAlignment);
			para.setFontColor(fColor);
			if(bgColor !=null)
				para.setBackgroundColor(fColor);
			return para;
		}
		//---------------------------
		
		public static void createDataCellBoldWithBackGroundColor(Table table, String content, int colspan, int rowspan, PdfFont f) {

			// PdfFont f = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);
			Cell cell = new Cell(rowspan, colspan).add(new Paragraph(content).setFont(f).setFontSize(10));
			cell.setMinHeight(14);
			cell.setTextAlignment(TextAlignment.CENTER);
			Color color = new DeviceRgb(247,246 ,231);
			cell.setBackgroundColor(color);
			cell.setBold();
			table.addCell(cell);
		}

}

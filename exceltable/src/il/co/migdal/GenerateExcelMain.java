package il.co.migdal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import il.co.migdal.beans.ExcelDataTable;
import il.co.migdal.beans.ExcelTableRow;

public class GenerateExcelMain {

	//private static String[] columns = {"��", "�����", "����� ����", "������"};
	
	
	public static void main(String[] args) {
		//ExcelDataTable dataTable = initData();
		try {
			addRowToTable();
			
			//generateTable(dataTable);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static void addRowToTable() throws IOException {
		FileInputStream file = new FileInputStream(new File("d:/temp/template.xlsx"));
		Workbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheetAt(0);
		Row dataRow = null;
	
		for (Row row : sheet) {
			
			dataRow = findTheStartRowData(row);
			if(dataRow != null) {
				break;
			}
		}
		
		if(dataRow == null) {
			return;
		}
		Integer startRowIndex = dataRow.getRowNum();
		CellStyle  cellStyle = dataRow.getRowStyle();
		for(int i=startRowIndex; i < startRowIndex+ 5; i++) {
			sheet.shiftRows(i,  i+ 1, 1);
			Row newRow = fillRowdata(sheet, dataRow);
			newRow.setRowStyle(cellStyle);
		}
		
		
		sheet.removeRow(dataRow);
		
		
		Date date = new Date();
		 FileOutputStream fileOut = new FileOutputStream("D:\\temp\\" + date.getTime() +".xlsx");
	     workbook.write(fileOut);
	     fileOut.close();

	     // Closing the workbook
	     workbook.close();
	}
	
	private static Row fillRowdata(Sheet sheet, Row dataRow) {
		Row newRow = sheet.createRow(dataRow.getRowNum()-1);
		
		for (Cell cell : dataRow) {
			Cell newcell =newRow.createCell(cell.getColumnIndex());
			newcell.setCellValue("temp");
			newcell.setCellStyle(cell.getCellStyle());
		}
		return newRow;
	}


	private static Row findTheStartRowData(Row row ) {
		for (Cell cell : row) {
			if(cell.getCellTypeEnum() != CellType.STRING) {
				continue;
				
			 }
			if(StringUtils.equals(cell.getStringCellValue(), "<startHere>")) {
				return row;
			}
		}
		return null;
	}
	
	
	private static ExcelDataTable initData() {
		ExcelDataTable dataTable = new ExcelDataTable();
//		 
//        for(int i = 0; i < columns.length; i++) {
//        	dataTable.getColumnHeaders().add(columns[i]);
//        }
        ExcelTableRow excelTableRow = new ExcelTableRow();
  //      excelTableRow.getRowDataList().add("����");
  //      excelTableRow.getRowDataList().add("���"); 
        excelTableRow.getRowDataList().add(new Date());
        excelTableRow.getRowDataList().add(Double.parseDouble("1005.34"));
        dataTable.getRowDataList().add(excelTableRow);
        
        
		return dataTable;
	}


	private static void generateTable(ExcelDataTable dataTable) throws IOException {
		
		// TODO Auto-generated method stub
		Workbook workbook = new XSSFWorkbook();
		 /* CreationHelper helps us create instances of various things like DataFormat, 
        Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
		//CreationHelper createHelper = workbook.getCreationHelper();
		Sheet sheet = workbook.createSheet("Employee");
		sheet.setRightToLeft(true);
		 // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        
        
        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
		headerCellStyle.setBorderBottom(BorderStyle.MEDIUM );
		headerCellStyle.setBorderTop(BorderStyle.MEDIUM );
		headerCellStyle.setBorderLeft(BorderStyle.MEDIUM );
		headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
		
		
		
        Row headerRow = sheet.createRow(0);
        Integer index = 0;
        for(String header : dataTable.getColumnHeaders()) {
            Cell cell = headerRow.createCell(index++);
            cell.setCellValue(header);
            cell.setCellStyle(headerCellStyle);
        }
        
        
        int rowNum = 1;
        for(ExcelTableRow excelTableRow : dataTable.getRowDataList()) {
        	Row row = sheet.createRow(rowNum++);
        	int idxCol =0;
        	for(Object dataCol : excelTableRow.getRowDataList()) {
        		addRowCell(workbook, row, idxCol++, dataCol);
        	}
        	
        }
        
     // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("D:\\temp\\poi-generated-file.xlsx");
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();

	}


	private static void addRowCell(Workbook workbook, Row row, int idxCol, Object dataCol) {
		final CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd-MM-yyyy"));
       
        final CellStyle doubleCellStyle = workbook.createCellStyle();
        doubleCellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
		if(dataCol instanceof String) {
			row.createCell(idxCol).setCellValue(dataCol.toString());
			return;
		}
		if(dataCol instanceof Date) {
			Cell dateOfBirthCell = row.createCell(idxCol);
			dateOfBirthCell.setCellValue((Date)dataCol);
			dateOfBirthCell.setCellStyle(dateCellStyle);
			return;
		}
		
		if(dataCol instanceof Double) {
			Cell doubleCell = row.createCell(idxCol);
			doubleCell.setCellValue((Double)dataCol);
			doubleCell.setCellStyle(doubleCellStyle);
			return;
		}
		
	}
}

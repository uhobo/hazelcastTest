package il.co.migdal.beans;

import java.util.ArrayList;
import java.util.List;

public class ExcelDataTable {
	private List<String> columnHeaders = new ArrayList<String>();

	private List<ExcelTableRow> rowDataList = new ArrayList<ExcelTableRow>();
	public List<String> getColumnHeaders() {
		return columnHeaders;
	}

	public void setColumnHeaders(List<String> columnHeaders) {
		this.columnHeaders = columnHeaders;
	}

	public List<ExcelTableRow> getRowDataList() {
		return rowDataList;
	}

	public void setRowDataList(List<ExcelTableRow> rowDataList) {
		this.rowDataList = rowDataList;
	}
	
	
	
	
}

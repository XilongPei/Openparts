package com.cnpc.framework.utils;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.CellRangeAddress;

public final class ExcelUtil {

	/**
	 * 合并单元格（行合并），合并规则：内容相同则合并
	 * 
	 * @param sheet	       合并的sheet
	 * @param colindex      合并列索引
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static HSSFSheet rowSpan(HSSFSheet sheet, int colindex, int contentBeginIndex) {
		// 总行数
		int rowNum = sheet.getLastRowNum();
		HSSFRow row = sheet.getRow(1);
		// 正文内容应该从第二行开始,第一行为表头的标题
		int startRow = contentBeginIndex;
		String startValue = "";
		for (int i = contentBeginIndex; i <= rowNum; i++) {
			row = sheet.getRow(i);
			String value = row.getCell(colindex).getRichStringCellValue().getString();
			if (i == contentBeginIndex) {
				startValue = value;
				continue;
			}
			if (StrUtil.isNotBlank(startValue) && StrUtil.isNotBlank(value) && startValue.equals(value)) {
				if (i == rowNum) 
					sheet.addMergedRegion(new CellRangeAddress(startRow, i , colindex, colindex));
				else
					continue;
			} else {
				if((i-1)>startRow)
					sheet.addMergedRegion(new CellRangeAddress(startRow, i - 1, colindex, colindex));
				startRow = i;
			}
			startValue=value;
		}
		return sheet;
	}
}

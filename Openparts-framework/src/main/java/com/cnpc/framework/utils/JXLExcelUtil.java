package com.cnpc.framework.utils;

import jxl.Cell;
import jxl.write.WritableSheet;

public final class JXLExcelUtil {

	/**
	 * 合并单元格（行合并），合并规则：内容相同则合并
	 * 
	 * @param sheet	       合并的sheet
	 * @param colindex      合并列索引
	 *                      @param contentBeginIndex 正文开始行
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static WritableSheet rowSpan(WritableSheet sheet, int colindex, int contentBeginIndex) throws Exception {
		// 总行数
		int rowNum = sheet.getRows();
		Cell[] cells = sheet.getRow(1);
		// 正文内容应该从第二行开始,第一行为表头的标题
		int startRow = contentBeginIndex;
		String startValue = "";
		for (int i = contentBeginIndex; i <= rowNum; i++) {
			cells = sheet.getRow(i);
			String value = cells[colindex].getContents();
			if (i == contentBeginIndex) {
				startValue = value;
				continue;
			}
			if (StrUtil.isNotBlank(startValue) && StrUtil.isNotBlank(value) && startValue.equals(value)) {
				if (i == rowNum) //sheet.mergeCells(int col1,int row1,int col2,int row2);//左上角到右下角
					sheet.mergeCells(colindex,startRow, colindex,i);
				else
					continue;
			} else {
				if((i-1)>startRow)
					sheet.mergeCells(colindex,startRow, colindex,i - 1);
				startRow = i;
			}
			startValue=value;
		}
		return sheet;
	}
}

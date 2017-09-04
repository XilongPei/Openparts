package com.cnpc.framework.query.util;

import com.cnpc.framework.exception.QueryException;
import com.cnpc.framework.query.entity.Column;
import com.cnpc.framework.query.entity.Query;
import com.cnpc.framework.query.entity.QueryCondition;
import com.cnpc.framework.utils.*;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.*;
import jxl.write.Number;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by billJiang on 2017/1/22.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 * 导出数据列表重构
 */
public class ExportUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExportUtil.class);

    private static final WritableFont FONT_BODY = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD, false);

    // 定义标题样式
    private static final WritableCellFormat ft_title = ExportUtil.getTitleFormat();
    // 定义表头样式
    private static final WritableCellFormat ft_head = ExportUtil.getHeaderFormat();
    // 定义居左样式
    private static final WritableCellFormat ft_left = ExportUtil.getLeftFormat();
    // 定义居左样式
    private static final WritableCellFormat ft_right = ExportUtil.getRightFormat();
    // 定义居中样式
    private static final WritableCellFormat ft_center = ExportUtil.getCenterFormat();
    // 定义小数数字样式
    private static final WritableCellFormat ft_decimal = ExportUtil.getDecimalFormat();
    // 定义整数数字样式
    private static final WritableCellFormat ft_intNumber = ExportUtil.getIntegerFormat();

    /**
     * 通用的样式设置
     *
     * @param format    单元格样式
     * @param alignment 水平对齐方式
     * @return 通用的样式设置
     */
    private static WritableCellFormat getCellFormat(WritableCellFormat format, Alignment alignment) {

        try {
            format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
            format.setAlignment(alignment);
            format.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
            format.setBorder(Border.LEFT, BorderLineStyle.THIN);
            format.setBorder(Border.RIGHT, BorderLineStyle.THIN);
            format.setBorder(Border.TOP, BorderLineStyle.THIN);
        } catch (WriteException e) {
            return format;
        }
        return format;

    }

    /**
     * 标题样式
     *
     * @return 标题样式
     */
    private static WritableCellFormat getTitleFormat() {
        WritableFont titleFont = new WritableFont(WritableFont.TIMES, 18, WritableFont.BOLD, false);
        WritableCellFormat title = new WritableCellFormat(titleFont);
        title = getCellFormat(title, Alignment.CENTRE);
        try {
            title.setWrap(true);
        } catch (WriteException e) {
            return title;
        }
        return title;
    }

    /**
     * 表头样式
     */
    private static WritableCellFormat getHeaderFormat() {
        WritableFont headFont = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false);
        WritableCellFormat head = new WritableCellFormat(headFont);
        head = getCellFormat(head, Alignment.CENTRE);
        return head;
    }

    /**
     * 居左样式
     */
    private static WritableCellFormat getLeftFormat() {
        WritableCellFormat left = new WritableCellFormat(FONT_BODY);
        left = getCellFormat(left, Alignment.LEFT);
        try {
            left.setWrap(true);
        } catch (WriteException ex) {
            return left;
        }
        return left;
    }

    /**
     * 居中样式
     */
    private static WritableCellFormat getCenterFormat() {
        WritableCellFormat center = new WritableCellFormat(FONT_BODY);
        center = getCellFormat(center, Alignment.CENTRE);
        return center;
    }

    /**
     * 居右样式
     */
    private static WritableCellFormat getRightFormat() {
        WritableCellFormat left = new WritableCellFormat(FONT_BODY);
        left = getCellFormat(left, Alignment.RIGHT);
        try {
            left.setWrap(true);
        } catch (WriteException ex) {
            return left;
        }
        return left;
    }

    /**
     * 数字样式
     */
    private static WritableCellFormat getNumberFormat(String format) {
        NumberFormat nf = new NumberFormat(format);
        WritableCellFormat number = new WritableCellFormat(nf);
        number.setFont(FONT_BODY);
        number = getCellFormat(number, Alignment.CENTRE);
        return number;
    }

    /**
     * 小数样式
     */
    private static WritableCellFormat getDecimalFormat() {
        return getNumberFormat("#.##");
    }

    /**
     * 整数样式
     */
    private static WritableCellFormat getIntegerFormat() {
        return getNumberFormat("#");
    }


    //向excel中写入内容

    /**
     * 是否需要导出
     */
    private static boolean isExport(Column column) {
        //服务器端条件列和隐藏列不导出
        if (column.getIsServerCondition() || column.getHidden())
            return false;
        if (column.getIsExport() || column.getIsJustExport())
            return true;
        return true;
    }

    /**
     * 写入标题/副标题
     *
     * @param queryCondition 查询条件（含有标题/副标题信息）
     * @param query          查询配置
     * @param sheet          sheet页
     * @throws QueryException 执行异常
     */
    public static void writeTitle(QueryCondition queryCondition, Query query, WritableSheet sheet) throws QueryException {
        try {
            //写标题
            Label title = new Label(0, 0, StrUtil.isEmpty(queryCondition.getSheetTitle()) ?
                    queryCondition.getSheetName() : queryCondition.getSheetTitle(), ft_title);
            //合并标题单元格
            int lastCol = -1;
            List<Column> colList = query.getColumnList();
            for (Column column : colList) {
                if (ExportUtil.isExport(column))
                    lastCol++;
            }
            if (lastCol == -1) {
                throw new QueryException(query.getId() + "的xml未配置任何可导出的列，请检查");
            }

            sheet.addCell(title);
            //行高自动调整
            sheet.setRowView(0, 1500);
            //左上角到右下角
            // sheet.mergeCells(int col1,int row1,int col2,int row2);
            sheet.mergeCells(0, 0, lastCol, 0);

            //写副标题
            if (!StrUtil.isEmpty(queryCondition.getSheetSubTitle())) {
                Label subTitle = new Label(0, 1, queryCondition.getSheetSubTitle(), ft_left);
                sheet.addCell(subTitle);
                sheet.setRowView(1, 600, false);
                sheet.mergeCells(0, 1, lastCol, 1);
            }


        } catch (WriteException ex) {
            throw new QueryException("写入标题时候发生异常：异常信息为：" + ex.getMessage());
        }

    }

    /**
     * 返回表头，所在的位置
     *
     * @param queryCondition 查询条件（含标题/副标题信息）
     * @return 位置
     */
    private static int getTitleIndex(QueryCondition queryCondition) {
        int index = 1;
        if (!StrUtil.isEmpty(queryCondition.getSheetSubTitle()))
            index = 2;
        return index;
    }

    /**
     * 写入表头
     *
     * @param queryCondition 查询条件（含有标题/副标题信息）
     * @param query          查询配置
     * @param sheet          sheet页
     * @throws QueryException 执行异常
     */
    public static void writeHeader(QueryCondition queryCondition, Query query, WritableSheet sheet) throws QueryException {
        int headIndex = getTitleIndex(queryCondition);
        try {
            sheet.setRowView(headIndex, 600, false);
            List<Column> colList = query.getColumnList();
            Label head;//表头文本
            int ignoreCount = 0; //忽略的列数量
            //TODO 此处以后可以扩展多表头
            for (int h = 0; h < colList.size(); ++h) {
                Column column = colList.get(h);
                if (isExport(column)) {
                    // 创建表头列
                    head = new Label(h - ignoreCount, headIndex, column.getHeader(), ft_head);
                    sheet.addCell(head);
                    if (column.getHeader().getBytes().length > sheet.getColumnWidth(h - ignoreCount))
                        sheet.setColumnView(h - ignoreCount, column.getHeader().getBytes().length + 2);
                } else {
                    ++ignoreCount;
                }

            }
        } catch (WriteException ex) {
            throw new QueryException("写入数据表头发生异常，异常信息：" + ex.getMessage());
        }

    }

    /**
     * 写入数据
     *
     * @param queryCondition 查询条件（含有标题/副标题信息）
     * @param query          查询配置
     * @param sheet          sheet页
     * @param objList        待写入的数据
     * @param objClass       映射的实体类
     * @throws QueryException 执行异常
     */
    public static void writeData(QueryCondition queryCondition, Query query, WritableSheet sheet, List objList, Class objClass) throws QueryException {
        int headIndex = getTitleIndex(queryCondition);
        try {
            int rowLen = objList.size();
            List<Column> colList = query.getColumnList();
            int colLen = colList.size();
            for (int rowIndex = 1; rowIndex <= rowLen; ++rowIndex) {
                // 创建一行数据列
                sheet.setRowView(rowIndex + headIndex, 500);
                //忽略的列数量
                int ignoreCount = 0;
                Object obj = objList.get(rowIndex - 1);

                for (int colIndex = 0; colIndex < colLen; ++colIndex) {
                    Column column = colList.get(colIndex);
                    if ("rowIndex".equals(column.getKey())) {
                        // 设置当前列序号
                        Number num = new Number(0, rowIndex + headIndex, rowIndex, ft_intNumber);
                        sheet.addCell(num);
                        continue;
                    }
                    String data = getCellStringData(objClass, obj, column);
                    if (isExport(column)) {
                        // 对齐方式
                        WritableCellFormat format = getAlignFormat(column);
                        //根据类型设置单元格类型并写入数据
                        if (column.getClassType().equals("java.lang.Double") &&StrUtil.isEmpty(column.getSuffix())) {
                            if (!StrUtil.isEmpty(data)) {
                                Number num1 = new Number(colIndex - ignoreCount, rowIndex + headIndex, Double.parseDouble(data),
                                        ft_decimal);
                                sheet.addCell(num1);
                            } else {
                                Label label = new Label(colIndex - ignoreCount, rowIndex + headIndex, "", ft_decimal);
                                sheet.addCell(label);
                            }
                        } else if (column.getClassType().equals("java.lang.Integer")&&StrUtil.isEmpty(column.getSuffix())) {
                            Number num2 = new Number(colIndex - ignoreCount, rowIndex + headIndex, Integer.parseInt(data),
                                    ft_intNumber);
                            sheet.addCell(num2);
                        } else if (column.getSuffix() != null) {
                            Label label = new Label(colIndex - ignoreCount, rowIndex + headIndex, data + "%", format);
                            sheet.addCell(label);
                        } else {
                            Label label = new Label(colIndex - ignoreCount, rowIndex + headIndex, data, format);
                            sheet.addCell(label);
                        }
                        if (!StrUtil.isEmpty(data) && data.getBytes().length > sheet.getColumnWidth(colIndex - ignoreCount))
                            sheet.setColumnView(colIndex - ignoreCount, data.getBytes().length + 4);
                    } else {
                        ++ignoreCount;
                    }
                }

            }
        } catch (Exception ex) {
            throw new QueryException("写入数据表头发生异常，异常信息：" + ex.getMessage());
        }

    }

    /**
     * 单元格数据转化
     *
     * @param objClass 映射实体类
     * @param obj      对象
     * @param column   列配置
     * @return 转化的值
     * @throws QueryException 转化异常
     */
    private static String getCellStringData(Class objClass, Object obj, Column column) throws QueryException {
        try {
            String data = null;
            if (objClass != null) {
                data = ObjectUtil.ObjectToString(obj, column.getKey(), column.getDateFormat());
            } else {
                Map<String, Object> dataMap = dataMap = (Map<String, Object>) obj;
                if (Date.class.getName().equals(column.getClassType())
                        || "date".equals(column.getClassType().toLowerCase())) {
                    data = column.getDateFormat() != null ? DateUtil.format(
                            (Date) dataMap.get(column.getKey().toUpperCase()), column.getDateFormat()) : DateUtil
                            .format((Date) dataMap.get(column.getKey().toUpperCase()));
                } else {
                    if (dataMap.get(column.getKey().toUpperCase()) != null) {
                        data = dataMap.get(column.getKey().toUpperCase()).toString();
                    }
                }
            }
            return data;
        } catch (Exception ex) {
            throw new QueryException("单元格数据转换方法getCellStringData错误，错误信息：" + ex.getMessage());
        }
    }

    /**
     * 根据列对齐方式，获取单元格对齐样式
     *
     * @param column 列配置
     * @return 对齐样式
     */
    private static WritableCellFormat getAlignFormat(Column column) {
        // 单元格对齐
        WritableCellFormat format;
        if (column.getAlign().equals("right")) {
            format = ft_right;
        } else if (column.getAlign().equals("left")) {
            format = ft_left;
        } else {
            format = ft_center;
        }
        return format;
    }

    /**
     * 执行其他一些需要特殊处理的方法比如合并 隐藏
     * @param queryCondition 查询参数
     * @param sheet 表格
     * @throws QueryException
     */
    public static void executeSheetMethod(QueryCondition queryCondition,WritableSheet sheet) throws QueryException {
        try {
            if (queryCondition.getSheetMethod() != null) {
                String sheetMethod = queryCondition.getSheetMethod();
                String[] params = sheetMethod.split("_");
                // 正文内容考试的行号索引
                int contentBeginIndex = getTitleIndex(queryCondition);
                if (params[0].equals("rowSpan")) {
                    for (int i = 1; i < params.length; i++) {
                        sheet = JXLExcelUtil.rowSpan(sheet, Integer.valueOf(params[i]).intValue(), contentBeginIndex);
                    }
                }
                if (params[0].equals("display")) {
                    for (int i = 1; i < params.length; i++) {
                        sheet.setColumnView(Integer.valueOf(params[i]).intValue(), 0);
                    }
                }
            }
        }catch (Exception ex){
            throw new QueryException("执行rowSpan方法异常，异常信息："+ex.getMessage());
        }

    }


    /**
     * 获取文件路径
     * @param fileName 文件名称
     * @return
     */
    public static String getFilePath(String fileName){
       return  PropertiesUtil.getValue("templatePath") + File.separator + fileName + ".xls";
    }


}

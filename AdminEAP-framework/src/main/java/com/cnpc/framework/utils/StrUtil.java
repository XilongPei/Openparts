package com.cnpc.framework.utils;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil {

    private StringBuffer sb = null;
    private String sysLineSeparator = null;

    public StrUtil() {
        if (sb != null) {
            sb = null;
        } else {
            sb = new StringBuffer();
        }
        sysLineSeparator = System.getProperty("line.separator");
    }

    /**
     * 输入指定字串返回非空字符
     *
     * @param str 指定字串
     * @return 返回类型 String 返回非空字符
     */
    public static String requote(String str) {
        if (str == null)
            str = "";
        if ("null".equalsIgnoreCase(str))
            str = "";
        return str;
    }

    public static String objectToString(Object objectStr) {
        if (objectStr == null)
            objectStr = "";
        if ("null".equalsIgnoreCase(String.valueOf(objectStr)))
            objectStr = "";
        return String.valueOf(objectStr);
    }

    public static String objectToString(Object objectStr, String e) {
        if (objectStr == null)
            objectStr = e;
        if ("null".equalsIgnoreCase(String.valueOf(objectStr)))
            objectStr = e;
        return String.valueOf(objectStr);
    }


    /**
     * 非空返回false
     *
     * @param
     * @param str
     * @param
     * @return 返回类型
     * @throws
     * @Title:
     * @Description: TODO
     */
    public static boolean isBlankOrNull(String str) {
        return "".equals(requote(str)) ? true : false;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 对象为null
     *
     * @param
     * @param obj
     * @param
     * @return 返回类型
     * @throws
     * @Title:
     * @Description: TODO
     */
    public static boolean isNull(Object obj) {
        return null == obj ? true : false;
    }

    /**
     * 非""返回TRUE
     *
     * @param
     * @param str
     * @param
     * @return 返回类型
     * @throws
     * @Title:
     * @Description: TODO
     */
    public static boolean isBlank(String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0))
            return true;
        for (int i = 0; i < strLen; i++)
            if (!Character.isWhitespace(str.charAt(i)))
                return false;
        return true;
    }

    public static String getRepeatedStr(String str, int num) {
        String strs = "";
        if (num == 0)
            return strs;
        for (int i = 0; i < num; i++) {
            strs += str;
        }
        return strs;
    }

    /**
     * GBK编码的字符串转为ISO885_1
     *
     * @param str GBK编码的字符
     * @return 返回类型 String 转化后的ISO885_1字符串
     * @throws Exception
     */
    public static String GBKToISO(String str) throws Exception {
        if (str != null) {
            return new String(str.getBytes("GBK"), "ISO8859_1");
        } else {
            return "";
        }
    }

    /**
     * ISO8859_1编码的字符串转为GBK
     *
     * @param str GBK编码的字符
     * @return 返回类型 String 转化后的GBK字符串
     * @throws Exception
     */
    public static String ISOToGBK(String str) throws Exception {
        if (str != null) {
            return new String(str.getBytes("ISO8859_1"), "GBK");
        } else {
            return "";
        }
    }

    /**
     * GB编码的字符串转为UTF8
     *
     * @param src GB编码的字符
     * @return 返回类型 String 转化后的UTF8字符串
     * @throws Exception
     */
    public static String gbToUtf8(String src) {
        byte[] b = src.getBytes();
        char[] c = new char[b.length];
        for (int i = 0; i < b.length; i++) {
            c[i] = (char) (b[i] & 0x00FF);
        }
        return new String(c);
    }

    /**
     * 将指定对象转化为非空String，如果为空返回“0”
     *
     * @param obj 指定对象
     * @return 返回类型 String 非空String
     */
    public static String null2Zero(Object obj) {

        if ((obj == null) || (obj.equals("")) || (obj.equals("null"))) {
            return "0";
        } else {
            return obj.toString().trim();
        }
    }

    /**
     * 将指定对象转化为非空String，如果为空返回“”
     *
     * @param obj 指定对象
     * @return 返回类型 String 非空String
     */
    public static String null2Str(Object obj) {

        if ((obj == null) || (obj.equals("null")) || (obj.equals(""))) {
            return "";
        } else {
            return obj.toString().trim();
        }
    }

    /**
     * 将一个字符串按给定分割字符分割成数组
     *
     * @param source  字符串
     * @param useChar 分割字符
     * @return 返回类型 String[] 数组字符串
     */

    public static String[] split(String source, char useChar) {
        List list = new ArrayList();
        String sub;
        String[] result;

        if (source.charAt(0) == useChar)
            source = source.substring(1, source.length());
        if (source.charAt(source.length() - 1) == useChar)
            source = source.substring(0, source.length() - 1);

        int start = 0;
        int end = source.indexOf(useChar);
        while (end > 0) {
            sub = source.substring(start, end);
            list.add(sub);
            start = end + 1;
            end = source.indexOf(useChar, start);
        }

        sub = source.substring(start, source.length());
        list.add(sub);

        result = new String[list.size()];

        Iterator iter = list.iterator();
        int i = 0;
        while (iter.hasNext()) {
            result[i++] = (String) iter.next();
        }
        return result;
    }

    /**
     * 将一个字符串按给定分割字符分割成List
     *
     * @param source  字符串
     * @param useChar 分割字符
     * @return 返回类型 List 分割成的List
     */
    public static List splitList(String source, char useChar) {
        List list = new ArrayList();
        String sub;

        if (source.charAt(0) == useChar)
            source = source.substring(1, source.length());
        if (source.charAt(source.length() - 1) == useChar)
            source = source.substring(0, source.length() - 1);

        int start = 0;
        int end = source.indexOf(useChar);
        while (end > 0) {
            sub = source.substring(start, end);
            list.add(sub);
            start = end + 1;
            end = source.indexOf(useChar, start);
        }

        sub = source.substring(start, source.length());
        list.add(sub);

        return list;
    }

    /**
     * 判断给定子字符串<code>subStr</code>是否在大字符�?<code>str</code>
     *
     * @param subStr 子字符串
     * @param str    大字符串
     * @return 返回类型 如果存在则返回true,否则返回false
     */
    public static boolean isIn(String subStr, String str) {
        if (subStr == null || str == null) {
            return false;
        }
        if (str.indexOf(subStr) == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 将数组中的每一项用“，”连接起来
     *
     * @param str 数组
     * @return 返回类型 String 字符串
     */
    public static String join(String[] str) {
        return join(str, ",");
    }

    public static String join(List<String> str) {
        String[] arr = new String[str.size()];
        int i = 0;
        for (String s : str) {
            arr[i++] = s;
        }
        return join(arr, ",");
    }

    /**
     * 将数组中的每一项用指定字符串连接起来
     *
     * @param str  数组
     * @param join 指定字符串
     * @return 返回类型 String 字符串
     */
    public static String join(String[] str, String join) {
        if (str == null || join == null) {
            return "";
        }
        String rtnStr = "";
        for (int i = 0; i < str.length; i++) {
            rtnStr += join + str[i];
        }
        if (rtnStr.indexOf(join) != -1) {
            rtnStr = rtnStr.substring(join.length());
        }
        return rtnStr;
    }

    /**
     * 字符串替换
     *
     * @param line      源字符串
     * @param oldString 将要被替换掉的子字符串
     * @param newString 将要用来替换旧子字符串的字符串
     * @return 返回类型 String 返回替换后的结果字符串
     */
    public static final String replace(String line, String oldString,
                                       String newString) {
        if (line == null) {
            return null;
        }
        int i = 0;
        if ((i = line.indexOf(oldString, i)) >= 0) {
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = line.indexOf(oldString, i)) > 0) {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            return buf.toString();
        }
        return line;
    }

    /**
     * 字符串替换，忽略大小写
     *
     * @param line      源字符串
     * @param oldString 将要被替换掉的子字符串
     * @param newString 将要用来替换旧子字符串的字符串
     * @return 返回类型 String 返回替换后的结果字符串
     */
    public static final String replaceIgnoreCase(String line, String oldString,
                                                 String newString) {
        if (line == null) {
            return null;
        }
        String lcLine = line.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i = 0;
        if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            return buf.toString();
        }
        return line;
    }

    /**
     * 字符串替换，并且统计替换的个数
     *
     * @param line      源字符串
     * @param oldString 将要被替换掉的子字符串
     * @param newString 将要用来替换旧子字符串的字符串
     * @param count     统计替换的个数
     * @return 返回类型 String 返回替换后的结果字符串
     */
    public static final String replace(String line, String oldString,
                                       String newString, int[] count) {
        if (line == null) {
            return null;
        }
        int i = 0;
        if ((i = line.indexOf(oldString, i)) >= 0) {
            int counter = 0;
            counter++;
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = line.indexOf(oldString, i)) > 0) {
                counter++;
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            count[0] = counter;
            return buf.toString();
        }
        return line;
    }

    /**
     * 将数字型日期转换为带间隔符的字符串型
     *
     * @param date     指定日期
     * @param interval 间隔符
     * @return 返回类型 String 带间隔符的字符串型
     */
    public static final String int2Date(Integer date, String interval) {
        String line = String.valueOf(date);
        if (line.length() != 8) {
            return null;
        } else {
            StringBuffer buf = new StringBuffer(10);
            buf.append(line.substring(0, 4));
            buf.append(interval);
            buf.append(line.subSequence(4, 6));
            buf.append(interval);
            buf.append(line.substring(6, 8));
            return buf.toString();
        }

    }

    /**
     * 将数字型日期转换为带间隔符的字符串型
     *
     * @param date     指定日期
     * @param interval 间隔符
     * @return 返回类型 String 带间隔符的字符串型
     */
    public static final String long2Date(Long date, String interval) {
        String line = String.valueOf(date);
        if (line.length() != 8) {
            return null;
        } else {
            StringBuffer buf = new StringBuffer(10);
            buf.append(line.substring(0, 4));
            buf.append(interval);
            buf.append(line.subSequence(4, 6));
            buf.append(interval);
            buf.append(line.substring(6, 8));
            return buf.toString();
        }

    }

    /**
     * 查一个字符串是否全部为空
     *
     * @param input 字符串
     * @return 返回类型 boolean 如果字符串为空格则返回false,否则返回strTemp.length()!
     */
    public static boolean checkDataValid(String input) {
        String strTemp = new String(input);
        if (strTemp == null || strTemp.length() == 0) {
            return false;
        }
        strTemp = strTemp.trim();
        return strTemp.length() != 0;
    }

    /**
     * 替换输入字符串中的非法字符串
     *
     * @param input 是要替换的字符串
     * @return 返回类型 String 如果字符串为空则返回input,否则返回buf.toString()替换后的字符串
     */
    public static String escapeHTML(String input) {

        if (input == null || input.length() == 0) {
            return input;
        }
        input = input.trim();
        StringBuffer buf = new StringBuffer();
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '<') {
                buf.append("&lt;");
            } else if (ch == '>') {
                buf.append("&gt;");
            } else if (ch == '\n') {
                buf.append("<br/>");
            } else if (ch == ' ') {
                buf.append("&nbsp;");
            } else if (ch == '\'') {
                buf.append("''");
            } else {
                buf.append(ch);
            }
        } // end for loop
        return buf.toString();
    }

    /**
     * 转换个包含有HTML标志(ie, &lt;b&gt;,&lt;table&gt;, etc)的字符串，将里面�?'><'转换�?'&lt''
     * and '&gt;'
     *
     * @param input 篇用于转换的text
     * @return 返回类型 String 转换后的字符串
     */
    public static final String escapeHTMLTags(String input) {
        if (input == null || input.length() == 0) {
            return input;
        }
        StringBuffer buf = new StringBuffer(input.length());
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '<') {
                buf.append("&lt;");
            } else if (ch == '>') {
                buf.append("&gt;");
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    /**
     * 追加字符串到字符串缓冲器
     *
     * @param str 要追加的字符串
     */
    public void append(String str) {
        sb.append(str);
    }

    /**
     * 追加字符串到字符串缓冲器 并追加一个缺省的换行符\n
     *
     * @param str 要追加的字符串
     */
    public void appendln(String str) {
        appendln(str, false);
    }

    /**
     * 追加字符串到字符串缓冲器 并根据<code>isSysLineSeparator</code>不同追加缺省的换行符\n或系统换行符
     *
     * @param str                 要追加的字符串
     * @param useSysLineSeparator 是否追加系统换行符表
     */
    public void appendln(String str, boolean useSysLineSeparator) {
        if (useSysLineSeparator) {
            sb.append(str);
            sb.append(this.sysLineSeparator);
        } else {
            sb.append(str);
            sb.append("\n");
        }
    }

    /**
     * 转换字符串缓冲器中的数据为字符串表示
     *
     * @return 返回类型 String 表示字符串缓冲器中的数据的字符串
     */
    public String toStr() {
        return sb.toString();
    }

    /**
     * 把一篇文档分割成小写的，以 , .\r\n:/\+为分割符的数组
     *
     * @param text 要被分割成数组的文档
     * @return 返回类型 String[] 返回分割成的数组
     */
    public static final String[] toLowerCaseWordArray(String text) {
        if (text == null || text.length() == 0) {
            return new String[0];
        }
        StringTokenizer tokens = new StringTokenizer(text, " ,\r\n.:/\\+");
        String[] words = new String[tokens.countTokens()];
        for (int i = 0; i < words.length; i++) {
            words[i] = tokens.nextToken().toLowerCase();
        }
        return words;
    }

    /**
     * 将对象数组转为字符串数组
     *
     * @param objs 对象数组
     * @return 返回类型 String[] 字符串数组
     */
    public static String[] objectArrayToStringArray(Object[] objs) {
        if (objs == null)
            return null;
        String[] s = new String[objs.length];
        System.arraycopy(objs, 0, s, 0, s.length);
        return s;
    }

    /**
     * 字符串加密
     *
     * @param str 字符串
     * @return 返回类型 String 加密后字符串
     */
    public static final String encrypt(String str) {

        String string_in = "YN8K1JOZVURB3MDETS5GPL27AXWIHQ94C6F0#$_@!~`%^&*()-+=[]{}'|?;:/,<>.\"\\ ";
        String string_out = " @!~`%^&*()-+=[]{}'|?;:/,<>._$#ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789\"\\";
        String outpass = "";
        try {
            if (str != null) {
                int offset = 0;
                Calendar calendar = Calendar.getInstance();
                int ss = calendar.get(Calendar.SECOND);
                offset = ss % 68;
                if (offset > 0)
                    offset = offset - 1;
                outpass = string_in.substring(offset, offset + 1);
                string_in = string_in + string_in;
                string_in = string_in.substring(offset, offset + 69);
                outpass = outpass + translate(str, string_in, string_out);
                outpass = strToAscStr(outpass, "-");
                return outpass;
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 简单加解密 江日念添加
     *
     * @param @param  str
     * @param @return
     * @return 返回类型
     * @throws
     * @Title:
     * @Description: TODO
     */
    public static final String simpleEncrypt(String str) {
        String in = strToAscStr(str, "-");
        return in;
    }

    public static final String simpleDisencrypt(String str) {
        String out = ascToStr(str, "-");
        return out;
    }

    /**
     * 字符串解密
     *
     * @param str 字符串
     * @return 返回类型 String 解密后字符串
     */
    public static final String disencrypt(String str) {
        str = ascToStr(str, "-");
        String string_in = "YN8K1JOZVURB3MDETS5GPL27AXWIHQ94C6F0#$_@!~`%^&*()-+=[]{}'|?;:/,<>.\"\\ ";
        String string_out = " @!~`%^&*()-+=[]{}'|?;:/,<>._$#ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789\"\\";
        try {
            int offset = 0;
            char c = str.charAt(0);
            offset = string_in.indexOf(c);
            string_in = string_in + string_in;
            string_in = string_in.substring(offset, offset + 69);
            String s = str.substring(1);
            s = s.toUpperCase();
            String inpass = translate(s, string_out, string_in);
            return inpass;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 取得str字符串转换为大写字母再取出里面的每一个字符，看这个字符在string_in中出现的位置，再取出string_out字符串在这个位置上的每个字符。
     *
     * @param str
     * @param string_in
     * @param string_out
     * @return 返回类型 String 返回转换后的字符串
     */
    private static final String translate(String str, String string_in,
                                          String string_out) {

        String s = str.toUpperCase();
        char[] outc = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int j = string_in.indexOf(c);
            outc[i] = string_out.charAt(j);

        }
        String outs = new String(outc);

        return outs;
    }

    /**
     * 对字符串进行编码,目的是解决数据库中文问题
     *
     * @param inputStr 指定字符串
     * @return 返回类型 String 返回编码后的字符串
     */
    public static String encode(String inputStr) {

        String tempStr = "";
        try {
            tempStr = new String(inputStr.getBytes("ISO-8859-1"));
        } catch (Exception ex) {
            System.out.print("encode() error: " + ex.toString());
        }
        return tempStr;
    }

    /**
     * 将指定url用GBK编码
     *
     * @param url 指定url
     * @return 返回类型 String 编码后url
     */
    public static String urlEncode(String url) {
        if (url == null || "".equals(url))
            return "";
        try {
            return java.net.URLEncoder.encode(url, "GBK");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 将指定url用GBK解码
     *
     * @param encodedUrl 指定url
     * @return 返回类型 String 解码后url
     */
    public static String urlDecode(String encodedUrl) {
        if (encodedUrl == null || "".equals(encodedUrl))
            return "";
        try {
            return java.net.URLDecoder.decode(encodedUrl, "GBK");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 字符串是否为空
     *
     * @param str 字符串
     * @return 返回类型 boolean 为空则为true，否则为false
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) || "".equals(str.trim()) || str.equals("null") || str.equals("undefined"))
            return true;
        return false;
    }


    /**
     * 将浮点型小数转换为百分数，小数点后第三位四舍五入
     *
     * @param f 浮点型小数
     * @return 返回类型 String 浮点型小数转换为百分数，小数点后第三位四舍五入后的字符串
     */
    public static String getRoundPercent(double f) {
        DecimalFormat df = new DecimalFormat("#####.00");

        return df.format(f);
    }

    /**
     * 金额数字转换
     *
     * @param f 浮点型小数
     * @return 返回类型 String 浮点型小数转换为百分数，小数点后第三位四舍五入后的字符串
     */
    public static String formartDouble(double f) {
        DecimalFormat df = new DecimalFormat("#,##0.0#");
        return df.format(f);
    }

    /**
     * 字符串金额数字转换
     *
     * @return 返回类型 String 浮点型小数转换为百分数，小数点后第三位四舍五入后的字符串
     */
    public static Double parseDouble(String str) {
        try {
            DecimalFormat df = new DecimalFormat("#,##0.0#");
            return Double.valueOf(String.valueOf(df.parseObject(str)));
        } catch (Exception e) {
            return new Double(0.0);
        }
    }

    /**
     * 将浮点型小数转换为百分数，小数点后第三位四舍五入
     *
     * @param f 浮点型小数
     * @return 返回类型 double 浮点型小数转换为百分数，小数点后第三位四舍五入后的浮点型小数
     */
    public static double getDoubledigit(double f) {
        DecimalFormat df = new DecimalFormat("#####.00");
        return Double.parseDouble(df.format(f));
    }

    /**
     * 将浮点型小数转换为整数
     *
     * @param f 浮点型小数
     * @return 返回类型 int 转换后的整数
     */

    public static int getIntRoundPercent(double f) {

        DecimalFormat df = new DecimalFormat("#####");
        Double.parseDouble(df.format(f));

        return Integer.parseInt(df.format(f));
    }

    /**
     * 将浮点型小数转换为百分数
     *
     * @param f 浮点型小数
     * @return 返回类型 int 转换后的点型小数
     */
    public static int getRoundPercent(float f) {
        double r = f * 100;
        String round = String.valueOf(r);
        if (round.indexOf(".") > 0) {
            round = round.substring(0, round.indexOf("."));
            int intValue = Integer.parseInt(round);
            if (r - intValue >= 0.5)
                intValue += 1;
            round = String.valueOf(intValue);
        }
        return Integer.parseInt(round);
    }

    /**
     * 得到YYYY-12200203型字串 YYYY：2009
     *
     * @return 返回类型 String YYYY-12200203型字串
     */
    public static String getYYYYString() {
        Calendar now = Calendar.getInstance();
        String number = "" + now.get(Calendar.YEAR) + "-"
                + now.getTimeInMillis();
        return number;
    }

    /**
     * 将指定日期去掉“_”
     *
     * @param data 指定日期
     * @return 返回类型 String 去掉“_”后的日期
     */
    public static String removeUnderScores(String data) {
        String temp = null;
        StringBuffer out = new StringBuffer();
        temp = data;

        StringTokenizer st = new StringTokenizer(temp, "_");
        while (st.hasMoreTokens()) {
            String element = (String) st.nextElement();
            out.append(capitalize(element));
        }
        return out.toString();
    }

    /**
     * 将指定字符串大写
     *
     * @param data 指定字符串
     * @return 返回类型 String 返回新字串
     */
    public static String capitalize(String data) {
        StringBuffer sbuf = new StringBuffer(data.length());
        sbuf.append(data.substring(0, 1).toUpperCase()).append(
                data.substring(1));
        return sbuf.toString();
    }

    /**
     * 将指定字符串的第pos个字符大写
     *
     * @param data 指定字符串
     * @param pos  第pos个字符
     * @return 返回类型 String 返回改变后的字符串
     */
    public static String capitalize(String data, int pos) {
        StringBuffer buf = new StringBuffer(data.length());
        buf.append(data.substring(0, pos - 1));
        buf.append(data.substring(pos - 1, pos).toUpperCase());
        buf.append(data.substring(pos, data.length()));
        return buf.toString();
    }

    /**
     * 将指定字符串的第pos个字符小写
     *
     * @param data 指定字符串
     * @param pos  第pos个字符
     * @return 返回类型 String 返回改变后的字符窜
     */
    public static String unCapitalize(String data, int pos) {
        StringBuffer buf = new StringBuffer(data.length());
        buf.append(data.substring(0, pos - 1));
        buf.append(data.substring(pos - 1, pos).toLowerCase());
        buf.append(data.substring(pos, data.length()));
        return buf.toString();
    }

    /**
     * 将一个字符串按给定分割字符串分割成数组
     *
     * @param text      字符串
     * @param separator 分割字符串
     * @return 返回类型 String[] 数组字符串
     */
    public static String[] split(String text, String separator) {
        StringTokenizer st = new StringTokenizer(text, separator);
        String[] values = new String[st.countTokens()];
        int pos = 0;
        while (st.hasMoreTokens()) {
            values[pos++] = st.nextToken();
        }
        return values;
    }

    /**
     * 将字符串中所有的tag全部替换成为指定的info
     *
     * @param source 原来的字符串
     * @param info   替换tag的字符串
     * @param tag    要被替换掉的tag
     * @return 返回类型 String 替换后的内容
     */
    public static String replaceAll(String source, String info, String tag) {
        if ((source == null) || (source.length() == 0))
            return "";

        if ((info == null) || (tag == null) || (tag.length() == 0))
            return source;

        int index = source.indexOf(tag);
        boolean valid = (index >= 0);
        if (!valid)
            return source;

        StringBuffer ret = new StringBuffer();
        int start = 0;
        int length = tag.length();
        while (valid) {
            ret.append(source.substring(start, index)).append(info);
            start = index + length;
            index = source.indexOf(tag, start);
            valid = (index >= 0);
        }
        ret.append(source.substring(start));
        return ret.toString();
    }

    /**
     * 将字符串中所有的tag全部替换成为指定的info
     *
     * @param source   原来的字符串
     * @param info     替换tag的字符串
     * @param startTag 被替换字符串起始点
     * @param endTag   被替换字符串结束点
     * @return 返回类型 String 替换后的字符串
     */
    public static String replaceAll(String source, String info,
                                    String startTag, String endTag) {
        if ((source == null) || (source.length() == 0)) {
            return "";
        }
        if ((info == null) || (startTag == null) || (startTag.length() == 0)
                || (endTag == null) || (endTag.length() == 0)) {
            return source;
        }
        int sIndex = source.indexOf(startTag);
        int eIndex = source.indexOf(endTag);
        boolean valid = (sIndex >= 0 && eIndex >= 0);
        if (!valid) {
            return source;
        } else {
            if (sIndex > eIndex) {
                eIndex = source.indexOf(endTag, sIndex);
            }
        }
        int sLength = startTag.length();
        int eLength = endTag.length();
        StringBuffer ret = new StringBuffer();
        int start = 0;
        while (valid) {
            info = source.substring(sIndex + 1, eIndex).trim();
            ret.append(source.substring(start, sIndex + 1)).append(info)
                    .append(endTag);
            start = eIndex + 1;
            sIndex = source.indexOf(startTag, start);
            eIndex = source.indexOf(endTag, start);
            valid = (sIndex >= 0 && eIndex >= 0 && eIndex > sIndex);
        }
        ret.append(source.substring(start));
        return ret.toString();
    }

    /**
     * 将输入字符中的SQL保留字进行替换，目前只替换英文半角的单引号'
     * 单引号替换方法：一个单引号换成连续的两个单引号，例如'ABC'D替换成''ABC''D
     *
     * @param s 源字符串
     * @return 返回类型 String 替换后字符串
     */
    public static String getSQLencode(String s) {
        if ((s == null) || (s.length() == 0))
            return "";
        StringBuffer sb = new StringBuffer();
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            switch (c) {
                case '\'':
                    sb.append("''");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 将输入字符中的格式化成precision指定的程度,截掉的部分用“..”补齐
     *
     * @param s         被格式化字符串
     * @param precision 格式化长度
     * @return 返回类型 String 格式化后字符串
     */
    public static String getFormatString(String s, int precision) {
        String retValue = "";
        if ((s == null) || (s.length() == 0))
            retValue = "";
        if (s.length() <= precision)
            retValue = s;
        if (s.length() == precision + 1)
            retValue = s;
        if (s.length() > precision + 1)
            retValue = s.substring(0, precision - 1) + "..";
        return retValue;
    }


    /**
     * 生成树形CODE码
     *
     * @param title      头
     * @param tail       尾
     * @param tailLength 长度
     * @return 返回类型 String 返回生成的code码
     */
    public static String ensureLengthWith0(String title, String tail,
                                           int tailLength) {
        int len = tail.length();

        if (len == tailLength)
            return title.concat(tail);
        if (len > tailLength)
            return title.concat(tail.substring(0, tailLength));

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tailLength - len; i++)
            sb.append('0');

        return title.concat(sb.toString().concat(tail));
    }

    public static final String filterHalfWord(String sourceStr)
            throws UnsupportedEncodingException {
        String result = "";
        char[] cc = sourceStr.toCharArray();
        for (int i = 0; i < cc.length; i++) {
            byte[] c = String.valueOf(cc[i]).getBytes("UTF-8");
            String hex = encodeHex(c);

            if (!"0".equals(hex.trim())) {
                result += String.valueOf(cc[i]);
            }
        }
        return result;
    }

    public static final String encodeHex(byte[] bytes) {
        StringBuffer buff = new StringBuffer(bytes.length * 2);
        String b;
        for (int i = 0; i < bytes.length; i++) {
            b = Integer.toHexString(bytes[i]);
            buff.append(b.length() > 2 ? b.substring(6, 8) : b);
            buff.append(" ");
        }
        return buff.toString();
    }

    public static String getRandom18Str() {
        String random18Str = "";
        random18Str = (new Date().getTime() + "" + Math
                .floor(Math.random() * 100000));
        random18Str = random18Str.substring(0, random18Str.indexOf("."));
        if (null != random18Str && 18 == random18Str.length()) {
            return random18Str;
        } else {
            return getRandom18Str();
        }
    }

    public static void main(String[] args) {
        System.out.println(encrypt("111111"));
        System.out.println(disencrypt("5d-3f-3f-3f-3f-3f-3f"));
    }

    /**
     * 字符转ASC
     *
     * @param str
     * @return
     */
    public static String strToAscStr(String str, String splitStr) {
        String retStr = "";
        if (null == str) {
            return "";
        }
        byte[] byteArray = str.getBytes();
        for (int i = 0; i < byteArray.length; i++) {
            retStr += Integer.toHexString(byteArray[i]) + splitStr;
        }
        if (retStr.length() > splitStr.length()) {
            return retStr.substring(0, retStr.length() - splitStr.length());
        } else {
            return retStr;
        }
    }

    /**
     * ASC转字符
     *
     * @return
     */
    public static String ascToStr(String str, String split) {
        String retStr = "";
        if (null == str) {
            return retStr;
        }
        String[] strArray = str.split(split);
        if (null != strArray && 0 < strArray.length) {
            for (int i = 0; i < strArray.length; i++) {
                retStr += (char) Integer.parseInt(strArray[i], 16);
            }
        }
        return retStr;
    }

    /**
     * 取得指定子串在字符串中出现的次数。
     * <p/>
     * <p>
     * 如果字符串为<code>null</code>或空，则返回<code>0</code>。
     * <pre>
     * StringUtil.countMatches(null, *)       = 0
     * StringUtil.countMatches("", *)         = 0
     * StringUtil.countMatches("abba", null)  = 0
     * StringUtil.countMatches("abba", "")    = 0
     * StringUtil.countMatches("abba", "a")   = 2
     * StringUtil.countMatches("abba", "ab")  = 1
     * StringUtil.countMatches("abba", "xxx") = 0
     * </pre>
     * </p>
     *
     * @param str    要扫描的字符串
     * @param subStr 子字符串
     * @return 子串在字符串中出现的次数，如果字符串为<code>null</code>或空，则返回<code>0</code>
     */
    public static int countMatches(String str, String subStr) {
        if ((str == null) || (str.length() == 0) || (subStr == null) || (subStr.length() == 0)) {
            return 0;
        }

        int count = 0;
        int index = 0;

        while ((index = str.indexOf(subStr, index)) != -1) {
            count++;
            index += subStr.length();
        }

        return count;
    }


    /**
     * 正则匹配
     *
     * @param pattern 正则表达式
     * @param source  要匹配的字符串
     * @return
     */
    public static boolean matches(String pattern, String source) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern argument cannot be null.");
        } else {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(source);
            return m.matches();
        }
    }

    /**
     * 去掉字符串中的html源码。<br>
     *
     * @param con 内容
     * @return 去掉后的内容
     */

    public static String clearHTML(String con) {
        String content = con;
        if (con != null) {
            content = con.replaceAll("</?[^>]+>", "");//剔出了<html>的标签
            content = content.replace("&nbsp;", "");
            content = content.replace(".", "");
            content = content.replace("\"", "‘");
            content = content.replace("'", "‘");
        }
        return content;
    }

    /**
     * 1,2,3转化为'1','2','3'
     *
     * @param str
     * @return
     */
    public static String getInStr(String str) {
        String[] strArr = str.split(",");
        String[] retArr = new String[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            retArr[i] = "'" + strArr[i].trim() + "'";
        }
        return StrUtil.join(retArr);
    }

    /**
     * 获取文件的后缀名
     */
    public static String getExtName(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 去掉左侧匹配的字符串
     *
     * @param source  原字符串
     * @param element 待删除字符串
     * @return 处理后的字符串
     */
    public static String ltrim(String source, String element) {
        String retStr = source;
        if (source.indexOf(element) == 0) {
            retStr = source.substring(element.length());
        }
        return retStr;
    }

    /**
     * 去除右侧匹配的字符串
     *
     * @param source  原字符串
     * @param element 待删除字符串
     * @return 处理后的字符串
     */
    public static String rtrim(String source, String element) {
        String retStr = source;
        if (source.indexOf(element) == source.length() - element.length()) {
            retStr = source.substring(0, source.indexOf(element));
        }
        return retStr;

    }

    /**
     * 去除首尾匹配的字符串
     *
     * @param source  原字符串
     * @param element 待删除字符串
     * @return 处理后的字符串
     */
    public static String trim(String source, String element) {
        return StrUtil.trim(source, element, element);
    }

    public static String trim(String source, String left, String right) {
        return StrUtil.rtrim(StrUtil.ltrim(source, left), right);
    }

    /**
     * 将map类型的数据抽取出来，并形成逗号分隔的字符串
     *
     * @param list
     * @param name
     * @return
     */
    public static String mapToStr(List list, String name) {
        String[] retArr = new String[list.size()];
        int i = 0;
        for (Object obj : list) {
            retArr[i] = ((Map) obj).get(name).toString();
            i++;
        }
        return StrUtil.join(retArr);
    }
}
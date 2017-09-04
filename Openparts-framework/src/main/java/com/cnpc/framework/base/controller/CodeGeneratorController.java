package com.cnpc.framework.base.controller;

import com.alibaba.fastjson.JSON;
import com.cnpc.framework.annotation.Header;
import com.cnpc.framework.base.entity.Function;
import com.cnpc.framework.base.pojo.FieldSetting;
import com.cnpc.framework.base.pojo.GenerateSetting;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.service.CodeGeneratorService;
import com.cnpc.framework.query.entity.Query;
import com.cnpc.framework.query.pojo.QueryDefinition;
import com.cnpc.framework.utils.DateUtil;
import com.cnpc.framework.utils.FreeMarkerUtil;
import com.cnpc.framework.utils.PingYinUtil;
import com.cnpc.framework.utils.StrUtil;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by billJiang on 2017/2/6.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 * 代码生成器控制类
 */
@Controller
@RequestMapping(value = "/generator")
public class CodeGeneratorController {

    @Resource
    private CodeGeneratorService codeGeneratorService;

    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public String setting() {
        return "tool/generator/generator_setting";
    }

    @RequestMapping(value = "/getQuerySetting", method = RequestMethod.POST)
    @ResponseBody
    public GenerateSetting getQuerySetting(String queryId, String className, HttpServletRequest request) throws ClassNotFoundException {
        GenerateSetting setting = new GenerateSetting();
        String realPath = request.getRealPath("/");
        if(StrUtil.isEmpty(realPath)){
            System.out.println("realPath is null:请在开发环境中使用该功能");
            setting.setIsExistQuery("2");
            return setting;
        }

        Query query = QueryDefinition.getQueryById(queryId);
        if (query == null) {
            setting.setIsExistQuery("0");
            return setting;
        } else {
            setting.setIsExistQuery("1");
        }

        setting.setQueryId(queryId);
        setting.setColsNum(1);
        setting.setClassName(StrUtil.isEmpty(query.getClassName()) ? className : query.getClassName());
        setting.setAllowPaging(query.getAllowPaging());
        setting.setTableName(query.getTableName());
        setting.setModelName(query.getTableName().replaceAll("列表", ""));
        //计算htmlPrefix javaPrefix nameSpace
        String nameSpace = null;
        if (!StrUtil.isEmpty(setting.getClassName())) {
            String javaPrefix = setting.getClassName();
            nameSpace = javaPrefix;
            int dot = setting.getClassName().lastIndexOf('.');
            if (dot > -1) {
                javaPrefix = setting.getClassName().substring(dot + 1);
                nameSpace = setting.getClassName().substring(0, dot);
                dot = nameSpace.lastIndexOf(".");
                if (dot > -1) {
                    nameSpace = nameSpace.substring(0, dot);
                }
            }
            setting.setJavaPrefix(javaPrefix);
            setting.setHtmlPrefix(javaPrefix.toLowerCase());
            setting.setNameSpace(nameSpace);
        }

        //文件路径
        String htmlDir = "";
        String[] level = new String[]{};
        if (!StrUtil.isEmpty(nameSpace)) {
            level = nameSpace.split("[.]");
        }
        //过滤掉com.cnpc
        for (int i = 2; i < level.length; i++) {
            htmlDir += (i < level.length - 1) ? level[i] + File.separator : level[i];
        }
        setting.setBusinessPackage(htmlDir.replace(File.separator, "/"));
        //request.getSession().getServletContext().getRealPath("/");
        String htmlPath = realPath + File.separator + "WEB-INF"
                + File.separator + "views" + File.separator + htmlDir;
        String javaPath = realPath.replaceAll("webapp", "java") + File.separator
                + setting.getNameSpace().replace(".", File.separator);
        setting.setHtmlPath(htmlPath);
        setting.setJavaPath(javaPath);

        //要编辑的属性列表
        Class<?> clazz = Class.forName(setting.getClassName());
        Field[] fields = clazz.getDeclaredFields();
        List<FieldSetting> fslist = new ArrayList<>();
        int i=0;
        for (Field field : fields) {
            if (field.getAnnotation(Column.class) == null && field.getAnnotation(JoinColumn.class) == null)
                continue;
            if (field.getAnnotation(Id.class) != null)
                continue;
            FieldSetting fs = new FieldSetting();
            fs.setRowIndex(++i);
            fs.setFieldParam(field);
            String name = field.getAnnotation(Header.class) != null ? field.getAnnotation(Header.class).name() : null;
            name = name != null ? name : (query.getColumn(fs.getColumnName()) != null ? query.getColumn(
                    fs.getColumnName()).getHeader() : null);
            name = name != null ? name : fs.getColumnName();
            fs.setLabelName(name);
            fs.setIsSelected("1");
            fs.setIsCondition("0");
            fslist.add(fs);
        }
        setting.setFields(fslist);
        setting.setHtmlTypes("list,addUpdate");
        setting.setJavaTypes("controller");
        setting.setIsCreateFunction("1");
        setting.setCurdType("dialog");
        return setting;
    }


    @RequestMapping(value = "/xml", method = RequestMethod.GET)
    public String configXML(String queryId, String className, String modelName, HttpServletRequest request) {
        request.setAttribute("queryId", queryId);
        request.setAttribute("className", className);
        request.setAttribute("modelName", modelName);
        return "tool/generator/generator_xml";
    }

    // 生成xml配置文件
    @RequestMapping(value = "generateXMLFile",method = RequestMethod.POST)
    @ResponseBody
    public Result generateXMLFile(String xmlContent, String xmlFile, HttpServletRequest request) {
        String xmlPath = request.getRealPath("/").replaceAll("webapp", "resources") + File.separator + "query"
                + File.separator + xmlFile;
        String rn = "\r\n";
        try {
            File file = new File(xmlPath);
            BufferedWriter output;
            String s;
            String s1 = new String();
            if (!file.exists()) {
                file.createNewFile();
                OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
                output = new BufferedWriter(write);
                output.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                output.write(rn);
                output.write("<queryContext xmlns=\"http://www.example.org/query\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.example.org/query query.xsd\">");
                output.write(rn);
                output.write("<!--本xml片段为代码生成器生成，时间：" + DateUtil.format(new Date(), DateUtil.formatStr_yyyyMMddHHmmss)
                        + "-->");
                output.write(rn);
                output.write(xmlContent);
                output.write(rn);
                output.write("</queryContext>");
                output.close();
                return new Result(true,"请刷新query下的"+xmlFile+"文件，查看首次生成xml配置");
            } else {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
                BufferedReader input = new BufferedReader(read);
                while ((s = input.readLine()) != null) {
                    s1 += s + rn;
                }
                s1 = s1.replaceAll("</queryContext>", "");
                OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
                output = new BufferedWriter(write);
                output.write(s1);
                output.write(rn);
                output.write("<!--本xml片段为代码生成器生成，时间：" + DateUtil.format(new Date(), DateUtil.formatStr_yyyyMMddHHmmss)
                        + "-->");
                output.write(rn);
                output.write(xmlContent);
                output.write(rn);
                output.write("</queryContext>");
                output.close();
            }
            return new Result(true,"请刷新query下的"+xmlFile+"文件，查看追加的xml配置");

        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false);
        }
    }


    //生成xml配置内容
    @RequestMapping(value = "/getXMLContent", method = RequestMethod.POST)
    @ResponseBody
    public Result getXMLContent(String queryId, String className, String modelName) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        String rn = "\r\n";
        // 生成query
        StringBuilder sb = new StringBuilder();
        sb.append("<query id=\"" + queryId + "\"");
        sb.append(" key=\"id\"");
        sb.append(" tableName=\"" + modelName + "列表\"");
        sb.append(" className=\"" + className + "\"");
        sb.append(" widthType=\"px\">");
        sb.append(rn);
        // 序号
        sb.append("        <column key=\"rowIndex\"");
        sb.append(" header=\"序号\"");
        sb.append(" width=\"80\"/>");
        sb.append(rn);

        Field[] fields = clazz.getDeclaredFields();
        String prefix = className.substring(className.lastIndexOf(".") + 1).toLowerCase();
        int i = 0;
        for (Field field : fields) {
            if (field.getAnnotation(Column.class) == null && field.getAnnotation(JoinColumn.class) == null)
                continue;
            if (field.getAnnotation(Id.class) != null)
                continue;
            FieldSetting fs = new FieldSetting();
            fs.setFieldParam(field);
            String name = field.getAnnotation(Header.class) != null ? field.getAnnotation(Header.class).name() : null;
            name = name != null ? name : fs.getColumnName();
            fs.setLabelName(name);
            fs.setIsSelected("1");
            fs.setIsCondition("0");
            sb.append("        <column key=\"" + fs.getColumnName().replace(".id",".name") + "\"");
            sb.append(" header=\"" + fs.getLabelName() + "\"");
            String classType=field.getType().getName().startsWith("com.cnpc")?String.class.getSimpleName():field.getType().getSimpleName();
            sb.append(" classType=\""+classType+"\"");
            if("textarea".equals(fs.getTagType())){
                sb.append(" align=\"left\"");
                sb.append(" allowSort=\"false\"");
            }else if("datepicker".equals(fs.getTagType())){
                sb.append(" allowSort=\"true\"");
                sb.append(" dateFormat=\"yyyy-mm-dd\"");
                sb.append(" operator=\"between\"");
            }else{
                sb.append(" allowSort=\"true\"");
            }
            //查看详情链接
           /* if (i == 0) {
                sb.append(" render=\"type=window,url=/" + prefix + "/show,param=id,winId=" + prefix
                        + "Win,winHeader=value,width=650,height=450,isMax=true\"");
            }*/
            sb.append(" width=\"150\"/>");
            i++;
            sb.append(rn);
        }

        sb.append("</query>");
        Result result = new Result();
        result.setMessage(sb.toString());
        return result;
    }


    //代码生成
    @RequestMapping(value="startGenerate",method = RequestMethod.POST)
    @ResponseBody
    public Result startGenerate(String object) throws IOException, TemplateException {
        GenerateSetting setting= JSON.parseObject(object,GenerateSetting.class);
        setting.setCurTime(new Date());
        setting.setAllFields(setting.getFields());
        List<FieldSetting> selectFields=new ArrayList<>();
        List<FieldSetting> leftFields=new ArrayList<>();
        for (FieldSetting fs : setting.getFields()) {
            if("1".equals(fs.getIsSelected())){
                selectFields.add(fs);
            }else{
                leftFields.add(fs);
            }
        }

        //创建功能菜单
        if ("1".equals(setting.getIsCreateFunction())&&!StrUtil.isEmpty(setting.getParFuncCode())) {
            // 获取父节点对象
            String hql = "from Function where code='" + setting.getParFuncCode() + "'";
            Function parFunc = codeGeneratorService.get(hql);
            setting.setParFuncName(parFunc.getName());
            if (parFunc != null) {
                // 如果code已经存在
                String code=setting.getJavaPrefix().toUpperCase();
                Function function = codeGeneratorService.get("from Function where code='" +code + "'");
                if (function == null) {
                    function=new Function();
                    function.setCode(code);
                    function.setParentId(parFunc.getId());
                    function.setLevelCode(codeGeneratorService.getLevelCode(Function.class.getName(), parFunc.getId()));
                    function.setFunctype("1");
                    //function.setUrl("/" + setting.getBusinessPackage() + "/" + setting.getHtmlPrefix() + "/list");
                    function.setUrl("/" + setting.getHtmlPrefix() + "/list");
                    function.setDeleted(1);
                    function.setCreateDateTime(new Date());
                    function.setUpdateDateTime(new Date());
                    function.setName(setting.getModelName() + "管理");
                    function.setIcon("fa fa-edit");
                    function.setRemark("本功能菜单为代码生成器生成，时间：" + DateUtil.format(new Date(), DateUtil.formatStr_yyyyMMddHHmmss));
                }
                codeGeneratorService.saveOrUpdate(function);
            }
        }

        //html文件生成
        String[] htmlArr;
        String fileName;
        if(!StrUtil.isEmpty(setting.getHtmlTypes())){
            htmlArr=setting.getHtmlTypes().split(",");
            setting.setFields(selectFields);
            for (String htmlType : htmlArr) {
                if("list".equals(htmlType)){
                    setting.setTitle(setting.getModelName()+"管理");
                    fileName=setting.getHtmlPrefix()+"_list.html";
                    FreeMarkerUtil.generateFile("list.html", setting.getHtmlPath() + File.separator + fileName, setting);
                } else if (htmlType.equals("addUpdate")) {
                    // System.out.println(FreeMarkerUtil.getTemplatePath("addUpdate.html"));
                    setting.setTitle("新增/编辑" + setting.getModelName());
                    fileName = setting.getHtmlPrefix() + "_edit.html";
                    FreeMarkerUtil.generateFile("edit.html", setting.getHtmlPath() + File.separator + fileName, setting);
                } else if (htmlType.equals("add")) {
                    setting.setTitle("新增" + setting.getModelName());
                    fileName = setting.getHtmlPrefix() + "_add.html";
                    FreeMarkerUtil.generateFile("add.html", setting.getHtmlPath() + File.separator + fileName, setting);
                } else if (htmlType.equals("update")) {
                    setting.setTitle("编辑" + setting.getModelName());
                    fileName = setting.getHtmlPrefix() + "_update.html";
                    FreeMarkerUtil.generateFile("update.jsp", setting.getHtmlPath() + File.separator + fileName, setting);
                }else if(htmlType.equals("show")){
                    setting.setTitle("查看" + setting.getModelName());
                    fileName = setting.getHtmlPrefix() + "_detail.html";
                    List<FieldSetting> fields = setting.getAllFields();
                    for (FieldSetting fs : fields) {
                        fs.setColumnName(fs.getColumnName().replaceAll(".id", ".name"));
                    }
                    List<FieldSetting> field_list=setting.getFields();
                    setting.setFields(fields);
                    FreeMarkerUtil.generateFile("detail.html", setting.getHtmlPath() + File.separator + fileName, setting);
                    setting.setFields(field_list);
                }
            }
        }

        //生成java后台文件
        String[] javaArr;
        if (!StrUtil.isEmpty(setting.getJavaTypes())) {
            javaArr=setting.getJavaTypes().split(",");
            for (String javaType : javaArr) {
                if ("controller".equals(javaType)) {
                    // ---------------------新增/更新丢掉的属性-----------------
                    List<String> dateFields = new ArrayList<String>();
                    List<String> leftDates = new ArrayList<String>();
                    List<String> leftList = new ArrayList<String>();
                    if (leftFields.size() > 0) {
                        //丢掉的日期类型 dateFields 设置值  leftDates 更新值
                        for (FieldSetting fs : leftFields) {
                            if (fs.getType().equals("Date")) {
                                String str_add = setting.getHtmlPrefix() + "." + "set"
                                        + fs.getColumnName().substring(0, 1).toUpperCase()
                                        + fs.getColumnName().substring(1, fs.getColumnName().length()) + "(new Date())";
                                dateFields.add(str_add);
                                String str_update = setting.getHtmlPrefix() + "_old." + "set"
                                        + fs.getColumnName().substring(0, 1).toUpperCase()
                                        + fs.getColumnName().substring(1, fs.getColumnName().length()) + "("
                                        + setting.getHtmlPrefix() + ".get"
                                        + fs.getColumnName().substring(0, 1).toUpperCase()
                                        + fs.getColumnName().substring(1, fs.getColumnName().length()) + "())";
                                leftDates.add(str_update);
                            }
                            leftList.add(fs.getColumnName());
                        }
                        setting.setDateFields(dateFields);
                        setting.setLeftDates(leftDates);
                        setting.setLeftFields(leftList);
                    }
                    // ------------------------------------------------------------
                    setting.setTitle(setting.getModelName() + "管理控制器");
                    fileName = setting.getJavaPrefix() + "Controller.java";
                    FreeMarkerUtil.generateFile("controller.html", setting.getJavaPath() + File.separator + "controller"
                            + File.separator + fileName, setting);
                }else if("service".equals(javaType)){
                    //接口
                    setting.setTitle(setting.getModelName()+"服务接口");
                    fileName=setting.getJavaPrefix()+"Service.java";
                    FreeMarkerUtil.generateFile("service.html",setting.getJavaPath()+File.separator+"service"+File.separator+fileName,setting);
                    //实现
                    setting.setTitle(setting.getModelName()+"服务实现");
                    fileName=setting.getJavaPrefix()+"ServiceImpl.java";
                    FreeMarkerUtil.generateFile("serviceImpl.html",setting.getJavaPath()+File.separator+"service"+File.separator+fileName,setting);
                }
            }
        }


        return new Result();
    }





}

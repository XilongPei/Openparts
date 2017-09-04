package com.cnpc.framework.utils;

import com.cnpc.framework.base.pojo.GenerateSetting;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.*;

public class FreeMarkerUtil {
	/**
	 * 获取模板路径
	 * 
	 * @param templateName
	 *            模板名称（含后缀名）
	 * @return
	 * @throws IOException
	 */
	public static String getTemplatePath(String templateName) throws IOException {
		Resource res = FreeMarkerUtil.getResource(templateName);
		return res.getFile().getPath();
	}

	/**
	 * 获取模板资源
	 * 
	 * @param templateName
	 *            模板名称（含后缀名）
	 * @return Resource
	 */
	public static Resource getResource(String templateName) {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource res = resolver.getResource("/template/" + templateName);
		return res;
	}

	/**
	 * 获取模板
	 * 
	 * @param templateName
	 *            模板名称（含后缀名）
	 * @return Template
	 * @throws IOException
	 */
	public static Template getTemplate(String templateName) throws IOException {
		Configuration cfg = new Configuration();
		Template temp = null;
		File tmpRootFile = getResource(templateName).getFile().getParentFile();
		if (tmpRootFile == null) {
			throw new RuntimeException("无法取得模板根路径！");
		}
		try {
			cfg.setDefaultEncoding("utf-8");
			cfg.setOutputEncoding("utf-8");
			cfg.setDirectoryForTemplateLoading(tmpRootFile);
			/* cfg.setDirectoryForTemplateLoading(getResourceURL()); */
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			temp = cfg.getTemplate(templateName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 根据freemark模板生成文件
	 * 
	 * @param templateName
	 *            模板名称（含后缀名）
	 * @param filePath
	 *            生成文件路径
	 * @param setting
	 *            参数
	 */
	public static void generateFile(String templateName, String filePath, GenerateSetting setting)
			throws TemplateException, IOException {
		Writer writer = null;
		Template template = getTemplate(templateName);
		String dir = filePath.substring(0, filePath.lastIndexOf("\\"));
		File fdir = new File(dir);
		if (!fdir.exists()) {
			if (!fdir.mkdirs()) {
				System.out.println("创建目录" + fdir.getAbsolutePath() + "失败");
				return;
			}
		}
		File file = new File(filePath);
		if(file.exists())
			file.delete(); 
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
		template.setEncoding("utf-8");
		template.process(setting, writer);
		writer.flush();
		writer.close();
	}

}

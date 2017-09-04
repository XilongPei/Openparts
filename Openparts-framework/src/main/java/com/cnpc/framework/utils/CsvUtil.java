package com.cnpc.framework.utils;

import com.cnpc.framework.base.pojo.CsvPOJO;
import com.cnpc.framework.base.pojo.CsvRow;
import org.springframework.core.io.Resource;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * cnpc http://blog.csdn.net/jrn1012
 */
public class CsvUtil {
	public static CsvPOJO getCaseList(String fileName) {
		CsvPOJO pojo = null;
		Resource[] resources = ConfigurationUtil.getAllResources("/**/" + fileName);
		if (resources == null || resources.length == 0) {
			throw new RuntimeException("CSV文件《" + fileName + "》没有找到！");
		}
		// for(Resource resouce:resources){
		// if(fileName.equals(resouce.getFilename())){
		try {
			pojo = readWithCsvMapReader(resources[0].getFile().getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// }
		// }
		return pojo;
	}

	private static CsvPOJO readWithCsvMapReader(String file) throws Exception {
		CsvPOJO csvPojo = new CsvPOJO();
		Map<String, String> readMap = null;
		ICsvMapReader mapReader = null;
		try {
			mapReader = new CsvMapReader(new FileReader(file), CsvPreference.STANDARD_PREFERENCE);
			String[] headers = mapReader.getHeader(true);
			List<CsvRow> rows = new ArrayList<CsvRow>();
			while ((readMap = mapReader.read(headers)) != null) {
				CsvRow row = new CsvRow();
				List<String> columns = new ArrayList<String>();
				for (String h : headers) {
					if (!StrUtil.isEmpty(h)) {
						columns.add(readMap.get(h));
					}
				}
				row.setCols(columns);
				rows.add(row);
			}
			csvPojo.setHeaders(headers);
			csvPojo.setRows(rows);

		} finally {
			if (mapReader != null) {
				mapReader.close();
			}
		}
		return csvPojo;
	}

}

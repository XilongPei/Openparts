package com.cnpc.framework.testng;


import com.cnpc.framework.base.pojo.CsvPOJO;
import com.cnpc.framework.base.pojo.CsvRow;
import com.cnpc.framework.utils.CsvUtil;
import com.cnpc.framework.utils.DateUtil;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cnpc on 2016/10/22.
 */
@ContextConfiguration(locations = {"classpath*:spring.xml" })
@TransactionConfiguration(defaultRollback = true)
public class BaseTest extends AbstractTestNGSpringContextTests {

    @DataProvider
    public Object[][] dataProvider(ITestContext context, Method method) {
        CsvPOJO csvPojo = CsvUtil.getCaseList(this.getClass().getSimpleName() + "_" + method.getName() + ".csv");
        Class<?>[] types = method.getParameterTypes();
        int param_len = types.length;

        int size = csvPojo.getRows().size();
        Object[][] result = new Object[size][];
        for (int i = 0; i < size; i++) {
            CsvRow row = csvPojo.getRows().get(i);
            List<String> objList = row.getCols();
            int obj_len = objList.size();
            if (param_len != obj_len) {
                throw new RuntimeException("CSV参数个数与测试方法" + method.getName() + "参数个数不一致");
            }
            // 类型转换
            List<Object> obj_list = new ArrayList<Object>();
            for (int j = 0; j < obj_len; j++) {
                if (types[j] == String.class) {
                    obj_list.add(objList.get(j));
                } else if (types[j] == Integer.class) {
                    obj_list.add(Integer.valueOf(objList.get(j)));
                } else if (types[j] == Double.class) {
                    obj_list.add(Double.valueOf(objList.get(j)));
                } else if (types[j] == Float.class) {
                    obj_list.add(Float.valueOf(objList.get(j)));
                } else if (types[j] == Date.class) {
                    obj_list.add(DateUtil.parseToDate(objList.get(j)));
                } else if (types[j] == Boolean.class) {
                    obj_list.add(Boolean.valueOf(objList.get(j)));
                    // TO DO 各种扩展
                }else if(types[j]==Long.class){
                    obj_list.add(Long.valueOf(objList.get(j)));
                }

            }
            result[i] = obj_list.toArray();
        }
        return result;
    }


}

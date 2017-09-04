package com.test.demo;

import com.cnpc.framework.testng.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;


public class DemoTest extends BaseTest {

	@Test(dataProvider = "dataProvider", groups = { "function-test" })
	public void testArea(String caseId, Double w, String l, String h, String check) {
		Assert.assertEquals(w * Double.parseDouble(l) + Double.parseDouble(h), Double.parseDouble(check));
	}


}

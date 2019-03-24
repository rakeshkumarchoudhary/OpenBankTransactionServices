package com.openbank.transactions.util;

import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.util.List;

import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import com.openbank.transactions.model.OpenBankTransactionBO;

public class OpenBankTransactionsUtilTest 
{
	
	private OpenBankTransactionsUtil testUtil;
	
	private String jsonString;
	
	private String transType;
	
	JSONParser jsonParser = new JSONParser();
	
	@Before
	public void setup() throws Exception {
		FileReader reader = new FileReader("transactions_stub.json");
		Object obj = jsonParser.parse(reader);
		jsonString =obj.toString();
		
		transType = "sandbox-payment";
	}
	
	@Test
	@SuppressWarnings("static-access")
	public void testGetJSONObjectFromJSONString()
	{
		List<OpenBankTransactionBO> transBOList = testUtil.getJSONObjectFromJSONString(jsonString);
		assertTrue(transBOList.size()>0);
	}
	
	@Test
	@SuppressWarnings("static-access")
	public void testGetJSONObjectFromJSONStringForTransType()
	{
		
		List<OpenBankTransactionBO> transBOList = testUtil.getJSONObjectFromJSONString(jsonString, transType);
		assertTrue(transBOList.size()>0);
	}
}

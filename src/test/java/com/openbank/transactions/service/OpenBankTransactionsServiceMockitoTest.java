package com.openbank.transactions.service;

import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.util.List;

import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.openbank.transactions.model.OpenBankTransactionBO;

@RunWith(MockitoJUnitRunner.class)
public class OpenBankTransactionsServiceMockitoTest {

	@InjectMocks
	OpenBankTransactionsServiceImpl openBankTransactionsService;
	
	@Mock
	RestTemplate restTemplate;
	
	private String jsonString;
	
	private String transType;
	
	private String openBankSandboxTransactionsEndPoint;
	
	JSONParser jsonParser = new JSONParser();
	
	@Before
	public void setup() throws Exception {
		FileReader reader = new FileReader("transactions_stub.json");
		Object obj = jsonParser.parse(reader);
		jsonString =obj.toString();
		transType = "sandbox-payment";
		ReflectionTestUtils.setField(openBankTransactionsService, "baseURL", "https://apisandbox.openbankproject.com");
		ReflectionTestUtils.setField(openBankTransactionsService, "resourcePath", "/obp/v1.2.1/banks/rbs/accounts/savings-kids-john/public/transactions");		
		openBankSandboxTransactionsEndPoint="https://apisandbox.openbankproject.com/obp/v1.2.1/banks/rbs/accounts/savings-kids-john/public/transactions";
	}
	
	
	@Test
	public void testGetAllOpenBankSandboxTransactions()
	{
		Mockito.when(restTemplate.getForObject(openBankSandboxTransactionsEndPoint,String.class)).thenReturn(jsonString);
		List<OpenBankTransactionBO> transBOList = openBankTransactionsService.getAllOpenBankSandboxTransactions();
		assertTrue(transBOList.size()>0);
	}
	
	
	@Test
	public void testGetOpenBankSandboxTransactionsforTransType()
	{
		Mockito.when(restTemplate.getForObject(openBankSandboxTransactionsEndPoint,String.class)).thenReturn(jsonString);
		List<OpenBankTransactionBO> transBOList = openBankTransactionsService.getOpenBankSandboxTransactionsforTransType(transType);
		assertTrue(transBOList.size()>0);
	}

	

	
}
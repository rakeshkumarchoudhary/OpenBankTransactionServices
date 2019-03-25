package com.openbank.transactions.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.openbank.transactions.model.OpenBankTransactionBO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringTestContext.class)
public class OpenBankTransactionsServiceTest {

	@Autowired
	OpenBankTransactionsServiceImpl openBankTransactionsService;
	
	@Autowired
	RestTemplate restTemplate;
	
	
	
	@Test
	public void testGetAllOpenBankSandboxTransactions()
	{
		List<OpenBankTransactionBO> transBOList = openBankTransactionsService.getAllOpenBankSandboxTransactions();
		assertTrue(transBOList.size()>0);
	}
	
	@Test
	public void testGetOpenBankSandboxTransactionsforTransType()
	{
		String transType="sandbox-payment";
		List<OpenBankTransactionBO> transBOList = openBankTransactionsService.getOpenBankSandboxTransactionsforTransType(transType);
		assertTrue(transBOList.size()>0);
	}
}

@Configuration
@ComponentScan({"com.openbank.transactions.beanconfiguration","com.openbank.transactions.service"})
class SpringTestContext
{
	
}
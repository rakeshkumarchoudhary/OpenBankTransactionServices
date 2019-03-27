package com.openbank.transactions.controller;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openbank.transactions.beanconfiguration.OpenBankTransactionsBeanConfig;
import com.openbank.transactions.exception.TransactionNotFoundException;
import com.openbank.transactions.model.OpenBankTransactionBO;
import com.openbank.transactions.service.OpenBankTransactionsServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class OpenBankTransactionsRestControllerMockitoTest {
	
	@Mock
	private OpenBankTransactionsServiceImpl serviceImpl;
	
	@InjectMocks
	private OpenBankTransactionsRestController restController = new OpenBankTransactionsRestController();
	
	@Mock
	private ObjectMapper mapper;
	
	private String jsonString;
	
	private String transType;
	
	JSONParser jsonParser = new JSONParser();
	
	private static ApplicationContext ctx = 
	         new AnnotationConfigApplicationContext(OpenBankTransactionsBeanConfig.class);
	
	@Before
	public void setup() throws Exception {
		jsonString ="";
		transType = "sandbox-payment";
	}
	
	@Test
	public void testGetAllTransactions() throws Exception {
		
		List<OpenBankTransactionBO> transBOList = new ArrayList<OpenBankTransactionBO>();
		OpenBankTransactionBO transBO = ctx.getBean(OpenBankTransactionBO.class);
		transBOList.add(transBO);
		Mockito.when(serviceImpl.getAllOpenBankSandboxTransactions()).thenReturn(transBOList);
		Mockito.when(mapper.writeValueAsString(transBOList)).thenReturn(jsonString);
		ResponseEntity<String> res =  restController.getAllTransactions();
		assertTrue(res.getStatusCode()==HttpStatus.OK);
		
	}
	
	@Test
	public void testGetAllTransactionsforTransType() throws Exception {
		
		List<OpenBankTransactionBO> transBOList = new ArrayList<OpenBankTransactionBO>();
		OpenBankTransactionBO transBO = ctx.getBean(OpenBankTransactionBO.class);
		transBOList.add(transBO);
		Mockito.when(serviceImpl.getOpenBankSandboxTransactionsforTransType(transType)).thenReturn(transBOList);
		Mockito.when(mapper.writeValueAsString(transBOList)).thenReturn(jsonString);
		ResponseEntity<String> res =  restController.getAllTransactionsforTransType(transType);
		assertTrue(res.getStatusCode()==HttpStatus.OK);
		
	}

	
	@Test
	public void testGetTotalTransactionAmountforTransType() throws Exception {
		
		List<OpenBankTransactionBO> transBOList = new ArrayList<OpenBankTransactionBO>();
		OpenBankTransactionBO transBO = ctx.getBean(OpenBankTransactionBO.class);
		transBO.setTransactionAmount(30.7);
		transBOList.add(transBO);
		Mockito.when(serviceImpl.getOpenBankSandboxTransactionsforTransType(transType)).thenReturn(transBOList);
		ResponseEntity<String> res =  restController.getTotalTransactionAmountforTransType(transType);
		assertTrue(res.getStatusCode()==HttpStatus.OK);
		
	}


	@Test (expected=TransactionNotFoundException.class)	
	public void testTransactionNotFound() throws Exception {
		
		List<OpenBankTransactionBO> transBOList = new ArrayList<OpenBankTransactionBO>();
		Mockito.when(serviceImpl.getOpenBankSandboxTransactionsforTransType("sand12345")).thenReturn(transBOList);
		ResponseEntity<String> res =  restController.getAllTransactionsforTransType("sand12345");
		assertTrue(res.getStatusCode()==HttpStatus.NOT_FOUND);
		
	}


}


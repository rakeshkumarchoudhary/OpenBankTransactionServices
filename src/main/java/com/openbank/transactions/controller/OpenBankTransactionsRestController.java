package com.openbank.transactions.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openbank.transactions.exception.TransactionNotFoundException;
import com.openbank.transactions.model.OpenBankTransactionBO;
import com.openbank.transactions.service.IOpenBankTransactionsService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Rakesh
 *
 */
@RestController
@Slf4j
public class OpenBankTransactionsRestController 
{
	
	@Autowired
	IOpenBankTransactionsService openBankService;
	
	@Autowired
	ObjectMapper mapper;
	
	/**
	 * 
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/allTransactions", method = RequestMethod.GET,headers="Accept=application/json")
	public ResponseEntity<String> getAllTransactions() throws JsonProcessingException
	{
		log.info("Entering method getAllTransactions ....");
		List<OpenBankTransactionBO> transBOList = openBankService.getAllOpenBankSandboxTransactions();
		if(transBOList.isEmpty())
			throw new TransactionNotFoundException();
		String jsonString = mapper.writeValueAsString(transBOList);
		return new ResponseEntity<String>(jsonString, HttpStatus.OK);	
		
	}
	
	/**
	 * 
	 * @param transType
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/allTransactionsforTransType/{transType}", method = RequestMethod.GET,headers="Accept=application/json")
	public ResponseEntity<String> getAllTransactionsforTransType(@PathVariable String transType) 
			throws JsonProcessingException
	{
		log.info("Entering method getAllTransactionsforTransType .... "+transType);
		List<OpenBankTransactionBO> transBOList = openBankService.getOpenBankSandboxTransactionsforTransType(transType);
		if(transBOList.isEmpty())
			throw new TransactionNotFoundException();
		String jsonString = mapper.writeValueAsString(transBOList);
		return new ResponseEntity<String>(jsonString, HttpStatus.OK);	
		
	}
	
	@RequestMapping(value = "/totalAmountforTransType/{transType}", method = RequestMethod.GET,headers="Accept=application/json")
	public ResponseEntity<String> getTotalTransactionAmountforTransType(@PathVariable String transType)		
	{
		log.info("Entering method getTotalTransactionAmountforTransType ...."+transType);
		String totalTransactionAmount="0.0";
		List<OpenBankTransactionBO> transBOList = openBankService.getOpenBankSandboxTransactionsforTransType(transType);
		if(transBOList.isEmpty())
			throw new TransactionNotFoundException();
		totalTransactionAmount = new Double(transBOList.stream().mapToDouble(n -> n.getTransactionAmount()).sum()).toString();
		return new ResponseEntity<String>(totalTransactionAmount, HttpStatus.OK);	
		
	}


}

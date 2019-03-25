package com.openbank.transactions.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.openbank.transactions.model.OpenBankTransactionBO;
import com.openbank.transactions.util.OpenBankTransactionsUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Rakesh
 *
 */
@Service
@Slf4j
public class OpenBankTransactionsServiceImpl implements IOpenBankTransactionsService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${openbank.sandbox.base_url}")
	private String baseURL;
	
	@Value("${openbank.sandbox.resource_path}")
	private String resourcePath;
	
	
	/* (non-Javadoc)
	 * @see com.openbank.transactions.service.IOpenBankTransactionsService#getAllOpenBankSandboxTransactions()
	 */
	@Override
	public List<OpenBankTransactionBO> getAllOpenBankSandboxTransactions()
	{
		log.info("Entering method getAllOpenBankSandboxTransactions ....");
		log.info("OpenBank Sandbox Transactions URI = "+ baseURL.concat(resourcePath));
		String result = restTemplate.getForObject(baseURL.concat(resourcePath), String.class);	
		log.info("Exiting method getAllOpenBankSandboxTransactions ....");
		return OpenBankTransactionsUtil.getJSONObjectFromJSONString(result);
	}
	
	
	/* (non-Javadoc)
	 * @see com.openbank.transactions.service.IOpenBankTransactionsService#getOpenBankSandboxTransactionsforTransType(java.lang.String)
	 */
	@Override
	public List<OpenBankTransactionBO> getOpenBankSandboxTransactionsforTransType(String transType)
	{
		log.info("Entering method getOpenBankSandboxTransactionsforTransType ...."+transType);
		log.info("OpenBank Sandbox Transactions URI = "+ baseURL.concat(resourcePath));
		String result = restTemplate.getForObject(baseURL.concat(resourcePath), String.class);
		log.info("Exiting method getOpenBankSandboxTransactionsforTransType ....");
		return OpenBankTransactionsUtil.getJSONObjectFromJSONStringforTransType(result, transType);
	}


}

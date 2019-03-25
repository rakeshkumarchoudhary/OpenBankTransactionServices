package com.openbank.transactions.service;

import java.util.List;

import com.openbank.transactions.model.OpenBankTransactionBO;

/**
 * 
 * @author Rakesh
 *
 */
public interface IOpenBankTransactionsService {

	/**
	 * 
	 * @return
	 */
	List<OpenBankTransactionBO> getAllOpenBankSandboxTransactions();

	/**
	 * 
	 * @return
	 */
	List<OpenBankTransactionBO> getOpenBankSandboxTransactionsforTransType(String transType);

}
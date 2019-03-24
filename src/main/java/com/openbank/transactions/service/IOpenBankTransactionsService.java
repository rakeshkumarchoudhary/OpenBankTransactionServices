package com.openbank.transactions.service;

import java.util.List;

import com.openbank.transactions.model.OpenBankTransactionBO;

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
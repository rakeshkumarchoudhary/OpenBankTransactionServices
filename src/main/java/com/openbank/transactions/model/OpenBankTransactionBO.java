package com.openbank.transactions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OpenBankTransactionBO {

	private String id;
	private String accountId;
	private String counterpartyAccount;
	private String counterpartyName;
	private String counterPartyLogoPath;
	private Double instructedAmount;
	private String instructedCurrency;
	private Double transactionAmount;
	private String transactionCurrency;
	private String transactionType;
	private String description;
	
}

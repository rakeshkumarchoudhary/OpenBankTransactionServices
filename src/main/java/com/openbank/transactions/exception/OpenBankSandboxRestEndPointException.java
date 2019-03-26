package com.openbank.transactions.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Rakesh
 *
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OpenBankSandboxRestEndPointException extends RuntimeException { 
	 private static final long serialVersionUID = 1L; 
	 
	 private String errorCode;
	 private String errorMessage;
	 
	 
} 
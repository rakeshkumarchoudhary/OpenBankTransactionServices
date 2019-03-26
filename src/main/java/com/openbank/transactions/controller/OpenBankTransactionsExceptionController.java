package com.openbank.transactions.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.openbank.transactions.exception.OpenBankSandboxRestEndPointException;
import com.openbank.transactions.exception.TransactionNotFoundException;

/**
 * 
 * @author Rakesh
 *
 */
@ControllerAdvice
@EnableWebMvc
public class OpenBankTransactionsExceptionController{
	
	/**
	 * 
	 * @param exp
	 * @return
	 */
	@ExceptionHandler(value = TransactionNotFoundException.class) 
	public ResponseEntity<String> exception(TransactionNotFoundException exp) { 
	  return new ResponseEntity<String>("Error Message: "+exp.getErrorMessage(), HttpStatus.NOT_FOUND); 
	 }
	
	
	/**
	 * 
	 * @param exp
	 * @return
	 */
	@ExceptionHandler(value = OpenBankSandboxRestEndPointException.class) 
	public ResponseEntity<String> openBankSandboxException(OpenBankSandboxRestEndPointException exp) { 
	  return new ResponseEntity<String>("Error Message: "+exp.getErrorMessage(), HttpStatus.NOT_FOUND); 
	 }


}

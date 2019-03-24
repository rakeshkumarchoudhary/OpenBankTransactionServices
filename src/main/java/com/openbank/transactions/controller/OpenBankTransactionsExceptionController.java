package com.openbank.transactions.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.openbank.transactions.exception.TransactionNotFoundException;

@ControllerAdvice
@EnableWebMvc
public class OpenBankTransactionsExceptionController{
	
	@ExceptionHandler(value = TransactionNotFoundException.class) 
	public ResponseEntity<String> exception(TransactionNotFoundException exception) { 
	  return new ResponseEntity<String>(new String("Transaction not found"), HttpStatus.NOT_FOUND); 
	 }

}

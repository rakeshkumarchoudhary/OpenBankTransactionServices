package com.openbank.transactions.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringTestContext.class })
@WebAppConfiguration
public class OpenBankTransactionsRestControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	@Before
	public void setup() throws Exception {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void testGetAllTransactions() throws Exception {
	    MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/allTransactions").accept(MediaType.APPLICATION_JSON)).andReturn();
	    assertTrue(mvcResult.getResponse().getStatus() == 200);    
	      
	}
	
	@Test
	public void testGetAllTransactionsforTransType() throws Exception {
	    MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/allTransactionsforTransType/sandbox-payment").accept(MediaType.APPLICATION_JSON)).andReturn();
	    assertTrue(mvcResult.getResponse().getStatus() == 200);	       
	      
	}
	
	
	@Test
	public void testTransactionNotFound() throws Exception {
	    MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/allTransactionsforTransType/sandbox-payment1234").accept(MediaType.APPLICATION_JSON)).andReturn();
	    assertTrue(mvcResult.getResponse().getStatus() == 404);	       
	      
	}
	
	
	@Test
	public void testGetTotalTransactionAmountforTransType() throws Exception {
	    MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/totalAmountforTransType/SANDBOX_TAN").accept(MediaType.APPLICATION_JSON)).andReturn();
	    assertTrue(mvcResult.getResponse().getStatus() == 200);       
	      
	}


}

@Configuration
@ComponentScan("com.openbank.transactions")
class SpringTestContext
{
	
}

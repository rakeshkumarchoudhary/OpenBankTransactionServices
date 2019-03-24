package com.openbank.transactions.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.openbank.transactions.beanconfiguration.OpenBankTransactionsBeanConfig;
import com.openbank.transactions.model.OpenBankTransactionBO;

public class OpenBankTransactionsUtil 
{

	private static ApplicationContext ctx = 
	         new AnnotationConfigApplicationContext(OpenBankTransactionsBeanConfig.class);
	
	/**
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static List<OpenBankTransactionBO> getJSONObjectFromJSONString(String jsonStr)
	{
		List<OpenBankTransactionBO> transBOList = new ArrayList<OpenBankTransactionBO>();
		JSONObject jsonObj = new JSONObject(jsonStr);		
		JSONArray jsonArr = jsonObj.getJSONArray("transactions");
		
		for(int transactionIndex=0;transactionIndex<jsonArr.length();++transactionIndex)
		{
			OpenBankTransactionBO transBO = ctx.getBean(OpenBankTransactionBO.class);
			mappingToOpenBankTransactionsBO(jsonArr, transactionIndex, transBO);
			
			transBOList.add(transBO);
		}
		return transBOList;
		
		
	}

	/**
	 * 
	 * @param jsonStr
	 * @param transType
	 * @return
	 */
	public static List<OpenBankTransactionBO> getJSONObjectFromJSONString(String jsonStr, String transType)
	{
		List<OpenBankTransactionBO> transBOList = new ArrayList<OpenBankTransactionBO>();
		
		JSONObject jsonObj = new JSONObject(jsonStr);		
		JSONArray jsonArr = jsonObj.getJSONArray("transactions");
		
		for(int transactionIndex=0;transactionIndex<jsonArr.length();++transactionIndex)
		{
			if(transType.equals(jsonArr.getJSONObject(transactionIndex).getJSONObject("details").optString("type")))
			{
				OpenBankTransactionBO transBO = ctx.getBean(OpenBankTransactionBO.class);
				mappingToOpenBankTransactionsBO(jsonArr, transactionIndex, transBO);
				
				transBOList.add(transBO);
			}
		}
		
		return transBOList;
		
		
	}
	
	

	/**
	 * @param jsonArr
	 * @param transactionIndex
	 * @param transBO
	 */
	private static void mappingToOpenBankTransactionsBO(JSONArray jsonArr, int transactionIndex,
			OpenBankTransactionBO transBO) {
		
		transBO.setId(jsonArr.getJSONObject(transactionIndex).optString("id"));
		transBO.setAccountId(jsonArr.getJSONObject(transactionIndex).getJSONObject("this_account").optString("id"));
		transBO.setCounterpartyAccount(jsonArr.getJSONObject(transactionIndex).getJSONObject("other_account").optString("number"));
		transBO.setCounterpartyName(jsonArr.getJSONObject(transactionIndex).getJSONObject("other_account").getJSONObject("holder").optString("name"));
		transBO.setCounterPartyLogoPath(jsonArr.getJSONObject(transactionIndex).getJSONObject("other_account").getJSONObject("metadata").optString("image_URL"));
		String amount= jsonArr.getJSONObject(transactionIndex).getJSONObject("details").getJSONObject("value").optString("amount");
		String currency= jsonArr.getJSONObject(transactionIndex).getJSONObject("details").getJSONObject("value").optString("currency");
		transBO.setInstructedAmount(new Double(amount));
		transBO.setInstructedCurrency(currency);
		transBO.setTransactionAmount(new Double(amount));
		transBO.setTransactionCurrency(currency);
		transBO.setTransactionType(jsonArr.getJSONObject(transactionIndex).getJSONObject("details").optString("type"));
		transBO.setDescription(jsonArr.getJSONObject(transactionIndex).getJSONObject("details").optString("description"));
		
	}


}

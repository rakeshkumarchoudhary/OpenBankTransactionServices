package com.openbank.transactions.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.openbank.transactions.beanconfiguration.OpenBankTransactionsBeanConfig;
import com.openbank.transactions.model.OpenBankTransactionBO;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Rakesh
 *
 */
@Slf4j
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
		log.info("Entering method getJSONObjectFromJSONString ....");
		List<OpenBankTransactionBO> transBOList = new ArrayList<OpenBankTransactionBO>();
		JSONObject jsonObj = new JSONObject(jsonStr);		
		JSONArray jsonArr = jsonObj.getJSONArray("transactions");
		
		jsonArr.forEach(n -> {			
			OpenBankTransactionBO transBO = ctx.getBean(OpenBankTransactionBO.class);
			mappingToOpenBankTransactionsBO(new JSONObject(n.toString()), transBO);
			transBOList.add(transBO);
		});

		log.info("Exiting method getJSONObjectFromJSONString ....");
		return transBOList;
		
		
	}

	/**
	 * 
	 * @param jsonStr
	 * @param transType
	 * @return
	 */
	public static List<OpenBankTransactionBO> getJSONObjectFromJSONStringforTransType(String jsonStr, String transType)
	{
		log.info("Entering method getJSONObjectFromJSONStringforTransType ....");
		List<OpenBankTransactionBO> transBOList = new ArrayList<OpenBankTransactionBO>();
		
		JSONObject jsonObj = new JSONObject(jsonStr);		
		JSONArray jsonArr = jsonObj.getJSONArray("transactions");
		jsonArr.forEach(n -> {
			if( transType.equals( new JSONObject(n.toString()).getJSONObject("details").optString("type"))) 
			{
				OpenBankTransactionBO transBO = ctx.getBean(OpenBankTransactionBO.class);
				mappingToOpenBankTransactionsBO(new JSONObject(n.toString()), transBO);
				transBOList.add(transBO);
			}
		});
		
		log.info("Exiting method getJSONObjectFromJSONStringforTransType ....");
		return transBOList;
		
	}
	
	

	/**
	 * @param jsonArr
	 * @param transactionIndex
	 * @param transBO
	 */
	private static void mappingToOpenBankTransactionsBO(JSONObject jsonObject,
			OpenBankTransactionBO transBO) {
		
		transBO.setId(jsonObject.optString("id"));
		transBO.setAccountId(jsonObject.getJSONObject("this_account").optString("id"));
		transBO.setCounterpartyAccount(jsonObject.getJSONObject("other_account").optString("number"));
		transBO.setCounterpartyName(jsonObject.getJSONObject("other_account").getJSONObject("holder").optString("name"));
		transBO.setCounterPartyLogoPath(jsonObject.getJSONObject("other_account").getJSONObject("metadata").optString("image_URL"));
		String amount= jsonObject.getJSONObject("details").getJSONObject("value").optString("amount");
		String currency= jsonObject.getJSONObject("details").getJSONObject("value").optString("currency");
		transBO.setInstructedAmount(new Double(amount));
		transBO.setInstructedCurrency(currency);
		transBO.setTransactionAmount(new Double(amount));
		transBO.setTransactionCurrency(currency);
		transBO.setTransactionType(jsonObject.getJSONObject("details").optString("type"));
		transBO.setDescription(jsonObject.getJSONObject("details").optString("description"));
	}


}

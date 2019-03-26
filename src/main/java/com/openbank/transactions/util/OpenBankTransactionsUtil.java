package com.openbank.transactions.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

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
		if(checkJsonkeyExists(jsonObj,"transactions"))
		{
			JSONArray jsonArr = jsonObj.getJSONArray("transactions");			
			StreamSupport.stream(jsonArr.spliterator(), false).
			map(val -> (JSONObject) val).
			forEach(jObj -> {			
				OpenBankTransactionBO transBO = ctx.getBean(OpenBankTransactionBO.class);
				mappingToOpenBankTransactionsBO(jObj, transBO);
				transBOList.add(transBO);
			});
		}

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
		if(checkJsonkeyExists(jsonObj,"transactions"))
		{
			JSONArray jsonArr = jsonObj.getJSONArray("transactions");			
			StreamSupport.stream(jsonArr.spliterator(), false).
			map(val -> (JSONObject) val).
			filter(jObj -> checkJsonkeyExists(jObj,"details") 
					&& checkJsonkeyExists(jObj.getJSONObject("details"), "type") 
					&& transType.equalsIgnoreCase(jObj.getJSONObject("details").optString("type"))).
			forEach(jsnObj -> {			
				OpenBankTransactionBO transBO = ctx.getBean(OpenBankTransactionBO.class);
				mappingToOpenBankTransactionsBO(jsnObj, transBO);
				transBOList.add(transBO);
			});
		}
		
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
		
		String amount= "";
		String currency="";
		if(checkJsonkeyExists(jsonObject,"id"))				
			transBO.setId(jsonObject.optString("id"));
		
		if(checkJsonkeyExists(jsonObject,"this_account") 
				&& checkJsonkeyExists(jsonObject.getJSONObject("this_account"), "id"))
			transBO.setAccountId(jsonObject.getJSONObject("this_account").optString("id"));
		
		if(checkJsonkeyExists(jsonObject,"other_account") 
				&& checkJsonkeyExists(jsonObject.getJSONObject("other_account"), "number"))
			transBO.setCounterpartyAccount(jsonObject.getJSONObject("other_account").optString("number"));
		
		if(checkJsonkeyExists(jsonObject,"other_account") 
				&& checkJsonkeyExists(jsonObject.getJSONObject("other_account"), "holder") 
					&& checkJsonkeyExists(jsonObject.getJSONObject("other_account").getJSONObject("holder"), "name"))
			transBO.setCounterpartyName(jsonObject.getJSONObject("other_account").getJSONObject("holder").optString("name"));
		
		if(checkJsonkeyExists(jsonObject,"other_account") 
				&& checkJsonkeyExists(jsonObject.getJSONObject("other_account"), "metadata") 
					&& checkJsonkeyExists(jsonObject.getJSONObject("other_account").getJSONObject("metadata"), "image_URL"))
			transBO.setCounterPartyLogoPath(jsonObject.getJSONObject("other_account").getJSONObject("metadata").optString("image_URL"));
		
		if(checkJsonkeyExists(jsonObject,"details") 
				&& checkJsonkeyExists(jsonObject.getJSONObject("details"), "value") 
					&& checkJsonkeyExists(jsonObject.getJSONObject("details").getJSONObject("value"), "amount"))
			amount= jsonObject.getJSONObject("details").getJSONObject("value").optString("amount");
		
		if(checkJsonkeyExists(jsonObject,"details") 
				&& checkJsonkeyExists(jsonObject.getJSONObject("details"), "value") 
					&& checkJsonkeyExists(jsonObject.getJSONObject("details").getJSONObject("value"), "currency"))
			currency= jsonObject.getJSONObject("details").getJSONObject("value").optString("currency");
		
		if(checkJsonkeyExists(jsonObject,"details") 
				&& checkJsonkeyExists(jsonObject.getJSONObject("details"), "type"))
			transBO.setTransactionType(jsonObject.getJSONObject("details").optString("type"));
		
		if(checkJsonkeyExists(jsonObject,"details") 
				&& checkJsonkeyExists(jsonObject.getJSONObject("details"), "description"))
			transBO.setDescription(jsonObject.getJSONObject("details").optString("description"));
		
		transBO.setInstructedAmount(new Double(amount));
		transBO.setInstructedCurrency(currency);
		transBO.setTransactionAmount(new Double(amount));
		transBO.setTransactionCurrency(currency);
	}

	/**
	 * 
	 * @param jsonObj
	 * @param key
	 * @return
	 */
	public static boolean checkJsonkeyExists(JSONObject jsonObj, String key)
	{
		return jsonObj.has(key);
	}
}

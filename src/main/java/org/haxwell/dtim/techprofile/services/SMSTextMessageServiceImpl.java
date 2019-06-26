package org.haxwell.dtim.techprofile.services;

import java.util.LinkedHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plivo.helper.api.client.RestAPI;
import com.plivo.helper.api.response.message.MessageResponse;
import com.plivo.helper.exception.PlivoException;

@Service
public class SMSTextMessageServiceImpl implements SMSTextMessageService {

	private static final Log logger = LogFactory.getLog(SMSTextMessageServiceImpl.class);

	@Autowired
	CacheService cache;

	public boolean sendSMS(String toPhoneNumber, String msg) {
		boolean rtn = false;

		if (toPhoneNumber.startsWith("0")) {
			rtn = true;
		} else {
			try {
				RestAPI api = getRestAPI();

				LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
				parameters.put("src", "19342227693"); // Sender's phone number with country code
				parameters.put("dst", toPhoneNumber); // Receiver's phone number with country code
				parameters.put("text", msg); // Your SMS text message

				// Send Unicode text
				// parameters.put("text", "こんにちは、元気ですか？"); // Your SMS text message - Japanese
				// parameters.put("text", "Ce est texte généré aléatoirement"); // Your SMS text
				// message - French
				// parameters.put("url", "http://example.com/report/"); // The URL to which with
				// the status of the message is sent
				// parameters.put("method", "GET"); // The method used to call the url

				try {
					// Send the message
					MessageResponse msgResponse = api.sendMessage(parameters);

//                // Print the response
//                System.out.println(msgResponse);

					logger.debug("sms sent to " + toPhoneNumber + " /  msg: [" + msg + "]");

					if (msgResponse.serverCode == 202) {
						rtn = true;
					} else {
						throw new Exception(msgResponse.serverCode + " / " + msgResponse.error);
					}

				} catch (PlivoException e) {
					logger.info("Caught exception from the SMS third party,  ------ " + e.getMessage());
				}

			} catch (Exception e) {
				logger.info("Caught exception trying to send SMS,  ------ " + e.getMessage());
			}
		}

		return rtn;
	}
	
	private RestAPI getRestAPI() {
		// TODO: Get these from a config file
		String authId = "MAMJFLMTDMN2IZMGE0ZG";
		String authToken = "NDNlNjcyMDE1OWE4ZTBmN2JjOTVmYmYxMDZiOTU1";

		return new RestAPI(authId, authToken, "v1");
	}
}

package org.haxwell.dtim.techprofile.services;

public interface SMSTextMessageService {

	public boolean sendSMS(String toPhoneNumber, String msg);
	
}

package com.member.model;

import java.util.List;

// recaptcha使用

public class RecaptchaResponse {
    private boolean success;
    private String challengeTs;
    private String hostname;
    private List<String> errorCodes;
    
    
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getChallengeTs() {
		return challengeTs;
	}
	public void setChallengeTs(String challengeTs) {
		this.challengeTs = challengeTs;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public List<String> getErrorCodes() {
		return errorCodes;
	}
	public void setErrorCodes(List<String> errorCodes) {
		this.errorCodes = errorCodes;
	}

    
}


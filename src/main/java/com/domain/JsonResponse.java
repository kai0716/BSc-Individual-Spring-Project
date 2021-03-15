package com.domain;

public class JsonResponse {
	private String status = null;
	        private Object result = null;
	        public String getStatus() {
	                return status;
	        }
	        public void setStatus(String status) {
	                this.status = status;
	        }
	        public Object getResult() {
	                return result;
	        }
	        public void setResult(Object result) {
	                this.result = result;
	        }
	        
	private String loginStatus = null;
	private Object loginResult = null;
	public String getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	public Object getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(Object loginResult) {
		this.loginResult = loginResult;
	}      
	

}

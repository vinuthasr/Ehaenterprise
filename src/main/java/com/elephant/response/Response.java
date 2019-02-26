package com.elephant.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class Response {
	private String status;
	private String message;
	private String message1;
	private String message2;
	private String message3;
	private String message4;
	private String message5;
	private String message6;
	private String message7;
	private String message8;
	private String message9;
	private String message10;
	private Object data;
	private Object errors;
	private boolean isApiTimeout;
	
	
	public String getMessage6() {
		return message6;
	}

	public void setMessage6(String message6) {
		this.message6 = message6;
	}

	public String getMessage7() {
		return message7;
	}

	public void setMessage7(String message7) {
		this.message7 = message7;
	}

	public String getMessage8() {
		return message8;
	}

	public void setMessage8(String message8) {
		this.message8 = message8;
	}

	public String getMessage9() {
		return message9;
	}

	public void setMessage9(String message9) {
		this.message9 = message9;
	}

	public String getMessage10() {
		return message10;
	}

	public void setMessage10(String message10) {
		this.message10 = message10;
	}

	
	
	
	public String getMessage3() {
		return message3;
	}

	public void setMessage3(String message3) {
		this.message3 = message3;
	}

	public String getMessage4() {
		return message4;
	}

	public void setMessage4(String message4) {
		this.message4 = message4;
	}

	public String getMessage5() {
		return message5;
	}

	public void setMessage5(String message5) {
		this.message5 = message5;
	}
	public String getMessage1() {
		return message1;
	}

	public void setMessage1(String message1) {
		this.message1 = message1;
	}

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getErrors() {
		return errors;
	}

	public void setErrors(Object errors) {
		this.errors = errors;
	}

	public boolean getIsApiTimeout() {
		return isApiTimeout;
	}

	public void setIsApiTimeout(boolean isApiTimeout) {
		this.isApiTimeout = isApiTimeout;
	}
}
package com.retailer.rewards.error;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ExceptionResponse {
	
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	    private LocalDateTime timestamp;
	    private int status;
	    private String message;
	    private String error;
		public LocalDateTime getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}
		public ExceptionResponse(LocalDateTime timestamp,  String message, String error) {
			super();
			this.timestamp = timestamp;
			this.message = message;
			this.error = error;
		}
		public int getStatus() {
			return status;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getError() {
			return error;
		}
		public void setError(String error) {
			this.error = error;
		}

}

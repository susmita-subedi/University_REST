package edu.npu.zu.exceptions;

public class InvalidAcctException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidAcctException(String msg) {
		super(msg);
	}

}

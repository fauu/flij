package com.github.fauu.flij.reader;

public class ExpressionReadException extends RuntimeException {

	public ExpressionReadException(String msg) {
		super("Read error: " + msg);
	}

}
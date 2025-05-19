/**
 * 
 */
package com.proquifa.net.modelo.comun.excepciones;

/**
 * @author vaguirre
 *
 */
@SuppressWarnings("serial")
public class ProquifaNetException extends Exception {

	/**
	 * 
	 */
	public ProquifaNetException() {
	}

	/**
	 * @param message
	 */
	public ProquifaNetException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ProquifaNetException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ProquifaNetException(String message, Throwable cause) {
		super(message, cause);
	}

}

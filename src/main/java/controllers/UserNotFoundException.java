package controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Antonio Nicolas on 26/04/2017.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such user")
public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}

package com.me.EzFlight.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.me.EzFlight.pojo.User;

public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> type) {
		return User.class.isAssignableFrom(type);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
//		User user = (User) o;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.invalid.firstName",
				"First Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.invalid.lastName", "Last Name Required");
	
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.invalid.email", "Email Required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "error.invalid.phone",
				"Phone Number Required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "error.invalid.phone",
				"Email Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.invalid.phone",
				"Password  Required");
		
	}
	
	
	
}

package com.liferay.practice.exercise.registration.validator;
import com.liferay.practice.exercise.registration.exception.RegistrationException;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface RegistrationValidator {

    /**
     * Validates an Assignment
     * 
     * @param titleMap
     * @param descriptionMap
     * @param dueDate
     * @throws AssignmentValidationException
     */

	public void validate(String firstName, String lastName, String emailAddress, String userName, String password1,
			String password2, long companyId, Date birthday, String homePhone, String mobilePhone, String address,
			String address2, String city, String state, String zip, String securityQuestion, String securityAnswer,
			boolean termsOfUse) throws RegistrationException;
}
package com.liferay.practice.exercise.registration.util.validator;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.practice.exercise.registration.exception.RegistrationException;
import com.liferay.practice.exercise.registration.validator.RegistrationValidator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
    immediate = true, 
    service = RegistrationValidator.class
)
public class RegistrationValidatorImpl implements RegistrationValidator {

    /**
     * Validates assignment values and throws
     * {AssignmentValidationExceptionException} if the assignment values are not
     * valid.
     * 
     * @param titleMap
     * @param descriptionMap
     * @param dueDate
     * @throws AssignmentValidationExceptionException
     */
	@Override
    public void validate(
    		String firstName, String lastName, String emailAddress, String userName, String password1, String password2, long companyId,
            final Date birthday,  String homePhone, String mobilePhone, String address,
			String address2, String city, String state, String zip, String securityQuestion, String securityAnswer,
			boolean termsOfUse)
        throws RegistrationException  {

        final List<String> errors = new ArrayList<>();

        boolean result = isRegistrationValid(firstName, lastName, emailAddress, userName, password1, password2, companyId,  birthday,homePhone, mobilePhone, address, address2, city, state, zip, securityQuestion, securityAnswer,
				termsOfUse, errors);
            if(!result) {
            	throw new RegistrationException(errors);
            }
    }

    private boolean isRegistrationValid(String firstName, String lastName, String emailAddress, String userName,
			String password1, String password2, long companyId, Date birthday, String homePhone, String mobilePhone,
			String address, String address2, String city, String state, String zip, String securityQuestion,
			String securityAnswer, boolean termsOfUse, List<String> errors) {
    	boolean result = true;

    	result &= isFirstNameValid(firstName, errors);
		result &= isLastNameValid(lastName, errors);
		result &= isEmailAddressValid(emailAddress, companyId, errors);
		result &= isUserNameValid(userName,companyId, errors);
		result &= isPassword2Valid(password1, password2, errors);
		result &= isHomePhoneValid(homePhone, errors);
		result &= isMobilePhoneValid(mobilePhone, errors);
		result &= isAddress1Valid(address, errors);
		result &= isAddress2Valid(address2, errors);
		result &= isCityValid(city, errors);
		result &= isZipValid(zip, errors);
		result &= isBirthdayValid(birthday, errors);
		result &= isSecurityQuestionValid(securityQuestion, errors);
		result &= isSecurityAnswerValid(securityAnswer, errors);
		result &= isTermsOfUseValid(termsOfUse, errors);
        

        return result;
	}



   
    private boolean isBirthdayValid(
        final Date dueDate, final List<String> errors) {

        boolean result = true;

        if (Validator.isNull(dueDate)) {
            errors.add("assignmentDateEmpty");
            result = false;
        }

        return result;
    }

    /**
     * Returns <code>true</code> if title is valid for an assignment. If not
     * valid, an error message is added to the errors list.
     * 
     * @param titleMap
     * @param errors
     * @return boolean <code>true</code> if the title is valid, otherwise
     *         <code>false</code>
     */
 
	private boolean isPassword2Valid(String password1, String password2, List<String> errors) {

		boolean result = true;

		if (Validator.isNull(password2)) {
			errors.add("password2Empty");
			result = false;
		} else if (!password2.equals(password1)) {
			errors.add("passwordMismatch");
			result = false;
		}

		return result;
	}
	
	private boolean isUserNameValid(String userName,final long companyId, final List<String> errors) {

		boolean result = true;
		
		if (Validator.isNull(userName)) {
			errors.add("usernameEmpty");
			result = false;
		} else if (!Validator.isAlphanumericName(userName)) {
			errors.add("usernameFormat");
			result = false;
		} else if (userName.length() < 4 || userName.length() > 16) {
			errors.add("usernameLength");
			result = false;
		} else {
			User user = _userLocalService.fetchUserByScreenName(companyId, userName.toLowerCase());
			if (user != null) {
				errors.add("usernameExisting");
				result = false;
			}
		}

		return result;
	}
	private boolean isFirstNameValid(final String firstName, final List<String> errors) {

		boolean result = true;
		
		if (Validator.isNull(firstName)) {
			errors.add("firstNameEmpty");
			result = false;
		} else if (!Validator.isAlphanumericName(firstName)) {
			errors.add("firstNameIncorrect");
			result = false;
		} else if (firstName.length() > 50) {
			errors.add("firstNameLength");
			result = false;
		}

		return result;
	}

	private boolean isLastNameValid(final String lastName, final List<String> errors) {

		boolean result = true;

		if (Validator.isNull(lastName)) {
			errors.add("lastNameEmpty");
			result = false;
		} else if (!Validator.isAlphanumericName(lastName)) {
			errors.add("lastNameFormat");
			result = false;
		} else if (lastName.length() > 50) {
			errors.add("lastNameLength");
			result = false;
		}

		return result;
	}
	
	
	private boolean isEmailAddressValid(final String emailAddress,long companyId, final List<String> errors) {

		boolean result = true;
		
		if (Validator.isNull(emailAddress)) {
			errors.add("emailAddressEmpty");
			result = false;
		} else if (!Validator.isEmailAddress(emailAddress)) {
			errors.add("emailAddressFormat");
			result = false;
		} else if (emailAddress.length() > 255) {
			errors.add("emailAddressLength");
			result = false;
		} else {
			User user = _userLocalService.fetchUserByEmailAddress(companyId, emailAddress.toLowerCase());
			if (user != null) {
				errors.add("emailAddressExisting");
				result = false;
			}
		}

		return result;
	}
	
	private boolean isHomePhoneValid(final String homePhone, final List<String> errors) {

		boolean result = true;

		if (homePhone.length() > 10) {
			errors.add("homePhoneLength");
			result = false;
		}

		return result;
	}
	

	private boolean isMobilePhoneValid(final String mobilePhone, final List<String> errors) {

		boolean result = true;

		if (mobilePhone.length() > 10) {
			errors.add("mobilePhoneLength");
			result = false;
		}

		return result;
	}
	
	private boolean isAddress1Valid(final String address1, final List<String> errors) {

		boolean result = true;

		if (Validator.isNull(address1)) {
			errors.add("address1Empty");
			result = false;
		} else if (!Validator.isAlphanumericName(address1)) {
			errors.add("address1Format");
			result = false;
		} else if (address1.length() > 255) {
			errors.add("address1Length");
			result = false;
		}

		return result;
	}
	
	
	private boolean isAddress2Valid(final String address2, final List<String> errors) {

		boolean result = true;

		if (address2.isEmpty()) {
			return result;
		}
		if (!Validator.isAlphanumericName(address2)) {
			errors.add("address2Format");
			result = false;
		} else if (address2.length() > 255) {
			errors.add("address2Length");
			result = false;
		}

		return result;
	}

	
	private boolean isCityValid(final String city, final List<String> errors) {

		boolean result = true;

		if (Validator.isNull(city)) {
			errors.add("cityEmpty");
			result = false;
		} else if (!Validator.isAlphanumericName(city)) {
			errors.add("cityFormat");
			result = false;
		} else if (city.length() > 255) {
			errors.add("cityLength");
			result = false;
		}

		return result;
	}
	
	
	private boolean isZipValid(final String zip, final List<String> errors) {

		boolean result = true;

		if (Validator.isNull(zip)) {
			errors.add("zipEmpty");
			result = false;
		} else if (zip.length() > 5) {
			errors.add("zipLength");
			result = false;
		} else if (!Validator.isDigit(zip)) {
			errors.add("zipFormat");
			result = false;
		}

		return result;
	}
	
	/**
	 * Returns <code>true</code> if last name is valid for a registration. If not
	 * valid, an error message is added to the errors list.
	 *
	 * @param dueDate
	 * @param errors
	 * @return boolean <code>true</code> if last name is valid, otherwise
	 *         <code>false</code>
	 */

	private boolean isSecurityQuestionValid(final String securityQuestion, final List<String> errors) {

		boolean result = true;

		if (Validator.isNull(securityQuestion)) {
			errors.add("securityQuestionEmpty");
			result = false;
		}

		return result;
	}
	
	/**
	 * Returns <code>true</code> if last name is valid for a registration. If not
	 * valid, an error message is added to the errors list.
	 *
	 * @param dueDate
	 * @param errors
	 * @return boolean <code>true</code> if last name is valid, otherwise
	 *         <code>false</code>
	 */

	private boolean isSecurityAnswerValid(final String securityAnswer, final List<String> errors) {

		boolean result = true;

		if (Validator.isNull(securityAnswer)) {
			errors.add("securityAnswerEmpty");
			result = false;
		} else if (!Validator.isAlphanumericName(securityAnswer)) {
			errors.add("securityAnswerFormat");
			result = false;
		} else if (securityAnswer.length() > 255) {
			errors.add("securityAnswerLength");
			result = false;
		}

		return result;
	}
	
	private boolean isTermsOfUseValid(final boolean termsOfUse, final List<String> errors) {

		boolean result = true;

		if (termsOfUse == false) {
			errors.add("termsOfUseChecked");
			result = false;
		}

		return result;
	}

	@Reference
	private UserLocalService _userLocalService;
	
}

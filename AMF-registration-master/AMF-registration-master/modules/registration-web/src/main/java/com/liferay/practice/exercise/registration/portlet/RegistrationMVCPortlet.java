package com.liferay.practice.exercise.registration.portlet;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.CountryConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.RegionConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult.State;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.ContactLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.service.persistence.RegionPersistence;
import com.liferay.portal.kernel.service.persistence.UserUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.practice.exercise.registration.constants.RegistrationMVCPortletKeys;
import com.liferay.practice.exercise.registration.exception.RegistrationException;
import com.liferay.practice.exercise.registration.model.DemoUser;
import com.liferay.practice.exercise.registration.validator.RegistrationValidator;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author nikita.gavand
 */
@Component(immediate = true, property = { "com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css", "com.liferay.portlet.instanceable=true",
		"javax.portlet.init-param.add-process-action-success-action=false",
		"javax.portlet.display-name=RegistrationMVC", "javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + RegistrationMVCPortletKeys.REGISTRATIONMVC,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user" }, service = Portlet.class)
public class RegistrationMVCPortlet extends MVCPortlet {

	public void addEntry2(ActionRequest request, ActionResponse response) throws PortalException {
		long time = System.currentTimeMillis();
		System.out.println("addEntry2 " + time);
		String firstName = "user" + time;
		String lastName = firstName;
		boolean autoPassword = true;
		String password1 = "test";
		String password2 = "test";
		boolean autoScreenName = isAutoScreenName();
		String screenName = firstName;
		String emailAddress = firstName + "@liferay.com";
		long facebookId = 0;
		String openId = null;
		String languageId = "en_US";
		String middleName = firstName;
		long prefixId = 1;
		long suffixId = 1;
		boolean male = true;
		DateFormat dateformat = DateFormat.getDateInstance();
		Date birthday = new Date(2000, 1, 1);
		// LocalDate localDate = LocalDate.parse(strDate, format);
		@SuppressWarnings("deprecation")
		int birthdayMonth = birthday.getMonth();//
		@SuppressWarnings("deprecation")
		int birthdayDay = birthday.getDay();// ParamUtil.getInteger(request, "birthdayDay");
		@SuppressWarnings("deprecation")
		int birthdayYear = birthday.getYear();// 120
		String jobTitle = "plumber";
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = true;
		long userId = 1;
		String entry = firstName + "^" + lastName;
		ServiceContext serviceContext = ServiceContextFactory.getInstance(User.class.getName(), request);
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();
		User testUser = userLocalService.getUserByScreenName(company.getCompanyId(), "test");
		User user1 = userLocalService.addUserWithWorkflow(testUser.getUserId(), company.getCompanyId(), autoPassword,
				password1, password2, autoScreenName, screenName, emailAddress, facebookId, openId,
				LocaleUtil.fromLanguageId(languageId), firstName, middleName, lastName, prefixId, suffixId, male,
				birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
				sendEmail, serviceContext);

	}

	public void addEntry(ActionRequest request, ActionResponse response) throws PortalException {
		try {
			PortletPreferences prefs = request.getPreferences();

			String[] guestbookEntries = prefs.getValues("user-entries", new String[1]);

			ArrayList<String> entries = new ArrayList<String>();

			if (guestbookEntries[0] != null) {
				entries = new ArrayList<String>(Arrays.asList(prefs.getValues("user-entries", new String[1])));
			}

			String firstName = ParamUtil.getString(request, "first_name");
			String lastName = ParamUtil.getString(request, "last_name");
			boolean autoPassword = true;
			String password1 = ParamUtil.getString(request, "password1");
			;
			String password2 = ParamUtil.getString(request, "password2");
			;
			boolean autoScreenName = isAutoScreenName();
			String screenName = ParamUtil.getString(request, "username");
			String emailAddress = ParamUtil.getString(request, "emailAddress");
			long facebookId = ParamUtil.getLong(request, "facebookId");
			String openId = ParamUtil.getString(request, "openId");
			String languageId = ParamUtil.getString(request, "languageId");
			String middleName = ParamUtil.getString(request, "middleName");
			long prefixId = ParamUtil.getInteger(request, "prefixId");
			long suffixId = ParamUtil.getInteger(request, "suffixId");
			boolean male = ParamUtil.getBoolean(request, "male", true);
			DateFormat dateformat = DateFormat.getDateInstance();
			Date birthday = ParamUtil.getDate(request, "birthday", dateformat);
			// LocalDate localDate = LocalDate.parse(strDate, format);
			String address1 = ParamUtil.getString(request, "Address1");
			String address2 = ParamUtil.getString(request, "Address2");
			String zip = ParamUtil.getString(request, "zipcode");
			String city = ParamUtil.getString(request, "city");
			;
			long regionId = ParamUtil.getLong(request, "region");
			String homePhone_ = ParamUtil.getString(request, "homePhone");
			String mobilePhone_ = ParamUtil.getString(request, "mobilePhone");
			String reminderQueryQuestion = ParamUtil.getString(request, "securityQuestion");
			String reminderQueryAnswer = ParamUtil.getString(request, "securityAnswer");
			boolean termsOfUse = ParamUtil.getBoolean(request, "emailUpdates", true);
			@SuppressWarnings("deprecation")
			int birthdayMonth = birthday.getMonth();//
			@SuppressWarnings("deprecation")
			int birthdayDay = birthday.getDay();// ParamUtil.getInteger(request, "birthdayDay");
			@SuppressWarnings("deprecation")
			int birthdayYear = birthday.getYear();// 120
			String jobTitle = ParamUtil.getString(request, "jobTitle");
			long[] groupIds = null;
			long[] organizationIds = null;
			long[] roleIds = null;
			long[] userGroupIds = null;
			boolean sendEmail = true;
			long userId = 1;

			String entry = firstName + "^" + lastName;
			// User user = UserLocalServiceUtil.createUser(user1);
			entries.add(entry);

			String[] array = entries.toArray(new String[entries.size()]);
			ServiceContext serviceContext = ServiceContextFactory.getInstance(User.class.getName(), request);
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

			prefs.setValues("user-entries", array);
			Company company = themeDisplay.getCompany();
			registrationValidator.validate(firstName, lastName, emailAddress, screenName, password1, password2,
					company.getCompanyId(), birthday, homePhone_, mobilePhone_, address1, address2, city, regionId + "",
					zip, reminderQueryQuestion, reminderQueryAnswer, termsOfUse);

			User user1 = userLocalService.addUserWithWorkflow(serviceContext.getUserId(), company.getCompanyId(),
					autoPassword, password1, password2, autoScreenName, screenName, emailAddress, facebookId, openId,
					LocaleUtil.fromLanguageId(languageId), firstName, middleName, lastName, prefixId, suffixId, male,
					birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds, roleIds,
					userGroupIds, sendEmail, serviceContext);

			userId = user1.getUserId();
			user1.setReminderQueryAnswer(reminderQueryAnswer);
			user1.setReminderQueryQuestion(reminderQueryQuestion);
			String className = String.valueOf(PortalUtil.getClassNameId(Contact.class.getName()));
			long countryId = 19;
			long typeId = 0;
			UserLocalServiceUtil.updateUser(user1);
			Address address = AddressLocalServiceUtil.addAddress(user1.getUserId(), className, user1.getContactId(),
					address1, address2, "", zip, city, regionId, countryId, typeId, false, true, serviceContext);
			Contact contact = ContactLocalServiceUtil.createContact(user1.getContactId());
			contact.setBirthday(birthday);
			contact.setEmailAddress(emailAddress);
			contact.setFirstName(firstName);
			contact.setLastName(lastName);
			contact.setMale(male);
			contact.setMiddleName(middleName);
			contact.setPrefixId(prefixId);
			contact.setUserId(user1.getUserId());
			contact.setUserName(screenName);
			long addressId = counterLocalService.increment();

			Address address11 = addressLocalService.createAddress(addressId);
			address11.setClassName(Contact.class.getCanonicalName());
			address11.setClassPK(user1.getContactId());
			address11.setCompanyId(serviceContext.getCompanyId());
			address11.setUserId(user1.getUserId()); // Use newly created user
			address11.setPrimary(true);
			long addressTypeId = getListTypeId(ListTypeConstants.CONTACT_ADDRESS, "personal"); // 11002,
			address11.setTypeId(addressTypeId);
			address11.setStreet1(address1);
			address11.setStreet2(address2);
			address11.setCity(city);

			if (!Validator.isNull(regionId)) {
				address11.setRegionId(regionId); // State
			}
			address11.setZip(zip);
			address11.setCountryId(CountryConstants.DEFAULT_COUNTRY_ID);
			addressLocalService.addAddress(address11);
			if (!Validator.isBlank(homePhone_)) { // Optional, only
													// add if
													// applicable.
				long phoneId = counterLocalService.increment();
				Phone homePhone = phoneLocalService.createPhone(phoneId);
				homePhone.setClassName(Contact.class.getCanonicalName());
				homePhone.setClassPK(user1.getContactId());
				homePhone.setCompanyId(serviceContext.getCompanyId());
				homePhone.setUserId(user1.getUserId()); // Use newly created user
				long homePhoneTypeId = getListTypeId(ListTypeConstants.CONTACT_PHONE, "personal");
				homePhone.setTypeId(homePhoneTypeId);
				homePhone.setNumber(homePhone_);
				phoneLocalService.addPhone(homePhone);
			}
			if (!Validator.isBlank(mobilePhone_)) {
				long phoneId = counterLocalService.increment();
				Phone mobilePhone = phoneLocalService.createPhone(phoneId);
				mobilePhone.setClassName(Contact.class.getCanonicalName());
				mobilePhone.setClassPK(user1.getContactId());
				mobilePhone.setCompanyId(serviceContext.getCompanyId());
				mobilePhone.setUserId(user1.getUserId()); // Use newly created user
				long mobilePhoneTypeId = getListTypeId(ListTypeConstants.CONTACT_PHONE, "mobile-phone"); // approach
				mobilePhone.setTypeId(mobilePhoneTypeId);
				mobilePhone.setNumber(mobilePhone_);
				phoneLocalService.addPhone(mobilePhone);
			}
			prefs.store();
			response.setRenderParameter("mvcPath", "/sucess.jsp");

		} catch (RegistrationException rve) {
			rve.getErrors().forEach(key -> SessionErrors.add(request, key));
			response.setProperty("errors", rve.getMessage());
			response.setRenderParameter("mvcPath", "/add_registration.jsp");
		} catch (IOException ex) {
			SessionErrors.add(request, ex.getClass().getName());
			Logger.getLogger(RegistrationMVCPortlet.class.getName()).log(Level.SEVERE, null, ex);
			response.setRenderParameter("mvcPath", "/add_registration.jsp");
		} catch (ValidatorException ex) {
			SessionErrors.add(request, "generic-error-message-key", "Dta validation Exception");
			Logger.getLogger(RegistrationMVCPortlet.class.getName()).log(Level.SEVERE, null, ex);
			response.setRenderParameter("mvcPath", "/add_registration.jsp");
		} catch (Exception ex) {
			SessionErrors.add(request, "generic-error-message-key", ex.getMessage());
			Logger.getLogger(RegistrationMVCPortlet.class.getName()).log(Level.SEVERE, null, ex);
			response.setRenderParameter("mvcPath", "/add_registration.jsp");
		}

	}

	protected boolean isAutoScreenName() {
		return false;
	}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws PortletException, IOException {

		PortletPreferences prefs = renderRequest.getPreferences();
		String[] guestbookEntries = prefs.getValues("user-entries", new String[1]);

		if (guestbookEntries[0] != null) {
			List<DemoUser> entries = parseEntries(guestbookEntries);
			renderRequest.setAttribute("entries", entries);
		}

		super.render(renderRequest, renderResponse);
	}

	public String register() {
		return "/add_registration.jsp";
	}

	private List<DemoUser> parseEntries(String[] registeredUser) {
		List<DemoUser> entries = new ArrayList<DemoUser>();

		for (String entry : registeredUser) {
			String[] parts = entry.split("\\^", 2);
			DemoUser gbEntry = new DemoUser(parts[0], parts[1]);
			entries.add(gbEntry);
		}

		return entries;
	}

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		include(viewTemplate, renderRequest, renderResponse);
//		try {
//			System.out.println(String.format("the current user is %s", userService.getCurrentUser().getScreenName()));
//		} catch (PortalException e) {
//			e.printStackTrace();
//		}
	}

	private long getListTypeId(String type, String name) {
		List<ListType> listTypes = listTypeLocalService.getListTypes(type);
		for (ListType listType : listTypes) {
			if (listType.getName().equalsIgnoreCase(name)) {
				return listType.getListTypeId();
			}
		}
		return 0;
	}

	@Reference
	private UserLocalService userLocalService;
	@Reference
	private AddressLocalService addressLocalService;
	@Reference
	private CounterLocalService counterLocalService;
	@Reference
	private PhoneLocalService phoneLocalService;
	@Reference
	private UserLocalService userService;
	@Reference
	private RegionPersistence regionPersistence;
	@Reference
	private ListTypeLocalService listTypeLocalService;
	@Reference
	private RegistrationValidator registrationValidator;
}
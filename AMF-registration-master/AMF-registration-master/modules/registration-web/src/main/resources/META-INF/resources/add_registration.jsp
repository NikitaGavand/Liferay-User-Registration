
<%@page import="com.liferay.portal.kernel.service.UserLocalService"%>
<%@page import="com.liferay.portal.kernel.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.kernel.theme.ThemeDisplay"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.model.User"%>
<%@page import="com.liferay.portal.kernel.service.UserLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.model.Region"%>
<%@page import="com.liferay.portal.kernel.service.RegionServiceUtil"%>
<%@page import="java.util.List"%>

<%@ include file="init.jsp"%>
<liferay-ui:error key="error.key" message="error.message"></liferay-ui:error>
<liferay-ui:error key="usernameFormat"
	message="error.registration-userName-format" />
<liferay-ui:error key="usernameLength"
	message="error.registration-userName-length" />
<liferay-ui:error key="usernameExisting"
	message="error.registration-userName-existing" />
<liferay-ui:error key="monthInvalid"
	message="error.registration-month-invalid" />
<liferay-ui:error key="dayInvalid"
	message="error.registration-day-invalid" />
<liferay-ui:error key="yearInvalid"
	message="error.registration-year-invalid" />
<liferay-ui:error key="dateInvalidLeapYear"
	message="error.registration-date-invalid-leap-year" />
<liferay-ui:error key="dateInvalidMonth"
	message="error.registration-date-invalid-month" />
<liferay-ui:error key="password1Empty"
	message="error.registration-password1-empty" />
<liferay-ui:error key="password1Format"
	message="error.registration-password1-format" />
<liferay-ui:error key="password2Empty"
	message="error.registration-password2-empty" />
<liferay-ui:error key="passwordMismatch"
	message="error.registration-password2-notEqual" />
<liferay-ui:error key="firstNameEmpty"
	message="error.registration-firstName-empty" />
<liferay-ui:error key="firstNameFormat"
	message="error.registration-firstName-format" />
<liferay-ui:error key="firstNameLength"
	message="error.registration-firstName-length" />
<liferay-ui:error key="lastNameEmpty"
	message="error.registration-lastName-empty" />
<liferay-ui:error key="lastNameFormat"
	message="error.registration-lastName-format" />
<liferay-ui:error key="lastNameLength"
	message="error.registration-lastName-length" />
<liferay-ui:error key="emailAddressEmpty"
	message="error.registration-emailAddress-empty" />
<liferay-ui:error key="emailAddressFormat"
	message="error.registration-emailAddress-format" />
<liferay-ui:error key="emailAddressLength"
	message="error.registration-emailAddress-length" />
<liferay-ui:error key="emailAddressExisting"
	message="error.registration-emailAddress-existing" />
<liferay-ui:error key="passwordMismatch"
	message="error.registration-password2-notEqual" />
<liferay-ui:error key="homePhoneFormat"
	message="error.registration-homePhone-format" />
<liferay-ui:error key="mobilePhoneFormat"
	message="error.registration-mobilePhone-format" />
<liferay-ui:error key="address1Empty"
	message="error.registration-address1-empty" />
<liferay-ui:error key="address1Format"
	message="error.registration-address1-format" />
<liferay-ui:error key="address1Length"
	message="error.registration-address1-length" />
<liferay-ui:error key="address2Format"
	message="error.registration-address2-format" />
<liferay-ui:error key="address2Length"
	message="error.registration-address2-length" />
<liferay-ui:error key="cityEmpty"
	message="error.registration-city-empty" />
<liferay-ui:error key="cityFormat"
	message="error.registration-city-format" />
<liferay-ui:error key="cityLength"
	message="error.registration-city-length" />
<liferay-ui:error key="zipEmpty" message="error.registration-zip-empty" />
<liferay-ui:error key="zipFormat"
	message="error.registration-zip-format" />
<liferay-ui:error key="zipLength"
	message="error.registration-zip-length" />
<liferay-ui:error key="securityQuestionEmpty"
	message="error.registration-securityQuestion-empty" />
<liferay-ui:error key="securityAnswerEmpty"
	message="error.registration-securityAnswer-empty" />
<liferay-ui:error key="securityAnswerFormat"
	message="error.registration-securityAnswer-format" />
<liferay-ui:error key="securityAnswerLength"
	message="error.registration-securityAnswer-length" />
<liferay-ui:error key="termsOfUseChecked"
	message="error.registration-termsOfUse-empty" />

<liferay-ui:error key="generic-error-message-key">
	<liferay-ui:message key="your-request-failed-with-message"
		arguments='<%= SessionErrors.get(liferayPortletRequest, "generic-error-message-key") %>' />
</liferay-ui:error>

<aui:script>
AUI().use('aui-base','aui-form-validator',   function(A) {}
</aui:script>

<portlet:renderURL var="addRegistration">
	<portlet:param name="addUrl" value="register" />
</portlet:renderURL>
<portlet:actionURL name="addEntry" var="addEntryURL"></portlet:actionURL>
<aui:form action="<%= addEntryURL %>" name="<portlet:namespace />fm">

	<%
    List<Region> regionList = RegionServiceUtil.getRegions(19);
    String  username = ParamUtil.getString(request,"username");
    %>
	<aui:fieldset>
		<aui:input name="first_name" type="text" maxLength="50"
			required="true">
			<aui:validator name="required"></aui:validator>
			<aui:validator name="maxLength">255
            </aui:validator>
		</aui:input>
		<aui:input name="middleName" type="text" maxLength="50"></aui:input>
		<aui:input name="last_name" type="text" maxLength="50" required="true"></aui:input>
		<aui:input name="emailAddress" type="email" required="true"
			maxLength="255"></aui:input>
		<aui:input name="username" value="<%= username==null?"":username %>"
			type="text" onKeyup="copyValue(this)" required="true" maxLength="16">

			<% 
           
            
            ThemeDisplay themeDisplay2 = (ThemeDisplay)request.getAttribute(
	    			WebKeys.THEME_DISPLAY);
            long companyId = themeDisplay2.getCompanyId();
            User existUser;
            
            existUser = UserLocalServiceUtil.fetchUserByScreenName(companyId, username);
             if (existUser!=null) { //default i kept true how to check this condition on check box basic
        %>
			<aui:validator name="required" />
			<%
            }
        %>
		</aui:input>
		<aui:input name="male" type="checkbox"></aui:input>
		<aui:input name="birthday" type="date" required="true"></aui:input>
		<aui:input type="password" name="password1" id="password"
			label="Password" required="true" />
		<aui:input type="password" name="password2" id="password2"
			label="Confirm Password" required="true">
			<aui:validator name="equalTo">'#<portlet:namespace />password'</aui:validator>
		</aui:input>
    Phone:
    		<aui:input name="homePhone" type="type" maxLength="10"
			minLength="10" pattern="^[0-9]*$"></aui:input>
		<aui:input name="mobilePhone" type="type" maxLength="10"
			minLength="10" pattern="^[0-9]*$"></aui:input>
    		
    Billing Address:
    		<aui:input name="Address1" type="textarea" required="true"
			maxLength="255"></aui:input>
		<aui:input name="Address2" type="textarea" maxLength="255"></aui:input>
		<aui:input name="city" type="text" required="true"></aui:input>

		<aui:select onChange="srdfrfvc" style="width: 147px;" name="region">
			<aui:option value="-1" required="true">select Region</aui:option>
			<%
              
                for(Region region : regionList){
                    %>
			<aui:option value="<%= region.getRegionId() %>"><%= region.getName() %></aui:option>
			<%
                }
              %>
		</aui:select>
		<aui:input name="zipcode" type="text" required="true" maxLength="5"></aui:input>
		<aui:input label="Receive email updates" name="emailUpdates"
			required="true" type="checkbox" />
	</aui:fieldset>
	<aui:select onChange="srdfrfvc" style="width: 147px;"
		name="securityQuestion">
		<aui:option value="-1" required="true">Select Security Question</aui:option>
		<aui:option value="maidenname?">What is your mother's maidenname?</aui:option>
		<aui:option value="car">What is the make of your first car?</aui:option>
		<aui:option value="school">What is your high school mascot?</aui:option>
		<aui:option value="actor">Who is your favorite actor?</aui:option>

	</aui:select>
	<aui:input type="text" name="securityAnswer"></aui:input>
	<aui:button-row>
		<aui:button type="submit"></aui:button>
		<aui:button type="cancel" onClick="<%= addRegistration.toString() %>"></aui:button>
	</aui:button-row>
</aui:form>
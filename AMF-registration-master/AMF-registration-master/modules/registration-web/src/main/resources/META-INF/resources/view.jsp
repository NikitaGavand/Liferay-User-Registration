<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@ include file="/init.jsp" %>

<portlet:actionURL name="addEntry2" var="addEntry2URL"></portlet:actionURL>
<%-- <a href="<%= addEntry2URL %>">create entry</a> --%>

<portlet:renderURL var="addEntryURL">
   <portlet:param name="mvcPath" value="/add_registration.jsp"></portlet:param>
</portlet:renderURL>

<jsp:useBean id="entries" class="java.util.ArrayList" scope="request"/>

<liferay-ui:search-container>
    <liferay-ui:search-container-results results="<%= entries %>" />

    <!-- <liferay-ui:search-container-row
        className="com.liferay.practice.exercise.registration.model.DemoUser"
        modelVar="entry"
    >
        <liferay-ui:search-container-column-text property="first_name" />

        <liferay-ui:search-container-column-text property="last_name" />
    </liferay-ui:search-container-row>

    <liferay-ui:search-iterator />
</liferay-ui:search-container> -->

<aui:button-row>
    <aui:button onClick="<%= addEntryURL.toString() %>" value="Add User for registration"></aui:button>
</aui:button-row>

<liferay-ui:error key="error.key" message="error.message"></liferay-ui:error>
<liferay-ui:error key="generic-error-message-key">
    <liferay-ui:message key="your-request-failed-with-message" arguments='<%= SessionErrors.get(liferayPortletRequest, "generic-error-message-key") %>' />
</liferay-ui:error>

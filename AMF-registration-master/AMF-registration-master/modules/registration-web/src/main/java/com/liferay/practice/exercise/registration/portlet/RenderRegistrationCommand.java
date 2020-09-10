package com.liferay.practice.exercise.registration.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.practice.exercise.registration.constants.RegistrationMVCPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, property = { "javax.portlet.name=" + RegistrationMVCPortletKeys.REGISTRATIONMVC,
		"mvc.command.name=register"  }, service = MVCRenderCommand.class)
public class RenderRegistrationCommand implements MVCRenderCommand {

		@Override
		public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
			// TODO Auto-generated method stub
			return "/add_registration.jsp";
		}

}

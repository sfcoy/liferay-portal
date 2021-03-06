/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.wiki.web.admin.portlet.security.auth;

import com.liferay.portal.kernel.security.auth.AlwaysAllowDoAsUser;
import com.liferay.wiki.web.security.auth.BaseAlwaysAllowDoAsUser;

import java.util.Arrays;
import java.util.Collection;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera
 */
@Component(
	service = AlwaysAllowDoAsUser.class
)
public class EditPageAttachmentAlwaysAllowDoAsUser
	extends BaseAlwaysAllowDoAsUser {

	@Override
	public Collection<String> getStrutsActions() {
		return _strutsActions;
	}

	private final Collection<String> _strutsActions = Arrays.asList(
		"/wiki_admin/edit_page_attachment");

}
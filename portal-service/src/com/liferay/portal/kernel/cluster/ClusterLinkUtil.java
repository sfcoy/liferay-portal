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

package com.liferay.portal.kernel.cluster;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Shuyang Zhou
 * @author Raymond Augé
 */
public class ClusterLinkUtil {

	public static Address getAddress(Message message) {
		return (Address)message.get(_ADDRESS);
	}

	public static ClusterLink getClusterLink() {
		PortalRuntimePermission.checkGetBeanProperty(ClusterLinkUtil.class);

		ClusterLink clusterLink = _instance._serviceTracker.getService();

		if ((clusterLink == null) || !clusterLink.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterLinkUtil has not been initialized");
			}

			return null;
		}

		return clusterLink;
	}

	public static void sendMulticastMessage(
		Message message, Priority priority) {

		ClusterLink clusterLink = getClusterLink();

		if (clusterLink == null) {
			return;
		}

		clusterLink.sendMulticastMessage(message, priority);
	}

	public static void sendMulticastMessage(Object payload, Priority priority) {
		Message message = new Message();

		message.setPayload(payload);

		sendMulticastMessage(message, priority);
	}

	public static void sendUnicastMessage(
		Address address, Message message, Priority priority) {

		ClusterLink clusterLink = getClusterLink();

		if (clusterLink == null) {
			return;
		}

		clusterLink.sendUnicastMessage(address, message, priority);
	}

	public static Message setAddress(Message message, Address address) {
		message.put(_ADDRESS, address);

		return message;
	}

	private ClusterLinkUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(ClusterLink.class);

		_serviceTracker.open();
	}

	private static final String _ADDRESS = "CLUSTER_ADDRESS";

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterLinkUtil.class);

	private static final ClusterLinkUtil _instance = new ClusterLinkUtil();

	private final ServiceTracker<ClusterLink, ClusterLink> _serviceTracker;

}
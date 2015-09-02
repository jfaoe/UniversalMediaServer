/*
 * PS3 Media Server, for streaming any medias to your PS3.
 * Copyright (C) 2008  A.Brochard
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; version 2
 * of the License only.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package net.pms;

import org.apache.commons.lang3.StringUtils;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Class Messages provides a mechanism to localize the text messages found in
 * PMS. It is based on {@link ResourceBundle}.
 */
public class Messages {
	private static final String BUNDLE_NAME = "resources.i18n.messages";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	/**
	 * Returns the locale-specific string associated with the key.
	 *
	 * @param key
	 *            Keys in PMS follow the format "group.x". group states where
	 *            this key is likely to be used. For example, NetworkTab refers
	 *            to the network configuration tab in the PMS GUI. x is just a
	 *            number.
	 * @return Descriptive string if key is found or a copy of the key string if
	 *         it is not.
	 */
	public static String getString(String key) {
		return getString(key, RESOURCE_BUNDLE);
	}

	public static String getString(String key, String lang) {
		if (StringUtils.isEmpty(lang)) {
			return getString(key);
		}
		if (lang.equalsIgnoreCase("en") || lang.equalsIgnoreCase("en-US") || lang.equalsIgnoreCase("en_US")) {
			return getDefaultString(key);
		} else {
			Locale l = new Locale(lang);
			ResourceBundle rb = ResourceBundle.getBundle(BUNDLE_NAME, l);
			if (rb == null) {
				rb = RESOURCE_BUNDLE;
			}
			return getString(key, rb);
		}
	}

	/**
	 * Always get the string from the default properties file regardless of default locale
	 */
	public static String getDefaultString(String key) {
		ResourceBundle rb = ResourceBundle.getBundle(BUNDLE_NAME, Locale.ROOT, new ResourceBundle.Control() {
	        @Override
	        public List<Locale> getCandidateLocales(String name,
	                                                Locale locale) {
	            return Collections.singletonList(Locale.ROOT);
	        }
		});
		return getString(key, rb);
	}

	private static String getString(String key, ResourceBundle rb) {
		try {
			return rb.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}

package compta.client.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessagesReader {

	public static String getMessages(final String key) {
		return getMessages(key, null, null);
	}

	public static String getMessages(final String key, final Object params[]) {
		return getMessages(key, null, params);
	}

	public static String getMessages(final String key, final Locale locale) {
		return getMessages(key, locale, null);
	}

	public static String getMessages(final String key, Locale locale, final Object params[]) {

		String text;
		if (locale == null) {
			locale = Locale.FRENCH;
		}
		final ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

		try {
			text = bundle.getString(key);
		}
		catch (final MissingResourceException e) {
			text = "?? key " + key + " not found ??";
		}

		if (params != null) {
			final MessageFormat mf = new MessageFormat(text, locale);
			text = mf.format(params, new StringBuffer(), null).toString();
		}

		return text;
	}

}

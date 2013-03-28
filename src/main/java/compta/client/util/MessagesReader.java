package compta.client.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessagesReader {

	public static String getMessages( String key,  Locale locale) {
		return getMessages(key, locale, null);
	}
	
	public static String getMessages( String key,  Locale locale, Object params[]) {

        String text;
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

        try {
            text = bundle.getString(key);
        } catch (MissingResourceException e) {
            text = "?? key " + key + " not found ??";
        }

        if (params != null) {
            MessageFormat mf = new MessageFormat(text, locale);
            text = mf.format(params, new StringBuffer(), null).toString();
        }

        return text;
    }
	
}

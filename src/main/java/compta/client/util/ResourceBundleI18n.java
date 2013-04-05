package compta.client.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class ResourceBundleI18n extends ResourceBundle {

	protected static final String	BUNDLE_NAME			= "messages";
	protected static final String	BUNDLE_EXTENSION	= "properties";
	protected static final String	CHARSET				= "UTF-8";
	protected static final Control	UTF8_CONTROL		= new UTF8Control();

	public ResourceBundleI18n() {
		setParent(ResourceBundle.getBundle(BUNDLE_NAME, FacesContext.getCurrentInstance().getViewRoot().getLocale(),
				UTF8_CONTROL));
	}

	@Override
	protected Object handleGetObject(final String key) {
		return parent.getObject(key);
	}

	@Override
	public Enumeration<String> getKeys() {
		return parent.getKeys();
	}

	protected static class UTF8Control extends Control {
		@Override
		public ResourceBundle newBundle(final String baseName, final Locale locale, final String format,
				final ClassLoader loader, final boolean reload) throws IllegalAccessException, InstantiationException,
				IOException {
			// The below code is copied from default Control#newBundle()
			// implementation.
			// Only the PropertyResourceBundle line is changed to read the file
			// as UTF-8.
			final String bundleName = toBundleName(baseName, locale);
			final String resourceName = toResourceName(bundleName, BUNDLE_EXTENSION);
			ResourceBundle bundle = null;
			InputStream stream = null;
			if (reload) {
				final URL url = loader.getResource(resourceName);
				if (url != null) {
					final URLConnection connection = url.openConnection();
					if (connection != null) {
						connection.setUseCaches(false);
						stream = connection.getInputStream();
					}
				}
			} else {
				stream = loader.getResourceAsStream(resourceName);
			}
			if (stream != null) {
				try {
					bundle = new PropertyResourceBundle(new InputStreamReader(stream, CHARSET));
				}
				finally {
					stream.close();
				}
			}
			return bundle;
		}
	}

}

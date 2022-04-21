package cn.thinkit.util.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import cn.thinkit.exp.MyOwnRuntimeException;
import cn.thinkit.util.ClassHelper;
import cn.thinkit.util.EnumerationAsStreamUtil;
import cn.thinkit.util.FileHelper;

public class PropertiesHelper {
	boolean isSearchSystemProperty = false;
	Properties p;

	public PropertiesHelper(Properties p) {
		this.p = p;
	}

	public PropertiesHelper(Properties p, boolean isSearchSystemProperty) {
		this.p = resolveProperties(p);
		this.isSearchSystemProperty = isSearchSystemProperty;
	}

	public Properties getProperties() {
		return p;
	}

	public String getProperty(String key, String defaultValue) {
		String value = null;
		if (isSearchSystemProperty) {
			value = System.getProperty(key);
		}
		if (value == null || "".equals(value.trim())) {
			value = getProperties().getProperty(key);
		}
		return value == null || "".equals(value.trim()) ? defaultValue : value;
	}

	public String getProperty(String key) {
		return getProperty(key, null);
	}

	public String getRequiredProperty(String key) {
		String value = getProperty(key);
		if (value == null || "".equals(value.trim())) {
			throw new IllegalStateException("required property is blank by key=" + key);
		}
		return value;
	}

	public Integer getInt(String key) {
		if (getProperty(key) == null) {
			return null;
		}
		return Integer.parseInt(getRequiredProperty(key));
	}

	public int getInt(String key, int defaultValue) {
		if (getProperty(key) == null) {
			return defaultValue;
		}
		return Integer.parseInt(getRequiredProperty(key));
	}

	public int getRequiredInt(String key) {
		return Integer.parseInt(getRequiredProperty(key));
	}

	public Boolean getBoolean(String key) {
		if (getProperty(key) == null) {
			return false;
		}
		return Boolean.parseBoolean(getRequiredProperty(key));
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		if (getProperty(key) == null) {
			return defaultValue;
		}
		return Boolean.parseBoolean(getRequiredProperty(key));
	}

	public boolean getRequiredBoolean(String key) {
		return Boolean.parseBoolean(getRequiredProperty(key));
	}

	public String getNullIfBlank(String key) {
		String value = getProperty(key);
		if (value == null || "".equals(value.trim())) {
			return null;
		}
		return value;
	}

	public PropertiesHelper setProperty(String key, String value) {
		p.setProperty(key, value);
		return this;
	}

	public PropertiesHelper putAll(Properties props) {
		p.putAll(resolveProperties(props));
		return this;
	}

	public void clear() {
		p.clear();
	}

	public Set<Entry<Object, Object>> entrySet() {
		return p.entrySet();
	}

	public Enumeration propertyNames() {
		return p.propertyNames();
	}

	public static Properties load(String... files) throws IOException {
		Properties properties = new Properties();
		for (String f : files) {
			File file = FileHelper.getFile(f);
			
			try (InputStream input = new FileInputStream(file);) {
				if (file.getPath().endsWith(".xml")) {
					properties.loadFromXML(input);
				} else {
					properties.load(input);
				}
				properties.putAll(properties);
			}
		}
		return properties;
	}

	public static String[] loadAllPropertiesFromClassLoader(Properties properties, String... resourceNames)
			throws IOException {
		List<String> successLoadProperties = new ArrayList<>();
		for (String resourceName : resourceNames) {
			Enumeration<URL> urls = ClassHelper.getDefaultClassLoader().getResources(resourceName);

			EnumerationAsStreamUtil.enumerationAsStream(urls).forEach(url -> {
				try {
					successLoadProperties.add(url.getFile());
					URLConnection con = url.openConnection();
					con.setUseCaches(false);
					try (InputStream input = con.getInputStream();) {

						Properties loadProperties = new Properties();
						loadProperties.load(input);

						Set<Object> keySet = loadProperties.keySet();
						keySet.stream().filter(object -> properties.get(object) == null)
						.forEach(object -> {
							String key = (String) object;
							Object value = loadProperties.get(key);
							properties.put(key, value);
						});
					} catch (Exception e) {
						throw new MyOwnRuntimeException("加载配置文件异常");
					}
				} catch (Exception e) {
					throw new MyOwnRuntimeException("加载配置文件异常");
				}
			});

		}
		return successLoadProperties.toArray(new String[0]);
	}

	private static Properties resolveProperties(Properties props) {
		return props;
	}

}
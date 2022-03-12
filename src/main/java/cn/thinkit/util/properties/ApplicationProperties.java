package cn.thinkit.util.properties;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import cn.thinkit.config.ConfigProp;
import cn.thinkit.exp.MyOwnRuntimeException;
import cn.thinkit.util.GLogger;

/**
 * 
 *
 */
public class ApplicationProperties {
  protected static final String [] PROPERTIES_FILE_NAMES = new String[] { "application.properties" };
  protected static PropertiesHelper propertiesHelper = new PropertiesHelper(new Properties(), true);

  private ApplicationProperties() {
  }

  static {
    reload();
  }

  public static void load(String... files) throws IOException {
    putAll(PropertiesHelper.load(files));
  }

  public static void putAll(Properties props) {
    ApplicationProperties.getHelper().putAll(props);
  }

  public static void clear() {
    ApplicationProperties.getHelper().clear();
  }

  /**
   * 加载配置
   */
  public static void reload() {
    try {
      GLogger.info("Start Load application.properties from classpath:" + Arrays.toString(PROPERTIES_FILE_NAMES));
      Properties p = new Properties();
      String[] loadedFiles = PropertiesHelper.loadAllPropertiesFromClassLoader(p, PROPERTIES_FILE_NAMES);
      GLogger.info("application.properties Load Success,files:" + Arrays.toString(loadedFiles));
      setProperties(p);

      ConfigProp configProp = new ConfigProp();
      
      Set<Entry<Object, Object>> set = propertiesHelper.entrySet();
      
      set.forEach(entry-> {
    	  GLogger.debug("[Property] " + entry.getKey() + "=" + entry.getValue());
          
          String keyToString = entry.getKey().toString();
          
          if("es.node".equalsIgnoreCase(keyToString)) {
          	configProp.setEsNode(entry.getValue().toString());
          	
          } else if("es.index".equalsIgnoreCase(keyToString)) {
          	configProp.setEsIndex(entry.getValue().toString());
          } else if("es.type".equalsIgnoreCase(keyToString)) {
        	  configProp.setEsType(entry.getValue().toString());
          }
          else if("es.cluster.name".equalsIgnoreCase(keyToString)) {
        	  configProp.setEsClusterName(entry.getValue().toString());
          } 
          
      });
      
      Properties propsA = new Properties();
      propsA.put("configProp", configProp);
      putAll(propsA);

    } catch (IOException e) {
    	throw new MyOwnRuntimeException("加载文件失败" , e);
    }
  }

  public static Properties getProperties() {
    return getHelper().getProperties();
  }

  private static PropertiesHelper getHelper() {
    Properties fromContext = GeneratorContext.getGeneratorProperties();
    if (fromContext != null) {
      return new PropertiesHelper(fromContext, true);
    }
    return propertiesHelper;
  }

  public static String getProperty(String key, String defaultValue) {
    return getHelper().getProperty(key, defaultValue);
  }

  public static String getProperty(String key) {
    return getHelper().getProperty(key);
  }

  public static String getRequiredProperty(String key) {
    return getHelper().getRequiredProperty(key);
  }

  public static int getRequiredInt(String key) {
    return getHelper().getRequiredInt(key);
  }

  public static boolean getRequiredBoolean(String key) {
    return getHelper().getRequiredBoolean(key);
  }

  public static String getNullIfBlank(String key) {
    return getHelper().getNullIfBlank(key);
  }

  public static boolean getBoolean(String key, boolean defaultValue) {
    return getHelper().getBoolean(key, defaultValue);
  }

  public static void setProperty(String key, String value) {
    GLogger.debug("[setProperty()] " + key + "=" + value);
    getHelper().setProperty(key, value);
  }

  /**
   * 设置属性
   * @param inputProps s输入属性
   */
  public static void setProperties(Properties inputProps) {
    propertiesHelper = new PropertiesHelper(inputProps, true);
    Set<Entry<Object, Object>> set = propertiesHelper.entrySet();
    
    set.forEach(entry -> GLogger.debug("[Property] " + entry.getKey() + "=" + entry.getValue()));
  }
}
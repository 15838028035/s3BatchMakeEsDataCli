package cn.thinkit.util;

/**
 * 
 * 生成器日志类
 *
 */
public class GLogger {
	
  private static final int DEBUG = 1;
  private static final int INFO = 5;
  private static final int ERROR = 10;
  private static final int WARN = 15;
  private  static final int LOG_LEVEL = INFO;
  
  private GLogger() {
	  throw new IllegalStateException("Utility class");
  }
  

  /**
   * 打印
   * 
   * @param s
   *          字符信息
   */
  public static void debug(String s) {
    if (LOG_LEVEL <= DEBUG) {
    	printlnInfo("[DEBUG] ["+ Thread.currentThread().getName() + "]"+ s);
    }
  }

  /**
   * 打印
   * 
   * @param s
   *          字符信息
   */
  public static void info(String s) {
    if (LOG_LEVEL <= INFO) {
    	printlnInfo("[INFO] ["+ Thread.currentThread().getName() + "]"+ s);
    }
  }
  
  /**
   * 打印
   * 
   * @param s
   *          字符信息
   */
  public static void info(String s, Object... arguments) {
	  
	  String formattedMessage = "";
      if (arguments != null) {
          if (arguments != null && arguments.length > 0) {
        	  formattedMessage = String.format(s.replace("{}", "%s"), arguments);
          }
          
      } else {
          formattedMessage = s;
      }
      
    if (LOG_LEVEL <= INFO) {
    	printlnInfo("[INFO] ["+ Thread.currentThread().getName() + "]"+ formattedMessage);
    }
  }

  /**
   * 打印
   * 
   * @param s
   *          字符信息
   */
  public static void warn(String s) {
    if (LOG_LEVEL <= WARN) {
    	printlnInfo("[CopyInfo WARN] ["+ Thread.currentThread().getName() + "]"+ s);
    }
  }

  /**
   * 打印
   * 
   * @param s
   *          字符信息
   */
  public static void warn(String s, Throwable e) {
    if (LOG_LEVEL <= WARN) {
    	printlnInfo("[WARN] ["+ Thread.currentThread().getName() + "]"+ s);
      e.printStackTrace();
    }
  }

  /**
   * 打印
   * 
   * @param s
   *          字符信息
   */
  public static void error(String s) {
    if (LOG_LEVEL <= ERROR) {
    	printlnInfo("[ERROR] ["+ Thread.currentThread().getName() + "]"+ s);
    }
  }
  
  /**
   * 打印
   * 
   * @param s
   *          字符信息
   */
  public static void error(String s, Exception e) {
    if (LOG_LEVEL <= ERROR) {
      printlnInfo("[ ERROR] ["+ Thread.currentThread().getName() + "]"+ s);
      e.printStackTrace();
    }
  }
  

  /**
   * 打印
   * 
   * @param s
   *          字符信息
   */
  public static void error(String s, Throwable e) {
    if (LOG_LEVEL <= ERROR) {
      printlnInfo("[ ERROR] ["+ Thread.currentThread().getName() + "]"+ s);
      e.printStackTrace();
    }
  }
  
  private static void printlnInfo(String msg) {
	  System.out.println(msg);
  }
  
  private static void printlnErrorInfo(String msg) {
	  System.err.println(msg);
  }
}
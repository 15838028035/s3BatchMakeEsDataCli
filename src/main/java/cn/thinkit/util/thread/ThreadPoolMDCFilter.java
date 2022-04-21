package cn.thinkit.util.thread;
import java.util.Map;
import java.util.concurrent.Callable;
 
/**
 * @Classname ThreadPoolMDCFilter
 * @Description 多线程MDC塞入
 */
public class ThreadPoolMDCFilter {
 
	private ThreadPoolMDCFilter() {
		
	}
 
    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            try {
                return callable.call();
            } finally {
            }
        };
    }
 
    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            
            try {
                runnable.run();
            } finally {
            }
        };
    }
 
}

package pl.modrakowski.mvpjug.common;

public class ThreadUtil {
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// Nothing.
		}
	}
}

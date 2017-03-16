package pl.modrakowski.mvpjug.common;

import android.support.annotation.Nullable;

public class StringUtil {
	public static boolean isEmpty(@Nullable String string) {
		return string == null || string.isEmpty();
	}
}

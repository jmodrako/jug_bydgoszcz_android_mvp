package pl.modrakowski.mvpjug.login.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class LocalUserRepository implements UserRepository {

	@Override
	public void isPremiumUser(
			@NonNull final String userName,
			@Nullable UserTypeAvailableListener userTypeAvailableListener) {

		Log.i("UserRepository", "isPremiumUser: checking local repository!");

		if (userTypeAvailableListener != null) {
			userTypeAvailableListener.onUserTypeAvailable(userName.contains("premium"));
		}
	}
}

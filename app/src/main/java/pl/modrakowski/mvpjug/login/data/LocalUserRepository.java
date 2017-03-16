package pl.modrakowski.mvpjug.login.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class LocalUserRepository implements UserRepository {

	@Override
	public void isPremiumUser(
			@NonNull final String userName,
			@Nullable UserTypeAvailableListener userTypeAvailableListener) {

		final boolean isPremium = userName.contains("premium");

		if (userTypeAvailableListener != null) {
			userTypeAvailableListener.onUserTypeAvailable(isPremium);
		}
	}
}

package pl.modrakowski.mvpjug.login.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface UserRepository {

	interface UserTypeAvailableListener {
		void onUserTypeAvailable(boolean isPremium);
	}

	void isPremiumUser(
			@NonNull String userName,
			@Nullable UserTypeAvailableListener userTypeAvailableListener);
}




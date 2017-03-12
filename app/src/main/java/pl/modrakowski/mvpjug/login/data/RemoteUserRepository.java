package pl.modrakowski.mvpjug.login.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static pl.modrakowski.mvpjug.common.ThreadUtil.sleep;

public class RemoteUserRepository implements UserRepository {

	@Override
	public void isPremiumUser(
			@NonNull final String userName,
			@Nullable final UserTypeAvailableListener userTypeAvailableListener) {

		Log.i("UserRepository", "isPremiumUser: calling server api!");

		// Simulate heavy call (network api call, etc).
		new Thread(new Runnable() {
			@Override public void run() {
				sleep(3000);

				if (userTypeAvailableListener != null) {
					userTypeAvailableListener.onUserTypeAvailable(userName.contains("premium"));
				}
			}
		}).start();
	}
}

package pl.modrakowski.mvpjug.login;

import android.support.annotation.NonNull;

import pl.modrakowski.mvpjug.common.BasePresenter;
import pl.modrakowski.mvpjug.common.NetworkChecker;
import pl.modrakowski.mvpjug.common.StringUtil;
import pl.modrakowski.mvpjug.login.data.UserRepository;

import static pl.modrakowski.mvpjug.login.data.UserRepository.UserTypeAvailableListener;

public class LoginPresenter extends BasePresenter<LoginView> {

	private static final int NAME_MIN_LENGTH_CHARS = 5;
	private static final int PASSWORD_MIN_LENGTH_CHARS = 3;

	private static final String WRONG_NAME_MESSAGE = "Provided name is to short.";
	private static final String WRONG_PASSWORD_MESSAGE = "Provided password is to short.";

	private final UserRepository localUserRepository;
	private final UserRepository remoteUserRepository;
	private final NetworkChecker networkChecker;

	public LoginPresenter(
			UserRepository localUserRepository,
			UserRepository remoteUserRepository,
			NetworkChecker networkChecker) {

		this.localUserRepository = localUserRepository;
		this.remoteUserRepository = remoteUserRepository;
		this.networkChecker = networkChecker;
	}

	@NonNull @Override protected LoginView provideEmptyView() {
		return LoginView.EMPTY;
	}

	public void onLoginButtonClicked(@NonNull String userName, @NonNull String userPassword) {
		final boolean isUserNameCorrect = validateInput(userName, NAME_MIN_LENGTH_CHARS);
		final boolean isUserPasswordCorrect = validateInput(userPassword, PASSWORD_MIN_LENGTH_CHARS);

		if (isUserNameCorrect) {
			view().clearUserNameError();
		} else {
			view().showUserNameError(WRONG_NAME_MESSAGE);
		}

		if (isUserPasswordCorrect) {
			view().clearUserPasswordError();
		} else {
			view().showUserPasswordError(WRONG_PASSWORD_MESSAGE);
		}

		if (isUserNameCorrect && isUserPasswordCorrect) {
			view().showProgress();

			final UserTypeAvailableListener listener = new UserTypeAvailableListener() {
				@Override public void onUserTypeAvailable(boolean isPremium) {
					view().hideProgress();

					if (isPremium) {
						view().continueAsPremiumUser();
					} else {
						view().continueAsNormalUser();
					}
				}
			};

			if (networkChecker.isOnline()) {
				remoteUserRepository.isPremiumUser(userName, listener);
			} else {
				localUserRepository.isPremiumUser(userName, listener);
			}
		}
	}

	private boolean validateInput(String value, int requiredLength) {
		final String trimmedValue = value.trim();
		return !StringUtil.isEmpty(trimmedValue) && trimmedValue.length() >= requiredLength;
	}
}

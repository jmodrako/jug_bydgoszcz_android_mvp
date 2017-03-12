package pl.modrakowski.mvpjug.login;

import pl.modrakowski.mvpjug.common.BaseView;

public interface LoginView extends BaseView {

	void continueAsNormalUser();

	void continueAsPremiumUser();

	void clearUserNameError();

	void showUserNameError(String wrongNameMessage);

	void clearUserPasswordError();

	void showUserPasswordError(String wrongPasswordMessage);

	void showProgress();

	void hideProgress();

	LoginView EMPTY = new LoginView() {
		@Override public void continueAsNormalUser() {
			// Nothing.
		}

		@Override public void continueAsPremiumUser() {
			// Nothing.
		}

		@Override public void clearUserNameError() {
			// Nothing.
		}

		@Override public void showUserNameError(String wrongNameMessage) {
			// Nothing.
		}

		@Override public void clearUserPasswordError() {
			// Nothing.
		}

		@Override public void showUserPasswordError(String wrongPasswordMessage) {
			// Nothing.
		}

		@Override public void showProgress() {
			// Nothing.
		}

		@Override public void hideProgress() {
			// Nothing.
		}
	};
}









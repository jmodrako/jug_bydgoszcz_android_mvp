package pl.modrakowski.mvpjug.login.ui.simple.login;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import pl.modrakowski.mvpjug.common.NetworkChecker;
import pl.modrakowski.mvpjug.login.LoginPresenter;
import pl.modrakowski.mvpjug.login.LoginView;
import pl.modrakowski.mvpjug.login.data.UserRepository;
import pl.modrakowski.mvpjug.login.data.UserRepository.UserTypeAvailableListener;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(BlockJUnit4ClassRunner.class)
public class LoginPresenterTest {

	private static final String WRONG_NAME = "we";
	private static final String WRONG_PASSWORD = "u";

	private static final String GOOD_USER_NAME = "jacekbeny";
	private static final String GOOD_PASSWORD = "password";

	private LoginPresenter sut;

	@Mock private LoginView mockView;
	@Mock UserRepository mockLocalUserRepository;
	@Mock UserRepository mockRemoteUserRepository;
	@Mock NetworkChecker mockNetworkChecker;

	@Before public void setUp() {
		MockitoAnnotations.initMocks(this);

		// Simulate no network.
		when(mockNetworkChecker.isOnline()).thenReturn(false);

		sut = new LoginPresenter(mockLocalUserRepository,
				mockRemoteUserRepository, mockNetworkChecker);
		sut.attach(mockView);
	}

	@Test public void login_wrongUserNameAndPassword_shouldShowErrorsOnView() {
		sut.onLoginButtonClicked(WRONG_NAME, WRONG_PASSWORD);

		verify(mockView).showUserNameError(anyString());
		verify(mockView).showUserPasswordError(anyString());
	}

	@Test public void login_wrongUserName_shouldShowErrorOnView() {
		sut.onLoginButtonClicked(WRONG_NAME, GOOD_PASSWORD);

		verify(mockView).showUserNameError(anyString());
		verify(mockView).clearUserPasswordError();
	}

	@Test public void login_wrongPassword_shouldShowErrorOnView() {
		sut.onLoginButtonClicked(GOOD_USER_NAME, WRONG_PASSWORD);

		verify(mockView).showUserPasswordError(anyString());
		verify(mockView).clearUserNameError();
	}

	@Test public void login_goodCredentials_shouldClearErrorsOnView() {
		sut.onLoginButtonClicked(GOOD_USER_NAME, GOOD_PASSWORD);

		verify(mockView).clearUserNameError();
		verify(mockView).clearUserPasswordError();
	}

	@Test public void login_goodCredentials_shouldShowProgress() {
		sut.onLoginButtonClicked(GOOD_USER_NAME, GOOD_PASSWORD);

		verify(mockView).showProgress();
	}

	@Test public void login_goodCredentials_continueAsPremiumWithPremiumUser() {
		setupMockCallbackForUser(true);

		sut.onLoginButtonClicked(GOOD_USER_NAME, GOOD_PASSWORD);

		verify(mockView).continueAsPremiumUser();
		verify(mockView, never()).continueAsNormalUser();
	}

	@Test public void login_goodCredentials_continueAsPremiumWithNormalUser() {
		setupMockCallbackForUser(false);

		sut.onLoginButtonClicked(GOOD_USER_NAME, GOOD_PASSWORD);

		verify(mockView, never()).continueAsPremiumUser();
		verify(mockView).continueAsNormalUser();
	}

	@Test public void detach_shouldNotCallAnyViewMethod() {
		sut.detach();

		verifyZeroInteractions(mockView);
	}

	private void setupMockCallbackForUser(final boolean isPremium) {
		doAnswer(new Answer() {
			@Override public Object answer(InvocationOnMock invocation) throws Throwable {
				final UserTypeAvailableListener callback = (UserTypeAvailableListener) invocation.getArguments()[1];
				callback.onUserTypeAvailable(isPremium);
				return null;
			}
		}).when(mockLocalUserRepository).isPremiumUser(anyString(), any(UserTypeAvailableListener.class));
	}

	@After public void tearDown() {
		sut.detach();
	}
}

package pl.modrakowski.mvpjug.login.ui.charged.di;

import javax.inject.Named;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import pl.modrakowski.mvpjug.common.NetworkChecker;
import pl.modrakowski.mvpjug.common.di.ActivityComponent;
import pl.modrakowski.mvpjug.common.di.NetworkModule;
import pl.modrakowski.mvpjug.login.KotlinLoginActivity;
import pl.modrakowski.mvpjug.login.LoginPresenter;
import pl.modrakowski.mvpjug.login.data.LocalUserRepository;
import pl.modrakowski.mvpjug.login.data.RemoteUserRepository;
import pl.modrakowski.mvpjug.login.data.UserRepository;
import pl.modrakowski.mvpjug.login.ui.charged.ChargedLoginActivity;

@Component(modules = {
		ChargedLoginComponent.ChargedModule.class,
		ActivityComponent.ActivityModule.class,
		NetworkModule.class
}) public interface ChargedLoginComponent extends ActivityComponent {

	void inject(ChargedLoginActivity activity);

	void inject(KotlinLoginActivity activity);

	@Module class ChargedModule {

		@Provides LoginPresenter providesLoginPresenter(
				@Named("LocalUserRepository") UserRepository localUserRepository,
				@Named("RemoteUserRepository") UserRepository remoteUserRepository,
				NetworkChecker networkChecker) {

			return new LoginPresenter(localUserRepository, remoteUserRepository, networkChecker);
		}

		@Provides
		@Named("RemoteUserRepository")
		UserRepository providesRemoteUserRepository() {
			return new RemoteUserRepository();
		}

		@Provides
		@Named("LocalUserRepository")
		UserRepository providesLocalUserRepository() {
			return new LocalUserRepository();
		}
	}
}

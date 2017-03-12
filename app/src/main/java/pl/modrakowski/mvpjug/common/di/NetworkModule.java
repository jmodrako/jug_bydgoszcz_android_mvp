package pl.modrakowski.mvpjug.common.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import pl.modrakowski.mvpjug.common.NetworkChecker;

@Module public class NetworkModule {
	@Provides NetworkChecker providesNetworkChecker(Context context) {
		return new NetworkChecker(context);
	}
}

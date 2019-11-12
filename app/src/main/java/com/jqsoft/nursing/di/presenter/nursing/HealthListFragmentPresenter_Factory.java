package com.jqsoft.nursing.di.presenter.nursing;

import android.content.SharedPreferences;

import com.jqsoft.nursing.di.contract.nursing.SpecificNursingFragmentContract;
import com.jqsoft.nursing.di.presenter.HealthListPresenter;
import com.jqsoft.nursing.di.view.IHealthListView;
import com.jqsoft.nursing.di_http.http.nursing.GCAService;

import javax.annotation.Generated;
import javax.inject.Provider;

import dagger.internal.Factory;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class HealthListFragmentPresenter_Factory
    implements Factory<HealthListPresenter> {
  private final Provider<IHealthListView> viewProvider;

  private final Provider<SharedPreferences> sharedPreferencesProvider;

  private final Provider<GCAService> gCAServiceProvider;

  public HealthListFragmentPresenter_Factory(
      Provider<IHealthListView> viewProvider,
      Provider<SharedPreferences> sharedPreferencesProvider,
      Provider<GCAService> gCAServiceProvider) {
    assert viewProvider != null;
    this.viewProvider = viewProvider;
    assert sharedPreferencesProvider != null;
    this.sharedPreferencesProvider = sharedPreferencesProvider;
    assert gCAServiceProvider != null;
    this.gCAServiceProvider = gCAServiceProvider;
  }

  @Override
  public HealthListPresenter get() {
    return new HealthListPresenter(
        viewProvider.get(), sharedPreferencesProvider.get(), gCAServiceProvider.get());
  }

  public static Factory<HealthListPresenter> create(
      Provider<IHealthListView> viewProvider,
      Provider<SharedPreferences> sharedPreferencesProvider,
      Provider<GCAService> gCAServiceProvider) {
    return new HealthListFragmentPresenter_Factory(
        viewProvider, sharedPreferencesProvider, gCAServiceProvider);
  }
}

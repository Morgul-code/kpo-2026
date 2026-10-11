package ru.hse.vyshkat;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import ru.hse.vyshkat.service.ConditionBasedServiceCenter;
import ru.hse.vyshkat.service.Fleet;
import ru.hse.vyshkat.service.ServiceCenter;

/** Production bindings. Unit tests can substitute their own ServiceCenter. */
public final class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Fleet.class).in(Scopes.SINGLETON);
        bind(ServiceCenter.class).to(ConditionBasedServiceCenter.class);
    }
}

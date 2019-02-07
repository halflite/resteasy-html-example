package net.halflite.resteasy.web.module;

import com.google.inject.AbstractModule;

import net.halflite.resteasy.web.resource.IndexResource;

public class ResourceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IndexResource.class);
    }
}

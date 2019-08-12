package com.supinfo.suppictures.Model.rest.RestConfig;

import org.glassfish.jersey.server.ResourceConfig;

public class SupPicturesApplication extends ResourceConfig {
    public SupPicturesApplication() {
        // Define the package which contains the service classes.
        packages("com.supinfo.suppictures.Model.rest.Resources");
    }
}

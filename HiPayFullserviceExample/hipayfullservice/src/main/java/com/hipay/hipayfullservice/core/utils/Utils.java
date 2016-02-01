package com.hipay.hipayfullservice.core.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by nfillion on 25/01/16.
 */

public class Utils {

    public static URL concatenate(URL baseUrl, String extraPath) throws URISyntaxException,
            MalformedURLException {
        URI uri = baseUrl.toURI();
        String newPath = uri.getPath() + '/' + extraPath;
        URI newUri = uri.resolve(newPath);
        return newUri.toURL();
    }
}

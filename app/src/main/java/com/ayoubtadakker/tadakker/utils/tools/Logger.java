package com.ayoubtadakker.tadakker.utils.tools;

import android.content.Context;

import io.sentry.Sentry;
import io.sentry.android.AndroidSentryClientFactory;
import io.sentry.event.BreadcrumbBuilder;
import io.sentry.event.Event;
import io.sentry.event.UserBuilder;

/**
 * Created by AYOUB on 28/02/2018.
 */

public class Logger {
    public static void INFO(String message) {
        Sentry.capture(message);
    }

    public static void ERROR(String message){
        Sentry.capture(message);
    }

    public static void ERROR(Event event){
        Sentry.capture(event);
    }

    public static void ERROR(Throwable throwable){
        Sentry.capture(throwable);
    }

    public static void WARN(String message){
        Sentry.capture(message);
    }

}


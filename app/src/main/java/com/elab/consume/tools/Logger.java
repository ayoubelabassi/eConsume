package com.elab.consume.tools;

import io.sentry.Sentry;
import io.sentry.event.Event;

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


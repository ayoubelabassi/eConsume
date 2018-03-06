package com.ayoubtadakker.tadakker.utils.tools;

import com.ayoubtadakker.tadakker.checker.compagne.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by AYOUB on 26/02/2018.
 */

public class Globals {

    public static final DateFormat DATE_FORMAT=new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat DISPLAY_DATE_FORMAT=new SimpleDateFormat("dd/MM/yyyy");
    public static final String SENTRY_KEY="https://48e40ec87c984307a8a9c5555052c236:a2a823ed626344b3a451a21818b95bbc@sentry.io/296264";
    public static final String EDIT_OP ="EDIT";
    public static final String DELETE_OP="DELETE";
    public static final String ADD_OP="DELETE";
    public static User CURRENT_USER;
}

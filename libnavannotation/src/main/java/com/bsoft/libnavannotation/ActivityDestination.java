package com.bsoft.libnavannotation;

public @interface ActivityDestination {

    String pageUrl();

    boolean needLogin() default false;

    boolean asStart() default false;

}

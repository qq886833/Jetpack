package com.bsoft.libnavannotation;

public @interface FragmentDestination {

    String pageUrl();

    boolean needLogin() default false;

    boolean asStart() default false;

}

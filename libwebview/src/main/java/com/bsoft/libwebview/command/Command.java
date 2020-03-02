package com.bsoft.libwebview.command;


import com.bsoft.libwebview.ICallbackFromMainToWeb;

import java.util.Map;

public interface Command {
    String name();
    void exec(Map params, ICallbackFromMainToWeb resultBack);
}

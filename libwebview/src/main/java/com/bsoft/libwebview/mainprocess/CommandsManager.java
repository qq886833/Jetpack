package com.bsoft.libwebview.mainprocess;

import com.bsoft.libwebview.ICallbackFromMainToWeb;
import com.bsoft.libwebview.IWebToMain;
import com.bsoft.libwebview.command.Command;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class CommandsManager extends IWebToMain.Stub {
    private HashMap<String, Command> mCommands = new HashMap<>();
    private static CommandsManager sInstance;

    public static CommandsManager getsInstance() {
        if (sInstance == null) {
            synchronized (CommandsManager.class) {
                sInstance = new CommandsManager();
            }
        }
        return sInstance;
    }

    public void registerCommand(Command command) {
        mCommands.put(command.name(), command);
    }

    public void executeCommand(String action, Map params, ICallbackFromMainToWeb resultBack) {
        if (mCommands.get(action) != null) {
            mCommands.get(action).exec(params, resultBack);
        }
    }

    @Override
    public void handleWebAction(final String actionName, String jsonParams, final ICallbackFromMainToWeb callback) {
        CommandsManager.getsInstance().executeCommand(actionName, new Gson().fromJson(jsonParams, Map.class), callback);
    }
}


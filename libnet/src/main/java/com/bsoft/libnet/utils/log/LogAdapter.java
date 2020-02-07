package com.bsoft.libnet.utils.log;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bsoft.libbasic.thirdpart.logger.AndroidLogAdapter;
import com.bsoft.libbasic.thirdpart.logger.FormatStrategy;
import com.bsoft.libbasic.thirdpart.logger.PrettyFormatStrategy;
import com.bsoft.libnet.constants.HttpConstants;


public class LogAdapter extends AndroidLogAdapter {
    private final FormatStrategy formatStrategy;

    public LogAdapter() {
        this.formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("log")
                .build();
    }

    public LogAdapter(@NonNull FormatStrategy formatStrategy) {
        this.formatStrategy = formatStrategy;
    }

    @Override
    public boolean isLoggable(int priority, @Nullable String tag) {
        return HttpConstants.isDebug && !TextUtils.isEmpty(tag) && tag.contains(CoreLogTag.TAG);
    }

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message) {
        formatStrategy.log(priority, tag, message);
    }
}

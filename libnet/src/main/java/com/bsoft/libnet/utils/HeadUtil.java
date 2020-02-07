package com.bsoft.libnet.utils;

import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebSettings;
import com.bsoft.libbasic.context.ContextProvider;
import com.bsoft.libnet.constants.HttpConstants;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by chenkai on 2018/3/19.
 */

public class HeadUtil {
    public static final Charset UTF8 = Charset.forName("UTF-8");

    public static String getSignature(Request request) {
        String accessToken = request.header(HttpConstants.Head_Token);
        if (!TextUtils.isEmpty(accessToken) && accessToken.length() >= 8) {

            String body = "";
            RequestBody requestBody = request.body();
            boolean hasRequestBody = requestBody != null;
            if (hasRequestBody && !bodyEncoded(request.headers())) {
                Buffer buffer = new Buffer();

                try {
                    requestBody.writeTo(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                if (isPlaintext(buffer)) {
                    body = buffer.readString(charset);
                }
            }

            String salt = accessToken.substring(4, 8);
            String[] signs = new String[]{body, salt};
            Arrays.sort(signs);
            String digest = MD5.getMD5(signs[0] + signs[1]).toLowerCase();
            return digest;
        } else {
            return null;
        }
    }

    public static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    public static boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    public static String getUserAgent() {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(ContextProvider.get().getApplication());
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


}

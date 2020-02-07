package com.bsoft.libbasic.context

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri

open class ApplicationContextProvider : ContentProvider(){

    companion object {
        var  mContext: Context? = null
    }
    override fun insert(uri: Uri, p1: ContentValues?): Uri? {
        return null
    }

    override fun query(uri: Uri, p1: Array<out String>?, p2: String?, p3: Array<out String>?, p4: String?): Cursor? {
        return null
    }

    override fun onCreate(): Boolean {

        mContext = getContext();
        //初始化全局Context提供者
        ContextProvider.get();
        return false;
    }

    override fun update(uri: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 0
    }

    override fun delete(uri: Uri, p1: String?, p2: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }


}
package com.bsoft.libbasic.shapref

import android.content.SharedPreferences

import com.bsoft.libbasic.thirdpart.fastsharedpreferences.FastSharedPreferences

open abstract class BaseSharpref {

   private var mName: String
    constructor(name: String)  {
        this.mName = name
    }


    protected  fun getString(key: String?): String? {
        return getString(key, null)
    }

    protected  fun getString(key: String?, def: String?): String? {
        return getShaprefrence().getString(key, def)
    }

    protected  fun setString(value: String?, key: String?): Boolean {
        return getEditor().putString(key, value).commit()
    }

    protected  fun getBoolean(key: String?): Boolean {
        return getBoolean(key, false)
    }

    protected  fun getBoolean(key: String?, def: Boolean): Boolean {
        return getShaprefrence().getBoolean(key, def)
    }

    protected  fun setBoolean(value: Boolean, key: String?): Boolean {
        return getEditor().putBoolean(key, value).commit()
    }

    protected  fun getFloat(key: String?): Float? {
        return getFloat(key, 0)
    }

    protected  fun getFloat(key: String?, def: Long): Float? {
        return getShaprefrence().getFloat(key, def.toFloat())
    }

    protected  fun setFloat(value: Float?, key: String?): Boolean {
        return getEditor().putFloat(key, value!!).commit()
    }

    protected  fun getLong(key: String?): Long? {
        return getLong(key, 0)
    }

    protected  fun getLong(key: String?, def: Long): Long {
        return getShaprefrence().getLong(key, def)
    }

    protected  fun setLong(value: Long, key: String?): Boolean {
        return getEditor().putLong(key, value).commit()
    }

    protected  fun getInt(key: String?): Int {
        return getInt(key, 0)
    }

    protected  fun getInt(key: String?, def: Int): Int {
        return getShaprefrence().getInt(key, def)
    }

    protected  fun setInt(value: Int, key: String?): Boolean {
        return getEditor().putInt(key, value).commit()
    }

    private  fun getShaprefrence(): FastSharedPreferences {
        return FastSharedPreferences.get(mName)
    }

    private  fun getEditor(): SharedPreferences.Editor {
        return getShaprefrence().edit()
    }

}
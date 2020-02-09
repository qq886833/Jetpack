package com.bsoft.libbasic.shapref

import com.bsoft.libbasic.constant.Constants


open class CoreSharpref :BaseSharpref{
    private val KEY_DEBUG = "debug"

    companion object{

        val instance : CoreSharpref by lazy(mode=LazyThreadSafetyMode.SYNCHRONIZED){
            CoreSharpref(Constants.SHARED_NAME)
        }
    }

    constructor(name: String):super(name){

}
    open fun getDebug(): Boolean {
        synchronized(this) { return getBoolean(KEY_DEBUG) }
    }


    open fun setDebug(debug: Boolean): Boolean {
        synchronized(this) { return setBoolean(debug, KEY_DEBUG) }
    }

}
package com.example.librolink

import android.content.Context

object Session {
    private const val PREFS = "librolink_prefs"
    private const val KEY_USER_ID = "user_id"

    fun saveUserId(ctx: Context, id: Long) {
        ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putLong(KEY_USER_ID, id).apply()
    }
    fun getUserId(ctx: Context): Long? {
        val id = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getLong(KEY_USER_ID, -1L)
        return if (id == -1L) null else id
    }
    fun clear(ctx: Context) {
        ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().remove(KEY_USER_ID).apply()
    }
}

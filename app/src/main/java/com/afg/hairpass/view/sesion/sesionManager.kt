package com.afg.hairpass.view.sesion

import android.content.Context
import android.content.SharedPreferences

class sesionManager (context: Context) {

        private val prefName = "LoginSession"
        private val email = "email"

        private val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        private val editor: SharedPreferences.Editor = sharedPreferences.edit()

        fun createLoginSession(email : String) {
            editor.putString(this.email, email)
            editor.apply()
        }

        fun isLoggedIn(): Boolean {
            return sharedPreferences.contains(email)
        }

        fun getUserEmail(): String? {
            return sharedPreferences.getString(email, null)
        }


        fun logout() {
            editor.clear()
            editor.apply()
        }
    }

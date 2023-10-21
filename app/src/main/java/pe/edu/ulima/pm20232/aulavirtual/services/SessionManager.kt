package pe.edu.ulima.pm20232.aulavirtual.services

import android.content.Context
import android.content.SharedPreferences

class SessionManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("my_session", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveCredentials(username: String, password: String) {
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }

    fun getSavedUsername(): String? {
        return sharedPreferences.getString("username", null)
    }

    fun getSavedPassword(): String? {
        return sharedPreferences.getString("password", null)
    }

    fun clearCredentials() {
        editor.remove("username")
        editor.remove("password")
        editor.apply()
    }
}

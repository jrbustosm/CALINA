package co.edu.ut.jrbustosm.calina.ui

import android.content.Context
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import java.util.*

suspend fun Context.setAppLocale(language: String, appViewModel: AppViewModel): Context {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = resources.configuration
    config.setLocale(locale)
    config.setLayoutDirection(locale)
    config.setLocale(locale)
    val resources = this.resources
    @Suppress("DEPRECATION")
    resources.updateConfiguration(config, resources.displayMetrics)
    if(language!="")
        appViewModel.setLanguage(language)
    return createConfigurationContext(config)
}
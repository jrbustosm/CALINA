package co.edu.ut.jrbustosm.calina

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.edu.ut.jrbustosm.calina.repositories.AppRepository
import co.edu.ut.jrbustosm.calina.repositories.AppRepositoryDB
import co.edu.ut.jrbustosm.calina.ui.cardDetail.GetCardDetailScreen
import co.edu.ut.jrbustosm.calina.ui.main.GetMainScreen
import co.edu.ut.jrbustosm.calina.ui.setAppLocale
import co.edu.ut.jrbustosm.calina.ui.theme.CALINATheme
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class MainActivity : ComponentActivity() {

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContext = applicationContext
        isWritePermission = (
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
        )
        if (isWritePermission) {
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 1)
        }

        setContent {

            val context = LocalContext.current

            val appViewModel: AppViewModel by viewModels {
                val appRepository:AppRepository = AppRepositoryDB(context)
                AppViewModelFactory(appRepository)
            }

            appViewModel.fetchCards(){
                GlobalScope.launch(Dispatchers.Main) {
                    context.setAppLocale(appViewModel.appUIState.language, appViewModel)
                }
            }   //Get All Cards



            CALINATheme {

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        GetMainScreen(appViewModel, navController)
                    }
                    composable(
                        route = "cardDetail/{imei_maker}/{imei_card}",
                        arguments = listOf(
                            navArgument("imei_maker"){ type = NavType.StringType },
                            navArgument("imei_card"){ type = NavType.StringType }
                        )
                    ){ backStackEntry ->
                        val imeiMaker = backStackEntry.arguments?.getString("imei_maker")
                        val imeiCard = backStackEntry.arguments?.getString("imei_card")
                        val cardUIState = if (imeiMaker != null && imeiCard!=null)
                            appViewModel.getByID(imeiMaker, imeiCard)
                            else null

                        if(cardUIState!=null) {
                            GetCardDetailScreen(
                                cardUIState = cardUIState,
                                appViewModel = appViewModel,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        lateinit var appContext: Context
        var isWritePermission: Boolean = false
    }
}
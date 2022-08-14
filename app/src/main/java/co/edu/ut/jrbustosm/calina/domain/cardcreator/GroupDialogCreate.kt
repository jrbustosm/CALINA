package co.edu.ut.jrbustosm.calina.domain.cardcreator

import androidx.compose.ui.platform.LocalContext
import co.edu.ut.jrbustosm.calina.R
import kotlinx.coroutines.GlobalScope

class GroupDialogCreate: CardDialogCreate() {

    override fun invoke(dialogFragment: DialogFragment) =
        dialogFragment==DialogFragment.GROUP

}
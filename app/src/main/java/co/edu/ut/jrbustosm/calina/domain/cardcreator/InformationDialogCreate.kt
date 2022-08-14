package co.edu.ut.jrbustosm.calina.domain.cardcreator

class InformationDialogCreate: CardDialogCreate() {

    override fun invoke(dialogFragment: DialogFragment): Boolean =
        dialogFragment == DialogFragment.INFORMATION
}
package co.edu.ut.jrbustosm.calina.domain.cardcreator

class ActionDialogCreate: CardDialogCreate() {

    override fun invoke(dialogFragment: DialogFragment): Boolean =
        dialogFragment == DialogFragment.ACTION
}
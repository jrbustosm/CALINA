package co.edu.ut.jrbustosm.calina.domain.cardcreator

class GroupSecondaryDialogCreate: CardDialogCreate() {

    override fun invoke(dialogFragment: DialogFragment): Boolean =
        dialogFragment == DialogFragment.SECONDARY_GROUP
}
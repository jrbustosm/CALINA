package co.edu.ut.jrbustosm.calina.domain.cardcreator

class RewardDialogCreate: CardDialogCreate() {

    override fun invoke(dialogFragment: DialogFragment): Boolean =
        dialogFragment == DialogFragment.REWARD
}
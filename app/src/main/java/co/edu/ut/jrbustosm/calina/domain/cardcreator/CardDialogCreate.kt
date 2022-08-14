package co.edu.ut.jrbustosm.calina.domain.cardcreator

abstract class CardDialogCreate {

    abstract operator fun invoke(dialogFragment: DialogFragment): Boolean
}

enum class DialogFragment {
    GROUP,
    INFORMATION,
    ACTION,
    REWARD,
    SECONDARY_GROUP
}

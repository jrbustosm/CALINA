package co.edu.ut.jrbustosm.calina.domain.triggers.effects

enum class EffectType(val id:Char, val re: Regex){
    DESTROY('D', Regex("^$")),
    MONEY('M', Regex("^:-?[0-9]+$")),
    CARD('C', Regex("^:[^|_#:]{1,40}:[^|_#:]{1,280}:[XIRG]:[CBASWX]:[0-9]{1,4}:[^|_#:]+:[10]$")),
    TITLE('T', Regex("^:[^|_#:]{1,40}$")),
    DIFFICULTY('Y', Regex("^:[CBASWX]$")),
    IMAGE('I', Regex("^:[^|_#:]+$")),
    DESCRIPTION('N', Regex("^:[^|_#:]{1,280}$")),
    INCREASE('X', Regex("^$")),
    DECREASE('Z', Regex("^$")),
    RESET('R', Regex("^$")),
    EDIT('E', Regex("^$")),
    USED('U', Regex("^$")),
    CLONE('H', Regex("^$")),
    MESSAGE('G', Regex("^:[^|_#:]{1,280}$")),
    NOTHING('O', Regex("^$")),
    REMOVE_TRIGGER('V', Regex("^$")),
    IMEI_CARD('Q', Regex("^$")),
    DATE_REG('A', Regex("^$")),
    DATE_REG_RESET('B', Regex("^$"));

    companion object {
        fun getAllValues(): String {
            val sb = StringBuilder()
            for (e in values()) sb.append(e.id)
            return sb.toString()
        }

        fun getEffectType(effectType: Char): EffectType = when (effectType) {
            'D' -> DESTROY
            'M' -> MONEY
            'C' -> CARD
            'T' -> TITLE
            'Y' -> DIFFICULTY
            'I' -> IMAGE
            'N' -> DESCRIPTION
            'X' -> INCREASE
            'Z' -> DECREASE
            'R' -> RESET
            'E' -> EDIT
            'U' -> USED
            'H' -> CLONE
            'G' -> MESSAGE
            'V' -> REMOVE_TRIGGER
            'Q' -> IMEI_CARD
            'A' -> DATE_REG
            'B' -> DATE_REG_RESET
            else -> NOTHING
        }
    }
}
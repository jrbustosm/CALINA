package co.edu.ut.jrbustosm.calina.domain.triggers.conditions

enum class ConditionType(val id:Char, val re: Regex){
    TRUE('T', Regex("^$")),
    RANDOM('R', Regex("^:[0-9]{1,3}$")),
    COUNT('C', Regex("^:[0-9]+$")),
    DATE_CREATE('D', Regex("^:[0-9]+$")),
    NEIGHBOUR('N', Regex("(^:[0-9]+)+$")),
    IMEI('I', Regex("^:[0-9]+$")),
    STATE('S', Regex("^:[NUB]$")),
    DATE_EXPIRED('E', Regex("^:[0-9]+$")),
    DESCRIPTION('W', Regex("^:[^|_#:]+$")),
    MONEY('M', Regex("^:[0-9]+$")),
    COUNT_CARDS('X', Regex("^:[0-9]+$")),
    LEVEL('L', Regex("^:[^|_#:]+$")),
    HOLIDAY('H', Regex("^:[1-3]?[0-9]:1?[0-9]:[0-9][0-9][0-9][0-9]$")),
    FALSE('F', Regex("^$")),
    DATE_REG('A', Regex("^:[0-9]+$")),;

    companion object{
        fun getAllValues(): String{
            val sb = StringBuilder()
            for(e in values()) sb.append(e.id)
            return sb.toString()
        }
        fun getConditionType(conditionType: Char): ConditionType = when(conditionType){
            'T' -> TRUE
            'R' -> RANDOM
            'C' -> COUNT
            'D' -> DATE_CREATE
            'N' -> NEIGHBOUR
            'I' -> IMEI
            'S' -> STATE
            'E' -> DATE_EXPIRED
            'W' -> DESCRIPTION
            'M' -> MONEY
            'X' -> COUNT_CARDS
            'L' -> LEVEL
            'H' -> HOLIDAY
            else -> FALSE
        }
    }
}
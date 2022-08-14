package co.edu.ut.jrbustosm.calina.domain.triggers

enum class EventType(val id:Char){
    OnUse('U'),
    OnGift('G'),
    OnReceive('R'),
    OnDestroy('D'),
    OnBuy('B'),
    OnEdit('E'),
    OnValidate('V'),
    OnOpenDetail('W'),
    OnList('L'),
    OnExpired('X'),
    OnSelect('S'),
    OnSelectByDay('Z');

    companion object{
        fun getAllValues(): String{
            val sb = StringBuilder()
            for(e in values()) sb.append(e.id)
            return sb.toString()
        }
        fun getEventType(triggerContext: Char): EventType? = when(triggerContext){
            'U' -> OnUse
            'G' -> OnGift
            'R' -> OnReceive
            'D' -> OnDestroy
            'B' -> OnBuy
            'E' -> OnEdit
            'V' -> OnValidate
            'W' -> OnOpenDetail
            'L' -> OnList
            'S' -> OnSelect
            'Z' -> OnSelectByDay
            'X' -> OnExpired
            else -> null
        }
    }
}
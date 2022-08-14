package co.edu.ut.jrbustosm.calina.domain.crypto

class Uncompress {

    operator fun invoke(text:String): String{
        var s = text
        for(it in Compress.finders.reversed()) s = s.replace(oldValue = it.second, newValue = it.first)
        return s
    }
}
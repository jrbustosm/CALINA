package co.edu.ut.jrbustosm.calina.domain.crypto

class Compress {

    operator fun invoke(text:String): String{
        var s = text
        for(it in finders) s = s.replace(oldValue = it.first, newValue = it.second)
        return s
    }

    companion object{
        val finders = listOf(
            Pair("\"cash\":", "A~"),
            Pair("\"cash_symbol\":", "B~"),
            Pair("\"date_create\":", "C~"),
            Pair("\"date_expire\":", "D~"),
            Pair("\"description\":", "E~"),
            Pair("\"difficulty\":", "F~"),
            Pair("\"imageResource\":", "G~"),
            Pair("\"imei_card\":", "H~"),
            Pair("\"imei_maker\":", "I~"),
            Pair("\"imei_owner\":\"", "J~"),
            Pair("\"isCloneable\":", "K~"),
            Pair("\"isEdit\":", "L~"),
            Pair("\"isSecondary\":", "M~"),
            Pair("\"isSelect\":", "N~"),
            Pair("\"isSymbol\"", "O~"),
            Pair("\"isTransfer\":", "P~"),
            Pair("\"level\":", "Q~"),
            Pair("\"levels\":", "R~"),
            Pair("\"number\":", "S~"),
            Pair("\"scope\":", "T~"),
            Pair("\"state\":", "U~"),
            Pair("\"title\":", "W~"),
            Pair("\"trigger\":", "X~"),
            Pair("\"type\":", "Y~"),
            Pair("\"count\":", "Z~"),
            Pair("~false", "~a"),
            Pair("~true", "~b"),
            Pair("~\"VERY_EASY\"", "~c"),
            Pair("~\"EASY\"", "~d"),
            Pair("~\"NORMAL\"", "~e"),
            Pair("~\"HARD\"", "~f"),
            Pair("~\"VERY_HARD\"", "~g"),
            Pair("~\"UNIQUE\"", "~h"),
            Pair("~\"ACTION\"", "~i"),
            Pair("~\"REWARD\"", "~j"),
            Pair("~\"INFORMATION\"", "~k"),
            Pair("~\"GROUP\"", "~l"),
            Pair("\"date_reg\":", "m~"),
        )
    }

}
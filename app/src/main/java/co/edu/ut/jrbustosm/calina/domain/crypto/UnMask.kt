package co.edu.ut.jrbustosm.calina.domain.crypto

class UnMask {

    operator fun invoke(text: String): String{
        val unmaskText = StringBuilder()
        var indexCount = 0
        var count = Mask.jumps[indexCount].second
        text.forEach { c->
            count--
            if(count==0){
                indexCount = (indexCount+1) % Mask.jumps.size
                count = Mask.jumps[indexCount].second + 1
            }else unmaskText.append(c)
        }
        return unmaskText.toString()
    }
}
package co.edu.ut.jrbustosm.calina.domain.crypto

class Mask {

    companion object {
        val jumps = listOf(
            Pair('G', 100),
            Pair('L', 100),
            Pair('O', 100),
            Pair('R', 100),
            Pair('I', 100),
            Pair('S', 100),
            Pair('E', 100),
            Pair('T', 100),
        )
    }

    operator fun invoke(text: String): String{
        val maskText = StringBuilder()
        var indexCount = 0
        var count = jumps[indexCount].second
        text.forEach { c ->
            count--
            if(count==0){
                maskText.append(jumps[indexCount].first)
                indexCount = (indexCount+1) % jumps.size
                count = jumps[indexCount].second
            }
            maskText.append(c)
        }
        return maskText.toString()
    }
}
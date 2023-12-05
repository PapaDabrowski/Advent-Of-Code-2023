import java.io.File

class Solver {
    private var inputList = mutableListOf<String>()
    fun firstSolution(): Int {
        var value: Int = 0
        val mapColours = mapOf("green" to 13, "blue" to 14, "red" to 12)
        for(line in inputList){
            val ID = Integer.parseInt(line.substringBefore(":").filter{it.isDigit()})
            val sets = line.substringAfter(": ").split("; ").toTypedArray()
            var flagWrong = 0
            for(set in sets)
            {
                for(s in set.split(",").toTypedArray())
                {
                    mapColours.forEach{ (key,value) ->
                        if (s.contains(key)) {
                            if (Integer.parseInt(s.filter { it.isDigit() }) > Integer.parseInt(mapColours.get(key).toString()))
                                flagWrong = -1
                        }
                    }
                }
            }
            if(flagWrong != -1) value += ID
        }
        return value
    }

    fun secondSolution(): Int {
        var value: Int = 0
        for(line in inputList){
            var mapColoursForSecond = mutableMapOf("green" to 0, "blue" to 0, "red" to 0)
            val sets = line.substringAfter(": ").split("; ").toTypedArray()
            var subtotal = 1
            for(set in sets)
            {
                for(s in set.split(",").toTypedArray())
                {
                    mapColoursForSecond.forEach{ (key,value) ->
                        if (s.contains(key)) {
                            if (Integer.parseInt(s.filter { it.isDigit() }) > Integer.parseInt(mapColoursForSecond.get(key).toString()))
                                mapColoursForSecond.replace(key, Integer.parseInt(s.filter { it.isDigit() }))
                        }
                    }
                }
            }
            mapColoursForSecond.forEach{ (key,value) ->
                subtotal *= value
            }
            value += subtotal
        }
        return value
    }

    fun readFileLineByLine(fileName : String) = File(fileName).forEachLine {
        inputList.add(it)
    }
}
import java.io.File

class Solver {
    private var inputList = mutableListOf<String>()
    private var mapValues = listOf<String>("1", "2", "3", "4", "5", "6",
        "7", "8", "9", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    private var numbersMap = mapOf("one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7,
        "eight" to 8, "nine" to 9)

    fun firstSolution(): Int {
        var value: Int = 0
        for(line in inputList){
            var result = line.filter {it.isDigit()}
            value += 10 * Integer.parseInt(result.first().toString()) + Integer.parseInt(result.last().toString())
        }
        return value
    }

    fun secondSolution(): Int {
        var value: Int = 0
        for(line in inputList){
            var firstValue = ""
            var firstIndex = Int.MAX_VALUE
            var lastValue = ""
            var lastIndex = Int.MIN_VALUE

            for (mV in mapValues)
            {
                var index = line.indexOf(mV, ignoreCase=true)
                if(index != -1) {
                    if (index < firstIndex) {
                        firstIndex = index
                        if(mV.length > 1) firstValue = numbersMap.get(mV).toString()
                        else firstValue = mV
                    }
                }
                index = line.lastIndexOf(mV, ignoreCase=true)
                    if (index > lastIndex) {
                        lastIndex = index
                        if(mV.length > 1) lastValue = numbersMap.get(mV).toString()
                        else lastValue = mV
                    }
            }
            value += 10 * Integer.parseInt(firstValue) + Integer.parseInt(lastValue)
        }
        return value
    }

    fun readFileLineByLine(fileName : String) = File(fileName).forEachLine {
        inputList.add(it)
    }
}

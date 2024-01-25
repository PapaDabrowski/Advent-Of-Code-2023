import java.io.File
import kotlin.math.sqrt

class Solver {
    private var inputList = mutableListOf<String>()

    fun firstSolution(): Int {
        var result = 1
        var listOfRecords = inputList[0].substringAfter(": ").split(" ").filter { !it.isNullOrEmpty() }.map { it.toInt() }.toMutableList()
            .zip(inputList[1].substringAfter(": ").split(" ").filter { !it.isNullOrEmpty() }.map { it.toInt() }.toMutableList())
        for(elem in listOfRecords)
        {
            var delta = elem.first * elem.first - 4 * elem.second
            result *= ((- elem.first - sqrt(delta.toDouble())) / ( -2 )).toInt() - ((- elem.first + sqrt(delta.toDouble())) / ( -2 )).toInt()
        }
        return result
    }

    fun secondSolution(): Int {
        var elem = Pair(inputList[0].substringAfter(": ").filter { it.isDigit() }.toLong(), inputList[1].substringAfter(": ").filter { it.isDigit() }.toLong())
        var delta= elem.first * elem.first - 4 * elem.second
        return ((- elem.first - sqrt(delta.toDouble())) / ( -2 )).toInt() - ((- elem.first + sqrt(delta.toDouble())) / ( -2 )).toInt()
    }

    fun readFileLineByLine(fileName : String) = File(fileName).forEachLine {
        inputList.add(it)
    }
}

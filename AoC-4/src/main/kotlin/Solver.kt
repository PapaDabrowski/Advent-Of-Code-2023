import java.io.File
import kotlin.math.*

class Solver {
    private var inputList = mutableListOf<String>()

    fun firstSolution(): Int {
        var value: Int = 0
        for(line in inputList)
        {
            var pairs = 0
            var base = 2.0
            var winNumbers = line.substringAfter(": ").substringBefore("|").split(" ").filter { !it.isNullOrEmpty() }.toList()
            var ownNumbers = line.substringAfter(": ").substringAfter("|").split(" ").filter { !it.isNullOrEmpty() }.toList()
            for(w in winNumbers)
                for(o in ownNumbers)
                    if(w == o) pairs++
            value += base.pow(pairs - 1).toInt()
        }
        return value
    }

    fun secondSolution(): Int {
        var amountOfCards : MutableList<Int> = MutableList(192) { 1 }
        var value: Int = 0
        for((index, line) in inputList.withIndex()) {
            var pairs = 0
            var winNumbers = line.substringAfter(": ").substringBefore("|").split(" ").filter { !it.isNullOrEmpty() }.toList()
            var ownNumbers = line.substringAfter(": ").substringAfter("|").split(" ").filter { !it.isNullOrEmpty() }.toList()
            for(w in winNumbers)
                if(ownNumbers.contains(w)) pairs++
            for(i in 1..pairs)
                if(index + i < amountOfCards.size) amountOfCards[index + i] = amountOfCards.get(index + i) + (amountOfCards.get(index) * 1)
        }
        for(i in amountOfCards) value += i
        return value
    }

    fun readFileLineByLine(fileName : String) = File(fileName).forEachLine {
        inputList.add(it)
    }
}

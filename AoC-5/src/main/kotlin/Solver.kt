import java.io.File
import kotlin.math.max
import kotlin.math.min

class Solver {
    private var inputList = mutableListOf<String>()

    inline fun <E> Iterable<E>.indexesOf(predicate: (E) -> Boolean)
            = mapIndexedNotNull{ index, elem -> index.takeIf{ predicate(elem) } }
    fun firstSolution(): Long {
        var seedsNumbers = inputList.get(0).substringAfter(": ").split(" ").map { it.toLong() }.toMutableList()
        var startStopIndexes = inputList.indexesOf { Regex("^.*map.*\$").matches(it) }
        for((index, input) in startStopIndexes.withIndex())
        {
            var tempList = inputList.subList(input + 1, inputList.lastIndex + 1)
            if(startStopIndexes.listIterator(index + 1).hasNext())
                tempList = inputList.subList(input + 1, startStopIndexes.listIterator(index + 1).next() - 1)
            for(seed in seedsNumbers)
                for(temp in tempList)
                {
                    val tempValues = temp.split(" ").map { it.toLong() }.toList()
                    val myRange = 0..tempValues.get(2)
                    if(myRange.contains(seed - tempValues.get(1))) {
                        seedsNumbers[seedsNumbers.indexOf(seed)] = tempValues.get(0) + (seed - tempValues.get(1))
                        break
                    }
                }
        }
        return seedsNumbers.min()
    }
    fun secondSolution(): Long {
        var seedsNumbers = inputList.get(0).substringAfter(": ").split(" ").map { it.toLong() }.toMutableList().chunked(2).toMutableList()
        var startStopIndexes = inputList.indexesOf { Regex("^.*map.*\$").matches(it) }
        for((index, input) in startStopIndexes.withIndex())
        {
            for(i in 1..10000) seedsNumbers.add(mutableListOf(-1,-1)) //nasty hack, kotlin do not allowed to extend list during iteration which is possible in C++.

            var tempList = inputList.subList(input + 1, inputList.lastIndex + 1)
            if(startStopIndexes.listIterator(index + 1).hasNext())
                tempList = inputList.subList(input + 1, startStopIndexes.listIterator(index + 1).next() - 1)
            var tempNumbers = mutableListOf<Long>()

            for(i in 0 .. 10000){
                val seed = seedsNumbers[i]
                if(seed[0] < 0) break
                var tempNumbersLength = tempNumbers.size
                for(temp in tempList)
                {
                    val tempValues = temp.split(" ").map { it.toLong() }.toList()
                    if ((max(seed[0], tempValues.get(1)) <= min(seed[0] + seed[1], tempValues.get(1) + tempValues.get(2)))) {
                        if (seed[0] < tempValues.get(1)) {
                            tempNumbers.add(tempValues.get(0))
                            if(!seedsNumbers.contains(mutableListOf(seed[0], tempValues.get(1) - seed[0])))
                                seedsNumbers.add(seedsNumbers.indexOf(seed) + 1, mutableListOf(seed[0], tempValues.get(1) - seed[0]))
                            if (seed[0] + seed[1] < tempValues.get(1) + tempValues.get(2)) tempNumbers.add(seed[0] + seed[1] - tempValues.get(1))
                            else
                            {
                                tempNumbers.add(tempValues.get(2))
                                if(!seedsNumbers.contains(mutableListOf(tempValues.get(1) + tempValues.get(2), seed[0] + seed[1] - tempValues.get(1) - tempValues.get(2))))
                                    seedsNumbers.add(seedsNumbers.indexOf(seed) + 1, mutableListOf(tempValues.get(1) + tempValues.get(2), seed[0] + seed[1] - tempValues.get(1) - tempValues.get(2)))
                            }
                        }
                        if (seed[0] > tempValues.get(1)) {
                            tempNumbers.add(tempValues.get(0) + seed[0] - tempValues.get(1))
                            if (seed[0] + seed[1] < tempValues.get(1) + tempValues.get(2)) {
                                tempNumbers.add(seed[1])
                                break
                            }
                            else
                            {
                                tempNumbers.add(tempValues.get(1) + tempValues.get(2) - seed[0])
                                if(!seedsNumbers.contains(mutableListOf(tempValues.get(1) + tempValues.get(2), seed[0] + seed[1] - tempValues.get(1) - tempValues.get(2))))
                                    seedsNumbers.add(seedsNumbers.indexOf(seed) + 1, mutableListOf(tempValues.get(1) + tempValues.get(2), seed[0] + seed[1] - tempValues.get(1) - tempValues.get(2)))
                            }
                        }
                    }
                }
                if(tempNumbers.size == tempNumbersLength)
                {
                    tempNumbers.add(seed[0])
                    tempNumbers.add(seed[1])
                }
            }
            seedsNumbers = tempNumbers.chunked(2).toMutableList()
        }
        var temp = Long.MAX_VALUE
        for(seed in seedsNumbers)
            if(temp > seed[0]) temp = seed[0]
        return temp
    }
    fun readFileLineByLine(fileName : String) = File(fileName).forEachLine {
        inputList.add(it)
    }
}

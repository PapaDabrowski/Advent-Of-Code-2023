import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Solver {
    private var inputList = mutableListOf<String>()
    fun firstSolution(): Int {
        val result = solver(2)
        return result.toInt()
    }

    fun secondSolution(): Long {
        val result = solver(1000000)
        return result
    }

    fun solver(scaleFactor : Int): Long {
        var result: Long = 0
        var temp = mutableListOf<MutableList<String>>()
        for (line in inputList) temp += line.split("").filter { it.isNotEmpty() }.toMutableList()

        var galaxiesCoordinates = mutableListOf<Pair<Int,Int>>()
        for(i in 0..<temp.size)
            for(j in 0..<temp[i].size)
                if(temp[i][j] == "#") galaxiesCoordinates.add(Pair( i, j))

        val coordinates = getEmptyLinesCoordinates()
        for(galaxy in galaxiesCoordinates){
            for(nextGalaxy in galaxiesCoordinates.subList(galaxiesCoordinates.indexOf(galaxy), galaxiesCoordinates.size)) {
                val rowRange = min(galaxy.first, nextGalaxy.first)..max(galaxy.first, nextGalaxy.first)
                val columnRange = min(galaxy.second, nextGalaxy.second)..max(galaxy.second, nextGalaxy.second)
                result += abs(galaxy.first - nextGalaxy.first) + abs(galaxy.second - nextGalaxy.second)
                var count = 0
                for(i in rowRange) if(coordinates.first.contains(i)) count++
                for(i in columnRange) if (coordinates.second.contains(i)) count++
                result -= count
                result += (count * scaleFactor)
            }
        }
        return result
    }
    private fun getEmptyLinesCoordinates() : Pair<List<Int>, List<Int>>
    {
        val regex = Regex("#")
        var rows = mutableListOf<Int>()
        for(i in 0..<inputList.size) if (!regex.containsMatchIn(inputList[i])) rows.add(i)

        var columns = mutableListOf<Int>()
        for(i in 0 ..<inputList.get(0).length) {
            var verticalLine = ""
            for (line in inputList) verticalLine += line[i]
            if (!regex.containsMatchIn(verticalLine)) columns.add(i)
        }
        return Pair(rows, columns)
    }
    fun readFileLineByLine(fileName: String) = File(fileName).forEachLine {
        inputList.add(it)
    }
}
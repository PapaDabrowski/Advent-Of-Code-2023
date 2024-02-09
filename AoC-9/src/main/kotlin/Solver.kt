import java.io.File

class Solver {
    private var inputList = mutableListOf<String>()

    fun firstSolution(): Int {
        var result : Int = 0
        for(line in inputList)
        {
            val generatedLinesList = mutableListOf<List<Int>>()
            var list =  line.split(" ").map { it.toInt() }.toMutableList()
            while(!list.all { it == 0 } && generatedLinesList.size < line.split(" ").size + 2){
                generatedLinesList.add(list)
                list = list.zipWithNext().map { it.second - it.first }.toMutableList()
            }
            result += generatedLinesList.sumOf { it.last() }
        }
        return result
    }

    fun secondSolution(): Int {
        var result : Int = 0
        for(line in inputList)
        {
            val generatedLinesList = mutableListOf<List<Int>>()
            var list =  line.split(" ").map { it.toInt() }.toMutableList()
            while(!list.all { it == 0 } && generatedLinesList.size < line.split(" ").size + 2){
                generatedLinesList.add(list)
                list = list.zipWithNext().map { it.second - it.first }.toMutableList()
            }
            var temp = 0
            for(elem in generatedLinesList.reversed()) temp = elem.first() - temp
            result += temp
        }
        return result
    }
    fun readFileLineByLine(fileName: String) = File(fileName).forEachLine {
        inputList.add(it)
    }
}
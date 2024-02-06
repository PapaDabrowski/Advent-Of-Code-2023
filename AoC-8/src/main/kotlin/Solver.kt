import java.io.File

class Solver {
    private var inputList = mutableListOf<String>()

    fun firstSolution(): Int {
        var result : Int = 1
        var leftRightInstructions = inputList.get(0)
        var listOfCoordinates = prepareDataCoordinates()
        var found = false
        var key = "AAA"
        while(!found)
            for(i in leftRightInstructions)
            {
                var temp = listOfCoordinates[key]?.first()
                if (temp != null) key = if(i == 'L') temp.first else temp.second
                if (key == "ZZZ") {
                    found = true
                    break
                }
                result++
            }

        return result
    }

    fun secondSolution(): Long {
        var startPoints = mutableListOf<String>()
        var leftRightInstructions = inputList.get(0)
        var listOfCoordinates = prepareDataCoordinates()

        for(elem in listOfCoordinates) if (elem.key.endsWith('A')) startPoints.add(elem.key)
        var found = false
        for(j in 0..<startPoints.size) {
            var result : Int = 0
            var found = false
            while(!found) {
                for (i in leftRightInstructions) {
                    var key = startPoints[j]
                    var temp = listOfCoordinates[key]?.first()
                    if (temp != null) startPoints[j] = if (i == 'L') temp.first else temp.second
                    result++
                    if (startPoints[j].endsWith('Z')) {
                        startPoints[j] = result.toString()
                        found = true
                        break
                    }
                }
            }
        }
        return findLCMOfListOfNumbers(startPoints.map { it.toLong() }.toMutableList())
    }

    //ready-made solution from baeldung
    fun findLCM(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0.toLong() && lcm % b == 0.toLong()) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    fun findLCMOfListOfNumbers(numbers: List<Long>): Long {
        var result = numbers[0]
        for (i in 1 until numbers.size) {
            result = findLCM(result, numbers[i])
        }
        return result
    }

    fun prepareDataCoordinates(): Map<String, List<Pair<String, String>>> {
        return inputList.subList(2, inputList.size).groupBy({ it.substringBefore(" = ") },
            { Pair<String,String> (it.substringAfter("(").substringBefore(","), it.substringAfter(", ").substringBefore(")") )})
    }

    fun readFileLineByLine(fileName: String) = File(fileName).forEachLine {
        inputList.add(it)
    }
}
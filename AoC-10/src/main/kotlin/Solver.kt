import java.io.File
import java.lang.Error
import java.lang.StringBuilder

class Solver {
    private var inputList = mutableListOf<String>()
    private var pipeLine = mutableListOf<Triple<String, Int, Int>>()
    fun firstSolution(): Int {
        var result: Int = 0
        var curr: Triple<String, Int, Int> = Triple<String, Int, Int>("", 0, 0)
        for (elem in inputList) if (elem.contains('S')) {
            curr = Triple("W", inputList.indexOf(elem), elem.indexOf('S'))
            pipeLine.add(curr)
            break
        }
        var previous = curr
        if(curr.third - 1 >= 0 && (inputList.get(curr.second).get(curr.third - 1).toString() == "-" || inputList.get(curr.second).get(curr.third - 1).toString() == "L" || inputList.get(curr.second).get(curr.third - 1).toString() == "F"))
            curr = Triple(inputList.get(curr.second).get(curr.third - 1).toString(), curr.second, curr.third - 1)
        else if(curr.third + 1 <= inputList.get(0).length && (inputList.get(curr.second).get(curr.third + 1).toString() == "-" || inputList.get(curr.second).get(curr.third + 1).toString() == "J" || inputList.get(curr.second).get(curr.third + 1).toString() == "7"))
            curr = Triple(inputList.get(curr.second).get(curr.third + 1).toString(), curr.second, curr.third + 1)
        else if(curr.second - 1 >= 0 && (inputList.get(curr.second - 1).get(curr.third).toString() == "|" || inputList.get(curr.second - 1).get(curr.third).toString() == "F" || inputList.get(curr.second - 1).get(curr.third).toString() == "7"))
            curr = Triple(inputList.get(curr.second - 1).get(curr.third).toString(), curr.second - 1, curr.third)
        else if(curr.second + 1 <= inputList.size && (inputList.get(curr.second + 1).get(curr.third).toString() == "|" || inputList.get(curr.second + 1).get(curr.third).toString() == "L" || inputList.get(curr.second + 1).get(curr.third).toString() == "J"))
            curr = Triple(inputList.get(curr.second + 1).get(curr.third).toString(), curr.second + 1, curr.third)

        while (curr.first != "S") {
            var directionY = curr.second - previous.second
            var directionX = curr.third - previous.third
            //inputList.set(curr.second, inputList.get(curr.second).replaceInString(curr.third, "X"))
            previous = curr
            pipeLine.add(curr)
            if (curr.first == "|") {
                curr = Triple<String, Int, Int>(
                    inputList.get(curr.second + directionY).get(curr.third).toString(),
                    curr.second + directionY,
                    curr.third
                )
            } else if (curr.first == "-") {
                curr = Triple<String, Int, Int>(
                    inputList.get(curr.second).get(curr.third + directionX).toString(),
                    curr.second,
                    curr.third + directionX
                )
            } else if (curr.first == "L") {
                curr = Triple<String, Int, Int>(
                    inputList.get(curr.second + directionX).get(curr.third + directionY).toString(),
                    curr.second + directionX,
                    curr.third + directionY
                )
            } else if (curr.first == "J") {
                curr = Triple<String, Int, Int>(
                    inputList.get(curr.second - directionX).get(curr.third - directionY).toString(),
                    curr.second - directionX,
                    curr.third - directionY
                )
            } else if (curr.first == "7") {
                curr = Triple<String, Int, Int>(
                    inputList.get(curr.second + directionX).get(curr.third + directionY).toString(),
                    curr.second + directionX,
                    curr.third + directionY
                )
            } else if (curr.first == "F") {
                curr = Triple<String, Int, Int>(
                    inputList.get(curr.second - directionX).get(curr.third - directionY).toString(),
                    curr.second - directionX,
                    curr.third - directionY
                )
            } else if (curr.first == ".") {
                println("ERROR - found . instead of pipe!")
                break
            }
            result++
        }
        return (result + (2 - 1)) / 2
    }

    fun secondSolution(): Int {
        var result: Int = 0
        val regex = Regex("[^.]")
        for(k in inputList) {
            val replacedText = regex.replace(k) { "." }
            inputList.set(inputList.indexOf(k), replacedText)
        }

        for(elem in pipeLine) {
            inputList.set(elem.second, inputList.get(elem.second).replaceInString(elem.third, elem.first))
        }

        for(elem in inputList){
            val regexForPipes = Regex("[JL|]")
            val tempElem = regexForPipes.replace(elem, "|")

            val regexForDots = Regex("\\.+")
            val matches = regexForDots.findAll(tempElem)

            for (match in matches) {
                val precedingSubstring = tempElem.substring(0, match.range.first)
                val countX = precedingSubstring.count { it == '|' }
                if(countX % 2 == 1) result += match.range.last - match.range.first + 1
            }
        }
        return result
    }

    fun String.replaceInString(index : Int, newChar : String) : String {
        val sb = StringBuilder(this)
        sb.setCharAt(index, newChar.single())
        return sb.toString()
    }

    fun readFileLineByLine(fileName: String) = File(fileName).forEachLine {
        inputList.add(it)
    }
}
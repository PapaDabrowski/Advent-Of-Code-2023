import java.io.File
class Solver {
    private var inputList = mutableListOf<String>()
    fun firstSolution(): Int {
        var value: Int = 0
        for((index, line) in inputList.withIndex())
        {
            val extractedNumbers = Regex("\\d+").findAll(line).map { it.value }.toList()
            var templine = line
            for (en in extractedNumbers) {
                val startIndex = if (templine.indexOf(en) > 0) templine.indexOf(en) - 1 else templine.indexOf(en)
                var endIndex = if (templine.indexOf(en) + en.length < templine.length) templine.indexOf(en) + en.length + 1 else templine.indexOf(en) + en.length

                var checkString = templine.substring(startIndex, endIndex)
                if(inputList.listIterator(index).hasPrevious()) checkString += (inputList.listIterator(index).previous().substring(startIndex, endIndex))
                if(inputList.listIterator(index + 1).hasNext()) checkString += (inputList.listIterator(index + 1).next().substring(startIndex, endIndex))
                if(Regex("[^\\w. ]").findAll(checkString).map { it.value }.toList().isNotEmpty()) value += Integer.parseInt(en)

                var replacingString = ""
                for(i in 1 .. en.length) replacingString += "."
                templine = templine.replaceFirst(en, replacingString)
            }
        }
        return value
    }
    fun secondSolution(): Int {
        var value: Int = 0
        for((index, line) in inputList.withIndex()) {
            var lineForCheck = mutableListOf<String>()
            lineForCheck.add(line)
            if (inputList.listIterator(index).hasPrevious()) lineForCheck.add(inputList.listIterator(index).previous())
            if (inputList.listIterator(index + 1).hasNext()) lineForCheck.add(inputList.listIterator(index + 1).next())

            for (en in Regex("([*])+").findAll(line).map { it.value }.toList()) {
                var partialValue = 1;
                for (curr in lineForCheck) {
                    var tempLine = curr
                    for (ex in Regex("\\d+").findAll(tempLine).map { it.value }.toList()) {
                        if (-ex.length <= tempLine.indexOf(ex) - lineForCheck[0].indexOf(en) && tempLine.indexOf(ex) - lineForCheck[0].indexOf(en) <= 1)
                        {
                            if(partialValue != 1){
                                partialValue *= Integer.parseInt(ex)
                                value += partialValue
                            }
                            else partialValue *= Integer.parseInt(ex)
                        }
                        var replacingString = ""
                        for (i in 1..ex.length) replacingString += "."
                        tempLine = tempLine.replaceFirst(ex, replacingString)
                    }
                }
                lineForCheck[0] = lineForCheck[0].replaceFirst(en, ".")
            }
        }
        return value
    }

    fun readFileLineByLine(fileName : String) = File(fileName).forEachLine {
        inputList.add(it)
    }
}
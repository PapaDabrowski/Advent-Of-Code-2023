import java.io.File

class Solver {
    private var inputList = mutableListOf<String>()

    class CompareDeckFirst
    {
        companion object : Comparator<Pair<String, Int>> {
            override fun compare(a: Pair<String, Int>, b: Pair<String, Int>): Int {
                val order = "23456789TJQKA"
                if(checkType(a) != checkType(b)) return checkType(a) - checkType(b)
                for(i in 0..a.first.length) if (a.first[i] != b.first[i]) return order.indexOf(a.first[i]) - order.indexOf(b.first[i])
                return 0
            }

            private fun checkType(elem : Pair<String, Int>) : Int
            {
                var res = elem.first.groupingBy { it }.eachCount().filter { it.value > 1 }.toList()
                return when {
                    res.isEmpty() -> 0
                    res.size == 1 && res.get(0).second == 2 -> 1
                    res.size == 2 && res.get(0).second == 2 && res.get(1).second == 2 -> 2
                    res.size == 1 && res.get(0).second == 3 -> 3
                    res.size == 1 && res.get(0).second == 4 -> 5
                    res.size == 1 && res.get(0).second == 5 -> 6
                    else -> 4
                }
            }
        }
    }

    class CompareDeckSecond {
        companion object : Comparator<Pair<String, Int>> {
            override fun compare(a: Pair<String, Int>, b: Pair<String, Int>): Int {
                val order = "J23456789TQKA"
                if (checkType(a) != checkType(b)) return checkType(a) - checkType(b)
                for (i in 0..a.first.length) if (a.first.get(i) != b.first.get(i)) return order.indexOf(a.first.get(i)) - order.indexOf(b.first.get(i))
                return 0
            }

            private fun checkType(elem: Pair<String, Int>): Int {
                var res = elem.first.groupingBy { it }.eachCount().filter { it.value > 1 }.toList().toMutableList()
                var resJ = elem.first.groupingBy { it }.eachCount().filter { it.key == 'J' }.toList()
                if(res.isEmpty() && resJ.isNotEmpty()) {
                    res.add(Pair<Char, Int>(resJ[0].first, 1 + resJ[0].second))
                }
                else if(res.isNotEmpty() && resJ.isNotEmpty()) {
                    res.remove(resJ[0])
                    if(res.isNotEmpty()) res[0] = Pair<Char, Int>(res[0].first, res[0].second + resJ[0].second)
                    else res.add(Pair<Char, Int>(resJ[0].first, 1 + resJ[0].second))
                }
                return when {
                    res.isEmpty() -> 0
                    res.size == 1 && res.get(0).second == 2 -> 1
                    res.size == 2 && res.get(0).second == 2 && res.get(1).second == 2 -> 2
                    res.size == 1 && res.get(0).second == 3 -> 3
                    res.size == 1 && res.get(0).second == 4 -> 5
                    res.size == 1 && (res.get(0).second == 5 || res.get(0).second == 6) -> 6
                    else -> 4
                }
            }
        }
    }

    fun firstSolution(): Int {
        var result : Int = 0
        var sortedDeck = parseData().sortedWith(CompareDeckFirst)
        for (elem in sortedDeck) result += elem.second * (sortedDeck.indexOf(elem) + 1)
        return result
    }

    fun secondSolution(): Int {
        var result : Int = 0
        var sortedDeck = parseData().sortedWith(CompareDeckSecond)
        for (elem in sortedDeck) result += elem.second * (sortedDeck.indexOf(elem) + 1)
        return result
    }

    fun parseData(): MutableList<Pair<String, Int>> {
        var outputList = mutableListOf<Pair<String, Int>>()
        for (line in inputList) outputList.add(Pair(line.substringBefore(" "), line.substringAfter(" ").toInt()))
        return outputList
    }

    fun readFileLineByLine(fileName: String) = File(fileName).forEachLine {
        inputList.add(it)
    }
}

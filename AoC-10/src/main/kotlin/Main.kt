import java.io.FileNotFoundException

fun main(args: Array<String>) {
    val solver = Solver()
    try{
        solver.readFileLineByLine("src/main/kotlin/input.txt")
    }
    catch(e: FileNotFoundException)
    {
        println("$e")
    }
    var result = solver.firstSolution()
    println("Solution 1 = $result")

    var result2 = solver.secondSolution()
    println("Solution 2 = $result2")

    println("Program arguments: ${args.joinToString()}")
}
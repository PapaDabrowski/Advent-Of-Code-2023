import java.io.FileNotFoundException

fun main(args: Array<String>) {
    val testSolver = Solver()
    val solver = Solver()
    try{
        solver.readFileLineByLine("src/main/kotlin/input.txt")
        testSolver.readFileLineByLine("src/main/kotlin/inputTest.txt")
    }
    catch(e: FileNotFoundException)
    {
        println("$e")
    }
    var result = solver.firstSolution()
    println("Solution 1 = $result")
    var resultTest = testSolver.firstSolution()
    println("Test Solution 1 = $resultTest")

    var result2 = solver.secondSolution()
    println("Solution 2 = $result2")
    var resultTest2 = testSolver.secondSolution()
    println("Test Solution 2 = $resultTest2")

    println("Program arguments: ${args.joinToString()}")
}
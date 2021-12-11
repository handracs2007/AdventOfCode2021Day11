import java.io.File

typealias Octopuses = List<MutableList<Int>>

fun Octopuses.zeroiseLitOctopus() = this.map { row -> row.map { if (it > 9) 0 else it }.toMutableList() }
fun Octopuses.countFlashes() = this.sumOf { row -> row.count { it == 0 } }
fun Octopuses.isAllLit() = this.all { row -> row.all { it == 0 } }

fun solvePart1(octopuses: Octopuses) {
    val steps = 100
    var flashes = 0

    var loopOctopuses = octopuses
    for (i in 1..steps) {
        for (y in loopOctopuses.indices) {
            for (x in loopOctopuses[y].indices) {
                increaseEnergy(loopOctopuses, x, y)
            }
        }

        loopOctopuses = loopOctopuses.zeroiseLitOctopus()
        flashes += loopOctopuses.countFlashes()
    }

    println("PART 1 ANSWER")
    println(flashes)
}

fun solvePart2(octopuses: Octopuses) {
    var step = 0
    var loopOctopuses = octopuses

    while (true) {
        step++

        for (y in loopOctopuses.indices) {
            for (x in loopOctopuses[y].indices) {
                increaseEnergy(loopOctopuses, x, y)
            }
        }

        loopOctopuses = loopOctopuses.zeroiseLitOctopus()

        if (loopOctopuses.isAllLit()) {
            println("PART 2 ANSWER")
            println(step + 1)

            break
        }
    }
}

fun increaseEnergy(octopuses: Octopuses, x: Int, y: Int) {
    octopuses[y][x]++

    if (octopuses[y][x] == 10) {
        // Check top left
        if (x - 1 >= 0 && y - 1 >= 0 && octopuses[y - 1][x - 1] <= 9) {
            increaseEnergy(octopuses, x - 1, y - 1)
        }

        // Check top
        if (y - 1 >= 0 && octopuses[y - 1][x] <= 9) {
            increaseEnergy(octopuses, x, y - 1)
        }

        // Check top right
        if (y - 1 >= 0 && x + 1 < octopuses[y - 1].size && octopuses[y - 1][x + 1] <= 9) {
            increaseEnergy(octopuses, x + 1, y - 1)
        }

        // Check right
        if (x + 1 < octopuses[y].size && octopuses[y][x + 1] <= 9) {
            increaseEnergy(octopuses, x + 1, y)
        }

        // Check bottom right
        if (y + 1 < octopuses.size && x + 1 < octopuses[y + 1].size && octopuses[y + 1][x + 1] <= 9) {
            increaseEnergy(octopuses, x + 1, y + 1)
        }

        // Check bottom
        if (y + 1 < octopuses.size && octopuses[y + 1][x] <= 9) {
            increaseEnergy(octopuses, x, y + 1)
        }

        // Check bottom left
        if (y + 1 < octopuses.size && x - 1 >= 0 && octopuses[y + 1][x - 1] <= 9) {
            increaseEnergy(octopuses, x - 1, y + 1)
        }

        // Check left
        if (x - 1 >= 0 && octopuses[y][x - 1] <= 9) {
            increaseEnergy(octopuses, x - 1, y)
        }
    }
//    octopuses.forEach(::println)
//    println()
}

fun main() {
    val octopuses = File("input.txt").readLines().map { row -> row.map { it.digitToInt() }.toMutableList() }

    solvePart1(octopuses)
    solvePart2(octopuses)
}
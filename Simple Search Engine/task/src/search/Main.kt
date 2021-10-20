package search

import java.io.File


 fun main(args: Array<String>) {

    val lines = File(args[1]).readLines()
    val DB = NewDB(lines.size)
    for (lineInd in lines.indices) DB.add(lines[lineInd], lineInd)
    DB.printMap()

    while (true) {
        println("\n=== Menu ===\n1. Find a person\n2. Print all people\n0. Exit")
        val input = readLine()!!
        when (input) {
            "1" -> {
                println("\nSelect a matching strategy: ALL, ANY, NONE")
                val t = readLine()!!
                println("\nEnter a name or email to search all suitable people.")
                val s = readLine()!!
                val out = DB.searchFor(s, NewDB.Search_strategy.valueOf(t)) //napisi lepo
                if (out.isEmpty()) println("No matching people found.")
                else {
                    if (out.size == 1) println("\n${out.size} person found:\n" + out.joinToString("\n"))
                    else println("\n${out.size} persons found:\n" + out.joinToString("\n"))
                }
            }
            "2" -> DB.printAll()
            "0" -> break
            else -> println("\nIncorrect option! Try again.")
        }
    }
    println("\nBye!")
}
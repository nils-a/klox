package com.github.nilsa.lox

import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.exitProcess

internal class Lox {
    internal fun main(args: Array<String>) {
        when (args.count()) {
            0 -> runPrompt()
            1 -> runFile(args[0])
            else -> {
                println("Usage: jlox [script]");
                exitProcess(64);
            }
        }
    }

    private fun runFile(path: String) {
        val bytes = Files.readAllBytes(Paths.get(path))
        run(String(bytes, Charset.defaultCharset()))

        // Indicate an error in the exit code.
        if (hadError) exitProcess(65);
    }

    private fun runPrompt() {
        val input = InputStreamReader(System.`in`)
        val reader = BufferedReader(input)

        while (true) {
            print("> ")
            val line = reader.readLine() ?: break
            run(line)
            hadError = false;
        }
    }

    private fun run(source: String) {
        val scanner = Scanner(source)
        val tokens: List<Token> = scanner.scanTokens()

        // For now, just print the tokens.
        for (token in tokens) {
            println(token)
        }
    }

    companion object {
        var hadError: Boolean = false

        fun error(line: Int, message: String) {
            report(line, "", message)
        }

        private fun report(
            line: Int, where: String,
            message: String
        ) {
            println("[line $line] Error$where: $message")
            hadError = true
        }
    }
}
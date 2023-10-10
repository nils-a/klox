package com.github.nilsa.lox

import org.junit.jupiter.api.Test

class ScannerTests {
    @Test
    fun `simple print statement results in 5 tokens`() {
        // given
        val input = "print(\"foo\")"
        val scanner = Scanner(input)

        // when
        val actual = scanner.scanTokens()

        // then
        actual.map { it.type }.shouldBeEqualTo(
            TokenType.PRINT,
            TokenType.LEFT_PAREN,
            TokenType.STRING,
            TokenType.RIGHT_PAREN,
            TokenType.EOF,
        )
    }

    @Test
    fun `line comments do not result in any tokens`() {
        // given
        val input = "// what dis?"
        val scanner = Scanner(input)

        // when
        val actual = scanner.scanTokens()

        // then
        actual.map { it.type }.shouldBeEqualTo(
            TokenType.EOF,
        )
    }

    @Test
    fun `multi-line comments do not result in any tokens`() {
        // given
        val input = """
            /* this is 
             * a multi-line
             * comment
             */
        """.trimIndent()
        val scanner = Scanner(input)

        // when
        val actual = scanner.scanTokens()

        // then
        actual.map { it.type }.shouldBeEqualTo(
            TokenType.EOF,
        )
    }

    @Test
    fun `multi-line comments also work inline`() {
        // given
        val input ="""print("foo" /* and nothing else */)"""
        val scanner = Scanner(input)

        // when
        val actual = scanner.scanTokens()

        // then
        actual.map { it.type }.shouldBeEqualTo(
            TokenType.PRINT,
            TokenType.LEFT_PAREN,
            TokenType.STRING,
            TokenType.RIGHT_PAREN,
            TokenType.EOF,
        )
        actual[2].literal.shouldBe("foo")
    }
}
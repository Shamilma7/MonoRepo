package com.russiantochechen

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SentenceCheckerTest {

    @Nested
    inner class containsOnlyTwoWords {
        @Test
        fun `with two words should return true`() {
            val sentence = "word1 word2"
            val sentenceChecker = SentenceChecker(sentence = sentence)
            assert(sentenceChecker.isOnlyTwoWords())
        }

        @Test
        fun `with three words should return false`() {
            val sentence = "word1 word2 word3"
            val sentenceChecker = SentenceChecker(sentence = sentence)
            assert(!sentenceChecker.isOnlyTwoWords())
        }

        @Test
        fun `with two words followed by space should return true`() {
            val sentence = "word1 word2 "
            val sentenceChecker = SentenceChecker(sentence = sentence)
            assert(sentenceChecker.isOnlyTwoWords())
        }

        @Test
        fun `with one word should return false`() {
            val sentence = "word1 "
            val sentenceChecker = SentenceChecker(sentence = sentence)
            assert(!sentenceChecker.isOnlyTwoWords())
        }

        @Test
        fun `with one word suffix should return false`() {
            val sentence = " word1"
            val sentenceChecker = SentenceChecker(sentence = sentence)
            assert(!sentenceChecker.isOnlyTwoWords())
        }
    }

    @Nested
    inner class isFirstWordFollowedBySquareBrackets {
        @Test
        fun `first word followed by square brackets should return true`() {
            val sentence = "word1 [a]"
            val sentenceChecker = SentenceChecker(sentence = sentence)
            assert(sentenceChecker.isFirstWordFollowedBySquareBrackets())
        }

        @Test
        fun `first word followed by just square brackets should return true`() {
            val sentence = "word1 []"
            val sentenceChecker = SentenceChecker(sentence = sentence)
            assert(sentenceChecker.isFirstWordFollowedBySquareBrackets())
        }

        @Test
        fun `first word followed by square brackets and words should return true`() {
            val sentence = "word1 [a, b, c, d] word3 word4"
            val sentenceChecker = SentenceChecker(sentence = sentence)
            assert(sentenceChecker.isFirstWordFollowedBySquareBrackets())
        }

        @Test
        fun `first word followed by single square bracket should return false`() {
            val sentence = "word1 ["
            val sentenceChecker = SentenceChecker(sentence = sentence)
            assert(!sentenceChecker.isFirstWordFollowedBySquareBrackets())
        }

        @Test
        fun `square brackets followed by second word should return false`() {
            val sentence = "[a] word"
            val sentenceChecker = SentenceChecker(sentence = sentence)
            assert(!sentenceChecker.isFirstWordFollowedBySquareBrackets())
        }
    }

    @Nested
    inner class isFirstWordFollowedByComma {
        @Test
        fun `first word followed by comma should return true`() {
            val sentence = "word1, word2"
            val sentenceChecker = SentenceChecker(sentence = sentence)
            assert(sentenceChecker.isFirstWordFollowedByComma())
        }

        @Test
        fun `just first word followed by comma should return true`() {
            val sentence = "word1,"
            val sentenceChecker = SentenceChecker(sentence = sentence)
            assert(sentenceChecker.isFirstWordFollowedByComma())
        }

        @Test
        fun `first word not followed by comma should return false`() {
            val sentence = "word1 word2"
            val sentenceChecker = SentenceChecker(sentence = sentence)
            assert(!sentenceChecker.isFirstWordFollowedByComma())
        }
    }

    @Nested
    inner class isSuffixDot {
        @Test
        fun `suffix dot should return true`() {
            val word = "word1."
            assert(SentenceChecker.isSuffixDot(word))
        }

        @Test
        fun `prefix dot should return false`() {
            val word = ".word1"
            assert(!SentenceChecker.isSuffixDot(word))
        }

        @Test
        fun `suffix dot with space should return false`() {
            val word = "word1 ."
            assert(!SentenceChecker.isSuffixDot(word))
        }
    }

    @Nested
    inner class isSuffixSemicolon {
        @Test
        fun `suffix semicolon should return true`() {
            val word = "word1;"
            assert(SentenceChecker.isSuffixSemicolon(word))
        }

        @Test
        fun `prefix semicolon should return false`() {
            val word = ";word1"
            assert(!SentenceChecker.isSuffixSemicolon(word))
        }

        @Test
        fun `suffix semicolon with space should return false`() {
            val word = "word1 ;"
            assert(!SentenceChecker.isSuffixSemicolon(word))
        }
    }
}
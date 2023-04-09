package com.russiantochechen.checker

import com.russiantochechen.domain.Paradigm
import com.russiantochechen.domain.Paradigm.*
import com.russiantochechen.domain.Word
import com.russiantochechen.extensions.hasParadigmEndings
import com.russiantochechen.extensions.replaceLast

/**
 * if (isSingularNom(noun.value)) {
 *                 val possibleForms = NOM.endings
 *                     .flatMap { boyningsform ->
 *                         boyningsform.getPossibleEndings()
 *                             .map {
 *                                 Word(value = "${noun}${it}", source = noun.source, paradigm = NOM)
 *                             }
 *                     }.toMutableList()
 *                 possibleForms.add(
 *                     Word(value = "${noun}", source = noun.source, paradigm = NOM)
 *                 )
 *                 return possibleForms
 *             }
 *             val possibleForms = NOM.endings
 *                 .flatMap { boyningsform ->
 *                     boyningsform.getPossibleEndings()
 *                         .map {
 *                             Word(value = "${noun.value.removeLastChar()}${it}", source = noun.source, paradigm = NOM)
 *                         }
 *                 }.toMutableList()
 *             possibleForms.add(
 *                 Word(value = "${noun.value.removeLastChar()}", source = noun.source, paradigm = NOM)
 *             )
 */
class WordTypeGuesser {
    companion object {
        fun getPossibleNomSingularForms(noun: Word): Set<Word> {
            val possibleForms = mutableSetOf<Word>()
            val nounEnding = guessEnding(noun)


            NOM.endings.flatMap { nom ->
                nom.getPossibleEndings().map {
                    possibleForms.add(
                        noun.copy(value = noun.value.replaceLast(nounEnding, it), paradigm = NOM)
                    )
                }
            }


            possibleForms.add(
                noun.copy(value = noun.value.replaceLast(nounEnding, ""), paradigm = NOM)
            )


            return possibleForms
        }

        fun getPossibleNomSingularFormsWithAllPossibilites(noun: Word): Set<Word> {
            val possibleForms = mutableSetOf<Word>()
            val nounEndings = guessEndingsWithAllPossibilities(noun)

            nounEndings.forEach { nounEnding ->
                NOM_LOOSE.endings.flatMap { nom ->
                    nom.getPossibleEndings().map {
                        possibleForms.add(
                            noun.copy(value = noun.value.replaceLast(nounEnding!!, it), paradigm = NOM_LOOSE)
                        )
                    }
                }
            }
            nounEndings.forEach { nounEnding ->
                possibleForms.add(
                    noun.copy(value = noun.value.replaceLast(nounEnding!!, ""), paradigm = NOM_LOOSE)
                )
            }

            return possibleForms
        }


        private fun guessEndingsWithAllPossibilities(noun: Word): List<String?> {
            val nounEnding = NOM_LOOSE.getPossibleEndingsForWord(noun)
            return nounEnding.map { validateNounEnding(noun, it) }
        }

        private fun guessEnding(noun: Word): String {
            var nounEnding = noun.paradigm.getEndingForWord(noun)
            nounEnding = validateNounEnding(noun, nounEnding)

            return nounEnding
        }

        private fun validateNounEnding(noun: Word, nounEnding: String?): String {
            var nounEnding1 = nounEnding
            if (noun.isBlank() || noun.paradigm == UNSPECIFIED || nounEnding1.isNullOrEmpty()) {
                nounEnding1 = NOM_LOOSE.getEndingForWord(noun)
            }

            if (nounEnding1 == null) {
                if (noun.value.endsWith("еб")) {
                    nounEnding1 = ""
                }
                if (noun.value.endsWith("ег")) {
                    nounEnding1 = ""
                }
                if (noun.value.endsWith("ес")) {
                    nounEnding1 = ""
                }
            }

            if (nounEnding1 == null) {
                return ""//throw IllegalArgumentException(noun.value)
            }
            return nounEnding1
        }

        fun guessParadigm(plainWord: String): List<Paradigm> {
            val paradigms = mutableListOf<Paradigm>()
            if (plainWord.hasParadigmEndings(GEN_STRICT)) paradigms.add(GEN_STRICT)
            if (plainWord.hasParadigmEndings(DAT)) paradigms.add(DAT)
            if (plainWord.hasParadigmEndings(ERG)) paradigms.add(ERG)
            if (plainWord.hasParadigmEndings(PLU)) paradigms.add(PLU)
            if (plainWord.hasParadigmEndings(NOM)) paradigms.add(NOM)
            else if (plainWord.hasParadigmEndings(NOM_LOOSE)) paradigms.add(NOM_LOOSE)
            if (plainWord.hasParadigmEndings(ALL)) paradigms.add(ALL)
            return paradigms
        }
    }
}
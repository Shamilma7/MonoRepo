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
        fun getPossibleNomSingularForms(noun: Word): List<Word> {
            val possibleForms = mutableListOf<Word>()
            var nounEnding = noun.paradigm.getEndingForWord(noun)

            if (noun.isBlank() || noun.paradigm == UNSPECIFIED || nounEnding.isNullOrEmpty()) {
                nounEnding = NOM.getEndingForWord(noun)
            }

            if (nounEnding == null) {
                if (noun.value.endsWith("еб")) {
                    nounEnding = ""
                }
                if (noun.value.endsWith("ег")) {
                    nounEnding = ""
                }
                if (noun.value.endsWith("ес")) {
                    nounEnding = ""
                }
            }

            if (nounEnding == null) {
                throw IllegalArgumentException(noun.value)
            }

            NOM.endings.flatMap { nom ->
                nom.getPossibleEndings().map {
                    possibleForms.add(
                        noun.copy(value = noun.value.replaceLast(nounEnding ?: "", it), paradigm = NOM)
                    )
                }
            }

            possibleForms.add(
                noun.copy(value = noun.value.replaceLast(nounEnding ?: "", ""), paradigm = NOM)
            )

            return possibleForms
        }

        fun guessParadigm(plainWord: String): List<Paradigm> {
            val paradigms = mutableListOf<Paradigm>()
            if (plainWord.hasParadigmEndings(GEN)) paradigms.add(GEN)
            if (plainWord.hasParadigmEndings(DAT)) paradigms.add(DAT)
            if (plainWord.hasParadigmEndings(ERG)) paradigms.add(ERG)
            if (plainWord.hasParadigmEndings(PLU)) paradigms.add(PLU)
            if (plainWord.hasParadigmEndings(NOM)) paradigms.add(NOM)
            if (plainWord.hasParadigmEndings(ALL)) paradigms.add(ALL)
            return paradigms
        }
    }
}
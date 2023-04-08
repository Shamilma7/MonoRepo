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
            val nounEnding = noun.paradigm.getEndingForWord(noun)

            if (noun.isBlank()) return emptyList()
            if (noun.paradigm == UNSPECIFIED) return emptyList()

            if (nounEnding.isNullOrEmpty()) {
                if (noun.paradigm != NOM) {
                    throw RuntimeException(
                        "Could not find paradigm ending for given word $noun"
                    )
                }
            }

            NOM.endings.flatMap { nom ->
                nom.getPossibleEndings().map {
                    possibleForms.add(
                        Word(value = noun.value.replaceLast(nounEnding ?: "", it), source = noun.source, paradigm = NOM)
                    )
                }
            }

            possibleForms.add(
                Word(value = noun.value.replaceLast(nounEnding ?: "", ""), source = noun.source, paradigm = NOM)
            )

            return possibleForms
        }

        fun guessParadigm(word: String): List<Paradigm> {
            val paradigms = mutableListOf<Paradigm>()
            if (word.hasParadigmEndings(GEN)) paradigms.add(GEN)
            if (word.hasParadigmEndings(DAT)) paradigms.add(DAT)
            if (word.hasParadigmEndings(ERG)) paradigms.add(ERG)
            if (word.hasParadigmEndings(PLU)) paradigms.add(PLU)
            if (word.hasParadigmEndings(NOM)) paradigms.add(NOM)
            if (word.hasParadigmEndings(ALL)) paradigms.add(ALL)
            return paradigms
        }
    }
}
package com.russiantochechen.domain

import com.russiantochechen.domain.boyningsform.Boyningsform
import com.russiantochechen.domain.boyningsform.Entall
import com.russiantochechen.domain.boyningsform.Flertall

/**
 * <trait name="paradigm" value="All"> - Dette indikerer at dette er paradigmet for alle formene av ordet. Eksempler på ordformer i dette paradigmet kan omfatte:
 * работу (rabotu) - akkusativ entall
 * работой (rabotoy) - instrumental entall
 * работами (rabotami) - instrumental flertall
 * <trait name="paradigm" value="Erg"> - Dette kan indikere at dette er ergativt paradigme, som brukes i noen språk for å indikere at subjektet i setningen er det aktive eller agerende elementet. Her er et eksempel på en ordform i dette paradigmet:
 * работой (rabotoy) - ergativ entall
 * <trait name="paradigm" value="Plu"> - Dette kan indikere at dette er paradigmet for flertallsformer av ordet. Her er et eksempel på en ordform i dette paradigmet:
 * работы (raboty) - nominativ flertall
 * <trait name="paradigm" value="Dat"> - Dette kan indikere at dette er paradigmet for dativ-kasusformer av ordet. Her er et eksempel på en ordform i dette paradigmet:
 * работе (rabote) - dativ entall
 * <trait name="paradigm" value="Gen"> - Dette kan indikere at dette er paradigmet for genitiv-kasusformer av ordet. Her er et eksempel på en ordform i dette paradigmet:
 * работы (raboty) - genitiv entall
 */
enum class Paradigm(val value: String, val endings: List<Boyningsform>, val description: String, val example: String) {
    UNSPECIFIED("", emptyList(), "", ""),
    ALL("All", listOf(Entall(listOf("у")), Flertall(listOf("ами"))), "Paradigme for alle former av ordet, med unntak av de som er tatt", "работа"),
    ERG("Erg", listOf(Entall(listOf("ой"))), "Ergativt paradigme, subjektet i setningen er det aktive eller agerende elementet", "работой (ergativ entall)"),
    PLU("Plu", listOf(Flertall(listOf("ы"))), "Plural paradigm, flertallsformer av ordet", "работы (flertall)"),
    DAT("Dat", listOf(Entall(listOf("е")), Flertall(listOf("ам"))), "Dativt paradigme (mottaker av handlingen)", "работе (entall)"),
    GEN("Gen", listOf(Entall(listOf("ы")), Flertall(listOf("от"))), "Genitive paradigme, genitiv-kasusformer", "работы (entall)"),
    NOM(
        "Nom", listOf(Entall(listOf("а", "я")), Flertall(listOf("ы"))), "Nomative paradigme",
        "работа (entall) | работы (flertall)"
    );

    fun hasEndings(word: String) = this.endings.any { boyningsform ->
        boyningsform.getPossibleEndings()
            .any { word.endsWith(it, ignoreCase = true) }
    }

    fun getEndingForWord(word: Word): String? {
        val matchingBoyningsform = this.endings.find { boyningsform ->
            boyningsform.getPossibleEndings().any { word.value.endsWith(it, ignoreCase = true) }
        }
        return matchingBoyningsform?.getPossibleEndings()?.find { word.value.endsWith(it, ignoreCase = true) }
    }
}
package com.russiantochechen.domain

import com.russiantochechen.domain.boyningsform.Boyningsform
import com.russiantochechen.domain.boyningsform.Entall
import com.russiantochechen.domain.boyningsform.Flertall

enum class Paradigm(val value: String, val endings: List<Boyningsform>, val description: String, val example: String) {
    ALL("All", listOf(Entall(listOf("а"))), "Paradigme for alle former av ordet", "работа"), ERG(
        "Erg", listOf(Entall(listOf("й"))),
        "Ergativt paradigme, subjektet i setningen er det aktive eller agerende elementet",
        "работой (ergativ entall)"
    ),
    PLU("Plu", listOf(Flertall(listOf("ы"))), "Plural paradigm, flertallsformer av ordet", "работы (flertall)"),
    DAT("Dat", listOf(Entall(listOf("е"))), "Dativt paradigme (mottaker av handlingen)", "работе (entall)"),
    GEN("Gen", listOf(Entall(listOf("ы"))), "Genitive paradigme, genitiv-kasusformer", "работы (entall)"),
    NOM(
        "Nom", listOf(Entall(listOf("а")), Flertall(listOf("ы"))), "Nomative paradigme",
        "работа (entall) | работы (flertall)"
    );
}
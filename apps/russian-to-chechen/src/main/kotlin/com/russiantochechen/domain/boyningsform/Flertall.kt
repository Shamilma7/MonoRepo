package com.russiantochechen.domain.boyningsform

data class Flertall(val endings: List<String>) : Boyningsform {
    override fun getBoyningsform(): BoyningsformEnum = BoyningsformEnum.FLERTALL
    override fun getPossibleEndings(): List<String> = endings
}
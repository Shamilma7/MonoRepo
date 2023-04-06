package com.russiantochechen.domain.boyningsform

data class Entall(val possibleEndings: List<String>) : Boyningsform {
    override fun getBoyningsform(): BoyningsformEnum = BoyningsformEnum.ENTALL
    override fun getPossibleEndings(): List<String> = possibleEndings
}
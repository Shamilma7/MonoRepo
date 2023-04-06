package com.russiantochechen.domain.boyningsform

data class Entall(val endings: List<String>) : Boyningsform {
    override fun getBoyningsform(): BoyningsformEnum = BoyningsformEnum.ENTALL
    override fun getPossibleEndings(): List<String> = endings
}
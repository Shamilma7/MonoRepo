package com.russiantochechen.domain.boyningsform

interface Boyningsform {
    fun getBoyningsform(): BoyningsformEnum
    fun getPossibleEndings(): List<String>
}
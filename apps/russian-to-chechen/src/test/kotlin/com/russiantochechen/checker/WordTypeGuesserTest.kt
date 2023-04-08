package com.russiantochechen.checker

import com.russiantochechen.domain.Paradigm
import com.russiantochechen.domain.Source
import com.russiantochechen.domain.Word
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * работа (rabota) - nominativ entall
 * работы (raboty) - genitiv entall
 * работе (rabote) - dativ entall
 * работу (rabotu) - akkusativ entall
 * работой (rabotoy) - instrumental entall
 * работы (raboty) - nominativ flertall
 * работ (rabot) - genitiv flertall
 * работам (rabotam) - dativ flertall
 * работы (raboty) - akkusativ flertall
 * работами (rabotami) - instrumental flertall
 *
 * <trait name="paradigm" value="Erg"> - Dette kan indikere at dette er ergativt paradigme, som brukes i noen språk for å indikere at subjektet i setningen er det aktive eller agerende elementet. Her er et eksempel på en ordform i dette paradigmet:
 * работой (rabotoy) - ergativ entall
 * <trait name="paradigm" value="Plu"> - Dette kan indikere at dette er paradigmet for flertallsformer av ordet. Her er et eksempel på en ordform i dette paradigmet:
 * работы (raboty) - nominativ flertall
 * <trait name="paradigm" value="Dat"> - Dette kan indikere at dette er paradigmet for dativ-kasusformer av ordet. Her er et eksempel på en ordform i dette paradigmet:
 * работе (rabote) - dativ entall
 * <trait name="paradigm" value="Gen"> - Dette kan indikere at dette er paradigmet for genitiv-kasusformer av ordet. Her er et eksempel på en ordform i dette paradigmet:
 * работы (raboty) - genitiv entall
 */
class WordTypeGuesserTest {

    @Test
    fun `should give paradigm Gen`() {
        val job = "работы"
        Assertions.assertThat(WordTypeGuesser.guessParadigm(job)).contains(Paradigm.GEN)
    }

    @Test
    fun `should give paradigm Dat`() {
        val job = "работе"
        Assertions.assertThat(WordTypeGuesser.guessParadigm(job)).contains(Paradigm.DAT)
    }

    @Test
    fun `should give paradigm Erg`() {
        val job = "работой"
        Assertions.assertThat(WordTypeGuesser.guessParadigm(job)).contains(Paradigm.ERG)
    }

    @Test
    fun `should give paradigm Plu`() {
        val job = "работы"
        Assertions.assertThat(WordTypeGuesser.guessParadigm(job)).contains(Paradigm.PLU)
    }

    @Test
    fun `should give paradigm All`() {
        val job = "работами"
        Assertions.assertThat(WordTypeGuesser.guessParadigm(job)).contains(Paradigm.ALL)
    }

    @Test
    fun `should give paradigm Nom`() {
        val job = "работа"
        Assertions.assertThat(WordTypeGuesser.guessParadigm(job)).contains(Paradigm.NOM)
    }

    @Test
    fun `should give paradigm Nom from flertall`() {
        val job = "работы"
        Assertions.assertThat(WordTypeGuesser.guessParadigm(job)).contains(Paradigm.NOM)
    }

    @Test
    fun `should give paradigm empty`() {
        val job = "работx"
        Assertions.assertThat(WordTypeGuesser.guessParadigm(job)).isEmpty()
    }

    @Test
    fun `should get possible nom singular forms from already singular NOM`() {
        val job = Word.from(plainWord = "работа", source = Source.ORIGINAL, Paradigm.NOM)
        Assertions.assertThat(WordTypeGuesser.getPossibleNomSingularForms(job)).containsExactlyInAnyOrder(
            Word.from(plainWord = "работа", source = Source.ORIGINAL, paradigm = Paradigm.NOM),
            Word.from(plainWord = "работя", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работовь", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работлю", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работы", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работ", source = Source.ORIGINAL, Paradigm.NOM),
        )
    }

    @Test
    fun `should get possible nom singular forms from already empty singular NOM`() {
        val job = Word.from(plainWord = "работ", source = Source.ORIGINAL, Paradigm.NOM)
        Assertions.assertThat(WordTypeGuesser.getPossibleNomSingularForms(job)).containsExactlyInAnyOrder(
            Word.from(plainWord = "работа", source = Source.ORIGINAL, paradigm = Paradigm.NOM),
            Word.from(plainWord = "работя", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работовь", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работлю", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работы", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работ", source = Source.ORIGINAL, Paradigm.NOM),
        )
    }

    @Test
    fun `should get possible nom singular forms from GEN`() {
        val job = Word.from(plainWord = "работы", source = Source.ORIGINAL, Paradigm.GEN)
        Assertions.assertThat(WordTypeGuesser.getPossibleNomSingularForms(job)).containsExactlyInAnyOrder(
            Word.from(plainWord = "работа", source = Source.ORIGINAL, paradigm = Paradigm.NOM),
            Word.from(plainWord = "работя", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работовь", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работлю", source = Source.ORIGINAL, Paradigm.NOM),

            Word.from(plainWord = "работы", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работ", source = Source.ORIGINAL, Paradigm.NOM),
        )
    }

    @Test
    fun `should get possible nom singular forms from DAT`() {
        val job = Word.from(plainWord = "работе", source = Source.ORIGINAL, Paradigm.DAT)
        Assertions.assertThat(WordTypeGuesser.getPossibleNomSingularForms(job)).containsExactlyInAnyOrder(
            Word.from(plainWord = "работа", source = Source.ORIGINAL, paradigm = Paradigm.NOM),
            Word.from(plainWord = "работя", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работовь", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работлю", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работы", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работ", source = Source.ORIGINAL, Paradigm.NOM),
        )
    }

    @Test
    fun `should get possible nom singular forms from ERG`() {
        val job = Word.from(plainWord = "работой", source = Source.ORIGINAL, Paradigm.ERG)
        Assertions.assertThat(WordTypeGuesser.getPossibleNomSingularForms(job)).containsExactlyInAnyOrder(
            Word.from(plainWord = "работа", source = Source.ORIGINAL, paradigm = Paradigm.NOM),
            Word.from(plainWord = "работя", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работовь", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работлю", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работы", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работ", source = Source.ORIGINAL, Paradigm.NOM),
        )
    }

    @Test
    fun `should get possible nom singular forms from PLU`() {
        val job = Word.from(plainWord = "работы", source = Source.ORIGINAL, Paradigm.PLU)
        Assertions.assertThat(WordTypeGuesser.getPossibleNomSingularForms(job)).containsExactlyInAnyOrder(
            Word.from(plainWord = "работа", source = Source.ORIGINAL, paradigm = Paradigm.NOM),
            Word.from(plainWord = "работя", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работовь", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работлю", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работы", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работ", source = Source.ORIGINAL, Paradigm.NOM),
        )
    }

    @Test
    fun `should get possible nom singular forms from ALL`() {
        val job = Word.from(plainWord = "работами", source = Source.ORIGINAL, Paradigm.ALL)
        Assertions.assertThat(WordTypeGuesser.getPossibleNomSingularForms(job)).containsExactlyInAnyOrder(
            Word.from(plainWord = "работа", source = Source.ORIGINAL, paradigm = Paradigm.NOM),
            Word.from(plainWord = "работя", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работовь", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работлю", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работы", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работ", source = Source.ORIGINAL, Paradigm.NOM),
        )
    }

    @Test
    fun `should get possible nom singular forms from NOM`() {
        val job = Word.from(plainWord = "работа", source = Source.ORIGINAL, paradigm = Paradigm.NOM)
        Assertions.assertThat(WordTypeGuesser.getPossibleNomSingularForms(job)).containsExactlyInAnyOrder(
            Word.from(plainWord = "работа", source = Source.ORIGINAL, paradigm = Paradigm.NOM),
            Word.from(plainWord = "работя", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работовь", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работлю", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работы", source = Source.ORIGINAL, Paradigm.NOM),
            Word.from(plainWord = "работ", source = Source.ORIGINAL, Paradigm.NOM),
        )
    }

    // todo finish test
    @Test
    fun `should get possible nom singular forms from люблю`() {
        val job = Word.from(plainWord = "люблю", source = Source.ORIGINAL, paradigm = Paradigm.NOM)
    }
}
package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Pruebas de comportamiento para validar los requerimientos de Gilded Rose.
 */
class GildedRoseTest {
    private static final String CONJURED_MANA_CAKE = ItemNames.CONJURED_PREFIX + " Mana Cake";

    /**
     * Verifica que un ítem normal reduzca sellIn y quality en 1 antes de vencer.
     */
    @Test
    void normalItem_beforeSellDate_decreasesSellInAndQualityByOne() {
        Item[] items = new Item[] { new NormalItem("+5 Dexterity Vest", 10, 20) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemState(items[0], "+5 Dexterity Vest", 9, 19);
    }

    /**
     * Verifica que un ítem normal degradado reduzca quality en 2 al estar vencido.
     */
    @Test
    void normalItem_afterSellDate_decreasesQualityByTwo() {
        Item[] items = new Item[] { new NormalItem("Elixir of the Mongoose", 0, 7) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemState(items[0], "Elixir of the Mongoose", -1, 5);
    }

    /**
     * Verifica que la calidad de un ítem normal nunca sea negativa.
     */
    @Test
    void normalItem_qualityNeverNegative() {
        Item[] items = new Item[] { new NormalItem("foo", 5, 0) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemState(items[0], "foo", 4, 0);
    }

    /**
     * Verifica que "Aged Brie" aumente su calidad en 1 antes de vencer.
     */
    @Test
    void agedBrie_beforeSellDate_increasesQualityByOne() {
        Item[] items = new Item[] { new AgedBrie(2, 0) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemState(items[0], ItemNames.AGED_BRIE, 1, 1);
    }

    /**
     * Verifica que "Aged Brie" aumente su calidad en 2 después de vencer.
     */
    @Test
    void agedBrie_afterSellDate_increasesQualityByTwo() {
        Item[] items = new Item[] { new AgedBrie(0, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemState(items[0], ItemNames.AGED_BRIE, -1, 12);
    }

    /**
     * Verifica que la calidad de "Aged Brie" no supere 50.
     */
    @Test
    void agedBrie_qualityCappedAtFifty() {
        Item[] items = new Item[] { new AgedBrie(2, 50) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemState(items[0], ItemNames.AGED_BRIE, 1, 50);
    }

    /**
     * Verifica que "Sulfuras" no cambie ni en sellIn ni en quality.
     */
    @Test
    void sulfuras_neverChanges() {
        Item[] items = new Item[] { new Sulfuras(0) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemState(items[0], ItemNames.SULFURAS, 0, 80);
    }

    /**
     * Verifica que "Sulfuras" mantenga calidad legendaria fija en 80.
     */
    @Test
    void sulfuras_qualityAlwaysEighty() {
        Item[] items = new Item[] { new Sulfuras(5) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemState(items[0], ItemNames.SULFURAS, 5, 80);
    }

    /**
     * Verifica que un "Backstage pass" con más de 10 días aumente calidad en 1.
     */
    @Test
    void backstagePass_moreThanTenDays_increasesQualityByOne() {
        Item[] items = new Item[] { new BackstagePass(15, 20) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemState(items[0], ItemNames.BACKSTAGE_PASSES, 14, 21);
    }

    /**
     * Verifica que un "Backstage pass" con 10 días o menos aumente calidad en 2.
     */
    @Test
    void backstagePass_tenDaysOrLess_increasesQualityByTwo() {
        Item[] items = new Item[] { new BackstagePass(10, 20) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemState(items[0], ItemNames.BACKSTAGE_PASSES, 9, 22);
    }

    /**
     * Verifica que un "Backstage pass" con 5 días o menos aumente calidad en 3.
     */
    @Test
    void backstagePass_fiveDaysOrLess_increasesQualityByThree() {
        Item[] items = new Item[] { new BackstagePass(5, 20) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemState(items[0], ItemNames.BACKSTAGE_PASSES, 4, 23);
    }

    /**
     * Verifica que un "Backstage pass" quede en calidad 0 después del concierto.
     */
    @Test
    void backstagePass_afterConcert_qualityDropsToZero() {
        Item[] items = new Item[] { new BackstagePass(0, 20) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemState(items[0], ItemNames.BACKSTAGE_PASSES, -1, 0);
    }

    /**
     * Verifica que la calidad de "Backstage passes" no supere 50.
     */
    @Test
    void backstagePass_qualityCappedAtFifty() {
        Item[] items = new Item[] { new BackstagePass(5, 49) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemState(items[0], ItemNames.BACKSTAGE_PASSES, 4, 50);
    }

    /**
     * Verifica que un ítem "Conjured" degrade el doble que un ítem normal.
     */
    @Test
    void conjuredItem_beforeSellDate_degradesTwiceAsFastAsNormalItem() {
        Item[] items = new Item[] { new ConjuredItem(CONJURED_MANA_CAKE, 3, 6) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemState(items[0], CONJURED_MANA_CAKE, 2, 4);
    }

    /**
     * Verifica que un "Conjured" vencido degrade nuevamente al doble.
     */
    @Test
    void conjuredItem_afterSellDate_degradesTwiceAsFastAgain() {
        Item[] items = new Item[] { new ConjuredItem(CONJURED_MANA_CAKE, 0, 6) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemState(items[0], CONJURED_MANA_CAKE, -1, 2);
    }

    /**
     * Verifica que la calidad de "Conjured" nunca sea negativa.
     */
    @Test
    void conjuredItem_qualityNeverNegative() {
        Item[] items = new Item[] { new ConjuredItem(CONJURED_MANA_CAKE, 0, 3) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemState(items[0], CONJURED_MANA_CAKE, -1, 0);
    }

    /**
     * Valida el estado completo de un ítem en una sola aserción compuesta.
     *
     * @param item ítem actual
     * @param expectedName nombre esperado
     * @param expectedSellIn sellIn esperado
     * @param expectedQuality calidad esperada
     */
    private void assertItemState(Item item, String expectedName, int expectedSellIn, int expectedQuality) {
        assertEquals(expectedName, item.getName());
        assertEquals(expectedSellIn, item.getSellIn());
        assertEquals(expectedQuality, item.getQuality());
    }

}
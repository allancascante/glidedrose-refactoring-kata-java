package com.gildedrose;

public class AgedBrie extends Item {
    public AgedBrie(int sellIn, int quality) {
        super(ItemNames.AGED_BRIE, sellIn, quality);
    }

    @Override
    public void update() {
        increaseQuality();
        decreaseSellIn();
        if (getSellIn() < 0) {
            increaseQuality();
        }
    }
}
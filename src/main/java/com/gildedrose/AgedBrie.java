package com.gildedrose;

public class AgedBrie extends Item {
    public static final String NAME = "Aged Brie";

    public AgedBrie(int sellIn, int quality) {
        super(NAME, sellIn, quality);
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

package com.gildedrose;

public class BackstagePass extends Item {
    public static final String NAME = "Backstage passes to a TAFKAL80ETC concert";

    public BackstagePass(int sellIn, int quality) {
        super(NAME, sellIn, quality);
    }

    @Override
    public void update() {
        increaseQuality();
        if (getSellIn() < 11) increaseQuality();
        if (getSellIn() < 6)  increaseQuality();
        decreaseSellIn();
        if (getSellIn() < 0)  resetQuality();
    }
}

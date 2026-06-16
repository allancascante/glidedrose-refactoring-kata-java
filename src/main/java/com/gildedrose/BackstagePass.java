package com.gildedrose;

public class BackstagePass extends Item {
    public BackstagePass(int sellIn, int quality) {
        super(ItemNames.BACKSTAGE_PASSES, sellIn, quality);
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
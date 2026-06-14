package com.gildedrose;

public class BackstagePass extends Item {
    public BackstagePass(int sellIn, int quality) {
        super(ItemNames.BACKSTAGE_PASSES, sellIn, quality);
    }

    @Override
    public void update() {
        increaseQuality();
        if (this.sellIn < 11) increaseQuality();
        if (this.sellIn < 6)  increaseQuality();
        decreaseSellIn();
        if (this.sellIn < 0)  resetQuality();
    }
}
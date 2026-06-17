package com.gildedrose;

public class ConjuredItem extends Item {
    public static final String PREFIX = "Conjured";

    private static final int DEGRADATION = 2;

    public ConjuredItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void update() {
        decreaseQualityBy(DEGRADATION);
        decreaseSellIn();
        if (getSellIn() < 0) {
            decreaseQualityBy(DEGRADATION);
        }
    }
}

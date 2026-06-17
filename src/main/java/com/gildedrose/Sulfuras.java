package com.gildedrose;

public class Sulfuras extends Item {
    public static final String NAME = "Sulfuras, Hand of Ragnaros";

    public Sulfuras(int sellIn) {
        super(NAME, sellIn, 80);
    }

    @Override
    public void update() {
        setQuality(80);
    }
}

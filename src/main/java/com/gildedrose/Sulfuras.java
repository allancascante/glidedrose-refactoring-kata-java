package com.gildedrose;

public class Sulfuras extends Item {
    public Sulfuras(int sellIn) {
        super(ItemNames.SULFURAS, sellIn, 80);
    }

    @Override
    public void update() {
        setQuality(80);
    }
}
package com.gildedrose;

import lombok.Getter;
import lombok.Setter;

/**
 * Representa un ítem del inventario de Gilded Rose.
 */
@Getter
@Setter
public abstract class Item {

    public static final int MIN_QUALITY = 0;

    private String name;

    private int sellIn;

    private int quality;

    /**
     * Construye un ítem con su nombre, días para vender y calidad.
     *
     * @param name nombre del ítem
     * @param sellIn días restantes para venderlo
     * @param quality valor de calidad del ítem
     */
    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    //Actualiza el sellIn y el quality dependiendo de las reglas del item
    public abstract void update();

    /**
     * Devuelve la representación en texto del ítem.
     *
     * @return texto con nombre, sellIn y quality
     */
    @Override
    public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }
    protected void decreaseSellIn(){
        this.sellIn = this.sellIn - 1;
    }
    protected void increaseQuality(){
        if (this.quality < 50){
            this.quality = this.quality + 1;
        }
    }
    protected void decreaseQuality(){
        decreaseQualityBy(1);
    }
    protected void decreaseQualityBy(int amount){
        this.quality = Math.max(MIN_QUALITY, this.quality - amount);
    }
    protected void resetQuality(){
        this.quality = MIN_QUALITY;
    }
}
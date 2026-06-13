package com.gildedrose;

import lombok.Getter;
import lombok.Setter;

/**
 * Representa un ítem del inventario de Gilded Rose.
 */
@Getter
@Setter
public class Item {

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

    /**
     * Devuelve la representación en texto del ítem.
     *
     * @return texto con nombre, sellIn y quality
     */
    @Override
    public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }
}

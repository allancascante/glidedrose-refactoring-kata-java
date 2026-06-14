package com.gildedrose;


class GildedRose {
    Item[] items;
    
    /**
     * Crea una instancia de la aplicación con el inventario dado.
     *
     * @param items inventario de ítems a procesar diariamente
     */
    public GildedRose(Item[] items) {
        this.items = items;
    }

    //Update Quality se simplifica por la nueva jerarquía aplicada
    public void updateQuality() {
    for (Item item : items) {
        item.update();
    }
}
}

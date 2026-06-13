package com.gildedrose;

/**
 * Orquesta la actualización diaria de calidad y fecha de venta para el inventario.
 */
class GildedRose {
    Item[] items;
    private static final ItemUpdaterRegistry UPDATER_REGISTRY = new ItemUpdaterRegistry();

    /**
     * Crea una instancia de la aplicación con el inventario dado.
     *
     * @param items inventario de ítems a procesar diariamente
     */
    public GildedRose(Item[] items) {
        this.items = items;
    }

    /**
     * Ejecuta la actualización diaria para todos los ítems.
     */
    public void updateQuality() {
        for (Item item : items) {
            UPDATER_REGISTRY.resolve(item).update(item);
        }
    }
}

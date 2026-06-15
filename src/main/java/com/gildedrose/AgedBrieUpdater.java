package com.gildedrose;

/**
 * Aplica las reglas de actualización para "Aged Brie".
 */
class AgedBrieUpdater implements ItemUpdater {

    /**
     * Aplica la regla de "Aged Brie": la calidad aumenta en 1 por día y,
     * después de vencer, aumenta en 2 por día.
     *
     * @param item ítem "Aged Brie" a actualizar
     */
    @Override
    public void update(Item item) {
        UpdaterSupport.increaseQuality(item);
        UpdaterSupport.decreaseSellIn(item);

        if (item.getSellIn() < 0) {
            UpdaterSupport.increaseQuality(item);
        }
    }
}

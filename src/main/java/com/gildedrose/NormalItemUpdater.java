package com.gildedrose;

/**
 * Aplica las reglas de actualización para ítems normales.
 */
class NormalItemUpdater implements ItemUpdater {

    /**
     * Aplica la regla de un ítem normal: la calidad baja en 1 por día y,
     * una vez vencido el sell by date, baja en 2 por día.
     *
     * @param item ítem normal a actualizar
     */
    @Override
    public void update(Item item) {
        UpdaterSupport.decreaseQuality(item);
        UpdaterSupport.decreaseSellIn(item);

        if (item.sellIn < 0) {
            UpdaterSupport.decreaseQuality(item);
        }
    }
}

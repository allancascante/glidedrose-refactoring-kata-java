package com.gildedrose;

/**
 * Aplica las reglas de actualización para "Backstage passes".
 */
class BackstagePassUpdater implements ItemUpdater {

    /**
        * Aplica la regla de "Backstage passes": aumenta su calidad en 1 por día,
        * en 2 cuando faltan 10 días o menos, en 3 cuando faltan 5 días o menos,
        * y cae a 0 después del concierto.
     *
     * @param item ítem "Backstage pass" a actualizar
     */
    @Override
    public void update(Item item) {
        UpdaterSupport.increaseQuality(item);

        if (item.getSellIn() < 11) {
            UpdaterSupport.increaseQuality(item);
        }

        if (item.getSellIn() < 6) {
            UpdaterSupport.increaseQuality(item);
        }

        UpdaterSupport.decreaseSellIn(item);

        if (item.getSellIn() < 0) {
            UpdaterSupport.resetQuality(item);
        }
    }
}

package com.gildedrose;

/**
 * Aplica las reglas de actualización para ítems "Conjured".
 */
class ConjuredItemUpdater implements ItemUpdater {

    private static final int DEGRADATION = 2;

    /**
     * Aplica la regla de ítems "Conjured": degradan su calidad al doble de
     * velocidad que un ítem normal, tanto antes como después de vencidos.
     *
     * @param item ítem "Conjured" a actualizar
     */
    @Override
    public void update(Item item) {
        UpdaterSupport.decreaseQualityBy(item, DEGRADATION);
        UpdaterSupport.decreaseSellIn(item);

        if (item.getSellIn() < 0) {
            UpdaterSupport.decreaseQualityBy(item, DEGRADATION);
        }
    }
}

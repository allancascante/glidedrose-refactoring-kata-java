package com.gildedrose;

/**
 * Aplica las reglas para "Sulfuras", que no cambia con el tiempo.
 */
class SulfurasUpdater implements ItemUpdater {
    private static final int LEGENDARY_QUALITY = 80;

    /**
     * Aplica la regla de "Sulfuras": no cambia su sellIn y mantiene una
     * calidad fija de 80.
     *
     * @param item ítem legendario
     */
    @Override
    public void update(Item item) {
        item.setQuality(LEGENDARY_QUALITY);
    }
}

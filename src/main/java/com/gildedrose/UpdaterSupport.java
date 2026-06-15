package com.gildedrose;

/**
 * Utilidades comunes para aplicar invariantes y cambios de estado en ítems.
 */
final class UpdaterSupport {
    static final int MAX_QUALITY = 50;
    static final int MIN_QUALITY = 0;

    /**
     * Evita instanciación de clase utilitaria.
     */
    private UpdaterSupport() {
    }

    /**
     * Reduce en uno el valor de sellIn.
     *
     * @param item ítem a modificar
     */
    static void decreaseSellIn(Item item) {
        item.setSellIn(item.getSellIn() - 1);
    }

    /**
     * Incrementa en uno la calidad respetando el máximo permitido.
     *
     * @param item ítem a modificar
     */
    static void increaseQuality(Item item) {
        if (item.getQuality() < MAX_QUALITY) {
            item.setQuality(item.getQuality() + 1);
        }
    }

    /**
     * Disminuye en uno la calidad respetando el mínimo permitido.
     *
     * @param item ítem a modificar
     */
    static void decreaseQuality(Item item) {
        decreaseQualityBy(item, 1);
    }

    /**
     * Disminuye la calidad en la cantidad indicada respetando el mínimo permitido.
     *
     * @param item ítem a modificar
     * @param amount cantidad de decremento
     */
    static void decreaseQualityBy(Item item, int amount) {
        item.setQuality(Math.max(MIN_QUALITY, item.getQuality() - amount));
    }

    /**
     * Establece la calidad del ítem en cero.
     *
     * @param item ítem a modificar
     */
    static void resetQuality(Item item) {
        item.setQuality(MIN_QUALITY);
    }
}

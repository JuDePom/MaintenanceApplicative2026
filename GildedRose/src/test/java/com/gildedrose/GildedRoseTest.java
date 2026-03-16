package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    private GildedRose app;

    private Item updateItem(String name, int sellIn, int quality) {
        Item item = new Item(name, sellIn, quality);
        app = new GildedRose(new Item[] { item });
        app.updateQuality();
        return app.items[0];
    }

    @Test
    void fooNameStaysSame() {
        Item item = updateItem("foo", 0, 0);
        assertEquals("foo", item.name);
    }

    // --- Normal Items ---

    @Test
    void normalItem_BeforeSellDate_DecreasesQualityAndSellIn() {
        Item item = updateItem("foo", 10, 20);
        assertEquals(9, item.sellIn);
        assertEquals(19, item.quality);
    }

    @Test
    void normalItem_OnSellDate_DecreasesQualityByTwo() {
        Item item = updateItem("foo", 0, 20);
        assertEquals(-1, item.sellIn);
        assertEquals(18, item.quality);
    }

    @Test
    void normalItem_OnSellDate_QualityZero_DoesNotGoBelowZero() {
        Item item = updateItem("foo", 0, 1);
        assertEquals(-1, item.sellIn);
        assertEquals(0, item.quality);
    }

    @Test
    void normalItem_QualityNeverNegative() {
        Item item = updateItem("foo", 10, 0);
        assertEquals(0, item.quality);
    }

    // --- Aged Brie ---

    @Test
    void agedBrie_BeforeSellDate_IncreasesQuality() {
        Item item = updateItem("Aged Brie", 10, 20);
        assertEquals(9, item.sellIn);
        assertEquals(21, item.quality);
    }

    @Test
    void agedBrie_OnSellDate_IncreasesQualityByTwo() {
        Item item = updateItem("Aged Brie", 0, 20);
        assertEquals(-1, item.sellIn);
        assertEquals(22, item.quality);
    }

    @Test
    void agedBrie_QualityNeverMoreThan50() {
        Item item = updateItem("Aged Brie", 10, 50);
        assertEquals(50, item.quality);
    }

    @Test
    void agedBrie_QualityNeverMoreThan50_OnSellDate() {
        Item item = updateItem("Aged Brie", 0, 49);
        assertEquals(50, item.quality);
    }

    // --- Sulfuras, Hand of Ragnaros ---

    @Test
    void sulfuras_NeverChanges_BeforeSellDate() {
        Item item = updateItem("Sulfuras, Hand of Ragnaros", 10, 80);
        assertEquals(10, item.sellIn);
        assertEquals(80, item.quality);
    }

    @Test
    void sulfuras_NeverChanges_OnSellDate() {
        Item item = updateItem("Sulfuras, Hand of Ragnaros", -1, 80);
        assertEquals(-1, item.sellIn);
        assertEquals(80, item.quality);
    }

    // --- Backstage passes ---

    @Test
    void backstagePasses_MoreThan10Days_IncreasesQualityByOne() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 11, 20);
        assertEquals(10, item.sellIn);
        assertEquals(21, item.quality);
    }

    @Test
    void backstagePasses_10DaysOrLess_IncreasesQualityByTwo() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 10, 20);
        assertEquals(9, item.sellIn);
        assertEquals(22, item.quality);
    }

    @Test
    void backstagePasses_10DaysOrLess_QualityCapped() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 10, 49);
        assertEquals(50, item.quality);
    }

    @Test
    void backstagePasses_5DaysOrLess_IncreasesQualityByThree() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 5, 20);
        assertEquals(4, item.sellIn);
        assertEquals(23, item.quality);
    }

    @Test
    void backstagePasses_5DaysOrLess_QualityCapped() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 5, 48);
        assertEquals(50, item.quality);
    }

    @Test
    void backstagePasses_AfterConcert_QualityDropsToZero() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 0, 20);
        assertEquals(-1, item.sellIn);
        assertEquals(0, item.quality);
    }

    @Test
    void backstagePasses_QualityNeverMoreThan50() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 11, 50);
        assertEquals(50, item.quality);
    }

}

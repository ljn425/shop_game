package shop.game.enums;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTypeTest {

    @Test
    public void enumTest() {
        Map<String, String> categories = new LinkedHashMap<>();
        for (CategoryType categoryType : CategoryType.values()) {
            categories.put(categoryType.name(), categoryType.getDisplayName());
        }
        System.out.println(categories.toString());
    }

}
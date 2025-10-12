import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.Mockito;
import praktikum.Bun;
import praktikum.Ingredient;
import praktikum.IngredientType;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IngredientTest {

    Ingredient ingredient = new Ingredient(IngredientType.SAUCE, "sauce_white", 10);

    @Test
    public void ingredientGetTyoe() {
        String actual = String.valueOf(ingredient.getType());
        assertEquals("SAUCE", actual);
    }

    @Test
    public void ingredientGetName() {
        String actual = ingredient.getName();
        assertEquals("sauce_white", actual);
    }

    @Test
    public void ingredientGetPrice() {
        float actual = ingredient.getPrice();
        assertEquals(10, actual);
    }
}

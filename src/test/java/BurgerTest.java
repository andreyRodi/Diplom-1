import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.Mockito;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BurgerTest {

    @Mock
    Bun bun;

    private Burger burger;
    private Ingredient ingredient;


    @BeforeEach
    public void setUp() {
        burger = new Burger();
        burger.setBuns(bun);
    }


    @Test
    public void addIngredientBurgerTest() {

        int sizeOfIngredientsBeforeAdding = burger.ingredients.size();
        burger.addIngredient(new Ingredient(IngredientType.SAUCE, "sauce_white", 10));
        int sizeOfIngredientsAfterAdding = burger.ingredients.size();

        assertNotEquals(sizeOfIngredientsBeforeAdding, sizeOfIngredientsAfterAdding, "Список ингредиентов не изменился");
    }

    @Test
    public void removeIngredientBurgerTest() {

        burger.addIngredient(new Ingredient(IngredientType.FILLING, "cutlet", 20));
        int sizeOfIngredientsBeforeAdding = burger.ingredients.size();
        burger.removeIngredient(0);
        int sizeOfIngredientsAfterAdding = burger.ingredients.size();

        assertNotEquals(sizeOfIngredientsBeforeAdding, sizeOfIngredientsAfterAdding, "Список ингредиентов не изменился");
    }

    @CsvSource({
            "0, 1",
            "1, 0",
            "0, 2",
            "2, 0",
            "1, 1"
    })

    @ParameterizedTest
    public void moveIngredientParameterizedTest(int index, int newIndex) {

        burger.addIngredient(new Ingredient(IngredientType.SAUCE, "sauce_white", 10));
        burger.addIngredient(new Ingredient(IngredientType.FILLING, "cutlet", 20));
        burger.addIngredient(new Ingredient(IngredientType.SAUCE, "sauce_chili", 15));
        String ingredientBefore = burger.ingredients.get(index).getName();
        burger.moveIngredient(index, newIndex);
        assertEquals(ingredientBefore, burger.ingredients.get(newIndex).getName(),"Ингредиент не оказался на новом месте");
    }

    @Test
    public void moveIngredientWithWrongIndexThrowsException() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> burger.moveIngredient(5, 0),
                "Ожидалось исключение при неверном индексе");
    }

    @Test
    public void moveIngredientWithWrongNewIndexThrowsException() {
        burger.addIngredient(new Ingredient(IngredientType.SAUCE, "sauce_white", 10));
        assertThrows(IndexOutOfBoundsException.class,
                () -> burger.moveIngredient(0, 10),
                "Ожидалось исключение при неверном новом индексе");
    }



    @CsvSource({
            "20, 85",
            "0, 45",
            "-10, 25"
    })

    @ParameterizedTest
    public void getPriceBurgerParameterizedTest(float bunPrice, float expectedPrice) {
        when(bun.getPrice()).thenReturn(bunPrice);

        burger.addIngredient(new Ingredient(IngredientType.SAUCE, "sauce_white", 10));
        burger.addIngredient(new Ingredient(IngredientType.FILLING, "cutlet", 20));
        burger.addIngredient(new Ingredient(IngredientType.SAUCE, "sauce_chili", 15));

        float actual = burger.getPrice();
        assertEquals(expectedPrice, actual, 0.01, "Неверная цена бургера");
    }


    @ParameterizedTest
    @MethodSource("receiptDataProvider")
    void getReceiptParameterizedTest(float bunPrice, String bunName, List<Ingredient> ingredients, List<String> expectedParts) {
        when(bun.getName()).thenReturn(bunName);
        when(bun.getPrice()).thenReturn(bunPrice);

        ingredients.forEach(burger::addIngredient);

        String receipt = burger.getReceipt();

        for (String expected : expectedParts) {
            assertTrue(receipt.contains(expected),
                    "В чеке отсутствует ожидаемая строка: " + expected + "\nЧек:\n" + receipt);
        }
    }

    private static Stream<Arguments> receiptDataProvider() {
        return Stream.of(
                Arguments.of(
                        20F,
                        "Пшеничная",
                        List.of(new Ingredient(IngredientType.SAUCE, "sauce_white", 10)),
                        List.of("(==== Пшеничная ====",
                                "= sauce sauce_white =",
                                "Price:")
                ),
                Arguments.of(
                        15F,
                        "Ржаная",
                        List.of(
                                new Ingredient(IngredientType.SAUCE, "sauce_white", 10),
                                new Ingredient(IngredientType.FILLING, "cutlet", 20)
                        ),
                        List.of("(==== Ржаная ====",
                                "= sauce sauce_white =",
                                "= filling cutlet =",
                                "Price:")
                ),
                Arguments.of(
                        10F,
                        "Булочка без ничего",
                        List.of(),
                        List.of("(==== Булочка без ничего ====",
                                "Price:")
                )
        );


    }
}


/*

хэппи пас
только с ингредиентами
без ничего
без цены

 public String getReceipt() {
        StringBuilder receipt = new StringBuilder(String.format("(==== %s ====)%n", bun.getName()));

        for (Ingredient ingredient : ingredients) {
            receipt.append(String.format("= %s %s =%n", ingredient.getType().toString().toLowerCase(),
                    ingredient.getName()));
        }

        receipt.append(String.format("(==== %s ====)%n", bun.getName()));
        receipt.append(String.format("%nPrice: %f%n", getPrice()));

        return receipt.toString();
    }
 */




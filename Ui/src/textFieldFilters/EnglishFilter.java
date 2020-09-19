package textFieldFilters;

import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class EnglishFilter {
    TextFormatter<Integer> integerTextFormatter = new TextFormatter<>(
            new IntegerStringConverter(),
            0,
            c -> Pattern.matches("^[a-zA-Z]*$", c.getText()) ? c : null );

    public TextFormatter<Integer> getEnglishTextFormatter() {
        return integerTextFormatter;
    }
}

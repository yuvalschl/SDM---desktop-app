package textFieldFilters;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

import java.util.regex.Pattern;

public class IntFilter {
    TextFormatter<Integer> integerTextFormatter = new TextFormatter<>(
            new IntegerStringConverter(),
            0,
            c -> Pattern.matches("\\d*", c.getText()) ? c : null );

    public TextFormatter<Integer> getIntegerTextFormatter() {
        return integerTextFormatter;
    }
}

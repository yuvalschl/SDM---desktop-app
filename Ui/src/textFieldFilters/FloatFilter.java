package textFieldFilters;

import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class FloatFilter {
    Pattern validEditingState = Pattern.compile("(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

    UnaryOperator<TextFormatter.Change> filter = c -> {
        String text = c.getControlNewText();
        if (validEditingState.matcher(text).matches()) {
            return c ;
        } else {
            return null ;
        }
    };

    StringConverter<Float> converter = new StringConverter<Float>() {

        @Override
        public Float fromString(String s) {
            return Float.valueOf(s);
        }


        @Override
        public String toString(Float d) {
            return d.toString();
        }
    };

    TextFormatter<Float> textFormatter = new TextFormatter<Float>(converter, (float) 0, filter);

    public Pattern getValidEditingState() {
        return validEditingState;
    }

    public UnaryOperator<TextFormatter.Change> getFilter() {
        return filter;
    }

    public StringConverter<Float> getConverter() {
        return converter;
    }

    public TextFormatter<Float> getTextFormatter() {
        return textFormatter;
    }
}

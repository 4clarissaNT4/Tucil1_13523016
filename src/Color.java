import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Color {
    private static final Map<Character, String> colorMap = new HashMap<>();
    private static final String[] colors = {
        "\u001B[30m", // Black
        "\u001B[31m", // Red
        "\u001B[32m", // Green
        "\u001B[33m", // Yellow
        "\u001B[34m", // Blue
        "\u001B[35m", // Magenta
        "\u001B[36m", // Cyan
        "\u001B[90m", // Bright Black
        "\u001B[91m", // Bright Red
        "\u001B[92m", // Bright Green
        "\u001B[93m", // Bright Yellow
        "\u001B[94m", // Bright Blue
        "\u001B[95m", // Bright Magenta
        "\u001B[96m"  // Bright Cyan
    };

    static {
        assignRandomColors();
    }

    private static void assignRandomColors() {
        Random random = new Random();
        for (char c = 'A'; c <= 'Z'; c++) {
            String color;
            do {
                color = colors[random.nextInt(colors.length)];
            } while (colorMap.containsValue(color));
            colorMap.put(c, color);
        }
    }

    public static String getColor(char c) {
        return colorMap.getOrDefault(c, "\u001B[0m"); 
    }

    public static String reset() {
        return "\u001B[0m";
    }
}
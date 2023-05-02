// Importar las clases necesarias
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ColorPalette extends JPanel {

    // Definir un arreglo de colores predefinidos
    private static final Color[] COLORS = {
        Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE,
        Color.CYAN, Color.MAGENTA, Color.PINK, Color.LIGHT_GRAY, Color.GRAY,
        Color.DARK_GRAY, Color.BLACK, Color.WHITE, new Color(139, 69, 19), new Color(128, 0, 128),new Color(255, 165, 0)
    };

    // Constructor de la clase ColorPalette
    public ColorPalette(ActionListener listener, int rows, int cols) {
        // Configurar el diseño de la paleta de colores
        setLayout(new GridLayout(rows, cols));

        // Crear botones de colores y añadirlos a la paleta
        for (Color color : COLORS) {
            JButton colorButton = new JButton();
            colorButton.setBackground(color);
            colorButton.addActionListener(listener);
            add(colorButton);
        }
    }
}
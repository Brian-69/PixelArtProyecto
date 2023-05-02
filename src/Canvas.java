import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener {
    private int x, y; // variables para la posición del ratón
    private Color currentColor = Color.BLUE; // variable para el color actual
    public static final int ROWS = 25;
    public static final int COLS = 25;
    public static final int CELL_SIZE = 20;
    private Color[][] grid = new Color[ROWS][COLS]; // matriz para representar la cuadrícula de celdas con valores de color
    private BufferedImage img; // imagen en el lienzo
    private Graphics2D g2d;

    public Canvas() { // constructor de la clase Canvas
        addMouseListener(this); // agrega un escucha de eventos de ratón al panel
        addMouseMotionListener(this); // agrega un escucha de eventos de movimiento de ratón al panel
        setBackground(Color.black); // establece el color de fondo del panel en negro
        img = new BufferedImage(ROWS * CELL_SIZE, COLS * CELL_SIZE, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setColor(currentColor);
    }
    

    @Override
    public void paintComponent(Graphics g) { // método para pintar el panel
        super.paintComponent(g); // llama al método super para pintar el fondo

        g.drawImage(img, 0, 0, null);

        for (int row = 0; row < ROWS; row++) { // itera sobre cada fila
            for (int col = 0; col < COLS; col++) { // itera sobre cada columna
                if (grid[row][col] != null) { // si la celda tiene un valor de color
                    g.setColor(grid[row][col]); // establece el color al valor de la celda
                    g.fillRect(row * CELL_SIZE, col * CELL_SIZE, CELL_SIZE, CELL_SIZE); // rellena la celda con el color establecido
                }
                g.setColor(Color.GRAY); // establece el color a gris para los bordes de las celdas
                g.drawRect(row * CELL_SIZE, col * CELL_SIZE, CELL_SIZE, CELL_SIZE); // dibuja el borde de la celda
            }
        }

        g.setColor(currentColor);
        g.fillOval(x - 5, y - 5, 10, 10);
    }
   
    @Override
    public void mouseClicked(MouseEvent e) { // método para los clics de ratón
        x = e.getX() / CELL_SIZE; // obtiene las coordenadas x e y de la celda
        y = e.getY() / CELL_SIZE;

        if (x < ROWS && y < COLS) { // si el clic está dentro de los límites de la cuadrícula
            grid[x][y] = currentColor; // establece el color de la celda clicada al color actual
            g2d.setColor(currentColor);
            g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            repaint(); // vuelve a pintar el panel para actualizar la cuadrícula con el nuevo color
        }
    }

    // métodos vacíos para los eventos de ratón no utilizados
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) { // método para los eventos de arrastre del ratón
    }

    @Override
    public void mouseMoved(MouseEvent e) { // método para los eventos de movimiento del ratón
        x = e.getX(); // actualiza las coordenadas x e y del ratón
        y = e.getY();
        repaint(); // vuelve a pintar el panel para actualizar la posición del marcador de posición del ratón
    }

    public void setColor(Color color) { // método para establecer el color actual
        this.currentColor = color; // establece el color actual como el color pasado como argumento
    }

    public BufferedImage getImg() {
        return img;
    }
    public void setImage(BufferedImage image) {
        img = image; // Establece la imagen (BufferedImage) a la imagen proporcionada
        g2d = img.createGraphics(); // Crea un objeto Graphics2D a partir de la imagen proporcionada
        repaint(); // Vuelve a pintar el componente, actualizando su apariencia
    }

    public void clear() {
        // Crea una nueva imagen (BufferedImage) con dimensiones basadas en el tamaño de las celdas y la cantidad de filas y columnas
        img = new BufferedImage(ROWS * CELL_SIZE, COLS * CELL_SIZE, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics(); // Crea un objeto Graphics2D a partir de la nueva imagen
        g2d.setColor(getBackground()); // Establece el color del objeto Graphics2D al color de fondo actual
        g2d.fillRect(0, 0, ROWS * CELL_SIZE, COLS * CELL_SIZE); // Rellena el rectángulo que representa el área de dibujo con el color de fondo
        grid = new Color[ROWS][COLS]; // Crea una nueva matriz de colores para representar la cuadrícula
        repaint(); // Vuelve a pintar el componente, actualizando su apariencia
        
    }

    } 
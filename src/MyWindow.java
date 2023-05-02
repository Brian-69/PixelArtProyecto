import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

//import MyWindow.ClearCanvasListener;
//import MyWindow.LoadImageListener;
//import MyWindow.SaveImageListener;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MyWindow extends JFrame implements ActionListener, MouseListener, ChangeListener {

JPanel contentPane;
Canvas canvas;
JLabel lbl;
//JSlider one;
static Graphics g;
static BufferedImage img;
int width, height, x, y;

ColorPalette colorPalette;
JPanel selectedColorPanel;
JSlider redSlider, greenSlider, blueSlider;
JLabel createArtLabel, selectedColorLabel;
JButton gridColorButton1, gridColorButton2, backgroundColorButton1, backgroundColorButton2;
JButton saveButton, loadButton, clearButton, titleButton;

public MyWindow(int width, int height) {
 this.width = width;
 this.height = height;

 components();
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setSize(width, height);
 setLocationRelativeTo(null);
 setLayout(null);
 setTitle("My Drawing App");
 setVisible(true);
 }

private void components() {
 contentPane = new JPanel();
 lbl = new JLabel("");
 canvas = new Canvas();
 //one = new JSlider(0, 100);

 saveButton = new JButton();
 ImageIcon saveIcon = new ImageIcon("images/save.png");
 Image scaledImage = saveIcon.getImage().getScaledInstance(170, 240, Image.SCALE_SMOOTH);
 saveButton.setIcon(new ImageIcon(scaledImage));
 saveButton.setBounds(canvas.getX(), canvas.getY() + canvas.getHeight() + 100, 135, 60);
 saveButton.addActionListener(new SaveImageListener());
 saveButton.setBorderPainted(false);
 saveButton.setContentAreaFilled(false);

 //Create button for clearing the canvas
 clearButton = new JButton();
 ImageIcon clearIcon = new ImageIcon("images/eraser.png");
 Image scaledImage1 = clearIcon.getImage().getScaledInstance(170, 240, Image.SCALE_SMOOTH);
 clearButton.setIcon(new ImageIcon(scaledImage1));
 clearButton.setBounds(saveButton.getX(), saveButton.getY() + saveButton.getHeight() + 60, 135, 60);
 clearButton.addActionListener(new ClearCanvasListener());
 clearButton.setBorderPainted(false);
 clearButton.setContentAreaFilled(false);

 //Create button for loading the image
 loadButton = new JButton();
 ImageIcon loadIcon = new ImageIcon("images/load.png");
 Image scaledImage2 = loadIcon.getImage().getScaledInstance(170, 240, Image.SCALE_SMOOTH);
 loadButton.setIcon(new ImageIcon(scaledImage2));
 loadButton.setBounds(saveButton.getX() + 50 + saveButton.getWidth() + 10, saveButton.getY(), 135, 60);
 loadButton.addActionListener(new LoadImageListener());
 loadButton.setBorderPainted(false);
 loadButton.setContentAreaFilled(false);
 
 contentPane = new JPanel() {
	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);

	        Graphics2D g2d = (Graphics2D) g;
	        int width = getWidth();
	        int height = getHeight();

	        Color color1 = new Color(230, 230, 230);
	        Color color2 = new Color(200, 200, 200);
	        Color color3 = new Color(150, 150, 150);
	        Color color4 = new Color(100, 100, 100);

	        int numSections = 3;
	        int sectionHeight = height / numSections;

	        GradientPaint gp1 = new GradientPaint(0, 0, color1, 0, sectionHeight, color2);
	        GradientPaint gp2 = new GradientPaint(0, sectionHeight, color2, 0, 2 * sectionHeight, color3);
	        GradientPaint gp3 = new GradientPaint(0, 2 * sectionHeight, color3, 0, 3 * sectionHeight, color4);

	        g2d.setPaint(gp1);
	        g2d.fillRect(0, 0, width, sectionHeight);

	        g2d.setPaint(gp2);
	        g2d.fillRect(0, sectionHeight, width, 2 * sectionHeight);


	        g2d.setPaint(gp3);
	        g2d.fillRect(0, 2 * sectionHeight, width, 3 * sectionHeight);
	    }
	};
	contentPane.setLayout(null);
	contentPane.setBounds(0, 0, width, height);
	contentPane.add(saveButton);
	contentPane.add(loadButton);
	contentPane.add(clearButton);

	lbl.setBounds(0, 0, width, height);
	lbl.setForeground(Color.white);
	lbl.setFont(new Font("Serif", Font.PLAIN, 500));

	//one.setBounds(0, 0, 335, 35);
	//one.addChangeListener(this);

	canvas.setBounds((width / 2) - 250, (height / 2) - 300, 500, 500);

	
	 titleButton = new JButton();
	 ImageIcon titleIcon = new ImageIcon("images/title.png");
	 Image scaledImage3 = titleIcon.getImage().getScaledInstance(370, 380, Image.SCALE_SMOOTH);
	 titleButton.setIcon(new ImageIcon(scaledImage3));
	 titleButton.setBounds(canvas.getX(), canvas.getY() - 60, 500, 70);
	 titleButton.setBorderPainted(false);
	 titleButton.setContentAreaFilled(false);
	 contentPane.add(titleButton);
	//lbl.setIcon(new ImageIcon(getClass().getResource("images/title.png")));
	createArtLabel = new JLabel("Crea tu propio arte");
	//createArtLabel.setHorizontalAlignment(SwingConstants.CENTER);
	//createArtLabel.setBounds(canvas.getX(), canvas.getY() - 60, 500, 50);

	selectedColorLabel = new JLabel("Color elegido");
	selectedColorLabel.setBounds(canvas.getX() + canvas.getWidth() + 20, canvas.getY() + canvas.getHeight() + 30, 150, 20);

	colorPalette = new ColorPalette(new ColorSelectionListener(), 4, 4);
	colorPalette.setBounds(canvas.getX() + canvas.getWidth() + 20, canvas.getY() + 50, 170, 250);

	backgroundColorButton1 = createColorButton(Color.BLACK, canvas.getX() + canvas.getWidth() + 50, colorPalette.getY() + colorPalette.getHeight() + 100);
	backgroundColorButton1.addActionListener(new BackgroundColorSelectionListener());

	backgroundColorButton2 = createColorButton(Color.WHITE, backgroundColorButton1.getX() + backgroundColorButton1.getWidth() + 10, backgroundColorButton1.getY());
	backgroundColorButton2.addActionListener(new BackgroundColorSelectionListener());

	selectedColorPanel = new JPanel();
	selectedColorPanel.setBackground(Color.WHITE);
	selectedColorPanel.setBounds(canvas.getX() + canvas.getWidth() + 20, canvas.getY() + canvas.getHeight() + 60, 100, 50);

	redSlider = createColorSlider(Color.RED, canvas.getX() + canvas.getWidth() / 2 - 150, canvas.getY() + canvas.getHeight() + 8);
	greenSlider = createColorSlider(Color.GREEN, redSlider.getX(), redSlider.getY() + redSlider.getHeight() + 18);
	blueSlider = createColorSlider(Color.BLUE, greenSlider.getX(), greenSlider.getY() + greenSlider.getHeight() + 21);

	canvas.addMouseListener(this);
	//contentPane.add(one);
	contentPane.add(canvas);
	contentPane.add(lbl);
	contentPane.add(colorPalette);
	contentPane.add(selectedColorPanel);
	contentPane.add(redSlider);
	contentPane.add(greenSlider);
	contentPane.add(blueSlider);
	contentPane.add(createArtLabel);
	contentPane.add(selectedColorLabel);
	contentPane.add(backgroundColorButton1);
	contentPane.add(backgroundColorButton2);
	contentPane.add(saveButton);
	contentPane.add(loadButton);
	contentPane.add(clearButton);
	contentPane.add(titleButton);

	add(contentPane);}

	private JSlider createColorSlider(Color color, int x, int y) {
	    JSlider slider = new JSlider(0, 255);
	    slider.setPaintTicks(false);
	    slider.setPaintLabels(false);
	    slider.setMajorTickSpacing(50);
	    slider.setMinorTickSpacing(10);
	    slider.setForeground(color);
	    slider.setBackground(color);
	    slider.setBounds(x, y, 300, 30);
	    slider.addChangeListener(new ChangeListener() {
	        @Override
	        public void stateChanged(ChangeEvent e) {
	            updateSelectedColorFromSliders();
	        }
	    });
	    return slider;
	}

	private void updateSelectedColorFromSliders() {
	    Color selectedColor = new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue());
	    selectedColorPanel.setBackground(selectedColor);
	    canvas.setColor(selectedColor);
	}

	public void actionPerformed(ActionEvent event) {
	    lbl.setText("HOLA TODOS !!!");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	    x = e.getX();
	    y = e.getY();
	    lbl.setText(x + " " + y);
	}
    
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

    // Método que se ejecuta cuando se cambia el valor del control deslizante 'one'
   

    // Método para crear botones de color
    private JButton createColorButton(Color color, int x, int y) {
        JButton button = new JButton(); // Crea un nuevo botón
        button.setBackground(color); // Establece el color de fondo del botón al color especificado
        button.setBounds(x, y, 30, 30); // Establece la posición y el tamaño del botón
        button.setPreferredSize(new Dimension(30, 30)); // Establece las dimensiones preferidas del botón
        return button; // Devuelve el botón creado
    }
    

    // Clase interna para manejar la selección de colores de fondo
    private class BackgroundColorSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource(); // Obtiene el botón que generó el evento
            Color selectedColor = source.getBackground(); // Obtiene el color de fondo del botón
            canvas.setBackground(selectedColor); // Establece el color de fondo del lienzo al color seleccionado
        }
    }

    // Clase interna para manejar la selección de colores de la paleta
    private class ColorSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource(); // Obtiene el botón que generó el evento
            Color selectedColor = source.getBackground(); // Obtiene el color de fondo del botón
            canvas.setColor(selectedColor); // Establece el color de dibujo del lienzo al color seleccionado
            selectedColorPanel.setBackground(selectedColor); // Establece el color de fondo del panel de color seleccionado
            redSlider.setValue(selectedColor.getRed()); // Establece el valor del control deslizante de rojo al valor de rojo del color seleccionado
            greenSlider.setValue(selectedColor.getGreen()); // Establece el valor del control deslizante de verde al valor de verde del color seleccionado
            blueSlider.setValue(selectedColor.getBlue()); // Establece el valor del control deslizante de azul al valor de azul del color seleccionado
        }
    }

    
    private class SaveImageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar imagen como");
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop/"));
            
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPG Image", "jpg"));
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG Image", "png"));
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("GIF Image", "gif"));

            int returnVal = fileChooser.showSaveDialog(MyWindow.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String filePath = file.getAbsolutePath();
                
                FileNameExtensionFilter selectedFilter = (FileNameExtensionFilter) fileChooser.getFileFilter();
                String extension = selectedFilter.getExtensions()[0];

                if (!filePath.toLowerCase().endsWith("." + extension)) {
                    file = new File(filePath + "." + extension);
                }

                try {
                    BufferedImage imgToSave = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2d = imgToSave.createGraphics();

                    g2d.setColor(canvas.getBackground());
                    g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    g2d.drawImage(canvas.getImg(), 0, 0, null);

                   /* // Dibuja la cuadrícula
                    g2d.setColor(Color.GRAY);
                    for (int row = 0; row < canvas.ROWS; row++) {
                        for (int col = 0; col < canvas.COLS; col++) {
                            g2d.drawRect(row * canvas.CELL_SIZE, col * canvas.CELL_SIZE, canvas.CELL_SIZE, canvas.CELL_SIZE);
                        }
                    }*/

                    ImageIO.write(imgToSave, extension, file);
                    g2d.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    // Clase interna para manejar la acción de cargar la imagen
    private class LoadImageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(); // Crea un nuevo selector de archivos
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, GIF & PNG Images", "jpg", "gif", "png"); // Crea un filtro para archivos de imagen
            fileChooser.setFileFilter(filter); // Establece el filtro de archivos en el selector de archivos
            int returnVal = fileChooser.showOpenDialog(MyWindow.this); // Muestra el cuadro de diálogo de abrir archivo y guarda el resultado
            if (returnVal == JFileChooser.APPROVE_OPTION) { // Si el usuario aprueba la selección de archivos
                File file = fileChooser.getSelectedFile(); // Obtiene el archivo seleccionado
                try {
                    BufferedImage image = ImageIO.read(file); // Lee la imagen del archivo
                    canvas.setImage(image); // Establece la imagen en el lienzo
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // Clase interna para manejar la acción de borrar el área de dibujo
    private class ClearCanvasListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.clear(); // Limpia el área de dibujo en el lienzo
        }
    }

    } // Fin de la clase
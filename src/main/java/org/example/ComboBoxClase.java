package org.example;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class ComboBoxClase extends JComboBox<String> {

    private Color colorFondo;
    private Color colorBorde;
    private Color colorTexto;
    private Color colorBoton;
    private Color colorFlecha;
    private int radio = 18;

    public ComboBoxClase(String[] items) {
        super(items);

        setFont(new Font("Arial", Font.PLAIN, 20));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        setUI(new ComboUIRedondo());

        aplicarTema();
    }

    public void aplicarTema() {
        colorFondo = Temas.actual.fondoCampo;
        colorBorde = Temas.actual.borde;
        colorTexto = Temas.actual.texto;
        colorBoton = Temas.actual.fondoCampo;
        colorFlecha = Temas.actual.texto;

        setBackground(colorFondo);
        setForeground(colorTexto);

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Fondo completo redondeado
        g2.setColor(colorFondo);
        g2.fillRoundRect(
                0,
                0,
                getWidth() - 1,
                getHeight() - 1,
                radio,
                radio
        );

        // Fondo del botón de flecha
        int anchoBoton = 38;

        g2.setColor(colorBoton);
        g2.fillRoundRect(
                getWidth() - anchoBoton,
                0,
                anchoBoton,
                getHeight() - 1,
                radio,
                radio
        );

        // Limpia la parte izquierda del botón para que no redondee por dentro
        g2.fillRect(
                getWidth() - anchoBoton,
                0,
                anchoBoton / 2,
                getHeight() - 1
        );

        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(colorBorde);
        g2.setStroke(new BasicStroke(1.2f));

        g2.drawRoundRect(
                0,
                0,
                getWidth() - 1,
                getHeight() - 1,
                radio,
                radio
        );

        g2.dispose();
    }

    private class ComboUIRedondo extends BasicComboBoxUI {

        @Override
        protected JButton createArrowButton() {
            JButton boton = new JButton() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();

                    g2.setRenderingHint(
                            RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON
                    );

                    int w = getWidth();
                    int h = getHeight();

                    g2.setColor(colorFlecha);

                    int[] x = {
                            w / 2 - 5,
                            w / 2,
                            w / 2 + 5
                    };

                    int[] y = {
                            h / 2 - 2,
                            h / 2 + 4,
                            h / 2 - 2
                    };

                    g2.setStroke(new BasicStroke(2f));
                    g2.drawPolyline(x, y, 3);

                    g2.dispose();
                }
            };

            boton.setOpaque(false);
            boton.setContentAreaFilled(false);
            boton.setBorderPainted(false);
            boton.setFocusPainted(false);
            boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            return boton;
        }

        @Override
        public void paintCurrentValueBackground(
                Graphics g,
                Rectangle bounds,
                boolean hasFocus
        ) {
            // Evita que BasicComboBoxUI pinte un fondo cuadrado encima
        }
    }
}
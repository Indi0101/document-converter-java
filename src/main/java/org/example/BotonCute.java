package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BotonCute extends JButton {

    private Color colorActual = Temas.actual.botonNormal;
    public BotonCute(String texto) {
        super(texto);

        setForeground(Temas.actual.texto);
        setFont(new Font("Arial", Font.PLAIN, 14));
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setMargin(new Insets(0, 0, 0, 0));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                colorActual = Temas.actual.botonHover;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                colorActual = Temas.actual.botonNormal;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                colorActual = Temas.actual.botonClick;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                colorActual = Temas.actual.botonHover;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Sombra suave
        g2.setColor(colorActual);
        g2.fillRoundRect(3, 4, getWidth() - 4, getHeight() - 4, 18, 18);

        // Fondo del botón
        g2.setColor(colorActual);
        g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 18, 18);

        super.paintComponent(g);

        g2.dispose();
    }
    public void aplicarTema() {
        colorActual = Temas.actual.botonNormal;
        setForeground(Temas.actual.texto);
        repaint();
    }
}

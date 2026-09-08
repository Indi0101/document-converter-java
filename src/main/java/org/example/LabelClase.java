package org.example;

import javax.swing.*;
import java.awt.*;

public class LabelClase extends JLabel {
    private Color colorFondo = Temas.actual.labelFondo;
    private Color colorBorde = Temas.actual.labelBorde;
    private int radio = 18;

    public LabelClase(String texto) {
        super(texto);
        setOpaque(false);
        setForeground(Color.decode("#F3DCDC"));
        setFont(new Font("Arial", Font.PLAIN, 20));
        setHorizontalAlignment(SwingConstants.LEFT);
        setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(colorFondo);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radio, radio);

        super.paintComponent(g);

        g2.dispose();
    }
    public void aplicarTema() {
        colorFondo = Temas.actual.labelFondo;
        colorBorde = Temas.actual.labelBorde;
        setForeground(Temas.actual.labelTexto);

        repaint();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(colorBorde);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radio, radio);

        g2.dispose();
    }
}

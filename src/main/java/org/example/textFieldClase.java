package org.example;

import javax.swing.*;
import java.awt.*;

public class textFieldClase extends JTextField {
    private Color colorFondo = Temas.actual.textFieldFondo;
    private Color colorBorde = Temas.actual.textFieldBorde;
    private int radio = 18;

    public textFieldClase(String texto) {
        super(texto);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        setForeground(Color.decode("#F3DCDC"));
        setCaretColor(Color.WHITE);
        setFont(new Font("Arial", Font.PLAIN, 14));
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
        colorFondo = Temas.actual.textFieldFondo;
        colorBorde = Temas.actual.textFieldBorde;

        setForeground(Temas.actual.textFieldTexto);
        setCaretColor(Temas.actual.textFieldCaret);

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

package org.example;

import javax.swing.*;
import java.awt.*;
import com.kitfox.svg.app.beans.SVGIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
public class PanelTema extends JPanel {

    private JRadioButton rbCute;
    private JRadioButton rbOscuro;
    private JRadioButton rbClaro;
    private JRadioButton rbSerio;

    public PanelTema(Runnable accionAlCambiarTema) {
        setLayout(new BorderLayout(0, 6));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setBackground(Temas.actual.fondo);

       /* JLabel titulo = new JLabel("Tema");
        titulo.setForeground(Temas.actual.textoSuave);
        titulo.setHorizontalAlignment(SwingConstants.LEFT);
        titulo.setFont(new Font("Arial", Font.BOLD, 12));*/

        rbCute = crearRadio("", "/iconos/suit-heart.svg");
        rbOscuro = crearRadio("", "/iconos/moon.svg");
        rbClaro = crearRadio("", "/iconos/brightness-high.svg");
        rbSerio = crearRadio("", "/iconos/briefcase.svg");

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbCute);
        grupo.add(rbOscuro);
        grupo.add(rbClaro);
        grupo.add(rbSerio);

        marcarTemaActual();
        actualizarEstadoRadios();

        rbCute.addActionListener(e -> cambiarTema(Temas.CUTE, accionAlCambiarTema));
        rbOscuro.addActionListener(e -> cambiarTema(Temas.OSCURO, accionAlCambiarTema));
        rbClaro.addActionListener(e -> cambiarTema(Temas.CLARO, accionAlCambiarTema));
        rbSerio.addActionListener(e -> cambiarTema(Temas.SERIO, accionAlCambiarTema));

        JPanel panelRadios = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        panelRadios.setOpaque(false);

        panelRadios.add(rbCute);
        panelRadios.add(rbOscuro);
        panelRadios.add(rbClaro);
        panelRadios.add(rbSerio);

        //add(titulo, BorderLayout.NORTH);
        add(panelRadios, BorderLayout.WEST);
    }

    private JRadioButton crearRadio(String texto, String rutaIcono) {

        JRadioButton radio = new RadioTema(texto);

        Icon iconoNormal = cargarIconoSVG(rutaIcono, 18, 18);

        radio.setIcon(iconoNormal);

        radio.setOpaque(false);
        radio.setContentAreaFilled(false);
        radio.setBorderPainted(false);
        radio.setFocusPainted(false);

        radio.setCursor(new Cursor(Cursor.HAND_CURSOR));
        radio.setMargin(new Insets(0, 0, 0, 0));
        radio.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        radio.setPreferredSize(new Dimension(28, 28));
        radio.setMinimumSize(new Dimension(28, 28));
        radio.setMaximumSize(new Dimension(28, 28));

        radio.setHorizontalAlignment(SwingConstants.CENTER);
        radio.setVerticalAlignment(SwingConstants.CENTER);
        radio.setHorizontalTextPosition(SwingConstants.CENTER);
        radio.setVerticalTextPosition(SwingConstants.CENTER);
        radio.setIconTextGap(0);

        return radio;
    }

    private void cambiarTema(TemaApp tema, Runnable accionAlCambiarTema) {
        Temas.cambiarTema(tema);

        actualizarEstadoRadios();
        if (accionAlCambiarTema != null) {
            accionAlCambiarTema.run();
        }
    }

    private void marcarTemaActual() {

        if (Temas.actual == Temas.CUTE) {
            rbCute.setSelected(true);
        } else if (Temas.actual == Temas.OSCURO) {
            rbOscuro.setSelected(true);
        } else if (Temas.actual == Temas.CLARO) {
            rbClaro.setSelected(true);
        } else if (Temas.actual == Temas.SERIO) {
            rbSerio.setSelected(true);
        }
    }

    public void aplicarTema() {
        setBackground(Temas.actual.fondo);

        for (Component c : getComponents()) {
            if (c instanceof JLabel) {
                c.setForeground(Temas.actual.textoSuave);
            }

            if (c instanceof JRadioButton) {
                c.setForeground(Temas.actual.texto);
            }
        }

        repaint();
    }
    private Icon cargarIconoSVG(String ruta, int ancho, int alto) {
        try {
            URL url = getClass().getResource(ruta);

            if (url == null) {
                System.out.println("No se encontró el icono: " + ruta);
                return null;
            }

            SVGIcon icono = new SVGIcon();
            icono.setSvgURI(url.toURI());
            icono.setPreferredSize(new Dimension(ancho, alto));

            return icono;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void actualizarEstadoRadios() {
        JRadioButton[] radios = {rbCute, rbOscuro, rbClaro, rbSerio};

        for (JRadioButton radio : radios) {
            radio.setOpaque(false);
            radio.setContentAreaFilled(false);
            radio.setBorderPainted(false);
            radio.repaint();
        }
    }
    private class RadioTema extends JRadioButton {

        private boolean hover = false;
        private boolean pressed = false;

        public RadioTema(String texto) {
            super(texto);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hover = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hover = false;
                    pressed = false;
                    repaint();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    pressed = true;
                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    pressed = false;
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

            if (hover || isSelected()) {
                Color base = isSelected()
                        ? Temas.actual.botonHover
                        : Temas.actual.botonNormal;

                g2.setColor(new Color(
                        base.getRed(),
                        base.getGreen(),
                        base.getBlue(),
                        hover ? 70 : 45
                ));

                g2.drawRoundRect(
                        3,
                        3,
                        getWidth() - 7,
                        getHeight() - 7,
                        10,
                        10
                );
            }

            if (isSelected()) {
                g2.setColor(Temas.actual.botonHover);
                g2.fillRoundRect(
                        getWidth() / 2 - 7,
                        getHeight() - 4,
                        14,
                        2,
                        4,
                        4
                );
            }

            if (pressed) {
                g2.translate(1, 1);
            }

            super.paintComponent(g2);

            g2.dispose();
        }
    }
}

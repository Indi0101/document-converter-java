package org.example;
import java.awt.Color;

public class TemaApp {
    public String nombre;

    public Color fondo;
    public Color fondoCampo;
    public Color fondoPanel;
    public Color borde;

    public Color botonNormal;
    public Color botonHover;
    public Color botonClick;

    public Color textFieldFondo;
    public Color textFieldBorde;
    public Color textFieldTexto;
    public Color textFieldCaret;

    public Color labelFondo;
    public Color labelBorde;
    public Color labelTexto;

    public Color texto;
    public Color textoSuave;

    public TemaApp(
            String nombre,
            Color fondo,
            Color fondoCampo,
            Color fondoPanel,
            Color borde,
            Color botonNormal,
            Color botonHover,
            Color botonClick,
            Color texto,
            Color textoSuave,
            Color textFieldFondo,
            Color textFieldBorde,
            Color textFieldTexto,
            Color textFieldCaret,
            Color labelFondo,
            Color labelBorde,
            Color labelTexto
    ) {
        this.nombre = nombre;
        this.fondo = fondo;
        this.fondoCampo = fondoCampo;
        this.fondoPanel = fondoPanel;
        this.borde = borde;
        this.botonNormal = botonNormal;
        this.botonHover = botonHover;
        this.botonClick = botonClick;
        this.texto = texto;
        this.textoSuave = textoSuave;
        this.textFieldFondo = textFieldFondo;
        this.textFieldBorde = textFieldBorde;
        this.textFieldTexto = textFieldTexto;
        this.textFieldCaret = textFieldCaret;
        this.labelFondo=labelFondo;
        this.labelBorde=labelBorde;
        this.labelTexto=labelTexto;

    }
}

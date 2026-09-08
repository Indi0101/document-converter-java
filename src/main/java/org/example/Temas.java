package org.example;

import java.awt.Color;

public class Temas {

    public static final TemaApp CUTE = new TemaApp(
            "Cute",
            Color.decode("#55484D"), // fondo principal: menos oscuro, cálido
            Color.decode("#63545A"), // campos
            Color.decode("#6A5559"), // panel lateral/top
            Color.decode("#9C7A80"), // bordes

            Color.decode("#D18484"), // botón normal
            Color.decode("#E7AAAA"), // hover
            Color.decode("#B96B6B"), // click

            Color.decode("#FFF0F0"), // texto principal
            Color.decode("#E8CCCC"),  // texto suave

            Color.decode("#6B555C"), // textFieldFondo
            Color.decode("#B98F96"), // textFieldBorde
            Color.decode("#FFF0F0"), // textFieldTexto
            Color.decode("#FFFFFF"),

            Color.decode("#6B555C"), //label convertir_archivo
            Color.decode("#B98F96"), //label convertir_archivo
            Color.decode("#FFF0F0") //label convertir_archivo
    );

    public static final TemaApp OSCURO = new TemaApp(
            "Oscuro",
            Color.decode("#202124"),
            Color.decode("#2B2C30"),
            Color.decode("#303134"),
            Color.decode("#5F6368"),

            Color.decode("#4F6F8F"),
            Color.decode("#6F95B8"),
            Color.decode("#35516D"),

            Color.decode("#E8EAED"),
            Color.decode("#BDC1C6"),

            Color.decode("#2B2C30"), // textFieldFondo
            Color.decode("#6B7280"), // textFieldBorde
            Color.decode("#E8EAED"), // textFieldTexto
            Color.decode("#FFFFFF"),  // textFieldCaret

            Color.decode("#2B2C30"), //label convertir_archivo
            Color.decode("#6B7280"), //label convertir_archivo
            Color.decode("#E8EAED") //label convertir_archivo

    );

    public static final TemaApp CLARO = new TemaApp(
            "Claro",
            Color.decode("#D4C0BA"), // fondo principal: beige grisáceo, no blanco puro
            Color.decode("#F3EAE5"), // campos
            Color.decode("#D8C8C2"), // panel lateral/top
            Color.decode("#B9AAA4"), // bordes

            Color.decode("#B77B7B"), // botón normal
            Color.decode("#D29A9A"), // hover
            Color.decode("#9E6666"), // click

            Color.decode("#FFF0F0"), // texto principal
            Color.decode("#E8CCCC"),  // texto suave

            Color.decode("#F6EEE9"), // textFieldFondo
            Color.decode("#BCAAA4"), // textFieldBorde
            Color.decode("#3E3532"), // textFieldTexto
            Color.decode("#3E3532"), // textFieldText

            Color.decode("#F6EEE9"), //label convertir_archivo
            Color.decode("#BCAAA4"), //label convertir_archivo
            Color.decode("#3E3532") //label convertir_archivo

    );

    public static final TemaApp SERIO = new TemaApp(
            "Serio",
            Color.decode("#1F2933"),
            Color.decode("#273444"),
            Color.decode("#2F3E4E"),
            Color.decode("#52616B"),
            Color.decode("#607D8B"),
            Color.decode("#78909C"),
            Color.decode("#455A64"),
            Color.decode("#ECEFF1"),
            Color.decode("#CFD8DC"),
            Color.decode("#263442"), // textFieldFondo
            Color.decode("#6C7A86"), // textFieldBorde
            Color.decode("#ECEFF1"), // textFieldTexto
            Color.decode("#FFFFFF"),  // textFieldCaret
            Color.decode("#263442"), //label convertir_archivo
            Color.decode("#6C7A86"), //label convertir_archivo
            Color.decode("#ECEFF1") //label convertir_archivo
    );

    public static TemaApp actual = CUTE;

    public static void cambiarTema(TemaApp nuevoTema) {

        actual = nuevoTema;
    }
}
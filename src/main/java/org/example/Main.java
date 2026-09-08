package org.example;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        UIManager.put("Component.arc", 18);
        UIManager.put("Button.arc", 18);
        UIManager.put("TextComponent.arc", 18);
        UIManager.put("ComboBox.arc", 18);
        UIManager.put("ProgressBar.arc", 18);
        new convertir_archivos_pdf();
        }
    }

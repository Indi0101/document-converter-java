package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.filechooser.FileNameExtensionFilter;

public class unirPDF extends JFrame {
    String archivo;
    private ArrayList<String> archivosPDF = new ArrayList<>();
    JPanel principal;
    JPanel grid ;
    JPanel panelDerecho;
    JLabel lblMensaje = new JLabel("Seleccione los PDFs que desea unir");
    JProgressBar barraCarga = new JProgressBar();
    JButton selec_archivos;
    JButton unirBTN;
    JButton eliminar_archivo;
    PanelTema panelTema;
    JPanel panelTop;

    JScrollPane scroll;
    String ruta_vista="C:\\Users\\zindi\\Documents\\convertirArchivosPDF\\vistas\\unir";
    ArrayList<JCheckBox> checks = new ArrayList<>();
    public unirPDF() {
            setTitle("Unir PDF");
            setSize(920, 520);
            setMinimumSize(new Dimension(760, 450));
            setResizable(true);
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            setLocation(15, 15);

            principal = new JPanel(new BorderLayout(5, 5));
            principal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            principal.setBackground(ColoresCute.FONDO);

            panelTop = new JPanel(new BorderLayout());
            //panelTop.setBackground(Temas.actual.fondoPanel);
            //panelTop.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));

            panelTema = new PanelTema(() -> aplicarTema());
            panelTop.add(panelTema,BorderLayout.CENTER);

            grid = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
            grid.setBackground(ColoresCute.FONDO);

            scroll = new JScrollPane(grid);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scroll.setBorder(BorderFactory.createLineBorder(ColoresCute.BORDE));

            JPanel panelDerechoContenedor = new JPanel(new BorderLayout(0, 10));
            panelDerechoContenedor.setPreferredSize(new Dimension(180, 0));
            panelDerechoContenedor.setBackground(ColoresCute.FONDO_PANEL);
            panelDerechoContenedor.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));

            panelDerecho = new JPanel(new GridLayout(3, 1, 0, 12));
            panelDerecho.setOpaque(false);

            selec_archivos = new BotonCute("Seleccionar");
            selec_archivos.setPreferredSize(new Dimension(150, 35));
            selec_archivos.addActionListener(e -> seleccionarArchivos());

            unirBTN = new BotonCute("Unir");
            unirBTN.setPreferredSize(new Dimension(150, 35));
            unirBTN.addActionListener(e -> iniciarUnion());

            eliminar_archivo = new BotonCute("Eliminar");
            eliminar_archivo.setPreferredSize(new Dimension(150, 35));
            eliminar_archivo.addActionListener(e -> eliminar_seleccion());

            panelDerecho.add(selec_archivos);
            panelDerecho.add(unirBTN);
            panelDerecho.add(eliminar_archivo);

            lblMensaje = new JLabel("Seleccione los PDFs");
            lblMensaje.setForeground(Color.decode("#D9C4C4"));
            lblMensaje.setFont(new Font("Arial", Font.PLAIN, 12));
            lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);

            barraCarga = new JProgressBar();
            barraCarga.setIndeterminate(true);
            barraCarga.setVisible(false);
            barraCarga.setForeground(ColoresCute.ROSA_HOVER);
            barraCarga.setBackground(ColoresCute.FONDO_CAMPO);

            JPanel panelEstado = new JPanel(new GridLayout(2, 1, 0, 5));
            panelEstado.setOpaque(false);
            panelEstado.add(lblMensaje);
            panelEstado.add(barraCarga);

            panelDerechoContenedor.add(panelDerecho, BorderLayout.NORTH);
            panelDerechoContenedor.add(panelEstado, BorderLayout.SOUTH);

            principal.add(panelTop,BorderLayout.NORTH);
            principal.add(scroll, BorderLayout.CENTER);
            principal.add(panelDerechoContenedor, BorderLayout.EAST);

            setContentPane(principal);
            setVisible(true);
            setState(JFrame.NORMAL);

    }
    public unirPDF(String archivo) {
        this();
        this.archivo = archivo;

        if (archivo != null && !archivo.isEmpty()) {
            archivosPDF.add(archivo);
            crear_vistas_previas(archivo, archivosPDF.size());
        }
    }
    private void crear_vistas_previas(String rutaPDF,int numeroDocumento) {
        String salida_documento=ruta_vista+"\\documento_"+numeroDocumento+"_preview.png";

        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "python",
                    "src/main/resources/python/vista_previa_unirPDF.py",
                    rutaPDF,
                    salida_documento
            );

            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );
            String linea;

            while ((linea = br.readLine()) != null) {
                System.out.println("PYTHON PREVIEW UNIR::: " + linea);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        vista_previa_archivo(salida_documento, numeroDocumento);

    }
    public String obtenerPaginasSeleccionadas() {
        ArrayList<String> paginas = new ArrayList<>();
        for (int i = 0; i < checks.size(); i++) {
            if (checks.get(i).isSelected()) {
                paginas.add(String.valueOf(i + 1));
            }
        }
        return String.join(",", paginas);
    }
    public void eliminar_seleccion(){
        String seleccionadas = obtenerPaginasSeleccionadas();

        if (seleccionadas.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione al menos un documento para eliminar"
            );
            return;
        }

        String[] pagina_seleccionada = seleccionadas.split(",");

        for (int i = pagina_seleccionada.length - 1; i >= 0; i--) {

            int id = Integer.parseInt(pagina_seleccionada[i]);

            int indice = id - 1;

            System.out.println("Eliminar índice: " + indice);

            grid.remove(indice);
            checks.remove(indice);
            archivosPDF.remove(indice);
        }

        grid.revalidate();
        grid.repaint();

        scroll.revalidate();
        scroll.repaint();

        //System.out.println("Eliminar: Pagina eliminada"+pagina_seleccionada);
        //System.out.println("Eliminar: Jpanel seleccionado"+grid.getComponent(Integer.parseInt(pagina_seleccionada)-1));

    }
    private void iniciarUnion() {
        barraCarga.setVisible(true);
        lblMensaje.setText("Uniendo PDFs...");

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                unirPDFs();
                return null;
            }

            @Override
            protected void done() {
                barraCarga.setVisible(false);

                try {
                    get();

                    lblMensaje.setText("PDF unido correctamente");

                    JOptionPane.showMessageDialog(
                            unirPDF.this,
                            "PDF unido correctamente",
                            "Proceso finalizado",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                } catch (Exception e) {
                    lblMensaje.setText("No se pudo unir");

                    String mensajeError = e.getCause() != null
                            ? e.getCause().getMessage()
                            : e.getMessage();

                    JOptionPane.showMessageDialog(
                            unirPDF.this,
                            mensajeError,
                            "No se puede unir",
                            JOptionPane.WARNING_MESSAGE);

                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }
    public void vista_previa_archivo(String rutaimagen, int numeroDocumento) {
        JCheckBox check = new JCheckBox("Documento " + numeroDocumento);
        check.setForeground(ColoresCute.TEXTO);
        check.setOpaque(false);
        checks.add(check);

        ImageIcon icon = new ImageIcon(rutaimagen);
        Image img = icon.getImage().getScaledInstance(
                120,
                160,
                Image.SCALE_SMOOTH
        );

        JLabel preview = new JLabel(new ImageIcon(img));
        preview.setHorizontalAlignment(JLabel.CENTER);

        JPanel card = new JPanel(new BorderLayout(0, 8));
        card.setBackground(ColoresCute.FONDO_CAMPO);
        card.setPreferredSize(new Dimension(150, 220));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColoresCute.ROSA_HOVER),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        card.add(preview, BorderLayout.CENTER);
        card.add(check, BorderLayout.SOUTH);

        grid.add(card);
        grid.revalidate();
        grid.repaint();
        scroll.revalidate();
        scroll.repaint();
    }
    public void seleccionarArchivos() {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);

        FileNameExtensionFilter filtroPDF = new FileNameExtensionFilter(
                "Archivos PDF (*.pdf)",
                "pdf"
        );
        chooser.setFileFilter(filtroPDF);
        chooser.setAcceptAllFileFilterUsed(false);

        int resultado = chooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {

            File[] archivos = chooser.getSelectedFiles();

            for (File archivo : archivos) {
                String nombreArchivo = archivo.getName().toLowerCase();

                // Validación extra por seguridad
                if (!nombreArchivo.endsWith(".pdf")) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Solo puedes seleccionar archivos PDF.",
                            "Archivo no permitido",
                            JOptionPane.WARNING_MESSAGE
                    );
                    continue;
                }

                archivosPDF.add(archivo.getAbsolutePath());
                int numeroDocumento = archivosPDF.size();
                crear_vistas_previas(archivo.getAbsolutePath(), numeroDocumento);
            }

            lblMensaje.setText("PDFs seleccionados: " + archivosPDF.size());

            grid.revalidate();
            grid.repaint();
            scroll.revalidate();
            scroll.repaint();
        }
    }
    public void unirPDFs() throws IOException {

        if (archivosPDF.size() < 2) {
            throw new IllegalStateException("Debes seleccionar al menos 2 PDFs para poder unirlos.");
        }
        if (archivosPDF.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Primero selecciona al menos un PDF",
                    "Sin archivos",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String salida = "C:\\Users\\zindi\\Documents\\convertirArchivosPDF\\pdf_unido.pdf";

        ArrayList<String> comando = new ArrayList<>();
        ArrayList<String> archivosSeleccionados = new ArrayList<>();

        String seleccionados = obtenerPaginasSeleccionadas();

        if (!seleccionados.isEmpty()) {
            String[] seleccionadosArray = seleccionados.split(",");

            for (String seleccionado : seleccionadosArray) {
                int id = Integer.parseInt(seleccionado);
                archivosSeleccionados.add(archivosPDF.get(id - 1));
            }
        }

        comando.add("python");
        comando.add("src/main/resources/python/unir_pdf.py");
        comando.add(salida);

        if (!archivosSeleccionados.isEmpty()) {
            comando.addAll(archivosSeleccionados);
        } else {
            comando.addAll(archivosPDF);
        }

        ProcessBuilder pb = new ProcessBuilder(comando);
        pb.redirectErrorStream(true);

        Process process = pb.start();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(process.getInputStream())
        );

        String linea;

        while ((linea = br.readLine()) != null) {
            System.out.println("PYTHON UNIR::: " + linea);
        }

        try {
            int codigoSalida = process.waitFor();
            System.out.println("CÓDIGO PYTHON UNIR: " + codigoSalida);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
    private void aplicarTema() {
        principal.setBackground(Temas.actual.fondo);
        grid.setBackground(Temas.actual.fondo);

        if (scroll != null) {
            scroll.setBorder(BorderFactory.createLineBorder(Temas.actual.borde));
        }

        if (lblMensaje != null) {
            lblMensaje.setForeground(Temas.actual.textoSuave);
        }

        if (barraCarga != null) {
            barraCarga.setForeground(Temas.actual.botonHover);
            barraCarga.setBackground(Temas.actual.fondoCampo);
        }

        if (panelTema != null) {
            panelTema.aplicarTema();
        }

        actualizarBotones(this);
        SwingUtilities.updateComponentTreeUI(this);
        repaint();
    }
    private void actualizarBotones(Container contenedor) {
        for (Component c : contenedor.getComponents()) {
            if (c instanceof BotonCute) {
                ((BotonCute) c).aplicarTema();
            }

            if (c instanceof Container) {
                actualizarBotones((Container) c);
            }
        }
    }
}

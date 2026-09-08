package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class separarPDF extends JFrame {

    private String archivo;

    private JPanel principal;
    private JPanel grid;
    private JPanel panelDerecho;
    private JPanel panelTop;
    private PanelTema panelTema;

    private JScrollPane scroll;
    private JLabel lblMensaje;
    private JProgressBar barraCarga;

    private JButton btnSeparar;
    private JButton btnSeleccionarTodo;
    private JButton btnLimpiar;
    private JButton btnBuscarPDF;
    private JButton btnDesmarcar;

    private ArrayList<JCheckBox> checks = new ArrayList<>();

    private final String carpetaPreview =
            "C:\\Users\\zindi\\Documents\\convertirArchivosPDF\\vistas\\separar";

    public separarPDF(String archivo) {
        this.archivo = archivo;

        setTitle("Separar PDF");
        setSize(920, 520);
        setMinimumSize(new Dimension(760, 450));
        setResizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocation(15, 15);

        construirInterfaz();

        if (archivo != null && !archivo.isEmpty()) {
            iniciarCargaPreview();
        } else {
            lblMensaje.setText("No se recibió ningún PDF");
        }

        setVisible(true);
        setState(JFrame.NORMAL);
    }

    private void construirInterfaz() {
        principal = new JPanel(new BorderLayout(10, 10));
        principal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        principal.setBackground(ColoresCute.FONDO);

        panelTop= new JPanel(new BorderLayout());
        panelTema = new PanelTema(()->aplicarTema());
        panelTop.add(panelTema,BorderLayout.CENTER);

        grid = new JPanel();
        grid.setBackground(ColoresCute.FONDO);

        scroll = new JScrollPane(grid);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createLineBorder(ColoresCute.BORDE));

        JPanel panelDerechoContenedor = new JPanel(new BorderLayout(0, 10));
        panelDerechoContenedor.setPreferredSize(new Dimension(180, 0));
        panelDerechoContenedor.setBackground(ColoresCute.FONDO_PANEL);
        panelDerechoContenedor.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));

        panelDerecho = new JPanel(new GridLayout(6, 1, 0, 12));
        panelDerecho.setOpaque(false);

        btnBuscarPDF = new BotonCute("Buscar PDF");
        btnBuscarPDF.setPreferredSize(new Dimension(150, 35));
        btnBuscarPDF.addActionListener(e -> buscarPDFDesdeSeparar());

        btnSeparar = new BotonCute("Separar");
        btnSeparar.setPreferredSize(new Dimension(150, 35));
        btnSeparar.addActionListener(e -> iniciarSeparacion());

        btnSeleccionarTodo = new BotonCute("Seleccionar todo");
        btnSeleccionarTodo.setPreferredSize(new Dimension(150, 35));
        btnSeleccionarTodo.addActionListener(e -> seleccionarTodo());

        btnDesmarcar = new BotonCute("Desmarcar");
        btnDesmarcar.setPreferredSize(new Dimension(150, 35));
        btnDesmarcar.addActionListener(e -> desmarcarSeleccion());
        
        btnLimpiar= new BotonCute("Limpair");
        btnLimpiar.setPreferredSize(new Dimension(150,35));
        btnLimpiar.addActionListener(e ->limpiarTodo());

        panelDerecho.add(btnBuscarPDF);
        panelDerecho.add(btnSeparar);
        panelDerecho.add(btnSeleccionarTodo);
        panelDerecho.add(btnLimpiar);
        panelDerecho.add(btnDesmarcar);

        lblMensaje = new JLabel("<html><center>Cargando<br>vista previa...</center></html>");
        lblMensaje.setForeground(ColoresCute.TEXTO_SUAVE);
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

        principal.add(panelTop, BorderLayout.NORTH);
        principal.add(scroll, BorderLayout.CENTER);
        principal.add(panelDerechoContenedor, BorderLayout.EAST);

        setContentPane(principal);
    }

    private void limpiarTodo() {

            if (checks.isEmpty()) {
                lblMensaje.setText("<html><center>No hay vistas<br>para limpiar</center></html>");
                return;
            }

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Deseas quitar todas las vistas previas?",
                    "Limpiar vistas",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (opcion != JOptionPane.YES_OPTION) {
                return;
            }

            grid.removeAll();
            checks.clear();
            archivo = null;

            grid.revalidate();
            grid.repaint();

            scroll.revalidate();
            scroll.repaint();

            lblMensaje.setText("<html><center>Vistas<br>limpiadas</center></html>");

    }

    private void iniciarCargaPreview() {
        barraCarga.setVisible(true);
        lblMensaje.setText("<html><center>Generando<br>vistas previas...</center></html>");

        SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>() {
            @Override
            protected Integer doInBackground() throws Exception {
                return generarPreviews();
            }

            @Override
            protected void done() {
                barraCarga.setVisible(false);

                try {
                    int totalPaginas = get();
                    cargarCards(totalPaginas);
                    lblMensaje.setText("<html><center>Páginas cargadas:<br>" + totalPaginas + "</center></html>");
                } catch (Exception e) {
                    lblMensaje.setText("<html><center>Error al cargar<br>vista previa</center></html>");

                    JOptionPane.showMessageDialog(
                            separarPDF.this,
                            "No se pudieron generar las vistas previas:\n" + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    private int generarPreviews() throws IOException {
        File carpeta = new File(carpetaPreview);

        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        ProcessBuilder pb = new ProcessBuilder(
                "python",
                "src/main/resources/python/separar_pdf.py",
                archivo,
                carpetaPreview
        );

        pb.redirectErrorStream(true);
        Process process = pb.start();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(process.getInputStream())
        );

        String totalPaginas = null;
        String linea;

        while ((linea = br.readLine()) != null) {
            System.out.println("PYTHON SEPARAR::: " + linea);

            if (linea.matches("\\d+")) {
                totalPaginas = linea;
            }
        }

        try {
            int codigoSalida = process.waitFor();
            System.out.println("CÓDIGO PYTHON SEPARAR: " + codigoSalida);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        if (totalPaginas == null) {
            throw new RuntimeException("Python no devolvió el total de páginas");
        }

        return Integer.parseInt(totalPaginas);
    }

    private void cargarCards(int totalPaginas) {
        grid.removeAll();
        checks.clear();

        int columnas = 3;
        int filas = (int) Math.ceil(totalPaginas / 3.0);

        grid.setLayout(new GridLayout(filas, columnas, 20, 20));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 1; i <= totalPaginas; i++) {
            crearCardPagina(i);
        }

        grid.revalidate();
        grid.repaint();
        scroll.revalidate();
        scroll.repaint();
    }

    private void crearCardPagina(int numeroPagina) {
        JCheckBox check = new JCheckBox("Página " + numeroPagina);
        check.setForeground(ColoresCute.TEXTO);
        check.setOpaque(false);
        checks.add(check);

        String rutaImagen = carpetaPreview + "\\pagina_" + numeroPagina + ".png";

        ImageIcon icon = new ImageIcon(rutaImagen);
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
    }

    private void iniciarSeparacion() {
        String paginas = obtenerPaginasSeleccionadas();

        if (paginas.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione al menos una página",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        barraCarga.setVisible(true);
        lblMensaje.setText("<html><center>Separando<br>PDF...</center></html>");

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                crearPDFSeleccionado(paginas);
                return null;
            }

            @Override
            protected void done() {
                barraCarga.setVisible(false);

                try {
                    get();

                    lblMensaje.setText("<html><center>PDF separado<br>correctamente</center></html>");

                    JOptionPane.showMessageDialog(
                            separarPDF.this,
                            "PDF separado correctamente",
                            "Proceso finalizado",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                } catch (Exception e) {
                    lblMensaje.setText("<html><center>Error al<br>separar PDF</center></html>");

                    JOptionPane.showMessageDialog(
                            separarPDF.this,
                            "Ocurrió un error al separar el PDF:\n" + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    public void crearPDFSeleccionado(String paginas) throws IOException {
        String salida = "C:\\Users\\zindi\\Documents\\convertirArchivosPDF\\pdf_separado.pdf";

        ProcessBuilder pb = new ProcessBuilder(
                "python",
                "src/main/resources/python/separar_seleccion.py",
                archivo,
                salida,
                paginas
        );

        pb.redirectErrorStream(true);
        Process process = pb.start();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(process.getInputStream())
        );

        String linea;

        while ((linea = br.readLine()) != null) {
            System.out.println("PYTHON PDF::: " + linea);
        }

        try {
            int codigoSalida = process.waitFor();
            System.out.println("CÓDIGO PYTHON SELECCIÓN: " + codigoSalida);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
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

    private void seleccionarTodo() {
        for (JCheckBox check : checks) {
            check.setSelected(true);
        }

        lblMensaje.setText("<html><center>Todas las páginas<br>seleccionadas</center></html>");
    }

    private void desmarcarSeleccion() {
        for (JCheckBox check : checks) {
            check.setSelected(false);
        }

        lblMensaje.setText("<html><center>Selección<br>desmarcada</center></html>");
    }
    private void buscarPDFDesdeSeparar() {
        JFileChooser chooser = new JFileChooser();

        FileNameExtensionFilter filtroPDF = new FileNameExtensionFilter(
                "Archivos PDF (*.pdf)",
                "pdf"
        );

        chooser.setFileFilter(filtroPDF);
        chooser.setAcceptAllFileFilterUsed(false);

        int resultado = chooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = chooser.getSelectedFile();

            if (!archivoSeleccionado.getName().toLowerCase().endsWith(".pdf")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Solo puedes seleccionar archivos PDF.",
                        "Archivo no permitido",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            this.archivo = archivoSeleccionado.getAbsolutePath();

            grid.removeAll();
            checks.clear();

            grid.revalidate();
            grid.repaint();

            iniciarCargaPreview();
        }
    }
    private void aplicarTema() {
        if (principal != null) principal.setBackground(Temas.actual.fondo);
        if (grid != null) grid.setBackground(Temas.actual.fondo);

        if (scroll != null) {
            scroll.setBorder(BorderFactory.createLineBorder(Temas.actual.borde));
        }

        if (panelDerecho != null) {
            panelDerecho.setBackground(Temas.actual.fondoPanel);
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
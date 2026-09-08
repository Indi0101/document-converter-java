package org.example;
import com.formdev.flatlaf.FlatDarkLaf;
import com.kitfox.svg.app.beans.SVGIcon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;

public class convertir_archivos_pdf extends JFrame {
   private JButton btn_buscar_archivo,
                   btn_separar,btn_unir,
                   btn_convertir;

    private JTextField ruta_archivo;

    private JLabel tipo_archivo,
                   lb_icono,
                   lb_estado;

    private String [] PDF_to= {"JPG","PNG"};
    private String [] pdf={"PDF"};

    private String obtener_tipo_archivo,
            nombre_archivo_salida;

    private ComboBoxClase convertir_a;

    private JPanel principal,
            panel1,
            panel2,
            panelIzquierdo,
            panelDerecho,
            panelTipoArchivo,
            panelIcono,
            panelCombo,
            espacioIzquierdo,
            panelConetedorIz,
            panelCarga,
            panelTop;

    private JProgressBar barraCarga;

    public unirPDF unirPDF;
    public separarPDF separarPDF;
    private PanelTema panelTema;

    convertir_archivos_pdf(){
        setTitle("Convertir PDF");
        setLocation(420, 15);
        setSize(700,260);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        principal = new JPanel(new BorderLayout(5, 5));
        principal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        principal.setBackground(Temas.actual.fondo);

        panelTop = new JPanel(new BorderLayout());
        panelTema= new PanelTema(()->aplicarTema());
        panelTop.add(panelTema, BorderLayout.CENTER);

        panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new GridLayout(3,1,0,10));
        panelDerecho= new JPanel();
        panelDerecho.setLayout(new GridLayout(4,1,0,10));
        panelDerecho.setPreferredSize(new Dimension(115,120));

        panel1= new JPanel(new BorderLayout(10,0));
        btn_buscar_archivo = new BotonCute("");
        try {
            URL url = Main.class.getResource("/iconos/search.svg");

            SVGIcon iconoBuscar = new SVGIcon();
            iconoBuscar.setSvgURI(url.toURI());
            iconoBuscar.setPreferredSize(new Dimension(18, 18));

            btn_buscar_archivo.setIcon(iconoBuscar);
            btn_buscar_archivo.setHorizontalAlignment(SwingConstants.CENTER);
            btn_buscar_archivo.setVerticalAlignment(SwingConstants.CENTER);

        } catch (Exception e) {
            e.printStackTrace();
            btn_buscar_archivo.setText("...");
        }
        btn_buscar_archivo.setPreferredSize(new Dimension(45, 36));
        btn_buscar_archivo.setHorizontalAlignment(SwingConstants.CENTER);
        btn_buscar_archivo.setVerticalAlignment(SwingConstants.CENTER);
        btn_buscar_archivo.setHorizontalTextPosition(SwingConstants.CENTER);
        btn_buscar_archivo.setVerticalTextPosition(SwingConstants.CENTER);
        btn_buscar_archivo.setIconTextGap(0);
        btn_buscar_archivo.setMargin(new Insets(0, 0, 0, 0));
        btn_buscar_archivo.setBorder(BorderFactory.createEmptyBorder());
        btn_buscar_archivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               try {
                   cargarArchivo();
               } catch (Exception ex) {
                   throw new RuntimeException(ex);
               }

            }
        });

        ruta_archivo = new textFieldClase("Selecciona un archivo...");
        ruta_archivo.setPreferredSize(new Dimension(450,35));
        ruta_archivo.setFont(new Font("Arial", Font.PLAIN, 14));
        if (ruta_archivo instanceof textFieldClase) {
            ((textFieldClase) ruta_archivo).aplicarTema();
        }

        panel1.add(btn_buscar_archivo,BorderLayout.WEST);
        panel1.add(ruta_archivo,BorderLayout.CENTER);
        panelConetedorIz = new JPanel(new BorderLayout(10, 0));
        espacioIzquierdo = new JPanel();
        espacioIzquierdo.setPreferredSize(new Dimension(45, 5));
        panel2=new JPanel(new BorderLayout(0,0));

        panelTipoArchivo = new JPanel(new GridBagLayout());
        panelIcono = new JPanel(new GridBagLayout());
        panelCombo = new JPanel(new GridBagLayout());

        tipo_archivo = new LabelClase("....");
        tipo_archivo.setFont(new Font("Arial", Font.PLAIN, 20));
        tipo_archivo.setPreferredSize(new Dimension(160, 35));
        if (tipo_archivo instanceof LabelClase) {
            ((LabelClase) tipo_archivo).aplicarTema();
        }

        convertir_a = new ComboBoxClase(pdf);
        convertir_a.setPreferredSize(new Dimension(157, 35));


        try {
            URL url = Main.class.getResource("/iconos/arrow-right.svg");
            SVGIcon svgIcon = new SVGIcon();
            svgIcon.setSvgURI(url.toURI());
            svgIcon.setPreferredSize(new java.awt.Dimension(30, 30));
            lb_icono = new JLabel(svgIcon);
            lb_icono.setPreferredSize(new Dimension(60,35));

        } catch (Exception e) {
            e.printStackTrace();
            lb_icono = new JLabel("→", SwingConstants.CENTER);
            lb_icono.setFont(new Font("Arial", Font.BOLD, 28));
        }

        panelTipoArchivo.setOpaque(false);
        panelIcono.setOpaque(false);
        espacioIzquierdo.setOpaque(false);
        panel2.setOpaque(false);
        panelDerecho.setOpaque(false);
        panel1.setOpaque(false);
        panelConetedorIz.setOpaque(false);
        panelIzquierdo.setOpaque(false);
        panelCombo.setOpaque(false);

        panelTipoArchivo.add(tipo_archivo);
        panelIcono.add(lb_icono);
        panelCombo.add(convertir_a);

        panel2.add(panelTipoArchivo, BorderLayout.WEST);
        panel2.add(panelIcono, BorderLayout.CENTER);
        panel2.add(panelCombo,BorderLayout.EAST);

        panelCarga = new JPanel(new BorderLayout(10, 0));
        panelCarga.setOpaque(false);
        lb_estado = new JLabel("");
        lb_estado.setFont(new Font("Arial", Font.PLAIN, 12));
        barraCarga = new JProgressBar();
        barraCarga.setIndeterminate(true);
        barraCarga.setVisible(false);
        barraCarga.setForeground(Color.decode("#D87C7C"));
        barraCarga.setBackground(Color.decode("#3E4444"));
        barraCarga.putClientProperty("JProgressBar.largeHeight", true);
        barraCarga.putClientProperty("JComponent.roundRect", true);
        panelCarga.add(lb_estado, BorderLayout.WEST);
        panelCarga.add(barraCarga, BorderLayout.CENTER);

        panelConetedorIz.add(espacioIzquierdo, BorderLayout.WEST);
        panelConetedorIz.add(panel2, BorderLayout.CENTER);

        btn_convertir = new BotonCute("Convertir");
        btn_convertir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarConversion();
            }
        });
        btn_separar = new BotonCute("Separar PDF");
        btn_separar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    enviarArchivoNewPanel(1);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        btn_unir = new BotonCute("Unir PDF");
        btn_unir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    enviarArchivoNewPanel(2);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        efectoEstadoBoton(btn_convertir, "Convertir archivo seleccionado");
        efectoEstadoBoton(btn_separar, "Separar páginas de un PDF");
        efectoEstadoBoton(btn_unir, "Unir varios archivos PDF");
        efectoEstadoBoton(btn_buscar_archivo, "Buscar archivo en tu equipo");

        panelDerecho.add(btn_convertir);
        panelDerecho.add(btn_separar);
        panelDerecho.add(btn_unir);
        panelIzquierdo.add(panel1);
        panelIzquierdo.add(panelConetedorIz);
        panelIzquierdo.add(panelCarga);

        principal.add(panelTop,BorderLayout.NORTH);
        principal.add(panelIzquierdo, BorderLayout.CENTER);
        principal.add(panelDerecho, BorderLayout.EAST);

        add(principal);
        setVisible(true);
    }
    private void enviarArchivoNewPanel(int i) {
        if(i==1)
        {
            if (Objects.equals(tipo_archivo.getText(), ".pdf")) {
                System.out.println("Archivo para SEPARAR: "+ruta_archivo.getText());
                ventanaSepararPDF(ruta_archivo.getText());
            }
            else if(Objects.equals(obtener_tipo_archivo,null)) {
               ventanaSepararPDF("");
            }{ System.out.println("MENSAJE:::NO ES PDF: "+tipo_archivo.getText());}
        }
        if (i==2)
        {
            if (Objects.equals(tipo_archivo.getText(), ".pdf")) {
                ventanaUnirPDF(ruta_archivo.getText());
            }
            else if(Objects.equals(obtener_tipo_archivo,null)) {
                ventanaUnirPDF("");
            }{ System.out.println("MENSAJE:::NO ES PDF");}

        }
        }
    public void cargarArchivo() throws IOException {
 //Seleccionar el archvio
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(null);
        if(resultado==JFileChooser.APPROVE_OPTION)
        {
            File archivo = fileChooser.getSelectedFile();
            System.out.println("Archivo seleccionado: " + archivo.getAbsolutePath());
            ruta_archivo.setText( archivo.getAbsolutePath());
     //Obtener el tipo de archivo ejemplo .word
           nombre_archivo_salida = archivo.getName();
           String extencion ;

            String tipo = Files.probeContentType(archivo.toPath());
            System.out.println("Tipo MIME: " + tipo);
           int i = nombre_archivo_salida.lastIndexOf(".");
            if (i > 0) {
                extencion = nombre_archivo_salida.substring(i + 1); // ej: "txt", "mp3"
                tipo_archivo.setText("."+extencion);
                llenarConboBox();
            }
        }
    }
    private void llenarConboBox() {
        convertir_a.removeAllItems();
        switch (tipo_archivo.getText()){
            case ".pdf":
                for(String formato: PDF_to){
                    convertir_a.addItem(formato);
                }
                obtener_tipo_archivo="pdf";
            case ".jpg":
                convertir_a.addItem("PDF");
                convertir_a.addItem("PNG");
                obtener_tipo_archivo="imagen";
                break;
            case ".png":
                convertir_a.addItem("PDF");
                convertir_a.addItem("JPG");
                obtener_tipo_archivo="imagen";
                break;
            case ".docx":
                convertir_a.addItem("PDF");
                obtener_tipo_archivo="word";
                break;
            case ".xlsx":
                convertir_a.addItem("PDF");
                obtener_tipo_archivo="excel";
                break;
            case ".pptx":
                convertir_a.addItem("PDF");
                obtener_tipo_archivo="ppt";
                break;
        }
    }
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        new convertir_archivos_pdf();
    }
    public void ventanaSepararPDF(String archivo){
            separarPDF= new separarPDF(archivo);
    }
    public void ventanaUnirPDF(String archivo){
            unirPDF = new unirPDF(archivo);
    }
    public void convertir_PDF() throws IOException {

        System.out.println("AVISO:"+nombre_archivo_salida);
        if (nombre_archivo_salida == null || nombre_archivo_salida.isEmpty()) {
            System.out.println("ERROR: nombre_archivo_salida es"+nombre_archivo_salida);
            JOptionPane.showMessageDialog(null,"Debe seleccionar un archivo");
            return;
        }

        String entrada = ruta_archivo.getText();
        String nombre_salida=nombre_archivo_salida.replace(tipo_archivo.getText(),"");
        String salida="C:\\Users\\zindi\\Documents\\convertirArchivosPDF\\"+nombre_salida+".pdf";

        System.out.println("TIPO: " + obtener_tipo_archivo);
        System.out.println("ENTRADA: " + entrada);
        System.out.println("SALIDA: " + salida);

        if (entrada == null) {
            System.out.println("ERROR: Hay un valor null");
            return;
        }
        switch (obtener_item_combo()){
            case "PDF":
                processBuilder(entrada,salida);
                break;
            case "PNG":
                obtener_tipo_archivo="pdf_png";
                processBuilder(entrada,salida);
                break;
            case "JPG":
                obtener_tipo_archivo="pdf_jpg";
                processBuilder(entrada,salida);
                break;
        }
    }
    public String obtener_item_combo(){

           String item = Objects.requireNonNull(convertir_a.getSelectedItem()).toString();
           System.out.println(item);
           return item;
   }
    public void processBuilder(String entrada,String salida)throws IOException{
       ProcessBuilder pb = new ProcessBuilder(
               "python",
               "src/main/resources/python/convertir.py",
               obtener_tipo_archivo,
               ruta_archivo.getText(),
               salida
       );
       pb.redirectErrorStream(true);
       Process process= pb.start();
       BufferedReader br = new BufferedReader(
               new InputStreamReader(process.getInputStream())
       );
       String linea;
       while((linea = br.readLine()) != null){

           System.out.println("PYTHON::: " + linea);
       }
       try {
           int codigoSalida = process.waitFor();
           if (codigoSalida==0)
           {
               ruta_archivo.setText("Seleccione un archivo..");
               nombre_archivo_salida="";
               tipo_archivo.setText(".......");
           }
           System.out.println("CÓDIGO PYTHON: " + codigoSalida);
       } catch (InterruptedException e) {e.printStackTrace();}
   }
    private void iniciarConversion() {

        if (nombre_archivo_salida == null || nombre_archivo_salida.isEmpty()) {
            System.out.println("ERROR: nombre_archivo_salida es"+nombre_archivo_salida);
            JOptionPane.showMessageDialog(null,"Debe seleccionar un archivo");
            lb_estado.setText("Debe seleccionar un archivo...");
            return;
        }
        barraCarga.setVisible(true);
        lb_estado.setText("Convirtiendo archivo...");

        btn_convertir.setEnabled(false);
        btn_separar.setEnabled(false);
        btn_unir.setEnabled(false);
        btn_buscar_archivo.setEnabled(false);

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                convertir_PDF();
                return null;
            }

            @Override
            protected void done() {
                barraCarga.setVisible(false);
                lb_estado.setText("Conversión finalizada");

                btn_convertir.setEnabled(true);
                btn_separar.setEnabled(true);
                btn_unir.setEnabled(true);
                btn_buscar_archivo.setEnabled(true);

                JOptionPane.showMessageDialog(
                        null,
                        "Archivo convertido correctamente",
                        "Conversión finalizada",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        };

        worker.execute();
    }
    private void efectoEstadoBoton(JButton boton, String mensaje) {
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lb_estado.setText(mensaje);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lb_estado.setText("");
            }
        });
    }
    private void aplicarTema() {
        if (principal != null) principal.setBackground(Temas.actual.fondo);
        if (panelIzquierdo != null) panelIzquierdo.setBackground(Temas.actual.fondo);
        if (panelDerecho != null) panelDerecho.setBackground(Temas.actual.fondoPanel);
        if (panel1 != null) panel1.setOpaque(false);
        if (panel2 != null) panel2.setOpaque(false);

        if (ruta_archivo instanceof textFieldClase) {
            ((textFieldClase) ruta_archivo).aplicarTema();
        } else {
            ruta_archivo.setBackground(Temas.actual.textFieldFondo);
            ruta_archivo.setForeground(Temas.actual.textFieldTexto);
            ruta_archivo.setCaretColor(Temas.actual.textFieldCaret);
            ruta_archivo.setBorder(BorderFactory.createLineBorder(Temas.actual.textFieldBorde));
        }

        if (tipo_archivo instanceof LabelClase) {
            ((LabelClase) tipo_archivo).aplicarTema();
        } else {
            tipo_archivo.setBackground(Temas.actual.labelFondo);
            tipo_archivo.setForeground(Temas.actual.labelTexto);
            tipo_archivo.setBorder(BorderFactory.createLineBorder(Temas.actual.labelBorde));
        }

        if (convertir_a != null) {
            convertir_a.aplicarTema();
        }

        if (barraCarga != null) {
            barraCarga.setForeground(Temas.actual.botonHover);
            barraCarga.setBackground(Temas.actual.fondoCampo);
        }

        if (lb_estado != null) {
            lb_estado.setForeground(Temas.actual.textoSuave);
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



# 📄 Document Converter

Aplicación de escritorio desarrollada principalmente en **Java**, con integración de **Python**, para convertir y gestionar diferentes tipos de documentos desde una única interfaz gráfica.

El proyecto combina una interfaz desarrollada con **Java Swing** con scripts especializados en Python para realizar operaciones de conversión, unión, separación y procesamiento de archivos.

Además de las conversiones, incluye herramientas visuales para trabajar con archivos PDF, vistas previas de documentos, selección de páginas, procesamiento en segundo plano y diferentes temas de interfaz.

---

## ✨ Funcionalidades principales

### 🔄 Conversión de archivos

La aplicación detecta automáticamente la extensión del archivo seleccionado y muestra las opciones de conversión disponibles.

Actualmente contempla conversiones entre:

| Archivo de entrada | Conversión disponible |
|---|---|
| PDF | JPG / PNG |
| JPG | PDF / PNG |
| PNG | PDF / JPG |
| DOCX | PDF |
| XLSX | PDF |
| PPTX | PDF |

El archivo se selecciona mediante el explorador de archivos de Java y la aplicación adapta las opciones de conversión según su extensión.

---

## 📑 Separar archivos PDF

La aplicación incluye una ventana específica para dividir documentos PDF.

Entre sus funciones se encuentran:

- Seleccionar un archivo PDF.
- Generar automáticamente una vista previa de cada página.
- Mostrar las páginas como tarjetas individuales.
- Seleccionar páginas específicas.
- Seleccionar todas las páginas.
- Desmarcar la selección.
- Limpiar las vistas cargadas.
- Crear un nuevo PDF utilizando únicamente las páginas seleccionadas.
- Mostrar el estado del procesamiento mediante una barra de progreso.
- Validar que exista al menos una página seleccionada.

Las vistas previas son generadas mediante **PyMuPDF**, mientras que la creación del nuevo documento se realiza con **pypdf**.

---

## 📚 Unir archivos PDF

También incluye una herramienta independiente para combinar varios documentos PDF.

Permite:

- Seleccionar múltiples archivos PDF desde el explorador.
- Validar que los archivos seleccionados sean PDF.
- Generar una vista previa de cada documento.
- Mostrar cada archivo como una tarjeta seleccionable.
- Eliminar documentos de la selección.
- Seleccionar qué documentos se desean combinar.
- Unir todos los documentos cargados cuando no existe una selección específica.
- Validar que existan al menos dos archivos antes de realizar la unión.
- Mostrar mensajes de estado y progreso durante el proceso.

La unión de documentos se realiza utilizando **pypdf**.

---

## 🖼️ Conversión entre imágenes y PDF

El proyecto utiliza **Pillow** para convertir imágenes a PDF.

Durante la conversión:

- Se comprueba que el archivo exista.
- Las imágenes son convertidas a formato RGB.
- Las imágenes con transparencia reciben un fondo blanco antes de crear el PDF.
- Se genera automáticamente el archivo de salida.

También utiliza **PyMuPDF** para recorrer las páginas de un PDF y exportarlas individualmente como:

- JPG
- PNG

Los archivos generados se numeran automáticamente según la página del documento.

---

## 📊 Conversión de documentos de Office

La aplicación integra automatización de Microsoft Office mediante Python.

### Excel → PDF

Utiliza `win32com.client` para:

1. Abrir Microsoft Excel.
2. Cargar el libro seleccionado.
3. Exportarlo como PDF.
4. Cerrar el documento.
5. Finalizar la instancia de Excel.

### PowerPoint → PDF

Utiliza el mismo mecanismo de automatización para:

1. Abrir PowerPoint.
2. Cargar la presentación.
3. Exportarla en formato PDF.
4. Cerrar la presentación.
5. Finalizar PowerPoint.

El proyecto también contempla el flujo de conversión:

**Word (DOCX) → PDF**

dentro del controlador principal de conversiones.

---

## 🔗 Integración Java + Python

Una de las características principales del proyecto es la integración entre ambos lenguajes.

### Java

Se encarga principalmente de:

- Interfaz gráfica.
- Selección de archivos.
- Validaciones.
- Control de ventanas.
- Selección de operaciones.
- Barras de progreso.
- Mensajes de estado.
- Gestión de temas.
- Ejecución y coordinación de los procesos.

### Python

Se utiliza para realizar el procesamiento específico de documentos:

- Conversión de archivos.
- Procesamiento de PDF.
- Creación de imágenes de vista previa.
- Extracción de páginas.
- Unión de documentos.
- Automatización de aplicaciones Microsoft Office.

Java ejecuta los scripts mediante:

```text
ProcessBuilder
       │
       ▼
Python
       │
       ▼
Procesamiento del documento
       │
       ▼
Archivo resultante
```

---

## ⚙️ Procesamiento en segundo plano

Las operaciones que pueden requerir mayor tiempo de ejecución utilizan `SwingWorker`.

Esto permite realizar conversiones y procesamiento de documentos sin ejecutar directamente las tareas pesadas sobre el hilo principal de la interfaz.

Durante estos procesos la aplicación:

- Muestra una barra de progreso.
- Actualiza mensajes de estado.
- Deshabilita temporalmente determinados controles.
- Reactiva los controles al finalizar.
- Informa al usuario cuando la operación termina.

---

## 🎨 Sistema de temas

La aplicación incorpora un sistema dinámico de personalización visual.

Actualmente dispone de cuatro temas:

- 💗 **Cute**
- 🌙 **Oscuro**
- ☀️ **Claro**
- 💼 **Serio**

Cada tema modifica diferentes propiedades de la interfaz:

- Fondo principal.
- Paneles.
- Campos de texto.
- Bordes.
- Botones.
- Estados hover.
- Estados click.
- Etiquetas.
- Texto principal y secundario.

Los cambios se aplican dinámicamente sobre los componentes de las diferentes ventanas.

---

## 🖌️ Componentes personalizados

En lugar de utilizar únicamente los componentes visuales predeterminados de Swing, el proyecto implementa varios componentes personalizados.

### `BotonCute`

Componente basado en `JButton` con:

- Bordes redondeados.
- Antialiasing.
- Cambio de color al pasar el cursor.
- Estado visual al hacer clic.
- Cursor interactivo.
- Integración con el sistema de temas.

### `ComboBoxClase`

Componente personalizado basado en `JComboBox` con:

- Fondo redondeado.
- Bordes personalizados.
- Flecha dibujada manualmente.
- Colores dinámicos.
- Integración con los temas.

### `textFieldClase`

Campo de texto personalizado con:

- Fondo redondeado.
- Bordes personalizados.
- Color del cursor dinámico.
- Cambio automático según el tema.

### `LabelClase`

Etiqueta personalizada con:

- Fondo redondeado.
- Bordes personalizados.
- Colores asociados al tema activo.

---

## 🧩 Interfaz gráfica

La interfaz principal está desarrollada con **Java Swing**.

También utiliza:

- **FlatLaf** para mejorar la apariencia de los componentes Swing.
- Iconos en formato **SVG**.
- `JFileChooser` para seleccionar archivos.
- `JProgressBar` para mostrar procesos activos.
- `JOptionPane` para alertas y mensajes.
- `JScrollPane` para las galerías de vistas previas.
- `GridLayout`, `FlowLayout` y `BorderLayout` para organizar la interfaz.

---

## 🛠️ Tecnologías utilizadas

### Java

- Java
- Java Swing
- Java AWT
- SwingWorker
- ProcessBuilder
- Java IO
- Java Collections
- NIO Files
- Maven

### Python

- Python 3
- PyMuPDF (`fitz`)
- pypdf
- Pillow (`PIL`)
- pywin32 / `win32com.client`

### Interfaz

- FlatLaf
- SVG Icons
- Componentes Swing personalizados

### Herramientas

- IntelliJ IDEA
- Git
- GitHub

---

## 🏗️ Arquitectura general

```text
┌──────────────────────────────┐
│       Java Swing UI          │
│                              │
│ Selección de archivos        │
│ Validaciones                 │
│ Conversión                   │
│ Unión / separación PDF      │
│ Temas y estados              │
└───────────────┬──────────────┘
                │
                │ ProcessBuilder
                ▼
┌──────────────────────────────┐
│        Python Scripts        │
│                              │
│ convertir.py                 │
│ excel_pdf.py                 │
│ imagen_pdf.py                │
│ pdf_imagen.py                │
│ ppt_pdf.py                   │
│ separar_pdf.py               │
│ separar_seleccion.py         │
│ unir_pdf.py                  │
└───────────────┬──────────────┘
                │
                ▼
┌──────────────────────────────┐
│     Archivo resultante       │
│                              │
│ PDF / JPG / PNG              │
└──────────────────────────────┘
```

---

## 📁 Estructura principal del proyecto

```text
src/
└── main/
    ├── java/
    │   └── org/example/
    │       ├── Main.java
    │       ├── convertir_archivos_pdf.java
    │       ├── unirPDF.java
    │       ├── separarPDF.java
    │       ├── BotonCute.java
    │       ├── ComboBoxClase.java
    │       ├── LabelClase.java
    │       ├── textFieldClase.java
    │       ├── PanelTema.java
    │       ├── TemaApp.java
    │       └── Temas.java
    │
    └── resources/
        ├── iconos/
        └── python/
            ├── convertir.py
            ├── excel_pdf.py
            ├── imagen_pdf.py
            ├── pdf_imagen.py
            ├── ppt_pdf.py
            ├── separar_pdf.py
            ├── separar_seleccion.py
            └── unir_pdf.py
```

---

## 💡 Conceptos aplicados

Durante el desarrollo del proyecto se trabajó con:

- Programación orientada a objetos.
- Desarrollo de aplicaciones de escritorio.
- Diseño de interfaces con Swing.
- Herencia de componentes Swing.
- Manejo de eventos.
- Procesamiento en segundo plano.
- Manejo de archivos.
- Validación de entradas.
- Integración entre Java y Python.
- Ejecución de procesos externos.
- Colecciones.
- Automatización de aplicaciones de escritorio.
- Procesamiento de archivos PDF.
- Personalización visual de componentes.
- Gestión de estados de interfaz.

---

## 💻 Requisitos

Para ejecutar todas las funcionalidades se requiere:

- Java JDK
- Maven
- Python 3

Dependencias Python utilizadas por las funciones revisadas:

```bash
pip install pymupdf pypdf pillow pywin32
```

Las conversiones de Excel y PowerPoint utilizan automatización COM, por lo que actualmente están orientadas a **Windows** y requieren tener instaladas las aplicaciones correspondientes de Microsoft Office.

---

## 📌 Estado del proyecto

Proyecto completado y funcional desarrollado como aplicación de escritorio para la conversión y procesamiento de documentos.

La aplicación integra **Java y Python** para ofrecer desde una única interfaz funciones de conversión de archivos, generación de vistas previas, separación de páginas y unión de documentos PDF.
----

## 👩‍💻 Autora

Desarrollado por **Indira** como parte de mi portafolio de desarrollo de software.

GitHub: [@Indi0101](https://github.com/Indi0101)

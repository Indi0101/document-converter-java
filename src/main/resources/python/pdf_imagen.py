import fitz
import os


def convertir_pdf_imagen(
        entrada,
        carpeta_salida,
        formato
):

    os.makedirs(carpeta_salida, exist_ok=True)

    pdf = fitz.open(entrada)

    for i, pagina in enumerate(pdf):

        pix = pagina.get_pixmap()

        salida = os.path.join(
            carpeta_salida,
            f"pagina_{i + 1}.{formato}"
        )

        pix.save(salida)

    pdf.close()

    print("OK")
    print(carpeta_salida)
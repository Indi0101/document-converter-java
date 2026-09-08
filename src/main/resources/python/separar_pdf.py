import fitz
import sys

def generar_preview_pdf(entrada, carpeta_salida):

    pdf = fitz.open(entrada)

    total_paginas = len(pdf)

    print("TOTAL")
    print(total_paginas)

    for i, pagina in enumerate(pdf):

        pix = pagina.get_pixmap()

        pix.save(
            f"{carpeta_salida}/pagina_{i+1}.png"
        )

    pdf.close()


if __name__ == "__main__":

    entrada = sys.argv[1]
    carpeta_salida = sys.argv[2]

    generar_preview_pdf(
        entrada,
        carpeta_salida
    )
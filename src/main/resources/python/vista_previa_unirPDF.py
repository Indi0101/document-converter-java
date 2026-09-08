import fitz
import sys
import os


def generar_preview_primera_pagina(entrada, salida_preview):

    os.makedirs(
        os.path.dirname(os.path.abspath(salida_preview)),
        exist_ok=True
    )

    pdf = fitz.open(entrada)

    pagina = pdf[0]

    pix = pagina.get_pixmap(
        matrix=fitz.Matrix(0.5, 0.5),
        alpha=False
    )

    pix.save(salida_preview)

    pdf.close()

    print("OK")
    print(salida_preview)


if __name__ == "__main__":

    entrada = sys.argv[1]
    salida_preview = sys.argv[2]

    generar_preview_primera_pagina(
        entrada,
        salida_preview
    )
import sys

from word_pdf import convertir_word_pdf
from excel_pdf import convertir_excel_pdf
from ppt_pdf import convertir_ppt_pdf
from imagen_pdf import convertir_imagen_pdf
from pdf_imagen import convertir_pdf_imagen


if __name__ == "__main__":

    if len(sys.argv) != 4:
        print("ERROR")
        print("Uso incorrecto")
        sys.exit(1)

    tipo = sys.argv[1]
    entrada = sys.argv[2]
    salida = sys.argv[3]

    try:
        match tipo:
            case "word":
                convertir_word_pdf(entrada, salida)

            case "excel":
                convertir_excel_pdf(entrada, salida)

            case "ppt":
                convertir_ppt_pdf(entrada, salida)

            case "imagen":
                convertir_imagen_pdf(entrada, salida)

            case "pdf_jpg":
                convertir_pdf_imagen(entrada,salida,"jpg")

            case "pdf_png":
                convertir_pdf_imagen(entrada,salida,"png")

            case _:
                print("ERROR")
                print("Tipo no soportado: " + tipo)

    except Exception as e:
        print("ERROR")
        print(str(e))
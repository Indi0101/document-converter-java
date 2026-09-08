import sys
from pypdf import PdfReader, PdfWriter

def crear_pdf_seleccionado(entrada, salida, paginas_texto):

    reader = PdfReader(entrada)
    writer = PdfWriter()

    paginas = paginas_texto.split(",")

    for p in paginas:
        numero = int(p) - 1
        writer.add_page(reader.pages[numero])

    with open(salida, "wb") as archivo_salida:
        writer.write(archivo_salida)

    print("OK")
    print(salida)


if __name__ == "__main__":

    entrada = sys.argv[1]
    salida = sys.argv[2]
    paginas_texto = sys.argv[3]

    crear_pdf_seleccionado(entrada, salida, paginas_texto)
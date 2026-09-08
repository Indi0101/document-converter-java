import sys
from pypdf import PdfWriter


def unir_pdfs(salida, archivos):

    writer = PdfWriter()

    for archivo in archivos:
        writer.append(archivo)

    with open(salida, "wb") as archivo_salida:
        writer.write(archivo_salida)

    writer.close()

    print("OK")
    print(salida)


if __name__ == "__main__":

    if len(sys.argv) < 4:
        print("ERROR")
        print("Debe enviar salida y al menos 2 PDFs")
        sys.exit(1)

    salida = sys.argv[1]
    archivos = sys.argv[2:]

    unir_pdfs(salida, archivos)
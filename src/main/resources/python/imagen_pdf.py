from PIL import Image
import os

def convertir_imagen_pdf(entrada, salida):

    if not os.path.exists(entrada):
        print("ERROR")
        print("La imagen no existe")
        return

    if not salida.lower().endswith(".pdf"):
        salida = salida + ".pdf"

    imagen = Image.open(entrada)

    if imagen.mode in ("RGBA", "LA"):
        fondo = Image.new("RGB", imagen.size, "white")
        fondo.paste(imagen, mask=imagen.split()[-1])
        imagen = fondo
    else:
        imagen = imagen.convert("RGB")

    imagen.save(salida, "PDF", resolution=100.0)

    print("OK")
    print(salida)
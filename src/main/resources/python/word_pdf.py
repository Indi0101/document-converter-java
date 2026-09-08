from docx2pdf import convert

def convertir_word_pdf(entrada, salida):

    convert(entrada, salida)

    print("OK")
    print(salida)
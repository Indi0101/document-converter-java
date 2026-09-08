import win32com.client


def convertir_ppt_pdf(entrada, salida):

    powerpoint = win32com.client.Dispatch("PowerPoint.Application")

    presentation = powerpoint.Presentations.Open(entrada)

    presentation.SaveAs(salida, 32)

    presentation.Close()

    powerpoint.Quit()

    print("OK")
    print(salida)
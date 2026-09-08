import win32com.client

def convertir_excel_pdf(entrada, salida):

    excel = win32com.client.Dispatch("Excel.Application")

    excel.Visible = False

    workbook = excel.Workbooks.Open(entrada)

    workbook.ExportAsFixedFormat(0, salida)

    workbook.Close(False)

    excel.Quit()

    print("OK")
    print(salida)
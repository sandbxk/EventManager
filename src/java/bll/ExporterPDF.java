package bll;

import bll.interfaces.IExporter;
import bll.interfaces.IPrintable;

public class ExporterPDF implements IExporter
{
    @Override
    public void toFile(IPrintable print)
    {
        Object pdfDoc = null;

        print.print(pdfDoc);
    }

    @Override
    public void toPrint(IPrintable print)
    {

    }
}

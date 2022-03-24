package bll;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

import java.io.*;

public class ExporterPDF
{
    public void export() throws IOException
    {
        File htmlSource = new File("input.html");
        File pdfDest = new File("output.pdf");

        // pdfHTML specific code
        ConverterProperties converterProperties = new ConverterProperties();
        HtmlConverter.convertToPdf(new FileInputStream(htmlSource),
                new FileOutputStream(pdfDest), converterProperties);
    }
}

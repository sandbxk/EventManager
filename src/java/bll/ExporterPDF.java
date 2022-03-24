package bll;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import j2html.TagCreator;

import java.io.*;
import java.util.stream.Stream;

public class ExporterPDF
{
    public void export() throws IOException
    {
        var html = TagCreator.body(
                TagCreator.h1("Hello, World!")
        ).render();

        File pdfDest = new File("output.pdf");

        // pdfHTML specific code
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setImmediateFlush(true);
        HtmlConverter.convertToPdf(new ByteArrayInputStream(html.getBytes()), new FileOutputStream(pdfDest), converterProperties);
    }

    public static void main(String[] args) throws IOException {
        new ExporterPDF().export();
    }
}

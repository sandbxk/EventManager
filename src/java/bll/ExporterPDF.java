package bll;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import j2html.TagCreator;
import j2html.attributes.Attr;
import j2html.tags.specialized.BodyTag;
import java.io.*;

public class ExporterPDF
{
    public void export() throws IOException
    {
        BodyTag body = TagCreator.body
                (
                        TagCreator.div(
                                TagCreator.h1("Hello, World!"),
                                TagCreator.br(Attr.shortFormFromAttrsString(".class1"))
                        ),
                    TagCreator.div(TagCreator.text("a"))
                );



        File pdfDest = new File("output.pdf");

        // pdfHTML specific code
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setImmediateFlush(true);
        HtmlConverter.convertToPdf(new ByteArrayInputStream(body.render().getBytes()), new FileOutputStream(pdfDest), converterProperties);
    }

    public static void main(String[] args) throws IOException {
        new ExporterPDF().export();
    }
}

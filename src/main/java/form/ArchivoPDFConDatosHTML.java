package form;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

public class ArchivoPDFConDatosHTML {

	public ArchivoPDFConDatosHTML(
	        String ruta,
            String porcionHTML) throws IOException, DocumentException {

		String htmlLiteral =
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <title>All the facts</title>  \n" +
                        "<style type = \"text/css\">\n" +
                        "   <!--\n" +
                        "      @page { size:8.5in 11in; margin-top: 1.8cm; margin-bottom: 2cm; margin-left: 2.5cm; margin-right: 1.5cm; }\n" +
                        "   -->\n" +
                        "</style>"+
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<div style=\"width:100%;height:185px;\"></div>" +
                porcionHTML+
                "</body></html>\n";
        FileUtils.writeByteArrayToFile(new File(ruta), convertirPDF(htmlLiteral));
	}
	
	private byte[] convertirPDF(String htmlLiteral) throws DocumentException, IOException {
        final ITextRenderer renderizador = new ITextRenderer();
        renderizador.setDocumentFromString(htmlLiteral);
        renderizador.layout();
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream(htmlLiteral.length())) {
        	renderizador.createPDF(stream);
            return stream.toByteArray();
        }
    }

}

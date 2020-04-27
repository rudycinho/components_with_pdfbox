package subsistema.pdf.form;

import subsistema.pdf.dto.DictamenDTOPDF;
import subsistema.pdf.dto.SucursalDTOPDF;
import subsistema.pdf.dto.UsuarioDTOPDF;
import subsistema.pdf.dto.PlanDePagosDTOPDF;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import subsistema.pdf.lib.basic.Alignment;
import subsistema.pdf.lib.basic.Style;
import subsistema.pdf.lib.tables.Cell;
import subsistema.pdf.lib.tables.Column;
import subsistema.pdf.lib.tables.RecursiveTable;
import subsistema.pdf.lib.tables.SimpleTable;
import subsistema.pdf.lib.text.simple.MultipleParagraph;
import subsistema.pdf.utils.factories.EasyComponentsFactory;
import subsistema.pdf.utils.factories.GeneradorFormularioFactory;
import subsistema.pdf.utils.settings.*;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class FormularioPlanPagos {

    private final float maxY         = Models.MODEL_2.getMaxY();
    private final float maxX         = Models.MODEL_2.getMaxX();
    private final float thickness    = Models.MODEL_2.getThickness();
    private final float marginStartX = Models.MODEL_2.getMarginStartX();
    private final float marginEndX   = Models.MODEL_2.getMarginEndX();
    private final FontEnum fontEnum  = Models.MODEL_2.getFont();

    private Style fuenteTituloTabla;
    private Style fuenteMinimalEspaciado;
    private Style fuenteMinimalNegrita;
    private Style fuenteMenorEspaciado;
    private Style fuenteMenorNegrita;

    public FormularioPlanPagos(
            String ruta,
            DictamenDTOPDF dictamen,
            PlanDePagosDTOPDF planDePagos,
            UsuarioDTOPDF usuarioEditor,
            SucursalDTOPDF sucursal,
            Date fechaExportacion) throws IOException {

        PDDocument doc = new PDDocument();

        FontGroup fontGroup = FontGroup.getFontGroup(fontEnum,doc);

        fuenteTituloTabla                     = Style.getStyle(fontGroup, StyleEnum.TABLE_BOLD_SPACED);
        fuenteMinimalEspaciado                = Style.getStyle(fontGroup, StyleEnum.TABLE_MINIMAL_NORMAL_SPACED);
        fuenteMenorEspaciado                  = Style.getStyle(fontGroup, StyleEnum.TABLE_LESS_MINIMAL_NORMAL_SPACED);
        fuenteMenorNegrita                    = Style.getStyle(fontGroup, StyleEnum.TABLE_LESS_MINIMAL_BOLD);
        fuenteMinimalNegrita                  = Style.getStyle(fontGroup, StyleEnum.TABLE_MINIMAL_BOLD);

        PDPage page = new PDPage(new PDRectangle(maxX,maxY));

        doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
        List<PDPageContentStream> contentStreams = new LinkedList<>();

        GeneradorFormularioFactory.crearCabecera(contentStream,doc, Models.MODEL_2,  Style.getStyle(fontGroup,StyleEnum.HEADER));
        GeneradorFormularioFactory.crearFechaExportacion(contentStream,fechaExportacion, Models.MODEL_2, Style.getStyle(fontGroup,StyleEnum.FOOTER));
        GeneradorFormularioFactory.crearUsuarioExportador(contentStream,usuarioEditor, Models.MODEL_2,  Style.getStyle(fontGroup,StyleEnum.OWNER_BOLD), Style.getStyle(fontGroup,StyleEnum.OWNER));
        GeneradorFormularioFactory.crearTitulo(contentStream,"PLAN DE PAGOS", Models.MODEL_2,  Style.getStyle(fontGroup,StyleEnum.TITLE));

        crearClienteSeccionPlanDePagos(contentStream, dictamen);
        crearResumenDatosSeccionPlanDePagos(contentStream, dictamen);
        crearDetallesTablePlanDePagos(contentStream,contentStreams,doc,planDePagos);

        int index=1;
        int total=contentStreams.size();
        for(PDPageContentStream contentStream1 : contentStreams){
            GeneradorFormularioFactory.crearMargen(contentStream1, Models.MODEL_2);
            GeneradorFormularioFactory.enumerarPaginas(contentStream1,index,total, Models.MODEL_2,  Style.getStyle(fontGroup,StyleEnum.NUMBER));
            index+=1;
        }

        for(PDPageContentStream e : contentStreams)
            e.close();

        doc.save(new File(ruta));
        doc.close();
    }

    private void crearDetallesTablePlanDePagos(
            PDPageContentStream contentStream,
            List<PDPageContentStream> contentStreams,
            PDDocument doc,
            PlanDePagosDTOPDF planDePagos) throws IOException {

        MultipleParagraph multipleParagraph = EasyComponentsFactory.getSimpleMultipleParagraph(
                "DETALLES",
                marginStartX + thickness, maxY - 380,
                fuenteMinimalNegrita,
                100,
                Alignment.LEFT);
        multipleParagraph.draw(contentStream);

        List<List<String>> listOfList = planDePagos.getContenido();
        List<Float>  widths = Arrays.asList(40f, 80f, 90f, 70f, 50f, 70f, 90f, 80f, 60f, 80f);
        List<String> header = planDePagos.getContenidoCabecera();
        List<String> footer = planDePagos.getSumatoria();

        Column headerColumn = EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(
                header, widths, fuenteMinimalNegrita, Alignment.CENTER);

        Column anotherHeaderColumn = EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(
                header, widths, fuenteMinimalNegrita, Alignment.CENTER);

        Column footerColumn = EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(
                footer, widths, fuenteMinimalNegrita, Alignment.CENTER);

        headerColumn.getCells().forEach(s -> {
            s.setColorFilling(Color.ORANGE);
            s.setHasFilling(true);
        });

        anotherHeaderColumn.getCells().forEach(s -> {
            s.setColorFilling(Color.ORANGE);
            s.setHasFilling(true);
        });

        List<Column> bodyColumns = new LinkedList<>();

        for (List<String> auxList : listOfList)
            bodyColumns.add(EasyComponentsFactory.getSimpleColumnFromTextAndWithsAndStyle(auxList, widths, fuenteMinimalEspaciado, Alignment.LEFT));

        bodyColumns.add(footerColumn);

        List<RecursiveTable> tables = EasyComponentsFactory.getHeaderTable(
                marginStartX + thickness, maxY - 400, maxY - 40, headerColumn, anotherHeaderColumn, bodyColumns, 45);

        Iterator<RecursiveTable> it = tables.iterator();
        it.next().draw(contentStream);

        while (it.hasNext()) {
            PDPage page = new PDPage(new PDRectangle(maxX, maxY));
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);
            it.next().draw(content);
            contentStreams.add(content);
        }
        contentStreams.add(0, contentStream);
    }

    private void crearClienteSeccionPlanDePagos(
            PDPageContentStream contentStream,
            DictamenDTOPDF dictamen) throws IOException {

        String usuario = dictamen.getNombreCliente();
        String ci = dictamen.getCi();

        String totalCuotaMensual = String.format("%s Bs", dictamen.getCuotaParcial());
        String interesFijo = String.format("%s%% Anual", dictamen.getTasaInteresFija());
        String segDesgravamen = String.format("%s%% Anual", dictamen.getSeguroDesgravamen());

        String cipMensual = String.format("%s%% Mensual", dictamen.getCip());

        String amortizacionFrecuencia = "Cada 30 dias";
        String totalCuotas = dictamen.getCuotaTotal();
        String tipoInteres = "Taza fija";
        String tipoCambio = dictamen.getCambioUS();
        String liquidoPagable = dictamen.getSueldoLiquidoPagable();
        String moneda = "Bolivianos";

        float width1 = 90f;
        float width2 = 60f;
        float width3 = 90f;
        float width4 = 60f;
        float width5 = 90f;
        float width6 = 70f;
        float initY = 200f;
        boolean margin = false;

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("Nombre Cliente"      , fuenteMenorNegrita, width1 + width2 / 2, 1f, 5f, margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("CI"                  , fuenteMenorNegrita, width1 + width2 / 2, 1f, 5f, margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Tipo de Amortizacion", fuenteMenorNegrita, width1 + width2 / 2, 1f, 5f, margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText("Moneda"              , fuenteMenorNegrita, width1, 1f, 5f, margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("Amortizacion cada"   , fuenteMenorNegrita, width1, 1f, 5f, margin);
        Cell c16 = EasyComponentsFactory.getSimpleCellFromText("Interes fijo"        , fuenteMenorNegrita, width1, 1f, 5f, margin);
        Cell c17 = EasyComponentsFactory.getSimpleCellFromText("Total Cuota Mensual" , fuenteMenorNegrita, width1, 1f, 5f, margin);

        Cell c21 = EasyComponentsFactory.getSimpleCellFromText(usuario                                      , fuenteMenorEspaciado, width3 + width4 + width5 + width6 + width2 / 2, 1f, 5f, margin);
        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(ci                                           , fuenteMenorEspaciado, width3 + width4 + width5 + width6 + width2 / 2, 1f, 5f, margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText("PRESTAMO AMORTIZABLE - METODO FRANCES", fuenteMenorEspaciado, width3 + width4 + width5 + width6 + width2 / 2, 1f, 5f, margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(moneda                                       , fuenteMenorEspaciado, width2, 1f, 5f, margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText(amortizacionFrecuencia                       , fuenteMenorEspaciado, width2, 1f, 5f, margin);
        Cell c26 = EasyComponentsFactory.getSimpleCellFromText(interesFijo                                  , fuenteMenorEspaciado, width2, 1f, 5f, margin);
        Cell c27 = EasyComponentsFactory.getSimpleCellFromText(totalCuotaMensual                            , fuenteMenorEspaciado, width2, 1f, 5f, margin);

        Cell c34 = EasyComponentsFactory.getSimpleCellFromText("Tipo de cambio(Bs.)", fuenteMenorNegrita, width3, 1f, 5f, margin);
        Cell c35 = EasyComponentsFactory.getSimpleCellFromText("Plazo(cuotas)"      , fuenteMenorNegrita, width3, 1f, 5f, margin);
        Cell c36 = EasyComponentsFactory.getSimpleCellFromText("Seg. desgravamen"   , fuenteMenorNegrita, width3, 1f, 5f, margin);

        Cell c44 = EasyComponentsFactory.getSimpleCellFromText(tipoCambio    , fuenteMenorEspaciado, width4, 1f, 5f, margin);
        Cell c45 = EasyComponentsFactory.getSimpleCellFromText(totalCuotas   , fuenteMenorEspaciado, width4, 1f, 5f, margin);
        Cell c46 = EasyComponentsFactory.getSimpleCellFromText(segDesgravamen, fuenteMenorEspaciado, width4, 1f, 5f, margin);

        Cell c54 = EasyComponentsFactory.getSimpleCellFromText("Liquido pagable(Bs)", fuenteMenorNegrita, width5, 1f, 5f, margin);
        Cell c55 = EasyComponentsFactory.getSimpleCellFromText("Tipo de Interes", fuenteMenorNegrita, width5, 1f, 5f, margin);
        Cell c56 = EasyComponentsFactory.getSimpleCellFromText("CIP", fuenteMenorNegrita, width5, 1f, 5f, margin);

        Cell c64 = EasyComponentsFactory.getSimpleCellFromText(liquidoPagable, fuenteMenorEspaciado, width6, 1f, 5f, margin);
        Cell c65 = EasyComponentsFactory.getSimpleCellFromText(tipoInteres   , fuenteMenorEspaciado, width6, 1f, 5f, margin);
        Cell c66 = EasyComponentsFactory.getSimpleCellFromText(cipMensual    , fuenteMenorEspaciado, width6, 1f, 5f, margin);

        EasyComponentsFactory.cambiarColor(new Color(77, 175, 134), c23, c24, c44, c64, c25, c45, c65, c26, c46, c66);

        float auxY = maxY - initY;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                marginStartX + thickness, auxY,
                new Cell[]{c11, c21},
                new Cell[]{c12, c22},
                new Cell[]{c13, c23},
                new Cell[]{c14, c24, c34, c44, c54, c64},
                new Cell[]{c15, c25, c35, c45, c55, c65},
                new Cell[]{c16, c26, c36, c46, c56, c66},
                new Cell[]{c17, c27}
        );
        Cell box = EasyComponentsFactory.getBoxStroke(10f, 5f, new Color(107, 250, 194), table);
        box.setHasMargin(true);
        box.setHasFilling(true);
        box.setColorMargin(new Color(107, 250, 194));
        box.setColorFilling(new Color(107, 250, 194));
        box.draw(contentStream);
    }

    private void crearResumenDatosSeccionPlanDePagos(
            PDPageContentStream contentStream,
            DictamenDTOPDF dictamen) throws IOException {

        String capitalAPagar = dictamen.getCuotaTotal();
        String interesAPagar = dictamen.getTasaInteresFija();
        String segDesgravamen = dictamen.getSeguroDesgravamen();
        String cip = dictamen.getCip();
        String totalAPagar = dictamen.getCuotaTotal();
        String fechaInicio = "";
        String fechaFin = "";

        float width1 = 100f;
        float width2 = 80f;
        float initY = 200f;
        boolean margin = false;

        Cell cLine = EasyComponentsFactory.getSimpleCellRectangle(width1 + width2, 2f, 1f, margin);

        Cell c11 = EasyComponentsFactory.getSimpleCellFromText("RESUMEN DE DATOS" , fuenteTituloTabla, width1 + width2, 1f, 5f, Alignment.CENTER, margin);
        Cell c12 = EasyComponentsFactory.getSimpleCellFromText("Capital a pagar"  , fuenteMenorNegrita, width1, 1f, 5f, margin);
        Cell c13 = EasyComponentsFactory.getSimpleCellFromText("Interes a pagar"  , fuenteMenorNegrita, width1, 1f, 5f, margin);
        Cell c14 = EasyComponentsFactory.getSimpleCellFromText("Seg. desgravamen" , fuenteMenorNegrita, width1, 1f, 5f, margin);
        Cell c15 = EasyComponentsFactory.getSimpleCellFromText("CIP"              , fuenteMenorNegrita, width1, 1f, 5f, margin);
        Cell c16 = EasyComponentsFactory.getSimpleCellFromText("Total a pagar"    , fuenteMenorNegrita, width1, 1f, 5f, margin);
        Cell c17 = EasyComponentsFactory.getSimpleCellFromText("Fecha inicio pago", fuenteMenorNegrita, width1, 1f, 5f, margin);
        Cell c18 = EasyComponentsFactory.getSimpleCellFromText("Fecha fin pago"   , fuenteMenorNegrita, width1, 1f, 5f, margin);

        Cell c22 = EasyComponentsFactory.getSimpleCellFromText(capitalAPagar , fuenteMenorEspaciado, width2, 1f, 5f, margin);
        Cell c23 = EasyComponentsFactory.getSimpleCellFromText(interesAPagar , fuenteMenorEspaciado, width2, 1f, 5f, margin);
        Cell c24 = EasyComponentsFactory.getSimpleCellFromText(segDesgravamen, fuenteMenorEspaciado, width2, 1f, 5f, margin);
        Cell c25 = EasyComponentsFactory.getSimpleCellFromText(cip           , fuenteMenorEspaciado, width2, 1f, 5f, margin);
        Cell c26 = EasyComponentsFactory.getSimpleCellFromText(totalAPagar   , fuenteMenorEspaciado, width2, 1f, 5f, margin);
        Cell c27 = EasyComponentsFactory.getSimpleCellFromText(fechaInicio   , fuenteMenorEspaciado, width2, 1f, 5f, margin);
        Cell c28 = EasyComponentsFactory.getSimpleCellFromText(fechaFin      , fuenteMenorEspaciado, width2, 1f, 5f, margin);

        float auxY = maxY - initY;

        SimpleTable table = EasyComponentsFactory.getSimpleTableFromCell(
                maxX - marginEndX - marginStartX - thickness - width1 - width2, auxY,
                new Cell[]{c11},
                new Cell[]{cLine},
                new Cell[]{c12, c22},
                new Cell[]{c13, c23},
                new Cell[]{c14, c24},
                new Cell[]{c15, c25},
                new Cell[]{c16, c26},
                new Cell[]{c17, c27},
                new Cell[]{c18, c28}
        );
        Cell box = EasyComponentsFactory.getBoxStroke(10f, 5f, new Color(107, 250, 194), table);
        box.setHasMargin(true);
        box.setHasFilling(true);
        box.setColorMargin(new Color(107, 250, 194));
        box.setColorFilling(new Color(107, 250, 194));
        box.draw(contentStream);
    }

}

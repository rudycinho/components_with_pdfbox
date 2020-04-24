import com.lowagie.text.DocumentException;
import subsistema.pdf.dto.*;
import subsistema.pdf.services.GeneradorFormulariosService;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static GeneradorFormulariosService service = new GeneradorFormulariosService();

    public static void main(String ...args) throws IOException, DocumentException {
        //"\u21B5"

        SolicitudCreditoViviendaDTOPDF solicitud  = SolicitudCreditoViviendaDTOPDF.getFake();
        DefinicionCreditoDTOPDF definicionCredito = DefinicionCreditoDTOPDF.getFake();
        DictamenDTOPDF dictamen                   = DictamenDTOPDF.getFake();
        PlanDePagosDTOPDF planDePagos             = PlanDePagosDTOPDF.getFake();
        UsuarioDTOPDF usuarioEditor               = UsuarioDTOPDF.getFake();
        SucursalDTOPDF sucursal                   = SucursalDTOPDF.getFake();
        DesafiliadoDTOPDF desafiliado             = DesafiliadoDTOPDF.getFake();
        UsuarioDTOPDF usuarioEditado              = UsuarioDTOPDF.getFake();
        PerfilDTOPDF perfil                       = PerfilDTOPDF.getFake();
        AjustesSistemaDTOPDF ajustesSistemaDTO    = AjustesSistemaDTOPDF.getFake();
        AfiliadoDTOPDF afiliado     = AfiliadoDTOPDF.getFake();
        List<EstadoAfiliacionDTOPDF> estados      = new LinkedList<>();
        estados.add(EstadoAfiliacionDTOPDF.getFake());
        estados.add(EstadoAfiliacionDTOPDF.getFake());
        estados.add(EstadoAfiliacionDTOPDF.getFake());
        estados.add(EstadoAfiliacionDTOPDF.getFake());
        estados.add(EstadoAfiliacionDTOPDF.getFake());
        estados.add(EstadoAfiliacionDTOPDF.getFake());
        estados.add(EstadoAfiliacionDTOPDF.getFake());
        estados.add(EstadoAfiliacionDTOPDF.getFake());
        estados.add(EstadoAfiliacionDTOPDF.getFake());
        estados.add(EstadoAfiliacionDTOPDF.getFake());

        String porcionHTML           =
                "<h3>Celephais</h3>\n" +
                        "\n" +
                        "<p>En un sueño, <strong>Kuranes</strong> vio la ciudad del valle, y la costa que se extendía más allá, y el nevado pico que dominaba el mar, y las galeras de alegres colores que salían del puerto rumbo a lejanas regiones donde el mar se junta con el cielo. Fue en un sueño también, donde recibió e1 nombre de <strong>Kuranes</strong>, ya que despierto se llamaba de otra manera. Quizá le resultó natural soñar un nuevo nombre, pues era el último miembro de su familia, y estaba solo entre los indiferentes millones de londinenses, de modo que no eran muchos los que hablaban con él y recordaban quién había sido. Había perdido sus tierras y riquezas; y le tenía sin cuidado la vida de las gentes de su alrededor; porque él prefería soñar y escribir sobre sus sueños. Sus escritos hacían reír a quienes los enseñaba, por lo que algún tiempo después se los guardó para sí, y finalmente dejó de escribir. Cuanto más se retraía del mundo que le rodeaba, más maravillosos se volvían sus sueños; y habría sido completamente inútil intentar transcribirlos al papel. <strong>Kuranes</strong> no era moderno, y no pensaba como los demás escritores. Mientras ellos se esforzaban en despojar la vida de sus bordados ropajes del mito y mostrar con desnuda fealdad lo repugnante que es la realidad, <strong>Kuranes</strong> buscaba tan sólo la belleza. Y cuando no conseguía revelar la verdad y la experiencia, la buscaba en la fantasía y la ilusión, en cuyo mismo umbral la descubría entre los brumosos recuerdos de los cuentos y los sueños de niñez. </p>\n" +
                        "<p>&nbsp;&nbsp;&nbsp;&nbsp;No son muchas las personas que saben las maravillas que guardan para ellas los relatos y visiones de su propia juventud; pues cuando somos niños escuchamos y soñamos y pensamos pensamientos a medias sugeridos; y cuando llegamos a la madurez y tratamos de recordar, la ponzoña de la vida nos ha vuelto torpes y prosaicos. Pero algunos de nosotros despiertan por la noche con extraños fantasmas de montes y jardines encantados, de fuentes que cantan al sol, de dorados acantilados que se asoman a unos mares rumorosos, de llanuras que se extienden en torno a soñolientas ciudades de bronce y de piedra, y de oscuras compañías de héroes que cabalgan sobre enjaezados caballos blancos por los linderos de bosques espesos; entonces sabemos que hemos vuelto la mirada, a través de la puerta de marfil, hacia ese mundo de maravilla que fue nuestro, antes de alcanzar la sabiduría y la infelicidad. </p>\n" +
                        "<p><strong>Kuranes</strong> regresó súbitamente a su viejo mundo de la niñez. Había estado soñando con la casa donde había nacido: el gran edificio de piedra cubierto de hiedra, donde habían vivido tres generaciones de antepasados suyos, y donde él había esperado morir. Brillaba la luna, y <strong>Kuranes</strong> había salido sigilosamente a la fragante noche de verano; atravesó los jardines, descendió por las terrazas, dejó atrás los grandes robles del parque, y recorrió el largo camino que conducía al pueblo. El pueblo parecía muy viejo; tenía su borde mordido como la luna que ha empezado a menguar, y <strong>Kuranes</strong> se preguntó silos tejados puntiagudos de las casitas ocultaban el sueño o la muerte. En las calles había tallos de larga yerba, y los cristales de las ventanas de uno y otro lado estaban rotos o miraban ciegamente. <strong>Kuranes</strong> no se detuvo, sino que siguió caminando trabajosamente, como llamado hacia algún objetivo. No se atrevió a desobedecer ese impulso por temor a que resultase una ilusión como las solicitudes y aspiraciones de la vida vigil, que no conducen a objetivo ninguno. Luego se sintió atraído hacia un callejón que salía de la calle del pueblo en dirección a los acantilados del canal, y llegó al final de todo… al precipicio y abismo donde el pueblo y el mundo caían súbitamente en un vacío infinito, y donde incluso el cielo, allá delante, estaba vacío y no lo iluminaban siquiera la luna roída o las curiosas estrellas. La fe le había instado a seguir avanzando hacia el precipicio, arrojándose al abismo, por el que descendió flotando, flotando, flotando; pasó oscuros, informes sueños no soñados, esferas de apagado resplandor que podían ser sueños apenas soñados, y seres alados y rientes que parecían burlarse de los soñadores de todos los mundos. Luego pareció abrirse una grieta de claridad en las tinieblas que tenía ante sí, y vio la ciudad del valle brillando espléndidamente allá, allá abajo, sobre un fondo de mar y de cielo, y una montaña coronada de nieve cerca de la costa. </p>\n" +
                        "<p><strong>Kuranes</strong> despertó en el instante en que vio la ciudad; sin embargo, supo con esa mirada fugaz que no era otra que Celephais, la ciudad del Valle de Ooth-Nargai, situada más allá de los Montes Tanarios, donde su espíritu había morado durante la eternidad de una hora, en una tarde de verano, hacía mucho tiempo, cuando había huido de su niñera y había dejado que la cálida brisa del mar le aquietara y le durmiera mientras observaba las nubes desde el acantilado próximo al pueblo. Había protestado cuando le encontraron, le despertaron y le llevaron a casa; porque precisamente en el momento en que le hicieron volver en sí, estaba a punto de embarcar en una galera dorada rumbo a esas seductoras regiones donde el cielo se junta con el mar. Ahora se Sintió igualmente irritado al despertar, ya que al cabo de cuarenta monótonos años había encontrado su ciudad fabulosa. </p>\n" +
                        "<p>Pero tres noches después, <strong>Kuranes</strong> volvió a Celephais. Como antes, soñó primero con el pueblo que parecía dormido o muerto, y con el abismo al que debía descender flotando en silencio; luego apareció la grieta de claridad una vez más, contempló los relucientes alminares de la ciudad, las graciosas galeras fondeadas en el puerto azul, y los árboles gingko del Monte Arán mecidos por la brisa marina. Pero esta vez no le sacaron del sueño; y descendió suavemente hacia la herbosa ladera como un ser alado, hasta que al fin sus pies descansaron blandamente en el césped. En efecto, había regresado al valle de Ooth-Nargai, y a la espléndida ciudad de Celephais. </p>\n" +
                        "<p><strong>Kuranes</strong> paseó en medio de yerbas fragantes y flores espléndidas, cruzó el burbujeante Naraxa por el minúsculo puente de madera donde había tallado su nombre hacía muchísimos años, atravesó la rumorosa arboleda, y se dirigió hacia el gran puente de piedra que hay a la entrada de la ciudad. Todo era antiguo; aunque los mármoles de sus muros no habían perdido su frescor, ni se habían empañado las pulidas estatuas de bronce que sostenían. Y <strong>Kuranes</strong> vio que no tenía por qué temer que hubiesen desaparecido las cosas que él conocía; porque hasta los centinelas de las murallas eran los mismos, y tan jóvenes como él los recordaba. Cuando entró en la ciudad, y cruzó las puertas de bronce, y pisó el pavimento de ónice, los mercaderes y camelleros le saludaron como si jamás se hubiese ausentado; y lo mismo ocurrió en el templo de turquesa de Nath-Horthath, donde los sacerdotes, adornados con guirnaldas de orquídeas le dijeron que no existe el tiempo en Ooth-Nargai, sino sólo la perpetua juventud. A continuación, <strong>Kuranes</strong> bajó por la Calle de los Pilares hasta la muralla del mar, y se mezcló con los mercaderes y marineros y los hombres extraños de esas regiones en las que el cielo se junta con el mar. Allí permaneció mucho tiempo, mirando por encima del puerto resplandeciente donde las ondulaciones del agua centelleaban bajo un sol desconocido, y donde se mecían fondeadas las galeras de lejanos lugares. Y contempló también el Monte Arán, qué se alzaba majestuoso desde la orilla, con sus verdes laderas cubiertas de árboles cimbreantes, y con su blanca cima rozando el cielo. </p>\n" +
                        "<p>Más que nunca deseó <strong>Kuranes</strong> zarpar en una galera hacia lejanos lugares, de los que tantas historias extrañas había oído; así que buscó nuevamente al capitán que en otro tiempo había accedido a llevarle. Encontró al hombre, Athib, sentado en el mismo cofre de especias en que le viera en el pasado; y Athib no pareció tener conciencia del tiempo transcurrido. Luego fueron los dos en bote a una galera del puerto, dio órdenes’ a los remeros, y salieron al Mar Cerenerio que llega hasta el cielo. Durante varios días se deslizaron por las aguas ondulantes, hasta que al fin llegaron al horizonte, donde el mar se junta con el cielo. No se detuvo aquí la galera, sino que siguió navegando ágilmente por el cielo azul entre vellones de nube teñidos de rosa. Y muy por debajo de la quilla, <strong>Kuranes</strong> divisó extrañas tierras y ríos y ciudades de insuperable belleza, tendi- das indolentemente a un sol que no parecía disminuir ni desaparecer jamás. Por último, Athib le dijo que -su viaje no terminaba nunca, y que pronto entraría en el puerto de Sarannian, la ciudad de mármol rosa de las nubes, construida sobre -la etérea costa donde el viento de poniente sopla hacia el cielo; pero cuando las más elevadas de las torres esculpidas de la ciudad surgieron a la vista, se produjo un ruido en alguna parte del espacio, y <strong>Kuranes</strong> despertó en su buhardilla de Londres. </p>\n" +
                        "<p>Después, <strong>Kuranes</strong> buscó en vano durante meses la maravillosa ciudad de Celephais y sus galeras que hacían la ruta del cielo; y aunque sus sueños le llevaron a numerosos -y espléndidos lugares, nadie pudo decirle cómo encontrar el Valle de Ooth-Nargai, situado más -allá ae los Montes Tanarios. Una noche voló por en- -cima de oscuras montañas donde brillaban débiles y solitarias fogatas de campamento, muy diseminadas, y había extrañas y velludas manadas de reses cuyos cabestros portaban tintineantes cencerros; y en la parte más inculta de esta región montañosa, tan remota que pocos hombres podían haberla visto, descubrió una especie de muralla o calzada empedrada, espantosamente antigua, que zigzagueaba a lo largo de cordilleras y valles, y demasiado gigantesca para haber sido construida por manos humanas. Más allá de esa muralla, en la claridad gris del alba, llegó a un país de exóticos jardines y cerezos; y cuando el sol se elevó, contempló tanta belleza de flores blancas, verdes follajes y campos de césped, pálidos senderos, cristalinos manantiales, pequeños lagos azules, puentes esculpidos y pagodas de roja techumbre, que, embargado de felicidad, olvidó Celephais por un instante. Pero nuevamente la recordó al descender por un blanco camino hacia una pagoda de roja techumbre; y si hubiese querido preguntar por ella a la gente de esta tierra, habría descubierto que no había allí gente alguna, sino pájaros y abejas y mariposas. otra noche, <strong>Kuranes</strong> subió por una interminable y húmeda escalera de caracol, hecha de piedra, y llegó a la ‘ventana de una torre que dominaba una inmensa llanura y un río iluminado por la luna llena; y en la silenciosa ciudad que se extendía a partir de la orilla del río, creyó ver algún rasgo o disposición que había conocido anteriormente. Habría bajado a preguntar el camino de Ooth-Nargai, si no hubiese surgido la temible aurora de algún remoto lugar del otro lado del horizonte, – mostrando las ruinas y antigüedades de la ciudad, y el estancamiento del río cubierto de cañas, y la tierra sembrada de muertos, tal como había permanecido desde que el rey Kynaratholis regresara de sus conquistas para encontrarse con la venganza de los dioses. </p>\n" +
                        "<p>Y así, <strong>Kuranes</strong> buscó inútilmente la maravillosa ciudad de Celephais y las galeras que navegaban por el cielo rumbo a Seranninan, contemplando entretanto numerosas maravillas y escapando en una ocasión milagrosamente del indescriptible gran sacerdote que se oculta tras una máscara de seda amarilla y vive solitario en un monasterio prehistórico de piedra, en la fría y desierta meseta de Leng. Al cabo del tiempo, le resultaron tan insoportables los desolados intervalos del día, que empezó a procurarse drogas a fin de aumentar sus periodos de sueño. El hachís le ayudó enormemente, y en una ocasión le trasladó a una región del espacio donde no existen las formas, pero los gases incandescentes estudian los secretos de la existencia. Y un gas violeta le dijo que esta parte del espacio estaba al exterior de lo que él llamaba el infinito. El gas no había oído hablar de planetas ni de organismos, sino que identificaba a <strong>Kuranes</strong> como una infinitud de materia, energía y gravitación. <strong>Kuranes</strong> se sintió ahora muy deseoso de regresar a la Celephais salpicada de alminares, y aumentó su dosis de droga. Después, un día de verano, le echaron de su buhardilla, y vagó sin rumbo por las calles, cruzó un puente, y se dirigió a una zona donde las casas eran cada vez más escuálidas. Y allí fue donde culminó su realización, y encontró el cortejo de caballeros que venían de Celephais para llevarle allí para siempre. </p>\n" +
                        "<p>Hermosos eran los caballeros, montados sobre caba-líos ruanos y ataviados con relucientes armaduras, y cuyos tabardos tenían bordados extraños blasones con hilo de oro. Eran tantos, que <strong>Kuranes</strong> casi los tomó por un ejército, aunque habían sido enviados en su honor; porque era él quien había creado Ooth-Nargai en sus sueños, motivo por el cual iba a ser nombrado ahora su dios supremo. A continuación, dieron a <strong>Kuranes</strong> un caballo y le colocaron a la cabeza de la comitiva, y emprendieron la marcha majestuosa por las campiñas de Surrey, hacia la región donde <strong>Kuranes</strong> y sus antepasados habían nacido. Era muy extraño, pero mientras cabalgaban parecía que retrocedían en el tiempo; pues cada vez que cruzaban un pueblo en el crepúsculo, veían a sus vecinos y sus casas como Chaucer y sus predecesores les vieron; y hasta se cruzaban a veces con algún caballero con un pequeño grupo de seguidores. Al avecinarse la noche marcharon más deprisa, y no tardaron en galopar tan prodigiosamente como si volaran en el aire. Cuando empezaba a alborear, llegaron a un pueblo que <strong>Kuranes</strong> había visto bullente de animación en su niñez, y dormido o muerto durante sus sueños. Ahora estaba vivo, y los madrugadores al deanos hicieron una reverencia al paso de los jinetes calle abajo, entre el resonar de los cascos, que luego desaparecieron por el callejón que termina en el abismo de los sueños. <strong>Kuranes</strong> se había precipitado en ese abismo de noche solamente, y se preguntaba cómo sería de día; así que miró con ansiedad cuando la columna empezó a acercarse al borde. Y mientras galopaba cuesta arriba hacia el precipicio, una luz radiante y dorada surgió de occidente y vistió el paisaje con refulgentes ropajes. El abismo era un caos hirviente de rosáceo y cerúleo esplendor; unas voces invisibles cantaban gozosas mientras el séquito de caballeros saltaba al vacío y descendía flotando graciosamente a través de las nubes luminosas y los plateados centelleos. Seguían flotando interminablemente los jinetes, y sus corceles pateaban el éter como si galopasen sobre doradas arenas; luego, los encendidos vapores se abrieron para revelar un resplandor aún más grande: el resplandor de la ciudad de Celephais, y la costa, más allá; y el pico que dominaba el mar, y las galeras de vivos colores que zarpan del puerto rumbo a lejanas regiones donde el cielo se junta con el mar. </p>\n" +
                        "<p>Y <strong>Kuranes</strong> reinó en Ooth-Nargai y todas las regiones vecinas de los sueños, y tuvo su corte alternativamente en Celephais y en la Serannian formada de nubes. Y aún reina allí, y reinará feliz para siempre; aunque al pie de los acantilados de Innsmouth, las corrientes del canal jugaban con el cuerpo de un vagabundo que había cruzado el pueblo semidesierto al amanecer; jugaban burlonamente, y lo arrojaban contra las rocas, junto a las Torres de Trevor cubiertas de hiedra, donde un millonario obeso y cervecero disfruta de un ambiente comprado de nobleza extinguida.</p>\n" +
                        "<p>&nbsp;&nbsp;&nbsp;&nbsp;</p>\n" +
                        "\n";


        service.FormularioInformacionUsuario(usuarioEditado,usuarioEditor,sucursal);
        service.FormularioInformacionSucursal(usuarioEditor,sucursal);
        service.FormularioAjustesSistema(ajustesSistemaDTO,usuarioEditor,sucursal);
        service.FormularioAfiliacion(afiliado,usuarioEditor,sucursal,estados);
        service.FormularioDesafiliacion(desafiliado,usuarioEditor,sucursal);
        service.FormularioInformacionPerfil(perfil,usuarioEditor,sucursal);
        service.FormularioPrecalificadorPrestamos(dictamen,usuarioEditor,sucursal);
        service.FormularioPlanPagos(dictamen,planDePagos,usuarioEditor,sucursal);
        service.FormularioRequisitosSolicitudCredito(porcionHTML,usuarioEditor,sucursal);
        service.FormularioDefinicionCredito(definicionCredito,usuarioEditor,sucursal);
        service.FormularioSolicitudCreditoVivienda(solicitud,usuarioEditor,sucursal);

    }

    /*
    public static void prueba() throws IOException {

        int maxY = (int) PDRectangle.LETTER.getHeight();
        int maxX = (int) PDRectangle.LETTER.getWidth();

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage(new PDRectangle(maxX, maxY));

        doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        String text1 = "Lorem ipsum dolor sit amet,\n consectetur\n adipiscing\n elit, \nsed do\n eiusmod\n tempor\n incididunt" +
                " ut labore et dolore magna aliqua.\n Ut enim ad minim veniam,\n quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Duis aute \nirure dolor in reprehenderit in " +
                " ut labore et dolore magna aliqua.\n Ut enim ad minim veniam, \nquis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. ";

        String text2 = "Lorem ipsum dolor sit amet, consectetur\n adipiscing elit, \nsed do eiusmod\n tempor\n incididunt" +
                " ut labore et dolore magna aliqua.Ut enim ad minim veniam,\n quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Duis aute \nirure dolor in reprehenderit in " +
                " ut labore et dolore magna aliqua.Ut enim ad minim veniam, \nquis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. ";

        String text3 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                " ut labore et dolore magna aliqua.\n Ut enim ad minim veniam,\n quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Duis aute \nirure dolor in reprehenderit in " +
                " ut labore et dolore magna aliqua.\n Ut enim ad minim veniam, \nquis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. ";

        String text4 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                " ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                " ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. ";

        String st = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                " ut labore et dolore magna aliqua.Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                " ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Ut enim ad minim veniam, ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. ";

        //PDImageXObject pdImage = PDImageXObject.createFromFile("C:/PdfBox_Examples/logo.png",doc);

        //String st = "Lorem ipsum dolor sit amet";

        Style style1 = Style.builder()
                .addFontSize(13f)
                .addTextColor(Color.BLUE)
                .addTextFont(PDType1Font.COURIER_BOLD)
                .addLeading(1.4f)
                .build();

        Style style2 = Style.builder()
                .addFontSize(13f)
                .addTextColor(Color.BLUE)
                .addTextFont(PDType1Font.HELVETICA)
                .addLeading(1.1f)
                .build();

        /*TextLine textLine = TextLine.builder()
                .addTextContent(st)
                .addAlignment(Alignment.LEFT)
                .addStartX(20)
                .addStartY(90)
                .addWidth(500f)
                .addStyle(style1)
                .build();

        float x0 = textLine.getStartX();
        float y0 = textLine.getStartY();
        float xf = textLine.getWidth();
        float yf = textLine.getHeight();


        /*Paragraph paragraph = Paragraph.builder()
                .addTextContent(st)
                .addAlignment(Alignment.LEFT)
                .addStartX(20)
                .addStartY(90)
                .addWidth(400f)
                .addStyle(style1)
                .build();

        float x0 = paragraph.getStartX();
        float y0 = paragraph.getStartY();
        float xf = paragraph.getWidth();
        float yf = paragraph.getHeight();

        Rectangle rectangle = new Rectangle(x0,y0,xf,yf);
        rectangle.draw(contentStream);

        //Grid grid = new Grid(maxX,maxY);
        //grid.draw(contentStream);

        MultipleParagraph component1 = MultipleParagraph.builder()
                .addTextContent(text1)
                .addAlignment(Alignment.LEFT)
                .addStartX(120f)
                .addStartY(490f)
                .addWidth(200f)
                .addStyle(style1)
                .build();

        MultipleParagraph component2 = MultipleParagraph.builder()
                .addTextContent(text2)
                .addAlignment(Alignment.LEFT)
                .addStartX(20f)
                .addStartY(290f)
                .addWidth(200f)
                .addStyle(style1)
                .build();

        MultipleParagraph component3 = MultipleParagraph.builder()
                .addTextContent(text3)
                .addAlignment(Alignment.LEFT)
                .addStartX(320f)
                .addStartY(-190f)
                .addWidth(200f)
                .addStyle(style2)
                .build();



        Cell cell1 = Cell.builder()
            .addRelativeDistanceX(10)
            .addRelativeDistanceY(10)
            .addHasMargin(true)
            .addHasFilling(false)
            .addColorMargin(Color.BLACK)
            .addColorFilling(Color.WHITE)
            .addComponent(component1)
            .build();

        Cell cell2 = Cell.builder()
                .addRelativeDistanceX(10)
                .addRelativeDistanceY(10)
                .addHasMargin(true)
                .addHasFilling(false)
                .addColorMargin(Color.BLACK)
                .addColorFilling(Color.WHITE)
                .addComponent(component2)
                .build();

        Cell cell3 = Cell.builder()
                .addRelativeDistanceX(10)
                .addRelativeDistanceY(10)
                .addHasMargin(true)
                .addHasFilling(false)
                .addColorMargin(Color.BLACK)
                .addColorFilling(Color.WHITE)
                .addComponent(component3)
                .build();

        Column column = Column.builder()
                .addStartX(20f)
                .addStartY(800f)
                .addCell(cell1)
                .addCell(cell2)
                .addCell(cell3)
                .build();

        //column.draw(contentStream);

        column.setDX(30);
        column.setDY(30);
        column.rebuild();

        column.draw(contentStream);

        float x0 = column.getStartX();
        float y0 = column.getStartY();
        float xf = column.getWidth();
        float yf = column.getHeight();

        //System.out.println(x0);
        //System.out.println(y0);
        //System.out.println(xf);
        //System.out.println(yf);

        Rectangle rectangle = Rectangle.builder()
                .addStartX(column.getStartX())
                .addStartY(column.getStartY())
                .addWidth(column.getWidth())
                .addHeight(column.getHeight())
                .addColor(Color.GREEN)
                .addThickness(3)
                .build();
        rectangle.draw(contentStream);

        System.out.println(column.getHeight());

        Cross cross = Cross.builder()
                .addStartX(column.getStartX())
                .addStartY(column.getStartY())
                .addWidth(column.getWidth())
                .addHeight(column.getHeight())
                .addColor(Color.GREEN)
                .addThickness(3)
                .build();
        cross.draw(contentStream);


        Image image = Image.builder()
                .addStartX(200)
                .addStartY(200)
                .addWidth(120)
                .addHeight(160)
                .addImage( PDImageXObject.createFromFile("/home/rudy/Code/spring/libPDFBOX/src/main/resources/saitama.png",doc))
                .build();
        image.draw(contentStream);


        /*contentStream.setStrokingColor(Color.ORANGE);
        contentStream.setNonStrokingColor(Color.ORANGE);
        contentStream.addRect(
                column.getStartX(),
                column.getStartY(),
                column.getWidth(),
                column.getHeight());//.setColor(Color.ORANGE);
        //contentStream.stroke();
        contentStream.fill();
        //rectangle.draw(contentStream);

        Image image = Image.builder()
                .addStartX(200)
                .addStartY(200)
                .addWidth(120)
                .addHeight(160)
                .addImage( PDImageXObject.createFromFile("/home/rudy/Code/spring/libPDFBOX/src/main/resources/saitama.png",doc))
                .build();

        image.draw(contentStream);


        Cell cell1 = Cell.builder()
                .addRelativeDistanceX(10)
                .addRelativeDistanceY(10)
                .addHasMargin(true)
                .addHasFilling(false)
                .addComponent(image)
                .build();

        Cell cell2 = Cell.builder()
                .addRelativeDistanceX(10)
                .addRelativeDistanceY(10)
                .addHasMargin(true)
                .addHasFilling(false)
                .addComponent(component3)
                .build();

        Cell cell3 = Cell.builder()
                .addRelativeDistanceX(10)
                .addRelativeDistanceY(10)
                .addHasMargin(true)
                .addHasFilling(false)
                .addComponent(Cross.builder()
                        .addWidth(50f)
                        .addHeight(-60f)
                        .addColor(Color.GREEN)
                        .addThickness(3)
                        .build())
                .build();

        Column column = Column.builder()
                .addStartX(20f)
                .addStartY(600f)
                .addCell(cell1)
                .addCell(cell2)
                .addCell(cell3)
                .build();

        //image.draw(contentStream);

        column.draw(contentStream);

        contentStream.close();
        doc.save(new File("/home/rudy/example.pdf"));
    }*/
}

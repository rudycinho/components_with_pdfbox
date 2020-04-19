package services;

import com.lowagie.text.DocumentException;
import dto.*;
import dto.PlanDePagosDTO;
import dto.EstadoAfiliacionDTO;
import dto.complex.DefinicionCreditoDTO;
import form.*;
import form.FormularioPlanPagos;
import form.FormularioPrecalificadorPrestamos;
import form.FormularioRequisitosSolicitudCredito;
import form.complex.FormularioDefinicionCredito;
import utils.Fecha;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class GeneradorFormulariosService {
	
	private final String DEST = "/home/rudy/times";

	public String FormularioInformacionUsuario() throws IOException {

		Date fechaExportacion        = new Date();
		UsuarioDTO usuarioEditadoDTO = UsuarioDTO.getFake();
		UsuarioDTO usuarioEditorDTO  = UsuarioDTO.getFake();
		SucursalDTO sucursalDTO      = SucursalDTO.getFake();

		String nombreArchivo = String.format("Formulario_Usuario_%s_%s_%s.pdf",
				usuarioEditadoDTO.getCarnetIdentidad().trim().replaceAll(" ", "_"),
				usuarioEditorDTO.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioInformacionUsuario(ruta,usuarioEditadoDTO,usuarioEditorDTO,sucursalDTO,fechaExportacion);

		return ruta;
	}

	public String FormularioInformacionSucursal() throws IOException {

		Date fechaExportacion        = new Date();
		UsuarioDTO usuarioEditorDTO  = UsuarioDTO.getFake();
		SucursalDTO sucursalDTO      = SucursalDTO.getFake();

		String nombreArchivo = String.format("Formulario_Sucursal_%s_%s_%s.pdf",
				sucursalDTO.getId().toString(),
				usuarioEditorDTO.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioInformacionSucursal(ruta,usuarioEditorDTO,sucursalDTO,fechaExportacion);

		return ruta;
	}

	public String FormularioInformacionPerfil() throws IOException {
		Date fechaExportacion = new Date();
		PerfilDTO perfilDTO          = PerfilDTO.getFake();
		UsuarioDTO usuarioEditorDTO  = UsuarioDTO.getFake();
		SucursalDTO sucursalDTO      = SucursalDTO.getFake();

		String nombreArchivo = String.format("Formulario_Perfil_%s_%s_%s.pdf",
				perfilDTO.getId().toString(),
				usuarioEditorDTO.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioInformacionPerfil(ruta,perfilDTO,usuarioEditorDTO,sucursalDTO,fechaExportacion);

		return ruta;
	}

	public String FormularioAjustesSistema() throws IOException {
		Date fechaExportacion               = new Date();
		AjustesSistemaDTO ajustesSistemaDTO = AjustesSistemaDTO.getFake();
		UsuarioDTO  usuarioEditorDTO        = UsuarioDTO.getFake();
		SucursalDTO sucursalDTO             = SucursalDTO.getFake();

		String nombreArchivo = String.format("Formulario_Ajustes_Sistema_%s_%s.pdf",
				usuarioEditorDTO.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioAjustesSistema(ruta,ajustesSistemaDTO,usuarioEditorDTO,sucursalDTO,fechaExportacion);

		return ruta;
	}

	public String FormularioAfiliacion() throws IOException {
		Date fechaExportacion = new Date();
		AfiliadoDTO               afiliadoDTO     = AfiliadoDTO.getFake();
 		UsuarioDTO                usuarioEditorDTO= UsuarioDTO.getFake();
		SucursalDTO               sucursalDTO     = SucursalDTO.getFake();
		List<EstadoAfiliacionDTO> estadosDTO      = new LinkedList<>();

		estadosDTO.add(EstadoAfiliacionDTO.getFake());
		estadosDTO.add(EstadoAfiliacionDTO.getFake());
		estadosDTO.add(EstadoAfiliacionDTO.getFake());
		estadosDTO.add(EstadoAfiliacionDTO.getFake());
		estadosDTO.add(EstadoAfiliacionDTO.getFake());
		estadosDTO.add(EstadoAfiliacionDTO.getFake());
		estadosDTO.add(EstadoAfiliacionDTO.getFake());
		estadosDTO.add(EstadoAfiliacionDTO.getFake());
		estadosDTO.add(EstadoAfiliacionDTO.getFake());
		estadosDTO.add(EstadoAfiliacionDTO.getFake());

		String nombreArchivo = String.format("Formulario_Afiliacion_%s_%s_%s.pdf",
				afiliadoDTO.getCarnetIdentidad().trim().replaceAll(" ", "_"),
				usuarioEditorDTO.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioAfiliacion(ruta,afiliadoDTO,estadosDTO,usuarioEditorDTO,sucursalDTO,fechaExportacion);

		return ruta;
	}

	public String FormularioDesafiliacion() throws IOException {
		Date fechaExportacion = new Date();
		DesafiliadoDTO desafiliadoDTO  = DesafiliadoDTO.getFake();
		UsuarioDTO usuarioEditorDTO = UsuarioDTO.getFake();
		SucursalDTO sucursalDTO     = SucursalDTO.getFake();

		String nombreArchivo = String.format("Formulario_Desafiliacion_%s_%s_%s.pdf",
				desafiliadoDTO.getCarnetIdentidad().trim().replaceAll(" ", "_"),
				usuarioEditorDTO.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioDesafiliacion(ruta,desafiliadoDTO,usuarioEditorDTO,sucursalDTO,fechaExportacion);

		return ruta;
	}

    public String FormularioPrecalificadorPrestamos() throws IOException {
		Date fechaExportacion = new Date();
		DictamenDTO dictamenDTO      = DictamenDTO.getFake();
		UsuarioDTO  usuarioEditorDTO = UsuarioDTO.getFake();
		SucursalDTO sucursalDTO      = SucursalDTO.getFake();

		String nombreArchivo = String.format("Formulario_Precalificador_Prestamos_%s_%s_%s.pdf",
				dictamenDTO.getCi().trim().replaceAll(" ", "_"),
				usuarioEditorDTO.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioPrecalificadorPrestamos(ruta,dictamenDTO,usuarioEditorDTO,sucursalDTO,fechaExportacion);

		return ruta;
    }

	public String FormularioPlanPagos() throws IOException {
		Date fechaExportacion = new Date();
		DictamenDTO    dictamenDTO      = DictamenDTO.getFake();
		PlanDePagosDTO planDePagosDTO   = PlanDePagosDTO.getFake();
		UsuarioDTO     usuarioEditorDTO = UsuarioDTO.getFake();
		SucursalDTO    sucursalDTO      = SucursalDTO.getFake();

		String nombreArchivo = String.format("Formulario_Plan_Pagos_%s_%s_%s.pdf",
				dictamenDTO.getCi().trim().replaceAll(" ", "_"),
				usuarioEditorDTO.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioPlanPagos(ruta,dictamenDTO,planDePagosDTO,usuarioEditorDTO,sucursalDTO,fechaExportacion);

		return ruta;
	}

	public String FormularioDefinicionCredito() throws IOException {

		Date fechaExportacion                     = new Date();
		DefinicionCreditoDTO definicionCreditoDTO = DefinicionCreditoDTO.getFake();
		UsuarioDTO usuarioEditorDTO               = UsuarioDTO.getFake();
		SucursalDTO sucursalDTO                   = SucursalDTO.getFake();

		String nombreArchivo = String.format("Formulario_Definicion_Credito_%s_%s_%s.pdf",
				definicionCreditoDTO.getCarnetIdentidad().trim().replaceAll(" ", "_"),
				usuarioEditorDTO.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioDefinicionCredito(ruta,definicionCreditoDTO,usuarioEditorDTO,sucursalDTO,fechaExportacion);

		return ruta;
	}

	public String FormularioRequisitosSolicitudCredito() throws IOException, DocumentException {
		Date fechaExportacion        = new Date();
		UsuarioDTO usuarioEditorDTO  = UsuarioDTO.getFake();
		SucursalDTO sucursalDTO      = SucursalDTO.getFake();
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

		String nombreArchivo = String.format("Formulario_Requisitos_Solicitud_Credito_%s_%s.pdf",
				usuarioEditorDTO.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioRequisitosSolicitudCredito(ruta,porcionHTML,usuarioEditorDTO,sucursalDTO,fechaExportacion);

		return ruta;
	}
    /*
	public String FormularioRequisitosSolicitudCredito(
			String htmlLiteral,
			Usuario usuarioEditor) {

		Date fechaCreacion = new Date();

		String nombreArchivo = String.format("Formulario_Plan_Pagos_%s_%s.pdf",
				usuarioEditor.getNombreCompleto().trimñ().replaceAll(" ", "_"),
				convertirDateToString(fechaCreacion));

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioRequisitosCredito(ruta,htmlLiteral,usuarioEditor,fechaCreacion);
		return ruta;
	}


	/*public String FormularioSolicitudCreditoVivienda(
			Usuario usuarioEditado, 
			Usuario usuarioEditor) throws IOException {
		
		Date fechaCreacion = new Date();
		
		String nombreArchivo = String.format("Formulario_Solicitud_Credito_%s_%s_%s.pdf",
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				usuarioEditado.getId(),
				convertirDateToString(fechaCreacion));
		
		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);
		
		new FormularioUsuario(ruta,usuarioEditado,usuarioEditor,fechaCreacion);
		
		new FormularioSolicitudCreditoVivienda(ruta,usuarioEditor,fechaCreacion);
        return ruta;
	}



	// 	COMPLEX

	public String FormularioPlanPagos(
			PlanDePagos planDePagos,
			Usuario usuarioEditor) {

		Date fechaCreacion = new Date();
		String ci = planDePagos.getDictamen().getCi();

		String nombreArchivo = String.format("Formulario_Plan_Pagos_%s_%s_%s.pdf",
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				ci==null ? "no_carnet" : ci.toString().trim().replace(" " , "_"),
				convertirDateToString(fechaCreacion));

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioPlanPagos(ruta,planDePagos,usuarioEditor,fechaCreacion);
        return ruta;
	}



	public String FormularioSolicitudCreditoVivienda(
			Usuario usuarioEditado,
			Usuario usuarioEditor) throws IOException {

		Date fechaCreacion = new Date();

		String nombreArchivo = String.format("Formulario_Solicitud_Credito_%s_%s_%s.pdf",
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				usuarioEditado.getId(),
				convertirDateToString(fechaCreacion));

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioUsuario(ruta,usuarioEditado,usuarioEditor,fechaCreacion);

		new FormularioSolicitudCreditoVivienda(ruta,usuarioEditor,fechaCreacion);
        return ruta;
	}

	public String FormularioSolicitudCreditoVivienda(
			Usuario usuarioEditado,
			Usuario usuarioEditor) throws IOException {

		Date fechaCreacion = new Date();

		String ruta = String.format("%s%s%s",DEST,"/","form.pdf");

		new FormularioUsuario(ruta,usuarioEditado,usuarioEditor,fechaCreacion);

		new FormularioSolicitudCreditoVivienda(ruta,usuarioEditor,fechaCreacion);
		return ruta;
	}

 */
}
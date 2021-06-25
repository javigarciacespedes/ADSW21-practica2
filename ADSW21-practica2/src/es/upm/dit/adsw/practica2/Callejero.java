package es.upm.dit.adsw.practica2;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Clase para representar contenidos de los ficheros de viales que distribuye el
 * ayuntamiento de Madrid
 * 
 * @author mmiguel
 *
 */
public class Callejero {

	private static final int COD_VIA = 0;
	private static final int VIA_CLASE = 1;
	private static final int VIA_PAR = 2;
	private static final int VIA_NOMBRE = 3;
	private static final int VIA_NOMBRE_ACENTOS = 4;
	private static final int COD_VIA_COMIENZA = 5;
	private static final int CLASE_COMIENZA = 6;
	private static final int PARTICULA_COMIENZA = 7;
	private static final int NOMBRE_COMIENZA = 8;
	private static final int NOMBRE_ACENTOS_COMIENZA = 9;
	private static final int COD_VIA_TERMINA = 10;
	private static final int CLASE_TERMINA = 11;
	private static final int PARTICULA_TERMINA = 12;
	private static final int NOMBRE_TERMINA = 13;
	private static final int NOMBRE_ACENTOS_TERMINA = 14;

	protected static final String fichero = "VialesVigentes_20201220.csv";
	protected Via[] vias;

	/**
	 * Constructor de callejero a partir de algun tipo de stream que incluye las
	 * vias del callejero. Ese stream esta soportado con un Scanner. El scanner
	 * incluye el contenido del callejero y el constructor lee el stream que debe
	 * estar en formato csv
	 * 
	 * @param viales    scanner del que extraemos el contenido del callejero
	 * @param numViales numero de viales que incluye el scanner
	 */
	public Callejero(Scanner viales, int numViales) {
		vias = new Via[numViales];
		String[] vias_csv;
		for (int i = 0; i < numViales; i++) {
			String linea = viales.nextLine();
			vias_csv = linea.split(";");
			vias[i] = new Via(Integer.parseInt(vias_csv[COD_VIA]), vias_csv[VIA_CLASE], vias_csv[VIA_PAR],
					vias_csv[VIA_NOMBRE], vias_csv[VIA_NOMBRE_ACENTOS], Integer.parseInt(vias_csv[COD_VIA_COMIENZA]),
					Integer.parseInt(vias_csv[COD_VIA_TERMINA]));
			if ((i == numViales - 1 && viales.hasNext()) || (i < numViales - 1 && !viales.hasNext()))
				throw new RuntimeException("Formato fichero errorneo");
		}
		viales.close();
		inicializaReferencias();
	}

	/**
	 * Metodo que inicializa la referecias a las vias de comienzo y terminacion de
	 * todas las vias contenidas en vias. Debe ser utilizado unicamente para hacer
	 * pruebas o en el constructor
	 */
	public void inicializaReferencias() {
		// TODO
		/*
		 * ordenaVias(); vias[0].setViaComienzo(vias[0].getViaComienzo());
		 * vias[0].setViaTermina(vias[1].getViaComienzo());
		 * 
		 * for (int i = 1; i <vias.length; i++) { vias[i].setViaComienzo(vias[i -
		 * 1].getViaTermina()); vias[i].setViaTermina(vias[i + 1].getViaComienzo()); }
		 */

		for (Via via : vias)
			for (Via via2 : vias) {
				if (via.getComienza() == via2.getCodigo())
					via.setViaComienzo(via2);
				if (via.getTermina() == via2.getCodigo())
					via.setViaTermina(via2);
			}

	}

	/**
	 * Metodo que ordena las vias en función del código de via. Debe ser utilizado
	 * unicamente para hacer pruebas
	 */
	public void ordenaVias() {
		// TODO
		SolucionP1.ordenaVias(vias);
	}

	/**
	 * Devuelve la referencia a la via cuyo codigo es el parametro del metodo
	 * 
	 * @param codigo codigo de la via buscada
	 * @return via cuyo codigo es codigo, o null si no existe
	 */
	public Via buscaViaCodigo(int codigo) {
		// TODO
		ordenaVias();
		Via via_encontrada = busquedaBinCodigo(vias, codigo);
		return via_encontrada;
		
	}

	/**
	 * Imprime en salida estandar todos los viales del callejero
	 */
	public void printViales() {
		for (Via via : vias) {
			via.formatPrint();
			System.out.println();
		}
	}

	/**
	 * Devuelve las vias del callejero
	 * 
	 * @return vias del callejero
	 */
	public Via[] getVias() {
		return vias;
	}

	/**
	 * Fija las vias del callejero. Debe estar completamente inicializada
	 * 
	 * @param vias nuevas vias del callejero
	 */
	public void setVias(Via[] vias) {
		this.vias = vias;
	}

	/**
	 * Devuelve el cojunto de vias del callejero ordenadas por nombre
	 * 
	 * @param vias array de vias a ordenar por nombre
	 * @return conjunto de vias ordenadas por nombre
	 */
	public void ordenaViasPorNombre(Via[] vias) {
		// TODO
		SolucionP1.ordenaViasPorNombre(vias);
	}

	/**
	 * Devuelve el cojunto de vias del callejero cuyo nombre comienza por viaBuscada
	 * 
	 * @param viaBuscada secuencia de caracteres en mayusculas con las que comienza
	 *                   el nombre de las vias que se buscan
	 * @return conjunto de vias que comienza por viaBuscada
	 */
	
	//https://parzibyte.me/blog/2018/10/31/busqueda-binaria-java-arreglos-cadenas/
	public Set<Via> buscaVia(String viaBuscada) {
		// TODO
		Set<Via> sol = new HashSet<Via>();
		ordenaViasPorNombre(vias);
		int izquierda = 0, derecha = vias.length - 1;
		while (izquierda <= derecha) {
			// Calculamos las mitades...
			int indiceDelElementoDelMedio = (int) Math.floor((izquierda + derecha) / 2);
			String elementoDelMedio = vias[indiceDelElementoDelMedio].getNombre();

			// Primero vamos a comparar y ver si el resultado es negativo, positivo o 0
			int resultadoDeLaComparacion = viaBuscada.compareTo(elementoDelMedio);

			// Si el resultado de la comparación es 0, significa que ambos elementos son
			// iguales
			// y por lo tanto quiere decir que hemos encontrado la búsqueda
			if (resultadoDeLaComparacion == 0) {
			sol.add(vias[indiceDelElementoDelMedio]);
				int igual_arriba;
				igual_arriba = indiceDelElementoDelMedio - 1;
				while(vias[igual_arriba].getNombre().contains(viaBuscada) == true) {
					sol.add(vias[igual_arriba]);
					igual_arriba--;
					                          
				}
				int igual_abajo;
				igual_abajo= indiceDelElementoDelMedio + 1;
			
				while(vias[igual_abajo].getNombre().contains(viaBuscada) == true) {
					sol.add(vias[igual_abajo]);
					igual_abajo++;
					                          
				}
				
				
				return sol;
			}

			// Si no, entonces vemos si está a la izquierda o derecha

			if (resultadoDeLaComparacion < 0) {
				derecha = indiceDelElementoDelMedio - 1;
			} else {
				izquierda = indiceDelElementoDelMedio + 1;
			}
		}
		
		return sol;
	}

	// https://javaparajavatos.wordpress.com/2017/05/01/busqueda-binaria/
	private Via busquedaBinCodigo(Via[] vias, double codigo) {

		int mitad, inferior = 0;
		int superior = vias.length - 1;

		do {
			mitad = (inferior + superior) / 2;
			if (codigo > vias[mitad].getCodigo()) {
				inferior = mitad + 1;
			} else {
				superior = mitad - 1;
			}
		} while (vias[mitad].getCodigo() != codigo && inferior <= superior);

		if (vias[mitad].getCodigo() == codigo) {
			return vias[mitad];
		} else {
			return null;
		}

	}

	/*
	 * // https://javaparajavatos.wordpress.com/2017/05/01/busqueda-binaria/ private
	 * Set<Via> busquedaBinNombre(Via[] vias, String valorBuscado) {
	 * 
	 * int mitad, inferior = 0; int superior = vias.length - 1; Set<Via>
	 * vias_encontradas = new HashSet<Via>(); do { mitad = (inferior + superior) /
	 * 2; if (valorBuscado.compareTo(vias[mitad].getNombre()) > 0) { inferior =
	 * mitad + 1; } else { superior = mitad - 1; }
	 * 
	 * } while (vias[mitad].getNombre() != valorBuscado && inferior <= superior);
	 * 
	 * while (vias[mitad].getNombre() == valorBuscado) {
	 * vias_encontradas.add(vias[mitad]); }
	 * 
	 * return vias_encontradas; }
	 */


	public static void main(String[] args) {
		try {
			FileInputStream fi = new FileInputStream(fichero);
			Scanner viales = new Scanner(fi);
			int lineas = 0;
			while (viales.hasNext()) {
				lineas++;
				viales.nextLine();
			}
			viales.close();
			fi = new FileInputStream(fichero);
			viales = new Scanner(fi);
			viales.nextLine(); // nos saltamos las cabeceras del fichero
			Callejero c = new Callejero(viales, lineas - 1);
			c.printViales();
			System.out.println();
			System.out.println(c.buscaVia("FILOMENA"));
			System.out.println(c.buscaViaCodigo(750700));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

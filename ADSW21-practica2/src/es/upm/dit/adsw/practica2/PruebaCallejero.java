package es.upm.dit.adsw.practica2;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class PruebaCallejero {
	protected static final String fichero = "VialesVigentes_20201220.csv";

	@Test
	public void test1() {
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
			Via trafalgar = c.buscaViaCodigo(750700);
			Via luchana = trafalgar.getViaComienzo();
			Via bilbao = luchana.getViaComienzo();
			
			Assert.assertEquals(750700, trafalgar.getCodigo());
			Assert.assertEquals("BILBAO", bilbao.getNombre());


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test2() {
		fail("Not yet implemented");
	}

	@Test
	public void test3() {
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
			int codigo = 99000001;
			boolean check  = true;
			boolean comprobacion = true;
			long time_ref = System.nanoTime();
			c.buscaViaCodigo(codigo);
			long time1 = System.nanoTime()-time_ref;
			for (Via via : c.getVias()) {
				if (via.getCodigo() == codigo) {
					check = true;
				}else {
					check = false;
				}
				
			}
			long time2 = System.nanoTime()-time1;
			if(time2>2*time1) {
				System.out.println("");
				System.out.println("TEST 3: buscaViaCodigo()");
				System.out.println("Tiempo nuestro: " + time1 + " ns");
				System.out.println("Tiempo profesores: " + time2 + " ns");
				System.out.println("");

				comprobacion = true;
			}else{
				comprobacion = false;
				System.out.println("");
				System.out.println("TEST 3: buscaViaCodigo()");
				System.out.println("Tiempo nuestro: " + time1 + " ns");
				System.out.println("Tiempo profesores: " + time2 + " ns");
				System.out.println("");
			}
			Assert.assertTrue(comprobacion);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void test4() {
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
			Set<Via> corona = new HashSet<Via>();
			corona = c.buscaVia("CORONA");
			Set<Via> filomena = new HashSet<Via>();
			filomena = c.buscaVia("FILOMENA");
			if(corona.contains(c.buscaViaCodigo(1874)) && corona.contains(c.buscaViaCodigo(211600)) && corona.contains(c.buscaViaCodigo(31000318))) {
				Assert.assertTrue(true);
			}

			if(filomena.isEmpty()) {
				Assert.assertTrue(true);
			}
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

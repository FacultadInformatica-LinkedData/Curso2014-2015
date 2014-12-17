package appBiblio;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import appBiblio.ShellBiblioApp;

//Clase que gestiona la ejecución del programa
public class AppBiblioMadrid {

	/**
	 * @param args
	 */
	private Label label; 
	 static Shell shell; //variable que contiene la shell sobre la que funcionará el programa
	public static void main(String[] args) throws Exception {
		Display display = new Display();
		shell = new ShellBiblioApp(display); //inicialización del programa con la shell que implementa el funcionamiento
	    shell.open(); //apertura del programa
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch()) {
	        display.sleep();
	      }
	    }
	    display.dispose(); //Cierre del programa

	}

}

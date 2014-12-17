package appBiblio;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.List;

 
/* 
	
Clase heredada de la clase Shell para implementar la funcionalidad de la aplicacion de bibliotecas de Madrid

*/

public class ShellBiblioApp extends Shell{

	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	private Label label;
	private Combo tipoBusqueda;
	private Combo tipoEvento;
	private Text biblioteca;
	private Text fecha;
	private Button btOk;
	private Button btTodo; 
	private Button btReset;
	private Consultas consultas;
	private int numPag=0;
	private Button btNextResults;
	private Button btPreviousResults;
	private Text eventosMostrar;
	private final int NINGUNA=0;
	private final int ACTIVIDADES_INFANTILES=1;
	private final int CONFERENCIAS_COLOQUIOS=2;
	private final int CUENTACUENTOS_TITERES_MARIONETAS=3;
	private final int CURSOS_TALLERES=4;
	private final int EXPOSICIONES=5;
	private final int RECITALES_PRESENTACIONES_ACTOS_LITERARIOS=6;
	private final int VISITAS_GUIADAS_TURISTICAS=7;



	
	
	public ShellBiblioApp(Display display) throws Exception {
		super(display); //llamada al constructor heredado para crear la ventana
		;
		consultas= new Consultas("/Users/Sanchez/Documents/workspace/AppBibloMadrid/src/appBiblio/eventosBiblioteca1.rdf");
		 label = new Label(this, SWT.NONE); 
		
		// creacion de un nuevo GridLayout con 2 columnas 
		    // de diferente tamano
		    GridLayout layout = new GridLayout(2, false);
		    this.setBounds(100, 100, 700, 600); //etablecer las medidas que tendra la ventana al iniciarse
		    this.setMinimumSize(700, 600); //establecer el tamano minimo de ventana que podra tener el programa
		    // aplicar el layout al shell
		    
		    this.setLayout(layout);
		    this.setText("Aplicacion Eventos Bibliotecas Madrid"); //definicion del nombre a mostrar en ventana
		    label.setText("Selecciona Tipo de Búsqueda: "); 
		    tipoBusqueda= new Combo(this, SWT.READ_ONLY);
		    tipoBusqueda.setItems(new String[]{"Busqueda por tipo","Busqueda por biblioteca", "Busqueda por fecha"});
		    tipoBusqueda.addSelectionListener(new SelectionAdapter() {
		    	@Override
		    	public void widgetSelected(SelectionEvent e) {
		    		Shell activo= e.display.getActiveShell();
		    		label.setText("");
		    		System.out.println("Seleccionado: "+tipoBusqueda.getSelectionIndex());
		    		tipoBusqueda.setEnabled(false);
		    		switch(tipoBusqueda.getSelectionIndex()){
		    			case 0:
		    				tipoEvento.setEnabled(true);
			    			btOk.setEnabled(true);
			    		    btReset.setEnabled(true);
		    				break;
		    			case 1:
		    				
		    				biblioteca.setEnabled(true);
		    				break;
		    			case 2: 		    	
		    				fecha.setEnabled(true);
		    				break;
		    		}
		    	
    				btOk.setEnabled(true);
	    		    btReset.setEnabled(true);
	    		    btTodo.setEnabled(true);
		    			
		    		
		    	}
			});
		    Label label=new Label(this,SWT.CENTER);
		    label.setText("Eventos Biblioteca: ");
		    tipoEvento= new Combo(this, SWT.READ_ONLY);
		    tipoEvento.setEnabled(false);
		    tipoEvento.setItems(new String[]{"0","1", "2", "3", "4", "5", "6", "7", "8"});
		    this.pack();
		    Label nombreBiblio =new Label(this, SWT.CENTER);
		    nombreBiblio.setText("Biblioteca: ");
		    biblioteca= new Text(this, SWT.SINGLE);
		    biblioteca.setEnabled(false);
		    Label fechaEvento= new Label(this, SWT.CENTER);
		    fechaEvento.setText("Fecha: ");
		    fecha= new Text(this, SWT.SINGLE);
		    fecha.setEnabled(false);
		    btOk=new Button(this, SWT.NONE);
		    btOk.setText("Buscar");
		    btOk.setEnabled(false);
		    btTodo=new Button(this, SWT.NONE);
		    btTodo.setText("Mostrar Todo");
		    btReset=new Button(this, SWT.NONE);
		    btReset.setText("Reset");
		    btReset.setEnabled(false);

		    
		    
		    
		    //Listeners Botones
		    btReset.addSelectionListener(new SelectionAdapter() {
		    	public void widgetSelected(SelectionEvent e){
		    		btReset.setEnabled(false);
		    		btOk.setEnabled(false);
		    		tipoBusqueda.setEnabled(true);
		    		biblioteca.setEnabled(false);
		    		fecha.setEnabled(false);
		    		tipoEvento.setEnabled(false);
		    		
		    	}
			});
		    
		    btOk.addSelectionListener(new SelectionAdapter() {@Override
		    public void widgetSelected(SelectionEvent e) {
		    	
		    	String buscar="";
		    	System.out.println(" Tipo Busqueda: "+tipoBusqueda.getSelectionIndex());
		    	System.out.println("Tipo evento"+ tipoEvento.getSelectionIndex());
		    	switch(tipoBusqueda.getSelectionIndex()){
		    		case 0:
		    			buscar+="/contenido/actividades";
		    			switch(tipoEvento.getSelectionIndex()){
		    			case NINGUNA: 
			    			break;
		    			case ACTIVIDADES_INFANTILES:
		    				buscar+="/ActividadesInfantiles";
		    				break;
			    		case  CONFERENCIAS_COLOQUIOS:
			    			buscar+="/ConferenciasColoquios";
			    			break; 
			    		case CUENTACUENTOS_TITERES_MARIONETAS:
			    			buscar+="/CuentacuentosTiteresMarionetas";
			    			break; 
			    		case CURSOS_TALLERES:
			    			buscar+="/CursosTalleres";
			    			break; 
			    		case EXPOSICIONES:
			    			buscar+="/Exposiciones";
			    			break; 
			    		case RECITALES_PRESENTACIONES_ACTOS_LITERARIOS:
			    			buscar+="/RecitalesPresentacionesActosLiterarios";
			    			break; 
			    		case VISITAS_GUIADAS_TURISTICAS: 
			    			buscar+="/VisitasGuiadasTuristicas";
			    			break; 
		    			}
		    		break;
		    		
		    		case 1: 
		    			buscar+=biblioteca.getText();
		    			break;
		    		case 2:
		    			buscar+=fecha.getText();
		    			break;
		    	}
				final List<String> resultados= generarResultados(consultas.consulta(buscar, tipoBusqueda.getSelectionIndex()));
		    	final Shell dialogShell = new Shell( SWT.PRIMARY_MODAL|SWT.RESIZE ); //creacion de una ventana emergente
	             dialogShell.setLayout(new GridLayout(1, false));
	             dialogShell.setBounds(100, 100, 700, 600); //establecimiento de tamano inicial de la ventana emergente
	 		    dialogShell.setMinimumSize(700, 600);//establecimiento de tamano minimo de la ventana emergente
	 		  
	 		   numPag=0;
	 		     eventosMostrar = new Text(dialogShell, SWT.MULTI | SWT.READ_ONLY | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
	 		    eventosMostrar.setText(resultados.get(numPag)); 
	           if(resultados.size()>1){
	        	   btNextResults = new Button(dialogShell, SWT.PUSH);
	        	   btNextResults.setText("Siguientes");
	        	    btPreviousResults = new Button(dialogShell, SWT.PUSH);
	        	   btPreviousResults.setText("Anteriores");
	        	   btNextResults.addSelectionListener(new SelectionAdapter() {
					
					@Override
					public void widgetSelected(SelectionEvent e) {
						if(numPag<resultados.size()-1){
							numPag++;
							System.out.println(numPag +" \n"+resultados.get(numPag));
							eventosMostrar.setText(resultados.get(numPag));
							dialogShell.redraw();
							}
						else
							btNextResults.setEnabled(false);
					}
					
					
					
				});
	        	btPreviousResults.addSelectionListener(new SelectionAdapter() 
	        	{
	        		@Override
	        		public void widgetSelected(SelectionEvent e) {
	        			if(numPag>0){
	        				numPag--;
	        				eventosMostrar.setText(resultados.get(numPag));
	        				if(btNextResults.getEnabled()==false) btNextResults.setEnabled(true);
	        				dialogShell.redraw();
	        			}
	        		}
				});
	           }
	           Button closeButton = new Button(dialogShell, SWT.PUSH);
	 		   closeButton.setText("Close");
	             closeButton.addSelectionListener(new SelectionAdapter() {
	                 @Override
	                 public void widgetSelected(SelectionEvent e) {
	                     dialogShell.dispose();
	                 }         
	             });
		     dialogShell.pack();
		     dialogShell.open();
		     dialogShell.redraw();
		     dialogShell.setVisible(true);
		    }
			});
		    
		    btTodo.addSelectionListener(new SelectionAdapter(){public void widgetSelected(SelectionEvent e) {
		    	@SuppressWarnings("static-access")
				final List<String> resultados= generarResultados(consultas.mostrarTodo());
		    	final Shell dialogShell = new Shell( SWT.PRIMARY_MODAL|SWT.RESIZE ); //creacion de una ventana emergente
	             dialogShell.setLayout(new GridLayout(1, false));
	             dialogShell.setBounds(100, 100, 700, 600); //establecimiento de tamano inicial de la ventana emergente
	 		    dialogShell.setMinimumSize(700, 600);//establecimiento de tamano minimo de la ventana emergente
	 		  
	 		   numPag=0;
	 		     eventosMostrar = new Text(dialogShell, SWT.MULTI | SWT.READ_ONLY | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
	 		    eventosMostrar.setText(resultados.get(numPag)); 
	           if(resultados.size()>1){
	        	   btNextResults = new Button(dialogShell, SWT.PUSH);
	        	   btNextResults.setText("Siguientes");
	        	    btPreviousResults = new Button(dialogShell, SWT.PUSH);
	        	   btPreviousResults.setText("Anteriores");
	        	   btNextResults.addSelectionListener(new SelectionAdapter() {
					
					@Override
					public void widgetSelected(SelectionEvent e) {
						if(numPag<resultados.size()-1){
							numPag++;
							eventosMostrar.setText(resultados.get(numPag));
							dialogShell.redraw();
							}
						else
							btNextResults.setEnabled(false);
					}
					
					
					
				});
	        	btPreviousResults.addSelectionListener(new SelectionAdapter() 
	        	{
	        		@Override
	        		public void widgetSelected(SelectionEvent e) {
	        			if(numPag>0){
	        				numPag--;
	        				eventosMostrar.setText(resultados.get(numPag));
	        				if(btNextResults.getEnabled()==false) btNextResults.setEnabled(true);
	        				dialogShell.redraw();
	        			}
	        		}
				});
	           }
	           Button closeButton = new Button(dialogShell, SWT.PUSH);
	 		   closeButton.setText("Close");
	             closeButton.addSelectionListener(new SelectionAdapter() {
	                 @Override
	                 public void widgetSelected(SelectionEvent e) {
	                     dialogShell.dispose();
	                 }         
	             });
		     dialogShell.pack();
		     dialogShell.open();
		     dialogShell.redraw();
		     dialogShell.setVisible(true);
	}});
}
	public List<String> generarResultados(List<String> res){
		List<String> resultsDivided=new ArrayList<String>();;
		int max=(res.size())%10;
		String mensaje="";
		if( res.size()<10){
			if(res.size()==0){
				mensaje+="No hay resultados";
				resultsDivided.add(mensaje);
				return resultsDivided;
			}
			
			for(int i=0; i<res.size();i++){
				String mostrar=" ";
				mostrar+=res.get(i)+"\n \n";
				resultsDivided.add(mostrar);
			}
			return resultsDivided;
			
		}
		else{
		
			for(int i=0;i<max;i++){
				String mostrar=" ";
				for(int j=i*10; j<10;j++){
					mostrar+=res.get(j)+"\n \n";
				}
				resultsDivided.add(mostrar);
			}
		}
		return resultsDivided;
	}
	 

	@Override
	protected void checkSubclass() {
		// TODO Auto-generated method stub
		//super.checkSubclass();
	}
	

	
}
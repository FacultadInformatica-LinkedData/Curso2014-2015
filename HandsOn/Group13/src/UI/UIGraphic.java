package UI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.SystemColor;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Map.Entry;

import Entities.*;

public class UIGraphic extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel PanelInformation;
	private static JTextField longitud;
	private static JTextField latitude;
	
	static JButton results0;
	static JButton result1;
	static JButton result2;
	static JButton result3;
	static JButton result4;
	static JButton result5;
	
	static JTextField textField;
	static JTextField textField_1;
	static JTextField textField_2;
	static JTextField textField_3;
	static JTextField textField_4;
	static JTextField textField_5;
	static JTextField textField_6;
	
	public static JButton[] buttons = new JButton[6];
	
	/**
	 * Create the frame.
	 */
	public UIGraphic() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 230, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setResizable(false);
		
		JPanel JPanel_Menu = new JPanel();
		JPanel_Menu.setBackground(SystemColor.activeCaption);
		JPanel_Menu.setBounds(0, 0, 224, 56);
		contentPane.add(JPanel_Menu);
		JPanel_Menu.setLayout(null);
		
		JLabel lblLatitude = new JLabel("Latitude");
		lblLatitude.setBounds(21, 11, 53, 14);
		JPanel_Menu.add(lblLatitude);
		
		latitude = new JTextField();
		latitude.setBounds(21, 25, 86, 20);
		JPanel_Menu.add(latitude);
		latitude.setColumns(10);
		
		longitud = new JTextField();
		longitud.setBounds(117, 25, 86, 20);
		JPanel_Menu.add(longitud);
		longitud.setColumns(10);
		
		JLabel lblLongitude = new JLabel("Longitude");
		lblLongitude.setBounds(117, 11, 60, 14);
		JPanel_Menu.add(lblLongitude);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.readingRDF();
			}
		});
		btnSearch.setBounds(62, 67, 89, 23);
		contentPane.add(btnSearch);
		
		PanelInformation = new JPanel();
		PanelInformation.setBounds(100, 100, 230, 378);
		
		JButton back = new JButton("Atras");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(true);
				setContentPane(contentPane);
			}
		});
		PanelInformation.setLayout(null);
		back.setBounds(80, 232, 68, 23);
		PanelInformation.add(back);

		textField = new JTextField();
		textField.setBounds(10, 11, 205, 20);
		PanelInformation.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(10, 42, 205, 20);
		PanelInformation.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(10, 73, 205, 20);
		PanelInformation.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(10, 104, 205, 20);
		PanelInformation.add(textField_3);
		textField_3.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setBounds(10, 135, 205, 20);
		PanelInformation.add(textField_4);
		textField_4.setColumns(10);

		JButton btnNewButton = new JButton("To GoogleMaps");
		btnNewButton.setBounds(60, 266, 110, 23);
		PanelInformation.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				String url = "https://www.google.es/maps/dir/"+textField_4.getText()+","+textField_5.getText();
				try {
					java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(10, 166, 205, 20);
		PanelInformation.add(textField_5);

		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(10, 197, 205, 20);
		PanelInformation.add(textField_6);
		
		results0 = new JButton("");
		results0.setBounds(0, 100, 224, 42);
		contentPane.add(results0);
		results0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				setContentPane(PanelInformation);
				int t=0;
				for (Entry<Integer, Sitio> elemento : Controller.getSortedMap().entrySet()) {
				    if(t==0){
				    	textField.setText((elemento.getValue().getName()));
				    	textField_1.setText((elemento.getValue().getAddress()));
				    	textField_2.setText((elemento.getValue().getPostalCode()));
				    	textField_3.setText((elemento.getValue().getTelephone()));
				    	textField_4.setText((elemento.getValue().getLatitude().toString()));
				    	textField_5.setText((elemento.getValue().getLongitude().toString()));
				    	
				    	if(!elemento.getValue().isAccesibility()){
				    		textField_6.setText("No Accesible Minusválidos");
				    	}else{
				    		textField_6.setText("Accesible Minusválidos");
				    	}
				    }
				    t++;
				}
			}
			
		});
		
		result1 = new JButton("");
		result1.setBounds(0, 142, 224, 42);
		contentPane.add(result1);
		result1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				setContentPane(PanelInformation);
				int t=0;
				for (Entry<Integer, Sitio> elemento : Controller.getSortedMap().entrySet()) {
				    if(t==1){
				    	textField.setText((elemento.getValue().getName()));
				    	textField_1.setText((elemento.getValue().getAddress()));
				    	textField_2.setText((elemento.getValue().getPostalCode()));
				    	textField_3.setText((elemento.getValue().getTelephone()));
				    	textField_4.setText((elemento.getValue().getLatitude().toString()));
				    	textField_5.setText((elemento.getValue().getLongitude().toString()));
				    	
				    	if(!elemento.getValue().isAccesibility()){
				    		textField_6.setText("No Accesible Minusválidos");
				    	}else{
				    		textField_6.setText("Accesible Minusválidos");
				    	}
				    }
				    t++;
				}
			}
		});
		
		result2 = new JButton("");
		result2.setBounds(0, 183, 224, 42);
		contentPane.add(result2);
		result2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				setContentPane(PanelInformation);
				int t=0;
				for (Entry<Integer, Sitio> elemento : Controller.getSortedMap().entrySet()) {
				    if(t==2){
				    	textField.setText((elemento.getValue().getName()));
				    	textField_1.setText((elemento.getValue().getAddress()));
				    	textField_2.setText((elemento.getValue().getPostalCode()));
				    	textField_3.setText((elemento.getValue().getTelephone()));
				    	textField_4.setText((elemento.getValue().getLatitude().toString()));
				    	textField_5.setText((elemento.getValue().getLongitude().toString()));
				    	
				    	if(!elemento.getValue().isAccesibility()){
				    		textField_6.setText("No Accesible Minusválidos");
				    	}else{
				    		textField_6.setText("Accesible Minusválidos");
				    	}
				    }
				    t++;
				}
			}
		});
		
		result3 = new JButton("");
		result3.setBounds(0, 225, 224, 42);
		contentPane.add(result3);
		result3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				setContentPane(PanelInformation);
				int t=0;
				for (Entry<Integer, Sitio> elemento : Controller.getSortedMap().entrySet()) {
				    if(t==3){
				    	textField.setText((elemento.getValue().getName()));
				    	textField_1.setText((elemento.getValue().getAddress()));
				    	textField_2.setText((elemento.getValue().getPostalCode()));
				    	textField_3.setText((elemento.getValue().getTelephone()));
				    	textField_4.setText((elemento.getValue().getLatitude().toString()));
				    	textField_5.setText((elemento.getValue().getLongitude().toString()));
				    	
				    	if(!elemento.getValue().isAccesibility()){
				    		textField_6.setText("No Accesible Minusválidos");
				    	}else{
				    		textField_6.setText("Accesible Minusválidos");
				    	}
				    }
				    t++;
				}
			}
		});
		
		result4 = new JButton("");
		result4.setBounds(0, 266, 224, 42);
		contentPane.add(result4);
		result4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				setContentPane(PanelInformation);
				int t=0;
				for (Entry<Integer, Sitio> elemento : Controller.getSortedMap().entrySet()) {
				    if(t==4){
				    	textField.setText((elemento.getValue().getName()));
				    	textField_1.setText((elemento.getValue().getAddress()));
				    	textField_2.setText((elemento.getValue().getPostalCode()));
				    	textField_3.setText((elemento.getValue().getTelephone()));
				    	textField_4.setText((elemento.getValue().getLatitude().toString()));
				    	textField_5.setText((elemento.getValue().getLongitude().toString()));
				    	
				    	if(!elemento.getValue().isAccesibility()){
				    		textField_6.setText("No Accesible Minusválidos");
				    	}else{
				    		textField_6.setText("Accesible Minusválidos");
				    	}
				    }
				    t++;
				}
			}
		});
		
		result5 = new JButton("");
		result5.setBounds(0, 305, 224, 42);
		contentPane.add(result5);
		result5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				setContentPane(PanelInformation);
				int t=0;
				for (Entry<Integer, Sitio> elemento : Controller.getSortedMap().entrySet()) {
				    if(t==5){
				    	textField.setText((elemento.getValue().getName()));
				    	textField_1.setText((elemento.getValue().getAddress()));
				    	textField_2.setText((elemento.getValue().getPostalCode()));
				    	textField_3.setText((elemento.getValue().getTelephone()));
				    	textField_4.setText((elemento.getValue().getLatitude().toString()));
				    	textField_5.setText((elemento.getValue().getLongitude().toString()));
				    	
				    	if(!elemento.getValue().isAccesibility()){
				    		textField_6.setText("No Accesible Minusválidos");
				    	}else{
				    		textField_6.setText("Accesible Minusválidos");
				    	}
				    }
				    t++;
				}
			}
		});
		
		buttons[0]=results0;
		buttons[1]=result1;
		buttons[2]=result2;
		buttons[3]=result3;
		buttons[4]=result4;
		buttons[5]=result5;
	}

	public static JTextField getLongitud() {
		return longitud;
	}

	public static void setLongitud(JTextField longitud) {
		UIGraphic.longitud = longitud;
	}

	public static JTextField getLatitude() {
		return latitude;
	}

	public static void setLatitude(JTextField latitude) {
		UIGraphic.latitude = latitude;
	}
}

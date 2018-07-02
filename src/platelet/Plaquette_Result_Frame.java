package platelet;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Plaquette_Result_Frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel, lblPatientId , lblDateOfBirth, lblNewLabel_1, lblNewLabel_2, lblSex, lblPlatletLifeTime;
	private String name ="Patient Name : ";
	private String id="Patient ID : ";
	String plateletCount="Platelet Count : ";
	String plateletDate="Platelet Date : ";
	String refferingPhysician="Reffering Physician : ";
	private Date dob=new Date();
	private int weight =0;
	private int height=0;
	private boolean sex=false;
	private Date Injectiondate=new Date();
	private double platletLifeTime=0;
	private double platletHalf=0;
	private JPanel panel_Curve = new JPanel();
	private SimpleDateFormat  simpleFormat = new SimpleDateFormat("yyyy/MM/dd");
	private DecimalFormat df = new DecimalFormat("0.00"); 
	private JPanel panel_Recuperation_Title;
	private JLabel lblFractionRecuperation;
	private JTable table = new JTable();
	private JPanel panel_RadioPharmaceutical_Report;
	private JPanel panel_RadioPharmaceuticalReport;
	private JLabel lblRadiopharmaceutical;
	private JPanel panel_RadioPharmaceuticalCenter;
	private JLabel lbl_ACDANumber;
	private JLabel lbl_ACDA_Date;
	private JLabel lbl_TRIS_Number;
	private JPanel panel_4;
	private JPanel panel_PatientIdentificationTitle;
	private JLabel lblPatientIdentification;
	private JLabel lbl_TRIS_Date;
	private JLabel lbl_NACL_Number;
	private JLabel lbl_NaCL_Date;
	private JLabel lbl_In_Number;
	private JLabel lbl_In_Date;
	private JPanel panel_Quality_Control;
	private JLabel lbl_Blood_Volume;
	private JLabel lbl_RendementMarquage;
	private JPanel panel_5;
	private JLabel lbl_Platlet_Activity;
	private JLabel lbl_RedCellActivity;
	private JLabel lbl_Plasma_Activity;
	private JLabel lblPlateletCount;
	private JLabel lblRefering_Physician;
	private JLabel lbl_InjectedActivity;
	// radiopharmaceutical 
	private String acdaNumber, trisNumber, naclNumber, inNumber;
	private Date acdaDate, trisDate, naclDate, inDate;
	// labelling value
	private String injectedActivity,volume, rendement, plateletActivity, redCellActivity, plasmaActivity;
	private JLabel lblPlateletDate;
	private Component horizontalStrut;
	private JButton btnChangeFitPoints;
	private JButton btnCapture;
	//Controleur
	private Plaquette_Controleur controleur;
	private JLabel lblTNa;
	private Component horizontalStrut_1;
	private Component horizontalStrut_2;
	private JComboBox<String> comboBox;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Plaquette_Result_Frame frame = new Plaquette_Result_Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
public Plaquette_Result_Frame() {
	buildGUI();
}
public Plaquette_Result_Frame(Plaquette_Controleur controleur) {
	this.controleur=controleur;
}
public void buildGUI() {
	makeGUI();
	pack();
	setSize(this.getPreferredSize());
}


public void setPatientIdentification (String lastName, String firstName, String id, Date dob, int weight, int height, boolean masculin, Date injectionDate, double platletLifeTime, double platletHalf, BufferedImage image, BufferedImage imageLn, int plateletCount, Date plateletDate, String refferingPhysician) {
	name +=lastName+"^"+firstName;
	this.id += id;
	this.dob=dob;
	this.weight=weight;
	this.height=height;
	this.sex=masculin;
	this.platletLifeTime=platletLifeTime;
	this.platletHalf=platletHalf;
	panel_Curve.add(new JLabel(new ImageIcon(image)));
	panel_Curve.add(new JLabel(new ImageIcon(imageLn)));
	this.plateletCount += plateletCount;
	this.refferingPhysician +=refferingPhysician;
	this.plateletDate+=simpleFormat.format(plateletDate);
	this.Injectiondate=injectionDate;
	
	
}

public void setFractionRecuperation(double[][] fractionsRecuperation) {
	//Cree table avec les fraction de recuperation
			DefaultTableModel model=(DefaultTableModel) table.getModel();
			model.addRow(new Object[] {"Time (hour)"});
			model.addRow(new Object[] {"%"});
			model.addColumn("title");
			table.setValueAt("%", 1, 0);
			table.setValueAt("Hours", 0, 0);
			for (int i=0; i<fractionsRecuperation.length ; i++) {
				model.addColumn(i);
				table.setValueAt(df.format(fractionsRecuperation[1][i]*100), 1, i+1);
				table.setValueAt(fractionsRecuperation[0][i], 0, i+1);
			}
}

public void setRadiopharmaceutical(String acdaNumber, Date acdaDate, String trisNumber, Date trisDate, String naclNumber, Date naclDate, String inNumber, Date inDate) {
	this.acdaNumber=acdaNumber;
	this.acdaDate=acdaDate;
	this.trisNumber=trisNumber;
	this.trisDate=trisDate;
	this.naclNumber=naclNumber;
	this.naclDate=naclDate;
	this.inNumber=inNumber;
	this.inDate=inDate;	
}

public void setLabellingValue(double injectedActivity,double volume, int rendement, int plateletActivity, int redCellActivity, int plasmaActivity) {
	this.injectedActivity=String.valueOf(injectedActivity);
	this.volume=String.valueOf(volume);
	this.rendement=String.valueOf(rendement);
	this.plateletActivity=String.valueOf(plateletActivity);
	this.redCellActivity=String.valueOf(redCellActivity);
	this.plasmaActivity=String.valueOf(plasmaActivity);
}
	
	private void makeGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Results Platlet");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblPla = new JLabel("Platlets Results - ");
		lblPla.setFont(new Font("Tahoma", Font.BOLD, 17));
		panel_1.add(lblPla);
		
		JLabel lblDate = new JLabel(simpleFormat.format(Injectiondate));
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(lblDate);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		
		
		panel_4 = new JPanel();
		panel_4.setForeground(Color.LIGHT_GRAY);
		panel_4.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_2.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Patient_Identification = new JPanel();
		panel_Patient_Identification.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.add(panel_Patient_Identification, BorderLayout.CENTER);
		panel_Patient_Identification.setLayout(new GridLayout(0, 3, 10, 10));
		
		lblNewLabel = new JLabel(name);
		panel_Patient_Identification.add(lblNewLabel);
		
		lblPatientId = new JLabel(id);
		panel_Patient_Identification.add(lblPatientId);
		
		lblDateOfBirth = new JLabel(simpleFormat.format(dob));
		panel_Patient_Identification.add(lblDateOfBirth);
		
		lblNewLabel_1 = new JLabel(String.valueOf(weight)+"kg");
		panel_Patient_Identification.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel(String.valueOf(height)+"cm");
		panel_Patient_Identification.add(lblNewLabel_2);
		
		lblSex = new JLabel();
		panel_Patient_Identification.add(lblSex);
		
		lblPlateletCount = new JLabel(this.plateletCount);
		panel_Patient_Identification.add(lblPlateletCount);
		
		lblPlateletDate = new JLabel(this.plateletDate);
		panel_Patient_Identification.add(lblPlateletDate);
		
		lblRefering_Physician = new JLabel(this.refferingPhysician);
		panel_Patient_Identification.add(lblRefering_Physician);
		if (sex) lblSex.setText("M");
		if (!sex) lblSex.setText("F");
		
		panel_PatientIdentificationTitle = new JPanel();
		panel_PatientIdentificationTitle.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.add(panel_PatientIdentificationTitle, BorderLayout.NORTH);
		
		lblPatientIdentification = new JLabel("Patient Identification");
		panel_PatientIdentificationTitle.add(lblPatientIdentification);
		
		horizontalStrut = Box.createHorizontalStrut(20);
		panel_2.add(horizontalStrut);
		
		panel_RadioPharmaceutical_Report = new JPanel();
		panel_RadioPharmaceutical_Report.setForeground(Color.LIGHT_GRAY);
		panel_RadioPharmaceutical_Report.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_2.add(panel_RadioPharmaceutical_Report);
		panel_RadioPharmaceutical_Report.setLayout(new BorderLayout(0, 0));
		
		panel_RadioPharmaceuticalReport = new JPanel();
		panel_RadioPharmaceuticalReport.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_RadioPharmaceutical_Report.add(panel_RadioPharmaceuticalReport, BorderLayout.NORTH);
		
		lblRadiopharmaceutical = new JLabel("RadioPharmaceutical");
		panel_RadioPharmaceuticalReport.add(lblRadiopharmaceutical);
		
		panel_RadioPharmaceuticalCenter = new JPanel();
		panel_RadioPharmaceuticalCenter.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_RadioPharmaceutical_Report.add(panel_RadioPharmaceuticalCenter, BorderLayout.CENTER);
		panel_RadioPharmaceuticalCenter.setLayout(new GridLayout(0, 1, 10, 0));
		
		panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_RadioPharmaceuticalCenter.add(panel_5);
		panel_5.setLayout(new GridLayout(0, 2, 0, 0));
		
		lbl_ACDANumber = new JLabel("ACDA Number : "+ acdaNumber);
		panel_5.add(lbl_ACDANumber);
		
		lbl_ACDA_Date = new JLabel("ACDA Date : "+ simpleFormat.format(acdaDate));
		panel_5.add(lbl_ACDA_Date);
		
		lbl_TRIS_Number = new JLabel("TRIS Number : "+ trisNumber);
		panel_5.add(lbl_TRIS_Number);
		
		lbl_TRIS_Date = new JLabel("TRIS Date : "+ simpleFormat.format(trisDate));
		panel_5.add(lbl_TRIS_Date);
		
		lbl_NACL_Number = new JLabel("NACL Number : "+ naclNumber);
		panel_5.add(lbl_NACL_Number);
		
		lbl_NaCL_Date = new JLabel("NACL Date : "+ simpleFormat.format(naclDate));
		panel_5.add(lbl_NaCL_Date);
		
		lbl_In_Number = new JLabel("111In number : "+ inNumber);
		panel_5.add(lbl_In_Number);
		
		lbl_In_Date = new JLabel("111In Date : "+ simpleFormat.format(inDate));
		panel_5.add(lbl_In_Date);
		
		panel_Quality_Control = new JPanel();
		panel_Quality_Control.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_RadioPharmaceuticalCenter.add(panel_Quality_Control);
		panel_Quality_Control.setLayout(new GridLayout(0, 2, 10, 0));
		
		lbl_InjectedActivity = new JLabel("Injected activity : "+this.injectedActivity +"Mbq");
		panel_Quality_Control.add(lbl_InjectedActivity);
		
		lbl_Blood_Volume = new JLabel("Blood Volume : "+ this.volume +"mL");
		panel_Quality_Control.add(lbl_Blood_Volume);
		
		lbl_RendementMarquage = new JLabel("Rendement Marquage : " + this.rendement +"%");
		panel_Quality_Control.add(lbl_RendementMarquage);
		
		lbl_Platlet_Activity = new JLabel("Platelet Activity : "+this.plateletActivity +"%");
		panel_Quality_Control.add(lbl_Platlet_Activity);
		
		lbl_RedCellActivity = new JLabel("Red Cell Activity : " + this.redCellActivity + "%");
		panel_Quality_Control.add(lbl_RedCellActivity);
		
		lbl_Plasma_Activity = new JLabel("Plasma Activity : "+this.plasmaActivity + "%");
		panel_Quality_Control.add(lbl_Plasma_Activity);
		
		
		JPanel panel_Image = new JPanel();
		contentPane.add(panel_Image, BorderLayout.CENTER);
		panel_Image.setLayout(new BorderLayout(0, 0));
		
		panel_Recuperation_Title = new JPanel();
		panel_Image.add(panel_Recuperation_Title, BorderLayout.SOUTH);
		
		lblFractionRecuperation = new JLabel("Fraction Recuperation");
		panel_Recuperation_Title.add(lblFractionRecuperation);
		table.setRowSelectionAllowed(false);
		panel_Recuperation_Title.add(table);
		
		
		panel_Image.add(panel_Curve, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.SOUTH);
		
		lblPlatletLifeTime = new JLabel("Platlet Life Time : "+df.format(this.platletLifeTime)+" days");
		lblPlatletLifeTime.setFont(new Font("Tahoma", Font.BOLD, 17));
		panel_3.add(lblPlatletLifeTime);
		
		btnChangeFitPoints = new JButton("Change Fit points");
		btnChangeFitPoints.addActionListener(controleur);
		
		horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_3.add(horizontalStrut_1);
		
		lblTNa = new JLabel("Platelet T1/2 : "+df.format(platletHalf) + " Hours");
	
		lblTNa.setFont(new Font("Dialog", Font.BOLD, 17));
		panel_3.add(lblTNa);
		
		horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel_3.add(horizontalStrut_2);
		panel_3.add(btnChangeFitPoints);
		
		btnCapture = new JButton("Capture");
		btnCapture.addActionListener(controleur);
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Linear", "Exponential", "Keep Values"}));
		comboBox.setSelectedIndex(0);
		panel_3.add(comboBox);
		panel_3.add(btnCapture);
	}
	
	public JButton getCaptureButton() {
		return btnCapture;
	}
	public JButton getChangeFitButton() {
		return btnChangeFitPoints;
	}
	public JPanel getContentPane() {
		return this.contentPane;
	}
	public int getResultChoice() {
		return comboBox.getSelectedIndex();
	}
	public JLabel getLabelPlatletLifeTime() {
		return lblPlatletLifeTime;
	}
	public JLabel getLabelTHalf() {
		return lblTNa;
	}
	public JComboBox<String> getCombobox(){
		return comboBox;
	}

}

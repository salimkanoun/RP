import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Image;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

public class Volume_Result_Frame extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblName, lblPatientId , lblDateOfBirth, lbl_PatientWeight, lbl_Patient_Height, lblSex;
	private String name ="Patient Name : ";
	private String id="Patient ID : ";
	private String refferingPhysician="Reffering Physician : ";
	private Date dob=new Date();
	private int weight =0;
	private int height=0;
	private boolean sex=false;
	private Date Injectiondate=new Date();
	private SimpleDateFormat  simpleFormat = new SimpleDateFormat("yyyy/MM/dd");
	private DecimalFormat df = new DecimalFormat("0.00"); 
	private JPanel panel_RadioPharmaceutical_Report;
	private JPanel panel_RadioPharmaceuticalReport;
	private JLabel lblRadiopharmaceutical;
	private JPanel panel_RadioPharmaceuticalCenter;
	private JPanel panel_4;
	private JPanel panel_PatientIdentificationTitle;
	private JLabel lblPatientIdentification;
	private JPanel panel_Quality_Control;
	private JPanel panel_5;
	private JLabel lblRefering_Physician;
	private Component horizontalStrut;
	private JButton btnCapture;
	//Controleur
	private Volume_Controleur controleur;
	private JLabel lblCorporalSurface;
	private JLabel lbl_CR51;
	private JLabel lbl_InjectedActivity;
	private JLabel lblRendementMarquage;
	private JPanel panel_Biology;
	private JLabel lbl_Hematocrite;
	private JLabel lbl_TheoricalWeight_Ratio;
	private JPanel panel_Table_Resultat;
	private JLabel lbl_Volume;
	private JLabel lblMeasured;
	private JLabel lblCalculated;
	private JLabel lblDifference;
	private JLabel lblRatio;
	private JLabel lblRedCell;
	private JLabel lblMeasurerc;
	private JLabel lblDiffredcell;
	private JLabel lblCalculrc;
	private JLabel lblRatiorc;
	private JLabel lblPlasma;
	private JLabel lbl_MesurePlasma;
	private JLabel lblCalculplasma;
	private JLabel lblDifferenceplasma;
	private JLabel lblRatioplasma;
	private JPanel panel_7;
	private JPanel panel_3;
	
	// results
	private double volumeGlobulaireMesure=0;
	private double volumePlasmatiqueMesure=0;
	private double volumeGlobulaireTheorique=0;
	private double volumePlasmatiqueTheorique=0;
	private double hematocrite=0;
	private double theoricalWeightRatio=0;
	private double surfaceCorporelle=0;
	private String modeleSC="   ";
	private Component horizontalStrut_1;
	private JPanel panel_Conclusions;
	private JLabel lblBloodVolume;
	private JLabel lblNewLabel_1;
	private JLabel lblConclusion;
	private JLabel lbl_RedCell_conclusion;
	private JLabel lbl_Plasma_Conclusion;
	private JLabel lblAge;
	private JLabel lbl_CR51_Number_Value;
	private JLabel lbl51CrDate;
	private JLabel lbl_CR51_Date_Value;
	private JLabel lblNaclNumber;
	private JLabel lbl_NaCl_Number_Value;
	private JLabel lblDate_2;
	private JLabel lbl_NaCl_Date_Value;
	private JLabel lbl_InjectedActivity_Value;
	private JLabel lbl_Rendement_Marquage_Value;
	private JLabel lblRelatifVolume;
	private JLabel lbl_RelatifVolume_GR;
	private JLabel lbl_RelatifVolume_Plasma;
	private JLabel lblTotal;
	private JLabel lblTotalvalue;
	private JPanel panel_6;
	private JPanel panel_8;
	private JComboBox<String> comboBox_GeneralConclusion;
	private JLabel lblGenral_Conclusion_Label;
	private JLabel lblInjectedActivityMbq;
	private JLabel lbl_InjectedActivityMqb_Value;
	private JLabel lblcrActivitymbq;
	private JLabel lbl_51Cr_Activity;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Volume_Result_Frame frame = new Volume_Result_Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param controleur_Volume 
	 */
public Volume_Result_Frame() {
	setBackground(Color.WHITE);
	buildGUI();
}
public Volume_Result_Frame(Volume_Controleur controleur_Volume) {
	this.controleur=controleur_Volume;
}
public void buildGUI() {
	makeGUI();
	colorValue();
	makeConclusions();
	calculerAge();
	pack();
	setSize(this.getPreferredSize());
}


public void setPatientIdentification (String lastName, String firstName, String id, Date dob, int weight, int height, boolean masculin, double surfaceCorporelle, String modeleSC, Date injectionDate, String refferingPhysician) {
	name +=lastName.toUpperCase()+"^"+firstName.toUpperCase();
	this.id += id;
	this.dob=dob;
	this.weight=weight;
	this.height=height;
	this.sex=masculin;
	this.surfaceCorporelle=surfaceCorporelle;
	this.modeleSC=modeleSC;
	this.refferingPhysician +=refferingPhysician;
	this.Injectiondate=injectionDate;
	
	
}


public void setRadiopharmaceutical(String Cr51Number, Date Cr51Date, String NaclNumber, Date NaClDate, double crActivity) {
	this.lbl_CR51_Number_Value.setText(Cr51Number);
	this.lbl_CR51_Date_Value.setText(simpleFormat.format(Cr51Date));
	this.lbl_NaCl_Number_Value.setText(NaclNumber);
	this.lbl_NaCl_Date_Value.setText(simpleFormat.format(NaClDate));
	this.lbl_51Cr_Activity.setText(String.valueOf(crActivity));
	
}

public void setLabellingValue(double rendementMarquage,double activity, double cr51Activity, double fractionMasseInjectee) {
	lbl_Rendement_Marquage_Value.setText(String.valueOf(Math.round(rendementMarquage*100)));
	lbl_InjectedActivity_Value.setText(String.valueOf(Math.round(activity)));
	lbl_InjectedActivityMqb_Value.setText(String.valueOf(df.format(cr51Activity * (fractionMasseInjectee) * rendementMarquage)));
	
}

public void setHematocriteWeightRatio(double hematocrite, double weightRatio) {
	this.hematocrite=hematocrite;
	this.theoricalWeightRatio=weightRatio;
}

public void setResults(double volumeGlobulaireMesure, double volumePlasmatiqueMesure, double volumeGlobulaireTheorique, double volumePlasmatiqueTheorique) {
	this.volumeGlobulaireMesure=volumeGlobulaireMesure;
	this.volumePlasmatiqueMesure=volumePlasmatiqueMesure;
	this.volumeGlobulaireTheorique=volumeGlobulaireTheorique;
	this.volumePlasmatiqueTheorique=volumePlasmatiqueTheorique;
}
	
	private void makeGUI() {
		Image image = new ImageIcon(ClassLoader.getSystemResource("logos/ImageJ.png")).getImage();
		setIconImage(image);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Red Cell Mass Results");
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblPla = new JLabel("Blood Volume / Red Cell Mass Results - ");
		lblPla.setBackground(Color.WHITE);
		lblPla.setFont(new Font("Tahoma", Font.BOLD, 17));
		panel_1.add(lblPla);
		
		JLabel lblDate = new JLabel(simpleFormat.format(Injectiondate));
		lblDate.setBackground(Color.WHITE);
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(lblDate);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel.add(panel_2, BorderLayout.CENTER);
		
		
		panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_4.setForeground(Color.LIGHT_GRAY);
		panel_4.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_2.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Patient_Identification = new JPanel();
		panel_Patient_Identification.setBackground(Color.WHITE);
		panel_Patient_Identification.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.add(panel_Patient_Identification, BorderLayout.CENTER);
		panel_Patient_Identification.setLayout(new GridLayout(0, 3, 10, 10));
		
		lblName = new JLabel(name);
		lblName.setBorder(new EmptyBorder(0, 5, 0, 0));
		panel_Patient_Identification.add(lblName);
		
		lblPatientId = new JLabel(id);
		panel_Patient_Identification.add(lblPatientId);
		
		lblDateOfBirth = new JLabel(simpleFormat.format(dob));
		lblDateOfBirth.setBorder(new EmptyBorder(0, 0, 0, 5));
		panel_Patient_Identification.add(lblDateOfBirth);
		
		lbl_PatientWeight = new JLabel("Weight : "+String.valueOf(weight)+"kg");
		lbl_PatientWeight.setBorder(new EmptyBorder(0, 5, 0, 0));
		panel_Patient_Identification.add(lbl_PatientWeight);
		
		lbl_Patient_Height = new JLabel("Height : "+String.valueOf(height)+"cm");
		panel_Patient_Identification.add(lbl_Patient_Height);
		
		lblSex = new JLabel();
		lblSex.setBorder(new EmptyBorder(0, 0, 0, 5));
		panel_Patient_Identification.add(lblSex);
		
		lblCorporalSurface = new JLabel("Corporal Surface : " + df.format(this.surfaceCorporelle) + "m2 - "+modeleSC.substring(3));
		lblCorporalSurface.setBorder(new EmptyBorder(0, 5, 0, 0));
		panel_Patient_Identification.add(lblCorporalSurface);
		
		lblAge = new JLabel("Age : ");
		panel_Patient_Identification.add(lblAge);
		
		lblRefering_Physician = new JLabel(this.refferingPhysician);
		lblRefering_Physician.setBorder(new EmptyBorder(0, 0, 0, 5));
		panel_Patient_Identification.add(lblRefering_Physician);
		if (sex) lblSex.setText("Sex : M");
		if (!sex) lblSex.setText("Sex : F");
		
		panel_PatientIdentificationTitle = new JPanel();
		panel_PatientIdentificationTitle.setBackground(Color.WHITE);
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
		panel_RadioPharmaceuticalReport.setBackground(Color.WHITE);
		panel_RadioPharmaceuticalReport.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_RadioPharmaceutical_Report.add(panel_RadioPharmaceuticalReport, BorderLayout.NORTH);
		
		lblRadiopharmaceutical = new JLabel("RadioPharmaceutical");
		panel_RadioPharmaceuticalReport.add(lblRadiopharmaceutical);
		
		panel_RadioPharmaceuticalCenter = new JPanel();
		panel_RadioPharmaceuticalCenter.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_RadioPharmaceutical_Report.add(panel_RadioPharmaceuticalCenter, BorderLayout.CENTER);
		panel_RadioPharmaceuticalCenter.setLayout(new GridLayout(0, 1, 10, 0));
		
		panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		panel_5.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_RadioPharmaceuticalCenter.add(panel_5);
		panel_5.setLayout(new GridLayout(0, 4, 10, 0));
		
		lbl_CR51 = new JLabel("51Cr Number : ");
		lbl_CR51.setBorder(new EmptyBorder(0, 5, 0, 0));
		lbl_CR51.setBackground(Color.WHITE);
		panel_5.add(lbl_CR51);
		
		lbl_CR51_Number_Value = new JLabel("N/A");
		panel_5.add(lbl_CR51_Number_Value);
		
		lbl51CrDate = new JLabel("Date : ");
		panel_5.add(lbl51CrDate);
		
		lbl_CR51_Date_Value = new JLabel("N/A");
		panel_5.add(lbl_CR51_Date_Value);
		
		lblNaclNumber = new JLabel("NaCl Number : ");
		lblNaclNumber.setBorder(new EmptyBorder(0, 5, 0, 0));
		panel_5.add(lblNaclNumber);
		
		lbl_NaCl_Number_Value = new JLabel("N/A");
		panel_5.add(lbl_NaCl_Number_Value);
		
		lblDate_2 = new JLabel("Date : ");
		panel_5.add(lblDate_2);
		
		lbl_NaCl_Date_Value = new JLabel("N/A");
		panel_5.add(lbl_NaCl_Date_Value);
		
		lblcrActivitymbq = new JLabel("51Cr Activity (MBq)");
		lblcrActivitymbq.setBorder(new EmptyBorder(0, 5, 0, 0));
		panel_5.add(lblcrActivitymbq);
		
		lbl_51Cr_Activity = new JLabel("N/A");
		panel_5.add(lbl_51Cr_Activity);
		
		panel_Quality_Control = new JPanel();
		panel_Quality_Control.setBackground(Color.WHITE);
		panel_Quality_Control.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_RadioPharmaceuticalCenter.add(panel_Quality_Control);
		panel_Quality_Control.setLayout(new GridLayout(0, 2, 10, 0));
		
		lbl_InjectedActivity = new JLabel("Injected Activity (CPM)");
		lbl_InjectedActivity.setBorder(new EmptyBorder(0, 5, 0, 0));
		panel_Quality_Control.add(lbl_InjectedActivity);
		
		lbl_InjectedActivity_Value = new JLabel("N/A");
		panel_Quality_Control.add(lbl_InjectedActivity_Value);
		
		lblInjectedActivityMbq = new JLabel("Injected Activity (Mbq)");
		lblInjectedActivityMbq.setBorder(new EmptyBorder(0, 5, 0, 0));
		panel_Quality_Control.add(lblInjectedActivityMbq);
		
		lbl_InjectedActivityMqb_Value = new JLabel("N/A");
		panel_Quality_Control.add(lbl_InjectedActivityMqb_Value);
		
		lblRendementMarquage = new JLabel("Labelling Rate (%)");
		lblRendementMarquage.setBorder(new EmptyBorder(0, 5, 0, 0));
		panel_Quality_Control.add(lblRendementMarquage);
		
		lbl_Rendement_Marquage_Value = new JLabel("N/A");
		panel_Quality_Control.add(lbl_Rendement_Marquage_Value);
		
		
		JPanel panel_Results = new JPanel();
		panel_Results.setBackground(Color.WHITE);
		contentPane.add(panel_Results, BorderLayout.CENTER);
		panel_Results.setLayout(new BorderLayout(0, 0));
		
		panel_Biology = new JPanel();
		panel_Biology.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_Biology.setBackground(Color.WHITE);
		panel_Results.add(panel_Biology, BorderLayout.CENTER);
		panel_Biology.setLayout(new BorderLayout(0, 15));
		
		panel_Table_Resultat = new JPanel();
		panel_Table_Resultat.setBackground(Color.WHITE);
		panel_Biology.add(panel_Table_Resultat);
		panel_Table_Resultat.setBorder(new LineBorder(new Color(0, 0, 255), 2));
		panel_Table_Resultat.setLayout(new GridLayout(0, 6, 15, 15));
		
		lbl_Volume = new JLabel("Volumes");
		lbl_Volume.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Volume.setBorder(null);
		lbl_Volume.setFont(new Font("Dialog", Font.BOLD, 20));
		panel_Table_Resultat.add(lbl_Volume);
		
		lblMeasured = new JLabel("Measured");
		lblMeasured.setHorizontalAlignment(SwingConstants.CENTER);
		lblMeasured.setFont(new Font("Dialog", Font.BOLD, 20));
		panel_Table_Resultat.add(lblMeasured);
		
		lblRelatifVolume = new JLabel("Relatif Volume");
		lblRelatifVolume.setFont(new Font("Dialog", Font.BOLD, 20));
		lblRelatifVolume.setHorizontalAlignment(SwingConstants.CENTER);
		lblRelatifVolume.setToolTipText("Measured/Weight");
		panel_Table_Resultat.add(lblRelatifVolume);
		
		lblCalculated = new JLabel("Theorical");
		lblCalculated.setFont(new Font("Dialog", Font.BOLD, 20));
		lblCalculated.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Table_Resultat.add(lblCalculated);
		
		lblDifference = new JLabel("Difference");
		lblDifference.setToolTipText("Difference=Measure-Theorical");
		lblDifference.setHorizontalAlignment(SwingConstants.CENTER);
		lblDifference.setFont(new Font("Dialog", Font.BOLD, 20));
		panel_Table_Resultat.add(lblDifference);
		
		lblRatio = new JLabel("Ratio");
		lblRatio.setToolTipText("Ratio=Measured/Theorical");
		lblRatio.setHorizontalAlignment(SwingConstants.CENTER);
		lblRatio.setFont(new Font("Dialog", Font.BOLD, 20));
		panel_Table_Resultat.add(lblRatio);
		
		lblRedCell = new JLabel("Red Cell");
		lblRedCell.setFont(new Font("Dialog", Font.BOLD, 20));
		lblRedCell.setHorizontalTextPosition(SwingConstants.CENTER);
		lblRedCell.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Table_Resultat.add(lblRedCell);
		
		lblMeasurerc = new JLabel("Measure_RC");
		lblMeasurerc.setFont(new Font("Dialog", Font.BOLD, 15));
		lblMeasurerc.setText(Math.round(volumeGlobulaireMesure)+"ml");
		lblMeasurerc.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Table_Resultat.add(lblMeasurerc);
		
		lbl_RelatifVolume_GR = new JLabel("0");
		lbl_RelatifVolume_GR.setText(Math.round(volumeGlobulaireMesure/weight)+"ml/Kg");
		lbl_RelatifVolume_GR.setFont(new Font("Dialog", Font.BOLD, 15));
		lbl_RelatifVolume_GR.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Table_Resultat.add(lbl_RelatifVolume_GR);
		
		lblCalculrc = new JLabel("CalculRC");
		lblCalculrc.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCalculrc.setText(Math.round(volumeGlobulaireTheorique)+"ml +/-"+Math.round(volumeGlobulaireTheorique*0.25));
		lblCalculrc.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Table_Resultat.add(lblCalculrc);
		
		lblDiffredcell = new JLabel("DiffRedCell");
		lblDiffredcell.setFont(new Font("Dialog", Font.BOLD, 15));
		lblDiffredcell.setText(Math.round(volumeGlobulaireMesure-volumeGlobulaireTheorique)+"ml ("+(Math.round( ( (volumeGlobulaireMesure-volumeGlobulaireTheorique) / volumeGlobulaireTheorique)*100) )+"%)");
		lblDiffredcell.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Table_Resultat.add(lblDiffredcell);
		
		lblRatiorc = new JLabel("RatioRC");
		lblRatiorc.setFont(new Font("Dialog", Font.BOLD, 15));
		lblRatiorc.setText(Math.round((volumeGlobulaireMesure/volumeGlobulaireTheorique)*100)+"%");
		lblRatiorc.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Table_Resultat.add(lblRatiorc);
		
		lblPlasma = new JLabel("Plasma");
		lblPlasma.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPlasma.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlasma.setFont(new Font("Dialog", Font.BOLD, 20));
		panel_Table_Resultat.add(lblPlasma);
		
		lbl_MesurePlasma = new JLabel("MeasurePlasma");
		lbl_MesurePlasma.setFont(new Font("Dialog", Font.BOLD, 15));
		lbl_MesurePlasma.setText(Math.round(volumePlasmatiqueMesure)+"ml");
		lbl_MesurePlasma.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Table_Resultat.add(lbl_MesurePlasma);
		
		lbl_RelatifVolume_Plasma = new JLabel("0");
		lbl_RelatifVolume_Plasma.setText(Math.round(volumePlasmatiqueMesure/weight)+"ml/Kg");
		lbl_RelatifVolume_Plasma.setFont(new Font("Dialog", Font.BOLD, 15));
		lbl_RelatifVolume_Plasma.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Table_Resultat.add(lbl_RelatifVolume_Plasma);
		
		lblCalculplasma = new JLabel("CalculPlasma");
		lblCalculplasma.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCalculplasma.setText(Math.round(volumePlasmatiqueTheorique)+"ml +/-"+Math.round(volumePlasmatiqueTheorique*0.25));
		lblCalculplasma.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Table_Resultat.add(lblCalculplasma);
		
		lblDifferenceplasma = new JLabel("DifferencePlasma");
		lblDifferenceplasma.setFont(new Font("Dialog", Font.BOLD, 15));
		lblDifferenceplasma.setText(Math.round(volumePlasmatiqueMesure-volumePlasmatiqueTheorique)+"ml ("+(Math.round(((volumePlasmatiqueMesure-volumePlasmatiqueTheorique)/volumePlasmatiqueTheorique)*100))+"%)");
		lblDifferenceplasma.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Table_Resultat.add(lblDifferenceplasma);
		
		lblRatioplasma = new JLabel("RatioPlasma");
		lblRatioplasma.setFont(new Font("Dialog", Font.BOLD, 15));
		lblRatioplasma.setText(Math.round((volumePlasmatiqueMesure/volumePlasmatiqueTheorique)*100)+"%");
		lblRatioplasma.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Table_Resultat.add(lblRatioplasma);
		
		lblTotal = new JLabel("Total");
		lblTotal.setFont(new Font("Dialog", Font.BOLD, 20));
		lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Table_Resultat.add(lblTotal);
		
		lblTotalvalue = new JLabel("Total_Value");
		lblTotalvalue.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalvalue.setFont(new Font("Dialog", Font.BOLD, 15));
		lblTotalvalue.setText(Math.round(volumeGlobulaireMesure+volumePlasmatiqueMesure)+"ml");
		panel_Table_Resultat.add(lblTotalvalue);
		
		panel_6 = new JPanel();
		panel_Biology.add(panel_6, BorderLayout.SOUTH);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		panel_Conclusions = new JPanel();
		panel_6.add(panel_Conclusions);
		panel_Conclusions.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_Conclusions.setBackground(Color.WHITE);
		panel_Conclusions.setLayout(new GridLayout(0, 2, 0, 10));
		
		lblBloodVolume = new JLabel("Red Cell Volume : ");
		lblBloodVolume.setBorder(new EmptyBorder(5, 5, 0, 0));
		lblBloodVolume.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_Conclusions.add(lblBloodVolume);
		
		lbl_RedCell_conclusion = new JLabel("N/A");
		lbl_RedCell_conclusion.setBorder(new EmptyBorder(5, 0, 0, 0));
		lbl_RedCell_conclusion.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_Conclusions.add(lbl_RedCell_conclusion);
		
		lblNewLabel_1 = new JLabel("Plasma Volume : ");
		lblNewLabel_1.setBorder(new EmptyBorder(0, 5, 5, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_Conclusions.add(lblNewLabel_1);
		
		lbl_Plasma_Conclusion = new JLabel("N/A");
		lbl_Plasma_Conclusion.setBorder(new EmptyBorder(0, 0, 5, 0));
		lbl_Plasma_Conclusion.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_Conclusions.add(lbl_Plasma_Conclusion);
		
		panel_8 = new JPanel();
		panel_6.add(panel_8, BorderLayout.SOUTH);
		
		lblConclusion = new JLabel("Conclusion : ");
		panel_8.add(lblConclusion);
		lblConclusion.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		comboBox_GeneralConclusion = new JComboBox<String>();
		comboBox_GeneralConclusion.setFont(new Font("Tahoma", Font.BOLD, 20));
		if (Locale.getDefault().getLanguage()=="fr") {
			comboBox_GeneralConclusion.setModel(new DefaultComboBoxModel<String>(new String[] {"Choose...", "Normal - Absence de Polyglobulie", "Polyglobulie Vraie", "Polyglobulie Relative", "Hemoconcentration", "Polyglobulie Apparente"}));
		}
		else {
			comboBox_GeneralConclusion.setModel(new DefaultComboBoxModel<String>(new String[] {"Choose...", "Normal - No Polycythemia", "Real Polycythemia", "Relative Polycythemia", "Hemoconcentration", "Apparent Polycythemia"}));
		}
		panel_8.add(comboBox_GeneralConclusion);
		
		lblGenral_Conclusion_Label = new JLabel("");
		lblGenral_Conclusion_Label.setFont(new Font("Dialog", Font.BOLD, 20));
		panel_8.add(lblGenral_Conclusion_Label);
		
		panel_7 = new JPanel();
		panel_7.setBackground(Color.WHITE);
		panel_Results.add(panel_7, BorderLayout.NORTH);
		
		panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBorder(new LineBorder(Color.BLACK));
		panel_7.add(panel_3);
		
		lbl_Hematocrite = new JLabel("Hematocrite (%) : "+df.format(hematocrite));
		panel_3.add(lbl_Hematocrite);
		
		horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_3.add(horizontalStrut_1);
		
		lbl_TheoricalWeight_Ratio = new JLabel("Theorical Weight Ratio : "+ df.format(theoricalWeightRatio));
		
		panel_3.add(lbl_TheoricalWeight_Ratio);
		
		JPanel panel_Bottum = new JPanel();
		panel_Bottum.setBackground(Color.WHITE);
		contentPane.add(panel_Bottum, BorderLayout.SOUTH);
		
		btnCapture = new JButton("Capture");
		btnCapture.addActionListener(controleur);
		panel_Bottum.add(btnCapture);
	}
	
	private void colorValue() {
		if (theoricalWeightRatio<0.8 ||theoricalWeightRatio>1.2) {
			lbl_TheoricalWeight_Ratio.setOpaque(true);
			lbl_TheoricalWeight_Ratio.setBackground(Color.red);
			lblCalculrc.setForeground(Color.red);
			lblCalculplasma.setForeground(Color.red);
			lblDiffredcell.setForeground(Color.red);
			lblDifferenceplasma.setForeground(Color.red);
			lblRatiorc.setForeground(Color.red);
			lblRatioplasma.setForeground(Color.red);
		}
	}
	
	private void makeConclusions() {
		if (Locale.getDefault().getLanguage()=="fr") {
			if ((volumeGlobulaireMesure/volumeGlobulaireTheorique) <= 1.25 && (volumeGlobulaireMesure/volumeGlobulaireTheorique)>1.125) lbl_RedCell_conclusion.setText("Augmentation moderee volume globulaire");
			if ((volumeGlobulaireMesure/volumeGlobulaireTheorique) > 1.25) lbl_RedCell_conclusion.setText("Augmentation significative volume globulaire");
			if ((volumeGlobulaireMesure/volumeGlobulaireTheorique) < 0.875  && (volumeGlobulaireMesure/volumeGlobulaireTheorique) >= 0.75) lbl_RedCell_conclusion.setText("Diminution moderee volume globulaire");
			if ((volumeGlobulaireMesure/volumeGlobulaireTheorique) < 0.75  ) lbl_RedCell_conclusion.setText("Diminution Significative volume globulaire");
			if ((volumeGlobulaireMesure/volumeGlobulaireTheorique) >= 0.875  && (volumeGlobulaireMesure/volumeGlobulaireTheorique)<=1.125) lbl_RedCell_conclusion.setText("Volume globulaire normal");
			
			if ((volumePlasmatiqueMesure/volumePlasmatiqueTheorique) <= 1.25 && (volumePlasmatiqueMesure/volumePlasmatiqueTheorique)>1.125) lbl_Plasma_Conclusion.setText("Augmentation moderee volume plasmatique");
			if ((volumePlasmatiqueMesure/volumePlasmatiqueTheorique) > 1.25) lbl_Plasma_Conclusion.setText("Augmentation significative volume plasmatique");
			if ((volumePlasmatiqueMesure/volumePlasmatiqueTheorique) < 0.875  && (volumePlasmatiqueMesure/volumePlasmatiqueTheorique) > 0.75) lbl_Plasma_Conclusion.setText("Diminution moderee volume plasmatique");
			if ((volumePlasmatiqueMesure/volumePlasmatiqueTheorique) < 0.75  ) lbl_Plasma_Conclusion.setText("Diminution Significative volume plasmatique");
			if ((volumePlasmatiqueMesure/volumePlasmatiqueTheorique) >= 0.875  && (volumePlasmatiqueMesure/volumePlasmatiqueTheorique)<=1.125) lbl_Plasma_Conclusion.setText("Volume plasmatique normal");
		}
		else {
			if ((volumeGlobulaireMesure/volumeGlobulaireTheorique) <= 1.25 && (volumeGlobulaireMesure/volumeGlobulaireTheorique)>1.125) lbl_RedCell_conclusion.setText("Moderate Increase of Red-cell volume");
			if ((volumeGlobulaireMesure/volumeGlobulaireTheorique) > 1.25) lbl_RedCell_conclusion.setText("Significant Increase of Red-cell volume");
			if ((volumeGlobulaireMesure/volumeGlobulaireTheorique) < 0.875  && (volumeGlobulaireMesure/volumeGlobulaireTheorique) >= 0.75) lbl_RedCell_conclusion.setText("Moderate Decrease of Red-cell volume");
			if ((volumeGlobulaireMesure/volumeGlobulaireTheorique) < 0.75  ) lbl_RedCell_conclusion.setText("Significant Decrease of Red-cell volume");
			if ((volumeGlobulaireMesure/volumeGlobulaireTheorique) >= 0.875  && (volumeGlobulaireMesure/volumeGlobulaireTheorique)<=1.125) lbl_RedCell_conclusion.setText("Normal Red-cell volume");
			
			if ((volumePlasmatiqueMesure/volumePlasmatiqueTheorique) <= 1.25 && (volumePlasmatiqueMesure/volumePlasmatiqueTheorique)>1.125) lbl_Plasma_Conclusion.setText("Moderate Increase of Plasma volume");
			if ((volumePlasmatiqueMesure/volumePlasmatiqueTheorique) > 1.25) lbl_Plasma_Conclusion.setText("Significant Increase of Plasma volume");
			if ((volumePlasmatiqueMesure/volumePlasmatiqueTheorique) < 0.875  && (volumePlasmatiqueMesure/volumePlasmatiqueTheorique) > 0.75) lbl_Plasma_Conclusion.setText("Moderate Decrease of Plasma volume");
			if ((volumePlasmatiqueMesure/volumePlasmatiqueTheorique) < 0.75  ) lbl_Plasma_Conclusion.setText("Significant Decrease of Plasma volume");
			if ((volumePlasmatiqueMesure/volumePlasmatiqueTheorique) >= 0.875  && (volumePlasmatiqueMesure/volumePlasmatiqueTheorique)<=1.125) lbl_Plasma_Conclusion.setText("Normal Plasma volume");
		
		}
}
	
	
	public JButton getCaptureButton() {
		return btnCapture;
	}
	
	public JComboBox<String> getComboConclusion(){
		return comboBox_GeneralConclusion;
	}
	
	public JLabel getLabelGeneralConclusion() {
		return lblGenral_Conclusion_Label;
	}
	
	
	private void calculerAge() {
		Calendar birthDay=new GregorianCalendar();
		birthDay.setTime(this.dob);
		Calendar examDate=new GregorianCalendar();
		examDate.setTime(this.Injectiondate);
		LocalDate birthdate = LocalDate.of (birthDay.get(Calendar.YEAR), birthDay.get(Calendar.MONTH)+1, birthDay.get(Calendar.DAY_OF_MONTH));          //Birth date
		LocalDate examdate =  LocalDate.of (examDate.get(Calendar.YEAR), examDate.get(Calendar.MONTH)+1, examDate.get(Calendar.DAY_OF_MONTH));          //Today's date
		Period age = Period.between(birthdate, examdate);
		double ageCalc=(age.getYears())+((double) (age.getMonths())/12);
		lblAge.setText("Age : "+df.format(ageCalc)+" Years");
	}


}

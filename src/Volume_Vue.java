import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyVetoException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.michaelbaranov.microba.calendar.DatePicker;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Component;

public class Volume_Vue extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_LastName, textField_FirstName, textField_ID, textFieldRefferingPhysican, 
	textField_weightEmpty, textField_WeightFull;
	private JSpinner spinner_weight, spinner_height;
	private JList<String> Sex ;
	private JLabel lbl_SC, lblAge, lblInjectedVolumeValue, lbl_Ideal_Weight, lbl_Weight_Ratio, lblTheoricalRedCell, lblTheoricalPlasmaVolume,
	lblTheoricalBloodVolume, lbl_masseEtalon, lbl_Corrected_Hematocrite,lbl_Hematocrite,lbl_Somatic_Hematocrite,
	lbl_Etalon_Mean, lbl_Etalon_SD, lbl_Etalon_CV,
	lbl_LavageGR_Mean, lbl_LavageGR_SD, lbl_LavageGR_CV,
	lbl_Lavage_SerringueMean, lbl_Lavage_Serringue_SD, lbl_Lavage_Serringue_CV,
	lbl_RedCell15_Mean,lbl_RedCell15_SD,lbl_RedCell15_CV ;
	private DatePicker dateOfBirth, ExamDate, dateCr51, dateNaCl;
	private double injectedVolume, masseEtalon, ageCalc;
	private DecimalFormat df = new DecimalFormat("0.00"); 
	private DecimalFormat df2 = new DecimalFormat("0.0000"); 
	private Volume_Controleur controleur;
	private Period age =null;
	private JTextField textField_WeightAfterEtalon;
	private Double  volumeGlobulaireTheorique,volumePlasmatiqueTheorique, rapportPoidsReelTheorique, surfaceCorporelle;
	private JTextField textField_CR51, textField_NaCl;
	private JComboBox<String> comboBoxSC;
	private JTextField textField_Cr_Activity_Mbq;

	public Volume_Vue(Volume_Controleur controleur) {
		this.controleur=controleur;
		makeVolumeGUI();
	}
	
	public Volume_Vue() {
		makeVolumeGUI();
		pack();
		this.setSize(this.getPreferredSize());
	}
	

	/**
	 * Create the frame.
	 * @return 
	 */
	public void makeVolumeGUI() {
		Image image = new ImageIcon(ClassLoader.getSystemResource("logos/ImageJ.png")).getImage();
		setIconImage(image);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Blood Volume Calculation");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel Main_center = new JPanel();
		contentPane.add(Main_center, BorderLayout.CENTER);
		Main_center.setLayout(new GridLayout(2, 2, 0, 0));
		
		JPanel Patient_identification = new JPanel();
		Patient_identification.setBorder(new LineBorder(new Color(0, 0, 0)));
		Main_center.add(Patient_identification);
		Patient_identification.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Identification_Center = new JPanel();
		panel_Identification_Center.setBorder(new LineBorder(new Color(0, 0, 0)));
		Patient_identification.add(panel_Identification_Center);
		panel_Identification_Center.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_Ligne1 = new JPanel();
		panel_Identification_Center.add(panel_Ligne1);
		panel_Ligne1.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel_Ligne1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_Ligne1.add(panel_8);
		panel_8.setLayout(new GridLayout(0, 2, 5, 5));
		
		JLabel lblPatientName = new JLabel("Patient Last Name");
		panel_8.add(lblPatientName);
		
		textField_LastName = new JTextField();
		panel_8.add(textField_LastName);
		textField_LastName.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name");
		panel_8.add(lblFirstName);
		
		textField_FirstName = new JTextField();
		panel_8.add(textField_FirstName);
		textField_FirstName.setColumns(10);
		
		JLabel lblId = new JLabel("ID");
		panel_8.add(lblId);
		
		textField_ID = new JTextField();
		panel_8.add(textField_ID);
		textField_ID.setColumns(10);
		
		JPanel panel_dob = new JPanel();
		panel_dob.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_Ligne1.add(panel_dob);
		panel_dob.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_dob.add(panel_6);
		
		JLabel lblWeight = new JLabel("Weight (Kg)");
		panel_6.add(lblWeight);
		
		spinner_weight = new JSpinner();
		spinner_weight.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if ((int)spinner_weight.getValue()!=0 && (int)spinner_height.getValue()!=0 && !Sex.isSelectionEmpty() && age!=null) {
					if ((int)spinner_weight.getValue()>93 ||(int)spinner_height.getValue()>184 && ageCalc>=15) comboBoxSC.setSelectedIndex(1);
					else if ((int)spinner_height.getValue()<73 || (int)spinner_weight.getValue()<6 ||ageCalc<15) comboBoxSC.setSelectedIndex(2);
					else comboBoxSC.setSelectedIndex(0);
					updateWeightLabels(comboBoxSC.getSelectedIndex());
				}
			}
		});
		panel_6.add(spinner_weight);
		spinner_weight.setToolTipText("Kg");
		spinner_weight.setModel(new SpinnerNumberModel(0, 0, 500, 1));
		
		JLabel lblHeight = new JLabel("Height (cm)");
		panel_6.add(lblHeight);
		
		spinner_height = new JSpinner();
		spinner_height.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if ((int)spinner_weight.getValue()!=0 && (int)spinner_height.getValue()!=0 && !Sex.isSelectionEmpty() && age!=null) {
					if ((int)spinner_weight.getValue()>93 ||(int)spinner_height.getValue()>184 && ageCalc>=15) comboBoxSC.setSelectedIndex(1);
					else if ((int)spinner_height.getValue()<73 || (int)spinner_weight.getValue()<6 ||ageCalc<15) comboBoxSC.setSelectedIndex(2);
					else comboBoxSC.setSelectedIndex(0);
					updateWeightLabels(comboBoxSC.getSelectedIndex());
				}
			}
		});
		panel_6.add(spinner_height);
		spinner_height.setToolTipText("cm");
		spinner_height.setModel(new SpinnerNumberModel(0, 0, 230, 1));
		
		JLabel lblSex = new JLabel("Sex");
		panel_6.add(lblSex);
		
		Sex = new JList<String>();
		Sex.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if ((int)spinner_weight.getValue()!=0 && (int)spinner_height.getValue()!=0 && !Sex.isSelectionEmpty() && age!=null) {
					if ((int)spinner_weight.getValue()>93 ||(int)spinner_height.getValue()>184 && ageCalc>=15) comboBoxSC.setSelectedIndex(1);
					else if ((int)spinner_height.getValue()<73 || (int)spinner_weight.getValue()<6 ||ageCalc<15) comboBoxSC.setSelectedIndex(2);
					else comboBoxSC.setSelectedIndex(0);
					updateWeightLabels(comboBoxSC.getSelectedIndex());
				}
			}
		});
		panel_6.add(Sex);
		Sex.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = new String[] {"Male", "Female"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		Sex.setSelectedIndex(0);
		
		JPanel panel_9 = new JPanel();
		panel_dob.add(panel_9);
		
		JLabel lblDateOfBirth = new JLabel("Date Of Birth");
		panel_9.add(lblDateOfBirth);
		
		dateOfBirth = new DatePicker(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
		panel_9.add(dateOfBirth);
		dateOfBirth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayAge();
				if ((int)spinner_weight.getValue()!=0 && (int)spinner_height.getValue()!=0 && !Sex.isSelectionEmpty() && age!=null) {
					if ((int)spinner_weight.getValue()>93 ||(int)spinner_height.getValue()>184 && ageCalc>=15) comboBoxSC.setSelectedIndex(1);
					else if ((int)spinner_height.getValue()<73 || (int)spinner_weight.getValue()<6 ||ageCalc<15) comboBoxSC.setSelectedIndex(2);
					else comboBoxSC.setSelectedIndex(0);
					updateWeightLabels(comboBoxSC.getSelectedIndex());
				}
			}
		});
		dateOfBirth.setBorder(new EmptyBorder(0, 5, 0 ,0));
		dateOfBirth.setToolTipText("Date format : MM-dd-yyyy");
		
		lblAge = new JLabel("Age : N/A");
		panel_9.add(lblAge);
		
		JPanel panel_10 = new JPanel();
		panel_9.add(panel_10);
		
		comboBoxSC = new JComboBox<String>();
		comboBoxSC.setModel(new DefaultComboBoxModel<String>(new String[] {"SC Dubois", "SC Boyd", "SC Pediatric"}));
		comboBoxSC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((int)spinner_weight.getValue()!=0 && (int)spinner_height.getValue()!=0 && !Sex.isSelectionEmpty() && age!=null) {
					updateWeightLabels(comboBoxSC.getSelectedIndex());
				}
			}
		});
		
		panel_10.add(comboBoxSC);
		
		JPanel panel_2 = new JPanel();
		panel_dob.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblExmaDate = new JLabel("Examination Date");
		panel_2.add(lblExmaDate);
		
		ExamDate = new DatePicker(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
		ExamDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayAge();
			}
		});
		panel_2.add(ExamDate);
		
		JLabel lblRefferingPhysician = new JLabel("Reffering Physician");
		panel_2.add(lblRefferingPhysician);
		
		textFieldRefferingPhysican = new JTextField();
		panel_2.add(textFieldRefferingPhysican);
		textFieldRefferingPhysican.setColumns(10);
		
		JPanel panel_Ligne2 = new JPanel();
		panel_Ligne1.add(panel_Ligne2);
		panel_Ligne2.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_Ligne2.setLayout(new GridLayout(0, 2, 0, 0));
		
		lbl_SC = new JLabel("Surface Area : N/A");
		panel_Ligne2.add(lbl_SC);
		lbl_SC.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		lblTheoricalBloodVolume = new JLabel("Theorical Blood Volume : N/A");
		panel_Ligne2.add(lblTheoricalBloodVolume);
		lblTheoricalBloodVolume.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		lbl_Ideal_Weight = new JLabel("Ideal Weight : N/A");
		panel_Ligne2.add(lbl_Ideal_Weight);
		lbl_Ideal_Weight.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		lbl_Weight_Ratio = new JLabel("Weight Ratio : N/A");
		panel_Ligne2.add(lbl_Weight_Ratio);
		lbl_Weight_Ratio.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		lblTheoricalRedCell = new JLabel("Theorical red cell volume : N/A");
		panel_Ligne2.add(lblTheoricalRedCell);
		lblTheoricalRedCell.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		lblTheoricalPlasmaVolume = new JLabel("Theorical Plasma Volume : N/A");
		panel_Ligne2.add(lblTheoricalPlasmaVolume);
		lblTheoricalPlasmaVolume.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		JPanel panel_Identification_Title = new JPanel();
		panel_Identification_Title.setBorder(new LineBorder(new Color(0, 0, 0)));
		Patient_identification.add(panel_Identification_Title, BorderLayout.NORTH);
		
		JLabel lblIdentification = new JLabel("Identification");
		panel_Identification_Title.add(lblIdentification);
		
		JPanel panel_Radiopharmaceutical = new JPanel();
		panel_Radiopharmaceutical.setBorder(new LineBorder(new Color(0, 0, 0)));
		Main_center.add(panel_Radiopharmaceutical);
		panel_Radiopharmaceutical.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Pharmaceutical_Title = new JPanel();
		panel_Pharmaceutical_Title.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_Radiopharmaceutical.add(panel_Pharmaceutical_Title, BorderLayout.NORTH);
		
		JLabel lblRadiopharmaceutical = new JLabel("Radiopharmaceuticals");
		panel_Pharmaceutical_Title.add(lblRadiopharmaceutical);
		
		JPanel panel_Pharmaceutical_Center = new JPanel();
		panel_Pharmaceutical_Center.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_Radiopharmaceutical.add(panel_Pharmaceutical_Center, BorderLayout.CENTER);
		panel_Pharmaceutical_Center.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 3));
		
		JPanel panel = new JPanel();
		panel_Pharmaceutical_Center.add(panel);
		panel.setLayout(new GridLayout(0, 3, 5, 5));
		
		JLabel lblcr = new JLabel("51Cr Number : ");
		panel.add(lblcr);
		
		textField_CR51 = new JTextField("N/A");
		panel.add(textField_CR51);
		textField_CR51.setColumns(10);
		
		dateCr51 = new DatePicker(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
		dateCr51.setBorder(new EmptyBorder(0, 5, 0 ,0));
		dateCr51.setToolTipText("Date format : MM-dd-yyyy");
		panel.add(dateCr51);
		
		JLabel lblNacl = new JLabel("NaCl Number :");
		panel.add(lblNacl);
		
		textField_NaCl = new JTextField("N/A");
		panel.add(textField_NaCl);
		textField_NaCl.setColumns(10);
		
		dateNaCl = new DatePicker(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
		dateNaCl.setBorder(new EmptyBorder(0, 5, 0 ,0));
		dateNaCl.setToolTipText("Date format : MM-dd-yyyy");
		panel.add(dateNaCl);
		
		JLabel lbl_Cr_Activity_Mbq = new JLabel("51Cr Activity (MBq)");
		panel.add(lbl_Cr_Activity_Mbq);
		
		textField_Cr_Activity_Mbq = new JTextField("0");
		panel.add(textField_Cr_Activity_Mbq);
		textField_Cr_Activity_Mbq.setColumns(10);
	
		
		JPanel panel_Injection = new JPanel();
		panel_Injection.setBorder(new LineBorder(new Color(0, 0, 0)));
		Main_center.add(panel_Injection);
		panel_Injection.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Injection_Title = new JPanel();
		panel_Injection_Title.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_Injection.add(panel_Injection_Title, BorderLayout.NORTH);
		
		JLabel lblInjection = new JLabel("51Cr-Erythrocyte");
		panel_Injection_Title.add(lblInjection);
		
		JPanel panel_Injection_Center = new JPanel();
		panel_Injection_Center.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_Injection.add(panel_Injection_Center, BorderLayout.CENTER);
		panel_Injection_Center.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel_Injection_Center.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_Injection_1 = new JPanel();
		panel_1.add(panel_Injection_1);
		panel_Injection_1.setLayout(new GridLayout(0, 2, 5, 5));
		
		JLabel lblWeightAfterInjection = new JLabel("Weight Empty (g)");
		panel_Injection_1.add(lblWeightAfterInjection);
		
		textField_weightEmpty = new JTextField();
		textField_weightEmpty.setText("0.0");
		textField_weightEmpty.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!textField_weightEmpty.getText().isEmpty() && !textField_WeightFull.getText().isEmpty() && !textField_WeightAfterEtalon.getText().isEmpty()) {
					deltaMasse();
				}
			}
		});
		panel_Injection_1.add(textField_weightEmpty);
		textField_weightEmpty.setColumns(10);
		
		JLabel lblWeightBeforeInjection = new JLabel("Weight Full (g)");
		panel_Injection_1.add(lblWeightBeforeInjection);
		
		textField_WeightFull = new JTextField();
		textField_WeightFull.setText("0.0");
		textField_WeightFull.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (!textField_weightEmpty.getText().isEmpty() && !textField_WeightFull.getText().isEmpty() && !textField_WeightAfterEtalon.getText().isEmpty()) {
					deltaMasse();
				}
			}
		});
		panel_Injection_1.add(textField_WeightFull);
		textField_WeightFull.setColumns(10);
		
		JLabel lblWeight_Etalon = new JLabel("Weight after etalon (g)");
		panel_Injection_1.add(lblWeight_Etalon);
		
		textField_WeightAfterEtalon = new JTextField();
		textField_WeightAfterEtalon.setText("0.0");
		textField_WeightAfterEtalon.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (!textField_weightEmpty.getText().isEmpty() && !textField_WeightFull.getText().isEmpty() && !textField_WeightAfterEtalon.getText().isEmpty()) {
					deltaMasse();
				}
			}
		});
		panel_Injection_1.add(textField_WeightAfterEtalon);
		textField_WeightAfterEtalon.setColumns(10);
		
		JLabel lblMasseEtalon = new JLabel("Masse Etalon (g)");
		panel_Injection_1.add(lblMasseEtalon);
		
		lbl_masseEtalon = new JLabel("N/A");
		panel_Injection_1.add(lbl_masseEtalon);
		
		JLabel lblInjectedVolumeml = new JLabel("Injected mass (g)");
		panel_Injection_1.add(lblInjectedVolumeml);
		
		lblInjectedVolumeValue = new JLabel("N/A");
		panel_Injection_1.add(lblInjectedVolumeValue);
		
		JPanel panel_Hematocrite_1 = new JPanel();
		panel_1.add(panel_Hematocrite_1);
		
		JPanel panel_Hematocrite = new JPanel();
		panel_Hematocrite_1.add(panel_Hematocrite);
		panel_Hematocrite.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_Hematocrite.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Hematocrite");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Hematocrite.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel_Hematocrite_Center = new JPanel();
		panel_Hematocrite.add(panel_Hematocrite_Center, BorderLayout.CENTER);
		panel_Hematocrite_Center.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnEnterValueHematocrite = new JButton("Enter Values Venous Hematocrite");
		btnEnterValueHematocrite.addActionListener(controleur);
		panel_Hematocrite_Center.add(btnEnterValueHematocrite);
		
		JPanel panel_Hematocrite_Results = new JPanel();
		panel_Hematocrite_Center.add(panel_Hematocrite_Results);
		panel_Hematocrite_Results.setLayout(new GridLayout(0, 2, 0, 0));
		
		lbl_Hematocrite = new JLabel("Venous Hematocrite : N/A");
		panel_Hematocrite_Results.add(lbl_Hematocrite);
		
		lbl_Corrected_Hematocrite = new JLabel("Corrected Hematocrite : N/A");
		panel_Hematocrite_Results.add(lbl_Corrected_Hematocrite);
		
		lbl_Somatic_Hematocrite = new JLabel("Somatic Hematocrite : N/A");
		panel_Hematocrite_Results.add(lbl_Somatic_Hematocrite);
		
		JPanel panel_Mesure = new JPanel();
		panel_Mesure.setBorder(new LineBorder(new Color(0, 0, 0)));
		Main_center.add(panel_Mesure);
		panel_Mesure.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Measure_Title = new JPanel();
		panel_Measure_Title.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_Mesure.add(panel_Measure_Title, BorderLayout.NORTH);
		
		JLabel lblMeasure = new JLabel("Measures");
		panel_Measure_Title.add(lblMeasure);
		
		JPanel panel_Mesure_Center = new JPanel();
		panel_Mesure_Center.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_Mesure.add(panel_Mesure_Center, BorderLayout.CENTER);
		panel_Mesure_Center.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_Etalon = new JPanel();
		panel_Etalon.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_Mesure_Center.add(panel_Etalon);
		
		JPanel panel_Etalon_Center = new JPanel();
		panel_Etalon.add(panel_Etalon_Center);
		panel_Etalon_Center.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Etalon_Results = new JPanel();
		panel_Etalon_Center.add(panel_Etalon_Results, BorderLayout.SOUTH);
		
		lbl_Etalon_Mean = new JLabel("Mean : N/A");
		panel_Etalon_Results.add(lbl_Etalon_Mean);
		
		lbl_Etalon_SD = new JLabel("SD : N/A");
		panel_Etalon_Results.add(lbl_Etalon_SD);
		
		lbl_Etalon_CV = new JLabel("CV : N/A");
		panel_Etalon_Results.add(lbl_Etalon_CV);
		
		JButton btnEnterValuesEtalon = new JButton("Enter Values Etalon");
		btnEnterValuesEtalon.addActionListener(controleur); 

		panel_Etalon_Center.add(btnEnterValuesEtalon, BorderLayout.CENTER);
		
		JPanel panel_LavageGR = new JPanel();
		panel_LavageGR.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_Mesure_Center.add(panel_LavageGR);
		
		JPanel panel_5 = new JPanel();
		panel_LavageGR.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JButton btn_LavageGR = new JButton("Enter Values Lavage GR");
		btn_LavageGR.addActionListener(controleur);
		panel_5.add(btn_LavageGR, BorderLayout.CENTER);
		
		JPanel panel_LavageGR_Results = new JPanel();
		panel_5.add(panel_LavageGR_Results, BorderLayout.SOUTH);
		
		lbl_LavageGR_Mean = new JLabel("Mean : N/A");
		panel_LavageGR_Results.add(lbl_LavageGR_Mean);
		
		lbl_LavageGR_SD = new JLabel("SD : N/A");
		panel_LavageGR_Results.add(lbl_LavageGR_SD);
		
		lbl_LavageGR_CV = new JLabel("CV : N/A");
		panel_LavageGR_Results.add(lbl_LavageGR_CV);
		
		JPanel panel_LavageSeringue = new JPanel();
		panel_LavageSeringue.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_Mesure_Center.add(panel_LavageSeringue);
		
		JPanel panel_7 = new JPanel();
		panel_LavageSeringue.add(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));
		
		JButton btn_LavageSeringue = new JButton("Enter Values Lavage Serringue");
		btn_LavageSeringue.addActionListener(controleur);
		panel_7.add(btn_LavageSeringue, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		panel_7.add(panel_3, BorderLayout.SOUTH);
		
		lbl_Lavage_SerringueMean = new JLabel("Mean  : N/A");
		panel_3.add(lbl_Lavage_SerringueMean);
		
		lbl_Lavage_Serringue_SD = new JLabel("SD : N/A");
		panel_3.add(lbl_Lavage_Serringue_SD);
		
		lbl_Lavage_Serringue_CV = new JLabel("CV : N/A");
		panel_3.add(lbl_Lavage_Serringue_CV);
		
		JPanel panel_GR_15 = new JPanel();
		panel_GR_15.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_Mesure_Center.add(panel_GR_15);
		
		JPanel panel_GR = new JPanel();
		panel_GR_15.add(panel_GR);
		panel_GR.setLayout(new BorderLayout(0, 0));
		
		JButton btn_RedCell15 = new JButton("Enter Values GR");
		btn_RedCell15.addActionListener(controleur);
		panel_GR.add(btn_RedCell15, BorderLayout.CENTER);
		
		JPanel panel_RedCell15_SD = new JPanel();
		panel_GR.add(panel_RedCell15_SD, BorderLayout.SOUTH);
		
		lbl_RedCell15_Mean = new JLabel("Mean : N/A");
		panel_RedCell15_SD.add(lbl_RedCell15_Mean);
		
		lbl_RedCell15_SD = new JLabel("SD : N/A");
		panel_RedCell15_SD.add(lbl_RedCell15_SD);
		
		lbl_RedCell15_CV = new JLabel("CV : N/A");
		panel_RedCell15_SD.add(lbl_RedCell15_CV);
		
		JPanel Bottum_Panel = new JPanel();
		Bottum_Panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(Bottum_Panel, BorderLayout.SOUTH);
		Bottum_Panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_11 = new JPanel();
		Bottum_Panel.add(panel_11);
		
		JButton btnCalculate = new JButton("Calculate");
		btnCalculate.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_11.add(btnCalculate);
		btnCalculate.addActionListener(controleur);
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_4.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		Bottum_Panel.add(panel_4, BorderLayout.EAST);
		
		JButton btnSave = new JButton("Save");
		panel_4.add(btnSave);
		btnSave.addActionListener(controleur);
		
		JPanel panel_12 = new JPanel();
		Bottum_Panel.add(panel_12, BorderLayout.WEST);
		
		JButton btnLoad = new JButton("Load");
		panel_12.add(btnLoad);
		btnLoad.addActionListener(controleur);
		
	}

	private void updateWeightLabels(int scModel) {
			Boolean masculin=null;
			if (Sex.getSelectedIndex()==0) masculin=true;
			if (Sex.getSelectedIndex()==1) masculin=false;
			if (scModel==0) {
				surfaceCorporelle=Plaquette_Modele.calculer_ScDubois((int)spinner_height.getValue(), (int)spinner_weight.getValue());
			}
			if (scModel==1) {
				surfaceCorporelle=Volume_Modele.calculScBoyd((int)spinner_height.getValue(), (int)spinner_weight.getValue());
			}
			if (scModel==2) {
				surfaceCorporelle=Volume_Modele.calculScPedia((int)spinner_height.getValue(), (int)spinner_weight.getValue());
			}
			double poidsIdeal=Volume_Modele.idealWeight((int)spinner_height.getValue(), masculin);
			rapportPoidsReelTheorique=((int)spinner_weight.getValue())/poidsIdeal;
			volumeGlobulaireTheorique=Volume_Modele.volumeGlobulaireTheorique(surfaceCorporelle, masculin, (age.getYears()+(double) (age.getMonths())/12));
			volumePlasmatiqueTheorique=Volume_Modele.volumePlasmatiqueTheorique(surfaceCorporelle, masculin);
			lbl_Ideal_Weight.setText("Ideal Weight : "+df.format(poidsIdeal) +"Kg");
			lbl_Weight_Ratio.setText("Weight Ratio : "+df.format(rapportPoidsReelTheorique));
			if (rapportPoidsReelTheorique<0.8 | rapportPoidsReelTheorique>1.2) {
				lbl_Weight_Ratio.setOpaque(true);
				lbl_Weight_Ratio.setBackground(Color.red);
			}
			else {
				lbl_Weight_Ratio.setOpaque(false);
			}
			lblTheoricalRedCell.setText("Theorical Red cell volume : " +volumeGlobulaireTheorique.intValue() +"ml");
			lblTheoricalPlasmaVolume.setText("Theorical Plasma volume : "+volumePlasmatiqueTheorique.intValue() +"ml");
			lblTheoricalBloodVolume.setText("Theorical blood volume : "+(volumeGlobulaireTheorique.intValue()+volumePlasmatiqueTheorique.intValue())+"ml");
			lbl_SC.setText("Surface Area : "+String.valueOf(df.format(surfaceCorporelle))+"m2");
	}
	
	private void displayAge() {
		Calendar birthDay=new GregorianCalendar();
		birthDay.setTime(dateOfBirth.getDate());
		Calendar examDate=new GregorianCalendar();
		examDate.setTime(ExamDate.getDate());
		LocalDate birthdate = LocalDate.of (birthDay.get(Calendar.YEAR), birthDay.get(Calendar.MONTH)+1, birthDay.get(Calendar.DAY_OF_MONTH));          //Birth date
		LocalDate examdate =  LocalDate.of (examDate.get(Calendar.YEAR), examDate.get(Calendar.MONTH)+1, examDate.get(Calendar.DAY_OF_MONTH));          //Today's date
		age = Period.between(birthdate, examdate);
		ageCalc=(age.getYears())+((double) (age.getMonths())/12);
		lblAge.setText("Age : "+df.format(ageCalc)+" Years");
	}
	
	public void deltaMasse() {
			double masseApresEtalon=Double.parseDouble(textField_WeightAfterEtalon.getText().replaceAll(",", "."));
			double before=Double.parseDouble(textField_WeightFull.getText().replaceAll(",", "."));
			double after=Double.parseDouble(textField_weightEmpty.getText().replaceAll(",", "."));
			masseEtalon=before-masseApresEtalon;
			injectedVolume=masseApresEtalon-after;
			lblInjectedVolumeValue.setText(String.valueOf(df2.format(injectedVolume)));
			lbl_masseEtalon.setText(String.valueOf(df2.format(masseEtalon)));
			
	}
	
	public String getPatientFirstName() {
		return textField_FirstName.getText();
	}
	
	public String getPatientLastName() {
		return textField_LastName.getText();
	}
	
	public void setPatientLastName(String lastName) {
		textField_LastName.setText(lastName);
	}
	
	public void setPatientFirstName(String firstName) {
		textField_FirstName.setText(firstName);
	}
	
	public String getPatientId() {
		return textField_ID.getText();
	}
	
	public void setPatientId(String id) {
		textField_ID.setText(id);
	}
	
	public Date getDob() {
		return dateOfBirth.getDate();
	}
	
	public void setDob(Date date) {
		try {
			dateOfBirth.setDate(date);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
	
	public int getPatientWeight() {
		return (int) spinner_weight.getValue();
	}
	
	public void setPatientWeight(int weight) {
		spinner_weight.setValue(weight);
	}
	
	public int getPatientHeight() {
		return (int) spinner_height.getValue();
	}
	
	public void setPatientHeight(int height) {
		spinner_height.setValue(height);
	}
	
	public boolean isMale() {
		Boolean male = null;
		if (Sex.getSelectedIndex()==0) male=true;
		if (Sex.getSelectedIndex()==1) male=false;
		return male;
	}
	
	public void setMale (boolean male) {
		if (male) Sex.setSelectedIndex(0);
		else Sex.setSelectedIndex(1);
	}
	
	public Date getInjectionDate() {
		return ExamDate.getDate();
	}
	
	public void setInjectionDate(Date injectionDate) {
		try {
			ExamDate.setDate(injectionDate);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
	
	public String getRefferingPhysician() {
		return this.textFieldRefferingPhysican.getText();
	}
	
	public void setRefferingPhysician(String physician) {
		textFieldRefferingPhysican.setText(physician);
	}

	public double getVolumeGlobulaireTheorique() {
		return volumeGlobulaireTheorique;
	}
	
	public double getVolumePlasmatiqueTheorique() {
		return volumePlasmatiqueTheorique;
	}
	
	public double getMasseEmpty() {
		double empty=Double.parseDouble(textField_weightEmpty.getText().replaceAll(",", "."));
		return empty;
	}
	
	public void setMasseEmpty(double masse) {
		textField_weightEmpty.setText(String.valueOf(masse));
	}

	public double getMasseFull() {
		double full=Double.parseDouble(textField_WeightFull.getText().replaceAll(",", "."));
		return full;
	}
	
	public void setMasseFull(double masse) {
		textField_WeightFull.setText(String.valueOf(masse));
	}
	
	public double getMasseAfterEtalon() {
		double masseApresEtalon=Double.parseDouble(textField_WeightAfterEtalon.getText().replaceAll(",", "."));
		return masseApresEtalon;
	}
	
	public void setMasseAfterEtalon(double masse) {
		textField_WeightAfterEtalon.setText(String.valueOf(masse));
	}
	
	public double getMasseInjectee() {
		return injectedVolume;
	}
	
	public double getMasseEtalon() {
		return masseEtalon;
	}
	
	public JLabel getLabelHematocrite() {
		return lbl_Hematocrite;
	}
	
	public JLabel getLabelHematocriteCorrected() {
		return lbl_Corrected_Hematocrite;
	}
	
	public JLabel getLabelHematocriteSomatic() {
		return lbl_Somatic_Hematocrite;
	}
	
	public JLabel getLabelEtalonMean() {
		return lbl_Etalon_Mean;
	}
	
	public JLabel getLabelEtalonSD() {
		return lbl_Etalon_SD;	
	}
	
	public JLabel getLabelEtalonCV() {
		return lbl_Etalon_CV;
	}

	public JLabel getLabelLavageGRMean() {
		return lbl_LavageGR_Mean;
	}
	
	public JLabel getLabelLavageGRSD() {
		return lbl_LavageGR_SD;
	}
	
	public JLabel getLabelLavageGRCV() {
		return lbl_LavageGR_CV;
	}
	
	public JLabel getLabelLavageSerringueMean() {
		return lbl_Lavage_SerringueMean;
	}
	
	public JLabel getLabelLavageSerringueSD() {
		return lbl_Lavage_Serringue_SD;
	}
	
	public JLabel getLabelLavageSerringueCV() {
		return lbl_Lavage_Serringue_CV;
	}
	
	public JLabel getLabelLavageGR15Mean() {
		return lbl_RedCell15_Mean;
	}
	
	public JLabel getLabelLavageGR15SD() {
		return lbl_RedCell15_SD;
	}
	
	public JLabel getLabelLavageGR15CV() {
		return lbl_RedCell15_CV;
	}
	
	public double getTheoricalWeightRatio() {
		return rapportPoidsReelTheorique;
	}
	public double getSurfaceCorporelle() {
		return this.surfaceCorporelle;
	}
	public String getSCModele() {
		return (String) comboBoxSC.getSelectedItem();
	}
	
	public String getCr51() {
		return textField_CR51.getText();
	}
	
	public void setCr51(String Cr51) {
		textField_CR51.setText(Cr51);
	}
	
	public Date getCr51Date() {
		return dateCr51.getDate();
	}
	
	public void setCr51Date(Date date) {
		try {
			dateCr51.setDate(date);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		};
	}
	
	public String getNacl() {
		return textField_NaCl.getText();
	}
	
	public void setNacl(String nacl) {
		textField_NaCl.setText(nacl);;
	}
	
	public Date getNaclDate() {
		return dateNaCl.getDate();
	}
	
	public void setNaclDate(Date date) {
		try {
			dateNaCl.setDate(date);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
	
	public double getCr51Activity() {
		return Double.parseDouble(textField_Cr_Activity_Mbq.getText().replaceAll(",", "."));
	}
	
	public void setCr51Activity(Double cr51Activity) {
		textField_Cr_Activity_Mbq.setText(String.valueOf(cr51Activity));
	}

}




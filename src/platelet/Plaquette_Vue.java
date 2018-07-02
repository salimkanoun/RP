package platelet;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;

import com.michaelbaranov.microba.calendar.DatePicker;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Font;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.AbstractListModel;

public class Plaquette_Vue extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_LastName;
	private JTextField textField_FirstName;
	private JTextField textField_ID;
	private JTextField textField_Platlet_Count;
	private JTextField textFieldRefferingPhysican;
	private JTextField textField_ACDA;
	private JTextField textField_Tris;
	private JTextField textField_NaCl;
	private JTextField textField_In111;
	private JTextField textField_WeightBefore;
	private JTextField textField_weightAfter;
	private JTextField textField_Activity;
	private JSpinner spinner_Hour_Injection, spinner_Min_Injection, spinner_weight, spinner_height  ;
	private DatePicker dateOfInjection, PlateletDate, dateACDA, dateTris, dateIn, dateNaCl;
	private JList<String> Sex;
	private JLabel lblAge, lblInjectedVolumeValue, lbl_SC, lbl_VST;
	private DatePicker dateOfBirth;
	private JTextField textField_CPM_perMl;
	private DecimalFormat df = new DecimalFormat("0.00"); 
	private JSpinner spinner_Blood_Volume,spinner_LabellingRate, spinnerPlateletActivity,spinner_RedCellActivity,spinner_PlasmaActivity;
	private double injectedVolume;
	

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vue_Plaquette frame = new Vue_Plaquette();
					frame.pack();
					frame.setSize(frame.getPreferredSize());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public Plaquette_Vue(Plaquette_Controleur controleur) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Platlet life calculation");
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
		panel_Ligne1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_Ligne1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_8 = new JPanel();
		panel_Ligne1.add(panel_8);
		panel_8.setLayout(new GridLayout(0, 2, 0, 0));
		
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
		
		JPanel panel_Ligne2 = new JPanel();
		panel_Identification_Center.add(panel_Ligne2);
		panel_Ligne2.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_Ligne2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_WeightHeigtValue = new JPanel();
		panel_Ligne2.add(panel_WeightHeigtValue, BorderLayout.CENTER);
		panel_WeightHeigtValue.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_WeightHeigtValue.add(panel_6);
		
		JLabel lblWeight = new JLabel("Weight");
		panel_6.add(lblWeight);
		
		spinner_weight = new JSpinner();
		spinner_weight.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if ((int)spinner_weight.getValue()!=0 && (int)spinner_height.getValue()!=0 && !Sex.isSelectionEmpty()) {
					double sc=Plaquette_Modele.calculer_ScDubois((int)spinner_height.getValue(), (int)spinner_weight.getValue());
					double vst=Plaquette_Modele.calculer_VolumeSanginTheroriqueSC((int)spinner_height.getValue(), (int)spinner_weight.getValue());
					Boolean masculin=null;
					if (Sex.getSelectedIndex()==0) masculin=true;
					if (Sex.getSelectedIndex()==1) masculin=false;
					double vstToulouse=Plaquette_Modele.calculer_VolumeSanguinTheorique((int)spinner_height.getValue(), (int)spinner_weight.getValue(), masculin);
					lbl_SC.setText("Surface Area : "+String.valueOf(df.format(sc)));
					lbl_VST.setText("Blood Volume : "+String.valueOf(df.format(vst))+" Tlse "+String.valueOf(df.format(vstToulouse)));
				}
			}
		});
		panel_6.add(spinner_weight);
		spinner_weight.setToolTipText("Kg");
		spinner_weight.setModel(new SpinnerNumberModel(0, null, 500, 1));
		
		JLabel lblHeight = new JLabel("Height");
		panel_6.add(lblHeight);
		
		spinner_height = new JSpinner();
		spinner_height.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if ((int)spinner_weight.getValue()!=0 && (int)spinner_height.getValue()!=0 && !Sex.isSelectionEmpty()) {
					double sc=Plaquette_Modele.calculer_ScDubois((int)spinner_height.getValue(), (int)spinner_weight.getValue());
					double vst=Plaquette_Modele.calculer_VolumeSanginTheroriqueSC((int)spinner_height.getValue(), (int)spinner_weight.getValue());
					Boolean masculin=null;
					if (Sex.getSelectedIndex()==0) masculin=true;
					if (Sex.getSelectedIndex()==1) masculin=false;
					double vstToulouse=Plaquette_Modele.calculer_VolumeSanguinTheorique((int)spinner_height.getValue(), (int)spinner_weight.getValue(), masculin);
					lbl_SC.setText("Surface Area : "+String.valueOf(df.format(sc)));
					lbl_VST.setText("Blood Volume : "+String.valueOf(df.format(vst))+" Tlse "+String.valueOf(df.format(vstToulouse)));
				}
			}
		});
		panel_6.add(spinner_height);
		spinner_height.setToolTipText("cm");
		spinner_height.setModel(new SpinnerNumberModel(0, null, 230, 1));
		
		JLabel lblSex = new JLabel("Sex");
		panel_6.add(lblSex);
		
		Sex = new JList<String>();
		Sex.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = new String[] {"M", "F"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		Sex.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if ((int)spinner_weight.getValue()!=0 && (int)spinner_height.getValue()!=0 && !Sex.isSelectionEmpty()) {
					double sc=Plaquette_Modele.calculer_ScDubois((int)spinner_height.getValue(), (int)spinner_weight.getValue());
					double vst=Plaquette_Modele.calculer_VolumeSanginTheroriqueSC((int)spinner_height.getValue(), (int)spinner_weight.getValue());
					Boolean masculin=null;
					if (Sex.getSelectedIndex()==0) masculin=true;
					if (Sex.getSelectedIndex()==1) masculin=false;
					double vstToulouse=Plaquette_Modele.calculer_VolumeSanguinTheorique((int)spinner_height.getValue(), (int)spinner_weight.getValue(), masculin);
					lbl_SC.setText("Surface Area : "+String.valueOf(df.format(sc)));
					lbl_VST.setText("Blood Volume : "+String.valueOf(df.format(vst))+" Tlse "+String.valueOf(df.format(vstToulouse)));
				}
			}
		});
		
		
		panel_6.add(Sex);
		
		JPanel panel_wheightHeight = new JPanel();
		panel_WeightHeigtValue.add(panel_wheightHeight);
		panel_wheightHeight.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lbl_SC = new JLabel("Surface Area : N/A");
		lbl_SC.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_wheightHeight.add(lbl_SC);
		
		lbl_VST = new JLabel("Blood Volume : N/A");
		lbl_VST.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_wheightHeight.add(lbl_VST);
		
		JPanel panel_dob = new JPanel();
		panel_Ligne2.add(panel_dob, BorderLayout.SOUTH);
		
		JLabel lblDateOfBirth = new JLabel("Date Of Birth");
		panel_dob.add(lblDateOfBirth);
		
		dateOfBirth = new DatePicker(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
		dateOfBirth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Calendar birthDay=new GregorianCalendar();
				birthDay.setTime(dateOfBirth.getDate());
				LocalDate birthdate = LocalDate.of (birthDay.get(Calendar.YEAR), birthDay.get(Calendar.MONTH), birthDay.get(Calendar.DAY_OF_MONTH));          //Birth date
				LocalDate today = LocalDate.now();                 //Today's date
				Period p = Period.between(birthdate, today);
				lblAge.setText("Age : "+String.valueOf(p.getYears())+" Years");
			}
		});
		panel_dob.add(dateOfBirth);
		dateOfBirth.setBorder(new EmptyBorder(0, 5, 0 ,0));
		dateOfBirth.setToolTipText("Date format : MM-dd-yyyy");
		
		lblAge = new JLabel("Age : N/A");
		panel_dob.add(lblAge);
		
		JPanel panel = new JPanel();
		panel_Identification_Center.add(panel);
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 10, 0));
		
		JLabel lblPlateletCount = new JLabel("Platelet Count");
		panel_2.add(lblPlateletCount);
		
		textField_Platlet_Count = new JTextField();
		textField_Platlet_Count.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!textField_Platlet_Count.getText().isEmpty()) {
					int plateletCount=Integer.parseInt(textField_Platlet_Count.getText());
					if (plateletCount<=5000) textField_Platlet_Count.setBackground(Color.red);
					if (plateletCount>5000 && plateletCount<30000) textField_Platlet_Count.setBackground(Color.ORANGE);
					if (plateletCount>=30000) textField_Platlet_Count.setBackground(Color.GREEN);
				}
			}
		});
		panel_2.add(textField_Platlet_Count);
		textField_Platlet_Count.setToolTipText("G/L");
		textField_Platlet_Count.setColumns(10);
		
		JLabel lblPlateletDate = new JLabel("Platelet Date");
		panel_2.add(lblPlateletDate);
		
		PlateletDate = new DatePicker(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
		panel_2.add(PlateletDate);
		
		JLabel lblRefferingPhysician = new JLabel("Reffering Physician");
		panel_2.add(lblRefferingPhysician);
		
		textFieldRefferingPhysican = new JTextField();
		panel_2.add(textFieldRefferingPhysican);
		textFieldRefferingPhysican.setColumns(10);
		
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
		
		JLabel lblRadiopharmaceutical = new JLabel("Radiopharmaceutical");
		panel_Pharmaceutical_Title.add(lblRadiopharmaceutical);
		
		JPanel panel_Pharmaceutical_Center = new JPanel();
		panel_Pharmaceutical_Center.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_Radiopharmaceutical.add(panel_Pharmaceutical_Center, BorderLayout.CENTER);
		panel_Pharmaceutical_Center.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_RadioPharmaceutical = new JPanel();
		panel_Pharmaceutical_Center.add(panel_RadioPharmaceutical);
		panel_RadioPharmaceutical.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_Radiopharmaceutical_ACDA = new JPanel();
		panel_RadioPharmaceutical.add(panel_Radiopharmaceutical_ACDA);
		
		JLabel lblAcda = new JLabel("ACDA :");
		panel_Radiopharmaceutical_ACDA.add(lblAcda);
		
		JLabel lblNumber_ACDA = new JLabel("Number");
		panel_Radiopharmaceutical_ACDA.add(lblNumber_ACDA);
		
		textField_ACDA = new JTextField();
		panel_Radiopharmaceutical_ACDA.add(textField_ACDA);
		textField_ACDA.setColumns(10);
		
		JLabel lblDate_ACDA = new JLabel("Date");
		panel_Radiopharmaceutical_ACDA.add(lblDate_ACDA);
		
		dateACDA = new DatePicker(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
		dateACDA.setBorder(new EmptyBorder(0, 5, 0 ,0));
		dateACDA.setToolTipText("Date format : MM-dd-yyyy");
		panel_Radiopharmaceutical_ACDA.add(dateACDA);
		
		JPanel panel_Radiopharmaceutical_Tris = new JPanel();
		panel_RadioPharmaceutical.add(panel_Radiopharmaceutical_Tris);
		
		JLabel lblTris = new JLabel("Tris :");
		panel_Radiopharmaceutical_Tris.add(lblTris);
		
		JLabel label_Tris = new JLabel("Number");
		panel_Radiopharmaceutical_Tris.add(label_Tris);
		
		textField_Tris = new JTextField();
		textField_Tris.setColumns(10);
		panel_Radiopharmaceutical_Tris.add(textField_Tris);
		
		JLabel label_Tris_1 = new JLabel("Date");
		panel_Radiopharmaceutical_Tris.add(label_Tris_1);
		
		dateTris = new DatePicker(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
		dateTris.setBorder(new EmptyBorder(0, 5, 0 ,0));
		dateTris.setToolTipText("Date format : MM-dd-yyyy");
		panel_Radiopharmaceutical_Tris.add(dateTris);
		
		JPanel panel_RadioPharmaceutical_NaCl = new JPanel();
		panel_RadioPharmaceutical.add(panel_RadioPharmaceutical_NaCl);
		
		JLabel lblNacl = new JLabel("NaCl :");
		panel_RadioPharmaceutical_NaCl.add(lblNacl);
		
		JLabel label_Nacl = new JLabel("Number");
		panel_RadioPharmaceutical_NaCl.add(label_Nacl);
		
		textField_NaCl = new JTextField();
		textField_NaCl.setColumns(10);
		panel_RadioPharmaceutical_NaCl.add(textField_NaCl);
		
		JLabel label_NaCl = new JLabel("Date");
		panel_RadioPharmaceutical_NaCl.add(label_NaCl);
		
		dateNaCl = new DatePicker(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
		dateNaCl.setBorder(new EmptyBorder(0, 5, 0 ,0));
		dateNaCl.setToolTipText("Date format : MM-dd-yyyy");
		panel_RadioPharmaceutical_NaCl.add(dateNaCl);
		
		JPanel panel_RadioParmaceutical_In111 = new JPanel();
		panel_RadioPharmaceutical.add(panel_RadioParmaceutical_In111);
		
		JLabel lblin = new JLabel("111In :");
		panel_RadioParmaceutical_In111.add(lblin);
		
		JLabel label_In111 = new JLabel("Number");
		panel_RadioParmaceutical_In111.add(label_In111);
		
		textField_In111 = new JTextField();
		textField_In111.setColumns(10);
		panel_RadioParmaceutical_In111.add(textField_In111);
		
		JLabel label_In111_1 = new JLabel("Date");
		panel_RadioParmaceutical_In111.add(label_In111_1);
		
		dateIn = new DatePicker(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
		dateIn.setBorder(new EmptyBorder(0, 5, 0 ,0));
		dateIn.setToolTipText("Date format : MM-dd-yyyy");
		panel_RadioParmaceutical_In111.add(dateIn);
		
		JPanel panel_Radiopharaceutical_Report = new JPanel();
		panel_Pharmaceutical_Center.add(panel_Radiopharaceutical_Report);
		
		JPanel panel_5 = new JPanel();
		panel_Radiopharaceutical_Report.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_5.add(panel_7);
		panel_7.setLayout(new GridLayout(0, 2, 5, 0));
		
		JLabel lblVolume = new JLabel("Blood Sample Volume (mL)");
		panel_7.add(lblVolume);
		
		spinner_Blood_Volume = new JSpinner();
		spinner_Blood_Volume.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		panel_7.add(spinner_Blood_Volume);
		
		JLabel lblRendementMarquage = new JLabel("Labelling Rate");
		panel_7.add(lblRendementMarquage);
		
		spinner_LabellingRate = new JSpinner();
		spinner_LabellingRate.setModel(new SpinnerNumberModel(0, null, 100, 1));
		panel_7.add(spinner_LabellingRate);
		
		JLabel lblPlateletActivity = new JLabel("Platelet activity(%)");
		panel_7.add(lblPlateletActivity);
		
		spinnerPlateletActivity = new JSpinner();
		spinnerPlateletActivity.setModel(new SpinnerNumberModel(0, null, 100, 1));
		panel_7.add(spinnerPlateletActivity);
		
		JLabel lblRedCellActivity = new JLabel("Red cell activity (%)");
		panel_7.add(lblRedCellActivity);
		
		spinner_RedCellActivity = new JSpinner();
		spinner_RedCellActivity.setModel(new SpinnerNumberModel(0, null, 100, 1));
		panel_7.add(spinner_RedCellActivity);
		
		JLabel lblPlasmaActiviy = new JLabel("Plasma activiy (%)");
		panel_7.add(lblPlasmaActiviy);
		
		spinner_PlasmaActivity = new JSpinner();
		spinner_PlasmaActivity.setModel(new SpinnerNumberModel(0, null, 100, 1));
		panel_7.add(spinner_PlasmaActivity);
		
		JPanel panel_Injection = new JPanel();
		panel_Injection.setBorder(new LineBorder(new Color(0, 0, 0)));
		Main_center.add(panel_Injection);
		panel_Injection.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Injection_Title = new JPanel();
		panel_Injection_Title.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_Injection.add(panel_Injection_Title, BorderLayout.NORTH);
		
		JLabel lblInjection = new JLabel("Indium Injection ");
		panel_Injection_Title.add(lblInjection);
		
		JPanel panel_Injection_Center = new JPanel();
		panel_Injection_Center.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_Injection.add(panel_Injection_Center, BorderLayout.CENTER);
		panel_Injection_Center.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_Injection_Center.add(panel_3, BorderLayout.NORTH);
		
		JLabel lblInjectionDate = new JLabel("Injection Date");
		panel_3.add(lblInjectionDate);
		
		dateOfInjection = new DatePicker(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
		//TENIR EN COMPTE LA DATE D INJECTION POUR LE CALCUL DE L AGE SK ///////////////
		dateOfInjection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_3.add(dateOfInjection);
		dateOfInjection.setBorder(new EmptyBorder(0, 5, 0 ,0));
		dateOfInjection.setToolTipText("Date format : MM-dd-yyyy");
		
		spinner_Hour_Injection = new JSpinner();
		spinner_Hour_Injection.setModel(new SpinnerNumberModel(23, null, 23, 1));
		panel_3.add(spinner_Hour_Injection);
		
		JLabel lblHour = new JLabel("hours");
		panel_3.add(lblHour);
		
		spinner_Min_Injection = new JSpinner();
		spinner_Min_Injection.setModel(new SpinnerNumberModel(59, null, 59, 1));
		panel_3.add(spinner_Min_Injection);
		
		JLabel lblMin = new JLabel("min");
		panel_3.add(lblMin);
		
		JPanel panel_1 = new JPanel();
		panel_Injection_Center.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 2, 5, 0));
		
		JLabel lblActivity = new JLabel("Activity (MBq)");
		panel_4.add(lblActivity);
		
		textField_Activity = new JTextField();
		panel_4.add(textField_Activity);
		textField_Activity.setColumns(10);
		
		JLabel lblWeightBeforeInjection = new JLabel("Weight before injection");
		panel_4.add(lblWeightBeforeInjection);
		
		textField_WeightBefore = new JTextField();
		textField_WeightBefore.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (!textField_weightAfter.getText().isEmpty() && !textField_WeightBefore.getText().isEmpty()) {
					double before=Double.parseDouble(textField_WeightBefore.getText());
					double after=Double.parseDouble(textField_weightAfter.getText());
					injectedVolume=before-after;
					lblInjectedVolumeValue.setText(String.valueOf(df.format(before-after)));
				}
			}
		});
		panel_4.add(textField_WeightBefore);
		textField_WeightBefore.setColumns(10);
		
		JLabel lblWeightAfterInjection = new JLabel("Weight after injection");
		panel_4.add(lblWeightAfterInjection);
		
		textField_weightAfter = new JTextField();
		textField_weightAfter.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!textField_weightAfter.getText().isEmpty() && !textField_WeightBefore.getText().isEmpty() ) {
					double before=Double.parseDouble(textField_WeightBefore.getText());
					double after=Double.parseDouble(textField_weightAfter.getText());
					injectedVolume=before-after;
					lblInjectedVolumeValue.setText(String.valueOf(df.format(before-after)));
				}
			}
		});
		panel_4.add(textField_weightAfter);
		textField_weightAfter.setColumns(10);
		
		JLabel lblInjectedVolumeml = new JLabel("Injected volume (mL)");
		panel_4.add(lblInjectedVolumeml);
		
		lblInjectedVolumeValue = new JLabel("N/A");
		panel_4.add(lblInjectedVolumeValue);
		
		JLabel lblCpmPerMl = new JLabel("CPM per mL at injection time\r\n");
		panel_4.add(lblCpmPerMl);
		
		textField_CPM_perMl = new JTextField();
		panel_4.add(textField_CPM_perMl);
		textField_CPM_perMl.setColumns(10);
		
		JPanel panel_Mesure = new JPanel();
		panel_Mesure.setBorder(new LineBorder(new Color(0, 0, 0)));
		Main_center.add(panel_Mesure);
		panel_Mesure.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Measure_Title = new JPanel();
		panel_Measure_Title.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_Mesure.add(panel_Measure_Title, BorderLayout.NORTH);
		
		JLabel lblMeasure = new JLabel("Measure");
		panel_Measure_Title.add(lblMeasure);
		
		JPanel panel_Mesure_Center = new JPanel();
		panel_Mesure_Center.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_Mesure.add(panel_Mesure_Center, BorderLayout.CENTER);
		
		JLabel label = new JLabel("");
		panel_Mesure_Center.add(label);
		
		JPanel panel_Mesure_Buttons = new JPanel();
		panel_Mesure_Center.add(panel_Mesure_Buttons);
		panel_Mesure_Buttons.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnInsertMeasureValue = new JButton("Insert measure Value");
		btnInsertMeasureValue.addActionListener(controleur);
		panel_Mesure_Buttons.add(btnInsertMeasureValue);
		
		JButton btnProcessImages = new JButton("Process Images");
		btnProcessImages.addActionListener(controleur);
		panel_Mesure_Buttons.add(btnProcessImages);
		
		JPanel Bottum_Panel = new JPanel();
		Bottum_Panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(Bottum_Panel, BorderLayout.SOUTH);
		
		JButton btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(controleur);
		Bottum_Panel.add(btnCalculate);
	}
	

	public Double getActiviteInj() {
		return Double.parseDouble(textField_Activity.getText());
	}
	public double getWheightBefore() {
		return Double.parseDouble(textField_WeightBefore.getText());
	}
	public double getWheightAfter() {
		return Double.parseDouble(textField_weightAfter.getText());
	}
	
	public boolean isMale() {
		Boolean masculin = null;
		if (Sex.getSelectedIndex()==0) masculin=true;
		if (Sex.getSelectedIndex()==1) masculin=false;
		return masculin;
	}
	
	public int getWeightValue() {
		return Integer.valueOf(spinner_weight.getValue().toString());
	}
	
	public int getHeightValue() {
		return Integer.valueOf(spinner_height.getValue().toString());
	}
	
	public Date getInjectionDate(){
		Calendar dayInjection=new GregorianCalendar();
		dayInjection.setTime(dateOfInjection.getDate());
		Calendar calendar = new GregorianCalendar(dayInjection.get(Calendar.YEAR), dayInjection.get(Calendar.MONTH), dayInjection.get(Calendar.DAY_OF_MONTH), Integer.parseInt(spinner_Hour_Injection.getValue().toString()), Integer.parseInt(spinner_Min_Injection.getValue().toString()), 00);
		Date dateInjection=calendar.getTime();
		return dateInjection;
	}
	
	public String getLastName() {
		return textField_LastName.getText();
	}
	
	public String getFirstName() {
		return textField_FirstName.getText();
	}
	
	public String getId() {
		return textField_ID.getText();
	}
	
	public Date getDob() {
		return dateOfBirth.getDate();
	}
	
	public Date getPlateletDate() {
		return this.PlateletDate.getDate();
	}
	
	public int getPlateletCount() {
		return Integer.parseInt(textField_Platlet_Count.getText());
	}
	
	public double getInjectedVolume() {
		return this.injectedVolume;
	}
	
	public double getCpmPerMlInjection() {
		return Double.parseDouble(textField_CPM_perMl.getText());
	}
	
	public String getRefferingPhysician() {
		return textFieldRefferingPhysican.getText();
	}
	
	public String getAcdaNumber() {
		return this.textField_ACDA.getText();
	}
	
	public Date getAcdaDate() {
		return this.dateACDA.getDate();
	}
	
	public String getTrisNumber() {
		return this.textField_Tris.getText();
	}
	
	public Date getTrisDate() {
		return this.dateTris.getDate();
	}
	
	public String getNaClNumber() {
		return this.textField_NaCl.getText();
	}
	
	public Date getNaClDate() {
		return this.dateNaCl.getDate();
	}
	
	public String getInNumber() {
		return this.textField_In111.getText();
	}
	
	public Date getInDate() {
		return this.dateIn.getDate();
	}
	
	public int getBloodVolume() {
		return (int)spinner_Blood_Volume.getValue();
	}
	
	public int getLabellingRate() {
		return (int)spinner_LabellingRate.getValue();
	}
	
	public int getPlateletActivity() {
		return (int) spinnerPlateletActivity.getValue();
	}
	
	public int getRedCellActivity() {
		return (int) spinner_RedCellActivity.getValue();
	}
	
	public int getPlasmaActivity() {
		return (int) spinner_PlasmaActivity.getValue();
	}
	
	
}

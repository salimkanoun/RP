package org.petctviewer.radiopharmacy.bloodVolume;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import ij.plugin.PlugIn;

public class Volume_Controleur implements ActionListener, PlugIn, ChangeListener  {
	
	private Volume_Vue gui=new Volume_Vue(this);
	private Double meanHematocrite;
	private DecimalFormat df = new DecimalFormat("0.00"); 
	private List<Volume_Generic_Value> valeursEtalon= new ArrayList<Volume_Generic_Value>() ;
	private List<Volume_Generic_Value> valeursLavageGR= new ArrayList<Volume_Generic_Value>() ;
	private List<Volume_Generic_Value> valeursLavageSerringue= new ArrayList<Volume_Generic_Value>() ;
	private List<Volume_Generic_Value> valeursGR= new ArrayList<Volume_Generic_Value>() ;
	private List<Double> valeursHematocrite=new ArrayList<Double>();
	private String unitGR;
	private Volume_Result_Frame guiResults;
	private double[] resultsEtalon, resultsLavageGR, resultsLavageSerringue, resultsGR;
	private boolean fullEtalonDilution;
	private double volumeDilutionEtalon, lavageGRDilutionVolume, lavageSerringueDilutionVolume, volumeDilutionGR;
	private double bloodDensity, etalonDensity;
	private File lastDirectory;
	private SimpleDateFormat  simpleFormat = new SimpleDateFormat("yyyyMMdd");

	public static void main(String[] args) {
		Volume_Controleur controleur= new Volume_Controleur();
		Dimension window=controleur.gui.getPreferredSize();
		controleur.gui.setSize((int)window.getWidth()+150,(int)window.getHeight());
		controleur.gui.setLocationRelativeTo(null);
		controleur.gui.setVisible(true);
	}
	
	//ImageJ Run Method
	@Override
	public void run(String arg0) {
		
		Volume_Controleur controleur= new Volume_Controleur();
		Dimension window=controleur.gui.getPreferredSize();
		controleur.gui.setSize((int)window.getWidth()+150,(int)window.getHeight());
		controleur.gui.setLocationRelativeTo(null);
		controleur.gui.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand()=="Enter Values Etalon") {
			Volume_Insert_Value_Generic valueEtalon= new Volume_Insert_Value_Generic("Etalon");
			valueEtalon.setModal(true);
			valueEtalon.setLocationRelativeTo(gui);
			fullEtalonDilution=valueEtalon.isFullEtalonDilution();
			if (!valeursEtalon.isEmpty()) {
				valueEtalon.setValeurs(valeursEtalon);
				valueEtalon.setDensity(etalonDensity);
			}
			valueEtalon.setVisible(true);
			// Si validation on recupere les resultats
			if (valueEtalon.getOk()) {
				valeursEtalon=valueEtalon.getValeurs();
				volumeDilutionEtalon=valueEtalon.getDilutionVolume();
				etalonDensity=valueEtalon.getDensity();
				updateEtalonLabels();
			}
			
			
		}
		if (event.getActionCommand()=="Enter Values Lavage GR") {
			Volume_Insert_Value_Generic valueLavageGR= new Volume_Insert_Value_Generic("Lavage GR");
			valueLavageGR.setModal(true);
			valueLavageGR.setLocationRelativeTo(gui);
			if (!valeursLavageGR.isEmpty()) valueLavageGR.setValeurs(valeursLavageGR);
			valueLavageGR.setVisible(true);
			// Si validation on recup�re les resultats
			if (valueLavageGR.getOk()) {
				valeursLavageGR=valueLavageGR.getValeurs();
				lavageGRDilutionVolume=valueLavageGR.getDilutionVolume();
				updateLavageGrLabels();
			}
		}
		
		if (event.getActionCommand()=="Enter Values Lavage Serringue") {
			Volume_Insert_Value_Generic valueLavageSerringue= new Volume_Insert_Value_Generic("Lavage Serringue");
			valueLavageSerringue.setModal(true);
			valueLavageSerringue.setLocationRelativeTo(gui);
			if (!valeursLavageSerringue.isEmpty()) valueLavageSerringue.setValeurs(valeursLavageSerringue);
			valueLavageSerringue.setVisible(true);
			// Si validation on recup�re les resultats
			if (valueLavageSerringue.getOk()) {
				valeursLavageSerringue=valueLavageSerringue.getValeurs();
				lavageSerringueDilutionVolume=valueLavageSerringue.getDilutionVolume();
				updateLavageSerringueLabels();
			}
		}
		
		if (event.getActionCommand()=="Enter Values GR") {
			Volume_Insert_Value_MesureVG valueGR= new Volume_Insert_Value_MesureVG();
			valueGR.makeGui();
			valueGR.setModal(true);
			valueGR.setLocationRelativeTo(gui);
			if (!valeursGR.isEmpty()) {
				valueGR.setValeurs(valeursGR);
				valueGR.setUnit(unitGR);
			}
			valueGR.setVisible(true);
			// Si validation on recup�re les resultats
			if (valueGR.getOk()) {
				valeursGR=valueGR.getValeurs();
				volumeDilutionGR=valueGR.getDilutionVolume();
				unitGR=valueGR.getUnit();
				bloodDensity=valueGR.getBloodDensity();
				calculateAndupdateGRLabels();
			}
		}
		
		if (event.getActionCommand()=="Enter Values Venous Hematocrite") {
			Volume_Insert_Value_Hematocrite insertHematocrite=new Volume_Insert_Value_Hematocrite();
			insertHematocrite.setModal(true);
			insertHematocrite.setLocationRelativeTo(gui);
			if (!valeursHematocrite.isEmpty()) insertHematocrite.setValeurs(valeursHematocrite);
			insertHematocrite.setVisible(true);
			if (insertHematocrite.ok) {
				valeursHematocrite=insertHematocrite.getHematocriteValues();
				updateHematocriteLabel();
				
			}
		}
		if (event.getActionCommand()=="Calculate") {
			  try {
				  //SAUVEGARDER LES VALEUR DE DILUTION DE CHAQUE DANS LE REGISTERY OU DANS UNE VARIABLE
				  	int backgroundCount=gui.getBackgroundCount();
					double activiteInjecte=Volume_Modele.activiteInjecteeGR( (resultsEtalon[0]-backgroundCount) , gui.getMasseInjectee(), gui.getMasseEtalon(), (resultsLavageSerringue[0]-backgroundCount), fullEtalonDilution);
					double volumeGlobulaireMesure=(activiteInjecte*meanHematocrite)/( (resultsGR[0]-backgroundCount)*100 );
					double volumeSgTotalMesure=((volumeGlobulaireMesure*100)/(meanHematocrite*0.91));
					double volumePlasmatiqueMesure=volumeSgTotalMesure-volumeGlobulaireMesure;
					double rendementMarquage=Volume_Modele.calculRendementMarquageGR(gui.getMasseEtalon(), gui.getMasseInjectee(), resultsEtalon[0], resultsLavageGR[0]);
					//Production de la GUI results
					guiResults=new Volume_Result_Frame(this);
					guiResults.setResults(volumeGlobulaireMesure, volumePlasmatiqueMesure, gui.getVolumeGlobulaireTheorique(), gui.getVolumePlasmatiqueTheorique());
					guiResults.setPatientIdentification(gui.getPatientLastName(), gui.getPatientFirstName(), gui.getPatientId(), gui.getDob(), gui.getPatientWeight(), gui.getPatientHeight(), gui.isMale(),gui.getSurfaceCorporelle(),gui.getSCModele(), gui.getInjectionDate(), gui.getRefferingPhysician());
					guiResults.setHematocriteWeightRatio(meanHematocrite, gui.getTheoricalWeightRatio());
					guiResults.buildGUI();
					guiResults.setRadiopharmaceutical(gui.getCr51(), gui.getCr51Date(), gui.getNacl(), gui.getNaclDate(), gui.getCr51Activity());
					guiResults.setLabellingValue(rendementMarquage, activiteInjecte, gui.getCr51Activity(), (gui.getMasseInjectee()/(gui.getMasseInjectee()+gui.getMasseEtalon()) ) );
					guiResults.setLocationRelativeTo(gui);
					guiResults.setModal(true);
					guiResults.setVisible(true);					
				  } catch (NullPointerException e) {
					  JOptionPane.showMessageDialog(gui, "Missing input Data, please fill all fields","Error", JOptionPane.ERROR_MESSAGE);
				  }
			
			
		}
		if(event.getActionCommand()=="Capture") {
			//On efface les bouttons 
			guiResults.getCaptureButton().setVisible(false);
			String conclusion=(String) guiResults.getComboConclusion().getSelectedItem();
	    	guiResults.getComboConclusion().setVisible(false);
	    	guiResults.getLabelGeneralConclusion().setText(conclusion);
			//On lance le filechooser
			JFileChooser chooser=new JFileChooser();
			if (lastDirectory!=null) chooser.setCurrentDirectory(lastDirectory);
			chooser.setSelectedFile(new File("Blood_Volume_"+gui.getPatientLastName()+"_"+simpleFormat.format(gui.getInjectionDate())+".png"));
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Image", "png");
			chooser.setFileFilter(filter);
			int valide=chooser.showSaveDialog(guiResults);
	    	//Si valide on capture et on sauve
			    	if (valide==JFileChooser.APPROVE_OPTION) {
			    		int response = 0;
			    		if (chooser.getSelectedFile().exists()) {
			    		    response = JOptionPane.showConfirmDialog(null, "Do you want to replace the existing file?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			    		    if (response != JOptionPane.YES_OPTION) {
			    		    	//on remet les bouttons et on s'arr�te
			    		    	guiResults.getCaptureButton().setVisible(true);
			    		    	guiResults.getComboConclusion().setVisible(true);
			    		    	guiResults.getLabelGeneralConclusion().setText("");
			    		    }
			    		}
			    		if (!chooser.getSelectedFile().exists() || response==JOptionPane.YES_OPTION) {
				    		
				    		Thread t = new Thread(new Runnable() {
				    		    @Override
				    		    public void run() {
				    		    	//BufferedImage capture =null;
									try {
										//Capture, nouvelle methode a utiliser sur le reste des programmes
										Container c = guiResults.getContentPane();
										BufferedImage capture = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
										c.paint(capture.getGraphics());
										lastDirectory=chooser.getCurrentDirectory();
										File outputfile = chooser.getSelectedFile();
										ImageIO.write(capture, "png", outputfile);
									} catch (IOException e) {
										e.printStackTrace();
									}
								
								guiResults.getCaptureButton().setVisible(true);
								guiResults.getComboConclusion().setVisible(true);
			    		    	guiResults.getLabelGeneralConclusion().setText("");
				    		    }
			
				    		   });
					try {
						t.start();
						Thread.sleep(200);
						t.interrupt();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						e.printStackTrace();
					}
					
				    }
			    }
	    	else if (valide==JFileChooser.CANCEL_OPTION) {
	    		guiResults.getCaptureButton().setVisible(true);
	    		guiResults.getComboConclusion().setVisible(true);
	    		guiResults.getLabelGeneralConclusion().setText("");
	    	}
		}
		
		if (event.getActionCommand()=="Save") {
			String json=buildJson();
			JFileChooser chooser=new JFileChooser();
			if (lastDirectory!=null) chooser.setCurrentDirectory(lastDirectory);
			chooser.setSelectedFile(new File("Data_"+gui.getPatientLastName()+"_"+simpleFormat.format(gui.getInjectionDate())+".json"));
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON", "json");
			chooser.setFileFilter(filter);
			int valide=chooser.showSaveDialog(guiResults);
			boolean force = false;
			if (valide==JFileChooser.APPROVE_OPTION && chooser.getSelectedFile().exists()) {
				int response = JOptionPane.showConfirmDialog(null, "Do you want to replace the existing file?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    		    if (response == JOptionPane.YES_OPTION) {
    		    	force=true;
    		    }
			}
			if (valide==JFileChooser.APPROVE_OPTION && (!chooser.getSelectedFile().exists() || force)) {
				lastDirectory=chooser.getCurrentDirectory();
				// On ecrit les CSV
				try {
					PrintWriter pw = new PrintWriter(chooser.getSelectedFile());
					pw.write(json);
					pw.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
			
		}
		
		if (event.getActionCommand()=="Load") {
			
			DateFormat df=new SimpleDateFormat("yyyyMMdd");
			JFileChooser chooser=new JFileChooser();
			if (lastDirectory!=null) chooser.setCurrentDirectory(lastDirectory);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON", "json");
			chooser.setFileFilter(filter);
			int valide=chooser.showOpenDialog(guiResults);
			
			if (valide==JFileChooser.APPROVE_OPTION) {
				//Clear all previous list
				valeursHematocrite.clear();
				valeursEtalon.clear();
				valeursLavageGR.clear();
				valeursLavageSerringue.clear();
				valeursGR.clear();
				//Fill new data
				try {
					JSONParser parser=new JSONParser();
					JSONObject fullJson=(JSONObject) parser.parse(new FileReader(chooser.getSelectedFile()));
					lastDirectory=chooser.getCurrentDirectory();
					//Set identification patient
					gui.setPatientLastName(fullJson.get("PatientLastName").toString());
					gui.setPatientFirstName(fullJson.get("PatientFirstName").toString());
					gui.setPatientId(fullJson.get("PatientID").toString());
					gui.setDob(df.parse(fullJson.get("PatientDOB").toString()));
					gui.setPatientWeight(Integer.parseInt(fullJson.get("PatientWeight").toString()));
					gui.setPatientHeight(Integer.parseInt(fullJson.get("PatientHeight").toString()));
					gui.setMale(Boolean.parseBoolean(fullJson.get("PatientIsMale").toString()));
					gui.setInjectionDate(df.parse(fullJson.get("InjectionDate").toString()));
					gui.setRefferingPhysician(fullJson.get("RefferingPhysician").toString());
					
					// Set poids
					gui.setMasseEmpty(Double.parseDouble(fullJson.get("MesureWeightEmpty").toString()));
					gui.setMasseFull(Double.parseDouble(fullJson.get("MesureWeightFull").toString()));
					gui.setMasseAfterEtalon(Double.parseDouble(fullJson.get("MesureWeightAfterEtalon").toString()));
					gui.deltaMasse();
					
					//Ajout Hematocrites
					JSONArray hematocrite=(JSONArray) fullJson.get("Hematocrite");
					for (int i=0 ; i<hematocrite.size() ; i++) {
						valeursHematocrite.add((Double) hematocrite.get(i));
					}
					updateHematocriteLabel();
					
					//Ajout Pharmaceutical
					gui.setCr51(fullJson.get("Cr51 Number").toString());
					gui.setCr51Date(df.parse(fullJson.get("Cr51 Date").toString()));
					gui.setNacl(fullJson.get("NaCl Number").toString());
					gui.setNaclDate(df.parse(fullJson.get("NaCl Date").toString()));
					
					// Ajout activite Cr51
					gui.setCr51Activity((double) fullJson.get("Cr51 Activity")); 
					
					//Ajout Valeur Etalon
					JSONArray etalon=(JSONArray) fullJson.get("EtalonValue");
					for (int i=0; i<etalon.size(); i++) {
						JSONObject resultatEtalon=(JSONObject) etalon.get(i);
						Volume_Generic_Value value = new Volume_Generic_Value( (double) resultatEtalon.get("CPM"), (double) resultatEtalon.get("ml"), (boolean) resultatEtalon.get("isUsed") );
						valeursEtalon.add(value);
					}
					fullEtalonDilution=((boolean)fullJson.get("IsFullEtalonDilution"));
					volumeDilutionEtalon =((double) fullJson.get("EtalonDilutionVolume"));
					if(fullJson.containsKey("EtalonDensity")) {
						etalonDensity=((double) fullJson.get("EtalonDensity"));
					}
					else etalonDensity=1;
					
					updateEtalonLabels();
					
					// Ajout lavage GR
					JSONArray lavageGR=(JSONArray) fullJson.get("LavageGRValue");
					for (int i=0; i<lavageGR.size(); i++) {
						JSONObject resultatlavageGR=(JSONObject) lavageGR.get(i);
						Volume_Generic_Value value = new Volume_Generic_Value( (double) resultatlavageGR.get("CPM"), (double) resultatlavageGR.get("ml"), (boolean) resultatlavageGR.get("isUsed") );
						valeursLavageGR.add(value);
					}
					lavageGRDilutionVolume =((double) fullJson.get("LavageGRDilutionVolume"));
					updateLavageGrLabels();
					
					//Ajout Lavage Serringue
					JSONArray lavageSerringue=(JSONArray) fullJson.get("LavageSerringueValue");
					for (int i=0; i<lavageSerringue.size(); i++) {
						JSONObject resultatlavageSerringue=(JSONObject) lavageSerringue.get(i);
						Volume_Generic_Value value = new Volume_Generic_Value( (double) resultatlavageSerringue.get("CPM"), (double) resultatlavageSerringue.get("ml"), (boolean) resultatlavageSerringue.get("isUsed") );
						valeursLavageSerringue.add(value);
					}
					lavageSerringueDilutionVolume =((double) fullJson.get("LavageSerringueDilution"));
					updateLavageSerringueLabels();
				
					//Ajout mesure GR
					JSONArray mesureGR=(JSONArray) fullJson.get("GRValue");
					for (int i=0; i<mesureGR.size(); i++) {
						JSONObject resultatMesureGR=(JSONObject) mesureGR.get(i);
						Volume_Generic_Value value = new Volume_Generic_Value( (double) resultatMesureGR.get("CPM"), (double) resultatMesureGR.get("ml"), (boolean) resultatMesureGR.get("isUsed"), (double) resultatMesureGR.get("Time")  );
						valeursGR.add(value);
					}
					volumeDilutionGR =((double) fullJson.get("GRDilution"));
					bloodDensity =((double) fullJson.get("BloodDensity"));
					if (fullJson.containsKey("GRValueUnit")) {
						unitGR=(String) fullJson.get("GRValueUnit");
					}
					else {
						JSONObject resultatMesureGR=(JSONObject) mesureGR.get(0);
						unitGR=(String) resultatMesureGR.get("Unit");
					}
					
					//Ajout Backgroung
					if (fullJson.containsKey("backgroundCount")) {
						gui.setBackgroundCount( Integer.parseInt(fullJson.get("backgroundCount").toString()) );
					}
					else {
						gui.setBackgroundCount(0);
					}
					
					calculateAndupdateGRLabels();
					
					
				} catch (IOException | ParseException | java.text.ParseException e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
	}
	
	private void updateHematocriteLabel() {
		DescriptiveStatistics stat=new DescriptiveStatistics() ;
		for (int i=0 ; i<valeursHematocrite.size(); i++) {
			stat.addValue((double) valeursHematocrite.get(i));
			
		}
		if (!valeursHematocrite.isEmpty()) {
			meanHematocrite=stat.getMean();
			gui.getLabelHematocrite().setText("Mean Hematocrite : "+df.format(meanHematocrite));
			gui.getLabelHematocriteCorrected().setText("Corrected Hematocrite : "+df.format(meanHematocrite*0.99));
			gui.getLabelHematocriteSomatic().setText("Somatic Hematocrite : "+df.format(meanHematocrite*0.91));
		}
		
	}
	
	private void updateEtalonLabels() {
		resultsEtalon=Volume_Modele.calculateMeanSdCv(valeursEtalon, volumeDilutionEtalon, etalonDensity, gui.getBackgroundCount() );
		gui.getLabelEtalonMean().setText("Mean : "+df.format(resultsEtalon[0]));
		gui.getLabelEtalonSD().setText("SD : "+ df.format(resultsEtalon[1]));
		gui.getLabelEtalonCV().setText("CV (%) : "+df.format(resultsEtalon[2]*100));	
		if ((resultsEtalon[2]*100)>5) {
			gui.getLabelEtalonCV().setOpaque(true); 
			gui.getLabelEtalonCV().setBackground(Color.red); 
		}
		else {
			gui.getLabelEtalonCV().setOpaque(false); 
		}
		
		
			
			
		}
	
	private void updateLavageGrLabels() {
		resultsLavageGR=Volume_Modele.calculateMeanSdCv(valeursLavageGR, lavageGRDilutionVolume, 1, gui.getBackgroundCount());
		gui.getLabelLavageGRMean().setText("Mean : "+df.format(resultsLavageGR[0]));
		gui.getLabelLavageGRSD().setText("SD : "+ df.format(resultsLavageGR[1]));
		gui.getLabelLavageGRCV().setText("CV (%) : "+df.format(resultsLavageGR[2]*100));
		if ((resultsLavageGR[2]*100)>5) {
			gui.getLabelLavageGRCV().setOpaque(true); 
			gui.getLabelLavageGRCV().setBackground(Color.red); 
		}
		else {
			gui.getLabelLavageGRCV().setOpaque(false); 
		}
	}
	
	private void updateLavageSerringueLabels() {
		resultsLavageSerringue=Volume_Modele.calculateMeanSdCv(valeursLavageSerringue, lavageSerringueDilutionVolume, 1, gui.getBackgroundCount());
		gui.getLabelLavageSerringueMean().setText("Mean : "+df.format(resultsLavageSerringue[0]));
		gui.getLabelLavageSerringueSD().setText("SD : "+ df.format(resultsLavageSerringue[1]));
		gui.getLabelLavageSerringueCV().setText("CV (%) : "+df.format(resultsLavageSerringue[2]*100));
		if ((resultsLavageSerringue[2]*100)>5) {
			gui.getLabelLavageSerringueCV().setOpaque(true); 
			gui.getLabelLavageSerringueCV().setBackground(Color.red); 
		}
		else {
			gui.getLabelLavageSerringueCV().setOpaque(false); 
		}
	}
	
	private void calculateAndupdateGRLabels() {
		//On trie les valeurs en fonctions des temps de mesure et on met les valeurs dans une liste separée
		HashMap<Double,List<Double>> mesures=new HashMap<Double,List<Double>>();
		//On ajoute dans la liste a chaque fois qu'on trouve un temps de mesure
		for (int i=0 ; i<valeursGR.size() ; i++) {
			
			// On calcule le nombre de CPM par ml en tenant compte de l'unite
			double cpmMl = 0;
			if(unitGR.equals("CPM/ml")) {
				cpmMl=valeursGR.get(i).getcpmMl() - gui.getBackgroundCount();
			}
			else if(unitGR.equals("CPM/mg")) {
				cpmMl=(valeursGR.get(i).getcpmMl() - gui.getBackgroundCount() )/bloodDensity ;
				
			}
			
			Double Time=valeursGR.get(i).getTime();
			//Si le temps est inconnu on ouvre une nouvelle liste
			if (valeursGR.get(i).getuse() && !mesures.containsKey(Time)) mesures.put(Time, new ArrayList<Double>());
			//On ajoute la valeur a la liste adHoc pour le temps de mesure
			if (valeursGR.get(i).getuse()) mesures.get(Time).add(cpmMl*volumeDilutionGR);
		}
		
		//On cree une nouvelle liste avec les moyenne de coups de chaque temps
		List<Double> moyenneMesureGRparTemps=new ArrayList<Double>();
		Double[] tempsMesure=new Double[mesures.size()];
		mesures.keySet().toArray(tempsMesure);
		
		for (int i=0 ; i<tempsMesure.length; i++) {
			double somme=0;
			for (int j=0 ; j<mesures.get(tempsMesure[i]).size(); j++) {
				somme+=mesures.get(tempsMesure[i]).get(j);
			}
			moyenneMesureGRparTemps.add( somme / (mesures.get(tempsMesure[i]).size()) );
		}
		
		//On recupere les moyenne, SD, CV avec la methode dediee GR	
		resultsGR=Volume_Modele.calculateMeanSdCvGR(moyenneMesureGRparTemps);
		//resultsGR=Volume_Modele.calculateMeanSdCv(valeursGR);
		gui.getLabelLavageGR15Mean().setText("Mean : "+df.format(resultsGR[0]));
		gui.getLabelLavageGR15SD().setText("SD : "+ df.format(resultsGR[1]));
		gui.getLabelLavageGR15CV().setText("CV (%) : "+df.format(resultsGR[2]*100));
		if ((resultsGR[2]*100)>5) {
			gui.getLabelLavageGR15CV().setOpaque(true); 
			gui.getLabelLavageGR15CV().setBackground(Color.red); 
		}
		else {
			gui.getLabelLavageGR15CV().setOpaque(false); 
		}
	}
	
	@SuppressWarnings("unchecked")
	private String buildJson() {
		DateFormat df=new SimpleDateFormat("yyyyMMdd");
		
		JSONObject save=new JSONObject();
		
		//On Ajoute les identification patient
		save.put("PatientLastName", gui.getPatientLastName());
		save.put("PatientFirstName", gui.getPatientFirstName());
		save.put("PatientID", gui.getPatientId());
		save.put("PatientDOB", df.format(gui.getDob()));
		save.put("PatientWeight", gui.getPatientWeight());
		save.put("PatientHeight", gui.getPatientHeight());
		save.put("PatientIsMale", gui.isMale());
		save.put("InjectionDate", df.format(gui.getInjectionDate()));
		save.put("RefferingPhysician", gui.getRefferingPhysician());
		
		// Ajout des poids
		save.put("MesureWeightEmpty", gui.getMasseEmpty());
		save.put("MesureWeightFull", gui.getMasseFull());
		save.put("MesureWeightAfterEtalon", gui.getMasseAfterEtalon());
		
		// Ajout des Hematocrites
		JSONArray hematocrite=new JSONArray();
		for (int i=0 ; i<valeursHematocrite.size() ; i++) {
			hematocrite.add(valeursHematocrite.get(i));
		}
		save.put("Hematocrite", hematocrite);
		
		//Ajout des lot et date radiopharmaceutiques
		save.put("Cr51 Number", gui.getCr51());
		save.put("Cr51 Date", df.format(gui.getCr51Date()));
		save.put("NaCl Number", gui.getNacl());
		save.put("NaCl Date", df.format(gui.getNaclDate()));
		
		// Ajout activite Cr51
		save.put("Cr51 Activity", gui.getCr51Activity());
		
		
		//Ajout des mesures
		
		JSONArray etalon=new JSONArray();
		for (int i=0; i<valeursEtalon.size(); i++) {
			JSONObject resultatEtalon=new JSONObject();
			resultatEtalon.put("CPM", valeursEtalon.get(i).getcpm());
			resultatEtalon.put("ml", valeursEtalon.get(i).getMl());
			resultatEtalon.put("isUsed", valeursEtalon.get(i).getuse());
			
			etalon.add(resultatEtalon);
		}
		save.put("EtalonValue", etalon);
		save.put("IsFullEtalonDilution", this.fullEtalonDilution);
		save.put("EtalonDilutionVolume", this.volumeDilutionEtalon);
		
		
		JSONArray lavageGR=new JSONArray();
		for (int i=0; i<valeursLavageGR.size(); i++) {
			JSONObject resultatLavageGR=new JSONObject();
			resultatLavageGR.put("CPM", valeursLavageGR.get(i).getcpm());
			resultatLavageGR.put("ml", valeursLavageGR.get(i).getMl());
			resultatLavageGR.put("isUsed", valeursLavageGR.get(i).getuse());
			lavageGR.add(resultatLavageGR);
		}	
		save.put("LavageGRValue", lavageGR);
		save.put("LavageGRDilutionVolume", lavageGRDilutionVolume);
				
		
		JSONArray lavageSerringue=new JSONArray();
		for (int i=0; i<valeursLavageSerringue.size(); i++) {
			JSONObject resultatLavageSerringue=new JSONObject();
			resultatLavageSerringue.put("CPM", valeursLavageSerringue.get(i).getcpm());
			resultatLavageSerringue.put("ml", valeursLavageSerringue.get(i).getMl());
			resultatLavageSerringue.put("isUsed", valeursLavageSerringue.get(i).getuse());
			lavageSerringue.add(resultatLavageSerringue);
		}
		save.put("LavageSerringueValue", lavageSerringue);
		save.put("LavageSerringueDilution", lavageSerringueDilutionVolume);
		
		
		JSONArray GR=new JSONArray();
		for (int i=0; i<valeursGR.size(); i++) {
			JSONObject resultatGR=new JSONObject();
			resultatGR.put("Time", valeursGR.get(i).getTime());
			resultatGR.put("CPM", valeursGR.get(i).getcpm());
			resultatGR.put("ml", valeursGR.get(i).getMl());
			resultatGR.put("isUsed", valeursGR.get(i).getuse());
			GR.add(resultatGR);
			
		}
		
		save.put("GRValue", GR);
		save.put("GRValueUnit", unitGR);
		save.put("GRDilution", volumeDilutionGR);
		save.put("BloodDensity", bloodDensity);
		save.put("EtalonDensity", etalonDensity);
		save.put("backgroundCount", gui.getBackgroundCount());
		
		return save.toJSONString();
	}
	

	@Override
	public void stateChanged(ChangeEvent arg0) {
		calculateAndupdateGRLabels();
		updateLavageSerringueLabels();
		updateLavageGrLabels();
		updateEtalonLabels();
		
	}

}

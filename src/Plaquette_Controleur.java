import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.jfree.data.xy.XYSeriesCollection;

import ij.plugin.PlugIn;

public class Plaquette_Controleur implements ActionListener, PlugIn {
	
	private Plaquette_Vue gui=new Plaquette_Vue(this);
	private Plaquette_Modele modele=new Plaquette_Modele();
	private List<Plaquette_Mesure> mesureCollection=new ArrayList<Plaquette_Mesure>();
	private boolean decayCorrection;
	private Plaquette_Result_Frame resultFrame;
	
	public static void main(String[] args) {
		Plaquette_Controleur controleur=new Plaquette_Controleur();
		controleur.gui.pack();
		controleur.gui.setSize(controleur.gui.getPreferredSize());
		controleur.gui.setLocationRelativeTo(null);;
		controleur.gui.setVisible(true);
	}
	
	public Plaquette_Controleur() {
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand()=="Calculate") {
			//Ajouter des check que toutes les valeurs sont entrees
			//Calcul du volume sg total
			double vst=Plaquette_Modele.calculer_VolumeSanguinTheorique(gui.getHeightValue(), gui.getWeightValue(), gui.isMale());
			// On recupere la fraction de recuperation theorique A INTEGRER DANS LA FEUILLE RESULTAT
			double fractionTheorique=modele.calculerRecuperationTheorique(gui.getCpmPerMlInjection(), vst, gui.getInjectedVolume());
			//On calcul les coups corriges et on met dans les objets mesure de la liste
			modele.calculer_countCorrected(mesureCollection, gui.getInjectionDate(), fractionTheorique, decayCorrection);
			modele.calculateMesureTime(mesureCollection, gui.getInjectionDate());
			//On genere le dataset des donn�es
			XYSeriesCollection[] dataset=modele.createDataset(mesureCollection, decayCorrection);
			//Calcul du T1/2
			double half =modele.getYHalf(dataset[0]);
			//Realisation du Fit
			double[] affine=modele.getLinearFit(dataset[0],mesureCollection);
			//Calcul de la duree de vie pq selon fonction affine
			double dureeViePq=modele.calculerDureeViePlaquette(affine);
			//On cree la courbe
			BufferedImage curve=modele.createCurve(dataset[0], affine);
			BufferedImage curveLn=modele.createCurve(dataset[0]);
			
			//On recupere les fraction de Recuperation
			double[][] fractionsRecuperation=modele.get_Fraction_Recuperation(mesureCollection, gui.getInjectionDate());
			
			//On cree l'interface resultat et on affiche
			resultFrame = new Plaquette_Result_Frame(this);
			resultFrame.setPatientIdentification(gui.getLastName(), gui.getFirstName(), gui.getId(), gui.getDob(), gui.getWeightValue(), gui.getHeightValue(), gui.isMale(), gui.getInjectionDate(), dureeViePq, half, curve, curveLn, gui.getPlateletCount(), gui.getPlateletDate(), gui.getRefferingPhysician());
			resultFrame.setFractionRecuperation(fractionsRecuperation);
			resultFrame.setRadiopharmaceutical(gui.getAcdaNumber(), gui.getAcdaDate(), gui.getTrisNumber(), gui.getTrisDate(), gui.getNaClNumber(), gui.getNaClDate(), gui.getInNumber(), gui.getInDate());
			resultFrame.setLabellingValue(gui.getActiviteInj(), gui.getBloodVolume(), gui.getLabellingRate(), gui.getPlateletActivity(), gui.getRedCellActivity(), gui.getPlasmaActivity());
			resultFrame.buildGUI();
			resultFrame.setLocationRelativeTo(null);
			resultFrame.setVisible(true);
			
		}
		
		if (event.getActionCommand()=="Insert measure Value") {
			Plaquette_Insert_Measure measure=new Plaquette_Insert_Measure();
			measure.setInjectionDate(gui.getInjectionDate());
			if (!mesureCollection.isEmpty()) {
				JTable table=measure.getTable();
				for (int i=0; i<mesureCollection.size(); i++) {
					DefaultTableModel model=(DefaultTableModel) table.getModel();
					Calendar measure1=new GregorianCalendar();
					measure1.setTime(mesureCollection.get(i).getMesureTime());
					StringBuilder date=new StringBuilder();
					date.append(measure1.get(Calendar.YEAR));
					int month =measure1.get(Calendar.MONTH)+1;
					String monthString=String.format("%02d", month);
					date.append(monthString);
					date.append(measure1.get(Calendar.DAY_OF_MONTH));
					
					model.addRow(new Object[] {date.toString(), String.valueOf(measure1.get(Calendar.HOUR_OF_DAY)), String.valueOf(measure1.get(Calendar.MINUTE)), String.valueOf(mesureCollection.get(i).getCount()), (boolean) mesureCollection.get(i).getuserForFit()});
				}
				measure.setTable(table);
			}
			measure.setLocationRelativeTo(gui);
			measure.setModal(true);
			measure.pack();
			measure.setSize(measure.getPreferredSize());
			measure.setVisible(true);
			//Si OK
			if (measure.getok()) {
				//On recupere les mesures
				mesureCollection=measure.getMeasures();
				//On recupere l'option decaycorrection
				decayCorrection=measure.getdecayCorrection();
			}
			
			
		}
		
		if (event.getActionCommand()=="Change Fit points") {
			//On ouvre le menu des insertion de mesure
			this.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Insert measure Value"));
			//On ferme l ancien resultat
			resultFrame.dispose();
			//On ouvre le nouveau
			this.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Calculate"));
			
		}
		
		if(event.getActionCommand()=="Capture") {
			//On efface les bouttons 
			resultFrame.getChangeFitButton().setVisible(false);
			resultFrame.getCaptureButton().setVisible(false);
			if (resultFrame.getResultChoice() ==0) resultFrame.getLabelTHalf().setVisible(false);
			if (resultFrame.getResultChoice() ==1) resultFrame.getLabelPlatletLifeTime().setVisible(false);
			//On lance le filechooser
			JFileChooser chooser=new JFileChooser();
			chooser.setSelectedFile(new File("Platelet"+gui.getLastName()+".png"));
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Image", "png");
			chooser.setFileFilter(filter);
			int valide=chooser.showSaveDialog(resultFrame);
	    	//Si valide on capture et on sauve
		    	if (valide==JFileChooser.APPROVE_OPTION) {
		    		Thread t = new Thread(new Runnable() {
		    		    @Override
		    		    public void run() {
		    		    	//BufferedImage capture =null;
							try {
								//Capture, nouvelle methode a utiliser sur le reste des programmes
								Container c = resultFrame.getContentPane();
								BufferedImage capture = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
								c.paint(capture.getGraphics());
								File outputfile = chooser.getSelectedFile();
								ImageIO.write(capture, "png", outputfile);
							} catch (IOException e) {
								e.printStackTrace();
							}
						resultFrame.getChangeFitButton().setVisible(true);
						resultFrame.getCaptureButton().setVisible(true);
						resultFrame.getCombobox().setVisible(true);
		    		    }
	
		    		   });
    		try {
				Thread.sleep(200);
				t.start();
				Thread.sleep(200);
				t.interrupt();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			
		    }
		    	if (valide==JFileChooser.CANCEL_OPTION) {
		    		resultFrame.getChangeFitButton().setVisible(true);
					resultFrame.getCaptureButton().setVisible(true);
					resultFrame.getLabelTHalf().setVisible(true);
					resultFrame.getLabelPlatletLifeTime().setVisible(true);
		    	}
	

		}
	}
		
	

	@Override
	public void run(String arg0) {
		Plaquette_Controleur controleur=new Plaquette_Controleur();
		controleur.gui.pack();
		controleur.gui.setSize(controleur.gui.getPreferredSize());
		controleur.gui.setLocationRelativeTo(null);;
		controleur.gui.setVisible(true);
		
	}
}

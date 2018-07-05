package org.petctviewer.radiopharmacy.bloodVolume;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Volume_Modele {
	
	/**
	* Calcul du poids ideal Lorentz FH. Ein neuer Konstitionsinde. Klin Wochenschr 8: 348–351, 1929 - DOI 
	*/
	protected static double idealWeight(int taille, boolean male) {
		double idealWeight=0;
		if (!male) idealWeight=(taille-100)-((taille-150)/2.5);
		if (male) idealWeight=(taille-100)-((taille-150)/4);
		return idealWeight;
	}
	
	/**
	* Calcul du volume globulaire theorique
	* 
	* @param surfaceCorporelle
	* @param male
	* @param age
	* @return
	*/
	protected static double volumeGlobulaireTheorique(double surfaceCorporelle, boolean male, double age) {
		double volumeGlobulaireTheorique=0;
		if (male) volumeGlobulaireTheorique = (1486*surfaceCorporelle)-825;
		if (!male) volumeGlobulaireTheorique= ((822*surfaceCorporelle)+(1.06*age));
		return volumeGlobulaireTheorique;
	}
	/**
	* Calcul du volume plasmatique theorique
	* @param surfaceCorporelle
	* @param male
	* @return
	*/
	protected static double volumePlasmatiqueTheorique(double surfaceCorporelle, boolean male) {
		double volumePlasmatiqueTheorique=0;
		if (male) volumePlasmatiqueTheorique=surfaceCorporelle*1578;
		if (!male) volumePlasmatiqueTheorique=surfaceCorporelle*1395;
		return volumePlasmatiqueTheorique;
	}
	
	/**
	 * Calculate Mean, SD and Coeff Variation of a list of value
	 * @param valeursList
	 * @return
	 */
	protected static double[] calculateMeanSdCv(List<Volume_Generic_Value> valeursList, double dilutionVolume, double density, int bckg) {
		DescriptiveStatistics stat=new DescriptiveStatistics();
		for (int i=0 ; i<valeursList.size(); i++) {
		if (valeursList.get(i).getuse()) {
			//Substract background from raw counts
			double counts=valeursList.get(i).getcpmMl()-bckg;
			if (counts<0) counts=0;
			//Divide by density in case of non diluted etalon (in this case have a density over 1)
			stat.addValue( ( (counts)*dilutionVolume) / density );
			}
		}
		double results[]= new double[3];
		results[0] = stat.getMean();
		results[1]=stat.getStandardDeviation();
		results[2]=(results[1]/results[0]);
		return results;
	}
	
	protected static double[] calculateMeanSdCvGR(List<Double> valeurs) {
		DescriptiveStatistics stat=new DescriptiveStatistics();
		for (int i=0 ; i<valeurs.size(); i++) {
		stat.addValue(valeurs.get(i));
		}
		double results[]= new double[3];
		results[0] = stat.getMean();
		results[1]=stat.getStandardDeviation();
		results[2]=(results[1]/results[0]);
		return results;
	}
	
	protected static double activiteInjecteeGR(double etalonMean, double masseInjecte, double masseEtalon, double mesureLavage, boolean fullEtalonDilution) {
		double activiteInjectee;
		if (fullEtalonDilution)  activiteInjectee = (etalonMean*masseInjecte/masseEtalon)-(mesureLavage);
		else activiteInjectee = (etalonMean*masseInjecte)-(mesureLavage);
		return activiteInjectee;
	}
	/**
	 * C'est un rendement d'amnistration
	 * SK PAS UTILE A SUPRIMMER PROBABLEMENT
	 * @param masseEtalon
	 * @param masseInjectee
	 * @param mesureEtalon
	 * @param mesureLavageGR
	 * @return
	 */
	protected static double calculRendementMarquageGR(double masseEtalon, double masseInjectee, double mesureEtalon, double mesureLavageGR) {
		double massetotale=masseEtalon+masseInjectee;
		double numerateur =mesureEtalon*massetotale/masseEtalon;
		double rendementMarquage=(numerateur)/(numerateur+mesureLavageGR);
		return rendementMarquage;
	
	}
	
	/**
	 * Calcul de la SC selon Byod
	 * @param taille
	 * @param poids
	 * @return SC Byod
	 */
	protected static double calculScBoyd(int taille, int poids) {
		double scBoyd=0.0003207*Math.pow(poids*1000, (0.7285-0.0188*(Math.log10(poids*1000))))*Math.pow(taille, 0.3);
		return scBoyd;
	}
	
	// A VALIDER PAS SUR
	protected static double calculScPedia(int taille, int poids) {
	double scPedia=(4*poids+7)/(poids+90);
	return scPedia;
	}
	
}

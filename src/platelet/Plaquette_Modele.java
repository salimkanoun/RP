package platelet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Plaquette_Modele {
	
	private double indiumLambda=(Math.log(2)/(2.8*24*3600));
	
	/**
	 * Calcul de la constante de recuperation therorique pour calcul de fraction de recuperation
	 * @return
	 */
	protected double calculerRecuperationTheorique(double cpmInitialmL, double vst, double poidsNet) {
		double recupTheorique=(cpmInitialmL*poidsNet)/vst;
		return recupTheorique;
	}
	
	/**
	 * Calcul le volume sangin total en fonction du sex
	 * @param taille
	 * @param poids
	 * @param masculin
	 * @return
	 */
	protected static double calculer_VolumeSanguinTheorique(int taille, int poids, boolean masculin) {
		double vst=0;
		if (masculin) vst = ((28.6*taille) + (31.7*poids) - 2830);
		if (!masculin) vst = ((16.5*taille) + (38.4*poids) - 1369);
		return vst;
	}
	/**
	 * Calcul Volume sangin total � partir de la formule de SC de Dubois
	 * @param taille
	 * @param poids
	 * @return
	 */
	protected static double calculer_VolumeSanginTheroriqueSC(int taille, int poids) {
		double vst=(840+1410)*calculer_ScDubois(taille, poids);
		return vst;
	}
	
	public static double calculer_ScDubois(int taille, int poids) {
		double surfaceCorporelleDubois=0.007184*Math.pow(taille,0.725)*Math.pow(poids, 0.425);
		return surfaceCorporelleDubois;
	}
	/**
	 * Calcul les coups corriges par rapport a heure d'injection et ajoute la valeur corrig�e dans les objets mesures
	 * @param mesureCollection
	 * @param injectionDate
	 */
	protected void calculer_countCorrected(List<Plaquette_Mesure> mesureCollection, Date injectionDate, double recuperationTheorique, boolean decayCorrection) {
		for (int i=0 ; i<mesureCollection.size() ; i++) {
			int count=mesureCollection.get(i).getCount();
			if (decayCorrection==true) {
				Date measureTime=mesureCollection.get(i).getMesureTime();
				int delaySeconds = (int) (measureTime.getTime()-injectionDate.getTime())/1000;
				double decayedFraction=Math.pow(Math.E, (indiumLambda*delaySeconds*(-1)));
				double correctedCount=count/(decayedFraction);
				mesureCollection.get(i).setCountdecayCorrected(correctedCount);
				mesureCollection.get(i).setFractionRecuperation(correctedCount/recuperationTheorique);
			}
			if (decayCorrection==false) {
				mesureCollection.get(i).setCountdecayCorrected(count);
				mesureCollection.get(i).setFractionRecuperation(count/recuperationTheorique);
				}
		}
	}
	
	protected double [][] get_Fraction_Recuperation(List<Plaquette_Mesure> mesureCollection, Date injectionDate) {
		double[][] fractionRecuperation = new double [2][mesureCollection.size()];
		for (int i=0; i<mesureCollection.size(); i++) {
			fractionRecuperation[1][i]=mesureCollection.get(i).getFractionRecuperation();
			fractionRecuperation[0][i]=(int) (mesureCollection.get(i).getMesureTime().getTime()-injectionDate.getTime())/(1000*3600);
		}
		return fractionRecuperation;
	}
	
	protected BufferedImage createCurve(XYSeriesCollection dataset, double[] regressionParameter) {
		JFreeChart xylineChart = ChartFactory.createXYLineChart("", "Hours", "Counts",dataset, PlotOrientation.VERTICAL, true, true, true);

		XYPlot plot = (XYPlot) xylineChart.getPlot();
		// Background
		plot.setBackgroundPaint(Color.WHITE);

		// XYLineAndShapeRenderer
		// reference:
		// https://stackoverflow.com/questions/28428991/setting-series-line-style-and-legend-size-in-jfreechart
		XYLineAndShapeRenderer lineAndShapeRenderer = new XYLineAndShapeRenderer();
		lineAndShapeRenderer.setSeriesPaint(0, Color.GREEN);
		lineAndShapeRenderer.setSeriesStroke(0, new BasicStroke(2.0F));
		//lineAndShapeRenderer.setBaseItemLabelsVisible(true);
		plot.setRenderer(lineAndShapeRenderer);
		//lineAndShapeRenderer.setBaseLegendTextFont(new Font("", Font.BOLD, 16));
		// XAxis
		NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
		domainAxis.setRange(dataset.getSeries(0).getMinX(), dataset.getSeries(0).getMaxX());
		domainAxis.setTickUnit(new NumberTickUnit(24.00));
		domainAxis.setTickMarkStroke(new BasicStroke(2.5F));
		domainAxis.setLabelFont(new Font("", Font.BOLD, 16));
		domainAxis.setTickLabelFont(new Font("", Font.BOLD, 12));
		// YAxis
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setRange(0.00, dataset.getSeries(0).getMaxY());
		rangeAxis.setTickUnit(new NumberTickUnit(dataset.getSeries(0).getMaxY()/10));
		rangeAxis.setTickMarkStroke(new BasicStroke(2.5F));
		rangeAxis.setLabelFont(new Font("", Font.BOLD, 16));
		rangeAxis.setTickLabelFont(new Font("", Font.BOLD, 12));
		// Grid
		plot.setDomainGridlinesVisible(false);
	
		drawRegressionLine(regressionParameter, xylineChart );
		
		BufferedImage buff = xylineChart.createBufferedImage(640, 512);
		
		return buff;
	}
	
	protected BufferedImage createCurve(XYSeriesCollection dataset) {
		JFreeChart xylineChart = ChartFactory.createXYLineChart("", "Hours", "Counts",dataset, PlotOrientation.VERTICAL, true, true, true);

		XYPlot plot = (XYPlot) xylineChart.getPlot();
		// Background
		plot.setBackgroundPaint(Color.WHITE);
		//plot.setRangeAxis(axis);

		// XYLineAndShapeRenderer
		// reference:
		// https://stackoverflow.com/questions/28428991/setting-series-line-style-and-legend-size-in-jfreechart
		XYLineAndShapeRenderer lineAndShapeRenderer = new XYLineAndShapeRenderer();
		lineAndShapeRenderer.setSeriesPaint(0, Color.GREEN);
		lineAndShapeRenderer.setSeriesStroke(0, new BasicStroke(2.0F));
		plot.setRenderer(lineAndShapeRenderer);
		//lineAndShapeRenderer.setBaseLegendTextFont(new Font("", Font.BOLD, 16));
		// XAxis
		NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
		domainAxis.setAutoRange(true);;
		domainAxis.setTickUnit(new NumberTickUnit(24.00));
		domainAxis.setTickMarkStroke(new BasicStroke(2.5F));
		domainAxis.setLabelFont(new Font("", Font.BOLD, 16));
		domainAxis.setTickLabelFont(new Font("", Font.BOLD, 12));
		// YAxis
		//NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		
		//rangeAxis.setRange(0.00, dataset.getSeries(0).getMaxY());
		//rangeAxis.setTickUnit(new NumberTickUnit(dataset.getSeries(0).getMaxY()/10));
		//rangeAxis.setTickMarkStroke(new BasicStroke(2.5F));
		//rangeAxis.setLabelFont(new Font("", Font.BOLD, 16));
		//rangeAxis.setTickLabelFont(new Font("", Font.BOLD, 12));
		
		NumberAxis rangeAxislog=new LogarithmicAxis("Log(y)");
		rangeAxislog.setAutoRange(true);
		rangeAxislog.setAutoTickUnitSelection(true);
		rangeAxislog.setTickMarkStroke(new BasicStroke(2.5F));
		rangeAxislog.setLabelFont(new Font("", Font.BOLD, 16));
		rangeAxislog.setTickLabelFont(new Font("", Font.BOLD, 12));
		plot.setRangeAxis(rangeAxislog);
		
		// Grid
		plot.setDomainGridlinesVisible(false);
		
		BufferedImage buff = xylineChart.createBufferedImage(640, 512);
		
		return buff;
	}

	/**
	 * Permet de rentrer les donnees corrigée deux datasets (lineaire et ln)
	 * @param mesureCollection
	 * @param injectionTime
	 * @param titre
	 * @return
	 */
	protected XYSeriesCollection[] createDataset(List<Plaquette_Mesure> mesureCollection, Boolean corrected) {
		String titre=null;
		if (corrected) titre="Activity (Decay corrected)";
		if (!corrected) titre="Activity";
		XYSeries courbe = new XYSeries(titre);
		XYSeries courbeln = new XYSeries(titre+" log Scale");
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeriesCollection datasetln = new XYSeriesCollection();
		for (int i = 0; i < mesureCollection.size(); i++) {
			courbe.add(mesureCollection.get(i).getDelayHourFromInjection(), mesureCollection.get(i).getCountdecayCorrected());
			courbeln.add(mesureCollection.get(i).getDelayHourFromInjection(),Math.log10(mesureCollection.get(i).getCountdecayCorrected()));
		}
		dataset.addSeries(courbe);
		datasetln.addSeries(courbeln);
		XYSeriesCollection[] datasets= new XYSeriesCollection[2];
		datasets[0]=dataset;
		datasets[1]=datasetln;
		return datasets;
	}
	
	protected void calculateMesureTime(List<Plaquette_Mesure> mesureCollection, Date injectionTime) {
		for (int i = 0; i < mesureCollection.size(); i++) {
			Date measureTime=mesureCollection.get(i).getMesureTime();
			Double delayHour = (double) ((measureTime.getTime()-injectionTime.getTime())/(1000*3600));
			mesureCollection.get(i).setDelayHourFromInjection(delayHour);
		}
	}
	/**
	 * Fait la regressdatasetion linear pour avoir ax+b (b en 0 et a en 1)
	 * @param dataset
	 * @return
	 */
	protected double[] getLinearFit(XYSeriesCollection dataset, List<Plaquette_Mesure> mesureCollection){
		List<Double> fitPointX=new ArrayList<Double>();
		List<Double> fitPointY=new ArrayList<Double>();
		for (int i=0; i<mesureCollection.size();i++) {
			if (mesureCollection.get(i).getuserForFit())  {
				fitPointY.add(mesureCollection.get(i).getCountdecayCorrected());
				fitPointX.add(mesureCollection.get(i).getDelayHourFromInjection());
			}
		}
		double[][] fitPoint=new double[fitPointX.size()][2];
		for (int i=0; i<fitPointX.size() ; i++) {
			fitPoint[i][0]=fitPointX.get(i);
			fitPoint[i][1]=fitPointY.get(i);
			
		}
		double[] parametreCourbe=Regression.getOLSRegression(fitPoint);
		return parametreCourbe;
	}
	//A FAIRE CHERCHER le T1/2 A TESTER
	protected double getYHalf(XYSeriesCollection dataset){
		double max=dataset.getSeries(0).getMaxY();
		@SuppressWarnings("unchecked")
		List<XYDataItem> valeur=dataset.getSeries(0).getItems();
		int numinf=0;
		int numsup=0;
		double y=0;
		double ymax=max;
		double t=0;
		//On verifie que le t1/2 est mesurable
		if (dataset.getSeries(0).getMinY()<(max/2)) {
			for (int i=0 ; i<valeur.size(); i++) {
			if (valeur.get(i).getY().doubleValue() > y && valeur.get(i).getY().doubleValue()<max/2) {
				y=valeur.get(i).getY().doubleValue();
				numinf=i;
			}
			if (valeur.get(i).getY().doubleValue() < ymax && valeur.get(i).getY().doubleValue()>max/2) {
				ymax=valeur.get(i).getY().doubleValue();
				numsup=i;
			}
		}
		//On prend les parametre qui passe par le point num et num+1
		double[][] fitPoint=new double[2][2];
		fitPoint[1][0]=valeur.get(numinf).getX().doubleValue();
		fitPoint[1][1]=valeur.get(numinf).getY().doubleValue();
		fitPoint[0][0]=valeur.get(numsup).getX().doubleValue();
		fitPoint[0][1]=valeur.get(numsup).getY().doubleValue();
		double[] parametreCourbe=Regression.getOLSRegression(fitPoint);
		t = ((max/2)-parametreCourbe[0])/parametreCourbe[1];
		
		}
		
		return t;
	}
	
	/**
	 * Fait le fit exponentiel
	 * @param dataset
	 * @return
	 */
	/*//NE MARCHE PAS FAIRE UNE FONCTION LN AJOUTER LA COURBE LN ET RELANCER UN FIT LINEAIRE
	protected double[] getExponentialFit(XYSeriesCollection dataset) {
		double[] parametreCourbeExp=Regression.getPowerRegression(dataset, 0);
		System.out.println(parametreCourbeExp[0]+" "+parametreCourbeExp[1]);
		return parametreCourbeExp;
	}*/
	
	/**
	 * Calcul le temps pour x=0 correspondant a la duree de vie des plaquettes
	 * @param affine
	 * @return
	 */
	protected double calculerDureeViePlaquette(double[] affine){
		double dureeViePqJours=((affine[0]*(-1))/affine[1])/24;
		return dureeViePqJours;
	}
	
	private void drawRegressionLine(double[] regressionParameters, JFreeChart xylineChart) {
		
		// Prepare a line function using the found parameters
		LineFunction2D linefunction2d = new LineFunction2D(
				regressionParameters[0], regressionParameters[1]);

		// Creates a dataset by taking sample values from the line function
		XYDataset dataset = DatasetUtils.sampleFunction2D(linefunction2d,
				0D, 300, 100, "Fitted Regression Line");

		// Draw the line dataset
		XYPlot xyplot = xylineChart.getXYPlot();
		xyplot.setDataset(1, dataset);
		XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(
				true, false);
		xylineandshaperenderer.setSeriesPaint(0, Color.ORANGE);
		xyplot.setRenderer(1, xylineandshaperenderer);
	}
	
	
	

}

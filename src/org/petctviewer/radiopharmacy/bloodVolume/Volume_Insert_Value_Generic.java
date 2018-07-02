package org.petctviewer.radiopharmacy.bloodVolume;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;

public class Volume_Insert_Value_Generic extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private boolean ok;
	private JSpinner spinner_dilutionVolume;
	private JCheckBox chckbxFullEtalonDilution= new JCheckBox("Full Etalon Dilution");
	private JSpinner spinner_density = new JSpinner();
	private JLabel lblUnit = new JLabel("Result can be expressed CPM/ml or CPM/g as density is assumed =1  ");
	private JLabel lblDensity = new JLabel("Density");
	private String title="value type";
	private List<Volume_Generic_Value> valeurs;
	DecimalFormat df ;
	private DefaultTableModel model;
	private Preferences jPrefer = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Volume_Insert_Value_Generic dialog = new Volume_Insert_Value_Generic();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Volume_Insert_Value_Generic() {
		makeGui();
	}
	
	public Volume_Insert_Value_Generic(String title) {
		this.title=title;
		if (!title.equals("Etalon")) {
			chckbxFullEtalonDilution.setVisible(false);
			spinner_density.setVisible(false);
			lblDensity.setVisible(false);
			lblUnit.setVisible(true);
		}
		makeGui();
	}

	
	private void makeGui() {
		Image image = new ImageIcon(ClassLoader.getSystemResource("logos/ImageJ.png")).getImage();
		setIconImage(image);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jPrefer = Preferences.userNodeForPackage(Volume_Insert_Value_MesureVG.class);
		jPrefer = jPrefer.node("BloodVolume");
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.'); 
		df = new DecimalFormat("#.#", otherSymbols);
		df.setMaximumFractionDigits(16);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		model=new DefaultTableModel(new Object[] {title+" CPM","Mass (mg or ml)", "Use"},0);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			JScrollPane scrollPane = new JScrollPane();
			panel.add(scrollPane, BorderLayout.CENTER);
			table = new JTable(model) {

	            private static final long serialVersionUID = 1L;
	            @Override
	            public Class<?> getColumnClass(int column) {
	                switch (column) {
	                    case 0:
	                        return String.class;
	                    case 1:
	                        return String.class;
	                    case 2:
	                        return Boolean.class;
	                    default:
	                    	return String.class;
	                }
	            }
	        };
			table.setPreferredScrollableViewportSize(new Dimension(100, 100));
			table.setModel(model);
			table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			scrollPane.setViewportView(table);
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1, BorderLayout.SOUTH);
				panel_1.setLayout(new BorderLayout(0, 0));
				{
					{
						lblUnit.setFont(new Font("Dialog", Font.PLAIN, 10));
						lblUnit.setVisible(false);
						panel_1.add(lblUnit, BorderLayout.NORTH);
					}
					{
						JPanel panel_2 = new JPanel();
						panel_1.add(panel_2, BorderLayout.CENTER);
						panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
						JButton btnAddValue = new JButton("Add Value");
						panel_2.add(btnAddValue);
						{
							{
								JButton btnRemove = new JButton("Remove");
								panel_2.add(btnRemove);
								{
									JPanel buttonPane = new JPanel();
									contentPanel.add(buttonPane, BorderLayout.SOUTH);
									buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
									{
										JButton okButton = new JButton("OK");
										okButton.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent arg0) {
											valeurs=new ArrayList<Volume_Generic_Value>();
											for (int i=0 ; i<table.getRowCount();i++) {
												Volume_Generic_Value valeur=new Volume_Generic_Value(Double.parseDouble(table.getValueAt(i, 0).toString().replaceAll(",", ".")), Double.parseDouble(table.getValueAt(i, 1).toString().replaceAll(",", ".")), Boolean.parseBoolean(table.getValueAt(i, 2).toString()));
												valeurs.add(valeur);
											}
											
											//On sauve le volume de dilution
											jPrefer.putDouble(title, (double) spinner_dilutionVolume.getValue());
											// et la fulldilutionVolume si Etalon
											if (title.equals("Etalon")) jPrefer.putBoolean("etalonFullDilution", chckbxFullEtalonDilution.isSelected());
											
											ok=true;
											dispose();
												
											}
										});
										
										{
											JPanel panel_3 = new JPanel();
											buttonPane.add(panel_3);
											panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
											
											
											panel_3.add(chckbxFullEtalonDilution);
											chckbxFullEtalonDilution.setSelected(jPrefer.getBoolean("etalonFullDilution", false));
										
											JLabel lblDilutionVolume = new JLabel("Dilution Volume (ml)");
											panel_3.add(lblDilutionVolume);
										
										
											spinner_dilutionVolume = new JSpinner();
											panel_3.add(spinner_dilutionVolume);
											spinner_dilutionVolume.setModel(new SpinnerNumberModel(1.0, 1.0, 999.0, 1.0));
											spinner_dilutionVolume.setValue(jPrefer.getDouble(title, 1));
											
											
											panel_3.add(lblDensity);
											spinner_density.setModel(new SpinnerNumberModel(1.0, 1.0, 1.2, 0.01));
											panel_3.add(spinner_density);
											
										}
										okButton.setActionCommand("OK");
										buttonPane.add(okButton);
										getRootPane().setDefaultButton(okButton);
									}
									{
										JButton cancelButton = new JButton("Cancel");
										cancelButton.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												dispose();
											}
										});
										cancelButton.setActionCommand("Cancel");
										buttonPane.add(cancelButton);
									}
								}
								btnRemove.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if (table.getSelectedRow()!=-1) {
											model.removeRow(table.getSelectedRow());
										}
									}
								});
							}
						}
						btnAddValue.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								model.addRow(new Object[]{"", "1", Boolean.TRUE});
								table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
								table.editCellAt(table.getRowCount()-1, 0);
								table.grabFocus();
							}
						});
					}
				}
			}
		}
		
		pack();
		setSize(this.getPreferredSize());
	}
	
	public void setValeurs(List<Volume_Generic_Value> valeurs) {
		for (int i=0; i<valeurs.size() ; i++) {
			model.addRow(new Object[]{valeurs.get(i).getcpm(),valeurs.get(i).getMl(), valeurs.get(i).getuse()});
		}
		
	}
	
	
	
	public double getDilutionVolume() {
		return (double) spinner_dilutionVolume.getValue();
	}
	
	public void setDilutionVolume(double dilutionVolume) {
		spinner_dilutionVolume.setValue(dilutionVolume);
	}
	
	
	public List<Volume_Generic_Value> getValeurs() {
		return this.valeurs;
	}
	
	public void addValeurs( Volume_Generic_Value value) {
		valeurs.add(value);
	}
	
	public boolean getOk() {
		return ok;
	}
	
	
	public boolean isFullEtalonDilution() {
		return chckbxFullEtalonDilution.isSelected();
	}
	
	public void setIsFullEtalonDilution(boolean fullDilution) {
		chckbxFullEtalonDilution.setSelected(fullDilution);
	}
	
	public double getDensity() {
		return (double) spinner_density.getValue();
	}
	
	public void setDensity(double density) {
		spinner_density.setValue(density);
	}

}

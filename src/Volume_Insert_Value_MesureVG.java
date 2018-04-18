import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class Volume_Insert_Value_MesureVG extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private boolean ok;
	private List<Volume_Generic_Value> valeurs;
	DecimalFormat df ;
	private DefaultTableModel model;
	private JSpinner spinner_density, spinner_DilutionVolume;
	private Preferences jPrefer = null;
	private JComboBox<String> comboboxUnit;
	private String unit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Volume_Insert_Value_MesureVG dialog = new Volume_Insert_Value_MesureVG();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.makeGui();
			dialog.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Volume_Insert_Value_MesureVG() {
		
	}
	public void makeGui() {
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
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		model=new DefaultTableModel(new Object[] {"Time (min)","CPM","Volume", "Use" },0);
		
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
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
	                        return String.class;
	                    case 3:
	                        return Boolean.class;
	                    default:
	                    	return String.class;
	                }
	            }
	        };
	       
		
			table.setPreferredScrollableViewportSize(new Dimension(100, 100));
			table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			scrollPane.setViewportView(table);
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1, BorderLayout.SOUTH);
				panel_1.setLayout(new BorderLayout(0, 0));
				{
					{
						JPanel panel_2 = new JPanel();
						panel_1.add(panel_2);
						panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
						JButton btnAddValue = new JButton("Add Value");
						panel_2.add(btnAddValue);
						
							{
								JButton btnRemove = new JButton("Remove");
								panel_2.add(btnRemove);
							
								JLabel lblBloodDensity = new JLabel("Blood density :");

								spinner_density = new JSpinner();
								spinner_density.setModel(new SpinnerNumberModel(new Double(1.05), new Double(1.0), new Double(1.1), new Double(0.01)));
								spinner_density.setLocale(Locale.US);
								spinner_density.setEnabled(false);
								
								String[] unit=new String[] {"CPM/ml", "CPM/mg"};
								comboboxUnit=new JComboBox<String>(unit);
								comboboxUnit.setSelectedIndex(0);
								String unitPref = jPrefer.get("VGUnit", null);
								if (unitPref !=null) {
									if (unitPref.equals("CPM/ml")) comboboxUnit.setSelectedIndex(0);
									else if (unitPref.equals("CPM/mg")) comboboxUnit.setSelectedIndex(1);
								}
								comboboxUnit.setToolTipText("if CPM/mg value will be divided by 1.05 to take accout blood density");
								comboboxUnit.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent e) {
										if (comboboxUnit.getSelectedIndex()==1) {
											spinner_density.setEnabled(true);
										}
										if (comboboxUnit.getSelectedIndex()==0) {
											spinner_density.setEnabled(false);
										}
									}
										
										
								});
								
								panel_2.add(comboboxUnit);
								panel_2.add(lblBloodDensity);
								panel_2.add(spinner_density);
							
							btnRemove.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									if (table.getSelectedRow()!=-1) {
										model.removeRow(table.getSelectedRow());
									}
								}
							});
						}
						btnAddValue.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								model.addRow(new Object[]{"0", "","1", Boolean.TRUE});
								table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
								table.editCellAt(table.getRowCount()-1, 0);
								table.grabFocus();
							}
						});
					}
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					valeurs=new ArrayList<Volume_Generic_Value>();
					for (int i=0 ; i<table.getRowCount();i++) {
						Volume_Generic_Value valeur=new Volume_Generic_Value(Double.parseDouble(table.getValueAt(i, 1).toString().replaceAll(",", ".")),Double.parseDouble(table.getValueAt(i, 2).toString().replaceAll(",", ".")), Boolean.parseBoolean(table.getValueAt(i, 3).toString()));
						Double temps=Double.parseDouble(table.getValueAt(i, 0).toString().replaceAll(",", "."));
						valeur.setTime( temps );
						valeurs.add(valeur);
					}
					unit=(comboboxUnit.getSelectedItem().toString());
					jPrefer.putDouble("VGDilution", (double) spinner_DilutionVolume.getValue());
					jPrefer.put("VGUnit", unit);
					ok=true;
					dispose();
						
					}
				});
				{
					JPanel panel_Dilution_Volume = new JPanel();
					buttonPane.add(panel_Dilution_Volume);
					{
						JLabel label = new JLabel("Dilution Volume (ml)");
						panel_Dilution_Volume.add(label);
					}
					{
						spinner_DilutionVolume = new JSpinner();
						spinner_DilutionVolume.setModel(new SpinnerNumberModel(1.0, 1.0, 999.0, 1.0));
						spinner_DilutionVolume.setValue(jPrefer.getDouble("VGDilution", 1));
						
						
						
						panel_Dilution_Volume.add(spinner_DilutionVolume);
					}
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
		
		pack();
		setSize(this.getPreferredSize());
	}
	
	public void setValeurs(List<Volume_Generic_Value> valeurs) {
		for (int i=0; i<valeurs.size() ; i++) {
			model.addRow(new Object[]{valeurs.get(i).getTime(),valeurs.get(i).getcpm(),valeurs.get(i).getMl(), valeurs.get(i).getuse(), "CPM/ml"});
		}
	}
	
	public boolean getOk() {
		return ok;
	}
	
	public double getDilutionVolume() {
		return (double) spinner_DilutionVolume.getValue();
	}
	
	public double getBloodDensity() {
		return (double)spinner_density.getValue();
	}
	
	public List<Volume_Generic_Value> getValeurs() {
		return this.valeurs;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit=unit;
		if (unit.equals("CPM/ml")) {
			this.comboboxUnit.setSelectedIndex(0);
		}
		else if (unit.equals("CPM/mg")) {
			this.comboboxUnit.setSelectedIndex(1);
			spinner_density.setEnabled(true);
			
		}
	}
	
	
	
}

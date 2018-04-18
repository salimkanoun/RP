import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

public class Volume_Insert_Value_Hematocrite extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private List<Double> hematocriteValues;
	boolean ok=false;
	private DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Volume_Insert_Value_Hematocrite dialog = new Volume_Insert_Value_Hematocrite();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Volume_Insert_Value_Hematocrite() {
		Image image = new ImageIcon(ClassLoader.getSystemResource("logos/ImageJ.png")).getImage();
		setIconImage(image);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		model=new DefaultTableModel(new Object[] {"Hematocrite (%)"},0);
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new BorderLayout(0, 0));
			JScrollPane scrollPane = new JScrollPane();
			panel.add(scrollPane);
			table = new JTable();
			table.setPreferredScrollableViewportSize(new Dimension(100, 100));
			table.setModel(model);
			table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			scrollPane.setViewportView(table);
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1, BorderLayout.SOUTH);
				{
					JButton btnAddValue = new JButton("Add Value");
					panel_1.add(btnAddValue);
					{
						JButton btnRemove = new JButton("Remove");
						btnRemove.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if (table.getSelectedRow()!=-1) {
									model.removeRow(table.getSelectedRow());
								}
							}
						});
						panel_1.add(btnRemove);
					}
					btnAddValue.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							model.addRow(new Object[]{""});
							table.editCellAt(table.getRowCount()-1, 0);
							table.grabFocus();
						}
					});
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
						hematocriteValues=new ArrayList<Double>();
						for (int i=0; i<table.getRowCount(); i++) {
							double hematocrite=Double.parseDouble(table.getValueAt(i, 0).toString().replaceAll("%", ""));
							if (hematocrite<1 || hematocrite>100) {
								JOptionPane.showMessageDialog(null,
									    "Hematocrite should be expressed in % (ex :55%)",
									    "Error Value",
									    JOptionPane.WARNING_MESSAGE);
							}
							else {
								hematocriteValues.add(hematocrite);
							}
						}
						
						ok=true;
						dispose();
					}
				});
				
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
	
	/*private boolean calculateMeanHematocrite() {
		double sumHematocrite=0;
		boolean valueCorrect=true;
		hematocriteValues=new ArrayList<Double>();
		for (int i=0; i<table.getRowCount(); i++) {
			double hematocrite=Double.parseDouble(table.getValueAt(i, 0).toString().replaceAll("%", ""));
			if (hematocrite<1 || hematocrite>100) {
				JOptionPane.showMessageDialog(this,
					    "Hematocrite should be expressed in % (ex :55%)",
					    "Error Value",
					    JOptionPane.WARNING_MESSAGE);
				valueCorrect=false;
			}
			else {
				hematocriteValues.add(hematocrite);
				sumHematocrite+=hematocrite;
			}
			
		}
		meanHematocrite=sumHematocrite/table.getRowCount();
		return valueCorrect;
	}*/
	
	//public double getMeanHematocrite() {
	//	return this.meanHematocrite;
	//}
	
	public List<Double> getHematocriteValues(){
		return hematocriteValues;
	}
	
	public void setValeurs(List<Double> valeurs) {
		for (int i=0; i<valeurs.size() ; i++) {
			model.addRow(new Object[] {valeurs.get(i)});
		}
	}
	

}

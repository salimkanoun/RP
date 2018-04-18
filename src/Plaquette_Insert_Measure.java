import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class Plaquette_Insert_Measure extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private List<Plaquette_Mesure> mesureCollection=new ArrayList<Plaquette_Mesure>();
	private Date injectionDate=new Date();
	private JDialog dialog=this;
	private boolean decayCorrection=true;
	// A SAUVER DANS LES JPREFS
	private JCheckBox chckbxinDecayCorrection; 
	private Preferences jPrefer = null;
	private boolean ok=false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Plaquette_Insert_Measure dialog = new Plaquette_Insert_Measure();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setSize(dialog.getPreferredSize());
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Plaquette_Insert_Measure() {
		makeGUI();
	}
	
	public void makeGUI() {
		jPrefer = Preferences.userNodeForPackage(Plaquette_Insert_Measure.class);
		jPrefer = jPrefer.node("PlateletLife");
		setTitle("Enter Measure");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(new BorderLayout(0, 0));
		
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
			
		      DefaultTableModel model = new DefaultTableModel(new Object[] {"Date", "Hour", "Minutes", "Activity", "Use For Fit"},0);
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
			                        return String.class;
			                    case 4:
			                        return Boolean.class;
			                    default:
			                    	return String.class;
			                }
			            }
			        };
			//this.model = (DefaultTableModel) table.getModel();
			table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			scrollPane.setViewportView(table);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnAddValue = new JButton("Add Value");
				btnAddValue.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Plaquette_Add_Value addValue=new Plaquette_Add_Value();
						addValue.setDate(injectionDate);
						addValue.setModal(true);
						addValue.setLocationRelativeTo(dialog);
						addValue.setVisible(true);
						if (addValue.getOk()) model.addRow(new Object[] {addValue.getDate(),String.valueOf(addValue.getHour()), String.valueOf(addValue.getMinutes()),String.valueOf(addValue.getCounts()), addValue.getUseForFit()});
					}
				});
				{
					chckbxinDecayCorrection = new JCheckBox("111In Decay correction");
					chckbxinDecayCorrection.setSelected(jPrefer.getBoolean("decayCorrection", true));
					buttonPane.add(chckbxinDecayCorrection);
				}
				buttonPane.add(btnAddValue);
			}
			{
				JButton btnEraseValue = new JButton("Erase Value");
				btnEraseValue.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (table.getSelectedRow()!=-1) model.removeRow(table.getSelectedRow());
					}
				});
				buttonPane.add(btnEraseValue);
			}
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						decayCorrection=chckbxinDecayCorrection.isSelected();
						for (int i=0; i<table.getRowCount(); i++) {
							Plaquette_Mesure mesure=new Plaquette_Mesure((String) table.getValueAt(i, 0), Integer.parseInt((String) table.getValueAt(i, 1)), Integer.parseInt((String) table.getValueAt(i, 2)), Integer.parseInt((String) table.getValueAt(i, 3)), (boolean) table.getValueAt(i, 4));
							mesureCollection.add(mesure);
						}
							jPrefer.putBoolean("decayCorrection", chckbxinDecayCorrection.isSelected());
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
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
public List<Plaquette_Mesure> getMeasures(){
	return mesureCollection;
}
public JTable getTable(){
	return table;
}

public void setTable(JTable table){
	this.table= table;
}
public void setInjectionDate(Date dateOfInjection) {
	this.injectionDate=dateOfInjection;
}
public boolean getdecayCorrection() {
	return decayCorrection;
}
public boolean getok() {
	return ok;
}


}

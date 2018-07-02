package platelet;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.michaelbaranov.microba.calendar.DatePicker;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.awt.Component;
import javax.swing.Box;

public class Plaquette_Add_Value extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField_Counts;
	private DatePicker measureDate ;
	private Date dateInjection=new Date() ;
	private Date dateMeasure;
	private int hour, minutes;
	private int counts;
	private JSpinner spinner_minutes, spinner_hours;
	private boolean ok;
	private JCheckBox chckbxUseForFit;
	private boolean useForFit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Plaquette_Add_Value dialog = new Plaquette_Add_Value();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Plaquette_Add_Value() {
		setBounds(100, 100, 450, 300);
		setTitle("Insert measure");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new GridLayout(0, 2, 5, 0));
			{
				JLabel lblNewLabel = new JLabel("Date");
				panel.add(lblNewLabel);
			}
			{
				measureDate = new DatePicker(this.dateInjection, new SimpleDateFormat("yyyy-MM-dd"));
				panel.add(measureDate);
			}
			{
				JLabel lblHours = new JLabel("Hours");
				panel.add(lblHours);
			}
			{
				spinner_hours = new JSpinner();
				panel.add(spinner_hours);
				spinner_hours.setModel(new SpinnerNumberModel(23, null, 23, 1));
			}
			{
				JLabel lblMinutes = new JLabel("minutes");
				panel.add(lblMinutes);
			}
			{
				spinner_minutes = new JSpinner();
				panel.add(spinner_minutes);
				spinner_minutes.setModel(new SpinnerNumberModel(59, null, 59, 1));
			}
			{
				JLabel lblCounts = new JLabel("Counts");
				panel.add(lblCounts);
			}
			{
				textField_Counts = new JTextField();
				panel.add(textField_Counts);
				textField_Counts.setColumns(10);
			}
			{
				Component horizontalStrut = Box.createHorizontalStrut(0);
				horizontalStrut.setEnabled(false);
				panel.add(horizontalStrut);
			}
			{
				chckbxUseForFit = new JCheckBox("Use For Fit");
				chckbxUseForFit.setSelected(true);
				panel.add(chckbxUseForFit);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dateMeasure=measureDate.getDate();
						hour=(int) spinner_hours.getValue();
						minutes=(int) spinner_minutes.getValue();
						counts = Integer.parseInt(textField_Counts.getText());
						useForFit=chckbxUseForFit.isSelected();
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
		pack();
		this.setPreferredSize(this.getPreferredSize());
	}
	
	public String getDate() {
		DateFormat df= new SimpleDateFormat("yyyyMMdd");
		return df.format(dateMeasure);
	}
	
	public int getHour() {
		return hour;
	}
	public int getMinutes() {
		return minutes;
	}
	public int getCounts() {
		return counts;
	}
	public void setDate(Date dateInjection) { 
		try {
			measureDate.setDate(dateInjection);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dateInjection=dateInjection;
		}
	public boolean getUseForFit() {
		return useForFit;
	}
	public boolean getOk() {
		return ok;
	}
}

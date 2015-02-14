import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DobbelsteenController extends JPanel implements ActionListener
{
	DobbelsteenModel dob;
	private JButton hoger = new JButton("hoger");
    private JButton lager = new JButton("lager");
    private JButton gooi = new JButton("gooi");

	public DobbelsteenController(DobbelsteenModel d)
	{
		dob=d;
		this.add(hoger);
		hoger.addActionListener(this);
        this.add(lager);
        lager.addActionListener(this);
        this.add(gooi);
        gooi.addActionListener(this);
	}
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == hoger)
        {
            dob.verhoog();
        }  
        if (e.getSource() == lager)
        {
            dob.verlaag();
        } 
        if (e.getSource() == gooi)
        {
            dob.gooi();
        } 
	}
	public Dimension getPreferredSize()
	{
	    return new Dimension(50,50);
	} 
}

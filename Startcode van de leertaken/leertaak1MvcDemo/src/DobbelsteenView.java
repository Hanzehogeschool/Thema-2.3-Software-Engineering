import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
    
public class DobbelsteenView extends JPanel implements ActionListener
{
	private int waarde;
    private Color kleur;
    DobbelsteenModel d;
	public DobbelsteenView(Color c)
	{
		kleur=c;
	}
    
    public void actionPerformed( ActionEvent e )
	{
	    d = (DobbelsteenModel) e.getSource();
	    waarde = d.getWaarde();
	    repaint();
	    
	}
	public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(kleur);
        g.fillRoundRect(5,5,70,70,10,10);
        g.setColor(Color.black);
        if (waarde==1)
        {
            g.fillOval(34,34,10,10);
        }
        else if (waarde==2)
        {
            g.fillOval(10,10,10,10);
            g.fillOval(60,60,10,10);
        }
        else if (waarde==3)
        {
            g.fillOval(10,10,10,10);
            g.fillOval(34,34,10,10);
            g.fillOval(60,60,10,10);
        }
        else if (waarde==4)
        {
            g.fillOval(10,10,10,10);
            g.fillOval(10,60,10,10);
            g.fillOval(60,10,10,10);
            g.fillOval(60,60,10,10);
        }
        else if (waarde==5)
        {
            g.fillOval(10,10,10,10);
            g.fillOval(10,60,10,10);
            g.fillOval(60,10,10,10);
            g.fillOval(60,60,10,10);
            g.fillOval(34,34,10,10);
        }
        else if (waarde==6)
        {
            g.fillOval(10,10,10,10);
            g.fillOval(10,60,10,10);
            g.fillOval(60,10,10,10);
            g.fillOval(60,60,10,10);
            g.fillOval(10,34,10,10);
            g.fillOval(60,34,10,10);
        }
        else; // meer waarden zijn er niet
    }    
	public Dimension getPreferredSize()
	{
	    return new Dimension(80,80);
	}    
}

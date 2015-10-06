package ui;

import java.util.Collections;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import solutions.District;
import solutions.DistrictMap;
import solutions.Settings;

import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

public class FrameRankedDistricts extends JFrame {
	public JPanel panel;
	public JScrollPane scrollPane;
	public JLabel lblVotes;
	public JLabel lblSeats;
	public JTable table;
	public static Vector<double[]> seats_votes = new Vector<double[]>(); 
	public JButton btnCopy;
	//label.setUI(new VerticalLabelUI());
	public FrameRankedDistricts() {
		super();
		initComponents();
	}
	
	class SeatPanel extends JPanel {
	    public void paintComponent(Graphics graphics0) {
	    	try {
	    		int l0 = 128+64+32+16;
	    		int l1 = 128+64+32;
	    		int l2 = 128+64;
	    		int l3 = 128;
				Graphics2D g = (Graphics2D)graphics0;
			    super.paintComponent(g);
			    g.setColor(Color.white);
			    g.fillRect(0, 0, 200, 200);

			    g.setColor(new Color(l2,l2,255));
			    g.fillRect(0, 0, 33, 200);
			    g.setColor(new Color(l1,l1,255));
			    g.fillRect(33, 0, 33, 200);
			    g.setColor(new Color(l0,l0,255));
			    g.fillRect(66, 0, 34, 200);

			    
			    g.setColor(new Color(255,l0,l0));
			    g.fillRect(100, 0, 33, 200);
			    g.setColor(new Color(255,l1,l1));
			    g.fillRect(133, 0, 33, 200);
			    g.setColor(new Color(255,l2,l2));
			    g.fillRect(166, 0, 34, 200);

				double inc = 200.0/(double)seats_votes.size();
				double y = 0;

			    for( int i = 0; i < seats_votes.size(); i++) {
			    	double[] dd = seats_votes.get(i);
			    	int w = (int)(dd[0]*100);
			    	int x = 100;
			    	if( w < 0) {
			    		x = x+w;
			    		w = -w;
					    g.setColor(new Color(l3,l3,255));
			    	} else {
					    g.setColor(new Color(255,l3,l3));
			    		
			    	}
			    	g.fillRect(x, (int)y, w, (int)Math.round(inc));
			    	y += inc;
			    }

			    g.setColor(Color.gray);
			    //g.drawLine(0,200, 200, 0);
			    g.drawLine(100,0, 100, 200);


	    	} catch (Exception ex) {
	    		ex.printStackTrace();
	    	}
	    }
	}
	
	private void initComponents() {
		getContentPane().setLayout(null);
		setSize(284,524);
		setTitle("Ranked district PVI");
		
		panel = new SeatPanel();
		panel.setBounds(40, 11, 200, 200);
		getContentPane().add(panel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 257, 200, 200);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		lblVotes = new JLabel("PVI -->");
		lblVotes.setBounds(40, 222, 79, 14);
		getContentPane().add(lblVotes);
		
		lblSeats = new JLabel("Rank -->");
		lblSeats.setUI(new VerticalLabelUI());
		lblSeats.setBounds(10, 136, 20, 75);
		getContentPane().add(lblSeats);
		
		btnCopy = new JButton("copy");
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActionEvent nev = new ActionEvent(table, ActionEvent.ACTION_PERFORMED, "copy");
				table.selectAll();
				table.getActionMap().get(nev.getActionCommand()).actionPerformed(nev);
			}
		});
		btnCopy.setBounds(151, 222, 89, 23);
		getContentPane().add(btnCopy);
	}
	public void setData(Vector<Double> ranked_dists) {
		Collections.sort(ranked_dists);
		
		Vector<double[]> swap = new Vector<double[]>();
		double inc = 1.0/(double)ranked_dists.size();
		double x = 0;
		for( int i = 0; i < ranked_dists.size(); i++) {
			swap.add(new double[]{(ranked_dists.get(i)/50.0),x});
			x += inc;
		}

		seats_votes = swap;
		
		//now set the table
		String[][] sd = new String[seats_votes.size()][2];
		for( int i = 0; i < sd.length; i++) {
			double[] dd = seats_votes.get(i);
			sd[i] = new String[]{""+dd[0],""+dd[1]};
		}
		TableModel tm1 = new DefaultTableModel(sd,new String[]{"PVI","Rank"});
		table.setModel(tm1);
		panel.invalidate();
		panel.repaint();

	}
}

package view;
    
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.ControllerInterface;
import model.BeatModelInterface;

public class DJView implements ActionListener,  BeatObserver, BPMObserver, ViewInterface {
	BeatModelInterface model;
	ControllerInterface controller;
    JFrame viewFrame;
    JPanel viewPanel;
	BeatBar beatBar;
	JLabel bpmOutputLabel;
    JFrame controlFrame;
    JPanel controlPanel;
    JLabel bpmLabel;
    JTextField bpmTextField;
    JButton setBPMButton;
    JButton increaseBPMButton;
    JButton decreaseBPMButton;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem startMenuItem;
    JMenuItem stopMenuItem;
	private JMenuItem beatMenuItem;
	private JMenu menu2;
	private JMenuItem heartMenuItem;
	private JMenuItem musicalMenuItem;
    BPMObserver bpmO;
    BeatObserver beatO;

    public DJView(ControllerInterface controller, BeatModelInterface model) {	
		this.controller = controller;
		this.model = model;
		model.registerObserver((BeatObserver)this);
		model.registerObserver((BPMObserver)this);
    }
    
    public void createView() {
		// Create all Swing components here
        viewPanel = new JPanel(new GridLayout(1, 2));
        viewFrame = new JFrame("View");
        viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewFrame.setSize(new Dimension(100, 80));
        bpmOutputLabel = new JLabel("offline", SwingConstants.CENTER);
		beatBar = new BeatBar();
		beatBar.setValue(0);
        JPanel bpmPanel = new JPanel(new GridLayout(2, 1));
		bpmPanel.add(beatBar);
        bpmPanel.add(bpmOutputLabel);
        viewPanel.add(bpmPanel);
        viewFrame.getContentPane().add(viewPanel, BorderLayout.CENTER);
        viewFrame.pack();
        viewFrame.setVisible(true);
	}
  
    
    public void createControls() {
		// Create all Swing components here

        JFrame.setDefaultLookAndFeelDecorated(true);
        controlFrame = new JFrame("Control");
        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlFrame.setSize(new Dimension(100, 80));

        controlPanel = new JPanel(new GridLayout(1, 2));

        menuBar = new JMenuBar();
        menu = new JMenu("DJ Control");
        startMenuItem = new JMenuItem("Start");
        menu.add(startMenuItem);
        startMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controller.start();
              //  controller.on();
            }
        });
        stopMenuItem = new JMenuItem("Stop");
        menu.add(stopMenuItem); 
        stopMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controller.stop();
            }
        });
        JMenuItem exit = new JMenuItem("Quit");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	controller.off();
            }
        });

        menu.add(exit);
        menuBar.add(menu);
        /*
        menu2 = new JMenu("Modelo");
        beatMenuItem = new JMenuItem("Beat");
        menu2.add(beatMenuItem);
        beatMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controller.BeatView();
            }
        });
        heartMenuItem = new JMenuItem("Heart");
        menu2.add(heartMenuItem); 
        heartMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controller.HeartView();
            }
        });
        musicalMenuItem= new JMenuItem("Musical Notes");
        menu2.add(musicalMenuItem); 
        musicalMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controller.MusicalView();
            }
        });        
        menuBar.add(menu2);
               */
        controlFrame.setJMenuBar(menuBar);

        bpmTextField = new JTextField(2);
        bpmLabel = new JLabel("Enter BPM:", SwingConstants.RIGHT);
        setBPMButton = new JButton("Set");
        setBPMButton.setSize(new Dimension(10,40));
        increaseBPMButton = new JButton(">>");
        decreaseBPMButton = new JButton("<<");
        setBPMButton.addActionListener(this);
        increaseBPMButton.addActionListener(this);
        decreaseBPMButton.addActionListener(this);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

		buttonPanel.add(decreaseBPMButton);
		buttonPanel.add(increaseBPMButton);

        JPanel enterPanel = new JPanel(new GridLayout(1, 2));
        enterPanel.add(bpmLabel);
        enterPanel.add(bpmTextField);
        JPanel insideControlPanel = new JPanel(new GridLayout(3, 1));
        insideControlPanel.add(enterPanel);
        insideControlPanel.add(setBPMButton);
        insideControlPanel.add(buttonPanel);
        controlPanel.add(insideControlPanel);
        
        bpmLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        bpmOutputLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        controlFrame.getRootPane().setDefaultButton(setBPMButton);
        controlFrame.getContentPane().add(controlPanel, BorderLayout.CENTER);

        controlFrame.pack();
        controlFrame.setVisible(true);
    }

	public void enableStopMenuItem() {
    	stopMenuItem.setEnabled(true);
	}

	public void disableStopMenuItem() {
    	stopMenuItem.setEnabled(false);
	}

	public void enableStartMenuItem() {
    	startMenuItem.setEnabled(true);
	}

	public void disableStartMenuItem() {
    	startMenuItem.setEnabled(false);
	}

    public void actionPerformed(ActionEvent event) {
		if (event.getSource() == setBPMButton) {
			//int bpm = Integer.parseInt(bpmTextField.getText());
        	controller.setBPM(bpmTextField.getText());
		} else if (event.getSource() == increaseBPMButton) {
			controller.increaseBPM();
		} else if (event.getSource() == decreaseBPMButton) {
			controller.decreaseBPM();
		}
    }

	public void updateBPM() {
		if (model != null) {
			int bpm = model.getBPM();
			if (bpm == 0) {
				if (bpmOutputLabel != null) {
        			bpmOutputLabel.setText("offline");
				}
			} else {
				if (bpmOutputLabel != null) {
					if(controller.getnInst()==-1){
						bpmOutputLabel.setText("Current BPM: " + model.getBPM());
					}
					else{
						bpmOutputLabel.setText("Intentos de creacion: " + controller.getnInst());
					}
						
        		}
			}
		}
	}
  
	public void updateBeat() {
		if (beatBar != null) {
			 beatBar.setValue(100);
		}
	}
	public void end(){
		controlFrame.setVisible(false);
    	viewFrame.setVisible(false);
	}

	@Override
	public void setModel(BeatModelInterface beat, ControllerInterface controller) {
		// TODO Auto-generated method stub
		
	}

}
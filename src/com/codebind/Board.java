package com.codebind;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Board extends JPanel {
    JPanel panelMain;
    private JPanel panelWorld, panelButtons;
    private JButton buttonStart, buttonStop, buttonReset, buttonExit;
    private boolean[][] society = new boolean[30][40];

    /**
     * Constructor
     */
    Board() {
        generatePanelMain();
        generatePanelWorld();
        generatePanelButtons();
        generateButtons();

        this.panelMain.add(this.panelWorld);
        this.panelButtons.add(this.buttonStart);
        this.panelButtons.add(this.buttonStop);
        this.panelButtons.add(this.buttonReset);
        this.panelButtons.add(this.buttonExit);
        this.panelMain.add(this.panelButtons);

        newWorld();
    }

    /**
     * Generate main-panel
     */
    private void generatePanelMain() {
        Dimension d = new Dimension(800, 500);
        this.panelMain = new JPanel();
        this.panelMain.setSize(d);
        this.panelMain.setMinimumSize(d);
        this.panelMain.setPreferredSize(d);
        this.panelMain.setMaximumSize(d);
        this.panelMain.setBackground(Color.GRAY);
        this.panelMain.setVisible(true);
    }

    /**
     * Generate world-panel
     */
    private void generatePanelWorld() {
        Dimension d = new Dimension(600, 450);
        this.panelWorld = new JPanel();
        this.panelWorld.setSize(d);
        this.panelWorld.setMinimumSize(d);
        this.panelWorld.setMaximumSize(d);
        this.panelWorld.setPreferredSize(d);
        this.panelWorld.setBackground(Color.blue);
        this.panelWorld.setVisible(true);
    }

    /**
     * Generate buttons-panel
     */
    private void generatePanelButtons() {
        Dimension d = new Dimension(600, 50);
        this.panelButtons = new JPanel();
        this.panelButtons = new JPanel();
        this.panelButtons.setMinimumSize(d);
        this.panelButtons.setMaximumSize(d);
        this.panelButtons.setPreferredSize(d);
        this.panelButtons.setBackground(Color.lightGray);
        this.panelButtons.setVisible(true);
        this.panelButtons.setSize(d);
    }

    /**
     * Generate buttons
     */
    private void generateButtons() {
        this.buttonStart = new JButton("Start");
        this.buttonStop = new JButton("Stop");
        this.buttonReset = new JButton("Reset");
        this.buttonExit = new JButton("Exit");

        // Click-action for button start
        this.buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCircle();
            }
        });

        // Click-action for button stop
        this.buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopCircle();
            }
        });

        // Click-action for button-reset
        this.buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newWorld();
            }
        });

        // Click-action for button-exit
        this.buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * Action-Listener for generator of new generation
     */
    private ActionListener circleGenerations = new ActionListener () {
        public void actionPerformed ( ActionEvent evt) {
            emptyWorld();
            calculateNewGeneration();
            printOutGeneration();
        }
    };

    /**
     * Timer for generator of new generation
     */
    private Timer circle = new Timer(1000, circleGenerations);

    /**
     * Generate new human cube
     * @return JPanel human
     */
    private JPanel generateHuman() {
        Dimension d = new Dimension(10, 10);
        JPanel human = new JPanel();
        human.setMinimumSize(d);
        human.setMaximumSize(d);
        human.setPreferredSize(d);
        human.setVisible(true);
        return human;
    }

    /**
     * Generate new random society (generation)
     */
    private void generateFirstSociety() {
        for (int x = 0; x < this.society.length; x++) {
            for (int y = 0; y < this.society[x].length; y++) {
                this.society[x][y] = Math.random() >= 0.5;
            }
        }
    }

    /**
     * Print actual society (generation)
     */
    private void printOutGeneration() {
        for (boolean[] xLine : this.society) {
            for (boolean yLine : xLine) {
                JPanel human = generateHuman();
                if (yLine) {
                    human.setBackground(Color.GREEN);
                } else {
                    human.setBackground(Color.BLACK);
                }
                this.panelWorld.add(human);
            }
        }

        // Refresh GUI
        this.panelWorld.revalidate();
        this.panelWorld.repaint();
    }

    /**
     * Create new world with new first society (generation)
     */
    private void newWorld(){
        emptyWorld();
        generateFirstSociety();
        printOutGeneration();
    }

    /**
     * Start Generator-Timer
     */
    private void startCircle() {
        this.circle.start();
    }

    /**
     * Stop Generator-Timer
     */
    private void stopCircle(){
        this.circle.stop();
    }

    /**
     * Delete actual society (generation)
     */
    private void emptyWorld(){
        this.panelWorld.removeAll();
        this.panelWorld.revalidate();
        this.panelWorld.repaint();
    }

    /**
     * Calculate if human dies or not
     * -> generate new society
     */
    private void calculateNewGeneration(){
        int counter = 0;

        for (int x = 0; x < society.length; x++){
            for (int y = 0; y < society[x].length; y++){
                counter = 0;
                for (int a = -1; a < 2; a++){
                    if (x == 0 && a == -1){
                        continue;
                    }
                    if (x == society.length-1 && a == +1){
                        continue;
                    }
                        if (y > 0){
                            if (society[x+a][y-1]){
                                counter++;
                            }
                        }
                        if (a != 0){
                            if (society[x+a][y]){
                                counter++;
                            }
                        }
                        if (y < society[x].length-1){
                            if (society[x+a][y+1]){
                                counter++;
                            }
                        }
                }
                this.society[x][y] = counter >= 2 && counter <= 3;
            }
        }
    }
}
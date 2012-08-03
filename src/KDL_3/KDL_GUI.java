/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * KDL_GUI.java
 *
 * Created on 09-Feb-2011, 13:13:23
 */

package KDL_3;

/**
 *
 * @author Edward Moore
 */

public class KDL_GUI extends javax.swing.JFrame {

    /** Creates new form KDL_GUI */
    public KDL_GUI() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        playerCountLabel = new javax.swing.JLabel();
        playGameButton = new javax.swing.JButton();
        gameOutputScrollPane = new javax.swing.JScrollPane();
        gameOutputTextArea = new javax.swing.JTextArea();
        playerCountTextField = new javax.swing.JTextField();
        gameOutputLabel = new javax.swing.JLabel();
        boardSelectionChoices = new javax.swing.JComboBox();
        boardSelection = new javax.swing.JLabel();
        cardSelection = new javax.swing.JLabel();
        cardsSelectionChoices = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kill Doctor Lucky v2");

        playerCountLabel.setText("No. of players (3-7):");

        playGameButton.setText("PLAY");
        playGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playGameButtonActionPerformed(evt);
            }
        });

        gameOutputTextArea.setColumns(20);
        gameOutputTextArea.setRows(5);
        gameOutputScrollPane.setViewportView(gameOutputTextArea);

        playerCountTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        playerCountTextField.setText("0");
        playerCountTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playerCountTextFieldActionPerformed(evt);
            }
        });

        gameOutputLabel.setText("Game Output:");

        boardSelectionChoices.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Original Mansion", "Directors Cut Mansion" }));

        boardSelection.setText("Board");

        cardSelection.setText("Cards");

        cardsSelectionChoices.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Original Cards", "Reduced Failure Cards" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gameOutputScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gameOutputLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 533, Short.MAX_VALUE)
                        .addComponent(playGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(boardSelection)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boardSelectionChoices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(cardSelection)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cardsSelectionChoices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(playerCountLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(playerCountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(209, 209, 209))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boardSelection)
                    .addComponent(boardSelectionChoices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardSelection)
                    .addComponent(cardsSelectionChoices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(playerCountLabel)
                    .addComponent(playerCountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(playGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(gameOutputLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(gameOutputScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void playGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playGameButtonActionPerformed
        //clear gameOutputTextArea
        gameOutputTextArea.selectAll();
        gameOutputTextArea.cut();
        String boardChoice;
        String cardsChoice;

        //Get Board selection
        boardChoice = (String) boardSelectionChoices.getSelectedItem();

        //Get Cards selection
        cardsChoice = (String) cardsSelectionChoices.getSelectedItem();

        //  Parse int supplied by user in text field
        int playerCount = Integer.parseInt(playerCountTextField.getText());

        //Create and play game, using playerCount value supplied by user
        // N.B. all game output is displayed in the gameOutputTextArea

        Game myGame = new Game(playerCount, gameOutputTextArea, boardChoice, cardsChoice);
        myGame.playGame();
    }//GEN-LAST:event_playGameButtonActionPerformed

    private void playerCountTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playerCountTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_playerCountTextFieldActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KDL_GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel boardSelection;
    private javax.swing.JComboBox boardSelectionChoices;
    private javax.swing.JLabel cardSelection;
    private javax.swing.JComboBox cardsSelectionChoices;
    private javax.swing.JLabel gameOutputLabel;
    private javax.swing.JScrollPane gameOutputScrollPane;
    private javax.swing.JTextArea gameOutputTextArea;
    private javax.swing.JButton playGameButton;
    private javax.swing.JLabel playerCountLabel;
    private javax.swing.JTextField playerCountTextField;
    // End of variables declaration//GEN-END:variables

}

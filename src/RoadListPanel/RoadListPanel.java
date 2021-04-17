package RoadListPanel;

import ContextApplication.ContextApplication;
import CardsForScrollers.RoadCard;
import Frames.AddRoadForm;
import Frames.DateFormFrame;
import Road.Road;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**Panel della lista delle strade
 * Contiene un insieme di panel scrollabile con le informazioni generali di una strada
 */
public class RoadListPanel extends JPanel {

    private JPanel test;
    private JScrollPane scrollPane;
    private ArrayList<RoadCard> cards;
    private static RoadListPanel instance = null;

    public static RoadListPanel getInstance() {
        if(instance == null)
            instance = new RoadListPanel();
        return instance;
    }

    //Funzione utile per aggiornare il panel
    public static RoadListPanel reload(){
        instance = new RoadListPanel();
        return instance;
    }

    private RoadListPanel(){
        test = new JPanel();

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{171, 37, 400, 0};
        gridBagLayout.rowHeights = new int[]{300, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        test.setLayout(gridBagLayout);


        JButton statBtn = new JButton("Visualizza Statistiche");
        statBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DateFormFrame.reload();
            }
        });
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        gbc_btnNewButton.gridx = 1;
        gbc_btnNewButton.gridy = 1;
        test.add(statBtn,gbc_btnNewButton);

        //Costruisce uno ScrollPane con le informazioni di ogni strada
        ScrollPane();

        /**Listener del button per la comparsa del form per l'aggiunta di una strada
         */
        JButton btnAggiungiStrada = new JButton("Aggiungi Strada");
        btnAggiungiStrada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddRoadForm.getFrame();
            }
        });

        GridBagConstraints gbc_btnAggiungiStrada = new GridBagConstraints();
        gbc_btnAggiungiStrada.gridx = 2;
        gbc_btnAggiungiStrada.gridy = 1;

        test.add(btnAggiungiStrada, gbc_btnAggiungiStrada);
        test.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        statBtn.setVisible(true);
        test.revalidate();
        test.repaint();
    }

    /**Funzione utile per la costruzione del ScrollPane delle strade
     *
     */
    public void ScrollPane(){

        //Pannello che conterra' i pannelli di ogni strada
        JPanel contentCards = new JPanel();
        contentCards.setLayout(new BoxLayout(contentCards, BoxLayout.PAGE_AXIS));

        ArrayList<Road> systemRoads = ContextApplication.getInstance().getRoads();
        cards = new ArrayList<RoadCard>();
        if(systemRoads != null) {
            for (Road r : systemRoads) {
                //Creo il Panel RoadCard che contiene le informazioni della strada
                RoadCard rCard = new RoadCard(r);

                //Salvo il Panel appena creato in un ArrayList locale
                cards.add(rCard);

                //Aggiungo il Panel al Panel che conterrà tutte le strade
                contentCards.add(rCard.getCardPanel());

                //Creo un area di spazio tra i Panels
                contentCards.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        //Rendo il Panel contenitore scrollabile
        scrollPane = new JScrollPane(contentCards);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridwidth = 3;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 0;

        test.add(scrollPane, gbc_scrollPane);
        contentCards.setVisible(true);
        scrollPane.setVisible(true);
    }

    public JPanel getTest() {
        return test;
    }

    public ArrayList<RoadCard> getCards(){
        return cards;
    }
}

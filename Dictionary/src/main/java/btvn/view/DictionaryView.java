
package btvn.view;

import btvn.controller.ButtonListener;
import btvn.model.Dictionary;
import btvn.model.Word;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tritranmn2
 */
public class DictionaryView extends JPanel {
    public JPanel contentPane;
    public DictionaryPane dictionaryPane;
    public FavoritePane favoritePane;
    public FrequencyPane frequencyPane;
    public ButtonListener btnListener;
    public JFrame mainFrame;
    public Dictionary modelVi;
    public Dictionary modelEn;
    public DictionaryView(JFrame mainFrame,Dictionary modelVi,Dictionary modelEn) {
        this.mainFrame = mainFrame;
        this.modelVi = modelVi;
        this.modelEn = modelEn;
    }

    public void setContentPane(JPanel contentPane) {
        this.contentPane = contentPane;
    }

    public void setDictionaryPane(DictionaryPane dictionaryPane) {
        this.dictionaryPane = dictionaryPane;
    }

    public void setFavoritePane(FavoritePane favoritePane) {
        this.favoritePane = favoritePane;
    }

    public void setFrequencyPane(FrequencyPane frequencyPane) {
        this.frequencyPane = frequencyPane;
    }
    
    

    public static void changeFont(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                changeFont(child, font);
            }
        }
    }
    public void setContentPane(String pane){
        if(pane.equals("DictionaryPane")){
            this.contentPane = this.dictionaryPane;
        }
        else if(pane.equals("FavoritePane")){
            this.contentPane = this.favoritePane;
        }
        else if(pane.equals("FrequencyPane")){
            this.contentPane = this.frequencyPane;
        } 
        this.mainFrame.setContentPane(this.contentPane);
        this.mainFrame.pack();
        this.mainFrame.setVisible(true);
    }

    public static void createAndShowUI(){
        JFrame mainFrame = new JFrame("Dictionary Small");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dictionary<Word> modelVi = new Dictionary<>();
        Dictionary<Word> modelEn = new Dictionary<>();
        modelVi.loadDictionary(".\\data\\Viet_Anh.xml");
        modelEn.loadDictionary(".\\data\\Anh_Viet.xml");
        modelVi.loadHistory("history_vi.bin");
        modelEn.loadHistory("history_en.bin");
        DictionaryView mainView = new DictionaryView(mainFrame,modelVi,modelEn);
        
        Dimension preferredSize = new Dimension(650,400);
        DictionaryPane dictionaryPane = new DictionaryPane(mainView);
        dictionaryPane.setPreferredSize(preferredSize);

        FavoritePane favoritePane = new FavoritePane(mainView);
        favoritePane.setPreferredSize(preferredSize);
        FrequencyPane frequencyPane = new FrequencyPane(mainView);
        frequencyPane.setPreferredSize(preferredSize);

        mainView.setContentPane(dictionaryPane);
        mainView.setDictionaryPane(dictionaryPane);
        mainView.setFavoritePane(favoritePane);
        mainView.setFrequencyPane(frequencyPane);
        
        changeFont(dictionaryPane, new Font("Segoe UI", 0, 14));
        changeFont(favoritePane, new Font("Segoe UI", 0, 14));
        changeFont(frequencyPane, new Font("Segoe UI", 0, 14));
        
        
        mainFrame.add(mainView.contentPane);
        mainFrame.pack();
        mainFrame.setVisible(true);
        
    }

    
    public static void main(String[] args) {
//        System.out.println("Hello World!");
        createAndShowUI();  
    }
    
    public void updateTableFavorite() {
        String lang = (String) this.favoritePane.cbbLanguage.getSelectedItem();
        DefaultTableModel modeltable = this.favoritePane.modeltable;
        modeltable.setRowCount(0); // clear all rows
        ArrayList<String> favoriteList = null;
        if(lang.equals("Anh-Việt")){
            favoriteList = this.modelEn.favoriteList;
        }else{
            favoriteList = this.modelVi.favoriteList;
        }
        for (String w : favoriteList) {
            modeltable.addRow(new Object[]{w});
        }
        this.favoritePane.table.setModel(modeltable);
    }
    
    public void updateTableFrequency(String strStartDate,String strEndDate) {
        String lang = (String) this.frequencyPane.cbbLanguage.getSelectedItem();
        DefaultTableModel modeltable = this.frequencyPane.modeltable;
        modeltable.setRowCount(0); // clear all rows
        Map<String,Integer> frequencyList = null;
        if(lang.equals("Anh-Việt")){
            frequencyList = this.modelEn.listWordAccessBetweenTime(strStartDate,strEndDate);
        }else{
            frequencyList = this.modelVi.listWordAccessBetweenTime(strStartDate,strEndDate);
        }
        
        for (Map.Entry<String,Integer> entry : frequencyList.entrySet()) {
            modeltable.addRow(new Object[]{entry.getKey(),entry.getValue()});
        }
        this.frequencyPane.table.setModel(modeltable);
    }
    
}

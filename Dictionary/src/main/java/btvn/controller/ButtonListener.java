package btvn.controller;

import btvn.model.Pair;
import btvn.model.Word;
import btvn.view.DictionaryView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tritranmn2
 */
public class ButtonListener implements ActionListener {
    public DictionaryView view ;

    public ButtonListener(DictionaryView view) {
        this.view = view;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String cm = e.getActionCommand();
        if(cm.equals("Trở về")){
            this.view.setContentPane("DictionaryPane");
        }
        else if(cm.equals("Bảng yêu thích")){
            this.view.setContentPane("FavoritePane");


            String lang = (String) this.view.favoritePane.cbbLanguage.getSelectedItem();
            DefaultTableModel modeltable = this.view.favoritePane.modeltable;
            modeltable.setRowCount(0); // clear all rows
            ArrayList<String> favoriteList = null;
            if(lang.equals("Anh-Việt")){
                favoriteList = this.view.modelEn.favoriteList;
            }else{
                favoriteList = this.view.modelVi.favoriteList;
            }
            for (String w : favoriteList) {
                modeltable.addRow(new Object[]{w});
            }
            this.view.favoritePane.table.setModel(modeltable);
        }
        else if(cm.equals("Bảng tần suất")){
            this.view.setContentPane("FrequencyPane");
        }
        else if(cm.equals("Thêm") || cm.equals("Sửa")){
            String lang = (String) this.view.dictionaryPane.cbbLanguage.getSelectedItem();
            String word = this.view.dictionaryPane.tfInputSearch.getText();
            String meaning = this.view.dictionaryPane.taMeaning.getText();
            if(lang.equals("Anh-Việt")){
                this.view.modelEn.updateMeaning(word, meaning);
            }else{
                this.view.modelVi.updateMeaning(word, meaning);
            }
        }
        else if(cm.equals("Xoá")){
            String lang = (String) this.view.dictionaryPane.cbbLanguage.getSelectedItem();
            String word = this.view.dictionaryPane.tfInputSearch.getText();
            if(lang.equals("Anh-Việt")){
                this.view.modelEn.removeWord(word);
            }else{
                this.view.modelVi.removeWord(word);
            }
        }

        else if(cm.equals("Lưu")){
            String lang = (String) this.view.dictionaryPane.cbbLanguage.getSelectedItem();
            if(lang.equals("Anh-Việt")){
                this.view.modelEn.writeDictionaryToXml(".\\data\\Anh_Viet.xml");
            }else{
                this.view.modelVi.writeDictionaryToXml(".\\data\\Viet_Anh.xml");
            }
            this.view.modelEn.writeHistoryToFile("history_en.bin");
            this.view.modelVi.writeHistoryToFile("history_vi.bin");
        }
        else if(cm.equals("Yêu thích")){
            String lang = (String) this.view.dictionaryPane.cbbLanguage.getSelectedItem();
            String word = this.view.dictionaryPane.tfInputSearch.getText();
            if(lang.equals("Anh-Việt")){
               this.view.modelEn.markFavorite(word);
            }else{
               this.view.modelVi.markFavorite(word);
            }
        }
        else if(cm.equals("Tìm kiếm")){
//            String lang = (String) this.view.frequencyPane.cbbLanguage.getSelectedItem();
            String strStartDate = this.view.frequencyPane.tfBegin.getText();
            String strEndDate = this.view.frequencyPane.tfEnd.getText();
            this.view.updateTableFrequency(strStartDate,strEndDate);
        }
        else if(cm.equals("tfInputSearch")) {
            String word = this.view.dictionaryPane.tfInputSearch.getText();
            String lang = (String) this.view.dictionaryPane.cbbLanguage.getSelectedItem();
            String meaning = "";
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDateTime = now.format(formatter);
            if(lang.equals("Anh-Việt")){
                meaning = this.view.modelEn.getMeaning(word);
                this.view.dictionaryPane.taMeaning.setText(meaning);
                if(meaning.length()!=0){
                    this.view.modelEn.history.add(new Pair(word,formattedDateTime));
                }
                
            }else{
                meaning = this.view.modelVi.getMeaning(word);
                this.view.dictionaryPane.taMeaning.setText(meaning);
                if(meaning.length()!=0){
                    this.view.modelVi.history.add(new Pair(word,formattedDateTime));
                }
            }
            
        }
        else if(cm.equals("cbbLanguage")) {
            this.view.updateTableFavorite();
        }
        else if(cm.equals("cbbSort")) {
            String lang = (String) this.view.favoritePane.cbbLanguage.getSelectedItem();
            String AZ = (String) this.view.favoritePane.cbbSort.getSelectedItem();

            if(lang.equals("Anh-Việt")){
                if(AZ.equals("A-Z")){
                    this.view.modelEn.sortAZ();
                }else{
                    this.view.modelEn.sortZA();
                }
            }else{
                if(AZ.equals("A-Z")){
                    this.view.modelVi.sortAZ();
                }else{
                    this.view.modelVi.sortZA();
                }
            }
            this.view.updateTableFavorite();
        }
//        JOptionPane.showMessageDialog(null, "Bạn vừa nhấn:"+cm);

    }
    
}

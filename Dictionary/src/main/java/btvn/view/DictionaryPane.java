package btvn.view;

import btvn.controller.ButtonListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 *
 * @author tritranmn2
 */
public class DictionaryPane extends JPanel {
    
    public JTextField tfInputSearch;
    public JComboBox cbbLanguage;
    public JTextArea taMeaning;
    public JButton btnFavoriteTable, btnFrequencyTable, btnAdd, btnDelete, btnUpdate,btnSave, btnFavorite;
    private ActionListener btnListener;

    public DictionaryPane(DictionaryView view) {
        this.tfInputSearch = new JTextField("Enter...",20);
        this.tfInputSearch.setActionCommand("tfInputSearch");
        this.cbbLanguage = new JComboBox(new String[]{"Anh-Việt","Việt-Anh"});
        this.taMeaning = new JTextArea();
        this.btnFavoriteTable = new JButton("Bảng yêu thích");
        this.btnFrequencyTable = new JButton("Bảng tần suất");
        this.btnAdd = new JButton("Thêm");
        this.btnDelete = new JButton("Xoá");
        this.btnUpdate = new JButton("Sửa");
        this.btnSave = new JButton("Lưu");
        this.btnFavorite = new JButton("Yêu thích");
        
        this.btnListener = new ButtonListener(view);
        this.tfInputSearch.addActionListener(btnListener);
        this.btnFavoriteTable.addActionListener(btnListener);
        this.btnFrequencyTable.addActionListener(btnListener);
        this.btnAdd.addActionListener(btnListener);
        this.btnDelete.addActionListener(btnListener);
        this.btnUpdate.addActionListener(btnListener);
        this.btnSave.addActionListener(btnListener);
        this.btnFavorite.addActionListener(btnListener);
        
        JPanel inputPane =new JPanel(new FlowLayout());
        inputPane.add(tfInputSearch);
        inputPane.add(cbbLanguage);
        inputPane.add(btnFavorite);

        JPanel btnPane =new JPanel(new FlowLayout());
        btnPane.add(btnFavoriteTable);
        btnPane.add(btnFrequencyTable);
        btnPane.add(btnAdd);
        btnPane.add(btnDelete);
        btnPane.add(btnUpdate);
        btnPane.add(btnSave);
        
        this.setLayout(new BorderLayout());
        this.add(inputPane,BorderLayout.NORTH);
        this.add(taMeaning,BorderLayout.CENTER);
        this.add(btnPane,BorderLayout.SOUTH);
        
    }
    
    public ActionListener getButtonListener(){
        return this.btnListener;
    }
    
    
}

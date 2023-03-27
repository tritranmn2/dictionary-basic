package btvn.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.Collator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Dictionary<T extends Word> extends HashMap<String, T> {
    public ArrayList<String> favoriteList;
    public ArrayList<Pair<String, String>> history;
    public Dictionary() {
        favoriteList= new ArrayList<String>();
        history = new ArrayList<Pair<String, String>>();
    }

    public void addWord(T word) {
        this.put(word.getWord(), word);
    }

    public void removeWord(String word) {
        this.remove(word);
    }

    public void updateMeaning(String word, String newMeaning) {
        T w = this.get(word);
        if (w != null) {
            w.setMeaning(newMeaning);
            this.put(word, w);
        }else{
            this.addWord((T) new Word(word,newMeaning));
        }
    }

    public String getMeaning(String word) {
        T w = this.get(word);
        if (w == null) {
            return null;
        } else {
            return w.getMeaning();
        }
    }

    public void markFavorite(String word) {
        T w = this.get(word);
        if (w != null) {
            w.setIsFavorite(true);
            if(!this.favoriteList.contains(w.getWord()))
                this.favoriteList.add(w.getWord());
        }
    }

    public boolean isFavorite(String word) {
        T w = this.get(word);
        if (w == null) {
            return false;
        } else {
            return w.getIsFavorite();
        }
    }
    
    
    public void loadDictionary(String filePath) {
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new FileInputStream(inputFile));
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("record");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String word = element.getElementsByTagName("word").item(0).getTextContent();
                    String meaning = element.getElementsByTagName("meaning").item(0).getTextContent();
                    T w = this.get(word);
                    if (w == null) {
                        w = (T) new Word(word, meaning);
                        this.addWord(w);
                    } else {
                        w.setMeaning(meaning);
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
    
    public void writeDictionaryToXml( String fileName){
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("dictionary");
            document.appendChild(rootElement);

            for (Word word : this.values()) {
                Element recordElement = document.createElement("record");
                rootElement.appendChild(recordElement);

                Element wordElement = document.createElement("word");
                wordElement.appendChild(document.createTextNode(word.getWord()));
                recordElement.appendChild(wordElement);

                Element meaningElement = document.createElement("meaning");
                meaningElement.appendChild(document.createTextNode(word.getMeaning()));
                recordElement.appendChild(meaningElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(fileName));
            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
  }



    public void sortAZ() {
        Collator collator = Collator.getInstance(new Locale("vi", "VN"));
        Collections.sort(this.favoriteList, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return collator.compare(s1, s2);
            }
        });

    }

    public void sortZA() {
        Collator collator = Collator.getInstance(new Locale("vi", "VN"));
        Collections.sort(this.favoriteList, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return collator.compare(s2, s1);
            }
        });
    }

    public void writeHistoryToFile(String filePath){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
            for (Pair<String,String> p : this.history) {
                oos.writeObject(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadHistory(String filePath) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
            Pair p;
            while (((p = (Pair<String,String>) ois.readObject()) != null) ) {
                this.history.add(p);
            }
            ois.close();
        } catch (Exception e) {
            System.out.println("read done");
        }

    }
    
    public Map<String, Integer> listWordAccessBetweenTime(String strStartDate, String strEndDate ){
        ArrayList<String> listWords = new ArrayList<String>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate startDate = LocalDate.parse(strStartDate, formatter);
        LocalDate endDate = LocalDate.parse(strEndDate, formatter);
        for(Pair p : this.history){
            String strDate = (String) p.getValue();
            LocalDate accessDate = LocalDate.parse(strDate, formatter);
            if(accessDate.isAfter(startDate.minusDays(1)) && accessDate.isBefore(endDate.plusDays(1))){
                listWords.add((String) p.getKey());
            }
        }
        Map<String, Integer> wordCount = new HashMap<>();
        for (String word : listWords) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }
        return wordCount;
    }

    

    
}




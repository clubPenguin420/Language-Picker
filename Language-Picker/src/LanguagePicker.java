import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.SAXException;

/**
 * The main class
 * @author Shrideep Gaddad
 * @version 1.0.0
 */

class LanguagePicker{
    private String field;
    private String optimalLanguage;
    private ArrayList<String> questionsList = new ArrayList<>();
    private ArrayList<ArrayList<String>> answersList = new ArrayList<>();
    private ArrayList<ArrayList<String>> resultsList = new ArrayList<>();
    private int questionNumber = 1;


    /**
     *
     * It is a constructor.
     *
     * @param c  what field did the user pick
     */
    public LanguagePicker(int c) {

        String[] choices = {"Web", "Desktop", "Games", "Robotics+Electronics"};
        field = choices[c - 1];
        run();
    }



    /**
     *
     * This function gets the questions and saves them to memory(arrays)
     *
     * @param chunks  a chunk is the question and answers and results
     *        
     */
    public void setQuestionsAndAnswers(NodeList chunks){        //The function that took the most time
        for (int i = 0; i < chunks.getLength(); i++) {

            Node chunk = chunks.item(i);
            if(chunk.getNodeType() == Node.ELEMENT_NODE){
                Element qANDaElement = (Element) chunk;
                NodeList questions = qANDaElement.getElementsByTagName("q").item(0).getChildNodes();
                NodeList answers = qANDaElement.getElementsByTagName("ans").item(0).getChildNodes();
                NodeList results = qANDaElement.getElementsByTagName("result").item(0).getChildNodes();
                ArrayList<String> answersTemp = new ArrayList<String>();
                ArrayList<String> resultsTemp = new ArrayList<String>();

                for(int j = 0; j < answers.getLength(); j++){
                    Node answer = answers.item(j);
                    Node result = results.item(j);
                    NodeList a = answer.getChildNodes();
                    NodeList r = result.getChildNodes();

                    for(int k = 0; k < a.getLength(); k++){
                        if(a.item(k).getNodeType() == Node.TEXT_NODE && r.item(k).getNodeType() == Node.TEXT_NODE){
                            answersTemp.add(a.item(k).getNodeValue());
                            resultsTemp.add(r.item(k).getNodeValue());
                        }
                    }
                }

                Node q = questions.item(0);
                questionsList.add(q.getNodeValue());
                if(answersTemp.size() != 0) {
                    answersList.add(answersTemp);
                }
                if(resultsTemp.size() != 0) {
                    resultsList.add(resultsTemp);
                }
            }
        }
    }



    /**
     *
     * This asks the questions and decides the language.
     *
     * @param input  Scanner class
     */
    public void askAndDecide(Scanner input) {                  //The secret neural engine that decides your future :)
        int selection;
        while(optimalLanguage == null){

            System.out.println( questionsList.get( questionNumber - 1 ) );
            for(int j = 0; j < answersList.get( questionNumber - 1 ).size(); j++) {
                System.out.println("\t " + answersList.get( questionNumber - 1 ).get(j));
            }
            while(true) {
                System.out.println();
                selection = input.nextInt();
                if(selection >= 1 && selection <= answersList.get( questionNumber - 1 ).size()){
                    break;
                }
                else {
                    System.out.println("NOT A VALID CHOICE >:(");
                }
            }
            String result = resultsList.get( questionNumber - 1 ).get( selection - 1);
            try{
                int res = Integer.parseInt(result);
                questionNumber = res;
            }
            catch(NumberFormatException e){
                optimalLanguage = result;
            }
        }
    }

    /**
     *
     * The main method
     *
     */
    public void run() {

        File file = new File(String.format("src/Questions/%s.xml", field));
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        Scanner input = new Scanner(System.in);

        try {
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);                  //Why is this built-in XML parser so convoluted, I hate it
            doc.getDocumentElement().normalize();
            NodeList chunks = doc.getElementsByTagName("question");

            setQuestionsAndAnswers(chunks);
            askAndDecide(input);

        } catch (SAXException | ParserConfigurationException | IOException error) {
            error.printStackTrace();
        }

    }


    /**
     *
     * to String method
     *
     */
    public String toString() {

        return "You chose to do " + field + " and " + optimalLanguage + " is the optimal first language";
    }
}

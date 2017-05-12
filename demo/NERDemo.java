import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations.AnswerAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.StringUtils;

import java.io.IOException;
import java.util.List;


/**
 * This is a demo of calling CRFClassifier programmatically.
 * <p>
 * Usage: <code> java -cp "stanford-ner.jar:." NERDemo [serializedClassifier [fileName]]</code>
 * <p>
 * If arguments aren't specified, they default to
 * ner-eng-ie.crf-3-all2006.ser.gz and some hardcoded sample text.
 * <p>
 * To use CRFClassifier from the command line:
 * java -mx400m edu.stanford.nlp.ie.crf.CRFClassifier -loadClassifier
 * [classifier] -textFile [file]
 * Or if the file is already tokenized and one word per line, perhaps in
 * a tab-separated value format with extra columns for part-of-speech tag,
 * etc., use the version below (note the 's' instead of the 'x'):
 * java -mx400m edu.stanford.nlp.ie.crf.CRFClassifier -loadClassifier
 * [classifier] -testFile [file]
 *
 * @author Jenny Finkel
 * @author Christopher Manning
 */


public class NERDemo {

    public static void main(String[] args) throws IOException {


//        NERDemo nerDemo = new NERDemo();
//        String fileName = "/nlp.txt";

//        try {
//            URI uri = nerDemo.getClass().getResource(fileName).toURI();
//            List<String> lines = Files.readAllLines(Paths.get(uri),
//                    Charset.defaultCharset());
//
//            for (String line : lines) {
////                System.out.println(line);
//                System.out.println("\u001B[33m" + "Data captured from text file - " + "\n" + line + "\u001B[0m");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String serializedClassifier = "classifiers/ner-eng-ie.crf-3-all2008.ser.gz";

        if (args.length > 0) {
            serializedClassifier = args[0];
        }

        AbstractSequenceClassifier classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);

      /* For either a file to annotate or for the hardcoded text example,
         this demo file shows two ways to process the output, for teaching
         purposes.  For the file, it shows both how to run NER on a String
         and how to run it on a whole file.  For the hard-coded String,
         it shows how to run it on a single sentence, and how to do this
         and produce an inline XML output format.
      */
        if (args.length > 1) {
            String fileContents = StringUtils.slurpFile(args[1]);
            List<List<CoreLabel>> out = classifier.classify(fileContents);
            for (List<CoreLabel> sentence : out) {
                for (CoreLabel word : sentence) {
                    System.out.print(word.word() + '/' + word.get(AnswerAnnotation.class) + ' ');
                }
                System.out.println();
            }
            out = classifier.classifyFile(args[1]);
            for (List<CoreLabel> sentence : out) {
                for (CoreLabel word : sentence) {
                    System.out.print(word.word() + '/' + word.get(AnswerAnnotation.class) + ' ');
                }
                System.out.println();
            }

        } else {
            String s1 = "This is a string containing name of a city in USA. City name is Dublin ";
            String s2 = "School name is Stanford University, which is located in USA.";
            String s3 = "He will become a hockey player and make USA proud";
            System.out.println(classifier.classifyToString(s1));
            System.out.println(classifier.classifyWithInlineXML(s2));
            System.out.println(classifier.classifyToString(s3));
            System.out.println(classifier.classifyToString(s2, "xml", true));
        }
    }

}

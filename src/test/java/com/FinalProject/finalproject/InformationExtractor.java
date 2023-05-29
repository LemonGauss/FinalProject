package com.FinalProject.finalproject;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;

import java.util.List;
import java.util.Properties;

public class InformationExtractor {
    public static void main(String[] args) {
        String text = "张三，30岁，硕士毕业于清华大学，有5年工作经验。";
        extractInformation(text);
    }

    public static void extractInformation(String text) {

        StanfordCoreNLP pipline = new StanfordCoreNLP(PropertiesUtils.asProperties(
                "annotators", "tokenize,ssplit,pos,lemma,ner,parse",
                "ssplit.isOneSentence", "true",
                "tokenize.language", "zh",
                "segment.model", "edu/stanford/nlp/models/segmenter/chinese/ctb.gz",
                "segment.sighanCorporaDict", "edu/stanford/nlp/models/segmenter/chinese",
                "segment.serDictionary", "edu/stanford/nlp/models/segmenter/chinese/dict-chris6.ser.gz",
                "segment.sighanPostProcessing", "true",
                "ner.model", "edu/stanford/nlp/models/ner/chinese.misc.distsim.crf.ser.gz",
                "ner.applyFineGrained", "false",
                "pos.model" , "edu/stanford/nlp/models/pos-tagger/chinese-distsim.tagger",
                "parse.model", "edu/stanford/nlp/models/srparser/chineseSR.ser.gz"

        ));




        Annotation annotation = new Annotation(text);
        pipline.annotate(annotation);

        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);
            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.EnhancedDependenciesAnnotation.class);
            if (dependencies == null) {
                System.out.println("Failed to get the semantic graph.");
            } else {
                System.out.println("Successfully got the semantic graph.");
            }

            for (CoreLabel token : tokens) {
                String word = token.word();
                String nerTag = token.ner();

                // Check if the entity is a person's name
                if (isPersonName(nerTag)) {
                    System.out.println("姓名: " + word);
                }

                // Check if the entity is an age
                if (isAge(nerTag, word)) {
                    System.out.println("年龄: " + word);
                }

                // Check if the entity is a degree
                if (isDegree(word)) {
                    System.out.println("最高学历: " + word);
                }

                // Check if the entity is a university
                if (isUniversity(nerTag)) {
                    System.out.println("毕业院校: " + word);
                }

                // Check if the entity is a work experience
                if (isWorkExperience(token, tokens, dependencies)) {
                    System.out.println("工作年限: " + word);
                }
            }
        }
    }

    private static boolean isPersonName(String nerTag) {
        return nerTag.equals("PERSON");
    }

    private static boolean isAge(String nerTag, String word) {
        return nerTag.equals("NUMBER") && word.matches("\\d+(岁|年)");
    }

    private static boolean isDegree(String word) {
        String lowercaseWord = word.toLowerCase();
        return lowercaseWord.contains("学士") || lowercaseWord.contains("硕士") || lowercaseWord.contains("博士");
    }

    private static boolean isUniversity(String nerTag) {
        return nerTag.equals("ORGANIZATION");
    }

    private static boolean isWorkExperience(CoreLabel token, List<CoreLabel> tokens, SemanticGraph dependencies) {
        String word = token.word();
        String nerTag = token.ner();

        // Check if the entity is labeled as a number and is likely a work experience
        if (nerTag.equals("NUMBER") && word.matches("\\d+")) {
            int index = token.index() - 1;
            int nextIndex = index + 1;

            if (nextIndex < tokens.size()) {
                CoreLabel nextToken = tokens.get(nextIndex);
                String nextWord = nextToken.word();
                String nextTag = nextToken.tag();
                SemanticGraphEdge edge = dependencies.getEdge(dependencies.getNodeByIndex(index + 1), dependencies.getNodeByIndex(nextIndex + 1));

                // Check if the next word is related to work experience (e.g., 年)
                if (nextWord.equals("年") && nextTag.equals("M")) {
                    return true;
                }

                // Check if the next word is a verb related to work experience (e.g., 工作)
                if (nextTag.equals("VV") && nextWord.equals("工作")) {
                    return true;
                }

                // Check if the next word is a noun related to work experience (e.g., 经验)
                if (nextTag.equals("NN") && nextWord.equals("经验")) {
                    return true;
                }

                // Check if the next word is a noun modifier related to work experience (e.g., 年限)
                if (nextTag.equals("NN") && edge != null && edge.getRelation().toString().equals("amod") && edge.getGovernor().index() == nextToken.index()) {
                    return true;
                }
            }
        }

        return false;
    }
}

package com.exemplojsf.watson;

import java.util.List;

import com.exemplojsf.util.Mensagens;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ElementTone;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.SentenceTone;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneCategory;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;

public class Tone {
    public ToneAnalysis getTone(String textAnalyze) {
        return getTone(textAnalyze, null);
    }

    public ToneAnalysis getTone(String textAnalyze, ToneOptions toneOptions) {
        return getToneAnalyzer().getTone(textAnalyze, toneOptions).execute();
    }

    public ElementTone getDocumentTone(String textAnalyze) {
        return getDocumentTone(textAnalyze, null);
    }

    public ElementTone getDocumentTone(String textAnalyze, ToneOptions toneOptions) {
        return getTone(textAnalyze, toneOptions).getDocumentTone();
    }

    public List<ToneCategory> getTones(String textAnalyze) {
        return getTones(textAnalyze, null);
    }

    public List<ToneCategory> getTones(String textAnalyze, ToneOptions toneOptions) {
        return getDocumentTone(textAnalyze, toneOptions).getTones();
    }

    public List<SentenceTone> getSentencesTone(String textAnalyze) {
        return getSentencesTone(textAnalyze, null);
    }

    public List<SentenceTone> getSentencesTone(String textAnalyze, ToneOptions toneOptions) {
        return getTone(textAnalyze, toneOptions).getSentencesTone();
    }

    public ToneAnalyzer getToneAnalyzer() {
        ToneAnalyzer service = new ToneAnalyzer(ToneAnalyzer.VERSION_DATE_2016_05_19);
        service.setUsernameAndPassword(Mensagens.getMensagem(Mensagens.API_TONE_ANALYZER_USERNAME), Mensagens.getMensagem(Mensagens.API_TONE_ANALYZER_PASSWORD));
        return service;
    }
}

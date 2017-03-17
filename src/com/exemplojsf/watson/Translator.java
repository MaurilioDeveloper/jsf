package com.exemplojsf.watson;

import java.util.List;

import com.exemplojsf.util.Mensagens;
import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Language;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Translation;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult;

public class Translator {
    public TranslationResult getTranslation(String textTranslation) {
        return getTranslation(textTranslation, Language.PORTUGUESE, Language.ENGLISH);
    }

    public TranslationResult getTranslation(String textTranslation, Language languageTo, Language languageFrom) {
        return getLanguageTranslator().translate(textTranslation, languageTo, languageFrom).execute();
    }

    public String getFirstTranslation(String textTranslation) {
        return getFirstTranslation(textTranslation, Language.PORTUGUESE, Language.ENGLISH);
    }

    public String getFirstTranslation(String textTranslation, Language languageTo, Language languageFrom) {
        return getTranslation(textTranslation, languageTo, languageFrom).getFirstTranslation();
    }

    public Integer getCharacterCount(String textTranslation) {
        return getCharacterCount(textTranslation, Language.PORTUGUESE, Language.ENGLISH);
    }

    public Integer getCharacterCount(String textTranslation, Language languageTo, Language languageFrom) {
        return getTranslation(textTranslation, languageTo, languageFrom).getCharacterCount();
    }

    public List<Translation> getTranslations(String textTranslation) {
        return getTranslations(textTranslation, Language.PORTUGUESE, Language.ENGLISH);
    }

    public List<Translation> getTranslations(String textTranslation, Language languageTo, Language languageFrom) {
        return getTranslation(textTranslation, languageTo, languageFrom).getTranslations();
    }

    public Integer getWordCount(String textTranslation) {
        return getWordCount(textTranslation, Language.PORTUGUESE, Language.ENGLISH);
    }

    public Integer getWordCount(String textTranslation, Language languageTo, Language languageFrom) {
        return getTranslation(textTranslation, languageTo, languageFrom).getWordCount();
    }

    public LanguageTranslator getLanguageTranslator() {
        LanguageTranslator service = new LanguageTranslator();
        service.setUsernameAndPassword(Mensagens.getMensagem(Mensagens.API_LANGUAGE_TRANSLATION_USERNAME), Mensagens.getMensagem(Mensagens.API_LANGUAGE_TRANSLATION_PASSWORD));
        return service;
    }
}

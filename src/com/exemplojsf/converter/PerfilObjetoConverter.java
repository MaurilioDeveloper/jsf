package com.exemplojsf.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;

import com.exemplojsf.bo.PerfilObjetoBO;
import com.exemplojsf.entity.PerfilObjeto;

@FacesConverter(forClass = PerfilObjeto.class, value = "perfilobjeto")
public class PerfilObjetoConverter implements Converter, Serializable {
    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(PerfilObjetoConverter.class);

    PerfilObjetoBO perfilObjetoBO = new PerfilObjetoBO();

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        try {
            if (value != null && !value.trim().isEmpty() && value.matches(".*\\d.*")) {
                final Integer cod = Integer.parseInt(value);
                return perfilObjetoBO.find(cod);
            }
        } catch (Exception e) {
            log.error("Erro no getAsObject", e);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        try {
            if (value == null)
                return null;
            PerfilObjeto perfilObjeto = (PerfilObjeto) value;
            return String.valueOf(perfilObjeto.getId());
        } catch (Exception e) {
            log.error("Erro no getAsString", e);
            return null;
        }
    }
}

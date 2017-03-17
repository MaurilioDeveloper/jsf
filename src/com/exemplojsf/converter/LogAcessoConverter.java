package com.exemplojsf.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;

import com.exemplojsf.bo.LogAcessoBO;
import com.exemplojsf.entity.Empresa;
import com.exemplojsf.entity.LogAcesso;

@FacesConverter(forClass = Empresa.class, value = "logacesso")
public class LogAcessoConverter implements Converter, Serializable {
    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(LogAcessoConverter.class);

    LogAcessoBO logAcessoBO = new LogAcessoBO();

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        try {
            if (value != null && !value.trim().isEmpty() && value.matches(".*\\d.*")) {
                final Integer cod = Integer.parseInt(value);
                return logAcessoBO.find(cod);
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
            LogAcesso logAcesso = (LogAcesso) value;
            return String.valueOf(logAcesso.getId());
        } catch (Exception e) {
            log.error("Erro no getAsString", e);
            return null;
        }
    }
}

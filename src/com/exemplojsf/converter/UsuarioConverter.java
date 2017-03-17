package com.exemplojsf.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;

import com.exemplojsf.bo.EmpresaBO;
import com.exemplojsf.entity.Empresa;
import com.exemplojsf.entity.Usuario;

@FacesConverter(forClass = Usuario.class, value = "usuario")
public class UsuarioConverter implements Converter, Serializable {
    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(UsuarioConverter.class);

    EmpresaBO empBO = new EmpresaBO();

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        try {
            if (value != null && !value.trim().isEmpty() && value.matches(".*\\d.*")) {
                final Integer cod = Integer.parseInt(value);
                return empBO.find(cod);
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
            Empresa empresa = (Empresa) value;
            return String.valueOf(empresa.getId());
        } catch (Exception e) {
            log.error("Erro no getAsString", e);
            return null;
        }
    }
}

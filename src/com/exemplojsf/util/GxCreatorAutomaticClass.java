package com.exemplojsf.util;

import java.io.File;
import java.io.FileOutputStream;

public class GxCreatorAutomaticClass {
    public static void main(String[] args) {
        String[] nomes = new String[] { "FormularioAlternativa","FormularioPergunta","FormularioResposta"};
        boolean entity = false;
        boolean bo = true;
        boolean mb = true;
        boolean converter = true;
        for (String nome : nomes) {
            generate(nome, entity, bo, mb, converter);
        }
    }

    public static void generate(String nome, boolean entity, boolean bo, boolean mb, boolean converter) {
        try {
            String path = "src/br/com/lume/isvendas/bo/";
            String pathEntity = "src/br/com/lume/isvendas/entity/";
            String pathMB = "src/br/com/lume/isvendas/managed/";
            String pathConverter = "src/br/com/lume/isvendas/converter/";
            FileOutputStream fio = null;
            StringBuilder sb = new StringBuilder();
            sb.append("package br.com.lume.isvendas.bo; ");
            sb.append(" ");
            sb.append("import java.util.HashMap; "); 
            sb.append("import java.util.List; "); 
            sb.append("import java.util.Map; "); 
            sb.append(" "); 
            sb.append("import org.apache.log4j.Logger; ");
            sb.append(" ");
            sb.append("import br.com.lume.isvendas.entity.xxx; ");
            sb.append("import br.com.lume.isvendas.exception.BancoDadosException; ");            
            sb.append(" ");
            sb.append("public class xxxBO extends BO<xxx> { ");
            sb.append("    private static final long serialVersionUID = 1L; ");
            sb.append(" ");
            sb.append("    private static Logger log = Logger.getLogger(xxxBO.class); ");
            sb.append(" ");
            sb.append("    public xxxBO() { ");
            sb.append("        super(); ");
            sb.append("        setClazz(xxx.class); ");
            sb.append("    } ");
            sb.append("     ");
            sb.append("    public List<xxx> listByEmpresa(Integer empIntCod) throws BancoDadosException{ "); 
            sb.append("        Map<String, Object> filtros = new HashMap<String, Object>(); "); 
            sb.append("        filtros.put(\"empresa.empIntCod\", empIntCod); "); 
            sb.append("        return super.listByFields(filtros); "); 
            sb.append("    } ");
            sb.append("} ");
            if (bo) {
                fio = new FileOutputStream(new File(path + nome + "BO.java"));
                fio.write(sb.toString().replaceAll("xxx", nome).getBytes());
                fio.close();
            }
            sb = new StringBuilder();
            sb.append("package br.com.lume.isvendas.entity; ");
            sb.append(" ");
            sb.append("import java.io.Serializable; ");
            sb.append(" ");
            sb.append("import javax.persistence.Column; ");
            sb.append("import javax.persistence.Entity; ");
            sb.append("import javax.persistence.GeneratedValue; ");
            sb.append("import javax.persistence.GenerationType; ");
            sb.append("import javax.persistence.Id; ");
            sb.append("import javax.persistence.Table; ");
            sb.append(" ");
            sb.append("import org.apache.log4j.Logger; ");
            sb.append(" ");
            sb.append("@Entity ");
            sb.append("@Table(name = \"xxx\") ");
            sb.append("public class xxx implements Serializable { ");
            sb.append("    private static final long serialVersionUID = 1L; ");
            sb.append(" ");
            sb.append(" ");
            sb.append("    private static Logger log = Logger.getLogger(xxx.class); ");
            sb.append(" ");
            sb.append("    @Id ");
            sb.append("    @GeneratedValue(strategy = GenerationType.IDENTITY) ");
            sb.append("    @Column(name = \"ID\") ");
            sb.append("    private Long id; ");
            sb.append(" ");
            sb.append("    public Long getId() { ");
            sb.append("        return id; ");
            sb.append("    } ");
            sb.append(" ");
            sb.append("    public void setId(Long id) { ");
            sb.append("        this.id = id; ");
            sb.append("    } ");
            sb.append("} ");
            if (entity) {
                fio = new FileOutputStream(new File(pathEntity + nome + ".java"));
                fio.write(sb.toString().replaceAll("xxx", nome).getBytes());
                fio.close();
            }
            sb = new StringBuilder();
            sb.append("package br.com.lume.isvendas.managed; ");
            sb.append(" ");
            sb.append("import javax.faces.bean.ManagedBean; ");
            sb.append("import javax.faces.bean.ViewScoped; ");
            sb.append(" ");
            sb.append("import br.com.lume.isvendas.bo.xxxBO; ");
            sb.append("import br.com.lume.isvendas.entity.xxx; ");
            sb.append(" ");
            sb.append("import org.apache.log4j.Logger; ");
            sb.append("import br.com.lume.isvendas.util.Mensagens; ");
            sb.append(" ");
            sb.append("@ManagedBean ");
            sb.append("@ViewScoped ");
            sb.append("public class xxxMB extends LumeManagedBean<xxx> { ");
            sb.append("    private static final long serialVersionUID = 1L; ");
            sb.append(" ");
            sb.append(" ");
            sb.append("    private static Logger log = Logger.getLogger(xxxMB.class); ");
            sb.append(" ");
            sb.append("    public xxxMB() { ");
            sb.append("        setClazz(xxx.class); ");
            sb.append("        setBo(new xxxBO()); ");            
            sb.append("    } ");
            sb.append("} ");
            if (mb) {
                fio = new FileOutputStream(new File(pathMB + nome + "MB.java"));
                fio.write(sb.toString().replaceAll("xxx", nome).getBytes());
                fio.close();
            }
            sb = new StringBuilder();
            sb.append("package br.com.lume.isvendas.converter; ");
            sb.append(" ");
            sb.append("import java.io.Serializable; ");
            sb.append(" ");
            sb.append("import javax.faces.component.UIComponent; ");
            sb.append("import javax.faces.context.FacesContext; ");
            sb.append("import javax.faces.convert.Converter; ");
            sb.append("import javax.faces.convert.FacesConverter; ");
            sb.append(" ");
            sb.append("import org.apache.log4j.Logger; ");
            sb.append(" ");
            sb.append("import br.com.lume.isvendas.bo.xxxBO; ");
            sb.append("import br.com.lume.isvendas.entity.xxx; ");
            sb.append(" ");
            sb.append("@FacesConverter(forClass = xxx.class, value = \"zzz\") ");
            sb.append("public class xxxConverter implements Converter, Serializable { ");
            sb.append("    private static final long serialVersionUID = 1L; ");
            sb.append(" ");
            sb.append("    private Logger log = Logger.getLogger(xxxConverter.class); ");
            sb.append(" ");
            sb.append("    xxxBO zzzBO = new xxxBO(); ");
            sb.append(" ");
            sb.append("    @Override ");
            sb.append("    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) { ");
            sb.append("        try { ");
            sb.append("            if (value != null && !value.trim().isEmpty() && value.matches(\".*\\\\d.*\")) { ");
            sb.append("                final Integer cod = Integer.parseInt(value); ");
            sb.append("                return zzzBO.find(cod); ");
            sb.append("            } ");
            sb.append("        } catch (Exception e) { ");
            sb.append("            log.error(\"Erro no getAsObject\", e); ");
            sb.append("        } ");
            sb.append("        return null; ");
            sb.append("    } ");
            sb.append(" ");
            sb.append("    @Override ");
            sb.append("    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) { ");
            sb.append("        try { ");
            sb.append("            if (value == null) ");
            sb.append("                return null; ");
            sb.append("            xxx zzz = (xxx) value; ");
            sb.append("            return String.valueOf(zzz.getId()); ");
            sb.append("        } catch (Exception e) { ");
            sb.append("            log.error(\"Erro no getAsString\", e); ");
            sb.append("            return null; ");
            sb.append("        } ");
            sb.append("    } ");
            sb.append("} ");
            if (converter) {
                fio = new FileOutputStream(new File(pathConverter + nome + "Converter.java"));
                String aux = sb.toString().replaceAll("xxx", nome);
                aux = aux.replaceAll("zzz", nome.toLowerCase());
                fio.write(aux.getBytes());
                fio.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

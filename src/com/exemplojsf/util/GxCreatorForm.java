package com.exemplojsf.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;

public class GxCreatorForm {
    public static final String TEMPLATE = "G:\\Faruk\\workspace_svn\\intelligentsales\\WebContent\\pages\\ultima\\templateGenerator.xhtml";

    public static final String OUTPUT = "G:\\Faruk\\workspace_svn\\intelligentsales\\WebContent\\pages\\ultima\\formularioresposta.xhtml";

    public static void main(String[] args) {
        try {
            String mb = "formularioRespostaMB";
            String entidade = "Avaliação";
            String id = "vfrIntCod";
            String label = "Código do avaliado,Data da avaliação ";
            // Copy SQL IN no Squirrel
            String nome = "'VFR_STR_CODAVALIADO','VFR_DTM_RESPOSTA'";           
            String tamanho = "'30','1'";
            String form = GxCreatorForm.criarFormulario(mb, nome, tamanho, label);
            String colunas = GxCreatorForm.criarColunas(nome, label);
            String template = GxCreatorForm.getStringTemplate();
            // System.out.println(template);
            template = template.replaceAll("#FORM", form);
            template = template.replaceAll("#COLUNAS", colunas);
            template = template.replaceAll("#MB", mb);
            template = template.replaceAll("#ENTIDADE", entidade);
            template = template.replaceAll("#ID", id);
            GxCreatorForm.salvarArquivo(template);
            System.out.println(template);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void salvarArquivo(String conteudo) throws Exception {
        
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUT), "UTF-8"));
        out.write(conteudo.toCharArray());
        out.close();
    }

    public static String getStringTemplate() throws Exception {
        return new String(Files.readAllBytes(Paths.get(TEMPLATE)));
    }

    public static String criarFormulario(String mb, String nome, String tamanho, String label) {
        String[] nomes = nome.split(",");
        String[] tamanhos = tamanho.split(",");
        String[] labels = label.split(",");
        String out = "";
        for (int i = 0; i < nomes.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("            <h:panelGroup styleClass=\"md-inputfield\"> \n");
            sb.append("                 <p:inputText id=\"xxx\" value=\"#{" + mb + ".entity.xxx}\" required=\"true\" maxlength=\"yyy\" /> \n");
            sb.append("                 <p:outputLabel for=\"xxx\" value=\"zzz\" /> \n");
            sb.append("                 <p:message styleClass=\"msg\" for=\"xxx\" /> \n");
            sb.append("             </h:panelGroup> \n");
            String n = nomes[i];
            n = n.replaceAll("'", "");
            n = n.toLowerCase();
            String[] enes = n.split("_");
            n = enes[0];
            for (int j = 1; j < enes.length; j++) {
                n += StringUtils.capitalize(enes[j]);
            }
            out += sb.toString().replaceAll("xxx", n).replaceAll("yyy", tamanhos[i].replaceAll("'", "")).replaceAll("zzz", labels[i].trim());
        }
        // System.out.println(out);
        return out;
    }

    public static String criarColunas(String nome, String label) {
        String[] nomes = nome.split(",");
        String[] labels = label.split(",");
        String out = "";
        for (int i = 0; i < nomes.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(
                    "                     <p:column filterBy=\"#{objeto.xxx}\" filterMatchMode=\"contains\" headerText=\"yyy\" sortBy=\"#{objeto.xxx}\"> \n");
            sb.append("                         <h:outputText value=\"#{objeto.xxx}\" /> \n");
            sb.append("                     </p:column> \n");
            String n = nomes[i];
            n = n.replaceAll("'", "");
            n = n.toLowerCase();
            String[] enes = n.split("_");
            n = enes[0];
            for (int j = 1; j < enes.length; j++) {
                n += StringUtils.capitalize(enes[j]);
            }
            out += sb.toString().replaceAll("xxx", n).replaceAll("yyy", labels[i].trim());;
        }
        // System.out.println(out);
        return out;
    }
}

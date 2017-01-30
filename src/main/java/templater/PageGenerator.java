package main.java.templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

/**
 * Created by admin on 25.01.2017.
 */
public class PageGenerator {
    private static final String HTML_DIR = "main\\templates";


    private static PageGenerator pageGenerator;
    private final Configuration cfg;

    public static PageGenerator instance(){
        if (pageGenerator==null)
            pageGenerator= new PageGenerator();
        return pageGenerator;
    }

    public String getPage(String filename, Map<String,Object> data) throws UnsupportedEncodingException {
        Writer stream = new StringWriter();
        try {
            cfg.setClassForTemplateLoading(this.getClass(), "/");
            Template template = cfg.getTemplate(HTML_DIR + File.separator+filename,"UTF-8");
            template.process(data,stream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        return stream.toString();
    }
    private PageGenerator(){cfg = new Configuration();}
}

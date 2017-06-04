package main;

import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.staticFileLocation;
import static spark.Spark.staticFiles;


/**
 * Created by darle on 6/3/2017.
 */
public class main {

    public static void main(String[] args) {

        staticFiles.location("/");

        Configuration configuration=new Configuration();
        configuration.setClassForTemplateLoading(main.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);


       get("/Home", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "index.ftl");
        }, freeMarkerEngine);

    }
}

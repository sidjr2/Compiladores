package edu.ufsj.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    public static void main(String[] args) throws IOException {
        if(args.length !=1){
            System.err.println("Usage: java edu.ufsj.tool.GenerateAst <output dir>");
            System.exit(64);
        }
        String outputDir = args[0];
        defineAst(outputDir, "Expr",Arrays.asList(
            "Binary : Expr left, Token Operator, Expr right",
            "Grouping: Expr expression",
            "Unary: Token operator, Expr rigth",
            "Literal: Object value"
        ));
        
    }

    /**
     * @param outputDir
     * @param baseName
     * @param types
     * @throws IOException
     */
    private static void defineAst(
            String outputDir, String baseName, List<String>types)
            throws IOException {
                
        String path = outputDir+"/"+baseName+".java";
        try (
            PrintWriter writer = new PrintWriter(path, "UTF-8")) {
            writer.println("package edu.ufsj.lox;");
            writer.println();
            writer.println("import java.util.List;");
            writer.println();
            writer.println("abstract class "+ baseName +" {");

            defineVisitor(writer, baseName, types);

            for(String type:types){
                String className = type.split(":")[0].trim();
                String fields = type.split(":")[1].trim();
                defineType(writer, baseName, className, fields);
            }

            //method accept()
            writer.println();
            writer.println("\tabstract <R> R accept"+
                            "(Visitor<R> visitor);");
            
            writer.println("}");


            writer.println();
            
            writer.close();
        }
    }

    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {
        
        writer.println("\tinterface Visitor<R> {");

        for(String type:types){
            
            String typeName = type.split(":")[0].trim();
            
            writer.println("\t\tR visit"+ typeName + baseName + "(" +
                            typeName + " " + baseName.toLowerCase()+ ");" );
        }

        writer.println("\t}");
    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fieldsList) {
        
        writer.println("\n\tstatic class "+ className +" extends "+ baseName +" {");
        
        //construtor
        writer.println("\t\t"+ className +" ( "+ fieldsList + " ) {");
        
        //Armazena paramentros
        String[] fields = fieldsList.split(",");
        for(String field:fields){
            String name = field.split("")[1];
            writer.println("\t\t\tthis."+ name +" = "+ name +";");
        }
       
        writer.println("\t\t}");

        //Visitor
        writer.println("\t\t@Override");
        writer.println("\t\t<R> R accept(Visitor<R> visitor) {");
        writer.println("\t\t\treturn visitor.visit"+ className + baseName + "(this);");
        writer.println("\t\t}");
       
       //Fields
        writer.println();

        for(String field:fields){
            writer.println("\t\tfinal"+ field+ ";");
        }
        writer.println("\t}");
    }
      
}
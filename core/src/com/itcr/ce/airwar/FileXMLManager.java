package com.itcr.ce.airwar;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Arturo on 27/4/2017.
 */

public class FileXMLManager {
    private static final String routeProject = System.getProperty("user.dir"); //Ruta del proyecto
    private static final XStream xstream = new XStream(new StaxDriver());

    /**
     * Metodo que lee un archivo y retorna el objeto almacenado en el
     * @param fileName Nombre del archivo
     * @return El objeto contenido en el archivo
     */
    public static Object getContent(String fileName){
        String objectXML = null; //Contenido xml del archivo
        try {
            objectXML = FileXMLManager.readFile(fileName); //Se obtiene el contenido del archivo
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object object = xstream.fromXML(objectXML); //Se transforma el xml a un objeto de java
        return object;
    }

    /**
     * Metodo que escribe un objeto en un archivo
     * @param object Objeto que se desea guardar
     * @param fileName Nombre del archivo
     */
    public static void writeContent(Object object, String fileName){
        xstream.autodetectAnnotations(true);
        String objectXML = xstream.toXML(object); //Se transforma el objeto java en un xml
        try {
            FileXMLManager.writeFile(fileName, objectXML); //Se guarda el xml en el archivo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Metodo que se encarga de comprobar la existencia del fichero indicado
     * @param fileName Nombre del fichero
     * @return True si existe, false en caso contrario
     * @throws IOException Excepcion
     */
    public static boolean checkExistence(String fileName) throws IOException {
        File archive = new File(routeProject + "/" + fileName); //Se abre el archivo

        if (archive.exists()) { //Se comprueba si el archivo existe
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo que lee el contenido de un fichero
     * @param fileName Nombre del fichero
     * @return El contenido del fichero
     * @throws IOException Excepcion
     */
    private static String readFile(String fileName) throws IOException {
        String text = "";

        FileReader file = new FileReader(routeProject + "/" + fileName);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            text += sc.nextLine();
        }
        sc.close();

        return text;
    }

    /**
     * Metodo que elimina el contenido de un fichero
     * @param archive  Archivo que se desea limpiar
     * @throws IOException Excepcion
     */
    private static void deleteContent(File archive) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter(archive));
        bw.write("");
        bw.close();
    }

    /**
     * Metodo que escribe en un fichero
     * @param fileName Nombre del fichero
     * @throws IOException Excepcion
     */
    private static void writeFile(String fileName, String text) throws IOException{
        File archive = new File(routeProject + "/" + fileName); //Se carga el archivo
        deleteContent(archive); //Se elimina el contenido del archivo
        FileWriter writer = new FileWriter(archive, true); //Se crea el objeto que escribe en el archivo
        writer.write(text); //Se escribe el texto en el archivo
        writer.close(); //Se cierra el escritor
    }
}

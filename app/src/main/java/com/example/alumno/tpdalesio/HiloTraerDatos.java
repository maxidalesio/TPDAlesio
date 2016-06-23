package com.example.alumno.tpdalesio;

import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class HiloTraerDatos implements Runnable {
    String uri;
    Handler colaMensajes;
    Boolean imagen;
    int posicion;

    public HiloTraerDatos(String s, Handler h, Boolean i){
        uri= s;
        colaMensajes= h;
        imagen= i;
    }

    public HiloTraerDatos(String s, Handler h, Boolean i, int p){
        this(s, h, i);
        posicion= p;
    }

    @Override
    public void run() {
        HttpManager conexion = new HttpManager(uri);
        Message mensaje= new Message();

        if (!imagen) {
            ArrayList<Noticia> lista = new ArrayList<Noticia>();

            try {
                String strXml = conexion.getStrDataByGET();

                XmlPullParser parser = Xml.newPullParser();
                Noticia n = null;
                try {
                    parser.setInput(new StringReader(strXml));
                    int event = parser.getEventType();
                    n = new Noticia();
                    while (event != XmlPullParser.END_DOCUMENT) {
                        switch (event) {
                            case XmlPullParser.START_TAG:
                                String tag = parser.getName();
                                switch (tag) {
                                    case "item":
                                        n = new Noticia();
                                        break;
                                    case "pubDate":
                                        n.setFecha(parser.nextText().toString());
                                        break;
                                    case "title":
                                        n.setTitulo(parser.nextText().toString());
                                        break;
                                    case "description":
                                        Spanned texto = Html.fromHtml(parser.nextText().toString());
                                        n.setDescripcion(texto.toString());
                                        break;
                                    case "enclosure":
                                        n.setImgPath(parser.getAttributeValue(null, "url"));
                                        break;
                                    case "link":
                                        n.setLink(parser.nextText().toString());
                                        break;
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                tag = parser.getName();
                                if (tag.equals("item")) {
                                    lista.add(n);
                                }
                                break;
                        }
                        event = parser.next();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mensaje.obj = lista;
                mensaje.arg1 = 1;
                colaMensajes.sendMessage(mensaje);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                byte[] foto= conexion.getBytesDataByGET();
                mensaje.obj = foto;
                mensaje.arg1 = 2;
                mensaje.arg2 = posicion;
                colaMensajes.sendMessage(mensaje);
            }  catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
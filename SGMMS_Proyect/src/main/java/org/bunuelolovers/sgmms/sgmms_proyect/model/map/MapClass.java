package org.bunuelolovers.sgmms.sgmms_proyect.model.map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import org.bunuelolovers.sgmms.sgmms_proyect.graph.Graph;
import org.bunuelolovers.sgmms.sgmms_proyect.structures.BinarySearchTree;


public class MapClass {


    private Image image;
    private GraphicsContext gc;

    private List<Block> blocks;
    private List<Zone> zones;
    private BinarySearchTree<Route> routes;
    private Graph corners;


    public MapClass(Image image, Canvas canvas) {
        corners = new Graph();
        this.image = image;

        blocks = new ArrayList<>();
        zones = new ArrayList<>();
        routes = new BinarySearchTree<>();

        gc = canvas.getGraphicsContext2D();

        initCorners();
        initBlocks();
        initZones();
        initRoutes();

    }

    public void initZones() {

        //primera fila
        zones.add(new Zone(ZoneType.PARKING, "Parking New York", 40, 74));
        zones.add(new Zone(ZoneType.RESIDENTIAL, "Acuarela 2", 290, 74));
        zones.add(new Zone(ZoneType.RESIDENTIAL, "Acuarela 1", 500, 74));
        zones.add(new Zone(ZoneType.COMMERCIAL, "Bocha Plaza", 706, 74));
        zones.add(new Zone(ZoneType.COMMERCIAL, "Unicentro", 942, 74));
        zones.add(new Zone(ZoneType.COMMERCIAL, "McDonalds", 1261, 74));

        //segunda fila
        zones.add(new Zone(ZoneType.COMMERCIAL, "Crepes and Waffles", 40, 380));
        zones.add(new Zone(ZoneType.PARK, "Central Park", 290, 380));
        zones.add(new Zone(ZoneType.RESIDENTIAL, "Acuarela 3", 500, 380));
        zones.add(new Zone(ZoneType.COMMERCIAL, "Plaza de las Americas", 706, 380));
        zones.add(new Zone(ZoneType.COMMERCIAL, "Plaza de la 93", 942, 380));
        zones.add(new Zone(ZoneType.COMMERCIAL, "Alcaldia", 1261, 380));

        //tercera fila
        zones.add(new Zone(ZoneType.COMMERCIAL, "Plaza de la 85", 40, 660));
        zones.add(new Zone(ZoneType.RESIDENTIAL, "Ventura 1", 290, 660));
        zones.add(new Zone(ZoneType.PARK, "Disney park", 500, 660));
        zones.add(new Zone(ZoneType.POLICE_STATION, "Policia", 706, 660));
        zones.add(new Zone(ZoneType.COMMERCIAL, "Embajada Alemana", 942, 660));
        zones.add(new Zone(ZoneType.RESIDENTIAL, "Residencia Militar", 1261, 660));

        //cuarta fila
        zones.add(new Zone(ZoneType.COMMERCIAL, "Plaza de la 80", 40, 900));
        zones.add(new Zone(ZoneType.RESIDENTIAL, "Verde Brisa 2", 290, 900));
        zones.add(new Zone(ZoneType.RESIDENTIAL, "Verde Brisa 1", 500, 900));
        zones.add(new Zone(ZoneType.PARK, "Parque del perro", 706, 900));
        zones.add(new Zone(ZoneType.HOSPITAL, "Palma Real", 942, 900));
        zones.add(new Zone(ZoneType.COMMERCIAL, "Plaza de la 68", 1261, 900));

        //quinta fila
        zones.add(new Zone(ZoneType.PARKING, "Parking Texas", 40, 1220));
        zones.add(new Zone(ZoneType.RESIDENTIAL, "Verde Brisa 3", 290, 1130));
        zones.add(new Zone(ZoneType.COMMERCIAL, "Plaza de la 40", 500, 1130));
        zones.add(new Zone(ZoneType.COMMERCIAL, "Plaza de la 30", 706, 1130));
        zones.add(new Zone(ZoneType.PARK, "Parque del gato", 942, 1130));
        zones.add(new Zone(ZoneType.COMMERCIAL, "Plaza de la 10", 1261, 1130));


    }

    public void initCorners() {

        //36 vertices

        corners.addVertex("Hospital", 945, 968);
        corners.addVertex("Bomberos", 244, 223);
        corners.addVertex("Policia", 670, 678);

        // Fila 0 -> vertices superiores
        corners.addVertex("B0", 151, 0);
        corners.addVertex("C0", 391, 0);
        corners.addVertex("D0", 599, 0);
        corners.addVertex("E0", 823, 0);
        corners.addVertex("F0", 1111, 0);

        // Fila 1
        corners.addVertex("A1", 0, 223);
        corners.addVertex("B1", 151, 223);
        corners.addVertex("C1", 391, 223);
        corners.addVertex("D1", 599, 223);
        corners.addVertex("E1", 823, 223);
        corners.addVertex("F1", 1111, 223);
        corners.addVertex("G1", 1280, 223);

        // Fila 2
        corners.addVertex("A2", 0, 511);
        corners.addVertex("B2", 151, 511);
        corners.addVertex("C2", 391, 511);
        corners.addVertex("D2", 599, 511);
        corners.addVertex("E2", 823, 511);
        corners.addVertex("F2", 1111, 511);
        corners.addVertex("G2", 1280, 511);

        // Fila 3
        corners.addVertex("A3", 0, 752);
        corners.addVertex("B3", 151, 752);
        corners.addVertex("C3", 391, 752);
        corners.addVertex("D3", 599, 752);
        corners.addVertex("E3", 823, 752);
        corners.addVertex("F3", 1111, 752);
        corners.addVertex("G3", 1280, 752);

        // Fila 4
        corners.addVertex("B4", 151, 991);
        corners.addVertex("C4", 391, 991);
        corners.addVertex("D4", 599, 991);
        corners.addVertex("E4", 823, 991);
        corners.addVertex("F4", 1111, 991);

        // Fila 5
        corners.addVertex("B5", 151, 1202);
        corners.addVertex("C5", 391, 1202);
        corners.addVertex("D5", 599, 1202);
        corners.addVertex("E5", 823, 1202);
        corners.addVertex("F5", 1111, 1202);

        // Fila 6 -> vertices inferiores
        corners.addVertex("B6", 151, 1280);
        corners.addVertex("C6", 391, 1280);
        corners.addVertex("D6", 599, 1280);
        corners.addVertex("E6", 823, 1280);
        corners.addVertex("F6", 1111, 1280);


        // Conexiones fila 1
        corners.connectVertices("A1", "B1");
        corners.connectVertices("B1", "C1");
        corners.connectVertices("C1", "D1");
        corners.connectVertices("D1", "E1");
        corners.connectVertices("E1", "F1");
        corners.connectVertices("F1", "G1");

        // Conexiones fila 2
        corners.connectVertices("A2", "B2");
        corners.connectVertices("B2", "C2");
        corners.connectVertices("C2", "D2");
        corners.connectVertices("D2", "E2");
        corners.connectVertices("E2", "F2");
        corners.connectVertices("F2", "G2");

        // Conexiones fila 3
        corners.connectVertices("A3", "B3");
        corners.connectVertices("B3", "C3");
        corners.connectVertices("C3", "D3");
        corners.connectVertices("D3", "E3");
        corners.connectVertices("E3", "F3");
        corners.connectVertices("F3", "G3");

        // Conexiones fila 4
        corners.connectVertices("B4", "C4");
        corners.connectVertices("C4", "D4");
        corners.connectVertices("D4", "E4");
        corners.connectVertices("E4", "F4");

        // Conexiones fila 5
        corners.connectVertices("B5", "C5");
        corners.connectVertices("C5", "D5");
        corners.connectVertices("D5", "E5");
        corners.connectVertices("E5", "F5");

        // Conexiones columna 2
        corners.connectVertices("B0", "B1");
        corners.connectVertices("B1", "B2");
        corners.connectVertices("B2", "B3");
        corners.connectVertices("B3", "B4");
        corners.connectVertices("B4", "B5");
        corners.connectVertices("B5", "B6");

        // Conexiones columna 3
        corners.connectVertices("C0", "C1");
        corners.connectVertices("C1", "C2");
        corners.connectVertices("C2", "C3");
        corners.connectVertices("C3", "C4");
        corners.connectVertices("C4", "C5");
        corners.connectVertices("C5", "C6");

        // Conexiones columna 4
        corners.connectVertices("D0", "D1");
        corners.connectVertices("D1", "D2");
        corners.connectVertices("D2", "D3");
        corners.connectVertices("D3", "D4");
        corners.connectVertices("D4", "D5");
        corners.connectVertices("D5", "D6");

        // Conexiones columna 5
        corners.connectVertices("E0", "E1");
        corners.connectVertices("E1", "E2");
        corners.connectVertices("E2", "E3");
        corners.connectVertices("E3", "E4");
        corners.connectVertices("E4", "E5");
        corners.connectVertices("E5", "E6");

        // Conexiones columna 6
        corners.connectVertices("F0", "F1");
        corners.connectVertices("F1", "F2");
        corners.connectVertices("F2", "F3");
        corners.connectVertices("F3", "F4");
        corners.connectVertices("F4", "F5");
        corners.connectVertices("F5", "F6");

        //Conexion Hospital
        corners.connectVertices("Hospital", "E4");
        corners.connectVertices("Hospital", "F4");

        corners.connectVertices("Bomberos", "B1");
        corners.connectVertices("Bomberos", "C1");

        corners.connectVertices("Policia", "D3");
        corners.connectVertices("Policia", "E3");

    }

    public void initBlocks() {
        // Bloques fila 0
        blocks.add(new Block(0, 0, 71, 131));
        blocks.add(new Block(230, 0, 116, 131));
        blocks.add(new Block(438, 0, 116, 131));
        blocks.add(new Block(646, 0, 116, 131));
        blocks.add(new Block(886, 0, 116, 131));
        blocks.add(new Block(1206, 0, 67, 131));

        // Bloques fila 1
        blocks.add(new Block(0, 313, 71, 106));
        blocks.add(new Block(230, 313, 116, 106));
        blocks.add(new Block(438, 313, 116, 106));
        blocks.add(new Block(646, 313, 116, 106));
        blocks.add(new Block(886, 313, 116, 106));
        blocks.add(new Block(1206, 313, 67, 106));

        // Bloques fila 2
        blocks.add(new Block(0, 585, 71, 106));
        blocks.add(new Block(230, 585, 116, 106));
        blocks.add(new Block(438, 585, 116, 106));
        blocks.add(new Block(646, 585, 116, 106));
        blocks.add(new Block(886, 585, 116, 106));
        blocks.add(new Block(1206, 585, 67, 106));

        // Bloques fila 3
        blocks.add(new Block(230, 825, 116, 106));
        blocks.add(new Block(438, 825, 116, 106));
        blocks.add(new Block(646, 825, 116, 106));
        blocks.add(new Block(886, 825, 116, 106));

        // Bloques fila 4
        blocks.add(new Block(230, 1048, 116, 106));
        blocks.add(new Block(438, 1048, 116, 106));
        blocks.add(new Block(646, 1048, 116, 106));
        blocks.add(new Block(886, 1048, 116, 106));

        // Bloques fila 5
        blocks.add(new Block(230, 1257, 116, 32));
        blocks.add(new Block(438, 1257, 116, 32));
        blocks.add(new Block(646, 1257, 116, 32));
        blocks.add(new Block(886, 1257, 116, 32));

        // Bloque lateral izquierdo
        blocks.add(new Block(0, 825, 71, 464));

        // Bloque lateral derecho
        blocks.add(new Block(1206, 825, 67, 464));
    }

    public void initRoutes() {

        // Fila 1
        routes.insert(new Route(ZoneType.MAIN_STREET, "Oak Avenue", corners.getVertex("A1"), corners.getVertex("B1")));
        routes.insert(new Route(ZoneType.MAIN_STREET, "Oak Avenue", corners.getVertex("B1"), corners.getVertex("C1")));
        routes.insert(new Route(ZoneType.MAIN_STREET, "Maple Street", corners.getVertex("C1"), corners.getVertex("D1")));
        routes.insert(new Route(ZoneType.MAIN_STREET, "Maple Street", corners.getVertex("D1"), corners.getVertex("E1")));
        routes.insert(new Route(ZoneType.MAIN_STREET, "Maple Street", corners.getVertex("E1"), corners.getVertex("F1")));
        routes.insert(new Route(ZoneType.MAIN_STREET, "Pine Boulevard", corners.getVertex("F1"), corners.getVertex("G1")));

        // Fila 2
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Cedar Lane", corners.getVertex("A2"), corners.getVertex("B2")));
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Cedar Lane", corners.getVertex("B2"), corners.getVertex("C2")));
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Birch Way", corners.getVertex("C2"), corners.getVertex("D2")));
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Birch Way", corners.getVertex("D2"), corners.getVertex("E2")));
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Elm Drive", corners.getVertex("E2"), corners.getVertex("F2")));
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Elm Drive", corners.getVertex("F2"), corners.getVertex("G2")));

        // Fila 3
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Willow Road", corners.getVertex("A3"), corners.getVertex("B3")));
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Willow Road", corners.getVertex("B3"), corners.getVertex("C3")));
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Ash Street", corners.getVertex("C3"), corners.getVertex("D3")));
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Ash Street", corners.getVertex("D3"), corners.getVertex("E3")));
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Cherry Way", corners.getVertex("E3"), corners.getVertex("F3")));
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Cherry Way", corners.getVertex("F3"), corners.getVertex("G3")));

        // Fila 4
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Juniper Street", corners.getVertex("B4"), corners.getVertex("C4")));
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Juniper Street", corners.getVertex("C4"), corners.getVertex("D4")));
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Spruce Drive", corners.getVertex("D4"), corners.getVertex("E4")));
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Spruce Drive", corners.getVertex("E4"), corners.getVertex("F4")));

        // Fila 5
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Magnolia Street", corners.getVertex("B5"), corners.getVertex("C5")));
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Magnolia Street", corners.getVertex("C5"), corners.getVertex("D5")));
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Poplar Way", corners.getVertex("D5"), corners.getVertex("E5")));
        routes.insert(new Route(ZoneType.RESIDENTIAL, "Poplar Way", corners.getVertex("E5"), corners.getVertex("F5")));

        // Columna 2
        routes.insert(new Route(ZoneType.AVENUE, "Second Avenue", corners.getVertex("B0"), corners.getVertex("B1")));
        routes.insert(new Route(ZoneType.AVENUE, "Second Avenue", corners.getVertex("B1"), corners.getVertex("B2")));
        routes.insert(new Route(ZoneType.AVENUE, "Second Avenue", corners.getVertex("B2"), corners.getVertex("B3")));
        routes.insert(new Route(ZoneType.AVENUE, "Second Avenue", corners.getVertex("B3"), corners.getVertex("B4")));
        routes.insert(new Route(ZoneType.AVENUE, "Second Avenue", corners.getVertex("B4"), corners.getVertex("B5")));
        routes.insert(new Route(ZoneType.AVENUE, "Second Avenue", corners.getVertex("B5"), corners.getVertex("B6")));

        // Columna 3
        routes.insert(new Route(ZoneType.AVENUE, "Third Avenue", corners.getVertex("C0"), corners.getVertex("C1")));
        routes.insert(new Route(ZoneType.AVENUE, "Third Avenue", corners.getVertex("C1"), corners.getVertex("C2")));
        routes.insert(new Route(ZoneType.AVENUE, "Third Avenue", corners.getVertex("C2"), corners.getVertex("C3")));
        routes.insert(new Route(ZoneType.AVENUE, "Third Avenue", corners.getVertex("C3"), corners.getVertex("C4")));
        routes.insert(new Route(ZoneType.AVENUE, "Third Avenue", corners.getVertex("C4"), corners.getVertex("C5")));
        routes.insert(new Route(ZoneType.AVENUE, "Third Avenue", corners.getVertex("C5"), corners.getVertex("C6")));

        // Columna 4
        routes.insert(new Route(ZoneType.AVENUE, "Fourth Avenue", corners.getVertex("D0"), corners.getVertex("D1")));
        routes.insert(new Route(ZoneType.AVENUE, "Fourth Avenue", corners.getVertex("D1"), corners.getVertex("D2")));
        routes.insert(new Route(ZoneType.AVENUE, "Fourth Avenue", corners.getVertex("D2"), corners.getVertex("D3")));
        routes.insert(new Route(ZoneType.AVENUE, "Fourth Avenue", corners.getVertex("D3"), corners.getVertex("D4")));
        routes.insert(new Route(ZoneType.AVENUE, "Fourth Avenue", corners.getVertex("D4"), corners.getVertex("D5")));
        routes.insert(new Route(ZoneType.AVENUE, "Fourth Avenue", corners.getVertex("D5"), corners.getVertex("D6")));

        // Columna 5
        routes.insert(new Route(ZoneType.AVENUE, "Fifth Avenue", corners.getVertex("E0"), corners.getVertex("E1")));
        routes.insert(new Route(ZoneType.AVENUE, "Fifth Avenue", corners.getVertex("E1"), corners.getVertex("E2")));
        routes.insert(new Route(ZoneType.AVENUE, "Fifth Avenue", corners.getVertex("E2"), corners.getVertex("E3")));
        routes.insert(new Route(ZoneType.AVENUE, "Fifth Avenue", corners.getVertex("E3"), corners.getVertex("E4")));
        routes.insert(new Route(ZoneType.AVENUE, "Fifth Avenue", corners.getVertex("E4"), corners.getVertex("E5")));
        routes.insert(new Route(ZoneType.AVENUE, "Fifth Avenue", corners.getVertex("E5"), corners.getVertex("E6")));

        // Columna 6
        routes.insert(new Route(ZoneType.AVENUE, "Sixth Avenue", corners.getVertex("F0"), corners.getVertex("F1")));
        routes.insert(new Route(ZoneType.AVENUE, "Sixth Avenue", corners.getVertex("F1"), corners.getVertex("F2")));
        routes.insert(new Route(ZoneType.AVENUE, "Sixth Avenue", corners.getVertex("F2"), corners.getVertex("F3")));
        routes.insert(new Route(ZoneType.AVENUE, "Sixth Avenue", corners.getVertex("F3"), corners.getVertex("F4")));
        routes.insert(new Route(ZoneType.AVENUE, "Sixth Avenue", corners.getVertex("F4"), corners.getVertex("F5")));
        routes.insert(new Route(ZoneType.AVENUE, "Sixth Avenue", corners.getVertex("F5"), corners.getVertex("F6")));

        // Conexiones especiales
        routes.insert(new Route(ZoneType.HEALTHCARE, "Hospital Access Road", corners.getVertex("Hospital"), corners.getVertex("E4")));
        routes.insert(new Route(ZoneType.HEALTHCARE, "Hospital Access Road", corners.getVertex("Hospital"), corners.getVertex("F4")));

        routes.insert(new Route(ZoneType.EMERGENCY, "Fire Station Lane", corners.getVertex("Bomberos"), corners.getVertex("B1")));
        routes.insert(new Route(ZoneType.EMERGENCY, "Fire Station Lane", corners.getVertex("Bomberos"), corners.getVertex("C1")));

        routes.insert(new Route(ZoneType.EMERGENCY, "Police Alley", corners.getVertex("Policia"), corners.getVertex("D3")));
        routes.insert(new Route(ZoneType.EMERGENCY, "Police Alley", corners.getVertex("Policia"), corners.getVertex("E3")));

    }


    public List<Block> getBlocks() {
        return blocks;
    }

    public Graph getCorners() {
        return corners;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void paint(GraphicsContext gc){
         gc.drawImage(image, 0,0);
    }

    public List<Zone> getZones() {
        return zones;
    }

    public BinarySearchTree<Route> getRoutes() {
        return routes;
    }
}

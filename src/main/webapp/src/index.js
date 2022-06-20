import "./styles.css";
import "ol/ol.css";

import proj4 from "proj4";
import {register} from "ol/proj/proj4";

import {Feature, Map, View} from "ol";
import OSM from "ol/source/OSM";
import VectorSource from "ol/source/Vector";
import {Tile as TileLayer, Vector as VectorLayer} from "ol/layer";
import {defaults as defaultControls, ScaleLine} from "ol/control";
import {LineString, Point} from "ol/geom";
import axios from "axios";
import {Circle, Fill, Stroke, Style, Text} from "ol/style";
import TextPlacement from "ol/style/TextPlacement";

// Adding Sweref99 coordinate system
proj4.defs("EPSG:3006", "+proj=utm +zone=33 +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs");
proj4.defs(
    "http://www.opengis.net/gml/srs/epsg.xml#3006",
    "+proj=utm +zone=33 +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs"
);
register(proj4);


const createBackgroundLayer = () => {
    return new TileLayer({
        source: new OSM()
    });
}

const createLocationsLayer = () => {
    const locationsLayerSource = new VectorSource({
        loader: function (extent, resolution, projection, success, failure) {
            axios({url: "http://localhost:8080/api/locations"})
                .then((response) => {
                    console.log(response.data);
                    const features = response.data.map(location => {
                        return new Feature({
                            geometry: new Point(location.point.coordinates),
                            locationType: location.type,
                            locationLabel: location.label
                        });
                    })

                    locationsLayerSource.addFeatures(features);
                    success(features);
                })
                .catch(error => {
                    console.warn(error);
                    failure();
                });
        }
    });

    return new VectorLayer({
        source: locationsLayerSource,
        style: function (feature) {
            const color = feature.get("locationType").toLowerCase();
            return new Style({
                image: new Circle({
                    radius: 3,
                    fill: null,
                    stroke: new Stroke({color: color, width: 2})
                }),
                text: new Text({
                    text: feature.get("locationLabel"),
                    fill: new Fill({color: "#30385B"}),
                    offsetY: -12,
                    font: "12px sans-serif"
                })
            });
        }
    });
}

const createWaysLayer = () => {
    const waysLayerSource = new VectorSource({
        loader: function (extent, resolution, projection, success, failure) {
            axios({url: "http://localhost:8080/api/ways"})
                .then((response) => {
                    console.log(response.data);
                    const features = response.data.map(way => {
                        return new Feature({
                            geometry: new LineString(way.geometry.coordinates),
                            label: way.label
                        });
                    })

                    waysLayerSource.addFeatures(features);
                    success(features);
                })
                .catch(error => {
                    console.warn(error);
                    failure();
                });
        }
    });

    return new VectorLayer({
        source: waysLayerSource,
        style: function (feature) {
            return new Style({
                stroke: new Stroke({ color: "#30385B", width: 1 }),
                text: new Text({
                    text: feature.get("label"),
                    fill: new Fill({color: "#30385B"}),
                    placement: TextPlacement.LINE,
                    textBaseline: "top",
                    font: "12px sans-serif"
                })
            });
        }
    });
}


const layers = [createBackgroundLayer(), createWaysLayer(), createLocationsLayer()];

new Map({
    target: "map",
    controls: defaultControls().extend([
        new ScaleLine({
            units: "metric"
        })
    ]),
    layers: layers,
    view: new View({
        projection: "EPSG:3006",
        center: [500000, 6800000],
        zoom: 8
    })
});
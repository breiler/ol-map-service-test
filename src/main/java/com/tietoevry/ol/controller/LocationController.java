package com.tietoevry.ol.controller;


import java.util.Arrays;
import java.util.List;

import com.tietoevry.ol.contract.Location;
import com.tietoevry.ol.contract.LocationType;
import com.tietoevry.ol.contract.Way;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {

  @RequestMapping(value = "/api/locations", method = RequestMethod.GET)
  @CrossOrigin(origins = "*", allowedHeaders = "*")
  public ResponseEntity<List<Location>> getLocations() {
    GeometryFactory geometryFactory = new GeometryFactory();

    List<Location> locations = Arrays.asList(
        Location.builder()
            .label("First point")
            .point(geometryFactory.createPoint(new Coordinate(410400, 6601760)))
            .type(LocationType.GREEN)
            .build(),
        Location.builder()
            .label("Second point")
            .point(geometryFactory.createPoint(new Coordinate(542560, 6702560)))
            .type(LocationType.RED)
            .build(),
        Location.builder()
            .label("Third point")
            .point(geometryFactory.createPoint(new Coordinate(448480, 6736160)))
            .type(LocationType.YELLOW)
            .build());

    return ResponseEntity.ok(locations);
  }

  @RequestMapping(value = "/api/ways", method = RequestMethod.GET)
  @CrossOrigin(origins = "*", allowedHeaders = "*")
  public ResponseEntity<List<Way>> getWay() {
    GeometryFactory geometryFactory = new GeometryFactory();

    List<Way> locations = Arrays.asList(
        Way.builder()
            .label("First way")
            .geometry(geometryFactory.createLineString(new Coordinate[]{new Coordinate(410400, 6601760), new Coordinate(542560, 6702560)}))
            .build(),
        Way.builder()
            .label("Second way")
            .geometry(geometryFactory.createLineString(new Coordinate[]{new Coordinate(542560, 6702560), new Coordinate(448480, 6736160)}))
            .build(),
        Way.builder()
            .label("Third way")
            .geometry(geometryFactory.createLineString(new Coordinate[]{new Coordinate(448480, 6736160), new Coordinate(410400, 6601760)}))
            .build());

    return ResponseEntity.ok(locations);
  }
}

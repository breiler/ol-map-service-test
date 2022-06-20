package com.tietoevry.ol.contract;

import lombok.Builder;
import lombok.Data;
import org.locationtech.jts.geom.Point;

@Data
@Builder
public class Location {
  private LocationType type;
  private String label;
  private Point point;
}

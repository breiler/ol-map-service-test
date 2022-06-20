package com.tietoevry.ol.contract;

import lombok.Builder;
import lombok.Data;
import org.locationtech.jts.geom.LineString;

@Data
@Builder
public class Way {
  private String label;
  private LineString geometry;
}

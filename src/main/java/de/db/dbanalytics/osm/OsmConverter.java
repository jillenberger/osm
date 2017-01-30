package de.db.dbanalytics.osm;/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2017 by the members listed in the COPYING,       *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.xml.dynsax.OsmXmlIterator;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

/**
 * @author jillenberger
 */
public class OsmConverter {

    private Collection<OsmWayHandler> wayHandlers;

    private Map<Long, OsmNode> osmNodes;

    public void convert(InputStream stream) {
        NetworkBuilder builder = new NetworkBuilder();
        /*
        Load all nodes.
         */
        OsmIterator it = new OsmXmlIterator(stream, false);
        for(EntityContainer container : it) {
            if(container.getType() == EntityType.Node) {
                OsmNode node = (OsmNode) container.getEntity();
                osmNodes.put(node.getId(), node);
            }
        }
        /*
        Iterate through all ways
         */
        it = new OsmXmlIterator(stream, false);
        for(EntityContainer container : it) {
            if(container.getType() == EntityType.Way) {
                for(OsmWayHandler handler : wayHandlers) {
                    handler.handle((OsmWay) container.getEntity(), builder);
                }
            }
        }
    }
}

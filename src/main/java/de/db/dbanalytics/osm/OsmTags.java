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

/**
 * @author jillenberger
 */
public interface OsmTags {

    String VALUE_YES = "yes";

    String VALUE_NO = "no";

    String VALUE_TRUE = "true";

    String VALUE_FALSE = "false";

    String VALUE_ONE = "1";

    String VALUE_ZERO = "0";

    String VALUE_MINUSONE = "-1";

    String VALUE_ROUNDABOUT = "roundabout";

    String KEY_ACCESS = "access";

    String KEY_JUNCTION = "junction";

    String KEY_ONEWAY = "oneway";
}

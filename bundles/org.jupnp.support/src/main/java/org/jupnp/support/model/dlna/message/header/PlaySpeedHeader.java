/*
 * Copyright (C) 2013 4th Line GmbH, Switzerland
 *
 * The contents of this file are subject to the terms of either the GNU
 * Lesser General Public License Version 2 or later ("LGPL") or the
 * Common Development and Distribution License Version 1 or later
 * ("CDDL") (collectively, the "License"). You may not use this file
 * except in compliance with the License. See LICENSE.txt for more
 * information.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.jupnp.support.model.dlna.message.header;

import org.jupnp.model.message.header.InvalidHeaderException;
import org.jupnp.model.types.InvalidValueException;
import org.jupnp.support.avtransport.lastchange.AVTransportVariable.TransportPlaySpeed;

/**
 * @author Mario Franco
 * @author Amit Kumar Mondal - Code Refactoring
 */
public class PlaySpeedHeader extends DLNAHeader<TransportPlaySpeed> {

    public PlaySpeedHeader() {
    }

    public PlaySpeedHeader(TransportPlaySpeed speed) {
        setValue(speed);
    }

    @Override
    public void setString(String s) {
        if (s.length() != 0) {
            try {
                TransportPlaySpeed t = new TransportPlaySpeed(s);
                setValue(t);
                return;
            } catch (InvalidValueException invalidValueException) {
              //no need to take any precaution measure
            }
        }
        throw new InvalidHeaderException("Invalid PlaySpeed header value: " + s);
    }

    @Override
    public String getString() {
        return getValue().getValue();
    }
}

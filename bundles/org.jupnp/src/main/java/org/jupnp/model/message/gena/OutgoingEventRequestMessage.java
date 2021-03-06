/**
 * Copyright (C) 2014 4th Line GmbH, Switzerland and others
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License Version 1 or later
 * ("CDDL") (collectively, the "License"). You may not use this file
 * except in compliance with the License. See LICENSE.txt for more
 * information.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

package org.jupnp.model.message.gena;

import org.jupnp.model.gena.GENASubscription;
import org.jupnp.model.message.StreamRequestMessage;
import org.jupnp.model.message.UpnpRequest;
import org.jupnp.model.message.header.ContentTypeHeader;
import org.jupnp.model.message.header.EventSequenceHeader;
import org.jupnp.model.message.header.NTEventHeader;
import org.jupnp.model.message.header.NTSHeader;
import org.jupnp.model.message.header.SubscriptionIdHeader;
import org.jupnp.model.message.header.UpnpHeader;
import org.jupnp.model.state.StateVariableValue;
import org.jupnp.model.types.NotificationSubtype;
import org.jupnp.model.types.UnsignedIntegerFourBytes;

import java.net.URL;
import java.util.Collection;

/**
 * @author Christian Bauer
 */
public class OutgoingEventRequestMessage extends StreamRequestMessage {

    final private Collection<StateVariableValue> stateVariableValues;

    public OutgoingEventRequestMessage(GENASubscription subscription,
                                       URL callbackURL,
                                       UnsignedIntegerFourBytes sequence,
                                       Collection<StateVariableValue> values) {

        super(new UpnpRequest(UpnpRequest.Method.NOTIFY, callbackURL));

        getHeaders().add(UpnpHeader.Type.CONTENT_TYPE, new ContentTypeHeader());
        getHeaders().add(UpnpHeader.Type.NT, new NTEventHeader());
        getHeaders().add(UpnpHeader.Type.NTS, new NTSHeader(NotificationSubtype.PROPCHANGE));
        getHeaders().add(UpnpHeader.Type.SID, new SubscriptionIdHeader(subscription.getSubscriptionId()));

        // Important! Pass by value so that we can safely increment it afterwards and before this is send!
        getHeaders().add(UpnpHeader.Type.SEQ, new EventSequenceHeader(sequence.getValue()));

        this.stateVariableValues = values;
    }

    public OutgoingEventRequestMessage(GENASubscription subscription, URL callbackURL) {
        this(subscription, callbackURL, subscription.getCurrentSequence(), subscription.getCurrentValues().values());
    }

    public Collection<StateVariableValue> getStateVariableValues() {
        return stateVariableValues;
    }
}

/*
 * Copyright (c) 2023. Andrea Giulianelli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.webbasedwodt;

import io.github.webbasedwodt.adapter.WoDTDigitalAdapter;
import io.github.webbasedwodt.adapter.WoDTDigitalAdapterConfiguration;
import io.github.webbasedwodt.ontology.TrafficLightOntology;
import io.github.webbasedwodt.physicaladapter.TrafficLightPhysicalAdapter;
import io.github.webbasedwodt.shadowing.MirrorShadowingFunction;
import it.wldt.core.engine.DigitalTwin;
import it.wldt.core.engine.DigitalTwinEngine;
import it.wldt.exception.EventBusException;
import it.wldt.exception.ModelException;
import it.wldt.exception.WldtConfigurationException;
import it.wldt.exception.WldtDigitalTwinStateException;
import it.wldt.exception.WldtEngineException;
import it.wldt.exception.WldtRuntimeException;
import it.wldt.exception.WldtWorkerException;

import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Java template project.
 */
public final class Launcher {
    private static final String EXPOSED_PORT_VARIABLE = "EXPOSED_PORT";
    private static final String PLATFORM_URL_VARIABLE = "PLATFORM_URL";
    private static final String PHYSICAL_ASSET_ID_VARIABLE = "PHYSICAL_ASSET_ID";

    static {
        // Checks on existence of environmental variables
        Objects.requireNonNull(System.getenv(EXPOSED_PORT_VARIABLE), "Please provide the exposed port");
        Objects.requireNonNull(System.getenv(PLATFORM_URL_VARIABLE), "Please provide the platform url");
        Objects.requireNonNull(System.getenv(PHYSICAL_ASSET_ID_VARIABLE), "Please provide the physical asset id");
    }

    private Launcher() { }

    /**
     * Main function.
     * @param args
     */
    public static void main(final String[] args) {
        try {
            final String trafficLightDTId = "traffic-light-dt";
            final int portNumber = Integer.parseInt(System.getenv(EXPOSED_PORT_VARIABLE));
            final DigitalTwin trafficLightDT = new DigitalTwin(trafficLightDTId, new MirrorShadowingFunction());
            trafficLightDT.addPhysicalAdapter(new TrafficLightPhysicalAdapter());
            trafficLightDT.addDigitalAdapter(new WoDTDigitalAdapter(
                    "wodt-dt-adapter",
                    new WoDTDigitalAdapterConfiguration(
                            "http://localhost:" + portNumber + "/",
                            new TrafficLightOntology(),
                            portNumber,
                            System.getenv(PHYSICAL_ASSET_ID_VARIABLE),
                            Set.of(System.getenv(PLATFORM_URL_VARIABLE)))
            ));

            final DigitalTwinEngine digitalTwinEngine = new DigitalTwinEngine();
            digitalTwinEngine.addDigitalTwin(trafficLightDT);
            digitalTwinEngine.startDigitalTwin(trafficLightDTId);
        } catch (ModelException
                 | WldtDigitalTwinStateException
                 | WldtWorkerException
                 | WldtRuntimeException
                 | EventBusException
                 | WldtConfigurationException
                 | WldtEngineException e) {
            Logger.getLogger(Launcher.class.getName()).info(e.getMessage());
        }
    }
}

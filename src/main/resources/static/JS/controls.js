import { baseLayers, overlayLayers } from "./layers.js";

export function addControls(map) {
    L.control.layers(baseLayers, overlayLayers).addTo(map);
    L.control.scale().addTo(map);
}
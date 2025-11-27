import {baseLayers} from './layers.js';

//inicializar el mapa.

export let map = null;

export function crearMapa() {
    if(map) return map; // evitar recrear el mapa si ya existe.
    
    map = L.map('map', {
        center: [-53.779688, -67.713832 ],
        zoom: 13,
        layers: [baseLayers.osm]
    });

    return map;
}

let mapCliclkCallbacks = [];

export function onMapClick(callback) {
    mapCliclkCallbacks.push(callback);
}

// Llamar a los callbacks registrados cuando se hace clic en el mapa
export function attachMapClickHandler(map) {
    map.on("click", (e) => {
        const lat = e.latlng.lat;
        const lng = e.latlng.lng;

        //ejectuar todos los callbacks registrados.
        mapCliclkCallbacks.forEach(cb => cb(lat, lng));
    });
}
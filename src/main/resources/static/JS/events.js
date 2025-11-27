import { agregarMarcador } from "./markers.js";


export function registrarEventosMapa(map) {
    map.on("click", (e) => {
        const { lat, lng} = e.latlng;
        agregarMarcador(map, lat,lng);


        const status = document.getElementById("mapStatus");
        if (status) {
            status.textContent = `Ubicacion seleccionadad: ${lat.toFixed(5)}, ${lng.toFixed(5)}`;
        }
    });
}
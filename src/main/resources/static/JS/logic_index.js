// imports

import { crearMapa, onMapClick, attachMapClickHandler } from "./map.js";
import { reverseGeocode } from "./nominatim.js";
import { getTramitesForLocation, renderTramites, clearTramites } from "./tramites.js";
import { addControls } from "./controls.js";
import { registrarEventosMapa } from "./events.js";
import { buscarCoordenadas } from "./search.js";
import { agregarMarcador, eliminarMarcador } from "./markers.js";
import { abrirModalTramite, cerrarModal } from "./modal.js";
import { updateSelectedAddress } from "./UI.js";

const selectedAddress = document.getElementById("selectedAddress");
const selectedCoords = document.getElementById("selectedCoords");
const clearBtn = document.getElementById("clearSelectionBtn");
// Inicializar el mapa
const map = crearMapa();

// Crear el mapa
attachMapClickHandler(map);
registrarEventosMapa(map);

onMapClick(async (lat, lng) => {
    selectedCoords.textContent = `Lat: ${lat.toFixed(5)}, Lng: ${lng.toFixed(5)}`;
    selectedAddress.textContent = "Cargando dirección...";
    try {
        const data = await reverseGeocode(lat, lng);
        selectedAddress.textContent = data?.display_name || "(sin direccion)";
    } catch {
        selectedAddress.textContent = "(error al obtener direccion)";
    }

    const tramites = getTramitesForLocation(lat, lng);
    renderTramites(tramites);
});
clearBtn.addEventListener("click", () => {
    selectedCoords.textContent = "Ninguna ubicación seleccionada";
    selectedAddress.textContent = "";
    clearTramites();
    map.setView([-53.779688, -67.713832], 13); // Centrar el mapa en la ubicación inicial.
});
// Agregar controles al mapa
addControls(map);

//agregar marcadores.

//busqueda con boton o imput.
document.getElementById("searchBtn").addEventListener("click", async () => {
    const query = document.getElementById("searchInput").value;
    if (!query) return;

    const results = await buscarCoordenadas (query);
    if (results.length > 0) {
        const { lat, lon } = results[0];
        agregarMarcador(map, lat, lon);
    } else {
        alert ("No se encontraron resultados para la búsqueda.");
    }
});

//--------------------
//--EVENTOS----//
//--------------------

//boton para buscar tramites.
document.getElementById("searchBtn").addEventListener("click", async () => {
    const status = document.getElementById("mapStatus");
    if (!status) return;
    const coordsText = status.textContent;
    const match = coordsText.match(/([-+]?\d*\.\d+|\d+),\s*([-+]?\d*\.\d+|\d+)/); // Expresión regular para extraer latitud y longitud
    if (!match) {
        alert ("Por favor, selecciona una ubicación en el mapa primero.");
        return;
    }
    const lat = parseFloat(match[1]);
    const lng = parseFloat(match[2]);
    const tramites = getTramitesForLocation(lat, lng);
    renderTramites(tramites);
});

//boton para eliminar marcador.
document.getElementById("clearSelectionBtn").addEventListener("click", () => {
    eliminarMarcador();
});


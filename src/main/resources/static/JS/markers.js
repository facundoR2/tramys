let curretMarker = null; // variable para almacenar el marcador actual.

export function agregarMarcador(map, lat, lon) {
    if (curretMarker) curretMarker.remove(); // si existe un marcador anterior, lo elimina.
    curretMarker = L.marker([lat, lon]).addTo(map); // creamos el marcador y lo agrego
    map.setView([lat, lon], 13); // se centra el mapa en el marcador.
}

export function eliminarMarcador() {
    if (curretMarker) {
        curretMarker.remove();
        curretMarker = null;
    }
}
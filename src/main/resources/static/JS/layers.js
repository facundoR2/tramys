export const baseLayers = {
    osm: L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
        maxZoom: 19,
        attribution: '© OpenStreetMap'
    }),
    toner: L.tileLayer("https://stame-tiles.a.ssl.fastly.net/toner/{z}/{x}/{y}.png", {
        attribution: 'Map tiles by Stamen, under CC BY 3.0. Data by OpenStreetMap, under ODbL.'
    })
};

export const overlayLayers = {
    puntos: L.layerGroup(),
};
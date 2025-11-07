// Inicializar mapa
const map = L.map('map').setView([ -34.6037, -58.3816 ], 13); // por defecto: Buenos Aires
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '© OpenStreetMap'
}).addTo(map);

let marker = null;
const mapStatus = document.getElementById('mapStatus');
const selectedCoords = document.getElementById('selectedCoords');
const selectedAddress = document.getElementById('selectedAddress');
const tramitesContainer = document.getElementById('tramitesContainer');
const noSelectionNote = document.getElementById('noSelectionNote');

// Mock función que devuelve trámites según ubicación (ejemplos)
function getTramitesForLocation(lat, lng) {
    // Ejemplo simple: variar resultados según cuadrante
    const list = [];
    if (lat > 0) list.push({ title: 'Inscripción de domicilio', desc: 'Registro de domicilio para residentes.' });
    else list.push({ title: 'Cambio de domicilio', desc: 'Trámite para actualizar tu dirección.' });

    if (lng > 0) list.push({ title: 'Certificado de antecedentes', desc: 'Solicita certificado nacional.' });
    else list.push({ title: 'Permiso de circulación', desc: 'Trámite municipal para vehículos.' });

    list.push({ title: 'Turno presencial', desc: 'Solicitar turno en la oficina más cercana.' });
    list.push({ title: 'Consulta de requisitos', desc: 'Ver documentos necesarios para un trámite.' });

    return list;
}

// Mostrar trámites en panel
function renderTramites(tramites) {
    tramitesContainer.innerHTML = '';
    if (!tramites || tramites.length === 0) {
        tramitesContainer.appendChild(noSelectionNote);
        return;
    }
    tramites.forEach(t => {
        const el = document.createElement('div');
        el.className = 'tramite';
        el.innerHTML = `
        <div class="meta">
        <div class="title">${t.title}</div>
        <div class="desc">${t.desc}</div>
        </div>
        <div style="display:flex;flex-direction:column;gap:6px;align-items:flex-end">
        <button class="btn" style="padding:6px 10px;font-size:13px">Iniciar</button>
        <button class="btn secondary" style="padding:6px 8px;font-size:12px">Detalles</button>
        </div>
        `;
        tramitesContainer.appendChild(el);
    });
}

// Al hacer click en el mapa: marcar y mostrar tramites
map.on('click', async function(e) {
    const {lat, lng} = e.latlng;
    if (marker) map.removeLayer(marker);
    marker = L.marker([lat,lng]).addTo(map);
    mapStatus.textContent = `Ubicación seleccionada: ${lat.toFixed(6)}, ${lng.toFixed(6)}`;
    selectedCoords.textContent = `Coordenadas: ${lat.toFixed(6)}, ${lng.toFixed(6)}`;

// Intentamos obtener dirección inversa con Nominatim (open)
    try {
        selectedAddress.textContent = 'Obteniendo dirección...';
        const res = await fetch(`https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${lat}&lon=${lng}`);
        if (res.ok) {
            const data = await res.json();
            selectedAddress.textContent = data.display_name || '';
        } else {
            selectedAddress.textContent = '';
            }
        } catch (err) {
            selectedAddress.textContent = '';
        }

        // Cargar trámites "reales" (aquí mock)
        const tramites = getTramitesForLocation(lat, lng);
        renderTramites(tramites);
});

        // Botones y búsqueda
        document.getElementById('searchBtn').addEventListener('click', async () => {
            const q = document.getElementById('searchInput').value.trim();
            if (!q) return alert('Ingrese un término de búsqueda');
            mapStatus.textContent = 'Buscando...';
            try {
                const resp = await fetch('https://nominatim.openstreetmap.org/search?format=json&q=' + encodeURIComponent(q));
                const results = await resp.json();
                if (results && results.length) {
                    const r = results[0];
                    const lat = parseFloat(r.lat), lon = parseFloat(r.lon);
                    map.setView([lat, lon], 15);
                    if (marker) map.removeLayer(marker);
                    marker = L.marker([lat, lon]).addTo(map);
                    selectedCoords.textContent = `Coordenadas: ${lat.toFixed(6)}, ${lon.toFixed(6)}`;
                    selectedAddress.textContent = r.display_name || '';
                    mapStatus.textContent = `Ubicación encontrada: ${r.display_name}`;
                    renderTramites(getTramitesForLocation(lat, lon));
                } else {
                    mapStatus.textContent = 'No se encontraron resultados';
                    alert('No se encontraron resultados para esa búsqueda.');
                }
            } catch (err) {
                mapStatus.textContent = 'Error en búsqueda';
                alert('Error al buscar. Intente nuevamente.');
            }
        });

        document.getElementById('locateBtn').addEventListener('click', () => {
            if (!navigator.geolocation) {
                alert('Geolocalización no soportada por el navegador.');
                return;
            }
            mapStatus.textContent = 'Obteniendo ubicación...';
            navigator.geolocation.getCurrentPosition(pos => {
                const lat = pos.coords.latitude, lon = pos.coords.longitude;
                map.setView([lat, lon], 15);
                if (marker) map.removeLayer(marker);
                marker = L.marker([lat, lon]).addTo(map);
                selectedCoords.textContent = `Coordenadas: ${lat.toFixed(6)}, ${lon.toFixed(6)}`;
                selectedAddress.textContent = 'Mi ubicación';
                mapStatus.textContent = 'Ubicación centrada';
                renderTramites(getTramitesForLocation(lat, lon));
            }, err => {
                alert('No se pudo obtener la ubicación: ' + err.message);
                mapStatus.textContent = 'Ubicación no disponible';
            });
        });

        // Botones header (navegación de ejemplo)
        document.getElementById('btnTramite').addEventListener('click', () => alert('Ir a: Trámites (pendiente de implementación)'));
        document.getElementById('btnDocumentos').addEventListener('click', () => alert('Ir a: Documentos (pendiente de implementación)'));
        document.getElementById('btnLugares').addEventListener('click', () => alert('Ir a: Lugares (pendiente de implementación)'));

        // Estado inicial
        renderTramites([]);
